package com.nanicky.emailsender.main.handler;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class UIemailHandler {
    public static void onStartSending(Button sendMailButton, Label errorLabel, Pane sendingPane) {
        sendMailButton.setDisable(true);
        sendingPane.setVisible(true);
        errorLabel.setText("");
    }

    public static void onFinishSending(Button sendMailButton, Label errorLabel, String errorText, Pane sendingPane) {
        sendMailButton.setDisable(false);
        sendingPane.setVisible(false);
        errorLabel.setText(errorText);
    }
}
