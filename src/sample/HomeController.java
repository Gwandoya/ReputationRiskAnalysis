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

import java.awt.datatransfer.FlavorEvent;
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
    private double stkMaxWeight = 100.0;
    private static int gpIndex = 1;

    @FXML
    private TreeView treeView;
    @FXML
    private Button addBtn;
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
    @FXML
    private Label weightValueLabel;
    @FXML
    private Button stkWeightSaveButton;
    @FXML
    private TextField stkWeightTextField;
    @FXML
    private TreeView stkWeightTreeView;
    @FXML
    private Label stkWeightViewHelpLabel;
    @FXML
    private Label stkWeightMaxValue;
    @FXML
    private GridPane roGP;
    @FXML
    private TreeView roTreeView;
    @FXML
    private Button roSaveBtn;

    private final Node kpaRootIcon =
            new ImageView(new Image(getClass().getResourceAsStream("multiuser_16.png")));
    private final Node stkRootIcon =
            new ImageView(new Image(getClass().getResourceAsStream("multiuser2_16.png")));
    private final Node expecStkIcon =
            new ImageView(new Image(getClass().getResourceAsStream("multiuser3_16.png")));
    private final Node expecExpecIcon =
            new ImageView(new Image(getClass().getResourceAsStream("multiuser4_16.png")));
    private final Node stkWeightIcon =
            new ImageView(new Image(getClass().getResourceAsStream("multiuser5_16.png")));
    private final Node roIcon =
            new ImageView(new Image(getClass().getResourceAsStream("multiuser6_16.png")));

    private final Image leafIcon =
            new Image(getClass().getResourceAsStream("user_16.png"));
    private final Image leafIcon2 =
            new Image(getClass().getResourceAsStream("multiuser_16.png"));
    private final Image leafIconG =
            new Image(getClass().getResourceAsStream("userG_16.png"));

    TreeItem<String> kpaRootItem = new TreeItem<String>("Key Performance Areas", kpaRootIcon);
    TreeItem<String> stkRootItem = new TreeItem<String>("Stakeholders", stkRootIcon);
    TreeItem<String> expecStkItem = new TreeItem<String>("Stakeholders", expecStkIcon);
    TreeItem<String> expecExpexItem = new TreeItem<String>("Expectations", expecExpecIcon);
    TreeItem<String> stkWeightItem = new TreeItem<String>("Stakeholders", stkWeightIcon);
    TreeItem<String> roItem = new TreeItem<String>("Stakeholders", roIcon);

    ArrayList<Stakeholder> stakeholders = new ArrayList<Stakeholder>();
    ArrayList<KPA> kpas = new ArrayList<KPA>();
    ArrayList<Expectation> expectations = new ArrayList<>();
    ArrayList<RO> ros = new ArrayList<>();
    //HashMap<Expectation, Integer> rOHMap= new HashMap<>();

    /**
     * Main SetUps
     */

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
        stkWeightItem.setExpanded(true);
        roItem.setExpanded(true);

        kpaTreeView.setRoot(kpaRootItem);
        stkTreeView.setRoot(stkRootItem);
        expStkTree.setRoot(expecStkItem);
        expKpaTree.setRoot(expecExpexItem);
        stkWeightTreeView.setRoot(stkWeightItem);
        roTreeView.setRoot(roItem);

        kpaTreeView.setShowRoot(false);
        stkTreeView.setShowRoot(false);
        expStkTree.setShowRoot(false);
        expKpaTree.setShowRoot(false);
        stkWeightTreeView.setShowRoot(false);
        roTreeView.setShowRoot(false);
    }

    public void setUpInit() {
        KPA x = new KPA(this, "ALPHA", "");
        KPA y = new KPA(this, "BETA", "");
        KPA z = new KPA(this, "CITRON", "");
        kpas.add(x);
        kpas.add(y);
        kpas.add(z);

        Stakeholder s1 = new Stakeholder(this, "Employees", "Employees have the highest potential impact on a company’s reputational capital. The quality of their work influences the quality of the products and services offered to customers.");
        Stakeholder s2 = new Stakeholder(this, "Customers", "The principal promise from customers is loyalty that generates repeat purchases and recommendations.");
        Stakeholder s3 = new Stakeholder(this, "Investors", "Investors enhance reputational capital when they speak favorably of a company, purchase shares, and instigate an upward spiral in the company’s market value.");
        Stakeholder s4 = new Stakeholder(this, "Partners", "Citizenship programs can create opportunities for partnerships to develop as well as enhance the trust between existing partners by increasing familiarity and social integration.");
        Stakeholder s5 = new Stakeholder(this, "Regulators", "Firms with strong regulatory relations may be able to shape zoning laws in their favor, reduce stringent regulations, and otherwise create favorable conditions for business.");
        Stakeholder s6 = new Stakeholder(this, "Activists", "Purchases of many consumer products and services can be substantially swayed by the endorsements of activist groups. In a highly competitive marketplace, the added advantage of an activist group’s seal of approval may directly translate into improved sales.");
        Stakeholder s7 = new Stakeholder(this, "Community", "Local communities may act to attract new investments or protect local companies that share their values and interests. Companies that participate in local communities benefit from community protection when threatened by insurgent groups of stakeholders.");
        Stakeholder s8 = new Stakeholder(this, "Media", "The media magnify a company’s actions for other stakeholders, and so influence how they come to regard a company. The media also seek out attention-getting stories. To do so they selectively filter from a company’s initiatives those more likely to draw readers and viewers, potentially creating or destroying corporate reputations.");
        stakeholders.add(s1);
        stakeholders.add(s2);
        stakeholders.add(s3);
        stakeholders.add(s4);
        stakeholders.add(s5);
        stakeholders.add(s6);
        stakeholders.add(s7);
        stakeholders.add(s8);

        Expectation e1 = new Expectation("Lorem Ipmsum Lorem Ipmsum Lorem Ipmsum Lorem Ipmsum Lorem Ipmsum Lorem Ipmsum Lorem Ipmsum ", 17.1, x, s1);
        Expectation e2 = new Expectation("Lorem Ipmsum Lorem Ipmsum Lorem Ipmsum Lorem Ipmsum Lorem Ipmsum Lorem Ipmsum Lorem Ipmsum ", 17.1, x, s2);
        Expectation e3 = new Expectation("Lorem Ipmsum Lorem Ipmsum Lorem Ipmsum Lorem Ipmsum Lorem Ipmsum Lorem Ipmsum Lorem Ipmsum ", 17.1, x, s3);
        Expectation e4 = new Expectation("Lorem Ipmsum Lorem Ipmsum Lorem Ipmsum Lorem Ipmsum Lorem Ipmsum Lorem Ipmsum Lorem Ipmsum ", 17.1, x, s4);
        Expectation e5 = new Expectation("Lorem Ipmsum Lorem Ipmsum Lorem Ipmsum Lorem Ipmsum Lorem Ipmsum Lorem Ipmsum Lorem Ipmsum ", 17.1, x, s5);
        Expectation e6 = new Expectation("Lorem Ipmsum Lorem Ipmsum Lorem Ipmsum Lorem Ipmsum Lorem Ipmsum Lorem Ipmsum Lorem Ipmsum ", 17.1, x, s6);

        expectations.add(e1);
        expectations.add(e2);
        expectations.add(e3);
        expectations.add(e4);
        expectations.add(e5);
        expectations.add(e6);

        Consumer<KPA> addKPA = (KPA k) -> kpaRootItem.getChildren().add(new TreeItem<String>(k.getName(), new ImageView(leafIcon)));
        Consumer<Stakeholder> addSTK = (Stakeholder s) -> stkRootItem.getChildren().add(new TreeItem<String>(s.getName(), new ImageView(leafIcon2)));

        kpas.forEach(addKPA);
        stakeholders.forEach(addSTK);


        updateMode(null, 0, 1);
        updateMode(null, 1, 1);
    }


    /**
     * MainTab & TreeView onClick
     */

    @FXML
    public void mainTabPaneClicked(Event event) {
        switch (mainTabPane.getSelectionModel().getSelectedIndex()) {
            case 0:
                buttonModifier(addBtn, true, "Add New KPA");
                buttonModifier(deleteBtn, true, "Delete This KPA");
                tabIndex = 0;
                break;
            case 1:
                buttonModifier(addBtn, true, "Add New Stakeholder");
                buttonModifier(deleteBtn, true, "Delete This Stakeholder");
                tabIndex = 1;
                break;
            case 2:
                buttonModifier(addBtn, false, "");
                buttonModifier(deleteBtn, false, "");
                tabIndex = 2;
                expectationSetUp();
                break;
            case 3:
                buttonModifier(addBtn, false, "");
                buttonModifier(deleteBtn, false, "");
                tabIndex = 3;
                stkWeightSetUp();
                break;
            case 4:
                buttonModifier(addBtn, false, "");
                buttonModifier(deleteBtn, false, "");
                tabIndex = 4;
                roSetUp();
                break;
            case 5:
                buttonModifier(addBtn, false, "");
                buttonModifier(deleteBtn, false, "");
                tabIndex = 5;
        }
    }

    @FXML
    public void kpaTreeViewOnClick(Event event) {
        TreeItem c = (TreeItem) kpaTreeView.getSelectionModel().getSelectedItem();
        if (kpas.size() > 0) {
            updateMode(stringSplitter(c.getValue().toString()), tabIndex, kpas.size());
        } else {
            updateMode(null, tabIndex, stakeholders.size());
        }
        kpas.stream().filter(kpa -> stringSplitter(c.getValue().toString()).equals(kpa.getName())).forEach(kpa -> {
            kpaTextArea.setText(kpa.getDesc());
            kpaTextField.setText(kpa.getName());
        });
    }

    @FXML
    public void stkTreeViewOnClick(Event event) {
        TreeItem c = (TreeItem) stkTreeView.getSelectionModel().getSelectedItem();
        if (stakeholders.size() > 0) {
            updateMode(stringSplitter(c.getValue().toString()), tabIndex, stakeholders.size());
        } else {
            updateMode(null, tabIndex, stakeholders.size());
        }
        stakeholders.stream().filter(stakeholder -> stringSplitter(c.getValue().toString()).equals(stakeholder.getName())).forEach(stakeholder -> {
            stkTextArea.setText(stakeholder.getDesc());
            stkTextField.setText(stakeholder.getName());
        });
    }

    @FXML
    public void stkExpTreeViewOnClick(Event event) {
        TreeItem c = (TreeItem) expStkTree.getSelectionModel().getSelectedItem();
        TreeItem v = (TreeItem) expKpaTree.getSelectionModel().getSelectedItem();
        updateExpKpaVP(stringSplitter(c.getValue().toString()));
        updateExpectations(c == null ? null : stringSplitter(c.getValue().toString()), v == null ? null : stringSplitter(v.getValue().toString()));

    }

    @FXML
    public void expExpTreeViewOnClick(Event event) {
        TreeItem c = (TreeItem) expStkTree.getSelectionModel().getSelectedItem();
        TreeItem v = (TreeItem) expKpaTree.getSelectionModel().getSelectedItem();
        updateExpectations(stringSplitter(stringSplitter(c.getValue().toString())), stringSplitter(v.getValue().toString()));
    }

    @FXML
    public void stkWeightTreeViewOnClick(Event event) {
        TreeItem c = (TreeItem) stkWeightTreeView.getSelectionModel().getSelectedItem();
        updateStkWeightView(stringSplitter(c.getValue().toString()));
    }

    public void roTreeViewOnClick(Event event) {
        TreeItem c = (TreeItem) roTreeView.getSelectionModel().getSelectedItem();
        updateROView(stringSplitter(c.getValue().toString()));
    }

    /**
     * Regular Buttons
     */

    @FXML
    public void handleAddBtn(ActionEvent event) throws InterruptedException {

        AddBoxController addBox = new AddBoxController();

        switch (tabIndex) {
            case 0:
                addBox.display("KPA");
                if (addBox.getContinue()) addTreeItem(addBox.getName(), 0);
                break;
            case 1:
                addBox.display("Stakeholder");
                if (addBox.getContinue()) addTreeItem(addBox.getName(), 1);
                break;
        }
    }

    @FXML
    public void handleDeleteBtn(ActionEvent event) {

        DeleteBoxController deleteBox = new DeleteBoxController();
        TreeItem c;
        TreeItem q = null;
        TreeItem z = null;
        boolean remove = false;
        switch (tabIndex) {
            case 0:
                c = (TreeItem) kpaTreeView.getSelectionModel().getSelectedItem();

                for (TreeItem<String> t : expecExpexItem.getChildren()) {
                    if (t.getValue().toString().equals(c.getValue().toString())) {
                        q = t;
                        break;
                    }
                }

                if (!kpaRootItem.getChildren().isEmpty()) {
                    deleteBox.display("Key performance area: ", stringSplitter(c.getValue().toString()));
                    if (deleteBox.getDeleteConfirm()) {
                        Predicate<KPA> filterTreeView = (KPA k) -> k.getName().equals(stringSplitter(c.getValue().toString()));
                        Predicate<Expectation> filterExpectation = (Expectation e) -> e.getKpa().getName().equals(stringSplitter(c.getValue().toString()));
                        expectations.removeIf(filterExpectation);
                        kpas.removeIf(filterTreeView);
                        removeTreeItem(c);
                        removeTreeItem(q);
                    }
                } else {
                    AlertBox.display("Invalid Action", "No selected item.");
                }
                break;

            case 1:
                c = (TreeItem) stkTreeView.getSelectionModel().getSelectedItem();

                for (TreeItem<String> t : expecStkItem.getChildren()) {
                    if (stringSplitter(t.getValue().toString()).equals(stringSplitter(c.getValue().toString()))) {
                        q = t;
                        break;
                    }
                }

                for (TreeItem<String> t : stkWeightItem.getChildren()) {
                    if (stringSplitter(t.getValue().toString()).equals(stringSplitter(c.getValue().toString()))) {
                        z = t;
                    }
                }

                if (!stkRootItem.getChildren().isEmpty()) {
                    deleteBox.display("Stakeholder: ", stringSplitter(c.getValue().toString()));
                    if (deleteBox.getDeleteConfirm()) {
                        Stakeholder stk = stakeholders.stream().filter(s -> s.getName().equals(stringSplitter(c.getValue().toString()))).findFirst().orElse(null);
                        Expectation exp = expectations.stream().filter(e -> e.getStakeholder().getName().equals(stringSplitter(c.getValue().toString()))).findFirst().orElse(null);
                        stkMaxWeight = stkMaxWeight + stk.getStkValue();
                        updateStkWeightView(null);
                        stakeholders.remove(stk);
                        expectations.remove(exp);
                        removeTreeItem(c);
                        removeTreeItem(q);
                        removeTreeItem(z);
                    }
                } else {
                    AlertBox.display("Invalid Action", "No selected item.");
                }
                break;
        }

    }

    /**
     * Save Buttons
     */

    @FXML
    public void kpaSave(ActionEvent event) {
        TreeItem c = (TreeItem) kpaTreeView.getSelectionModel().getSelectedItem();
        kpas.stream().filter(k ->
                stringSplitter(c.getValue().toString()).equals(k.getName())).forEach(k ->
            {
                k.setDesc(kpaTextArea.getText());
                k.setName(kpaTextField.getText());
                c.valueProperty().set(kpaTextField.getText());
            });
    }

    @FXML
    public void stkSave(ActionEvent event) {
        TreeItem c = (TreeItem) stkTreeView.getSelectionModel().getSelectedItem();
        stakeholders.stream().filter(stakeholder ->
                stringSplitter(c.getValue().toString()).equals(stakeholder.getName())).forEach(stakeholder ->
            {
                stakeholder.setDesc(stkTextArea.getText());
                stakeholder.setName(stkTextField.getText());
                c.valueProperty().set(stkTextField.getText());
            });
    }

    @FXML
    public void expSaveBtnOnClick(ActionEvent event) {
        TreeItem c = (TreeItem) expStkTree.getSelectionModel().getSelectedItem();
        TreeItem t = (TreeItem) expKpaTree.getSelectionModel().getSelectedItem();
        KPA kpa = kpas.stream().filter(k -> stringSplitter(t.getValue().toString()).equals(k.getName()))
                .findFirst().orElse(null);
        Stakeholder stk = stakeholders.stream().filter(s -> stringSplitter(c.getValue().toString()).equals(s.getName()))
                .findFirst().orElse(null);
        if (editWeight(stk, kpa, Double.parseDouble(expWeightField.getText()), tabIndex)) {
            Expectation exp = new Expectation(expDescArea.getText(), Double.parseDouble(expWeightField.getText()), kpa, stk);
            kpa.removeExpectationIfPresent(stk);
            kpa.addExpectation(exp);
            expectations.add(exp);
            if (stk.getMaxValue() == 0.0 && kpa.getExpectation(stk).getWeight() > 0) {
                c.graphicProperty().set(new ImageView(leafIconG));
            }
        }
        updateExpKpaVP(stk.getName());
    }

    @FXML
    public void saveStkWeightClicked(ActionEvent event) {
        TreeItem c = (TreeItem) stkWeightTreeView.getSelectionModel().getSelectedItem();
        Stakeholder stk = stakeholders.stream().filter(s ->
                s.getName().equals(stringSplitter(c.getValue().toString()))).findFirst().orElse(null);
        if (editWeight(stk, null, Double.parseDouble(stkWeightTextField.getText()), tabIndex)) {
            String s = stringSplitter(c.getValue().toString());
            c.valueProperty().set(s + " - " + stk.getStkValue() + "%");
        }
    }

    public void roSaveAction(ActionEvent event) {
        TreeItem c = (TreeItem) roTreeView.getSelectionModel().getSelectedItem();

       /**
        * Lägg till så att man kan hämta värde från specifik rad i roGP.
        */
        /*
        ros.stream().filter(r ->
                stringSplitter(c.getValue().toString()).equals(r.getExpectation().getStakeholder().getName())).forEach(r ->
        {
            r.setRisk();

        });
        */
    }


    /**
     * Hjälpmetoder
     */

    public String stringSplitter(String s) {
        String ans = "";
        for (char c : s.toCharArray()) {
            if (c != ' ') ans = ans + c;
            else break;
        }
        return ans;
    }

    public static void deleteRow(GridPane grid, final int row) {
        Set<Node> deleteNodes = new HashSet<>();
        for (Node child : grid.getChildren()) {
            // get index from child
            Integer rowIndex = GridPane.getRowIndex(child);
            // handle null values for index=0
            int r = rowIndex == null ? 0 : rowIndex;
            if (r > row) {
                // decrement rows for rows after the deleted row
                GridPane.setRowIndex(child, r - 1);
            } else if (r == row) {
                // collect matching rows for deletion
                deleteNodes.add(child);
            }
        }
        // remove nodes from row
        grid.getChildren().removeAll(deleteNodes);
    }

    public boolean editWeight(Stakeholder s, KPA k, double nWeight, int tabIndex) {
        switch (tabIndex) {
            case 2:
                double d = s.getMaxValue();
                double a = 0;
                if (k.getExpectation(s) != null) {
                    a = k.getExpectation(s).getWeight();
                }
                if (d + a - nWeight >= 0) {
                    s.setMaxValue(d + a - nWeight);
                    weightValueLabel.setText("Remaining weight to distribute: " + s.getMaxValue());
                    return true;
                } else {
                    new AlertBox();
                    AlertBox.display("Stakeholder expectation weight - Overflow", "You can distribute more than 100%");
                    return false;
                }
            case 3:
                if (stkMaxWeight - nWeight >= 0) {
                    double dd = s.getStkValue();
                    s.setStkValue(nWeight);
                    stkMaxWeight = stkMaxWeight + dd - nWeight;
                    stkWeightMaxValue.setText("Remaining weight to distribute: " + stkMaxWeight);
                    return true;
                } else {
                    new AlertBox();
                    AlertBox.display("Stakeholder expectation weight - Overflow", "You can distribute more than 100%");
                    return false;
                }
        }
        return false;
    }

    public void updateStatusLabel(String s) {
        //  statusLabel.setText(s);
    }

    public void buttonModifier(Button btn, boolean b, String s) {
        if (b) {
            btn.setText(s);
            btn.setDisable(false);
            btn.setVisible(true);
        } else if (!b) {
            btn.setText(s);
            btn.setDisable(true);
            btn.setVisible(false);
        }
    }

    public Expectation newExpectation(String stakeholderString, String kpaString) {
        KPA kpa = kpas.stream().filter(k -> kpaString.equals(k.getName()))
                .findFirst().get();
        Stakeholder stk = stakeholders.stream().filter(s -> stakeholderString.equals(s.getName()))
                .findFirst().get();
        return new Expectation("", 0, kpa, stk);
    }

    private boolean containsInTree(String s, int i) {
        switch (i) {
            case 0:
                for (TreeItem<String> t : expecStkItem.getChildren()) {
                    if (s.equals(stringSplitter(t.getValue().toString()))) {
                        return true;
                    }
                }
                break;
            case 1:
                for (TreeItem<String> t : expecExpexItem.getChildren()) {
                    if (s.equals(stringSplitter(t.getValue().toString()))) {
                        return true;
                    }
                }
                break;
            case 2:
                for (TreeItem<String> t : stkWeightItem.getChildren()) {
                    if (s.equals(stringSplitter(t.getValue().toString()))) {
                        return true;
                    }
                }
                break;
            case 3:
                for (TreeItem<String> t : roItem.getChildren()) {
                    if (s.equals(stringSplitter(t.getValue().toString()))) {
                        return true;
                    }
                }
        }
        return false;
    }

    /**
     * Updaters
     */

    public void updateROGP(Stakeholder s) {
        for (int i = gpIndex; i > 0; i--) {
            deleteRow(roGP, i);

        }

        gpIndex = 1;
            for (Expectation e : expectations) {
                if (s.equals(e.getStakeholder())) {
                    TextArea eTA = new TextArea();
                    TextArea rTA = new TextArea();
                    TextField vTF = new TextField();

                    if (!e.hasRO()) {
                        RO r = new RO(e, "", 0, gpIndex);
                        ros.add(r);
                        e.setRo(r);
                    }
                    eTA.setText(e.getDescription());
                    if (!e.getRo().getRisk().isEmpty()) {
                        rTA.setText(e.getRo().getRisk());
                    }
                    eTA.setEditable(false);
                    eTA.setWrapText(true);
                    rTA.setWrapText(true);

                    roGP.addRow(gpIndex, eTA);
                    roGP.add(rTA, 1, gpIndex);
                    roGP.add(vTF, 2, gpIndex);
                    e.setGpIndex(gpIndex);
                    gpIndex++;
                }
            }

        for (RO r : ros) {
            System.out.println(r.getExpectation().getStakeholder().getName());
        }
    }

    public void updateMode(String c, int index, int listSize) {
        if (c == null) {
            switch (index) {
                case 0:
                    if (listSize == 0) {
                        kpaHelpLabel.setText("Add  key performance areas");
                        kpaTreeView.setDisable(true);
                    } else {
                        kpaHelpLabel.setText("Select a key performance area");
                        kpaTreeView.setDisable(false);
                    }
                    kpaTextArea.setText("");
                    kpaTextField.setText("");
                    kpaTextField.setDisable(true);
                    kpaTextArea.setDisable(true);
                    kpaSaveBtn.setDisable(true);
                    break;
                case 1:
                    if (listSize == 0) {
                        stkHelpLabel.setText("Add stakeholders");
                        stkTreeView.setDisable(true);
                    } else {
                        stkHelpLabel.setText("Select a stakeholder");
                        stkTreeView.setDisable(false);
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

    public void updateExpectations(String c, String v) {
        if (c == null) {
            if (stakeholders.size() > 0 && kpas.size() > 0) {
                expHelpLabel.setText("Select a stakeholder");
                expStkTree.setDisable(false);
            } else if (stakeholders.size() == 0 && kpas.size() > 0) {
                expHelpLabel.setText("Add stakeholders before entering this step");
                expStkTree.setDisable(true);
            } else if (stakeholders.size() > 0 && kpas.size() == 0) {
                expHelpLabel.setText("Add key performance areas before entering this step");
                expStkTree.setDisable(true);
            } else {
                expHelpLabel.setText("Add stakeholders and key performance areas before entering this step");
                expStkTree.setDisable(true);
            }
            expWeightField.setText("");
            expKpaTree.setDisable(true);
            expWeightField.setDisable(true);
            expDescArea.setDisable(true);
            weightValueLabel.setText(" ");
        } else if (v == null) {
            expHelpLabel.setText("Select a corresponding key performance area");
            expWeightField.setText("");
            expKpaTree.setDisable(false);
            expWeightField.setDisable(true);
            expDescArea.setDisable(true);
            Stakeholder choosenStk = stakeholders.stream().filter(stk -> c.equals(stk.getName())).findFirst().orElse(null);
            weightValueLabel.setText("Remaining weight to distribute: " + choosenStk.getMaxValue());
            //updateExpKpaVP(c);

        } else {
            Expectation expectation = expectations.stream().filter(exp -> c.equals(exp.getStakeholder().getName())
                    && v.equals(exp.getKpa().getName()))
                    .findFirst().orElse(newExpectation(c, v));
            expWeightField.setText(null);
            expWeightField.setPromptText(expectation.getWeight() + "");
            expDescArea.setText(expectation.getDescription());
            expKpaTree.setDisable(false);
            expWeightField.setDisable(false);
            expDescArea.setDisable(false);
            expHelpLabel.setText(c + " expectation regarding " + v);
            Stakeholder choosenStk = stakeholders.stream().filter(stk -> c.equals(stk.getName())).findFirst().orElse(null);
            weightValueLabel.setText("Remaining weight to distribute: " + choosenStk.getMaxValue());
            //updateExpKpaVP(c);
        }
    }

    public void updateStkWeightView(String c) {
        if (c == null) {
            if (stakeholders.size() > 0) {
                stkWeightViewHelpLabel.setText("Select a stakeholder");
                stkWeightTreeView.setDisable(false);
            } else {
                stkWeightViewHelpLabel.setText("Add stakeholders before entering this step");
                stkWeightTreeView.setDisable(true);
            }
            stkWeightTextField.setText(null);
            stkWeightTextField.setDisable(true);
            stkWeightSaveButton.setDisable(true);
            stkWeightMaxValue.setText("Remaining weight to distribute: " + stkMaxWeight);
        } else {
            Stakeholder stakeholder = stakeholders.stream().filter(s ->
                    c.equals(s.getName())).findFirst().orElse(null);
            stkWeightViewHelpLabel.setText("Give " + c + " a weight");
            stkWeightTextField.setText(null);
            stkWeightTextField.setPromptText(stakeholder.getStkValue() + "");
            stkWeightTextField.setDisable(false);
            stkWeightSaveButton.setDisable(false);
            stkWeightMaxValue.setText("Remaining weight to distribute: " + stkMaxWeight);
        }
    }

    public void updateROView(String c) {
        if (c == null) {
            /*if (ros.size() > 0) {

            } else {

            }
            */
            roGP.setVisible(false);

        } else {
            Stakeholder stakeholder = stakeholders.stream().filter(s ->
                    c.equals(s.getName())).findFirst().orElse(null);
            roGP.setVisible(true);
            updateROGP(stakeholder);
            roSaveBtn.setDisable(false);
        }

    }

    public void updateExpKpaVP(String c) {
        Stakeholder s = stakeholders.stream().filter(stk -> stk.getName().equals(c)).findFirst().orElse(null);
        if (!s.getExpectations().isEmpty()) {
            for (Expectation e : expectations) {
                KPA k = e.getKpa();
                if (e.getStakeholder().getName().equals(s.getName())) {
                    for (TreeItem<String> t : expecExpexItem.getChildren()) {
                        String ans = stringSplitter(t.getValue().toString());
                        if (ans.equals(k.getName())) {
                            if (e.getWeight() > 0) {
                                t.valueProperty().set(ans + " - " + e.getWeight() + "%");
                            } else {
                                t.valueProperty().set(ans);
                            }
                        }
                    }
                }
            }
        } else {
            for (TreeItem<String> t : expecExpexItem.getChildren()) {
                t.valueProperty().set(stringSplitter(t.getValue().toString()));
            }
        }
    }

    /**
     * Setups
     */

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

    public void stkWeightSetUp() {
        for (Stakeholder s : stakeholders) {
            if (!containsInTree(s.getName(), 2)) {
                addTreeItem(s.getName(), 4);
            }
        }
    }

    public void roSetUp() {
        for (Stakeholder s : stakeholders) {
            if (!containsInTree(s.getName(), 3)){
                addTreeItem(s.getName(), 5);
            }
        }
    }

    /**
     * TreeItem metoder
     */

    public void addTreeItem(String name, int i) {
        switch (i) {
            case 0:
                KPA newKPA = new KPA(this, name, "");
                kpas.add(newKPA);
                kpaRootItem.getChildren().add(new TreeItem<String>(name, new ImageView(leafIcon)));
                updateMode(null, tabIndex, kpas.size());
                break;
            case 1:
                Stakeholder newStkhldr = new Stakeholder(this, name, "");
                stakeholders.add(newStkhldr);
                stkRootItem.getChildren().add(new TreeItem<String>(name, new ImageView(leafIcon2)));
                updateMode(null, tabIndex, stakeholders.size());
                break;
            case 2:
                expecStkItem.getChildren().add(new TreeItem<String>(name, new ImageView(leafIcon)));
                updateExpectations(null, null);
                break;
            case 3:
                expecExpexItem.getChildren().add(new TreeItem<String>(name, new ImageView(leafIcon)));
                updateExpectations(null, null);
                break;
            case 4:
                stkWeightItem.getChildren().add(new TreeItem<String>(name, new ImageView(leafIcon2)));
                updateStkWeightView(null);
                break;
            case 5:
                roItem.getChildren().add(new TreeItem<String>(name, new ImageView(leafIcon)));
                updateROView(null);
                break;
        }
    }

    public void removeTreeItem(TreeItem t) {
        if (t != null) {
            boolean remove = t.getParent().getChildren().remove(t);
        } else {
            System.out.println("No matching TreeItem");
        }
    }
}