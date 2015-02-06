import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class SimpleFailureCommand extends HystrixCommand<String> {

    public SimpleFailureCommand() {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
    }

    @Override
    protected String run() throws Exception {
        throw new RuntimeException("this command always fails");
    }

    @Override
    protected String getFallback() {
        return "Hello failure";
    }

    public static void main(String[] args) {
        System.out.println(new SimpleFailureCommand().execute());
    }
}
