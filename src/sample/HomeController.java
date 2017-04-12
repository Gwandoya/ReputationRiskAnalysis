package sample;

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
import sun.reflect.generics.tree.Tree;

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
    @FXML
    private TreeView expStkTree;
    @FXML
    private TreeView expKpaTree;
    @FXML
    private TextField expWeightField;
    @FXML
    private TextArea expDescArea;
    @FXML
    private Label expHelpLabel;
    @FXML
    private Label stkHelpLabel;
    @FXML
    private Label kpaHelpLabel;
    @FXML
    private Button kpaSaveBtn;
    @FXML
    private Button stkSaveBtn;

    private final Node kpaRootIcon =
            new ImageView(new Image(getClass().getResourceAsStream("multiuser_16.png")));
    private final Node stkRootIcon =
            new ImageView(new Image(getClass().getResourceAsStream("multiuser2_16.png")));
    private final Node expecStkIcon =
            new ImageView(new Image(getClass().getResourceAsStream("multiuser3_16.png")));
    private final Node expecExpecIcon =
            new ImageView(new Image(getClass().getResourceAsStream("multiuser4_16.png")));
    private final Image leafIcon =
            new Image(getClass().getResourceAsStream("user_16.png"));

    TreeItem<String> kpaRootItem = new TreeItem<String>("Key Performance Areas", kpaRootIcon);
    TreeItem<String> stkRootItem = new TreeItem<String>("Stakeholders", stkRootIcon);
    TreeItem<String> expecStkItem = new TreeItem<String>("Stakeholders", expecStkIcon);
    TreeItem<String> expecExpexItem = new TreeItem<String>("Expectations", expecExpecIcon);

    ArrayList<Stakeholder> stakeholders = new ArrayList<Stakeholder>();
    ArrayList<KPA> kpas = new ArrayList<KPA>();
    ArrayList<Expectation> expectations = new ArrayList<>();
    

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

        expecExpexItem.setExpanded(true);
        expecStkItem.setExpanded(true);

        kpaTreeView.setRoot(kpaRootItem);
        stkTreeView.setRoot(stkRootItem);

        expStkTree.setRoot(expecStkItem);
        expKpaTree.setRoot(expecExpexItem);

        kpaTreeView.setShowRoot(false);
        stkTreeView.setShowRoot(false);
        expStkTree.setShowRoot(false);
        expKpaTree.setShowRoot(false);
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

        updateMode(null, 0, 1);
        updateMode(null, 1, 1);
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
        final TreeItem[] q = new TreeItem[1];
        switch (tabIndex) {
            case 0 :
                c = (TreeItem)kpaTreeView.getSelectionModel().getSelectedItem();
                /*
                Consumer<TreeItem> expTI = (TreeItem t) -> {
                    if(t.getValue().toString().equals(c.getValue().toString())) {
                         q[0] = t;
                    }
                };
                expKpaTree.getParent().getChildrenUnmodifiable
                */
                if (!kpaRootItem.getChildren().isEmpty()) {
                    deleteBox.display("KPA", c.getValue().toString());
                    if (deleteBox.getDeleteConfirm()) {
                        Predicate<KPA> filter = (KPA k) -> k.getName().equals(c.getValue().toString());
                        /*
                        Consumer<KPA> removeExp = (KPA k) -> {
                            if(k.getName().equals(c.getValue().toString())){
                                for (Stakeholder s : stakeholders) {
                                    k.removeExpectation(s);
                                }
                                for (Expectation e : expectations) {
                                    if (k.getName().equals(e.getKpa().getName()))
                                    expectations.remove(e);
                                }
                            }
                        };
                        kpas.forEach(removeExp);
                        */
                        kpas.removeIf(filter);
                        removeTreeItem(c);

                        /**
                         * DEBUG kod
                         */
                    /*
                        Consumer<Stakeholder> st = (Stakeholder s) -> System.out.println(s.getName());
                        Consumer<KPA> kp = (KPA k) -> System.out.println(k.getName());
                        Consumer<Expectation> ex = (Expectation e) -> System.out.println(e.getStakeholder().getName());
                        stakeholders.forEach(st);
                        kpas.forEach(kp);
                        expectations.forEach(ex);
                      */

                        
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
        kpas.stream().filter(k -> c.getValue().toString().equals(k.getName())).forEach(k -> {
            k.setDesc(kpaTextArea.getText());
            k.setName(kpaTextField.getText());
            c.valueProperty().set(kpaTextField.getText());
        });
    }

    @FXML
    public void stkSave(ActionEvent event) {
        TreeItem c = (TreeItem)stkTreeView.getSelectionModel().getSelectedItem();
        stakeholders.stream().filter(stakeholder -> c.getValue().toString().equals(stakeholder.getName())).forEach(stakeholder -> {
            stakeholder.setDesc(stkTextArea.getText());
            stakeholder.setName(stkTextField.getText());
            c.valueProperty().set(stkTextField.getText());
        });
    }

    @FXML
    public void kpaTreeViewOnClick(Event event) {
        TreeItem c = (TreeItem)kpaTreeView.getSelectionModel().getSelectedItem();
        updateMode(c.getValue().toString(), tabIndex, kpas.size());
        kpas.stream().filter(kpa -> c.getValue().toString().equals(kpa.getName())).forEach(kpa -> {
            kpaTextArea.setText(kpa.getDesc());
            kpaTextField.setText(kpa.getName());
        });
    }

    @FXML
    public void stkTreeViewOnClick(Event event) {
        TreeItem c = (TreeItem)stkTreeView.getSelectionModel().getSelectedItem();
        updateMode(c.getValue().toString(), tabIndex, stakeholders.size());
        stakeholders.stream().filter(stakeholder -> c.getValue().toString().equals(stakeholder.getName())).forEach(stakeholder -> {
            stkTextArea.setText(stakeholder.getDesc());
            stkTextField.setText(stakeholder.getName());
        });
    }

    @FXML
    public void stkExpTreeViewOnClick(Event event) {
        TreeItem c = (TreeItem)expStkTree.getSelectionModel().getSelectedItem();
        TreeItem v = (TreeItem)expKpaTree.getSelectionModel().getSelectedItem();
        UpdateExpectations(c == null ? null : c.getValue().toString(), v == null ? null : v.getValue().toString());
    }

    @FXML
    public void expExpTreeViewOnClick(Event event) {
        TreeItem c = (TreeItem)expStkTree.getSelectionModel().getSelectedItem();
        TreeItem v = (TreeItem)expKpaTree.getSelectionModel().getSelectedItem();
        UpdateExpectations(c.getValue().toString(), v.getValue().toString());
    }

    @FXML
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
                expectationSetUp();
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

    @FXML
    public void expSaveBtnOnClick(ActionEvent event) {
        TreeItem c = (TreeItem)expStkTree.getSelectionModel().getSelectedItem();
        TreeItem t = (TreeItem) expKpaTree.getSelectionModel().getSelectedItem();
        String stkName = c.getValue().toString();
        String kpaName = t.getValue().toString();
        KPA kpa = kpas.stream().filter(k -> kpaName.equals(k.getName()))
                .findFirst().orElse(null);
        Stakeholder stk = stakeholders.stream().filter(s -> stkName.equals(s.getName()))
                .findFirst().orElse(null);
        Expectation exp = new Expectation(expDescArea.getText(), Integer.parseInt(expWeightField.getText()), kpa, stk);
        kpa.removeExpectationIfPresent(stk);
        kpa.addExpectation(exp);
        expectations.add(exp);
    }

    public Expectation newExpectation(String stakeholderString, String kpaString) {
        KPA kpa = kpas.stream().filter(k -> kpaString.equals(k.getName()))
                .findFirst().get();
        Stakeholder stk = stakeholders.stream().filter(s -> stakeholderString.equals(s.getName()))
                .findFirst().get();
        return new Expectation("Hej", 0, kpa, stk);
    }

    public void updateMode(String c, int index, int listSize) {
        if (c == null) {
            switch (index) {
                case 0:
                    if (listSize == 0) {
                        kpaHelpLabel.setText("Add a key performance area");
                    } else {
                        kpaHelpLabel.setText("Select a key performance area");
                    }
                    kpaTextArea.setText("");
                    kpaTextField.setText("");
                    kpaTextField.setDisable(true);
                    kpaTextArea.setDisable(true);
                    kpaSaveBtn.setDisable(true);
                    break;
                case 1:
                    if (listSize == 0) {
                        stkHelpLabel.setText("Add a stakeholder");
                    } else {
                        stkHelpLabel.setText("Select a stakeholder");
                    }
                    stkTextArea.setText("");
                    stkTextField.setText("");
                    stkTextArea.setDisable(true);
                    stkTextField.setDisable(true);
                    stkSaveBtn.setDisable(true);
                    break;
            }
        } else {
            switch (index) {
                case 0:
                    kpaHelpLabel.setText("Enter a description for: " + c);
                    kpaTextField.setDisable(false);
                    kpaTextArea.setDisable(false);
                    kpaSaveBtn.setDisable(false);
                    break;
                case 1:
                    stkHelpLabel.setText("Enter a description for: " + c);
                    stkTextArea.setDisable(false);
                    stkTextField.setDisable(false);
                    stkSaveBtn.setDisable(false);
                    break;
            }
        }
    }

    public void UpdateExpectations(String c, String v) {
        if (c == null) {
            expHelpLabel.setText("Select a stakeholder");
            expWeightField.setText("");
            expKpaTree.setDisable(true);
            expWeightField.setDisable(true);
            expDescArea.setDisable(true);
        } else if (v == null) {
            expHelpLabel.setText("Select a corresponding key performance area");
            expWeightField.setText("");
            expKpaTree.setDisable(false);
            expWeightField.setDisable(true);
            expDescArea.setDisable(true);
        } else {
            Expectation expectation = expectations.stream().filter(exp -> c.equals(exp.getStakeholder().getName())
                    && v.equals(exp.getKpa().getName()))
                    .findFirst().orElse(newExpectation(c, v));
            expWeightField.setText(expectation.getWeight()+"");
            expDescArea.setText(expectation.getDescription());
            expKpaTree.setDisable(false);
            expWeightField.setDisable(false);
            expDescArea.setDisable(false);
            expHelpLabel.setText(c + " expectation regarding " + v);
        }
    }

    public void expectationSetUp() {
        for (Stakeholder s : stakeholders) {
            if (!containsInTree(s.getName(), 0)) {
                addTreeItem(s.getName(), 2);
            }
        }
        for (KPA k : kpas) {
            if (!containsInTree(k.getName(), 1)) {
                addTreeItem(k.getName(), 3);
            }
        }
    }

    private boolean containsInTree(String s, int i) {
        switch (i) {
            case 0 :
                for (TreeItem t : expecStkItem.getChildren()) {
                    if (s.equals(t.getValue().toString())) {
                        return true;
                    }
                }
                break;
            case 1 :
                for (TreeItem t : expecExpexItem.getChildren()) {
                    if (s.equals(t.getValue().toString())) {
                        return true;
                    }
                }
                break;
        }
        return false;
    }

    public void addTreeItem(String name, int i) {
        switch (i) {
            case 0:
                KPA newKPA = new KPA(name, "");
                kpas.add(newKPA);
                kpaRootItem.getChildren().add(new TreeItem<String>(name, new ImageView(leafIcon)));
                updateMode(null, tabIndex, kpas.size());
                break;
            case 1:
                Stakeholder newStkhldr = new Stakeholder(name, "");
                stakeholders.add(newStkhldr);
                stkRootItem.getChildren().add(new TreeItem<String>(name, new ImageView(leafIcon)));
                updateMode(null, tabIndex, stakeholders.size());
                break;
            case 2:
                expecStkItem.getChildren().add(new TreeItem<String>(name, new ImageView(leafIcon)));
                break;
            case 3:
                expecExpexItem.getChildren().add(new TreeItem<String>(name, new ImageView(leafIcon)));
                break;
        }
    }

    public void removeTreeItem(TreeItem t) {
        boolean remove = t.getParent().getChildren().remove(t);
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

    public void updateStatusLabel(String s) {
      //  statusLabel.setText(s);
    }

    public class Expectation {
        private String description;
        double weight;
        KPA kpa;
        Stakeholder stakeholder;

        public Expectation(String description, int weight, KPA kpa, Stakeholder stakeholder) {
            this.description = description;
            this.weight = weight;
            this.kpa = kpa;
            this.stakeholder = stakeholder;
        }

        public String getDescription () {return description;}

        public double getWeight() { return weight; }

        public KPA getKpa () { return kpa; }

        public Stakeholder getStakeholder () { return stakeholder; }

        public void setDescription(String description) { this.description = description; }

        public void setWeight(Double weight) { this.weight = weight; }
    }

    public class Stakeholder {
        private String name;
        private String desc;
        HashMap<KPA, Expectation> expectationHashMap = new HashMap<>();

        public Stakeholder(String name, String desc) {
            this.name = new String(name);
            this.desc = desc;
        }

        public Expectation getExpectation(Stakeholder stakeholder) {
            return expectationHashMap.get(stakeholder);
        }

        public Collection<Expectation> getExpectations() {
            return expectationHashMap.values();
        }

        public void addExpectation(Expectation expectation) {
            expectationHashMap.put(expectation.getKpa(), expectation);
            expectation.getKpa().addExpectationIfNotPresent(expectation);
        }

        public void addExpectationIfNotPresent(Expectation expectation) {
            if(!expectationHashMap.containsValue(expectation)) {
                addExpectation(expectation);
            }
        }

        public void removeExpectation(Expectation expectation) {
            expectations.remove(expectation);
            expectationHashMap.remove(expectation.getKpa())
                    .getStakeholder()
                    .removeExpectationIfPresent(expectation);
        }

        public void removeExpectation(KPA kpa) {
            removeExpectation(expectationHashMap.get(kpa));
        }

        public void removeExpectationIfPresent(Expectation expectation) {
            removeExpectationIfPresent(expectation.getKpa());
        }

        public void removeExpectationIfPresent(KPA kpa) {
            if (expectationHashMap.containsKey(kpa))
                expectationHashMap.remove(kpa);
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
        HashMap<Stakeholder, Expectation> expectationHashMap = new HashMap<>();

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

        public Expectation getExpectation(Stakeholder stakeholder) {
            return expectationHashMap.get(stakeholder);
        }

        public Collection<Expectation> getExpectations() {
            return expectationHashMap.values();
        }

        public void addExpectation(Expectation expectation) {
            expectationHashMap.put(expectation.getStakeholder(), expectation);
            expectation.getStakeholder().addExpectationIfNotPresent(expectation);
        }

        public void addExpectationIfNotPresent(Expectation expectation) {
            if(!expectationHashMap.containsValue(expectation)) {
                addExpectation(expectation);
            }
        }

        public void removeExpectation(Expectation expectation) {
            expectations.remove(expectation);
            expectationHashMap.remove(expectation.getStakeholder())
                    .getKpa()
                    .removeExpectationIfPresent(expectation);
        }

        public void removeExpectation(Stakeholder stakeholder) {
            removeExpectation(expectationHashMap.get(stakeholder));
        }

        public void removeExpectationIfPresent(Expectation expectation) {
            removeExpectationIfPresent(expectation.getStakeholder());
        }

        public void removeExpectationIfPresent(Stakeholder stk) {
            if (expectationHashMap.containsKey(stk))
                expectationHashMap.remove(stk);
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}