package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    @FXML
    private ScrollPane mainScrollPane;
    @FXML
    private TabPane kpaTabPane;
    @FXML
    private TabPane stkTabPane;



    ArrayList<Stakeholder> stakeholders = new ArrayList<Stakeholder>();
    ArrayList<StakeholderKPA> kpas = new ArrayList<StakeholderKPA>();
    // HashMap<S, >


    public HomeController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("home.fxml"));
        fxmlLoader.setController(this);
        try {
            parent = (Parent) fxmlLoader.load();
            scene = new Scene(parent, 886, 624);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void redirectHome(Stage stage) {
        this.stage = stage;
        stage.setTitle("Reputation Risk Analysis Tool");
        stage.setScene(scene);
        stage.setResizable(true);
        setUpInit();
        stage.hide();
        stage.show();

    }



    public void setUpInit() {
        Stakeholder one = new Stakeholder("Andreas");
        Stakeholder two = new Stakeholder("Benjamin");
        Stakeholder three = new Stakeholder("Anton");
        stakeholders.add(one);
        stakeholders.add(two);
        stakeholders.add(three);
    }



    public void stkPaneUpdate(String name) {
       // stkPane.
    }

    public void stkPaneCreate(String name) {

    }

    @FXML
    public void handleAddStkBtn(ActionEvent event) throws InterruptedException {

        AddBox addBox = new AddBox();
        addBox.display();
        boolean ok = addBox.getContinue();
        if (ok) {
            String newName = addBox.getName();
            boolean type = addBox.getType();

            boolean isAlphabetical = newName.chars().allMatch(Character::isLetter);
            boolean fulHaxx = newName.equals("");
            if (isAlphabetical && !fulHaxx) {
                if (type) {
                    StakeholderKPA newKPA = new StakeholderKPA(newName, "");
                    kpas.add(newKPA);
                    kpaTabPane.getTabs().add(new Tab(newName));
                } else if (!type) {
                    Stakeholder newStkhldr = new Stakeholder(newName);
                    stakeholders.add(newStkhldr);
                    stkTabPane.getTabs().add(new Tab(newName));
                }


            } else if (newName == "") {
                AlertBox.display("AlertBox1", "TestFact1");

            } else {
                AlertBox.display("Unvalid input!", "The name can only contain letters.");
            }
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

    public class Stakeholder {
        private final SimpleStringProperty name;

        public Stakeholder(String name) {
            this.name = new SimpleStringProperty(name);
        }

        public String getName() {
            return name.get();
        }

        public void setName(String nName) {
            name.set(nName);
        }
    }

    public class StakeholderKPA {
        private final String name;
        private String desc;


        public StakeholderKPA(String name, String desc) {
            this.name = new String(name);
            this.desc = new String(desc);
        }

        public String getName() {
            return name;
        }

        public String getDesc() {
            return desc;
        }
    }

    public class StakeholderExp{
        String name;
        public StakeholderExp(String name) {
            this.name = new String(name);
        }
    }


}