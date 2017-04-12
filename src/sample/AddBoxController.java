package sample;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.WindowEvent;
import javafx.event.ActionEvent;

import java.io.IOException;

public class AddBoxController {

    private Parent parent;
    private Scene scene;
    private Stage stage;

    private static boolean con;
    private static String name;

    @FXML
    private Button okBtn;
    @FXML
    private Button closeBtn;
    @FXML
    private TextField nameField;


    public AddBoxController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addBox.fxml"));
        fxmlLoader.setController(this);
        try {
            parent = (Parent) fxmlLoader.load();
            scene = new Scene(parent, 369, 148);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void display(String type) {
        this.stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);
        stage.onHiddenProperty().unbind();
        stage.setTitle("Add New " + type);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                con = false;
            }
        });
        stage.showAndWait();

    }

    public void okBtnHandeler(ActionEvent event) {
        name = nameField.getText();
        if (name.length()<30 && !name.isEmpty()) {
            con = true;
            stage.close();
        }else if (name.isEmpty()) {
            AlertBox.display("Invalid input", "Name field is empty");
        } else {
            AlertBox.display("Invalid input", "Input is longer than 30 characters");
        }
    }

    public void closeBtnHandeler(ActionEvent event) {
        con = false;
        stage.close();
    }

    public static String getName() {
        return name;
    }

    public static boolean getContinue() {
        return con;
    }
}



