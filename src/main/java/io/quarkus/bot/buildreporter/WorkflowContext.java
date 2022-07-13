package io.quarkus.bot.buildreporter;

import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GHPullRequest;
import org.kohsuke.github.GHRepository;

public class WorkflowContext {

    public final String repository;
    public final String type;
    public final String logContext;
    public final String htmlUrl;

    private WorkflowContext(String repository, String type, String logContext, String htmlUrl) {
        this.repository = repository;
        this.type = type;
        this.logContext = this.type + " #" + logContext;
        this.htmlUrl = htmlUrl;
    }

    public static WorkflowContext issue(GHIssue issue) {
        return new WorkflowContext(issue.getRepository().getFullName(),
                "Issue",
                String.valueOf(issue.getNumber()),
                issue.getHtmlUrl().toString());
    }

    public static WorkflowContext pullRequest(GHPullRequest pullRequest) {
        return new WorkflowContext(pullRequest.getRepository().getFullName(),
                "Pull request",
                String.valueOf(pullRequest.getNumber()),
                pullRequest.getHtmlUrl().toString());
    }

    public static WorkflowContext fork(GHRepository fork) {
        return new WorkflowContext(fork.getFullName(),
                "Fork",
                fork.getFullName(),
                fork.getHtmlUrl().toString());
    }

    public String getRepository() {
        return repository;
    }

    public String getType() {
        return type;
    }

    public String getLogContext() {
        return logContext;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }
}
