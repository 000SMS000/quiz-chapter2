package Q4;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author SMS
 */
public class Q4 extends Application {
    private final Map<String, String> users = new HashMap<>();
    private Scene loginScene;

    @Override
    public void start(Stage stage) {
        loadUsersFromResource("users.txt");
        stage.setTitle("Login Page");

        Label header = new Label("Welcome");
        header.getStyleClass().add("title");

        Label userLbl = new Label("User Name:");
        Label passLbl = new Label("Password:");

        TextField username = new TextField();
        username.setPromptText("User Name");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        Button signIn = new Button("Sign in");
        signIn.getStyleClass().addAll("btn", "btn-teal");
        Button exitBtn = new Button("Exit");
        exitBtn.getStyleClass().addAll("btn", "btn-teal");

        Label status = new Label();

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(12);
        grid.add(header, 0, 0, 2, 1);
        grid.add(userLbl, 0, 1); grid.add(username, 1, 1);
        grid.add(passLbl, 0, 2); grid.add(password, 1, 2);

        HBox actions = new HBox(10, signIn, exitBtn);
        actions.setAlignment(Pos.CENTER);
        grid.add(actions, 1, 3);
        grid.add(status, 1, 4);

        BorderPane loginRoot = new BorderPane(grid);
        loginRoot.setPadding(new Insets(16));
        loginRoot.getStyleClass().add("bg");

        loginScene = new Scene(loginRoot, 360, 320);
        loginScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setScene(loginScene);

        exitBtn.setOnAction(e -> stage.close());
        signIn.setOnAction(e -> {
            String u = username.getText().trim();
            String p = password.getText();
            if (u.isEmpty() || p.isEmpty()) {
                status.setText("Please enter username and password");
                status.getStyleClass().setAll("error");
                return;
            }
            String givenHash = md5(p);
            String expected = users.get(u);
            if (expected != null && expected.equalsIgnoreCase(givenHash)) {
                status.setText("");
                stage.setScene(optionsScene(stage, u));
            } else {
                status.setText("Invalid credentials");
                status.getStyleClass().setAll("error");
            }
        });

        stage.show();

    }
    private Scene optionsScene(Stage stage, String username) {
        stage.setTitle("Options Page");

        Button addStudent = new Button("Add Student");
        addStudent.getStyleClass().addAll("btn", "btn-teal");
        Button viewStudent = new Button("View Student");
        viewStudent.getStyleClass().addAll("btn", "btn-teal");

        VBox box = new VBox(12, addStudent, viewStudent);
        box.setAlignment(Pos.CENTER);

        StackPane root = new StackPane(box);
        root.getStyleClass().add("bg");

        Scene scene = new Scene(root, 360, 320);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        return scene;
    }


    private void loadUsersFromResource(String resourcePath) {
        try (InputStream is = getClass().getResourceAsStream(resourcePath)) {
            if (is == null) throw new IllegalStateException("users.txt not found in resources");
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                String line;
                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty() || line.startsWith("#")) continue;
                    String[] parts = line.split(":");
                    if (parts.length == 2) {
                        users.put(parts[0].trim(), parts[1].trim());
                    }
                }
            }
        } catch (Exception ex) {
             new Alert(Alert.AlertType.ERROR, "Failed to open file:\n" + ex.getMessage()).showAndWait();
        }
    }

    private static String md5(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] dig = md.digest(s.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : dig) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            return "";
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
