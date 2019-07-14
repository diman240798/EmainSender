package util;

import javafx.scene.control.TableView;
import model.TableFileModel;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class TableViewUtil {
    public static List<File> getFilesFromTable(TableView<TableFileModel> tableView) {
        return tableView.getItems().stream().map(fileModel -> new File(fileModel.getPath())).collect(Collectors.toList());
    }
}
