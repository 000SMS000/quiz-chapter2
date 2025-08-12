package Q2;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author SMS
 */
public class Q2 extends Application{
    @Override
    public void start(Stage stage) {
        TextField celsiusField = new TextField();
        celsiusField.setPromptText("Enter Celsius");

        Label resultLabel = new Label();
        ToggleGroup group = new ToggleGroup();
        RadioButton toF = new RadioButton("Fahrenheit");
        RadioButton toK = new RadioButton("Kelvin");
        toF.setToggleGroup(group);
        toK.setToggleGroup(group);

        Button convertBtn = new Button("Convert");

        convertBtn.setOnAction(e -> {
            try {
                double celsius = Double.parseDouble(celsiusField.getText());
                if (toF.isSelected()) {
                    double fahrenheit = celsius * 9 / 5 + 32;
                    resultLabel.setText(String.format("New Teperature is: %.2f °F", fahrenheit));
                } else if (toK.isSelected()) {
                    double kelvin = celsius + 273.15;
                    resultLabel.setText(String.format("New Teperature is: %.2f K", kelvin));
                } else {
                    resultLabel.setText("Select conversion type");
                }
            } catch (NumberFormatException ex) {
                resultLabel.setText("Invalid input!");
            }
        });

        VBox root = new VBox(10, celsiusField, toF, toK, convertBtn, resultLabel);
        root.setPadding(new Insets(15));
        Scene scene = new Scene(root, 300, 200);
        stage.setTitle("Temperature Converter");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
    
}
