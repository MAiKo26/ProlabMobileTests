package com.sama_consulting.prolabmobile5.Beans;

import java.io.Serializable;
import java.util.List;

public class Antibiogramme implements Serializable {
    String numAtb;
    String code;
    String libelle;
    String Prelevement;
    String Remarque;
    List<AntibiogrammeDetail> details;

    public Antibiogramme() {
    }

    public Antibiogramme(String numAtb, String code, String libelle, String prelevement, String remarque, List<AntibiogrammeDetail> details) {
        this.numAtb = numAtb;
        this.code = code;
        this.libelle = libelle;
        Prelevement = prelevement;
        Remarque = remarque;
        this.details = details;
    }

    public Antibiogramme(String numAtb, String code, String libelle, String prelevement, String remarque) {
        this.numAtb = numAtb;
        this.code = code;
        this.libelle = libelle;
        Prelevement = prelevement;
        Remarque = remarque;
        this.details = null;
    }

    public String getNumAtb() {
        return numAtb;
    }

    public void setNumAtb(String numAtb) {
        this.numAtb = numAtb;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getPrelevement() {
        return Prelevement;
    }

    public void setPrelevement(String prelevement) {
        Prelevement = prelevement;
    }

    public String getRemarque() {
        return Remarque;
    }

    public void setRemarque(String remarque) {
        Remarque = remarque;
    }

    public List<AntibiogrammeDetail> getDetails() {
        return details;
    }

    public void setDetails(List<AntibiogrammeDetail> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "Antibiogramme{" +
                "numAtb='" + numAtb + '\'' +
                ", code='" + code + '\'' +
                ", libelle='" + libelle + '\'' +
                ", Prelevement='" + Prelevement + '\'' +
                ", Remarque='" + Remarque + '\'' +
                ", details=" + details +
                '}';
    }
}
