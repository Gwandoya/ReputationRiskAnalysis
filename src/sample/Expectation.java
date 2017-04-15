package sample;


public class Expectation {
    private String description;
    double weight;
    KPA kpa;
    Stakeholder stakeholder;

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

    public void setDescription(String description) {
        this.description = description;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
