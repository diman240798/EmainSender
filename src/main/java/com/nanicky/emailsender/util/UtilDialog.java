package com.nanicky.emailsender.util;

import com.nanicky.emailsender.Main;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Paths;

public class UtilDialog {
    public static File createDirChooserDialog() {
        DirectoryChooser dirChooser = new DirectoryChooser();
//        dirChooser.setInitialDirectory(Paths.get("").toAbsolutePath().toFile());

        Stage stage = new Stage();
        stage.initOwner(Main.primaryStage);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setAlwaysOnTop(true);


        return dirChooser.showDialog(stage);
    }
}
