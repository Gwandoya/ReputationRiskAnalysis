package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

/**
 * Created by dödsadde on 2017-04-05.
 */
public class DeleteBoxController {

    private Parent parent;
    private Scene scene;
    private Stage stage;

    @FXML
    private Button deleteBtn;
    @FXML
    private Button closeBtn;
    @FXML
    private TextField textField;
    @FXML
    private Label name;

    private static boolean delete = false;

    public DeleteBoxController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("deleteBox.fxml"));
        fxmlLoader.setController(this);
        try {
            parent = (Parent) fxmlLoader.load();
            scene = new Scene(parent, 369, 148);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void display(String type, String s) {
        this.stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);
        stage.onHiddenProperty().unbind();
        stage.setTitle("Delete " + type + ": " + s);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                delete = false;
            }
        });
        name.setText(s);
        stage.showAndWait();


    }

    public void deleteBtnHandeler(ActionEvent event) {
        delete = true;
        stage.close();
    }

    public void closeBtnHandeler(ActionEvent event) {
        delete = false;
        stage.close();
    }

    public static boolean getDeleteConfirm (){return delete;}
}
