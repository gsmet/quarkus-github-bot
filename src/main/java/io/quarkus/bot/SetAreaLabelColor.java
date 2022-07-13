package io.quarkus.bot;

import java.io.IOException;
import java.util.Locale;

import javax.inject.Inject;

import org.jboss.logging.Logger;
import org.kohsuke.github.GHEventPayload;
import org.kohsuke.github.GHLabel;

import io.quarkiverse.githubapp.ConfigFile;
import io.quarkiverse.githubapp.event.Label;
import io.quarkus.bot.config.Feature;
import io.quarkus.bot.config.QuarkusGitHubBotConfig;
import io.quarkus.bot.config.QuarkusGitHubBotConfigFile;
import io.quarkus.bot.util.Labels;
import io.quarkus.bot.util.Repositories;

public class SetAreaLabelColor {

    private static final Logger LOG = Logger.getLogger(SetAreaLabelColor.class);

    private static final String AREA_LABEL_COLOR = "0366d6";

    @Inject
    QuarkusGitHubBotConfig quarkusBotConfig;

    @Inject
    Repositories repositories;

    void setAreaLabelColor(@Label.Created GHEventPayload.Label labelPayload,
            @ConfigFile("quarkus-github-bot.yml") QuarkusGitHubBotConfigFile quarkusBotConfigFile) throws IOException {
        if (!repositories.isMainRepository(labelPayload.getRepository())) {
            return;
        }
        if (!Feature.SET_AREA_LABEL_COLOR.isEnabled(quarkusBotConfigFile)) {
            return;
        }

        GHLabel label = labelPayload.getLabel();

        if (!label.getName().startsWith(Labels.AREA_PREFIX)
                || AREA_LABEL_COLOR.equals(label.getColor().toLowerCase(Locale.ROOT))) {
            return;
        }

        if (!quarkusBotConfig.isDryRun()) {
            label.set().color(AREA_LABEL_COLOR);
        } else {
            LOG.info("Label " + label.getName() + " - Set color: #" + AREA_LABEL_COLOR);
        }
    }
}
