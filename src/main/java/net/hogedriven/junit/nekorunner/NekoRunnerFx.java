package net.hogedriven.junit.nekorunner;

import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.SwingUtilities;

import javafx.application.Application;
import javafx.application.Platform;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

/**
 *
 * @author hakurai
 */
public class NekoRunnerFx extends Runner {

    private static final AtomicInteger testCount = new AtomicInteger(0);
    private final Runner runner;
    private static boolean initialized;

    public NekoRunnerFx(Class<?> klass) throws InitializationError {
        runner = new BlockJUnit4ClassRunner(klass);
        testCount.incrementAndGet();
    }

    @Override
    public void run(RunNotifier notifier) {
        synchronized (this) {
            if (initialized == false) {
                initialized = true;
                new Thread(){
                    @Override
                    public void run() {
                        Application.launch(NekoFrame.class);
                    }
                }.start();
            }
        }

        runner.run(notifier);
        if (testCount.decrementAndGet() == 0) {
            Platform.exit();
        }


    }

    @Override
    public Description getDescription() {
        return runner.getDescription();
    }
}
