package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class AddBox {

    private static boolean answer;
    private static boolean selGroup;
    private static String name;
    private final ToggleGroup group = new ToggleGroup();


    public void display() {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setResizable(false);
        window.setAlwaysOnTop(true);
        window.onHiddenProperty().unbind();
        window.setTitle("Add new...");
        window.setMinWidth(350);




        Label label = new Label();
        label.setText("Enter name:");

        TextField nameField = new TextField();
        nameField.setPromptText("Enter name....");

        HBox hBox1 = new HBox(label, nameField);

        RadioButton kpaBtn = new RadioButton("KPA");
        kpaBtn.setToggleGroup(group);
        RadioButton stkBtn = new RadioButton("Stakeholder");
        stkBtn.setToggleGroup(group);
        VBox vBox1 = new VBox(kpaBtn,stkBtn);




        Button okBtn = new Button("Confirm");
        Button closeBtn = new Button("Close");

        okBtn.setOnAction(e-> {
            answer = true;
            name = nameField.getText();
            selGroup = kpaBtn.isSelected();
            window.close();
        });

        closeBtn.setOnAction(e-> {
            answer = false;
            window.close();
        });

        HBox hBox2 = new HBox(okBtn, closeBtn);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(hBox1, vBox1, hBox2);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    public static String getName() {
        return name;
    }

    public static boolean getType() {
        return selGroup;
    }

    public static boolean getContinue() {
        return answer;
    }
}