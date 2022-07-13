package io.quarkus.bot.util;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.kohsuke.github.GHRepository;

import io.quarkus.bot.config.QuarkusGitHubBotConfig;

@Singleton
public class Repositories {

    @Inject
    QuarkusGitHubBotConfig quarkusGitHubBotConfig;

    public boolean isMainRepository(GHRepository repository) {
        if (repository == null) {
            return false;
        }

        return quarkusGitHubBotConfig.getMainRepository().equals(repository.getFullName());
    }

    public boolean isForkOfMainRepository(GHRepository repository) throws IOException {
        if (repository == null) {
            return false;
        }

        if (!repository.isFork()) {
            return false;
        }

        return isMainRepository(repository.getSource());
    }
}
