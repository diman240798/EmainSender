package com.nanicky.emailsender;

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
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Arrays;

@SpringBootApplication
public class Main extends Application {
    private static final String APP_NAME = "Email Sender";

    public static Stage primaryStage;
    private ConfigurableApplicationContext springContext;
    private FXMLLoader fxmlLoader;
    private Parent rootNode;

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void init() throws Exception {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(Main.class);
        builder.headless(false);
        springContext = builder.run();
        fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(springContext::getBean);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        fxmlLoader.setLocation(getClass().getResource("/fxml/sample.fxml"));
        rootNode = fxmlLoader.load();

        Scene mainScene = new Scene(rootNode, 1400, 800);
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
        //grab your rootNode here
        rootNode.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        //move around here
        rootNode.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            }
        });
        /**** DRAGGABLE ******/
        primaryStage.setScene(mainScene);
        Main.primaryStage = primaryStage;
        // TRAY ICON
        javax.swing.SwingUtilities.invokeLater(this::setTrayIcon);
//        Platform.runLater(this::setTrayIcon);
    }

    private void setTrayIcon() {
        PopupMenu trayMenu = new PopupMenu();
        MenuItem item = new MenuItem("Выйти");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        trayMenu.add(item);

        URL imageURL = getClass().getResource("/images/tray.png");
        Image icon = Toolkit.getDefaultToolkit().getImage(imageURL);

        TrayIcon trayIcon = new TrayIcon(icon, Main.APP_NAME, trayMenu);
        trayIcon.setImageAutoSize(true);
        trayIcon.addActionListener(this::onTryClicked);

        SystemTray tray = SystemTray.getSystemTray();
        try {
            boolean trayAlreadyAdded = Arrays.stream(tray.getTrayIcons()).anyMatch(iconTray -> iconTray.getToolTip().equals(Main.APP_NAME));
            if (trayAlreadyAdded) {
                System.out.println("Tray Already Added. App is running.");
                System.exit(0);
            } else {
                tray.add(trayIcon);
            }
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    private void onTryClicked(ActionEvent actionEvent) {
        Platform.runLater(this::showScene);
    }

    private void showScene() {
        Main.primaryStage.show();
        Main.primaryStage.toFront();

    }

    @Override
    public void stop() {
        springContext.stop();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
