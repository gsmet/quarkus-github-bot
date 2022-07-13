package io.quarkus.bot.config;

import java.util.Optional;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "quarkus-github-bot")
public interface QuarkusGitHubBotConfig {

    String MAIN_REPOSITORY_FULL_NAME = "quarkusio/quarkus";

    Optional<String> mainRepository();

    Optional<Boolean> dryRun();

    public default String getMainRepository() {
        return mainRepository().orElse(MAIN_REPOSITORY_FULL_NAME);
    }

    public default boolean isDryRun() {
        Optional<Boolean> dryRun = dryRun();
        return dryRun.isPresent() && dryRun.get();
    }
}
