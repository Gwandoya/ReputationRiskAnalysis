package sample;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class HomeController {
    private Parent parent;
    private Scene scene;
    private Stage stage;

    @FXML
    private TreeView treeView;
    @FXML
    private Button addStkBtn;
    @FXML
    private Button editStkBtn;
    @FXML
    private Button deleteStkButton;


    private final Node rootIcon =
            new ImageView(new Image(getClass().getResourceAsStream("multiuser_16.png")));
    private final Node leafIcon =
            new ImageView(new Image(getClass().getResourceAsStream("user_16.png")));
    List<StakeholderLI> stakeholderLIs = Arrays.<StakeholderLI>asList(
            new StakeholderLI("Andreas"),
            new StakeholderLI("Benjamin"));


    public HomeController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("home.fxml"));
        fxmlLoader.setController(this);
        try {
            parent = (Parent) fxmlLoader.load();
            scene = new Scene(parent, 800, 600);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void redirectHome(Stage stage) {
        stage.setTitle("Reputation Risk Analysis Tool");
        stage.setScene(scene);
        treeViewInit();
        stage.hide();
        stage.show();

    }

    public void treeViewInit() {
        TreeItem<String> rootItem = new TreeItem<String>("Stakeholder Groups", rootIcon);
        rootItem.setExpanded(true);

        for (StakeholderLI stakeholderLI: stakeholderLIs){
            TreeItem<String> item = new TreeItem<String>(stakeholderLI.getName(), leafIcon);
            rootItem.getChildren().add(item);
        }

        treeView.setRoot(rootItem);


    }

    public class StakeholderLI {
        private final SimpleStringProperty name;

        public StakeholderLI(String name) {
            this.name = new SimpleStringProperty(name);
        }

        public String getName(){return name.get();}

        public void setName(String nName) {name.set(nName);}
    }


}