/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.hogedriven.junit.nekorunner;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author eguchi
 */
public class NekoFrame extends Application {

    private Timeline timeline;

    @Override
    public void start(Stage stage) throws Exception {

        StackPane stackPane = new StackPane();
        BorderPane neko0 = createNeko("neko0.jpg");
        BorderPane neko1 = createNeko("neko1.jpg");

        stackPane.getChildren().addAll(neko0, neko1);

        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);

        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO, new KeyValue(neko1.visibleProperty(), false)),
                new KeyFrame(Duration.millis(500), new KeyValue(neko1.visibleProperty(), true)),
                new KeyFrame(Duration.millis(1000), new KeyValue(neko1.visibleProperty(), false)));

        Scene scene = new Scene(stackPane);

        timeline.play();

        stage.centerOnScreen();
        stage.setScene(scene);
        stage.show();
    }

    private BorderPane createNeko(String path) {
        BorderPane neko0 = new BorderPane();
        ImageView neko0Image = new ImageView(new Image(getClass().getResourceAsStream(path)));
        neko0.setCenter(neko0Image);
        return neko0;
    }

    @Override
    public void stop() throws Exception {
        timeline.stop();
    }
}
