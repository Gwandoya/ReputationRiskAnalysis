package sample;

import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * Created by dödsadde on 2017-05-08.
 */
public class Elements {
    RO ro;
    AnchorPane anchorPane = new AnchorPane();
    String [] apStrings = {
            "Catastrophic Threat",
            "Significant Threat",
            "Moderate Threat",
            "Minor Threat",
            "Insignificant Threat",
            "No Impact",
            "Insignificant Opportunity",
            "Minor Opportunity",
            "Moderate Opportunity",
            "Significant Opportunity",
            "Outstanding Opportunity"
    };


    public Elements(RO ro) {
        this.ro = ro;
    }

    public SplitMenuButton createMenuButton() {
        MenuItem menuItemp8 = new MenuItem("8");
        MenuItem menuItemp5 = new MenuItem("5");
        MenuItem menuItemp3 = new MenuItem("3");
        MenuItem menuItemp2 = new MenuItem("2");
        MenuItem menuItemp1 = new MenuItem("1");
        MenuItem menuItem0  = new MenuItem("0");
        MenuItem menuItemn1 = new MenuItem("-1");
        MenuItem menuItemn2 = new MenuItem("-2");
        MenuItem menuItemn3 = new MenuItem("-3");
        MenuItem menuItemn5 = new MenuItem("-5");
        MenuItem menuItemn8 = new MenuItem("-8");


        SplitMenuButton splitMenuButton =
                new SplitMenuButton(
                        menuItemp8,
                        menuItemp5,
                        menuItemp3,
                        menuItemp2,
                        menuItemp1,
                        menuItem0,
                        menuItemn1,
                        menuItemn2,
                        menuItemn3,
                        menuItemn5,
                        menuItemn8
                );

        menuItemn8.setOnAction(a -> {
            ro.setValue(-8);
            setMenuItem(splitMenuButton, anchorPane, -8);
        });
        menuItemn5.setOnAction(a -> {
            ro.setValue(-5);
            setMenuItem(splitMenuButton, anchorPane, -5);
        });
        menuItemn3.setOnAction(a -> {
            ro.setValue(-3);
            setMenuItem(splitMenuButton, anchorPane, -3);
        });
        menuItemn2.setOnAction(a -> {
            ro.setValue(-2);
            setMenuItem(splitMenuButton, anchorPane, -2);
        });
        menuItemn1.setOnAction(a -> {
            ro.setValue(-1);
            setMenuItem(splitMenuButton, anchorPane, -1);
        });
        menuItem0.setOnAction(a -> {
            ro.setValue(0);
            setMenuItem(splitMenuButton, anchorPane, 0);
        });
        menuItemp1.setOnAction(a -> {
            ro.setValue(1);
            setMenuItem(splitMenuButton, anchorPane, 1);
        });
        menuItemp2.setOnAction(a -> {
            ro.setValue(2);
            setMenuItem(splitMenuButton, anchorPane, 2);
        });
        menuItemp3.setOnAction(a -> {
            ro.setValue(3);
            setMenuItem(splitMenuButton, anchorPane, 3);
        });
        menuItemp5.setOnAction(a -> {
            ro.setValue(5);
            setMenuItem(splitMenuButton, anchorPane, 5);
        });
        menuItemp8.setOnAction(a -> {
            ro.setValue(8);
            setMenuItem(splitMenuButton, anchorPane, 8);
        });
        splitMenuButton.setText("Value: ");
        return splitMenuButton;
    }

