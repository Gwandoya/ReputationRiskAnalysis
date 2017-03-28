package sample;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        LoginController loginController = new LoginController();
        loginController.launchLogingController(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
