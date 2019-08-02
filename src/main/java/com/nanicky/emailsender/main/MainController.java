package com.nanicky.emailsender.main;

import com.nanicky.emailsender.Main;
import com.nanicky.emailsender.main.handler.EmailHandler;
import com.nanicky.emailsender.main.handler.ReportHandler;
import com.nanicky.emailsender.main.handler.UIemailHandler;
import com.nanicky.emailsender.model.*;
import com.nanicky.emailsender.scheduler.MyTimer;
import com.nanicky.emailsender.scheduler.TimeUtil;
import com.nanicky.emailsender.service.AppDataService;
import com.nanicky.emailsender.service.DirStorageService;
import com.nanicky.emailsender.service.SendingReportService;
import com.nanicky.emailsender.service.UserDataService;
import com.nanicky.emailsender.util.EmailValidator;
import com.nanicky.emailsender.util.TimeValidator;
import com.nanicky.emailsender.util.UtilDialog;
import com.nanicky.emailsender.util.UtilStackTrace;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Controller("mainController")
public class MainController implements Initializable {
    @Autowired
    private AppDataService appDataService;
    @Autowired
    private UserDataService userDataService;
    @Autowired
    private DirStorageService dirsService;
    @Autowired
    private SendingReportService reportService;


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
    TableView<TableFileModel> filesTableView;
    @FXML
    TableView<TableEmailModel> emailsTableView;
    @FXML
    Pane sendingPane;
    @FXML
    Label errorLabel;
    @FXML
    CheckBox showHidePassFLG;
    @FXML
    TextField emailToText;
    @FXML
    ChoiceBox<DirectoryStorage> dirChoiceBox;
    @FXML
    TextField timeText;
    @FXML
    Label incorrectTimeLabel;
    @FXML
    Button setTimeButton;
    @FXML
    private Label timeElapsedLabel;
    @FXML
    private Button stopButton;
    @FXML
    private Button addEmailButton;
    @FXML
    private Label timeDescLabel;


