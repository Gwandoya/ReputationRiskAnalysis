package sample;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        HomeController homeController = new HomeController(null);
        homeController.redirectHome(primaryStage);

    }

    public static void main(String[] args) {
        launch(args);
    }
}