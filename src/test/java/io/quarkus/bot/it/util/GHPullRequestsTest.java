package io.quarkus.bot.it.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import io.quarkus.bot.util.GHPullRequests;

public class GHPullRequestsTest {
    public static final String FEATURES_ADD_BRANCH_TO_PULL_REQUEST_TITLE = "features: [ ADD_BRANCH_TO_PULL_REQUEST_TITLE ]\n";
    public static final String FEATURES_CHECK_EDITORIAL_RULES = "features: [ CHECK_EDITORIAL_RULES ]\n";

    private static final String DEFAULT_BRANCH = "main";

    @Test
    public void testDropBranchPrefixVersionBranch() {
        assertThat(GHPullRequests.dropBranchPrefix("My PR", "3.8", DEFAULT_BRANCH)).isEqualTo(("My PR"));
        assertThat(GHPullRequests.dropBranchPrefix("My PR", "main", DEFAULT_BRANCH)).isEqualTo(("My PR"));
        assertThat(GHPullRequests.dropBranchPrefix("(3.8) My PR", "main", DEFAULT_BRANCH)).isEqualTo(("(3.8) My PR"));
        assertThat(GHPullRequests.dropBranchPrefix("[3.8] My PR", "main", DEFAULT_BRANCH)).isEqualTo(("[3.8] My PR"));
        assertThat(GHPullRequests.dropBranchPrefix("[3.8] My PR", "3.8", DEFAULT_BRANCH)).isEqualTo(("My PR"));
        assertThat(GHPullRequests.dropBranchPrefix("[3.9] My PR", "3.8", DEFAULT_BRANCH)).isEqualTo(("My PR"));
        assertThat(GHPullRequests.dropBranchPrefix("(3.9) My PR", "3.8", DEFAULT_BRANCH)).isEqualTo(("My PR"));
        assertThat(GHPullRequests.dropBranchPrefix("My PR [3.7]", "3.8", DEFAULT_BRANCH)).isEqualTo(("My PR [3.7]"));
        assertThat(GHPullRequests.dropBranchPrefix("3.10.4 Backports 1", "3.10", DEFAULT_BRANCH))
                .isEqualTo(("3.10.4 Backports 1"));
        assertThat(GHPullRequests.dropBranchPrefix("(3.10) My PR", "3.10", DEFAULT_BRANCH)).isEqualTo(("My PR"));
        assertThat(GHPullRequests.dropBranchPrefix("[3.10] My PR", "3.10", DEFAULT_BRANCH)).isEqualTo(("My PR"));
    }

    @Test
    public void testDropBranchPrefixNonVersionBranch() {
        assertThat(GHPullRequests.dropBranchPrefix("My PR", "3.x", DEFAULT_BRANCH)).isEqualTo("My PR");
        assertThat(GHPullRequests.dropBranchPrefix("[3.x] My PR", "3.x", DEFAULT_BRANCH)).isEqualTo("My PR");
        assertThat(GHPullRequests.dropBranchPrefix("[other] My PR", "3.x", DEFAULT_BRANCH)).isEqualTo("[other] My PR");
        assertThat(GHPullRequests.dropBranchPrefix("[3.x]My PR", "3.x", DEFAULT_BRANCH)).isEqualTo("My PR");
        assertThat(GHPullRequests.dropBranchPrefix("[3.x]  My PR", "3.x", DEFAULT_BRANCH)).isEqualTo("My PR");
    }

    @Test
    public void testNormalizeTitleVersionBranch() {
        assertThat(GHPullRequests.normalizeTitle("My PR", "3.8", DEFAULT_BRANCH)).isEqualTo(("[3.8] My PR"));
        assertThat(GHPullRequests.normalizeTitle("My PR", "3.10", DEFAULT_BRANCH)).isEqualTo(("[3.10] My PR"));
        assertThat(GHPullRequests.normalizeTitle("My PR", "main", DEFAULT_BRANCH)).isEqualTo(("My PR"));
        assertThat(GHPullRequests.normalizeTitle("(3.8) My PR", "main", DEFAULT_BRANCH)).isEqualTo(("(3.8) My PR"));
        assertThat(GHPullRequests.normalizeTitle("3.8.4 backports 1", "3.8", DEFAULT_BRANCH))
                .isEqualTo(("[3.8] 3.8.4 backports 1"));
        assertThat(GHPullRequests.normalizeTitle("(3.10) My PR", "3.10", DEFAULT_BRANCH)).isEqualTo(("[3.10] My PR"));
        assertThat(GHPullRequests.normalizeTitle("[3.8] My PR", "main", DEFAULT_BRANCH)).isEqualTo(("[3.8] My PR"));
        assertThat(GHPullRequests.normalizeTitle("[3.8] My PR", "3.8", DEFAULT_BRANCH)).isEqualTo(("[3.8] My PR"));
        assertThat(GHPullRequests.normalizeTitle("[3.9] My PR", "3.8", DEFAULT_BRANCH)).isEqualTo(("[3.8] My PR"));
        assertThat(GHPullRequests.normalizeTitle("(3.9) My PR", "3.8", DEFAULT_BRANCH)).isEqualTo(("[3.8] My PR"));
        assertThat(GHPullRequests.normalizeTitle("My PR [3.7]", "3.8", DEFAULT_BRANCH)).isEqualTo(("[3.8] My PR [3.7]"));
        assertThat(GHPullRequests.normalizeTitle("2.10 - My PR", "2.10", DEFAULT_BRANCH)).isEqualTo(("[2.10] My PR"));
        assertThat(GHPullRequests.normalizeTitle("3.10.4 Backports 1", "3.10", DEFAULT_BRANCH))
                .isEqualTo(("[3.10] 3.10.4 Backports 1"));
    }

    @Test
    public void testNormalizeTitleNonVersionBranch() {
        assertThat(GHPullRequests.normalizeTitle("My PR", "3.x", DEFAULT_BRANCH)).isEqualTo("[3.x] My PR");
        assertThat(GHPullRequests.normalizeTitle("[3.x] My PR", "3.x", DEFAULT_BRANCH)).isEqualTo("[3.x] My PR");
        assertThat(GHPullRequests.normalizeTitle("[other] My PR", "3.x", DEFAULT_BRANCH)).isEqualTo("[3.x] [other] My PR");
        assertThat(GHPullRequests.normalizeTitle("My PR", "feature-branch", DEFAULT_BRANCH))
                .isEqualTo("[feature-branch] My PR");
        assertThat(GHPullRequests.normalizeTitle("[feature-branch] My PR", "feature-branch", DEFAULT_BRANCH))
                .isEqualTo("[feature-branch] My PR");
    }
}
