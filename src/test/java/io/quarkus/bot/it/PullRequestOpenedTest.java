package io.quarkus.bot.it;

import static io.quarkiverse.githubapp.testing.GitHubAppTesting.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kohsuke.github.GHEvent;
import org.kohsuke.github.GHPullRequestFileDetail;
import org.kohsuke.github.PagedIterable;
import org.mockito.junit.jupiter.MockitoExtension;

import io.quarkiverse.githubapp.testing.GitHubAppTest;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@GitHubAppTest
@ExtendWith(MockitoExtension.class)
public class PullRequestOpenedTest {

    @Test
    void titleEndsWithDot() throws IOException {
        given().github(mocks -> mocks.configFile("quarkus-github-bot.yml").fromString("features: [ CHECK_EDITORIAL_RULES ]\n"))
                .when().payloadFromClasspath("/pullrequest-opened-title-ends-with-dot.json")
                .event(GHEvent.PULL_REQUEST)
                .then().github(mocks -> {
                    verify(mocks.pullRequest(527350930)).comment(
                            """
                                    Thanks for your pull request!

                                    Your pull request does not follow our editorial rules. Could you have a look?

                                    - title should not end up with dot

                                    <sub>This message is automatically generated by a bot.</sub>""");
                    verifyNoMoreInteractions(mocks.ghObjects());
                });
    }

    @Test
    void titleStartsWithLowercase() throws IOException {
        given().github(mocks -> mocks.configFile("quarkus-github-bot.yml").fromString("features: [ CHECK_EDITORIAL_RULES ]\n"))
                .when().payloadFromClasspath("/pullrequest-opened-title-starts-with-lowercase.json")
                .event(GHEvent.PULL_REQUEST)
                .then().github(mocks -> {
                    verify(mocks.pullRequest(527350930)).comment(
                            """
                                    Thanks for your pull request!

                                    Your pull request does not follow our editorial rules. Could you have a look?

                                    - title should preferably start with an uppercase character (if it makes sense!)

                                    <sub>This message is automatically generated by a bot.</sub>""");
                    verifyNoMoreInteractions(mocks.ghObjects());
                });
    }

    @Test
    void titleStartsWithgRPC() throws IOException {
        given().github(mocks -> mocks.configFile("quarkus-github-bot.yml").fromString("features: [ CHECK_EDITORIAL_RULES ]\n"))
                .when().payloadFromClasspath("/pullrequest-opened-title-starts-with-gRPC.json")
                .event(GHEvent.PULL_REQUEST)
                .then().github(mocks -> {
                    verifyNoMoreInteractions(mocks.ghObjects());
                });
    }

    @Test
    void titleContainsIssueNumber() throws IOException {
        given().github(mocks -> mocks.configFile("quarkus-github-bot.yml").fromString("features: [ CHECK_EDITORIAL_RULES ]\n"))
                .when().payloadFromClasspath("/pullrequest-opened-title-contains-issue-number.json")
                .event(GHEvent.PULL_REQUEST)
                .then().github(mocks -> {
                    verify(mocks.pullRequest(527350930)).comment(
                            """
                                    Thanks for your pull request!

                                    Your pull request does not follow our editorial rules. Could you have a look?

                                    - title should not contain an issue number (use `Fix #1234` in the description instead)

                                    <sub>This message is automatically generated by a bot.</sub>""");
                    verifyNoMoreInteractions(mocks.ghObjects());
                });
    }

    @Test
    void titleStartsWithFeat() throws IOException {
        given().github(mocks -> mocks.configFile("quarkus-github-bot.yml").fromString("features: [ CHECK_EDITORIAL_RULES ]\n"))
                .when().payloadFromClasspath("/pullrequest-opened-title-starts-with-feat.json")
                .event(GHEvent.PULL_REQUEST)
                .then().github(mocks -> {
                    verify(mocks.pullRequest(527350930)).comment(
                            """
                                    Thanks for your pull request!

                                    Your pull request does not follow our editorial rules. Could you have a look?

                                    - title should preferably start with an uppercase character (if it makes sense!)
                                    - title should not start with chore/docs/feat/fix/refactor but be a proper sentence

                                    <sub>This message is automatically generated by a bot.</sub>""");
                    verifyNoMoreInteractions(mocks.ghObjects());
                });
    }

