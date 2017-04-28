package sample;

import java.util.Collection;
import java.util.HashMap;


public class Stakeholder {
    private HomeController homeController;
    private String name;
    private String desc;
    private double maxValue = 100.0;
    private double stkValue;
    HashMap<KPA, Expectation> expectationHashMap = new HashMap<>();

    public Stakeholder(HomeController homeController, String name, String desc) {
        this.homeController = homeController;
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
        if (!expectationHashMap.containsValue(expectation)) {
            addExpectation(expectation);
        }
    }

    public void removeExpectation(Expectation expectation) {
        homeController.expectations.remove(expectation);
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
            kpa.removeExpectation(this);
            expectationHashMap.remove(kpa);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    public void setStkValue(double stkValue) {
        this.stkValue = stkValue;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public Double getMaxValue() {
        return maxValue;
    }

    public Double getStkValue() {
        return stkValue;
    }

    public boolean getDistBoolean() {
        if (maxValue == 0) return true;
        else return false;
    }

    public void deleteSTK() {
        for (Expectation e : expectationHashMap.values()) {
            e.deleteExp();
            expectationHashMap.remove(e);
        }
        for (KPA k : HomeController.kpas) {
            removeExpectationIfPresent(k);
        }
        HomeController.stakeholders.remove(this);
    }
}
