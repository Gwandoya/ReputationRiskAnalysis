package sample;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;


public class KPA {
    private HomeController homeController;
    private String name;
    private String desc;
    HashMap<Stakeholder, Expectation> expectationHashMap = new HashMap<>();
    ArrayList<Double> mathP = new ArrayList<>();
    ArrayList<Double> mathN = new ArrayList<>();

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
            stk.removeExpectation(this);
            expectationHashMap.remove(stk);
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void deleteKPA() {
        for (int i = 0; i < HomeController.expectations.size(); i++) {
            Expectation e = HomeController.expectations.get(i);
            if (e.getKpa().equals(this)) {
                double x = e.getStakeholder().getMaxValue();
                e.getStakeholder().setMaxValue(x + e.getWeight());
                expectationHashMap.remove(e);
                e.deleteExp();
            }
        }
        for (Stakeholder s : HomeController.stakeholders) {
            removeExpectationIfPresent(s);
        }
        HomeController.kpas.remove(this);
    }

    public void addToPArrayList(double xp) {
        mathP.add(xp);
    }

    public void addToNArrayList(double xn) {
        mathN.add(xn);
    }

    public Double getPvalue() {
        Double x = 0.0;
        for (Double d : mathP) {
            x = x + d;
        }
        return x;
    }

    public Double getNvalue() {
        Double y = 0.0;
        for (Double d : mathN) {
            y = y + d;
        }
        return y;
    }
}
