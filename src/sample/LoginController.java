package sample;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


public class LoginController {

    private Parent parent;
    private Scene scene;
    private Stage stage;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;

    private HomeController homeController;

    public LoginController() {
        FXMLLoader fxmlLoader = new          FXMLLoader(getClass().getResource("login.fxml"));
        fxmlLoader.setController(this);
        try {
            parent = (Parent) fxmlLoader.load();
            scene = new Scene(parent, 600, 400);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * TODO
     * Skapa server cntrl och möjlighet att skapa ny användare.
     */
    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) {
        System.out.println(usernameField.getText());
       // if (usernameField.getText().equals("admin")&& passwordField.getText().equals("password")) {
            homeController = new HomeController();
            homeController.redirectHome(stage);
        //} else {
          //  System.out.print("LOGIN ERROR: " + usernameField.getText());
        //}

    }

    public void launchLogingController(Stage stage) {
        this.stage = stage;
        stage.setTitle("User Login");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.hide();
        stage.show();
    }
}