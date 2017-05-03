package sample;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class MathBackend {
    private static Double rtVD = 0.0;
    private static Double opVD = 0.0;

    /**
     * Method för att kalkylera fav/thr för alla stakeholders.
     */
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
                        System.out.println("Error - Calculate STK - y " + e.getStakeholder().getName() + "~" + e.getKpa().getName());
                        y = 0.0;
                    }
                    try {
                        z = e.getWeight() / 100;
                    } catch (Exception e3) {
                        System.out.println("Error - Calculate STK - z " + e.getStakeholder().getName() + "~" + e.getKpa().getName());
                        z = 0.0;
                    }
                    try {
                        x = y * (e.getRo().getValue() * z);
                    } catch (Exception e2) {
                        System.out.println("Error - Calculate STK - x " + e.getStakeholder().getName() + "~" + e.getKpa().getName());
                        x = 0.0;
                    }
                    if (x >= 0)
                        s.addToPArrayList(x);
                    else
                        s.addToNArrayList(x);
                }
            }
        }

    }

    /**
     * Method för att kalkylera fav/thr för alla kpa:s.
     */
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
                    System.out.println("Error - Calculate Kpa -y " + e.getKpa().getName() + "~" + e.getStakeholder().getName());
                    y = 0.0;
                }
                try {
                    z = e.getWeight() /100;
                } catch (Exception e2) {
                    System.out.println("Error - calculate Kpa -z " + e.getKpa().getName()+ "~" + e.getStakeholder().getName());
                    z = 0.0;
                }
                try {
                    x = y * ((e.getRo().getValue() * z));
                } catch (Exception e3) {
                    System.out.println("Error - Calculate Kpa -x " + e.getKpa().getName()+ "~" + e.getStakeholder().getName());
                    x = 0.0;
                }
                if (x >=0)
                    k.addToPArrayList(x);
                else
                    k.addToNArrayList(x);
            }
        }
    }

    /**
     * Samlar ihop den totala possitiva summan av Opportunity Favors.
     */
    public static Double calculateOPV() {
        opVD = 0.0;
        for (Stakeholder s : HomeController.stakeholders) {
            for (Double d : s.mathP) {
                opVD = opVD + d;
            }
        }
        return (double)Math.round(opVD * 1000d) / 1000d;
    }

    /**
     * Samlar ihop den totala negativa summan av Risk Threats.
     */
    public static Double calculateRTV() {
        rtVD = 0.0;
        for (Stakeholder s : HomeController.stakeholders) {
            for (Double d : s.mathN) {
                rtVD = rtVD + d;
            }
        }
        return (double)Math.round(rtVD * 1000d) / 1000d;
    }

    /**
     * Adderar opVD och rtVD till ett "global-värde"
     */
    public static Double calculateGVV() {
        Double gvVD = opVD + rtVD;
        return (double)Math.round(gvVD * 1000d) / 1000d;
    }


    /**
     * Method för att kontrollera att outputen ser korrekt ut
     *
     * !SKA TAS BORT INNAN "PRODUCTION"!
     */
    public static void debugPrints() {
        /**
         * * Debug-kod för MathBackend.calculateSTK
         */
        System.out.println("Stakeholders:");
        for (Stakeholder s : HomeController.stakeholders) {
            System.out.println(s.getName());
            int i = 1;
            for (Double d : s.mathP) {
                System.out.println("P-Tal " + i + ": " + d);
                i++;
            }
            i = 1;
            for (Double d : s.mathN) {
                System.out.println("N-Tal " + i + ": " + d);
                i++;
            }
            System.out.println("PV: " + s.getPvalue());
            System.out.println("NV: " + s.getNvalue());
        }
        /**
         * Debug-kod för MathBackend.calculateKPA
         */
        System.out.println("KPAs");
        for (KPA k : HomeController.kpas) {
            System.out.println(k.getName());
            int i = 1;
            for (Double d : k.mathP) {
                System.out.println("P-Tal " + i + ": " + d);
                i++;
            }
            i = 1;
            for (Double d : k.mathN) {
                System.out.println("N-Tal " + i + ": " + d);
                i++;
            }
            System.out.println("PV: " + k.getPvalue());
            System.out.println("NV: " + k.getNvalue());

        }
    }

    public static Double calculateSFT(int i) {
        Double p = 0.0;
        Double n = 0.0;
        for (Stakeholder s : HomeController.stakeholders) {
            for (Double d : s.mathP) {
                p = p + d;
            }
            for (Double d : s.mathN) {
                n = n + d;
            }
        }
        if (i == 0)
            return (double)Math.round(p * 1000d) / 1000d;
        else
            return (double)Math.round(n * 1000d) / 1000d;
    }

    public static Double calculateKFT(int i) {
        Double p = 0.0;
        Double n = 0.0;
        for (KPA k : HomeController.kpas) {
            for (Double d : k.mathP) {
                p = p + d;
            }
            for (Double d : k.mathN) {
                n = n + d;
            }
        }
        if (i == 0)
            return (double)Math.round(p * 1000d) / 1000d;
        else
            return (double)Math.round(n * 1000d) / 1000d;
    }
}
