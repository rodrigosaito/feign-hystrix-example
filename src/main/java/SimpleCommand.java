import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class SimpleCommand extends HystrixCommand<String> {

    protected SimpleCommand() {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
    }

    @Override
    protected String run() throws Exception {
        return "Hello World";
    }

    public static void main(String[] args) {
        System.out.println(new SimpleCommand().execute());
    }
}
