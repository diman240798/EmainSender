package com.nanicky.emailsender.main.handler;

import com.nanicky.emailsender.model.SendingReport;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.List;

public class ReportHandler {
    public static void showReports(List<SendingReport> reports) {
        int height = 400;
        int width = 700;

        TableView<SendingReport> reportTableView = new TableView<>();
        reportTableView.setPrefWidth(width);
        reportTableView.setPrefHeight(height);

        TableColumn<SendingReport, String> emailToColumn = new TableColumn<>("Email");
        TableColumn<SendingReport, String> statusColumn = new TableColumn<>("Статус");
        TableColumn<SendingReport, String> filesColumn = new TableColumn<>("Файлы");
        TableColumn<SendingReport, String> errorColumn = new TableColumn<>("Ошибка");
        // style
        emailToColumn.setPrefWidth(200);
        statusColumn.setPrefWidth(100);
        filesColumn.setPrefWidth(300);
        errorColumn.setPrefWidth(100);
        // value factory
        emailToColumn.setCellValueFactory(new PropertyValueFactory<>("emailTo"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        filesColumn.setCellValueFactory(new PropertyValueFactory<>("files"));
        errorColumn.setCellValueFactory(new PropertyValueFactory<>("errorText"));

        reportTableView.getColumns().addAll(emailToColumn, statusColumn, filesColumn, errorColumn);

        BorderPane pane = new BorderPane();
        pane.setCenter(reportTableView);
        Scene scene = new Scene(pane, width, height);
        scene.getStylesheets().add("css/sky.css");
        Stage stage = new Stage(StageStyle.UTILITY);
        stage.setTitle("Отчет");
        stage.setScene(scene);

        reportTableView.getItems().addAll(reports);

        stage.show();
    }
}
