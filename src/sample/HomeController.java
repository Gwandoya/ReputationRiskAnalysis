package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Node;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.QuadCurve2D;
import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.*;
import java.util.function.Consumer;

public class HomeController {
    private Parent parent;
    private Scene scene;
    private Stage stage;

    RRATObject mainObject;

    private boolean markedStk = false;
    private int tabIndex = 0;
    private int prevIndex = 0;
    private double stkMaxWeight = 100.0;
    private static int gpIndex = 1;
    private static int sGpIndex = 1;
    private static int kGpIndex = 1;
    private boolean debug = false;
    private TreeItem previousTreeItem;
    private TreeItem previousAltTreeItem;
    private int previousTabIndex = 0;

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
    @FXML
    private Button genBtn;
    @FXML
    private Tab expTab;
    @FXML
    private Tab swTab;
    @FXML
    private Tab roTab;
    @FXML
    private Tab resultTab;
    @FXML
    private Label sftPV;
    @FXML
    private Label sftNV;
    @FXML
    private Label kftPV;
    @FXML
    private Label kftNV;
    @FXML
    private Label opV;
    @FXML
    private Label rtV;
    @FXML
    private Label gvV;
    @FXML
    private GridPane sftGP;
    @FXML
    private GridPane kftGP;
    @FXML
    private ScrollPane stkStcSP;
    @FXML
    private ScrollPane kpaStcSP;

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
    private final Image programIcon =
            new Image(getClass().getResourceAsStream("RRAT-Icon.png"));

    TreeItem<String> kpaRootItem = new TreeItem<String>("KeyPerformanceAreas", kpaRootIcon);
    TreeItem<String> stkRootItem = new TreeItem<String>("Stakeholders", stkRootIcon);
    TreeItem<String> expecStkItem = new TreeItem<String>("ExpectationStakeholders", expecStkIcon);
    TreeItem<String> expecExpexItem = new TreeItem<String>("Expectations", expecExpecIcon);
    TreeItem<String> stkWeightItem = new TreeItem<String>("wStakeholders", stkWeightIcon);
    TreeItem<String> roItem = new TreeItem<String>("ROStakeholders", roIcon);

    public static ArrayList<Stakeholder> stakeholders = new ArrayList<Stakeholder>();
    public static ArrayList<KPA> kpas = new ArrayList<KPA>();
    public static ArrayList<Expectation> expectations = new ArrayList<>();
    public static ArrayList<RO> ros = new ArrayList<>();

    static HashMap<Integer, TextArea> textAreaHashMap = new HashMap<>();

    /**Main SetUps*/

    public HomeController(RRATObject rratobject) {
        if (rratobject != null)
            mainObject = rratobject;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("home.fxml"));
        fxmlLoader.setController(this);
        try {
            parent = (Parent) fxmlLoader.load();
            scene = new Scene(parent, 1024, 720);
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
        stage.getIcons().add(programIcon);
    }

