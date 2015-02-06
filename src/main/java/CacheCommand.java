import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

import java.util.Date;
import java.util.UUID;

public class CacheCommand extends HystrixCommand<String> {

    private final String name;

    public CacheCommand(final String name) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionIsolationThreadTimeoutInMilliseconds(10000)));

        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        Thread.sleep(5000);

        return name;
    }

    @Override
    protected String getCacheKey() {
//        return name;
        return UUID.randomUUID().toString();
    }

    public static void main(String[] args) {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();

        try {
            Date start = new Date();

            String first = new CacheCommand("first").execute();
            System.out.println(first);

            String firstCached = new CacheCommand("first").execute();
            System.out.println(firstCached);

            Date end = new Date();
            System.out.println(end.getTime() - start.getTime());
        } finally {
            context.shutdown();
        }

    }
}