    private MyTimer timer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        timer = new MyTimer(timeElapsedLabel, this::sendMail, timeDescLabel);
        emailFromText.setOnKeyTyped(event -> {
            String email = emailFromText.getText() + event.getCharacter();
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
                addEmailButton.setDisable(false);
            } else {
                incorrectEmailToLabel.setOpacity(1);
                addEmailButton.setDisable(true);
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

        filesTableView.getColumns().addAll(nameColumn, pathColumn);

        TableColumn<TableEmailModel, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailColumn.setPrefWidth(366);
        emailsTableView.getColumns().add(emailColumn);

        dirChoiceBox.setOnAction(event -> {
            DirectoryStorage dir = dirChoiceBox.getValue();
            if (dir == null) return;
            errorLabel.setText("");
            try {
                dirsService.findByPath(dir.getPath());
                emailToText.setDisable(false);
                setUI(dir);
            } catch (Exception e) {
                errorLabel.setText(UtilStackTrace.getStackTrace(e));
            }
        });

        AppData appData = appDataService.get();
        if (appData != null) {
            List<DirectoryStorage> dirs = appData.getDirs();
            dirChoiceBox.getItems().addAll(dirs);

            if (appData.getSendingTime() != null && !appData.getSendingTime().isEmpty()) {
                timeText.setText(appData.getSendingTime());
                onSetTime(null);
            }
        }

        UserData userData = userDataService.get();
        if (userData != null) {
            emailFromText.setText(userData.getEmail());
            loginText.setText(userData.getEmail().split("@")[0]);
            passwordText.setText(userData.getPassword());
        }

        timeText.setOnKeyTyped(event -> {
            if (timer.isRunning()) {
                event.consume();
                return;
            }
            String time = timeText.getText() + event.getCharacter();
            boolean formatIsValid = TimeValidator.validate(time);
            if (formatIsValid) {
                incorrectTimeLabel.setOpacity(0);
                setTimeButton.setDisable(false);
            } else {
                incorrectTimeLabel.setOpacity(1);
                setTimeButton.setDisable(true);
            }
        });

        ImageView folderImage = new ImageView("images/folder.png");
        folderImage.setFitHeight(24);
        folderImage.setFitWidth(30);
        chooseDirButton.setGraphic(folderImage);
    }

    /*public void onChooseFileClicked(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(Paths.get("").toAbsolutePath().toFile());
        Stage stage = new Stage();
        stage.initOwner(Main.primaryStage);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setAlwaysOnTop(true);

        chooseFileButton.setDisable(true);
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            ObservableList<TableFileModel> tableItems = filesTableView.getItems();
            tableItems.add(new TableFileModel(file.getName(), file.getPath()));
            filesTableView.setItems(tableItems);
        }
        chooseFileButton.setDisable(false);
    }*/

    public void onChooseDirClicked(ActionEvent actionEvent) {
        chooseDirButton.setDisable(true);

        File dir = UtilDialog.createDirChooserDialog();

        if (dir != null) {
            DirectoryStorage directoryStorage = new DirectoryStorage(dir.getName(), dir.getPath());
            AppData appData = appDataService.get();
            if (appData == null) appData = new AppData();
            List<DirectoryStorage> dirs = appData.getDirs();
            dirs.add(directoryStorage);
            directoryStorage.setAppData(appData);
            appDataService.save(appData);

            dirChoiceBox.getItems().add(directoryStorage);
            dirChoiceBox.setValue(directoryStorage);

            setUI(directoryStorage);
        }
        chooseDirButton.setDisable(false);
    }

    public void onAddEmail(ActionEvent event) {
        String email = emailToText.getText();
        DirectoryStorage dir = dirChoiceBox.getValue();
        if (dir == null) return;
        errorLabel.setText("");
        try {
            dir = dirsService.findByPath(dir.getPath());
            Set<String> emails = dir.getEmails();
            emails.add(email);
            dirsService.save(dir);
            emailToText.setText("");
            addEmailButton.setDisable(true);
            setUI(dir);
        } catch (Exception e) {
            errorLabel.setText(UtilStackTrace.getStackTrace(e));
        }
    }

    private void updateFilesTable(File dir) {
        List<File> filesInChosenDir = Arrays.asList(dir.listFiles(File::isFile));
        updateFilesTable(filesInChosenDir);
    }

    private void updateFilesTable(List<File> filesInChosenDir) {
        ObservableList<TableFileModel> tableItems = filesTableView.getItems();
        tableItems.clear();
        filesInChosenDir.forEach(file -> {
            tableItems.add(new TableFileModel(file.getName(), file.getPath()));
        });
        filesTableView.setItems(tableItems);
    }

    public void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.DELETE) {
            TableEmailModel selectedItem = emailsTableView.getSelectionModel().getSelectedItem();
            if (selectedItem == null) return;
            DirectoryStorage dir = dirChoiceBox.getValue();
            dir = dirsService.findByPath(dir.getPath());
            Set<String> emails = dir.getEmails();
            emails.remove(selectedItem.getEmail());
            dirsService.save(dir);
            setUI(dir);
        }
    }

    public void sendMail(ActionEvent actionEvent) {
        sendMail();
    }

    private void sendMail() {
        UIemailHandler.onStartSending(sendMailButton, errorLabel, sendingPane, setTimeButton, stopButton);
        new Thread(() -> {
            AtomicReference<String> errorText = new AtomicReference<>("");
            List<SendingReport> reports = new ArrayList<>();

            try {

                AppData appData = appDataService.get();
                if (appData == null) {
                    throw new RuntimeException("No data was added yet");
                }

                List<DirectoryStorage> appDataDirs = appData.getDirs();
                UserData userData = userDataService.get();
                if (userData == null) {
                    throw new RuntimeException("No credentials were added yet");
                }


                for (int i = 0; i < appDataDirs.size(); i++) {
                    DirectoryStorage directoryStorage = appDataDirs.get(i);
                    // updateUi
                    Platform.runLater(() -> setUI(directoryStorage));

                    String subject = directoryStorage.getSubject();
                    String body = directoryStorage.getBody();
                    List<File> files = Arrays.asList(new File(directoryStorage.getPath()).listFiles(File::isFile));

                    Set<String> emails = directoryStorage.getEmails();
                    Iterator<String> iterator = emails.iterator();
                    while (iterator.hasNext()) {
                        String emailTo = iterator.next();
                        try {
                            EmailHandler emailHandler = new EmailHandler(userData, emailTo, subject, body, files);
                            emailHandler.sendMail();
                            reports.add(new SendingReport(emailTo, files));
                        } catch (Exception e) {
                            reports.add(new SendingReport(emailTo, files, UtilStackTrace.getStackTrace(e)));
                        }
                    }
                }


            } catch (Exception e) {
                System.out.println("ERROR");
                errorText.set(e.getMessage());
                e.printStackTrace();
                System.out.println("ERROR");
            } finally {
                reportService.save(reports);
                Platform.runLater(() -> {
                    UIemailHandler.onFinishSending(sendMailButton, errorLabel, errorText.get(), sendingPane, setTimeButton, stopButton);
                    onSetTime(null);
                });
            }
        }).start();
    }

    public void onStopScheduling(ActionEvent event) {
        onStopScheduling();
    }

    private void onStopScheduling() {
        timer.stop();
        timeText.setText("");
        AppData appData = appDataService.get();
        if (appData != null) {
            appData.setSendingTime("");
            appDataService.save(appData);
        }
        timeText.setDisable(false);
        stopButton.setDisable(true);
        timeDescLabel.setDisable(true);
    }

    public void onSetTime(ActionEvent event) {
        AppData appData = appDataService.get();
        if (appData == null)
            return;


        setTimeButton.setDisable(true);
        timeText.setDisable(true);
        String timeStr = timeText.getText();
        appData.setSendingTime(timeStr);
        appDataService.save(appData);
        int diffMinutes = (int) TimeUtil.getDiffMinutes(timeStr);
        timer.setTimer(diffMinutes);
        stopButton.setDisable(false);
    }

    private void setUI(DirectoryStorage directoryStorage) {
        updateFilesTable(new File(directoryStorage.getPath()));
        updateEmailsTable(directoryStorage.getEmails());
        updateBodyAndSubject(directoryStorage);
    }

    private void updateBodyAndSubject(DirectoryStorage directoryStorage) {
        bodyText.setText(directoryStorage.getBody());
        subjectText.setText(directoryStorage.getSubject());
    }

    public void onSaveBodySubject(ActionEvent event) {
        DirectoryStorage dir = dirChoiceBox.getValue();
        if (dir == null) return;
        dir.setBody(bodyText.getText());
        dir.setSubject(subjectText.getText());
        dirsService.save(dir);
    }

    public void onReport(ActionEvent event) {
        List<SendingReport> reports = reportService.getAll();
        ReportHandler.showReports(reports);
    }

    public void onDeleteDir(ActionEvent event) {
        DirectoryStorage dir = dirChoiceBox.getValue();
        if (dir == null) return;
        AppData appData = appDataService.get();
        appData.getDirs().remove(dir);
        appData = appDataService.save(appData);
        ObservableList<DirectoryStorage> items = dirChoiceBox.getItems();
        items.clear();
        items.addAll(appData.getDirs());
        emailsTableView.getItems().clear();
        filesTableView.getItems().clear();
    }

    private void updateEmailsTable(Set<String> emails) {
        List<TableEmailModel> emailModels = emails.stream().map(TableEmailModel::new).collect(Collectors.toList());
        ObservableList<TableEmailModel> items = emailsTableView.getItems();
        items.clear();
        items.addAll(emailModels);
    }

    public void onSaveUserDataClicked(ActionEvent actionEvent) {
        String emailFrom = emailFromText.getText();
        String password = showHidePassFLG.isSelected() ? passwordVisibleText.getText() : passwordText.getText();
        userDataService.save(emailFrom, password);
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

    public void onExitClicked(ActionEvent actionEvent) {
        Main.primaryStage.hide();
    }
}
