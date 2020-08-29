import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

public class HelloWorldTest {

    @Rule
    public final SystemOutRule outRule = new SystemOutRule().enableLog();

    @Test
    public void emptyParams() {
        HelloWorld.main(new String[0]);
        Assert.assertEquals("No input parameters provided\n", outRule.getLog());
    }

    @Test
    public void oneParam() {
        HelloWorld.main(new String[] {"testParam"});
        Assert.assertEquals("testParam\n", outRule.getLog());
    }

    @Test
    public void fourParams() {
        HelloWorld.main(new String[] {"one", "two", "three", "four"});
        Assert.assertEquals("one\ntwo\nthree\nfour\n", outRule.getLog());
    }
}

