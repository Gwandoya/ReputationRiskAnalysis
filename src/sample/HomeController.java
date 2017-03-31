package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.*;

public class HomeController {
    private Parent parent;
    private Scene scene;
    private Stage stage;

    private boolean markedStk = false;

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
    }

    @FXML
    public void handleAddStkBtn(ActionEvent event) throws InterruptedException {

        String stkName = AddStakeholderBox.display();

        boolean isAlphabetical = stkName.chars().allMatch(Character::isLetter);
        boolean fulHaxx = stkName.equals("");
        if (isAlphabetical && !fulHaxx) {
            StakeholderLI newStkhldr = new StakeholderLI(stkName);
            stakeholderLIs.add(newStkhldr);
            treeViewUpdate(newStkhldr.getName());
        } else if (stkName == ""){
            AlertBox.display("Unvalid input!", "The name box is empty.");
        } else {
            AlertBox.display("Unvalid input!", "The name can only contain letters.");
        }
    }

    @FXML
    public void handleEditStkBtn(ActionEvent event) {

    }

    @FXML
    public void handleDeleteStkBtn(ActionEvent event) {
    }

    @FXML
    public void returnMarkedStk(Event event) {
        System.out.println("TEST");

        System.out.println();

    }

    /**
     * ToDo
     * Pekar mot MathBackend, efter detta generera grafer och presentera dom i en ny scene. aka alot...
     */
    /* public void generateBtnHandler(Event event) {
        // MathBackend();
    }

    */

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