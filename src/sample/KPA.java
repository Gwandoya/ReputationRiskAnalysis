package sample;

import java.util.Collection;
import java.util.HashMap;


public class KPA {
    private HomeController homeController;
    private String name;
    private String desc;
    HashMap<Stakeholder, Expectation> expectationHashMap = new HashMap<>();

    public KPA(HomeController homeController, String name, String desc) {
        this.homeController = homeController;
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
        if (!expectationHashMap.containsValue(expectation)) {
            addExpectation(expectation);
        }
    }

    public void removeExpectation(Expectation expectation) {
        homeController.expectations.remove(expectation);
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
