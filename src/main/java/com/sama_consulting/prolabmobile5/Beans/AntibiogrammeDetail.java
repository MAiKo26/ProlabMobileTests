package com.sama_consulting.prolabmobile5.Beans;

import java.io.Serializable;

public class AntibiogrammeDetail implements Serializable {
    String Libelle;
    String DC;
    String Diametre;
    String ConcentrationCritique;
    String CMI;
    String Resultat;
    String ResultatInterpret;

    public AntibiogrammeDetail() {
    }

    public AntibiogrammeDetail(String libelle, String DC, String diametre, String concentrationCritique, String CMI, String resultat, String resultatInterpret) {
        Libelle = libelle;
        this.DC = DC;
        Diametre = diametre;
        ConcentrationCritique = concentrationCritique;
        this.CMI = CMI;
        Resultat = ConvertResult(resultat);
        ResultatInterpret = resultatInterpret;
    }

    public String getLibelle() {
        return Libelle;
    }

    public void setLibelle(String libelle) {
        Libelle = libelle;
    }

    public String getDC() {
        return DC;
    }

    public void setDC(String DC) {
        this.DC = DC;
    }

    public String getDiametre() {
        return Diametre;
    }

    public void setDiametre(String diametre) {
        Diametre = diametre;
    }

    public String getConcentrationCritique() {
        return ConcentrationCritique;
    }

    public void setConcentrationCritique(String concentrationCritique) {
        ConcentrationCritique = concentrationCritique;
    }

    public String getCMI() {
        return CMI;
    }

    public void setCMI(String CMI) {
        this.CMI = CMI;
    }

    public String getResultat() {
        return Resultat;
    }

    public void setResultat(String resultat) {
        Resultat = ConvertResult(resultat);
    }

    public String getResultatInterpret() {
        return ResultatInterpret;
    }

    public void setResultatInterpret(String resultatInterpret) {
        ResultatInterpret = resultatInterpret;
    }

    private String ConvertResult(String res) {
        if (res != null) {
            switch (res.toUpperCase()) {
                case "S":
                    return "SENSIBLE";
                case "R":
                    return "Résistant";
                case "In":
                    return "Intermédiaire";
                case "RB":
                    return "Resistant Bas";
                case "SB":
                    return "SENSIBLE Bas";
                default:
                    return res;
            }
        } else {
            return "";
        }
    }
}
