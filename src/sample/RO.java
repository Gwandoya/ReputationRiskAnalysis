package sample;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * Created by Andreas on 2017-04-19.
 */
public class RO {
    private Expectation expectation;
    private String risk = "";
    private int value;
    private int gridIndex;
    private TextArea textArea;
    private TextField textField;

    public RO(Expectation expectation, String risk, int value, int gridIndex, TextArea textArea, TextField textField) {
        this.expectation = expectation;
        this.risk = risk;
        this.value = value;
        this.gridIndex = gridIndex;
        //expectation.setGpIndex(gridIndex);
        this.textArea = textArea;
        this.textField = textField;
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

    public void setTextArea(TextArea textArea) {
        this.textArea = textArea;
    }

    public void setTextField(TextField textField) {
        this.textField = textField;
    }

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

    public TextArea getTextArea() { return textArea; }

    public TextField getTextField() { return textField; }
}
