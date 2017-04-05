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
import java.util.function.Consumer;
import java.util.function.Predicate;

public class HomeController {
    private Parent parent;
    private Scene scene;
    private Stage stage;

    private boolean markedStk = false;
    private int tabIndex = 0;

    @FXML
    private TreeView treeView;
    @FXML
    private Button addStkBtn;
    @FXML
    private Button editStkBtn;
    @FXML
    private Button deleteStkBtn;
    @FXML
    private TextField stkNameTextView;
    @FXML
    private Label wrongNameLbl;
    @FXML
    private ScrollPane mainScrollPane;
    @FXML
    private TabPane mainTabPane;
    @FXML
    private TabPane kpaTabPane;
    @FXML
    private TabPane stkTabPane;

    ArrayList<Stakeholder> stakeholders = new ArrayList<Stakeholder>();
    ArrayList<KPA> kpas = new ArrayList<KPA>();

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
        KPA x = new KPA("ALPHA","");
        KPA y = new KPA("BETA","");
        KPA z = new KPA("CITRON","");
        kpas.add(x);
        kpas.add(y);
        kpas.add(z);

        Stakeholder one = new Stakeholder("Andreas");
        Stakeholder two = new Stakeholder("Benjamin");
        Stakeholder three = new Stakeholder("Anton");
        stakeholders.add(one);
        stakeholders.add(two);
        stakeholders.add(three);

        Consumer<KPA> addKPA = (KPA k) -> kpaTabPane.getTabs().add(new Tab(k.getName()));
        Consumer<Stakeholder> addSTK = (Stakeholder s) -> stkTabPane.getTabs().add(new Tab(s.getName()));
        kpas.forEach(addKPA);
        stakeholders.forEach(addSTK);
    }

    @FXML
    public void handleAddStkBtn(ActionEvent event) throws InterruptedException {

        AddBoxController addBox = new AddBoxController();

        switch (tabIndex) {
            case 0 :
                addBox.display("KPA");
                if(addBox.getContinue()) addToTab(addBox.getName(), 0);
                break;
            case 1 :
                addBox.display("Stakeholder");
                if (addBox.getContinue()) addToTab(addBox.getName(), 1);
                break;
        }

    }

    @FXML
    public void handleEditStkBtn(ActionEvent event) {

    }

    @FXML
    public void handleDeleteStkBtn(ActionEvent event) {
        DeleteBoxController deleteBox = new DeleteBoxController();
        switch (tabIndex) {
            case 0 :
                if (!kpaTabPane.getTabs().isEmpty()){
                    deleteBox.display("KPA",kpaTabPane.getSelectionModel().getSelectedItem().getText());
                    if (deleteBox.getDeleteConfirm()) {
                        Predicate<KPA> filter = (KPA k) -> k.getName().equals(kpaTabPane.getSelectionModel().getSelectedItem().getText());
                        kpas.removeIf(filter);
                        kpaTabPane.getTabs().remove(kpaTabPane.getSelectionModel().getSelectedIndex());
                    }
                } else { AlertBox.display("Invalid Action","No selected item."); }
                break;
            case 1 :
                if (!stkTabPane.getTabs().isEmpty()){
                    deleteBox.display("Stakeholder", stkTabPane.getSelectionModel().getSelectedItem().getText());
                    if (deleteBox.getDeleteConfirm()) {
                        Predicate<Stakeholder> filter = (Stakeholder s) -> s.getName().equals(stkTabPane.getSelectionModel().getSelectedItem().getText());
                        stakeholders.removeIf(filter);
                        stkTabPane.getTabs().remove(stkTabPane.getSelectionModel().getSelectedIndex());
                    }
                } else { AlertBox.display("Invalid Action","No selected item."); }
                break;
        }
    }

    public void mainTabPaneClicked(Event event) {
        switch (mainTabPane.getSelectionModel().getSelectedIndex()) {
            case 0 :
                buttonModifier(addStkBtn, true, "Add New KPA");
                buttonModifier(editStkBtn, true, "Edit This KPA");
                buttonModifier(deleteStkBtn, true, "Delete This KPA");
                tabIndex = 0;
                break;
            case 1 :
                buttonModifier(addStkBtn, true, "Add New Stakeholder");
                buttonModifier(editStkBtn, true, "Edit This Stakeholder");
                buttonModifier(deleteStkBtn, true, "Delete This Stakeholder");
                tabIndex = 1;
                break;
            case 2 :
                buttonModifier(addStkBtn, false, "");
                buttonModifier(editStkBtn, false, "");
                buttonModifier(deleteStkBtn, false, "");
                tabIndex = 2;
                break;
            case 3 :
                buttonModifier(addStkBtn, false, "");
                buttonModifier(editStkBtn, false, "");
                buttonModifier(deleteStkBtn, false, "");
                tabIndex = 3;
                break;
            case 4 :
                buttonModifier(addStkBtn, false, "");
                buttonModifier(editStkBtn, false, "");
                buttonModifier(deleteStkBtn, false, "");
                tabIndex = 4;
                break;
        }


    }

    public void addToTab (String name, int i) {
        switch (i) {
            case 0:
                KPA newKPA = new KPA(name, "");
                kpas.add(newKPA);
                kpaTabPane.getTabs().add(new Tab(name));
                break;
            case 1:
                Stakeholder newStkhldr = new Stakeholder(name);
                stakeholders.add(newStkhldr);
                stkTabPane.getTabs().add(new Tab(name));
                break;
        }
    }

    public void delete (String name, int i, int j) {

    }

    public void buttonModifier(Button btn, boolean b, String s) {
        if(b) {
            btn.setText(s);
            btn.setDisable(false);
            btn.setVisible(true);
        } else if(!b){
            btn.setText(s);
            btn.setDisable(true);
            btn.setVisible(false);
        }
    }

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

    public class KPA {
        private final String name;
        private String desc;


        public KPA(String name, String desc) {
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

}