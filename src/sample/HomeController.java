package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;

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
    @FXML
    private TextField stkNameTextView;
    @FXML
    private Label wrongNameLbl;


    ArrayList<StakeholderLI> stakeholderLIs = new ArrayList<StakeholderLI>();


    private final Node rootIcon =
            new ImageView(new Image(getClass().getResourceAsStream("multiuser_16.png")));
    private final Image leafIcon =
            new Image(getClass().getResourceAsStream("user_16.png"));

    TreeItem<String> rootItem = new TreeItem<String>("Stakeholders", rootIcon);

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
        setUpInit();
        rootItem.setExpanded(true);

        for (StakeholderLI stakeholderLI : stakeholderLIs) {
            TreeItem<String> item = new TreeItem<String>(stakeholderLI.getName(), new ImageView(leafIcon));
            rootItem.getChildren().add(item);
        }
        treeView.setRoot(rootItem);
    }

    public void setUpInit() {
        StakeholderLI one = new StakeholderLI("Andreas");
        StakeholderLI two = new StakeholderLI("Benjamin");
        StakeholderLI three = new StakeholderLI("Anton");
        stakeholderLIs.add(one);
        stakeholderLIs.add(two);
        stakeholderLIs.add(three);
    }

    public void treeViewUpdate(String name) {
        TreeItem<String> item = new TreeItem<String>(name, new ImageView(leafIcon));
        rootItem.getChildren().add(item);
        /*
        for (StakeholderLI stakeholderLI : stakeholderLIs) {
            for (TreeItem<String> leaf : rootItem.getChildren()) {
                if (!stakeholderLI.getName().equalsIgnoreCase(leaf.toString())) {
                    TreeItem<String> item = new TreeItem<String>(stakeholderLI.getName(), new ImageView(leafIcon));
                    rootItem.getChildren().add(item);
                }
            }
        }
        */
    }

    @FXML
    public void handleAddStkBtn(ActionEvent event) throws InterruptedException {
        /**
         * TODO
         * ´Flytta över detta till en "message dialog" ist.
         */
        if (!stkNameTextView.getText().isEmpty()) {
            String temp = stkNameTextView.getText();
            boolean isAlphabetical = temp.chars().allMatch(Character::isLetter);
            if (isAlphabetical) {
                StakeholderLI newStkhldr = new StakeholderLI(stkNameTextView.getText());
                //Thread.sleep(200);
                stakeholderLIs.add(newStkhldr);
                treeViewUpdate(newStkhldr.getName());
                wrongNameLbl.setVisible(false);
            } else {
                wrongNameLbl.setText("Only letters");
                wrongNameLbl.setVisible(true);
            }
        } else {
            wrongNameLbl.setText("Please Enter a Name!");
            wrongNameLbl.setVisible(true);
        }
    }

    @FXML
    public void handleEditStkBtn(ActionEvent event) {
    }

    @FXML
    public void handleDeleteStkBtn(ActionEvent event) {
    }


    public class StakeholderLI {
        private final SimpleStringProperty name;

        public StakeholderLI(String name) {
            this.name = new SimpleStringProperty(name);
        }

        public String getName() {
            return name.get();
        }

        public void setName(String nName) {
            name.set(nName);
        }
    }


}