    @Test
    void titleStartsWithFix() throws IOException {
        given().github(mocks -> mocks.configFile("quarkus-github-bot.yml").fromString("features: [ CHECK_EDITORIAL_RULES ]\n"))
                .when().payloadFromClasspath("/pullrequest-opened-title-starts-with-fix.json")
                .event(GHEvent.PULL_REQUEST)
                .then().github(mocks -> {
                    verify(mocks.pullRequest(527350930)).comment(
                            """
                                    Thanks for your pull request!

                                    Your pull request does not follow our editorial rules. Could you have a look?

                                    - title should preferably start with an uppercase character (if it makes sense!)
                                    - title should not start with chore/docs/feat/fix/refactor but be a proper sentence

                                    <sub>This message is automatically generated by a bot.</sub>""");
                    verifyNoMoreInteractions(mocks.ghObjects());
                });
    }

    @Test
    void titleStartsWithChore() throws IOException {
        given().github(mocks -> mocks.configFile("quarkus-github-bot.yml").fromString("features: [ CHECK_EDITORIAL_RULES ]\n"))
                .when().payloadFromClasspath("/pullrequest-opened-title-starts-with-chore.json")
                .event(GHEvent.PULL_REQUEST)
                .then().github(mocks -> {
                    verify(mocks.pullRequest(527350930)).comment(
                            """
                                    Thanks for your pull request!

                                    Your pull request does not follow our editorial rules. Could you have a look?

                                    - title should preferably start with an uppercase character (if it makes sense!)
                                    - title should not start with chore/docs/feat/fix/refactor but be a proper sentence

                                    <sub>This message is automatically generated by a bot.</sub>""");
                    verifyNoMoreInteractions(mocks.ghObjects());
                });
    }

    @Test
    void titleStartsWithDocs() throws IOException {
        given().github(mocks -> mocks.configFile("quarkus-github-bot.yml").fromString("features: [ CHECK_EDITORIAL_RULES ]\n"))
                .when().payloadFromClasspath("/pullrequest-opened-title-starts-with-docs.json")
                .event(GHEvent.PULL_REQUEST)
                .then().github(mocks -> {
                    verify(mocks.pullRequest(527350930)).comment(
                            """
                                    Thanks for your pull request!

                                    Your pull request does not follow our editorial rules. Could you have a look?

                                    - title should preferably start with an uppercase character (if it makes sense!)
                                    - title should not start with chore/docs/feat/fix/refactor but be a proper sentence

                                    <sub>This message is automatically generated by a bot.</sub>""");
                    verifyNoMoreInteractions(mocks.ghObjects());
                });
    }

    @Test
    void titleStartsWithMaintenanceBranch() throws IOException {
        given().github(mocks -> mocks.configFile("quarkus-github-bot.yml").fromString("features: [ CHECK_EDITORIAL_RULES ]\n"))
                .when().payloadFromClasspath("/pullrequest-opened-title-starts-with-maintenance-branch.json")
                .event(GHEvent.PULL_REQUEST)
                .then().github(mocks -> {
                    verifyNoMoreInteractions(mocks.ghObjects());
                });
    }

    @Test
    void titleStartsWithMaintenanceBranchParenthesis() throws IOException {
        given().github(mocks -> mocks.configFile("quarkus-github-bot.yml").fromString("features: [ CHECK_EDITORIAL_RULES ]\n"))
                .when().payloadFromClasspath("/pullrequest-opened-title-starts-with-maintenance-branch-parenthesis.json")
                .event(GHEvent.PULL_REQUEST)
                .then().github(mocks -> {
                    verify(mocks.pullRequest(527350930))
                            .setTitle("[3.8] Upgrade postgresql-jdbc to 42.2.18");
                    verifyNoMoreInteractions(mocks.ghObjects());
                });
    }

