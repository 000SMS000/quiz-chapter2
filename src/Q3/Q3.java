package Q3;

//name : mahmoud srour
//id: 120210278

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


/**
 *
 * @author SMS
 */
public class Q3 extends Application {

    private TextArea textArea;

    @Override
    public void start(Stage stage) {
        textArea = new TextArea();
        textArea.setWrapText(true);
        textArea.setEditable(false);

        BorderPane root = new BorderPane(textArea);
        BorderPane.setMargin(textArea, new Insets(8));

        MenuBar menuBar = buildMenuBar(stage);
        root.setTop(menuBar);

        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("File View");
        stage.setScene(scene);
        stage.show();
    }

    private MenuBar buildMenuBar(Stage stage) {
        Menu fileMenu = new Menu("_File");
        fileMenu.setMnemonicParsing(true);

        MenuItem open = new MenuItem("Open");
        open.setOnAction(e -> doOpen(stage));

        MenuItem close = new MenuItem("Close");
        close.setOnAction(e -> {
            textArea.clear();
            textArea.setEditable(false);
        });

        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(e -> {
            Stage s = (Stage) textArea.getScene().getWindow();
            s.close();
        });

        fileMenu.getItems().addAll(open, new SeparatorMenuItem(), close, new SeparatorMenuItem(), exit);

        Menu formatMenu = new Menu("Edit");
        formatMenu.setMnemonicParsing(true);

        MenuItem sizeItem = new MenuItem("Font");
        sizeItem.setOnAction(e -> chooseFontSize());

        MenuItem colorItem = new MenuItem("Color");
        colorItem.setOnAction(e -> chooseTextColor());

        formatMenu.getItems().addAll(sizeItem, colorItem);

        return new MenuBar(fileMenu, formatMenu);
    }

    private void doOpen(Stage stage) {
        try {
            FileChooser fc = new FileChooser();
            fc.setTitle("Open TXT File");
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files (*.txt)", "*.txt"));
            File selected = fc.showOpenDialog(stage);
            if (selected == null) return;

            String content = Files.readString(selected.toPath(), StandardCharsets.UTF_8);
            textArea.setText(content);
            textArea.setEditable(true);

        } catch (IOException ex) {
            new Alert(Alert.AlertType.ERROR, "Failed to open file:\n" + ex.getMessage()).showAndWait();
        }
    }

    private void chooseFontSize() {
        List<Integer> sizes = Arrays.asList(12, 14, 16, 18, 20, 24, 28, 32);
        ChoiceDialog<Integer> dialog = new ChoiceDialog<>(16, sizes);
        dialog.setTitle("Font Selection");
        dialog.setHeaderText("Select the font from list");
        dialog.setContentText("Available Size");
        Optional<Integer> res = dialog.showAndWait();
        res.ifPresent(size -> textArea.setStyle("-fx-font-size: " + size + "px;"));
    }

    private void chooseTextColor() {
        List<String> colors = Arrays.asList("Red", "Black", "Blue", "Green", "Yellow", "Purple");
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Red", colors);
        dialog.setTitle("Color Selection");
        dialog.setHeaderText("Select the color from list");
        dialog.setContentText("Available Colors");
        Optional<String> res = dialog.showAndWait();
        res.ifPresent(color -> {
            String current = textArea.getStyle();
            String cleaned = current.replaceAll("-fx-text-fill:\\s*[^;]+;", "");
            textArea.setStyle(cleaned + "; -fx-text-fill: " + color + ";");
        });
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}
