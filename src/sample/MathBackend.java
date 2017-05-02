package sample;

import java.util.ArrayList;

public class MathBackend {

    public static void calculateSTK() {
        Double x = null;
        Double y = null;
        Double z = null;
        for (Stakeholder s : HomeController.stakeholders) {
            s.mathP.clear();
            s.mathN.clear();
            if (s.getStkValue() > 0) {
                for (Expectation e : s.getExpectations()) {
                    try {
                        y = s.getStkValue() /100;
                    } catch (Exception e1) {
                        System.out.print("error - calculateChart - y");
                    }
                    try {
                        z = e.getWeight() / 100;
                    } catch (Exception e3) {
                        System.out.println("error - calculateChart - z");
                    }
                    try {
                        x = y * (e.getRo().getValue() * z);
                    } catch (Exception e2) {
                        System.out.print("error - calculateChart- x");
                    }
                    if (x >= 0)
                        s.addToPArrayList(x);
                    else
                        s.addToNArrayList(x);
                }
            }
        }

    }

    public static void calculateKPA() {
        Double x = null;
        Double y = null;
        Double z = null;
        for (KPA k : HomeController.kpas) {
            k.mathP.clear();
            k.mathN.clear();
            for (Expectation e : k.getExpectations()) {
                try {
                    y = e.getStakeholder().getStkValue() /100;
                } catch (Exception e1) {
                    System.out.println("Error -calcKpa -y");
                }
                try {
                    z = e.getWeight() /100;
                } catch (Exception e2) {
                    System.out.println("Error -calcKpa -z");
                }
                try {
                    x = y * (e.getRo().getValue() * z);
                } catch (Exception e3) {
                    System.out.println("Error - calcKpa -x");
                }
                if (x >=0)
                    k.addToPArrayList(x);
                else
                    k.addToNArrayList(x);

            }
        }

    }

}
