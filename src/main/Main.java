package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class Main extends Application {
    private static final String APP_NAME = "Email Sender";

    public static Stage primaryStage;

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("resources/fxml/sample.fxml"));
        Scene mainScene = new Scene(root, 1400, 800);
        // main Stage
        /*CLOSING*/
        Platform.setImplicitExit(false);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                primaryStage.hide();
                event.consume();
            }
        });
        /*CLOSING*/
        primaryStage.setTitle(APP_NAME);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
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
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            }
        });
        /**** DRAGGABLE ******/
        primaryStage.setScene(mainScene);
        Main.primaryStage = primaryStage;
        javax.swing.SwingUtilities.invokeLater(this::setTrayIcon);
    }

    private void setTrayIcon() {
        if (!SystemTray.isSupported()) {
            return;
        }

        PopupMenu trayMenu = new PopupMenu();
        MenuItem item = new MenuItem("Выйти");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        trayMenu.add(item);

        URL imageURL = getClass().getResource("resources/images/tray.png");

        java.awt.Image icon = Toolkit.getDefaultToolkit().getImage(imageURL);
        TrayIcon trayIcon = new TrayIcon(icon, Main.APP_NAME, trayMenu);
        trayIcon.setImageAutoSize(true);
        trayIcon.addActionListener(this::onTryClicked);

        SystemTray tray = SystemTray.getSystemTray();
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    private void onTryClicked(ActionEvent actionEvent) {
        Platform.runLater(this::startNewGui);
    }

    private void startNewGui() {
        Main.primaryStage.show();
        Main.primaryStage.toFront();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
