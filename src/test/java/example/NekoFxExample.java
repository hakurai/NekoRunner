package example;

import net.hogedriven.junit.nekorunner.NekoRunnerFx;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

@RunWith(NekoRunnerFx.class)
public class NekoFxExample {

    @Test
    public void test_neko_run() throws Exception {
        TimeUnit.SECONDS.sleep(5L);
    }
}
