package sample;


public class Expectation {
    private String description;
    private double weight = 0;
    private KPA kpa;
    private Stakeholder stakeholder;
    private RO ro;
    private int gpIndex;

    public Expectation(String description, double weight, KPA kpa, Stakeholder stakeholder) {
        this.description = description;
        this.weight = weight;
        this.kpa = kpa;
        this.stakeholder = stakeholder;
    }

    public String getDescription() {
        return description;
    }

    public double getWeight() {
        return weight;
    }

    public KPA getKpa() {
        return kpa;
    }

    public Stakeholder getStakeholder() {
        return stakeholder;
    }

    public RO getRo() { return ro; }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public void setRo(RO ro) { this.ro = ro; }

    public void setGpIndex(int index) { this.gpIndex = gpIndex; }

    public int getGpIndex() { return ro.getGridIndex(); }

    public boolean hasRO() {
        if (ro != null) return true;
        else return false;
    }
}
