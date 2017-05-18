package sample;

import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class saveFunction {
    final static FileChooser fileChooser = new FileChooser();

    /* // SPARKOD
    final DirectoryChooser directoryChooser =
                new DirectoryChooser();
        final File selectedDirectory =
                directoryChooser.showDialog(stage);
        if (selectedDirectory != null) {
            selectedDirectory.getAbsolutePath();
        }
     */

    public static void writeToFile(RRATObject mainObject) {
        try {
            ArrayList<Stakeholder> stakeholders = new ArrayList<>(HomeController.stakeholders);
            ArrayList<KPA> kpas = new ArrayList<>(HomeController.kpas);
            ArrayList<Expectation> expectations = new ArrayList<>(HomeController.expectations);
            ArrayList<RO> ros = new ArrayList<>(HomeController.ros);
            mainObject.setRRATOBject(stakeholders, ros, expectations, kpas);
            System.out.println(mainObject.toString());

            FileOutputStream fos = new FileOutputStream(mainObject.getFileName());
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(mainObject);
            oos.close();
        } catch (FileNotFoundException fne) {
            fne.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static void writeToChosenFile(RRATObject mainObject, Stage stage) {
        ArrayList<Stakeholder> stakeholders = new ArrayList<>(HomeController.stakeholders);
        ArrayList<KPA> kpas = new ArrayList<>(HomeController.kpas);
        ArrayList<Expectation> expectations = new ArrayList<>(HomeController.expectations);
        ArrayList<RO> ros = new ArrayList<>(HomeController.ros);
        mainObject.setRRATOBject(stakeholders, ros, expectations, kpas);
        System.out.println(mainObject.toString());

        configureFileChooser("Save as..");
        fileChooser.setInitialFileName(mainObject.getFileName());

        try {
            File file = fileChooser.showSaveDialog(stage);
            if (file != null) {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(mainObject);
                objectOutputStream.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            AlertBox.display("TEST1", "TEST1");
        } catch (IOException e) {
            e.printStackTrace();
            AlertBox.display("TEST2", "TEST2");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void readFromFile(Stage stage) {
        try {
            configureFileChooser("Load File");
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                FileInputStream fileInputStream = new FileInputStream(file);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                RRATObject rratObject = (RRATObject) objectInputStream.readObject();
                HomeController.handleLoadedFile(rratObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void readFromMultipleFiles(Stage stage) {
        List<File> list =
                fileChooser.showOpenMultipleDialog(stage);
        if (list != null) {
            /**
             * DO SOMETHING
             */
        }
    }

    public static void configureFileChooser(String title) {
        fileChooser.setTitle(title);
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Default", "*.bin")
        );
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    }
}
