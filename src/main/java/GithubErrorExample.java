import feign.Feign;
import feign.Logger;
import feign.RequestLine;
import feign.Response;
import feign.gson.GsonDecoder;

import java.util.List;

/**
 * adapted from {@code com.example.retrofit.GitHubClient}
 */
public class GithubErrorExample {

    public static void main(String... args) throws InterruptedException {
        Apiary apiary = Feign.builder()
                .decoder(new GsonDecoder())
                .logger(new Logger.ErrorLogger())
                .logLevel(Logger.Level.BASIC)
                .errorDecoder(new ErrorDecoder())
                .target(Apiary.class, "https://private-fae5-apresentacaonetflixtools.apiary-mock.com");


        double random = Math.random();

        if (random < 0.5) {
            List<Contributor> contributors = apiary.notFound();
        } else {
            List<Contributor> contributors = apiary.error();
        }

    }

    interface Apiary {
        @RequestLine("GET /NAO_ENCONTREI")
        List<Contributor> notFound();

        @RequestLine("GET /v2/multiorders")
        List<Contributor> error();
    }

    static class Contributor {

        String login;
        int contributions;
    }

    static class ErrorDecoder implements feign.codec.ErrorDecoder{

        @Override
        public Exception decode(String methodKey, Response response) {

            if (response.status() == 400) {
                return new DeuMerdaException(response.reason());
            }

            if (response.status() == 404) {
                return new NaoAcheiException(response.reason());
            }

            return null;
        }
    }

    static class DeuMerdaException extends RuntimeException {
        public DeuMerdaException(String message) {
            super(message);
        }

    }

    static class NaoAcheiException extends RuntimeException {
        public NaoAcheiException(String message) {
            super(message);
        }
    }
}