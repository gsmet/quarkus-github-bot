package io.quarkus.bot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import org.jboss.logging.Logger;
import org.kohsuke.github.GHEventPayload;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHRepositoryDiscussion;

import io.quarkiverse.githubapp.ConfigFile;
import io.quarkiverse.githubapp.event.Discussion;
import io.quarkus.bot.config.Feature;
import io.quarkus.bot.config.QuarkusGitHubBotConfig;
import io.quarkus.bot.config.QuarkusGitHubBotConfigFile;
import io.quarkus.bot.config.QuarkusGitHubBotConfigFile.TriageRule;
import io.quarkus.bot.util.Labels;
import io.quarkus.bot.util.Repositories;
import io.quarkus.bot.util.Strings;
import io.quarkus.bot.util.Triage;
import io.smallrye.graphql.client.Response;
import io.smallrye.graphql.client.dynamic.api.DynamicGraphQLClient;

class TriageDiscussion {

    private static final Logger LOG = Logger.getLogger(TriageDiscussion.class);

    @Inject
    QuarkusGitHubBotConfig quarkusBotConfig;

    @Inject
    Repositories repositories;

    void triageDiscussion(@Discussion.Created @Discussion.CategoryChanged GHEventPayload.Discussion discussionPayload,
            @ConfigFile("quarkus-github-bot.yml") QuarkusGitHubBotConfigFile quarkusBotConfigFile,
            DynamicGraphQLClient gitHubGraphQLClient) throws IOException {
        if (!repositories.isMainRepository(discussionPayload.getRepository())) {
            return;
        }
        if (!Feature.TRIAGE_DISCUSSIONS.isEnabled(quarkusBotConfigFile)) {
            return;
        }

        if (quarkusBotConfigFile.triage.rules.isEmpty()) {
            return;
        }

        GHRepositoryDiscussion discussion = discussionPayload.getDiscussion();

        if (!quarkusBotConfigFile.triage.discussions.monitoredCategories.contains(discussion.getCategory().getId())) {
            if (quarkusBotConfigFile.triage.discussions.logCategories) {
                LOG.info("Discussion category " + discussion.getCategory().getId() + " - " + discussion.getCategory().getName()
                        + " is not monitored, ignoring discussion.");
            }
            return;
        }

        Set<String> labels = new TreeSet<>();
        Set<String> mentions = new TreeSet<>();
        List<String> comments = new ArrayList<>();

        for (TriageRule rule : quarkusBotConfigFile.triage.rules) {
            if (Triage.matchRuleFromDescription(discussion.getTitle(), discussion.getBody(), rule)) {
                if (!rule.labels.isEmpty()) {
                    labels.addAll(rule.labels);
                }
                if (!rule.notify.isEmpty()) {
                    for (String mention : rule.notify) {
                        if (!mention.equals(discussion.getUser().getLogin())) {
                            mentions.add(mention);
                        }
                    }
                }
                if (Strings.isNotBlank(rule.comment)) {
                    comments.add(rule.comment);
                }
            }
        }

        if (!labels.isEmpty()) {
            if (!quarkusBotConfig.isDryRun()) {
                addLabels(gitHubGraphQLClient, discussion, discussionPayload.getRepository(), Labels.limit(labels));
            } else {
                LOG.info("Discussion #" + discussion.getNumber() + " - Add labels: " + String.join(", ", Labels.limit(labels)));
            }
        }

        if (!mentions.isEmpty()) {
            comments.add("/cc @" + String.join(", @", mentions));
        }

        for (String comment : comments) {
            if (!quarkusBotConfig.isDryRun()) {
                addComment(gitHubGraphQLClient, discussion, comment);
            } else {
                LOG.info("Discussion #" + discussion.getNumber() + " - Add comment: " + comment);
            }
        }

        // TODO: we would need to get the labels via GraphQL. For now, let's see if we can avoid one more query.
        //        if (mentions.isEmpty() && !Labels.hasAreaLabels(labels) && !GHIssues.hasAreaLabel(issue)) {
        //            if (!quarkusBotConfig.isDryRun()) {
        //                issue.addLabels(Labels.TRIAGE_NEEDS_TRIAGE);
        //            } else {
        //                LOG.info("Discussion #" + discussion.getNumber() + " - Add label: " + Labels.TRIAGE_NEEDS_TRIAGE);
        //            }
        //        }
    }

    private static void addLabels(DynamicGraphQLClient gitHubGraphQLClient, GHRepositoryDiscussion discussion,
            GHRepository repository, Collection<String> labels) {
        // unfortunately, we need to get the ids of the labels
        Set<String> labelIds = new HashSet<>();
        for (String label : labels) {
            try {
                labelIds.add(repository.getLabel(label).getNodeId());
            } catch (IOException e) {
                LOG.error("Discussion #" + discussion.getNumber() + " - Unable to get id for label: " + label);
            }
        }

        if (labelIds.isEmpty()) {
            return;
        }

        try {
            Map<String, Object> variables = new HashMap<>();
            variables.put("labelableId", discussion.getNodeId());
            variables.put("labelIds", labelIds.toArray(new String[0]));

            Response response = gitHubGraphQLClient.executeSync("""
                    mutation AddLabels($labelableId: ID!, $labelIds: [ID!]!) {
                      addLabelsToLabelable(input: {
                        labelableId: $labelableId,
                        labelIds: $labelIds}) {
                            clientMutationId
                      }
                    }""", variables);

            if (response.hasError()) {
                LOG.info("Discussion #" + discussion.getNumber() + " - Unable to add labels: " + String.join(", ", labels)
                        + " - " + response.getErrors());
            }
        } catch (ExecutionException | InterruptedException e) {
            LOG.info("Discussion #" + discussion.getNumber() + " - Unable to add labels: " + String.join(", ", labels));
        }
    }

    private static void addComment(DynamicGraphQLClient gitHubGraphQLClient, GHRepositoryDiscussion discussion,
            String comment) {
        try {
            Map<String, Object> variables = new HashMap<>();
            variables.put("discussionId", discussion.getNodeId());
            variables.put("comment", comment);

            Response response = gitHubGraphQLClient.executeSync("""
                    mutation AddComment($discussionId: ID!, $comment: String!) {
                      addDiscussionComment(input: {
                        discussionId: $discussionId,
                        body: $comment }) {
                            clientMutationId
                      }
                    }""", variables);

            if (response.hasError()) {
                LOG.info("Discussion #" + discussion.getNumber() + " - Unable to add comment: " + comment
                        + " - " + response.getErrors());
            }
        } catch (ExecutionException | InterruptedException e) {
            LOG.info("Discussion #" + discussion.getNumber() + " - Unable to add comment: " + comment);
        }
    }
}
