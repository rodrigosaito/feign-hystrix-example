import java.util.List;

import feign.Feign;
import feign.Logger;
import feign.Param;
import feign.RequestLine;
import feign.gson.GsonDecoder;

/**
 * adapted from {@code com.example.retrofit.GitHubClient}
 */
public class GithubExample {

    public static void main(String... args) throws InterruptedException {
        GitHub github = Feign.builder()
                .decoder(new GsonDecoder())
                .logger(new Logger.ErrorLogger())
                .logLevel(Logger.Level.BASIC)
                .target(GitHub.class, "https://api.github.com");

        System.out.println("Let's fetch and print a list of the contributors to this library.");
        List<Contributor> contributors = github.contributors("netflix", "feign");
        for (Contributor contributor : contributors) {
            System.out.println(contributor.login + " (" + contributor.contributions + ")");
        }
    }

    interface GitHub {

        @RequestLine("GET /repos/{owner}/{repo}/contributors")
        List<Contributor> contributors(@Param("owner") String owner, @Param("repo") String repo);
    }

    static class Contributor {

        String login;
        int contributions;
    }
}