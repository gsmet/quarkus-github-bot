package io.quarkus.bot;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.jboss.logging.Logger;
import org.kohsuke.github.GHEventPayload;
import org.kohsuke.github.GHPullRequest;
import org.kohsuke.github.GHWorkflowRun;

import io.quarkiverse.githubapp.ConfigFile;
import io.quarkiverse.githubapp.event.PullRequest;
import io.quarkus.bot.config.Feature;
import io.quarkus.bot.config.QuarkusGitHubBotConfig;
import io.quarkus.bot.config.QuarkusGitHubBotConfigFile;
import io.quarkus.bot.util.Repositories;
import io.quarkus.bot.workflow.QuarkusWorkflowConstants;

class CancelWorkflowOnClosedPullRequest {
    private static final Logger LOG = Logger.getLogger(CancelWorkflowOnClosedPullRequest.class);

    @Inject
    QuarkusGitHubBotConfig quarkusBotConfig;

    @Inject
    Repositories repositories;

    public void onClose(@PullRequest.Closed GHEventPayload.PullRequest pullRequestPayload,
            @ConfigFile("quarkus-github-bot.yml") QuarkusGitHubBotConfigFile quarkusBotConfigFile) throws IOException {
        if (!repositories.isMainRepository(pullRequestPayload.getRepository())) {
            return;
        }
        if (!Feature.QUARKUS_REPOSITORY_WORKFLOW.isEnabled(quarkusBotConfigFile)) {
            return;
        }

        GHPullRequest pullRequest = pullRequestPayload.getPullRequest();

        List<GHWorkflowRun> ghWorkflowRuns = pullRequest.getRepository()
                .queryWorkflowRuns()
                .branch(pullRequest.getHead().getRef())
                .list()
                .toList()
                .stream()
                .filter(workflowRun -> workflowRun.getHeadRepository().getOwnerName()
                        .equals(pullRequest.getHead().getRepository().getOwnerName()))
                .filter(workflowRun -> QuarkusWorkflowConstants.QUARKUS_CI_WORKFLOW_NAME.equals(workflowRun.getName()) ||
                        QuarkusWorkflowConstants.QUARKUS_DOCUMENTATION_CI_WORKFLOW_NAME.equals(workflowRun.getName()))
                .filter(workflowRun -> workflowRun.getStatus() == GHWorkflowRun.Status.QUEUED
                        || workflowRun.getStatus() == GHWorkflowRun.Status.IN_PROGRESS)
                .collect(Collectors.toList());

        for (GHWorkflowRun workflowRun : ghWorkflowRuns) {
            if (!quarkusBotConfig.isDryRun()) {
                workflowRun.cancel();
            } else {
                LOG.info("Workflow run #" + workflowRun.getId() + " - Cancelling as pull request #" + pullRequest.getNumber()
                        + " is now closed");
            }
        }
    }
}
