package sample;

/**
 * Created by Andreas on 2017-04-19.
 */
public class RO {
    private Expectation expectation;
    private String risk = "";
    private int value;
    private int gridIndex;

    public RO(Expectation expectation, String risk, int value, int gridIndex) {
        this.expectation = expectation;
        this.risk = risk;
        this.value = value;
        this.gridIndex = gridIndex;
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
}
