package sample;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class AddStakeholderBox {

    static boolean answer;
    static String name;

    public static String display() {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setResizable(false);
        window.setAlwaysOnTop(true);
        window.onHiddenProperty().unbind();
        window.setTitle("Add new stakeholder");
        window.setMinWidth(250);



        Label label = new Label();
        label.setText("Enter name");

        TextField nameField = new TextField();
        nameField.setPromptText("Enter name....");


        Button okBtn = new Button("Confirm");
        Button closeBtn = new Button("Close");

        okBtn.setOnAction(e-> {
            answer = true;
            name = nameField.getText();
            window.close();
        });

        closeBtn.setOnAction(e-> {
            answer = false;
            window.close();
        });

        HBox hBox = new HBox(okBtn, closeBtn);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, nameField, hBox);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        if (answer!=true) {
            return "";
        } else {
            return name;
        }
    }
}