    @Test
    void titleStartsWithMaintenanceBranchForMain() throws IOException {
        given().github(mocks -> mocks.configFile("quarkus-github-bot.yml").fromString("features: [ CHECK_EDITORIAL_RULES ]\n"))
                .when().payloadFromClasspath("/pullrequest-opened-title-starts-with-maintenance-branch-for-main.json")
                .event(GHEvent.PULL_REQUEST)
                .then().github(mocks -> {
                    verify(mocks.pullRequest(527350930)).comment(
                            """
                                    Thanks for your pull request!

                                    Your pull request does not follow our editorial rules. Could you have a look?

                                    - title should preferably start with an uppercase character (if it makes sense!)

                                    <sub>This message is automatically generated by a bot.</sub>""");
                    verifyNoMoreInteractions(mocks.ghObjects());
                });
    }

    @Test
    void titleForMaintenanceBranchWithNoPrefix() throws IOException {
        given().github(mocks -> mocks.configFile("quarkus-github-bot.yml").fromString("features: [ CHECK_EDITORIAL_RULES ]\n"))
                .when().payloadFromClasspath("/pullrequest-opened-title-for-maintenance-branch-with-no-prefix.json")
                .event(GHEvent.PULL_REQUEST)
                .then().github(mocks -> {
                    verify(mocks.pullRequest(527350930))
                            .setTitle("[3.10] Upgrade postgresql-jdbc to 42.2.18");
                    verifyNoMoreInteractions(mocks.ghObjects());
                });
    }

    @Test
    void triageFromChangedFiles() throws IOException {
        given()
                .github(mocks -> {
                    mocks.configFile("quarkus-github-bot.yml")
                            .fromString("""
                                    features: [ TRIAGE_ISSUES_AND_PULL_REQUESTS ]
                                    triage:
                                      rules:
                                        - directories:
                                            - foo/*
                                            - bar/*
                                          labels: [area/test1, area/test2]
                                        - title: keyword
                                          directories:
                                            - foobar
                                          labels: [area/test3]""");
                    PagedIterable<GHPullRequestFileDetail> changedFilesMocks = MockHelper.mockPagedIterable(
                            MockHelper.mockGHPullRequestFileDetail("foo/Something.java"),
                            MockHelper.mockGHPullRequestFileDetail("something/foobar/SomethingElse.java"));
                    when(mocks.pullRequest(527350930).listFiles())
                            .thenReturn(changedFilesMocks);
                })
                .when().payloadFromClasspath("/pullrequest-opened-title-contains-keyword.json")
                .event(GHEvent.PULL_REQUEST)
                .then().github(mocks -> {
                    verify(mocks.pullRequest(527350930))
                            .addLabels("area/test1", "area/test2");
                    verifyNoMoreInteractions(mocks.ghObjects());
                });
    }

    @Test
    void triageFromDescription() throws IOException {
        given()
                .github(mocks -> {
                    mocks.configFile("quarkus-github-bot.yml")
                            .fromString("""
                                    features: [ TRIAGE_ISSUES_AND_PULL_REQUESTS ]
                                    triage:
                                      rules:
                                        - title: keyword
                                          directories:
                                            - foo/*
                                            - bar/*
                                          labels: [area/test1, area/test2]
                                        - title: somethingelse
                                          directories:
                                            - foobar
                                          labels: [area/test3]""");
                    PagedIterable<GHPullRequestFileDetail> changedFilesMock = MockHelper.mockPagedIterable(
                            MockHelper.mockGHPullRequestFileDetail("pom.xml"));
                    when(mocks.pullRequest(527350930).listFiles())
                            .thenReturn(changedFilesMock);
                })
                .when().payloadFromClasspath("/pullrequest-opened-title-contains-keyword.json")
                .event(GHEvent.PULL_REQUEST)
                .then().github(mocks -> {
                    verify(mocks.pullRequest(527350930))
                            .addLabels("area/test1", "area/test2");
                    verifyNoMoreInteractions(mocks.ghObjects());
                });
    }

