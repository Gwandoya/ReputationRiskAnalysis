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
                    if (e.getRo().getValue() >= 0 ) {
                        try {
                            x = y * (e.getRo().getValue() * z);
                        } catch (Exception e2) {
                            System.out.print("error - calculateChart- x");
                        }
                        if (x != null)
                            s.addToPArrayList(x);
                    } else if (e.getRo().getValue() < 0) {
                        x = s.getStkValue() * (e.getRo().getValue() * z);
                        s.addToNArrayList(x);
                    }
                }
            }
        }

    }

    public static void calculateKPA() {

    }

}
