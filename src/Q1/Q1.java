package Q1;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 * @author SMS
 */
public class Q1 extends Application {

    /**
     *
     * @param stage
     */
    @Override
    public void start(Stage stage) {
        ObservableList<String> items;
        items = FXCollections.observableArrayList("Black", "Blue", "Cyan", "Dark Gray","Gray","Green");
        ListView<String> listView1 = new ListView<>(items);
        listView1.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        ListView<String> listView2 = new ListView<>();
        Button copyBtn = new Button("Copy >>>");

        copyBtn.setOnAction(e -> {
            ObservableList<String> selectedItems = listView1.getSelectionModel().getSelectedItems();
            if (selectedItems.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "No item selections", ButtonType.OK);
                alert.showAndWait();
            } else {
                listView2.getItems().addAll(selectedItems);
            }
        });

        HBox root = new HBox(10, listView1, copyBtn, listView2);
        Scene scene = new Scene(root, 400, 250);
        stage.setTitle("Multiple Selection Lists");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
