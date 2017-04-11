package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
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

public class HomeController  {
    private Parent parent;
    private Scene scene;
    private Stage stage;

    private boolean markedStk = false;
    private int tabIndex = 0;

    @FXML
    private TreeView treeView;
    @FXML
    private Button addBtn;
    @FXML
    private Button editBtn;
    @FXML
    private Button deleteBtn;
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
    @FXML
    private Label statusLabel;
    @FXML
    private GridPane riskGridPane;

    private final Node kpaRootIcon =
            new ImageView(new Image(getClass().getResourceAsStream("multiuser_16.png")));
    private final Node stkRootIcon =
            new ImageView(new Image(getClass().getResourceAsStream("multiuser2_16.png")));
    private final Image leafIcon =
            new Image(getClass().getResourceAsStream("user_16.png"));

    TreeItem<String> kpaRootItem = new TreeItem<String>("Key Performance Areas", kpaRootIcon);
    TreeItem<String> stkRootItem = new TreeItem<String>("Stakeholders", stkRootIcon);

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

        Stakeholder s1 = new Stakeholder("Employees", "Employees have the highest potential impact on a company’s reputational capital. The quality of their work influences the quality of the products and services offered to customers.");
        Stakeholder s2 = new Stakeholder("Customers", "The principal promise from customers is loyalty that generates repeat purchases and recommendations.");
        Stakeholder s3 = new Stakeholder("Investors", "Investors enhance reputational capital when they speak favorably of a company, purchase shares, and instigate an upward spiral in the company’s market value.");
        Stakeholder s4 = new Stakeholder("Partners", "Citizenship programs can create opportunities for partnerships to develop as well as enhance the trust between existing partners by increasing familiarity and social integration.");
        Stakeholder s5 = new Stakeholder("Regulators", "Firms with strong regulatory relations may be able to shape zoning laws in their favor, reduce stringent regulations, and otherwise create favorable conditions for business.");
        Stakeholder s6 = new Stakeholder("Activists", "Purchases of many consumer products and services can be substantially swayed by the endorsements of activist groups. In a highly competitive marketplace, the added advantage of an activist group’s seal of approval may directly translate into improved sales.");
        Stakeholder s7 = new Stakeholder("Community", "Local communities may act to attract new investments or protect local companies that share their values and interests. Companies that participate in local communities benefit from community protection when threatened by insurgent groups of stakeholders.");
        Stakeholder s8 = new Stakeholder("Media", "The media magnify a company’s actions for other stakeholders, and so influence how they come to regard a company. The media also seek out attention-getting stories. To do so they selectively filter from a company’s initiatives those more likely to draw readers and viewers, potentially creating or destroying corporate reputations.");
        stakeholders.add(s1);
        stakeholders.add(s2);
        stakeholders.add(s3);
        stakeholders.add(s4);
        stakeholders.add(s5);
        stakeholders.add(s6);
        stakeholders.add(s7);
        stakeholders.add(s8);

        Consumer<KPA> addKPA = (KPA k) -> kpaRootItem.getChildren().add(new TreeItem<String>(k.getName(), new ImageView(leafIcon)));
        Consumer<Stakeholder> addSTK = (Stakeholder s) -> stkRootItem.getChildren().add(new TreeItem<String>(s.getName(), new ImageView(leafIcon)));
        kpas.forEach(addKPA);
        stakeholders.forEach(addSTK);
    }

    @FXML
    public void handleAddBtn(ActionEvent event) throws InterruptedException {

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
    public void handleEditBtn(ActionEvent event) {

    }

    @FXML
    public void handleDeleteBtn(ActionEvent event) {

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
        TreeItem c = (TreeItem)stkTreeView.getSelectionModel().getSelectedItem();
        for (Stakeholder s : stakeholders) {
            if (c.getValue().toString().equals(s.getName())) {
                s.setDesc(stkTextArea.getText());
                s.setName(stkTextField.getText());
                c.valueProperty().set(stkTextField.getText());
            }
        }
    }

    public void mainTabPaneClicked(Event event) {
        switch (mainTabPane.getSelectionModel().getSelectedIndex()) {
            case 0 :
                buttonModifier(addBtn, true, "Add New KPA");
                buttonModifier(editBtn, true, "Edit This KPA");
                buttonModifier(deleteBtn, true, "Delete This KPA");
                tabIndex = 0;
                break;
            case 1 :
                buttonModifier(addBtn, true, "Add New Stakeholder");
                buttonModifier(editBtn, true, "Edit This Stakeholder");
                buttonModifier(deleteBtn, true, "Delete This Stakeholder");
                tabIndex = 1;
                break;
            case 2 :
                buttonModifier(addBtn, false, "");
                buttonModifier(editBtn, false, "");
                buttonModifier(deleteBtn, false, "");
                tabIndex = 2;
                break;
            case 3 :
                buttonModifier(addBtn, false, "");
                buttonModifier(editBtn, false, "");
                buttonModifier(deleteBtn, false, "");
                tabIndex = 3;
                break;
            case 4 :
                buttonModifier(addBtn, false, "");
                buttonModifier(editBtn, false, "");
                buttonModifier(deleteBtn, false, "");
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
                Stakeholder newStkhldr = new Stakeholder(name, "");
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

    public void kpaTreeViewOnClick(Event event) {
        TreeItem c = (TreeItem)kpaTreeView.getSelectionModel().getSelectedItem();
        for (KPA k : kpas) {
            if (c.getValue().toString().equals(k.getName())) {
                kpaTextArea.setText(k.getDesc());
                kpaTextField.setText(k.getName());
            }
        }
    }

    public void stkTreeViewOnClick(Event event) {
        TreeItem c = (TreeItem)stkTreeView.getSelectionModel().getSelectedItem();
        for (Stakeholder s : stakeholders) {
            if (c.getValue().toString().equals(s.getName())) {
                stkTextArea.setText(s.getDesc());
                stkTextField.setText(s.getName());
            }
        }
    }

    public static void updateStatusLabel(String s) {
      //  statusLabel.setText(s);
    }

    public class Expectations {
        private String description;
        double weight;

        public Expectations(String description, int weight) {
            this.description = description;
            this.weight = weight;
        }

        public String getDescription () {return description;}

        public double getWeight() { return weight; }

        public void setDescription(String description) { this.description = description; }

        public void setWeight(Double weight) { this.weight = weight; }
    }

    public class Stakeholder {
        private String name;
        private String desc;


        public Stakeholder(String name, String desc) {
            this.name = new String(name);
            this.desc = desc;
        }

        public String getName() {
            return name;
        }



        public void setName(String name) {
            this.name = name;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
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