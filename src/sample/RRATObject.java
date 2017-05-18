package sample;

import javafx.scene.control.TreeItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by dödsadde on 2017-05-17.
 */
public class RRATObject extends Vector {
    private String fileName = "Default.bin";
    private ArrayList stakeholders;
    private ArrayList kpas;
    private ArrayList expectations;
    private ArrayList ros;

    public RRATObject() {

    }

    public RRATObject(ArrayList<Stakeholder> stakeholders,
                      ArrayList<RO> ros,
                      ArrayList<Expectation> expectations,
                      ArrayList<KPA> kpas
    ) {
        this.stakeholders = stakeholders;
        this.ros = ros;
        this.expectations = expectations;
        this.kpas = kpas;
    }

    public void setRRATOBject(ArrayList<Stakeholder> stakeholders,
                              ArrayList<RO> ros,
                              ArrayList<Expectation> expectations,
                              ArrayList<KPA> kpas
    ) {
        this.stakeholders = stakeholders;
        this.ros = ros;
        this.expectations = expectations;
        this.kpas = kpas;
    }

    public String getFileName() {
        return fileName;
    }

    public ArrayList getStakeholders() {
        return stakeholders;
    }

    public ArrayList getKpas() {
        return kpas;
    }

    public ArrayList getExpectations() {
        return expectations;
    }

    public ArrayList getRos() {
        return ros;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "RRATObject{" +
                "stakeholders=" + stakeholders +
                ", kpas=" + kpas +
                ", expectations=" + expectations +
                ", ros=" + ros +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RRATObject that = (RRATObject) o;

        if (fileName != null ? !fileName.equals(that.fileName) : that.fileName != null) return false;
        if (stakeholders != null ? !stakeholders.equals(that.stakeholders) : that.stakeholders != null) return false;
        if (kpas != null ? !kpas.equals(that.kpas) : that.kpas != null) return false;
        if (expectations != null ? !expectations.equals(that.expectations) : that.expectations != null) return false;
        return ros != null ? ros.equals(that.ros) : that.ros == null;

    }

    @Override
    public int hashCode() {
        int result = fileName != null ? fileName.hashCode() : 0;
        result = 31 * result + (stakeholders != null ? stakeholders.hashCode() : 0);
        result = 31 * result + (kpas != null ? kpas.hashCode() : 0);
        result = 31 * result + (expectations != null ? expectations.hashCode() : 0);
        result = 31 * result + (ros != null ? ros.hashCode() : 0);
        return result;
    }
}
