package io.quarkus.bot.util;

import java.util.regex.Pattern;

import org.kohsuke.github.GHLabel;
import org.kohsuke.github.GHPullRequest;

public final class GHPullRequests {

    private static final Pattern CLEAN_VERSION_PATTERN = Pattern.compile("^\\[?\\(?[0-9]+\\.[0-9]+\\]?\\)?(?![\\.0-9])[ -]*");

    public static boolean hasLabel(GHPullRequest pullRequest, String labelName) {
        for (GHLabel label : pullRequest.getLabels()) {
            if (labelName.equals(label.getName())) {
                return true;
            }
        }
        return false;
    }

    public static String dropBranchPrefix(String title, String branch, String defaultBranch) {
        if (title == null || title.isBlank()) {
            return title;
        }
        if (isDefaultBranch(branch, defaultBranch)) {
            return title;
        }

        if (Branches.isVersionBranch(branch)) {
            return CLEAN_VERSION_PATTERN.matcher(title).replaceFirst("");
        }

        String exactPrefix = "[" + branch + "]";
        String stripped = title.stripLeading();
        if (stripped.startsWith(exactPrefix)) {
            return stripped.substring(exactPrefix.length()).stripLeading();
        }
        return title;
    }

    public static String normalizeTitle(String title, String branch, String defaultBranch) {
        if (title == null || title.isBlank()) {
            return title;
        }
        if (isDefaultBranch(branch, defaultBranch)) {
            return title;
        }

        return "[" + branch + "] " + dropBranchPrefix(title, branch, defaultBranch);
    }

    private static boolean isDefaultBranch(String branch, String defaultBranch) {
        if (branch == null || branch.isBlank()) {
            return true;
        }
        return branch.equals(defaultBranch);
    }
}