    @Test
    void triageFromChangedFilesAndDescription() throws IOException {
        given()
                .github(mocks -> {
                    mocks.configFile("quarkus-github-bot.yml")
                            .fromString("""
                                    features: [ TRIAGE_ISSUES_AND_PULL_REQUESTS ]
                                    triage:
                                      rules:
                                        - title: keyword
                                          directories:
                                            - foo/*
                                            - bar/*
                                          labels: [area/test1, area/test2]
                                        - title: somethingelse
                                          directories:
                                            - foobar
                                          labels: [area/test3]
                                          allowSecondPass: true""");
                    PagedIterable<GHPullRequestFileDetail> changedFilesMocks = MockHelper.mockPagedIterable(
                            MockHelper.mockGHPullRequestFileDetail("foobar/pom.xml"));
                    when(mocks.pullRequest(527350930).listFiles())
                            .thenReturn(changedFilesMocks);
                })
                .when().payloadFromClasspath("/pullrequest-opened-title-contains-keyword.json")
                .event(GHEvent.PULL_REQUEST)
                .then().github(mocks -> {
                    verify(mocks.pullRequest(527350930))
                            .addLabels("area/test1", "area/test2", "area/test3");
                    verifyNoMoreInteractions(mocks.ghObjects());
                });
    }

    @Test
    void triageComment() throws IOException {
        given()
                .github(mocks -> {
                    mocks.configFile("quarkus-github-bot.yml")
                            .fromString("""
                                    features: [ TRIAGE_ISSUES_AND_PULL_REQUESTS ]
                                    triage:
                                      rules:
                                        - directories:
                                            - foo
                                            - bar
                                          comment: 'This is an urgent PR'""");
                    PagedIterable<GHPullRequestFileDetail> changedFilesMocks = MockHelper.mockPagedIterable(
                            MockHelper.mockGHPullRequestFileDetail("foo/Something.java"));
                    when(mocks.pullRequest(527350930).listFiles())
                            .thenReturn(changedFilesMocks);
                })
                .when().payloadFromClasspath("/pullrequest-opened-title-does-not-contain-keyword.json")
                .event(GHEvent.PULL_REQUEST)
                .then().github(mocks -> {
                    verify(mocks.pullRequest(527350930))
                            .comment("This is an urgent PR");
                    verifyNoMoreInteractions(mocks.ghObjects());
                });
    }

    @Test
    void triageBasicNotify() throws IOException {
        given().github(mocks -> mocks.configFile("quarkus-github-bot.yml")
                .fromString("features: [ TRIAGE_ISSUES_AND_PULL_REQUESTS ]\n"
                        + "triage:\n"
                        + "  rules:\n"
                        + "    - title: test\n"
                        + "      notify: [prodsec]\n"
                        + "      comment: 'This is a security issue'\n"
                        + "      notifyInPullRequest: true"))
                .when().payloadFromClasspath("/pullrequest-opened-title-contains-test.json")
                .event(GHEvent.PULL_REQUEST)
                .then().github(mocks -> {
                    verify(mocks.pullRequest(527350930))
                            .comment("/cc @prodsec");
                    verify(mocks.pullRequest(527350930))
                            .comment("This is a security issue");
                    verifyNoMoreInteractions(mocks.ghObjects());
                });
    }

    @Test
    void triageIdNotify() throws IOException {
        given().github(mocks -> mocks.configFile("quarkus-github-bot.yml")
                .fromString("features: [ TRIAGE_ISSUES_AND_PULL_REQUESTS ]\n"
                        + "triage:\n"
                        + "  rules:\n"
                        + "    - id: 'security'\n"
                        + "      title: test\n"
                        + "      notify: [prodsec,max]\n"
                        + "      comment: 'This is a security issue'\n"
                        + "      notifyInPullRequest: true\n"
                        + "    - id: 'devtools'\n"
                        + "      title: test\n"
                        + "      notify: [max]\n"
                        + "      notifyInPullRequest: true"))
                .when().payloadFromClasspath("/pullrequest-opened-title-contains-test.json")
                .event(GHEvent.PULL_REQUEST)
                .then().github(mocks -> {
                    verify(mocks.pullRequest(527350930))
                            .comment("This is a security issue");
                    verify(mocks.pullRequest(527350930))
                            .comment("/cc @max (devtools,security), @prodsec (security)");
                    verifyNoMoreInteractions(mocks.ghObjects());
                });
    }

