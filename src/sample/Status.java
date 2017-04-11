package sample;

/**
 * Created by dödsadde on 2017-04-10.
 */
public class Status implements Runnable {

    private Thread t;
    private static String m;
    private String d = "";

    @Override
    public void run() {
        HomeController.updateStatusLabel(m);
        try{
            Thread.sleep(10000);
        } catch (Exception e) {
            System.out.print(e);
        }
        HomeController.updateStatusLabel(d);
    }

    public static void status(String s) {
        m = s;
        Thread t = new Thread(new Status());
        t.start();
    }

}
