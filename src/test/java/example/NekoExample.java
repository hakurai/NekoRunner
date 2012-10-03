package example;

import java.util.concurrent.TimeUnit;

import net.hogedriven.junit.nekorunner.NekoRunner;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(NekoRunner.class)
public class NekoExample {

    @Test
    public void test_neko_run() throws Exception {
        TimeUnit.SECONDS.sleep(5L);
    }
}