    @Test
    void descriptionMissingForSmallDocChange() throws IOException {
        given()
                .github(mocks -> {
                    mocks.configFile("quarkus-github-bot.yml")
                            .fromString("features: [ CHECK_EDITORIAL_RULES ]");
                    PagedIterable<GHPullRequestFileDetail> changedFilesMocks = MockHelper.mockPagedIterable(
                            MockHelper.mockGHPullRequestFileDetail("docs/src/main/asciidoc/doc-change1"),
                            MockHelper.mockGHPullRequestFileDetail("docs/src/main/asciidoc/doc-change2"));
                    when(mocks.pullRequest(527350930).listFiles())
                            .thenReturn(changedFilesMocks);
                })
                .when().payloadFromClasspath("/pullrequest-opened-description-doc-missing-small-patch.json")
                .event(GHEvent.PULL_REQUEST)
                .then().github(mocks -> {
                    verifyNoMoreInteractions(mocks.ghObjects());
                });
    }

    @Test
    void descriptionMissingForLargeDocChange() throws IOException {
        given()
                .github(mocks -> {
                    mocks.configFile("quarkus-github-bot.yml")
                            .fromString("features: [ CHECK_EDITORIAL_RULES ]");
                    PagedIterable<GHPullRequestFileDetail> changedFilesMocks = MockHelper.mockPagedIterable(
                            MockHelper.mockGHPullRequestFileDetail("docs/src/main/asciidoc/doc-change1"),
                            MockHelper.mockGHPullRequestFileDetail("docs/src/main/asciidoc/doc-change2"));
                    when(mocks.pullRequest(527350930).listFiles())
                            .thenReturn(changedFilesMocks);
                })
                .when().payloadFromClasspath("/pullrequest-opened-description-doc-missing-large-patch.json")
                .event(GHEvent.PULL_REQUEST)
                .then().github(mocks -> {
                    verify(mocks.pullRequest(527350930)).comment(
                            """
                                    Thanks for your pull request!

                                    Your pull request does not follow our editorial rules. Could you have a look?

                                    - description should not be empty, describe your intent or provide links to the issues this PR is fixing (using `Fixes #NNNNN`) or changelogs

                                    <sub>This message is automatically generated by a bot.</sub>""");
                    verifyNoMoreInteractions(mocks.ghObjects());
                });
    }

    @Test
    void descriptionNotMissingForLargeDocChange() throws IOException {
        given()
                .github(mocks -> {
                    mocks.configFile("quarkus-github-bot.yml")
                            .fromString("features: [ CHECK_EDITORIAL_RULES ]");
                })
                .when().payloadFromClasspath("/pullrequest-opened-description-doc-not-missing.json")
                .event(GHEvent.PULL_REQUEST)
                .then().github(mocks -> {
                    verifyNoMoreInteractions(mocks.ghObjects());
                });
    }

    @Test
    void descriptionMissingForMultipleCommits() throws IOException {
        given()
                .github(mocks -> {
                    mocks.configFile("quarkus-github-bot.yml")
                            .fromString("features: [ CHECK_EDITORIAL_RULES ]");
                })
                .when().payloadFromClasspath("/pullrequest-opened-description-doc-missing-multiple-commits.json")
                .event(GHEvent.PULL_REQUEST)
                .then().github(mocks -> {
                    verify(mocks.pullRequest(527350930)).comment(
                            """
                                    Thanks for your pull request!

                                    Your pull request does not follow our editorial rules. Could you have a look?

                                    - description should not be empty, describe your intent or provide links to the issues this PR is fixing (using `Fixes #NNNNN`) or changelogs

                                    <sub>This message is automatically generated by a bot.</sub>""");
                    verifyNoMoreInteractions(mocks.ghObjects());
                });
    }

    @Test
    void descriptionMissingForSmallNonDocChange() throws IOException {
        given()
                .github(mocks -> {
                    mocks.configFile("quarkus-github-bot.yml")
                            .fromString("features: [ CHECK_EDITORIAL_RULES ]");
                    PagedIterable<GHPullRequestFileDetail> changedFilesMocks = MockHelper.mockPagedIterable(
                            MockHelper.mockGHPullRequestFileDetail("extensions/hibernate-validation/pom.xml"));
                    when(mocks.pullRequest(527350930).listFiles())
                            .thenReturn(changedFilesMocks);
                })
                .when().payloadFromClasspath("/pullrequest-opened-description-non-doc-small-patch.json")
                .event(GHEvent.PULL_REQUEST)
                .then().github(mocks -> {
                    verifyNoMoreInteractions(mocks.ghObjects());
                });
    }

