package sample;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {
    private Parent parent;
    private Scene scene;
    private Stage stage;
    @FXML
    private Text welcomeText;

    public HomeController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("home.fxml"));
        fxmlLoader.setController(this);
        try {
            parent = (Parent) fxmlLoader.load();
            scene = new Scene(parent, 600, 400);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void redirectHome(Stage stage) {
        stage.setTitle("Reputation Risk Analysis");
        stage.setScene(scene);
        stage.hide();
        stage.show();
    }


}