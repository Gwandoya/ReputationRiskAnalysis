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
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Node;

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
    private TextField kpaTextField;
    @FXML
    private TextField stkTextField;
    @FXML
    private TextArea kpaTextArea;
    @FXML
    private TextArea stkTextArea;
    @FXML
    private TreeView kpaTreeView;
    @FXML
    private TreeView stkTreeView;

    private final Node rootIcon =
            new ImageView(new Image(getClass().getResourceAsStream("multiuser_16.png")));
    private final Image leafIcon =
            new Image(getClass().getResourceAsStream("user_16.png"));

    TreeItem<String> kpaRootItem = new TreeItem<String>("Key Performance Areas", rootIcon);
    TreeItem<String> stkRootItem = new TreeItem<String>("Stakeholders", rootIcon);

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
        treeViewInit();
        stage.hide();
        stage.show();

    }

    public void treeViewInit() {
        setUpInit();

        kpaRootItem.setExpanded(true);
        stkRootItem.setExpanded(true);

        kpaTreeView.setRoot(kpaRootItem);
        stkTreeView.setRoot(stkRootItem);
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

        Consumer<KPA> addKPA = (KPA k) -> kpaRootItem.getChildren().add(new TreeItem<String>(k.getName(), new ImageView(leafIcon)));
        Consumer<Stakeholder> addSTK = (Stakeholder s) -> stkRootItem.getChildren().add(new TreeItem<String>(s.getName(), new ImageView(leafIcon)));
        kpas.forEach(addKPA);
        stakeholders.forEach(addSTK);
    }

    @FXML
    public void handleAddStkBtn(ActionEvent event) throws InterruptedException {

        AddBoxController addBox = new AddBoxController();

        switch (tabIndex) {
            case 0 :
                addBox.display("KPA");
                if(addBox.getContinue())  addTreeItem(addBox.getName(), 0);
                break;
            case 1 :
                addBox.display("Stakeholder");
                if (addBox.getContinue())  addTreeItem(addBox.getName(), 1);
                break;
        }
    }

    @FXML
    public void handleEditStkBtn(ActionEvent event) {

    }

    @FXML
    public void handleDeleteStkBtn(ActionEvent event) {

        DeleteBoxController deleteBox = new DeleteBoxController();
        TreeItem c;
        switch (tabIndex) {
            case 0 :
                c = (TreeItem)kpaTreeView.getSelectionModel().getSelectedItem();
                if (!kpaRootItem.getChildren().isEmpty()) {
                    deleteBox.display("KPA", c.getValue().toString());
                    if (deleteBox.getDeleteConfirm()) {
                        Predicate<KPA> filter = (KPA k) -> k.getName().equals(c.getValue().toString());
                        kpas.removeIf(filter);
                        removeTreeItem(c);
                    }
                } else { AlertBox.display("Invalid Action","No selected item."); }
                break;

            case 1 :
                c = (TreeItem)stkTreeView.getSelectionModel().getSelectedItem();
                if (!stkRootItem.getChildren().isEmpty()){
                    deleteBox.display("Stakeholder", c.getValue().toString());
                    if (deleteBox.getDeleteConfirm()) {
                        Predicate<Stakeholder> filter = (Stakeholder s) -> s.getName().equals(c.getValue().toString());
                        stakeholders.removeIf(filter);
                        removeTreeItem(c);
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

    public void addTreeItem(String name, int i) {
        switch (i) {
            case 0:
                KPA newKPA = new KPA(name, "");
                kpas.add(newKPA);
                kpaRootItem.getChildren().add(new TreeItem<String>(name, new ImageView(leafIcon)));
                break;
            case 1:
                Stakeholder newStkhldr = new Stakeholder(name);
                stakeholders.add(newStkhldr);
                stkRootItem.getChildren().add(new TreeItem<String>(name, new ImageView(leafIcon)));
                break;
        }
    }

    public void removeTreeItem(TreeItem t) {
        boolean remove = t.getParent().getChildren().remove(t);
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

    @FXML
    public void kpaSave(ActionEvent event) {
        TreeItem c = (TreeItem)kpaTreeView.getSelectionModel().getSelectedItem();
        for(KPA k : kpas) {
            if(c.getValue().toString().equals(k.getName())) {
                k.setDesc(kpaTextArea.getText());
                k.setName(kpaTextField.getText());
                c.valueProperty().set(kpaTextField.getText());
            }
        }
    }

    @FXML
    public void stkSave(ActionEvent event) {
        Consumer<KPA> showKPA = (KPA k) -> System.out.println(k.getName() + ": " + k.getDesc());
        kpas.forEach(showKPA);
    }

    public void kpaTreeViewOnClick(Event event) {
        TreeItem c = (TreeItem)kpaTreeView.getSelectionModel().getSelectedItem();
        for(KPA k : kpas) {
            if (c.getValue().toString().equals(k.getName())) {
                kpaTextArea.setText(k.getDesc());
                kpaTextField.setText(k.getName());
            }
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
        private String name;
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

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}