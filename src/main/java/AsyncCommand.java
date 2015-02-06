import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AsyncCommand extends HystrixCommand<String> {

    private final String name;

    public AsyncCommand(final String name) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionIsolationThreadTimeoutInMilliseconds(10000)));

        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        Thread.sleep(5000L);

        return name;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Date start = new Date();

        Future<String> first = new AsyncCommand("First").queue();
        Future<String> second = new AsyncCommand("Second").queue();

        System.out.println(second.get());
        System.out.println(first.get());

        Date end = new Date();
        System.out.println(end.getTime() - start.getTime());
    }
}
