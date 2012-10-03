package net.hogedriven.junit.nekorunner;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

public class NekoRunner extends Runner {

    private static final AtomicInteger testCount = new AtomicInteger(0);

    private static JFrame frame;

    private static Timer timer;

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

        timer = new Timer(100, new ActionListener() {

            private boolean b;

            @Override
            public void actionPerformed(ActionEvent e) {
                final ImageIcon icon = b ? icon0 : icon1;
                neko.setIcon(icon);
                b = !b;
            }
        });

        frame = new JFrame();
        frame.add(neko, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
        timer.start();
    }

    private void destroy() {
        if (timer != null) {
            timer.stop();
        }
        if (frame != null) {
            frame.dispose();
        }
    }

    @Override
    public Description getDescription() {
        return runner.getDescription();
    }

}