    @Test
    void descriptionMissingForMediumNonDocChange() throws IOException {
        given()
                .github(mocks -> {
                    mocks.configFile("quarkus-github-bot.yml")
                            .fromString("features: [ CHECK_EDITORIAL_RULES ]");
                    PagedIterable<GHPullRequestFileDetail> changedFilesMocks = MockHelper.mockPagedIterable(
                            MockHelper.mockGHPullRequestFileDetail("nondoc"),
                            MockHelper.mockGHPullRequestFileDetail("docs/src/main/asciidoc/doc-change2"));
                    when(mocks.pullRequest(527350930).listFiles())
                            .thenReturn(changedFilesMocks);
                })
                .when().payloadFromClasspath("/pullrequest-opened-description-non-doc-medium-patch.json")
                .event(GHEvent.PULL_REQUEST)
                .then().github(mocks -> {
                    verify(mocks.pullRequest(527350930)).comment(
                            """
                                    Thanks for your pull request!

                                    Your pull request does not follow our editorial rules. Could you have a look?

                                    - description should not be empty, describe your intent or provide links to the issues this PR is fixing (using `Fixes #NNNNN`) or changelogs

                                    <sub>This message is automatically generated by a bot.</sub>""");
                    verifyNoMoreInteractions(mocks.ghObjects());
                });
    }

    @Test
    void descriptionMissingForLargeNonDocChange() throws IOException {
        given()
                .github(mocks -> {
                    mocks.configFile("quarkus-github-bot.yml")
                            .fromString("features: [ CHECK_EDITORIAL_RULES ]");
                    PagedIterable<GHPullRequestFileDetail> changedFilesMocks = MockHelper.mockPagedIterable(
                            MockHelper.mockGHPullRequestFileDetail("nondoc"),
                            MockHelper.mockGHPullRequestFileDetail("docs/src/main/asciidoc/doc-change2"));
                    when(mocks.pullRequest(527350930).listFiles())
                            .thenReturn(changedFilesMocks);
                })
                .when().payloadFromClasspath("/pullrequest-opened-description-non-doc-large-patch.json")
                .event(GHEvent.PULL_REQUEST)
                .then().github(mocks -> {
                    verify(mocks.pullRequest(527350930)).comment(
                            """
                                    Thanks for your pull request!

                                    Your pull request does not follow our editorial rules. Could you have a look?

                                    - description should not be empty, describe your intent or provide links to the issues this PR is fixing (using `Fixes #NNNNN`) or changelogs

                                    <sub>This message is automatically generated by a bot.</sub>""");
                    verifyNoMoreInteractions(mocks.ghObjects());
                });
    }

    @Test
    void descriptionMissingForBOM() throws IOException {
        given()
                .github(mocks -> {
                    mocks.configFile("quarkus-github-bot.yml")
                            .fromString("features: [ CHECK_EDITORIAL_RULES ]");
                    PagedIterable<GHPullRequestFileDetail> changedFilesMocks = MockHelper.mockPagedIterable(
                            MockHelper.mockGHPullRequestFileDetail("nonbom"),
                            MockHelper.mockGHPullRequestFileDetail("bom/application/pom.xml"));
                    when(mocks.pullRequest(527350930).listFiles())
                            .thenReturn(changedFilesMocks);
                })
                .when().payloadFromClasspath("/pullrequest-opened-description-missing-bom.json")
                .event(GHEvent.PULL_REQUEST)
                .then().github(mocks -> {
                    verify(mocks.pullRequest(527350930)).comment(
                            """
                                    Thanks for your pull request!

                                    Your pull request does not follow our editorial rules. Could you have a look?

                                    - description should not be empty, describe your intent or provide links to the issues this PR is fixing (using `Fixes #NNNNN`) or changelogs

                                    <sub>This message is automatically generated by a bot.</sub>""");
                    verifyNoMoreInteractions(mocks.ghObjects());
                });
    }
}