    public void treeViewInit() {
        //setUpInit(3);
        if (mainObject == null)
            mainObject = new RRATObject();
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

        kpaTreeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                TreeItem c = (TreeItem) kpaTreeView.getSelectionModel().getSelectedItem();
                if (c != previousTreeItem) {
                    if (saveCheck()) {
                        if (kpas.size() > 0) {
                            updateMode(stringSplitter(c.getValue().toString()), tabIndex, kpas.size());
                        } else {
                            updateMode(null, tabIndex, stakeholders.size());
                        }
                        kpas.stream().filter(kpa -> stringSplitter(c.getValue().toString()).equals(kpa.getName())).forEach(kpa -> {
                            kpaTextArea.setText(kpa.getDesc());
                            kpaTextField.setText(kpa.getName());
                        });
                        previousTreeItem = c;
                    }
                }
            } catch (Exception e) {
                System.out.print("kpaTreeView - Listener - Exception");
            }
        });

        stkTreeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                TreeItem c = (TreeItem) stkTreeView.getSelectionModel().getSelectedItem();
                if (c != previousTreeItem) {
                    if (saveCheck()) {
                        if (stakeholders.size() > 0) {
                            updateMode(stringSplitter(c.getValue().toString()), tabIndex, stakeholders.size());
                        } else {
                            updateMode(null, tabIndex, stakeholders.size());
                        }
                        stakeholders.stream().filter(stakeholder -> stringSplitter(c.getValue().toString()).equals(stakeholder.getName())).forEach(stakeholder -> {
                            stkTextArea.setText(stakeholder.getDesc());
                            stkTextField.setText(stakeholder.getName());
                        });
                        previousTreeItem = c;
                    }
                }
            } catch (Exception e) {
                System.out.print("stkTreeView - Listener - Exception");
            }
        });

        expStkTree.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                MultipleSelectionModel msm = expKpaTree.getSelectionModel();
                TreeItem c = (TreeItem) expStkTree.getSelectionModel().getSelectedItem();
                TreeItem v = null;
                try {
                    v = (TreeItem) expKpaTree.getTreeItem(0);
                } catch (Exception e) {
                    System.out.println("exp-kpa-empty");
                }
                msm.select(expKpaTree.getRow(v));
                if (c != previousTreeItem || v != previousAltTreeItem) {
                    if (saveCheck()) {
                        updateExpectations(
                                c == null ? null : stringSplitter(c.getValue().toString()),
                                v == null ? null : stringSplitter(v.getValue().toString())
                        );
                        previousTreeItem = c;
                        previousAltTreeItem = v;
                    }
                }
            } catch (Exception e) {
                System.out.print("expStkTreeView - Listener - Exception");
            }
        });

        expKpaTree.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                TreeItem c = (TreeItem) expStkTree.getSelectionModel().getSelectedItem();
                TreeItem v = (TreeItem) expKpaTree.getSelectionModel().getSelectedItem();
                if (c != previousTreeItem || v != previousAltTreeItem) {
                    if (saveCheck()) {
                        updateExpectations(
                                c == null ? null : stringSplitter(c.getValue().toString()),
                                v == null ? null : stringSplitter(v.getValue().toString())
                        );
                        previousTreeItem = c;
                        previousAltTreeItem = v;
                    }
                }
            } catch (Exception e) {
                System.out.print("expKpaTreeView - Listener - Exception");
            }
        });

        stkWeightTreeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                TreeItem c = (TreeItem) stkWeightTreeView.getSelectionModel().getSelectedItem();
                if (c != previousTreeItem) {
                    if (saveCheck()) {
                        updateStkWeightView(stringSplitter(c.getValue().toString()));
                        previousTreeItem = c;
                    }
                }
            } catch (Exception e) {
                System.out.print("stkWeightTreeView - Listener - Exception");
            }
        });

        roTreeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                TreeItem c = (TreeItem) roTreeView.getSelectionModel().getSelectedItem();
                if (c != previousTreeItem) {
                    if (saveCheck()) {
                        updateROView(stringSplitter(c.getValue().toString()));
                        previousTreeItem = c;
                    }
                }
            } catch (Exception e) {
                System.out.print("roTreeView - Listener - Exception");
            }
        });

        mainTabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                SingleSelectionModel ssm = mainTabPane.getSelectionModel();
                boolean tabContinue = true;
                if (mainTabPane.getSelectionModel().getSelectedIndex() != tabIndex) {
                    try {
                        if (saveCheck())
                            tabContinue = true;
                        else
                            tabContinue = false;
                    } catch (Exception e) {
                        tabContinue = true;
                    }

                    switch (mainTabPane.getSelectionModel().getSelectedIndex()) {
                        case 0:
                            if (!tabContinue) {
                                ssm.select(tabIndex);
                            } else {
                                tabIndex = 0;
                            }
                            break;
                        case 1:
                            if (!tabContinue) {
                                ssm.select(tabIndex);
                            } else {
                                tabIndex = 1;
                            }
                            break;
                        case 2:
                            if (!tabContinue) {
                                ssm.select(tabIndex);
                            } else {
                                tabIndex = 2;
                                Stakeholder s = stakeholders.get(0);
                                expectationSetUp();
                                expStkTree.getSelectionModel().select(0);
                                updateExpKpaVP(s.getName());
                                updateExpectations(s.getName(), null);
                            }
                            break;
                        case 3:
                            if (!tabContinue) {
                                ssm.select(tabIndex);
                            } else {
                                tabIndex = 3;
                                stkWeightSetUp();
                                updateStkWeightVP();
                                prevIndex = tabIndex;
                            }
                            break;
                        case 4:
                            if (!tabContinue) {
                                ssm.select(tabIndex);
                            } else {
                                tabIndex = 4;
                                Stakeholder s4 = stakeholders.get(0);
                                roSetUp();
                                updateROView(s4.getName());
                                roTreeView.getSelectionModel().select(0);
                                prevIndex = tabIndex;
                            }
                            break;
                        case 5:
                            if (!tabContinue) {
                                ssm.select(tabIndex);
                            } else {
                                tabIndex = 5;
                                prevIndex = tabIndex;

                                MathBackend.calculateSTK();
                                MathBackend.calculateKPA();
                                opV.setText("" + MathBackend.calculateOPV());
                                rtV.setText("" + MathBackend.calculateRTV());
                                gvV.setText("" + MathBackend.calculateGVV());
                                sftPV.setText("" + MathBackend.calculateSFT(0));
                                sftNV.setText("" + MathBackend.calculateSFT(1));
                                kftPV.setText("" + MathBackend.calculateKFT(0));
                                kftNV.setText("" + MathBackend.calculateKFT(1));
                                stkGPCalculate();
                                kpaGPCalculate();
                                setStkImpactGraph();
                                setKpaImpactGraph();
                            }
                    }
                }
            } catch (Exception e) {
                System.out.println("MainTabPane - Listener - Exception");
            }
        });

    }

    public void setUpInit(int index) {
        switch (index) {
            case 1:
                KPA q = new KPA("TestKpaEtt", "Test desription för ettan");
                KPA w = new KPA("TestKpaTvå", "Test desription för tvåan");
                KPA e = new KPA("TestKpaTre", "Test desription för trean");
                kpas.add(q);
                kpas.add(w);
                kpas.add(e);

                Stakeholder a = new Stakeholder("TestStakeholderEtt", "Description för stk-ett");
                Stakeholder s = new Stakeholder("TestStakeholderTvå", "Description för stk-två");
                Stakeholder d = new Stakeholder("TestStakeholderTre", "Description för stk-tre");
                stakeholders.add(a);
                stakeholders.add(s);
                stakeholders.add(d);

                Expectation ex1 = new Expectation("Description stk1, kpa1", 34, q, a);
                Expectation ex2 = new Expectation("Description stk1, kpa2", 33, w, a);
                Expectation ex3 = new Expectation("Description stk1, kpa3", 33, e, a);
                Expectation ex4 = new Expectation("Description stk2, kpa1", 34, q, s);
                Expectation ex5 = new Expectation("Description stk2, kpa2", 33, w ,s);
                Expectation ex6 = new Expectation("Description stk2, kpa3", 33, e, s);
                Expectation ex7 = new Expectation("Description stk3, kpa1", 34, q, d);
                Expectation ex8 = new Expectation("Description stk3, kpa2", 33, w, d);
                Expectation ex9 = new Expectation("Description stk3, kpa3", 33, e, d);
                expectations.add(ex1);
                expectations.add(ex2);
                expectations.add(ex3);
                expectations.add(ex4);
                expectations.add(ex5);
                expectations.add(ex6);
                expectations.add(ex7);
                expectations.add(ex8);
                expectations.add(ex9);
                /*
                RO r1 = new RO(ex1, "risk för :" + ex1.getStakeholder().getName() + " i kombination med " + ex1.getKpa().getName(), 5, 1, null, null);
                RO r2 = new RO(ex2, "risk för :" + ex2.getStakeholder().getName() + " i kombination med " + ex2.getKpa().getName(), 5, 2, null, null);
                RO r3 = new RO(ex3, "risk för :" + ex3.getStakeholder().getName() + " i kombination med " + ex3.getKpa().getName(), 5, 3, null, null);
                RO r4 = new RO(ex4, "risk för :" + ex4.getStakeholder().getName() + " i kombination med " + ex4.getKpa().getName(), 5, 1, null, null);
                RO r5 = new RO(ex5, "risk för :" + ex5.getStakeholder().getName() + " i kombination med " + ex5.getKpa().getName(), 5, 2, null, null);
                RO r6 = new RO(ex6, "risk för :" + ex6.getStakeholder().getName() + " i kombination med " + ex6.getKpa().getName(), 5, 3, null, null);
                RO r7 = new RO(ex7, "risk för :" + ex7.getStakeholder().getName() + " i kombination med " + ex7.getKpa().getName(), 5, 1, null, null);
                RO r8 = new RO(ex8, "risk för :" + ex8.getStakeholder().getName() + " i kombination med " + ex8.getKpa().getName(), 5, 2, null, null);
                RO r9 = new RO(ex9, "risk för :" + ex9.getStakeholder().getName() + " i kombination med " + ex9.getKpa().getName(), 5, 3, null, null);
                ros.add(r1);
                ros.add(r2);
                ros.add(r3);
                ros.add(r4);
                ros.add(r5);
                ros.add(r6);
                ros.add(r7);
                ros.add(r8);
                ros.add(r9);
                r1.setRisk("Ettans risk");
                r2.setRisk("Tvåans Risk");
                r3.setRisk("Treans Risk");
                r4.setRisk("Fyrans Risk");
                r5.setRisk("Femmans Risk");
                r6.setRisk("Sexxans Risk");
                r7.setRisk("Sjuans Risk");
                r8.setRisk("Åttans Risk");
                r9.setRisk("Nians Risk");
                r1.setValue(0);
                r2.setValue(1);
                r3.setValue(2);
                r4.setValue(3);
                r5.setValue(4);
                r6.setValue(5);
                r7.setValue(6);
                r8.setValue(7);
                r9.setValue(8);
                ex1.setRo(r1);
                ex2.setRo(r2);
                ex3.setRo(r3);
                ex4.setRo(r4);
                ex5.setRo(r5);
                ex6.setRo(r6);
                ex7.setRo(r7);
                ex8.setRo(r8);
                ex9.setRo(r9);
                */

                kpas.stream().forEach(kpa -> kpaRootItem.getChildren().add(new TreeItem<>(kpa.getName(), new ImageView(leafIcon))));
                kpas.stream().forEach(kpa -> addTreeItem(kpa.getName(), 3));
                stakeholders.stream().forEach(stakeholder -> stkRootItem.getChildren().add(new TreeItem<>(stakeholder.getName(), new ImageView(leafIcon))));
                stakeholders.stream().forEach(stakeholder -> addTreeItem(stakeholder.getName(), 2));
                break;
            case 2:
                KPA x = new KPA("ALPHA", "");
                KPA y = new KPA("BETA", "");
                KPA z = new KPA("CITRON", "");
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
                Consumer<Stakeholder> addSTK = (Stakeholder st) -> stkRootItem.getChildren().add(new TreeItem<String>(st.getName(), new ImageView(leafIcon2)));

                kpas.forEach(addKPA);
                stakeholders.forEach(addSTK);


                updateMode(null, 0, 1);
                updateMode(null, 1, 1);
                break;
            case 3:
                KPA kpa1 = new KPA("Kpa1", "KPA1");
                KPA kpa2 = new KPA("Kpa2", "KPA2");
                kpas.add(kpa1);
                kpas.add(kpa2);

                Stakeholder stk1 = new Stakeholder("Stk1", "STK1");
                Stakeholder stk2 = new Stakeholder("Stk2", "STK2");
                stakeholders.add(stk1);
                stakeholders.add(stk2);

                Consumer<KPA> addKPA1 = (KPA k) -> kpaRootItem.getChildren().add(new TreeItem<String>(k.getName(), new ImageView(leafIcon)));
                Consumer<Stakeholder> addSTK1 = (Stakeholder st) -> stkRootItem.getChildren().add(new TreeItem<String>(st.getName(), new ImageView(leafIcon2)));

                kpas.forEach(addKPA1);
                stakeholders.forEach(addSTK1);
                break;
        }
    }

    /**Button Handlers*/

    public void loadFileOnClick(ActionEvent event) {
        RRATObject rratObject = saveFunction.readFromFile(stage);
        if (rratObject != null) handleLoadedFile(rratObject);
    }

    public void saveAsOnClick(ActionEvent event) {
        saveFunction.writeToChosenFile(mainObject, stage);
    }

    public void saveOnClick(ActionEvent event) {
        System.out.println("SaveClicked" + mainObject.toString());
        saveFunction.writeToFile(mainObject);
    }

    public void stakeholderAddBtnOnClick(ActionEvent actionEvent) throws InterruptedException {
        AddBoxController addBox = new AddBoxController();
        addBox.display("Stakeholder");
        if (addBox.getContinue()) addTreeItem(addBox.getName(), 1);
    }

    public void kpaAddBtnOnClick(ActionEvent actionEvent)  throws InterruptedException {
        AddBoxController addBox = new AddBoxController();
        addBox.display("KPA");
        if (addBox.getContinue()) addTreeItem(addBox.getName(), 0);
    }

    public void stakeholderDeleteBtnOnClick(ActionEvent actionEvent) {
        DeleteBoxController deleteBox = new DeleteBoxController();
        TreeItem c;
        TreeItem q = null;
        TreeItem z = null;
        boolean remove = false;

        try {
            c = (TreeItem) stkTreeView.getSelectionModel().getSelectedItem();

            for (TreeItem<String> t : expecStkItem.getChildren()) {
                if (stringSplitter(t.getValue().toString()).equals(stringSplitter(c.getValue().toString()))) {
                    q = t;
                    break;
                }
            }

            for (TreeItem<String> t : stkWeightItem.getChildren()) {
                if (stringSplitter(t.getValue().toString()).equals(stringSplitter(c.getValue().toString()))){
                    z = t;
                    break;
                }
            }

            if (!stkRootItem.getChildren().isEmpty()) {
                deleteBox.display("Stakeholder: ", stringSplitter(c.getValue().toString()));
                if (deleteBox.getDeleteConfirm()) {
                    Stakeholder stk = stakeholders.stream().filter(s -> s.getName().equals(stringSplitter(c.getValue().toString()))).findFirst().orElse(null);
                    Expectation exp = expectations.stream().filter(e -> e.getStakeholder().getName().equals(stringSplitter(c.getValue().toString()))).findFirst().orElse(null);
                    stkMaxWeight = stkMaxWeight + stk.getStkValue();
                    updateStkWeightView(null);
                    if (stk != null) stk.deleteSTK();
                    //exp.deleteExp();
                    removeTreeItem(c);
                    removeTreeItem(q);
                    removeTreeItem(z);
                    previousTreeItem = null;
                    stkTreeView.getSelectionModel().select(null);
                    updateMode(null, 1, stakeholders.size());
                }
            } else {
                AlertBox.display("Invalid Action", "No selected item.");
            }
        } catch (Exception e) {
            System.out.println("Stk delete error");
        }
    }

    public void kpaDeleteBtnOnClick(ActionEvent actionEvent) {
        DeleteBoxController deleteBox = new DeleteBoxController();
        TreeItem c;
        TreeItem q = null;
        TreeItem z = null;
        boolean remove = false;

        try {
            c = (TreeItem) kpaTreeView.getSelectionModel().getSelectedItem();

            for (TreeItem<String> t : expecExpexItem.getChildren()) {
                if (stringSplitter(t.getValue().toString()).equals(stringSplitter(c.getValue().toString()))) {
                    q = t;
                    break;
                }
            }

            if (!kpaRootItem.getChildren().isEmpty()) {
                deleteBox.display("Key performance area: ", stringSplitter(c.getValue().toString()));
                if (deleteBox.getDeleteConfirm()) {
                    KPA k = kpas.stream().filter(kpa ->
                            kpa.getName().equals(stringSplitter(c.getValue().toString()))).findFirst().orElse(null);
                    for (Expectation e : expectations) {
                        if (e.getKpa().equals(k) && e.getStakeholder().getDistBoolean() && e.getWeight() > 0) {
                            TreeItem treeItem = expecStkItem.getChildren().stream().filter((TreeItem t) ->
                                    t.getValue().toString().equals(e.getStakeholder().getName())).
                                    findFirst().orElse(null);
                            if (treeItem != null) treeItem.graphicProperty().set(new ImageView(leafIcon));
                        }
                    }
                    if (k != null) k.deleteKPA();
                    removeTreeItem(c);
                    removeTreeItem(q);
                    previousTreeItem = null;
                    kpaTreeView.getSelectionModel().select(null);
                    updateMode(null, 0, kpas.size());
                }
            } else {
                AlertBox.display("Invalid Action", "No selected item.");
            }
        } catch (Exception e) {
            System.out.println("Kpa delete btn");
        }
    }

    @FXML
    public void kpaSave(ActionEvent event) {
        TreeItem c = (TreeItem) kpaTreeView.getSelectionModel().getSelectedItem();
        KPA kpa = kpas.stream().filter(k ->
                k.getName().equals(stringSplitter(c.getValue().toString()))).findFirst().orElse(null);

        if (containsInTree(kpa.getName(), 1)) {
            for (TreeItem<String> t : expecExpexItem.getChildren()) {
                if (stringSplitter(t.getValue().toString()).equals(kpa.getName())) {
                    t.setValue(kpaTextField.getText());
                }
            }
        }

        kpa.setDesc(kpaTextArea.getText());
        kpa.setName(kpaTextField.getText());
        c.valueProperty().set(kpaTextField.getText());
    }

    @FXML
    public void stkSave(ActionEvent event) {
        TreeItem c = (TreeItem) stkTreeView.getSelectionModel().getSelectedItem();
        Stakeholder stk = stakeholders.stream().filter(s ->
            s.getName().equals(stringSplitter(c.getValue().toString()))).findFirst().orElse(null);

        if (containsInTree(stk.getName(), 0)) {
            for (TreeItem<String> t : expecStkItem.getChildren()) {
                if (stringSplitter(t.getValue().toString()).equals(stk.getName())) {
                    t.setValue(stkTextField.getText());
                }
            }
        }

        if (containsInTree(stk.getName(), 2)) {
            for (TreeItem<String> t : stkWeightItem.getChildren()) {
                if (stringSplitter(t.getValue().toString()).equals(stk.getName())) {
                    t.setValue(stkTextField.getText());
                }
            }
        }

        stk.setDesc(stkTextArea.getText());
        stk.setName(stkTextField.getText());
        c.valueProperty().set(stkTextField.getText());
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
            if (expectationExist(exp));
            kpa.removeExpectationIfPresent(stk);
            kpa.addExpectation(exp);
            expectations.add(exp);
            if (stk.getDistBoolean()) {
                c.graphicProperty().set(new ImageView(leafIconG));
            } else {
                c.graphicProperty().set(new ImageView(leafIcon));
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
        Stakeholder stakeholder = stakeholders.stream().filter(s ->
                s.getName().equals(stringSplitter(c.getValue().toString()))).findFirst().orElse(null);

        expectations.stream().filter(expectation ->
                expectation.getStakeholder().equals(stakeholder)).forEach(expectation -> {

            try {
                expectation.getRo().setRisk(textAreaHashMap
                                .get(expectation.getGpIndex())
                                .getText()
                );
            } catch (Exception e) {
                System.out.println("RO-Save error");
            }

        });

        updateROView(stakeholder.getName());
    }

    /**Help methods*/

    public boolean saveCheck() {
        try {
            String rootTree = previousTreeItem.getParent().getValue().toString();
            AlertBox alertBox = new AlertBox();
            MultipleSelectionModel msm;
            MultipleSelectionModel msm2;
            switch (rootTree) {
                case "KeyPerformanceAreas":
                    msm = kpaTreeView.getSelectionModel();
                    KPA k = kpas.stream().
                            filter(kpa -> stringSplitter(previousTreeItem.
                                    getValue().toString()).
                                    equals(kpa.getName()))
                            .findFirst().orElse(null);
                    if (!k.getName().equals(kpaTextField.getText())
                            || !k.getDesc().equals(kpaTextArea.getText())) {
                        alertBox.display("Not saved", "Save b4");
                        Platform.runLater(() -> msm.select(kpaTreeView.getRow(previousTreeItem)));
                        return false;
                    }
                    break;
                case "Stakeholders":
                    msm = stkTreeView.getSelectionModel();
                    Stakeholder s = stakeholders.stream().
                            filter(stk -> stringSplitter(previousTreeItem.
                                    getValue().toString()).
                                    equals(stk.getName()))
                            .findFirst().orElse(null);
                    if (!s.getName().equals(stkTextField.getText())
                            || !s.getDesc().equals(stkTextArea.getText())) {
                        alertBox.display("stk not saved", "save b4");
                        Platform.runLater(() -> msm.select(stkTreeView.getRow(previousTreeItem)));
                        return false;
                    }
                    break;
                case "ExpectationStakeholders":
                    msm = expStkTree.getSelectionModel();
                    msm2 = expKpaTree.getSelectionModel();
                    Stakeholder es = stakeholders.stream().
                        filter(stk -> stringSplitter(previousTreeItem.
                                getValue().toString()).
                                equals(stk.getName()))
                        .findFirst().orElse(null);
                    KPA ek = kpas.stream().
                            filter(kpa -> stringSplitter(previousAltTreeItem.
                                    getValue().toString()).
                                    equals(kpa.getName()))
                            .findFirst().orElse(null);
                    Expectation ee = ek.getExpectation(es);
                    boolean eeSaved = false;
                    if (ee == null && (expDescArea.getText().length() > 0 || Double.parseDouble(expWeightField.getText()) > 0))
                        eeSaved = true;
                    if (!ee.getDescription().equals(expDescArea.getText())
                            || ee.getWeight() != Double.parseDouble(
                            expWeightField.getText())
                            || eeSaved) {
                        alertBox.display("stk not saved", "save b4");
                        Platform.runLater(() -> {
                            msm.select(expStkTree.getRow(previousTreeItem));
                            msm2.select(expKpaTree.getRow(previousAltTreeItem));
                        });
                        return false;
                    }
                    break;

                case "wStakeholders":
                    msm = stkWeightTreeView.getSelectionModel();
                    Stakeholder ws = stakeholders.stream().
                            filter(stk -> stringSplitter(previousTreeItem.
                                    getValue().toString()).
                                    equals(stk.getName()))
                            .findFirst().orElse(null);
                    if (!ws.getStkValue().equals(Double.parseDouble(stkWeightTextField.getText()))) {
                        alertBox.display("stk not saved", "save b4");
                        Platform.runLater(() -> msm.select(stkWeightTreeView.getRow(previousTreeItem)));
                        return false;
                    }
                    break;
                case "ROStakeholders":
                    msm = roTreeView.getSelectionModel();
                    Stakeholder ros = stakeholders.stream().
                            filter(stk -> stringSplitter(previousTreeItem.
                                    getValue().toString()).
                                    equals(stk.getName()))
                            .findFirst().orElse(null);
                    int saveCount = 0;
                    for (Expectation e : ros.getExpectations()) {
                        if (e.getRo().getRisk().equals(textAreaHashMap.get(e.getRo().getGridIndex()).getText()))
                            saveCount++;
                    }
                    if (saveCount != ros.getExpectations().size()) {
                        alertBox.display("stk not saved", "save b4");
                        Platform.runLater(() -> msm.select(roTreeView.getRow(previousTreeItem)));
                        return false;
                    }
                    break;
            }
        } catch (Exception e) {
            System.out.println("saveCheck(): treeItem == null");
        }

        return true;
    }

    public boolean expectationExist(Expectation expectation) {
        for (Expectation e : expectations) {
            if (e.getStakeholder().equals(expectation.getStakeholder()) && e.getKpa().equals(expectation.getKpa())) {
                //expectations.remove(e);
                e.deleteExp();
                RO r = ros.stream().filter(ro -> ro.getExpectation().equals(e)).findFirst().orElse(null);
                if (r != null) {
                    r.setExpectation(expectation);
                    expectation.setRo(r);
                }
                return true;
            }
        }
        return false;
    }

    public boolean hasContinue(Integer index) {
        if (index == null) index = tabIndex;
        switch (index) {
            case 0 :
            case 1 :
                if (kpas.size() != 0 && stakeholders.size() != 0) return true;
                return false;
            case 2 :
                for (KPA k : kpas) {
                    for (Stakeholder s : stakeholders) {
                        if (!s.getDistBoolean()) return false;
                        /*
                        if (k.getExpectation(s) == null) {
                            return false;
                        }
                        */
                    }
                }
                return true;
            case 3 :
                System.out.println("DEBUG -3");
                if (stkMaxWeight == 0) return true;
                else return false;

            case 4:
                System.out.println("DEBUG - 4");
                for (Expectation e : expectations) {
                    if (e.getWeight() > 0 && !e.hasRO() && hasContinue(2)) return false;
                }
                return true;
        }
        return false;
    }

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
                double a = 0;
                if (k.getExpectation(s) != null) {
                    a = k.getExpectation(s).getWeight();
                }
                if (s.getMaxValue() + a - nWeight >= 0) {
                    s.setMaxValue(s.getMaxValue() + a - nWeight);
                    weightValueLabel.setText("Remaining weight to distribute: " + s.getMaxValue());
                    return true;
                } else {
                    new AlertBox();
                    AlertBox.display("Stakeholder expectation weight - Overflow", "You can distribute more than 100%");
                    return false;
                }
            case 3:
                double dd = s.getStkValue();
                if (stkMaxWeight + dd - nWeight >= 0) {
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

    /*
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
    */

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

    /**Updaters*/

    public void updateROGP(Stakeholder s) {

        for (int i = gpIndex; i > 0; i--) {
            deleteRow(roGP, i);
            textAreaHashMap.remove(i);
        }

        gpIndex = 1;

        for (Expectation e : s.getExpectations()) {
            if (e.getWeight() > 0) {
                Text text = new Text(e.getDescription());
                TextFlow eTF = new TextFlow(text);
                TextArea rTA = new TextArea();

                if (!e.hasRO()) {
                    RO r = new RO(e, "", 0, gpIndex);
                    ros.add(r);
                    e.setRo(r);
                }
                Elements elements = new Elements(e.getRo());
                AnchorPane anchorPane = elements.getAnchorPane();
                SplitMenuButton vSMB = elements.createMenuButton();

                try {
                    rTA.setText(e.getRo().getRisk());
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                elements.setMenuItem(vSMB, anchorPane, e.getRo().getValue());

                rTA.setWrapText(true);

                HBox hBox = new HBox(anchorPane, vSMB);
                hBox.setHgrow(anchorPane, Priority.ALWAYS);

                roGP.addRow(gpIndex, eTF);
                roGP.add(rTA, 1, gpIndex);
                roGP.add(hBox, 2, gpIndex);
                //roGP.add(anchorPane, 2, gpIndex);
                //roGP.add(vSMB, 3, gpIndex);
                for (Node n : roGP.getChildren()) {
                    roGP.setMargin(n, new Insets(5, 5, 5, 5));
                }
                textAreaHashMap.put(gpIndex, rTA);
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
                    } else {
                        kpaHelpLabel.setText("Select a key performance area");
                    }
                    kpaTextArea.setText("");
                    kpaTextField.setText("");
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
                    break;
            }
        } else {
            switch (index) {
                case 0:
                    kpaHelpLabel.setText("Enter a description for: " + c);
                    break;
                case 1:
                    stkHelpLabel.setText("Enter a description for: " + c);
                    break;
            }
        }
    }

    public void updateExpectations(String c, String v) {
        if (c == null) {
            if (stakeholders.size() > 0 && kpas.size() > 0) {
                expHelpLabel.setText("Select a stakeholder");
            } else if (stakeholders.size() == 0 && kpas.size() > 0) {
                expHelpLabel.setText("Add stakeholders before entering this step");
            } else if (stakeholders.size() > 0 && kpas.size() == 0) {
                expHelpLabel.setText("Add key performance areas before entering this step");
            } else {
                expHelpLabel.setText("Add stakeholders and key performance areas before entering this step");
            }
            expWeightField.setText("");
            weightValueLabel.setText(" ");
            //updateExpKpaVP(null);

        } else if (v == null) {
            expHelpLabel.setText("Select a corresponding key performance area");
            expWeightField.setText("");
            Stakeholder choosenStk = stakeholders.stream().filter(stk -> c.equals(stk.getName())).findFirst().orElse(null);
            weightValueLabel.setText("Remaining weight to distribute: " + choosenStk.getMaxValue());
            updateExpKpaVP(c);

        } else {
            Expectation expectation = expectations.stream().filter(exp -> c.equals(exp.getStakeholder().getName())
                    && v.equals(exp.getKpa().getName()))
                    .findFirst().orElse(newExpectation(c, v));
            expWeightField.setText(null);
            expWeightField.setText(expectation.getWeight() + "");
            expDescArea.setText(expectation.getDescription());
            expHelpLabel.setText(c + " expectation regarding " + v);
            Stakeholder choosenStk = stakeholders.stream().filter(stk ->
                    c.equals(stk.getName())).findFirst().orElse(null);
            weightValueLabel.setText("Remaining weight to distribute: " + choosenStk.getMaxValue());
            updateExpKpaVP(c);
        }
    }

    public void updateStkWeightView(String c) {
        if (c == null) {
            if (stakeholders.size() > 0) {
                stkWeightViewHelpLabel.setText("Select a stakeholder");
            } else {
                stkWeightViewHelpLabel.setText("Add stakeholders before entering this step");
            }
            stkWeightTextField.setText(null);
            stkWeightMaxValue.setText("Remaining weight to distribute: " + stkMaxWeight);
        } else {
            Stakeholder stakeholder = stakeholders.stream().filter(s ->
                    c.equals(s.getName())).findFirst().orElse(null);
            stkWeightViewHelpLabel.setText("Give " + c + " a weight");
            stkWeightTextField.setText(null);
            stkWeightTextField.setPromptText(stakeholder.getStkValue() + "");
            stkWeightMaxValue.setText("Remaining weight to distribute: " + stkMaxWeight);
        }
    }

    public void updateROView(String c) {
        if (c != null) {
            Stakeholder stakeholder = stakeholders.stream().filter(s ->
                    c.equals(s.getName())).findFirst().orElse(null);
            roGP.setVisible(true);
            updateROGP(stakeholder);
        }
    }

    public void updateStkWeightVP() {
        for (Stakeholder s : stakeholders) {
            for (TreeItem<String> t : stkWeightItem.getChildren()) {
                if (stringSplitter(t.getValue().toString()).equals(s.getName())) {
                    if (s.getStkValue() > 0)
                        t.valueProperty().set(s.getName() + " - " + s.getStkValue() + "%");
                    else
                        t.valueProperty().set(s.getName());
                }
            }
        }
    }

    public void updateExpKpaVP(String c) {
        Stakeholder s = stakeholders.stream().filter(stk -> stk.getName().equals(c)).findFirst().orElse(null);

        for (KPA k : kpas) {
            if (k.getExpectation(s) == null) {
                Expectation e = newExpectation(s.getName(), k.getName());
                k.addExpectation(e);
                expectations.add(e);
            }
        }
        for (Expectation e : expectations) {
            if (e.getStakeholder().getName().equals(s.getName())) {
                for (TreeItem<String> t : expecExpexItem.getChildren()) {
                    String ans = stringSplitter(t.getValue().toString());
                    if (ans.equals(e.getKpa().getName())) {
                        if (e.getWeight() > 0) {
                            t.valueProperty().set(ans + " - " + e.getWeight() + "%");
                        } else {
                            t.valueProperty().set(ans);
                        }
                    }
                }
            }
        }
    }

    /**Setups*/

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
        ArrayList<TreeItem> treeItemsToBeRemoved = new ArrayList<>();
        for (TreeItem t : roItem.getChildren()) {
            Stakeholder s = stakeholders.stream().filter(stakeholder ->
                    stakeholder.getName().equals(t.getValue().toString())).findFirst().orElse(null);
            if (s == null || s.getStkValue() == 0) {
                treeItemsToBeRemoved.add(t);
            }
        }
        for (TreeItem t : treeItemsToBeRemoved) {
            boolean x = t.getParent().getChildren().remove(t);
        }
        for (Stakeholder s : stakeholders) {
            if (s.getStkValue() > 0) {
                if (!containsInTree(s.getName(), 3)) {
                    addTreeItem(s.getName(), 5);
                }
            }
        }
        updateROView(null);
    }

    /**TreeItem methods*/

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

    /**Result Generating Methods*/

    public void genBtnOnClick(ActionEvent actionEvent) {
        //https://docs.oracle.com/javafx/2/charts/bar-chart.htm
        //MathBackend.stkGPCalculate(sftGP);
        //MathBackend.kpaGPCalculate(kftGP);
        //MathBackend.debugPrints();
    }

    public void setStkImpactGraph() {
        final CategoryAxis yAxis = new CategoryAxis();
        final NumberAxis xAxis = new NumberAxis();
        final StackedBarChart<Number, String> sbc = new StackedBarChart<Number, String>(xAxis, yAxis);
        final XYChart.Series<Number, String> positive = new XYChart.Series<>();
        final XYChart.Series<Number, String> negative = new XYChart.Series<>();

        ArrayList<String> stakeholderArray = new ArrayList<>();
       // for (Stakeholder s : stakeholders) {
        for (int i = stakeholders.size(); i > 0; i--) {
            Stakeholder s = stakeholders.get(i-1);
            stakeholderArray.add(s.getName());
        }

        yAxis.setCategories(FXCollections.observableArrayList(stakeholderArray));
        yAxis.setLabel("Stakeholders");
        xAxis.setLabel("Impact");
        //sbc.setTitle("Stakeholders Impact");

        positive.setName("Positive");
        negative.setName("Negative");
        for (int i = stakeholders.size(); i > 0; i--) {
            Stakeholder s = stakeholders.get(i-1);

            positive.getData().add(
                    new XYChart.Data(s.getPvalue(), s.getName())
            );
            negative.getData().add(
                    new XYChart.Data(s.getNvalue(), s.getName())
            );
        }

        sbc.getData().addAll(negative, positive);
        stkStcSP.setContent(sbc);
    }

    public void setKpaImpactGraph() {
        final CategoryAxis yAxis = new CategoryAxis();
        final NumberAxis xAxis = new NumberAxis();
        final StackedBarChart<Number, String> sbc = new StackedBarChart<Number, String>(xAxis, yAxis);
        final XYChart.Series<Number, String> positive = new XYChart.Series<>();
        final XYChart.Series<Number, String> negative = new XYChart.Series<>();

        ArrayList<String> kpaArray = new ArrayList<>();
        //for (KPA k : kpas) {
        for (int i = kpas.size(); i > 0; i--) {
            KPA k = kpas.get(i-1);
            kpaArray.add(k.getName());
        }

        yAxis.setCategories(FXCollections.observableArrayList(kpaArray));
        yAxis.setLabel("Key Performance Areas");
        xAxis.setLabel("Impact");

        positive.setName("Positive");
        negative.setName("Negative");
        //for (KPA k : kpas) {
        for (int i = kpas.size(); i > 0; i--) {
            KPA k = kpas.get(i-1);
            positive.getData().add(
                    new XYChart.Data(k.getPvalue(), k.getName())
            );
            negative.getData().add(
                    new XYChart.Data(k.getNvalue(), k.getName())
            );
        }

        sbc.getData().addAll(negative, positive);
        kpaStcSP.setContent(sbc);
    }

    public void stkGPCalculate() {

        for (int i = sGpIndex; i > 0; i--) {
            deleteRow(sftGP, i);
        }

        sGpIndex = 1;


        //if (debug != true) {
        for (Stakeholder s : stakeholders) {
            if (s.mathP.size() != 0 || s.mathN.size() != 0) {
                Label stkL = new Label();
                Label favL = new Label();
                Label thrL = new Label();

                stkL.setText(s.getName());
                stkL.setStyle("-fx-font-weight: bold");
                favL.setText("" + (double) Math.round(s.getPvalue() * 1000d) / 1000d);
                thrL.setText("" + (double) Math.round(s.getNvalue() * 1000d) / 1000d);

                sftGP.addRow(sGpIndex, stkL);
                sftGP.add(favL, 1, sGpIndex);
                sftGP.add(thrL, 2, sGpIndex);
                sGpIndex++;
            }
        }
        //}
        //debug = true;
    }

    public void kpaGPCalculate() {

        for (int i = kGpIndex; i > 0; i--) {
            deleteRow(kftGP, i);
        }

        kGpIndex = 1;

        for (KPA k : kpas) {
            if (k.mathP.size() != 0 || k.mathN.size() != 0) {
                Label stkL = new Label();
                Label favL = new Label();
                Label thrL = new Label();

                stkL.setText(k.getName());
                stkL.setStyle("-fx-font-weight: bold");
                favL.setText("" + (double)Math.round(k.getPvalue() * 1000d) / 1000d);
                thrL.setText("" + (double)Math.round(k.getNvalue() * 1000d) / 1000d);

                kftGP.addRow(kGpIndex, stkL);
                kftGP.add(favL, 1, kGpIndex);
                kftGP.add(thrL, 2, kGpIndex);
                kGpIndex++;
            }
        }
    }

    public void handleLoadedFile(RRATObject mainObject) {
        //Rensar alla ArrayLists
        stakeholders.clear();
        kpas.clear();
        expectations.clear();
        ros.clear();

        //Rensar alla TreeViews
        kpaRootItem.getChildren().clear();
        stkRootItem.getChildren().clear();
        expecExpexItem.getChildren().clear();
        expecStkItem.getChildren().clear();
        stkWeightItem.getChildren().clear();
        roItem.getChildren().clear();

        //Adderar dom nya ArrayListorna
        stakeholders = mainObject.getStakeholders();
        kpas = mainObject.getKpas();
        expectations = mainObject.getExpectations();
        ros = mainObject.getRos();

        //Adderar dom nya Löfen till träden
        for (KPA k : kpas) {
            kpaRootItem.getChildren().add(new TreeItem<String>(k.getName(), new ImageView(leafIcon)));
        }
        for (Stakeholder s : stakeholders) {
            stkRootItem.getChildren().add(new TreeItem<String>(s.getName(), new ImageView(leafIcon2)));
        }
        SingleSelectionModel singleSelectionModel = mainTabPane.getSelectionModel();
        singleSelectionModel.select(0);
        previousTreeItem = null;
        previousAltTreeItem = null;
        stkMaxWeight = 100;
        for (Stakeholder s : stakeholders) {
            stkMaxWeight = stkMaxWeight - s.getStkValue();
        }
    }
}