package main;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.File;

public class Main extends Application {
    private static final String APP_NAME = "Email Sender";

    public static Stage primaryStage;

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle(APP_NAME);
        // splash
        Pane splashRoot = new Pane();
        ImageView imageView = new ImageView();
        imageView.setFitHeight(300);
        imageView.setFitWidth(400);
        File spalshImage = new File("res/images/splash image big.png");
        imageView.setImage(new Image(spalshImage.toURI().toString()));
        splashRoot.getChildren().add(imageView);
        Scene splashScene = new Scene(splashRoot, 400, 300);
        // main
        File mainScreenFXML = new File("res/fxml/sample.fxml");
        Parent root = FXMLLoader.load(mainScreenFXML.toURI().toURL());
        Scene mainScene = new Scene(root, 1400, 800);
        // main Stage
        Stage mainStage = new Stage();
        Main.primaryStage = mainStage;
        mainStage.setTitle(APP_NAME);
        mainStage.setScene(mainScene);
        mainStage.initStyle(StageStyle.TRANSPARENT);
        /**** DRAGGABLE ******/
        //grab your root here
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });

        //move around here
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mainStage.setX(event.getScreenX() - xOffset);
                mainStage.setY(event.getScreenY() - yOffset);
            }
        });
        /**** DRAGGABLE ******/
        mainStage.setOnHidden(event -> Platform.exit());
        mainScene.getRoot().requestFocus();
        mainStage.setScene(mainScene);
        // set
        primaryStage.setScene(splashScene);
        primaryStage.initStyle(StageStyle.UNDECORATED);



        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2000), ev -> {
            primaryStage.hide();
            mainStage.show();
        }));

        Timeline timeline1 = new Timeline(new KeyFrame(Duration.millis(100), ev -> {
            File spalshImageGif = new File("res/images/splash gif.gif");
            imageView.setImage(new Image(spalshImageGif.toURI().toString()));
            timeline.play();
        }));
        timeline1.play();
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
