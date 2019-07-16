package main;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.*;
import main.handler.EmailHandler;
import main.handler.UIemailHandler;
import util.EmailValidator;
import model.TableFileModel;
import util.TableViewUtil;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.stream.Collectors.toList;

public class Controller implements Initializable {
    @FXML
    Label incorrectEmailFromLabel;
    @FXML
    Label incorrectEmailToLabel;
    @FXML
    TextField emailFromText;
    @FXML
    TextField loginText;
    @FXML
    PasswordField passwordText;
    @FXML
    TextField passwordVisibleText;
    @FXML
    TextField emailToText;
    @FXML
    TextField extensionsText;
    @FXML
    Button chooseFileButton;
    @FXML
    TextField subjectText;
    @FXML
    TextArea bodyText;
    @FXML
    Button sendMailButton;
    @FXML
    Button chooseDirButton;
    @FXML
    TableView<TableFileModel> tableView;
    @FXML
    Pane sendingPane;
    @FXML
    Label errorLabel;
    @FXML
    CheckBox showHidePassFLG;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        emailFromText.setOnKeyTyped(event -> {
            String email = emailFromText.getText();
            if (EmailValidator.validate(email)) {
                incorrectEmailFromLabel.setOpacity(0);
            } else {
                incorrectEmailFromLabel.setOpacity(1);
            }
            String login = email.split("@")[0];
            loginText.setText(login);
        });
        emailToText.setOnKeyTyped(event -> {
            String email = emailToText.getText();
            if (EmailValidator.validate(email)) {
                incorrectEmailToLabel.setOpacity(0);
            } else {
                incorrectEmailToLabel.setOpacity(1);
            }
        });

        TableColumn<TableFileModel, String> nameColumn = new TableColumn<>("Имя файла");
        TableColumn<TableFileModel, String> pathColumn = new TableColumn<>("Путь");
        // style
        nameColumn.setPrefWidth(150);
        pathColumn.setPrefWidth(600);
        // value factory
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        pathColumn.setCellValueFactory(new PropertyValueFactory<>("path"));

        tableView.getColumns().addAll(nameColumn, pathColumn);

    }

    public void onChooseFileClicked(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(Paths.get("").toAbsolutePath().toFile());
        if (extensionsText.getText().length() > 0) {
            List<String> extensionsList = ExtentionsValidator.validateAndGet(extensionsText.getText());
            extensionsList = extensionsList.stream().map(ext -> "*." + ext).collect(toList());
            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("extensions", extensionsList);
            fileChooser.getExtensionFilters().add(filter);
        }
        Stage stage = new Stage();
        stage.initOwner(Main.primaryStage);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setAlwaysOnTop(true);

        chooseFileButton.setDisable(true);
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            ObservableList<TableFileModel> tableItems = tableView.getItems();
            tableItems.add(new TableFileModel(file.getName(), file.getPath()));
            tableView.setItems(tableItems);
        }
        chooseFileButton.setDisable(false);
    }

    public void onChooseDirClicked(ActionEvent actionEvent) {
        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setInitialDirectory(Paths.get("").toAbsolutePath().toFile());

        Stage stage = new Stage();
        stage.initOwner(Main.primaryStage);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setAlwaysOnTop(true);

        chooseDirButton.setDisable(true);
        File dir = dirChooser.showDialog(stage);
        if (dir != null) {
            List<File> filesInChosenDir = Arrays.asList(dir.listFiles(File::isFile));
            if (extensionsText.getText().length() > 0) {
                List<String> extensionsList = ExtentionsValidator.validateAndGet(extensionsText.getText());
                filesInChosenDir = filesInChosenDir.stream().filter(file -> extensionsList.stream().anyMatch(ext -> file.getName().endsWith(ext))).collect(toList());
            }
            filesInChosenDir.forEach(file -> {
                ObservableList<TableFileModel> tableItems = tableView.getItems();
                tableItems.add(new TableFileModel(file.getName(), file.getPath()));
                tableView.setItems(tableItems);
            });
        }
        chooseDirButton.setDisable(false);
    }

    public void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.DELETE) {
            tableView.getItems().removeAll(tableView.getSelectionModel().getSelectedItem());
        }
    }

    public void sendMail(ActionEvent actionEvent) {
        UIemailHandler.onStartSending(sendMailButton, errorLabel, sendingPane);
        new Thread(()-> {
            AtomicReference<String> errorText = new AtomicReference<>("");
            try {
                String emailFrom = emailFromText.getText();
                String login = loginText.getText();
                String password = showHidePassFLG.isSelected() ? passwordVisibleText.getText() : passwordText.getText();
                String emailTo = emailToText.getText();
                String subject = subjectText.getText();
                String body = bodyText.getText();
                List<File> files = TableViewUtil.getFilesFromTable(tableView);

                EmailHandler emailHandler = new EmailHandler(emailFrom, login, password, emailTo, subject, body, files);
                emailHandler.sendMail();
            } catch (Exception e) {
                errorText.set(e.getMessage());
                e.printStackTrace();
            } finally {
                Platform.runLater(() -> {
                    UIemailHandler.onFinishSending(sendMailButton, errorLabel, errorText.get(), sendingPane);
                });
            }
        }).start();
    }

    public void onClearEmailFrom(ActionEvent actionEvent) {
        emailFromText.setText("");
    }

    public void onClearLogin(ActionEvent actionEvent) {
        loginText.setText("");
    }

    public void onClearPassword(ActionEvent actionEvent) {
        passwordText.setText("");
        passwordVisibleText.setText("");
    }

    public void onClearEmailTo(ActionEvent actionEvent) {
        emailToText.setText("");
    }

    public void onClearExtensions(ActionEvent actionEvent) {
        extensionsText.setText("");
    }

    public void onClearSubject(ActionEvent actionEvent) {
        subjectText.setText("");
    }

    public void onShowHidePasswordClicked(ActionEvent actionEvent) {
        if (showHidePassFLG.isSelected()) {
            String text = passwordText.getText();
            passwordVisibleText.setText(text);
            passwordText.setVisible(false);
            passwordVisibleText.setVisible(true);
        } else {
            String text = passwordVisibleText.getText();
            passwordText.setText(text);
            passwordVisibleText.setVisible(false);
            passwordText.setVisible(true);
        }
    }

    public void onClearAllData(ActionEvent actionEvent) {
        tableView.getItems().clear();
        subjectText.setText("");
        bodyText.setText("");
    }

    public void onExitClicked(ActionEvent actionEvent) {
        Main.primaryStage.hide();
    }
}
