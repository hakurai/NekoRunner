package net.hogedriven.junit.nekorunner;

import java.awt.BorderLayout;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

public class NekoRunner extends Runner {

    private static final AtomicInteger testCount = new AtomicInteger(0);

    private static JFrame frame;

    private static final ScheduledExecutorService executor = Executors
        .newSingleThreadScheduledExecutor();

    private static boolean initialized;

    private final Runner runner;

    public NekoRunner(Class<?> klass) throws InitializationError {
        runner = new BlockJUnit4ClassRunner(klass);
        testCount.incrementAndGet();
    }

    @Override
    public void run(RunNotifier notifier) {
        if (initialized == false) {
            initialized = true;
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    try {
                        init();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

            });
        }
        runner.run(notifier);
        if (testCount.decrementAndGet() == 0) {
            executor.shutdownNow();
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    destroy();
                }

            });
        }
    }

    private void init() throws IOException {
        final ImageIcon icon0 =
            new ImageIcon(ImageIO.read(getClass().getResource("neko0.jpg")));
        final ImageIcon icon1 =
            new ImageIcon(ImageIO.read(getClass().getResource("neko1.jpg")));
        final JLabel neko = new JLabel(icon0);
        executor.scheduleAtFixedRate(new Runnable() {

            private boolean b;

            @Override
            public void run() {
                final ImageIcon icon = b ? icon0 : icon1;
                SwingUtilities.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        neko.setIcon(icon);
                    }
                });
                b = !b;
            }
        }, 500L, 100L, TimeUnit.MILLISECONDS);

        frame = new JFrame();
        frame.getContentPane().add(neko, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

    private void destroy() {
        if (frame != null) {
            frame.dispose();
        }
    }

    @Override
    public Description getDescription() {
        return runner.getDescription();
    }

}
