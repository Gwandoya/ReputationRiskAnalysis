package sample;

import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.Serializable;

/**
 * Created by Andreas on 2017-04-19.
 */
public class RO implements Serializable {
    private Expectation expectation;
    private String risk = "";
    private int value = 0;
    private int gridIndex;

    public RO(Expectation expectation, String risk, int value, int gridIndex) {
        this.expectation = expectation;
        this.risk = risk;
        this.value = value;
        this.gridIndex = gridIndex;
        //expectation.setGpIndex(gridIndex);
        //this.splitMenuButton = splitMenuButton;
    }

    public void setRisk(String risk) {
        this.risk = risk;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setExpectation(Expectation expectation) {
        this.expectation = expectation;
    }

    public void setGridIndex(int gridIndex) {
        this.gridIndex = gridIndex;
    }

    /*
    public void setAnchorPane() {

    }
    */

    //public void setElements() { this.elements = elements; }

    /*
    public void setTextArea(TextArea textArea) {
        this.textArea = textArea;
    }
    */

    /*
    public void setTextField(TextField textField) {
        this.textField = textField;
    }
    */

    //public Elements getElements() { return elements; }

    public String getRisk() {
        return risk;
    }

    public int getValue() {
        return value;
    }

    public Expectation getExpectation() {
        return expectation;
    }

    public int getGridIndex() {
        return gridIndex;
    }

    //public TextArea getTextArea() { return textArea; }

    //public TextField getTextField() { return textField; }
}