    public void setMenuItem(SplitMenuButton splitMenuButton, AnchorPane anchorPane, int value) {
        switch (value) {
            case -8:
                splitMenuButton.getItems().set(0, splitMenuButton.getItems().get(0));
                anchorPane.getChildren().clear();
                anchorPane.setStyle("-fx-background-color: #ffe5e5");
                Text t1 = new Text(apStrings[0]);
                TextFlow textFlow1 = new TextFlow(t1);
                anchorPane.getChildren().add(textFlow1);
                splitMenuButton.setText("Value: -8");
                break;
            case -5:
                splitMenuButton.getItems().set(1, splitMenuButton.getItems().get(1));
                anchorPane.getChildren().clear();
                anchorPane.setStyle("-fx-background-color: #ffeee6");
                Text t2 = new Text(apStrings[1]);
                TextFlow textFlow2 = new TextFlow(t2);
                anchorPane.getChildren().add(textFlow2);
                splitMenuButton.setText("Value: -5");
                break;
            case -3:
                splitMenuButton.getItems().set(2, splitMenuButton.getItems().get(2));
                anchorPane.getChildren().clear();
                anchorPane.setStyle("-fx-background-color: #fff3e6");
                Text t3 = new Text(apStrings[2]);
                TextFlow textFlow3 = new TextFlow(t3);
                anchorPane.getChildren().add(textFlow3);
                splitMenuButton.setText("Value: -3");
                break;
            case -2:
                splitMenuButton.getItems().set(3, splitMenuButton.getItems().get(3));
                anchorPane.getChildren().clear();
                anchorPane.setStyle("-fx-background-color: #fff9e6");
                Text t4 = new Text(apStrings[3]);
                TextFlow textFlow4 = new TextFlow(t4);
                anchorPane.getChildren().add(textFlow4);
                splitMenuButton.setText("Value: -2");
                break;
            case -1:
                splitMenuButton.getItems().set(4, splitMenuButton.getItems().get(4));
                anchorPane.getChildren().clear();
                anchorPane.setStyle("-fx-background-color: #fffef2");
                Text t5 = new Text(apStrings[4]);
                TextFlow textFlow5 = new TextFlow(t5);
                anchorPane.getChildren().add(textFlow5);
                splitMenuButton.setText("Value: -1");
                break;
            case 0:
                splitMenuButton.getItems().set(5, splitMenuButton.getItems().get(5));
                anchorPane.getChildren().clear();
                anchorPane.setStyle("-fx-background-color: #FFFFFF");
                Text t6 = new Text(apStrings[5]);
                TextFlow textFlow6 = new TextFlow(t6);
                anchorPane.getChildren().add(textFlow6);
                splitMenuButton.setText("Value: 0");
                break;
            case 1:
                splitMenuButton.getItems().set(6, splitMenuButton.getItems().get(6));
                anchorPane.getChildren().clear();
                anchorPane.setStyle("-fx-background-color: #e6ffff");
                Text t7 = new Text(apStrings[6]);
                TextFlow textFlow7 = new TextFlow(t7);
                anchorPane.getChildren().add(textFlow7);
                splitMenuButton.setText("Value: 1");
                break;
            case 2:
                splitMenuButton.getItems().set(7, splitMenuButton.getItems().get(7));
                anchorPane.getChildren().clear();
                anchorPane.setStyle("-fx-background-color: #e6fff8");
                Text t8 = new Text(apStrings[7]);
                TextFlow textFlow8 = new TextFlow(t8);
                anchorPane.getChildren().add(textFlow8);
                splitMenuButton.setText("Value: 2");
                break;
            case 3:
                splitMenuButton.getItems().set(8, splitMenuButton.getItems().get(8));
                anchorPane.getChildren().clear();
                anchorPane.setStyle("-fx-background-color: #e6fff4");
                Text t9 = new Text(apStrings[8]);
                TextFlow textFlow9 = new TextFlow(t9);
                anchorPane.getChildren().add(textFlow9);
                splitMenuButton.setText("Value: 3");
                break;
            case 5:
                splitMenuButton.getItems().set(9, splitMenuButton.getItems().get(9));
                anchorPane.getChildren().clear();
                anchorPane.setStyle("-fx-background-color: #e6ffee");
                Text t10 = new Text(apStrings[9]);
                TextFlow textFlow10 = new TextFlow(t10);
                anchorPane.getChildren().add(textFlow10);
                splitMenuButton.setText("Value: 5");
                break;
            case 8:
                splitMenuButton.getItems().set(10, splitMenuButton.getItems().get(10));
                anchorPane.getChildren().clear();
                anchorPane.setStyle("-fx-background-color: #e6ffe6");
                Text t11 = new Text(apStrings[10]);
                TextFlow textFlow11 = new TextFlow(t11);
                anchorPane.getChildren().add(textFlow11);
                splitMenuButton.setText("Value: 8");
                break;
        }
    }

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    /**
     * Behövs denna?
     */
    public void setAnchorPane() {

    }
}
