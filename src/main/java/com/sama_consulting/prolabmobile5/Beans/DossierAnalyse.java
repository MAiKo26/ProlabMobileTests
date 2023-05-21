package com.sama_consulting.prolabmobile5.Beans;

import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class DossierAnalyse implements Serializable {
    private int color;
    private String titre;
    private String Patient;
    private String Ans;
    private String Mois;
    private String Jours;
    private String Nenreg;
    private String Medecin;
    private String gsmMedecin;
    private String ListeAbreviation;
    private String ListeAnalysesTermines;
    private String ListeAnalysesEnCours;
    private Date datePrelevemnt;
    private Time heurePrelevemnt;
    private Integer status;
    private Integer resultFlag;
    private Boolean urgent;
    private Boolean livre;
    private Boolean signer;
    private Double solde;
    private Date promisLe;
    private Integer nbrImpr;
    private Integer EtatPublication;
    private ArrayList<DossierAnalyseDetail> analyses;

    public DossierAnalyse() {
    }

    public DossierAnalyse(int color, String patient, String nenreg, String medecin, Date datePrelevemnt, String gsmmedecin, Integer status) {
        this.color = color;
        Patient = patient;
        Nenreg = nenreg;
        Medecin = medecin;
        gsmMedecin = gsmmedecin;
        this.datePrelevemnt = datePrelevemnt;
    }

    public DossierAnalyse(int color, String titre, String patient, String ans, String mois, String jours, String nenreg, String medecin, String gsmMedecin, String listeAbreviation, String listeAnalysesTermines, String listeAnalysesEnCours, Date datePrelevemnt, Time heurePrelevemnt, Integer status, Integer resultFlag, Boolean urgent, Boolean livre, Boolean signer, Double solde, Date promisLe, Integer nbrImpr, Integer etatPublication, ArrayList<DossierAnalyseDetail> analyses) {
        this.color = color;
        this.titre = titre;
        Patient = patient;
        Ans = ans;
        Mois = mois;
        Jours = jours;
        Nenreg = nenreg;
        Medecin = medecin;
        this.gsmMedecin = gsmMedecin;
        ListeAbreviation = listeAbreviation;
        ListeAnalysesTermines = listeAnalysesTermines;
        ListeAnalysesEnCours = listeAnalysesEnCours;
        this.datePrelevemnt = datePrelevemnt;
        this.heurePrelevemnt = heurePrelevemnt;
        this.status = status;
        this.resultFlag = resultFlag;
        this.urgent = urgent;
        this.livre = livre;
        this.signer = signer;
        this.solde = solde;
        this.promisLe = promisLe;
        this.nbrImpr = nbrImpr;
        EtatPublication = etatPublication;
        this.analyses = analyses;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getPatient() {
        return Patient;
    }

    public void setPatient(String patient) {
        Patient = patient;
    }

    public String getAns() {
        return Ans;
    }

    public void setAns(String ans) {
        Ans = ans;
    }

    public String getMois() {
        return Mois;
    }

    public void setMois(String mois) {
        Mois = mois;
    }

    public String getJours() {
        return Jours;
    }

    public void setJours(String jours) {
        Jours = jours;
    }

    public String getNenreg() {
        return Nenreg;
    }

    public void setNenreg(String nenreg) {
        Nenreg = nenreg;
    }

    public String getMedecin() {
        return Medecin;
    }

    public void setMedecin(String medecin) {
        Medecin = medecin;
    }

    public String getGsmMedecin() {
        return gsmMedecin;
    }

    public void setGsmMedecin(String gsmMedecin) {
        this.gsmMedecin = gsmMedecin;
    }

    public String getListeAbreviation() {
        return ListeAbreviation;
    }

    public void setListeAbreviation(String listeAbreviation) {
        ListeAbreviation = listeAbreviation;
    }

    public String getListeAnalysesTermines() {
        return ListeAnalysesTermines;
    }

    public void setListeAnalysesTermines(String listeAnalysesTermines) {
        ListeAnalysesTermines = listeAnalysesTermines;
    }

    public String getListeAnalysesEnCours() {
        return ListeAnalysesEnCours;
    }

    public void setListeAnalysesEnCours(String listeAnalysesEnCours) {
        ListeAnalysesEnCours = listeAnalysesEnCours;
    }

    public Date getDatePrelevemnt() {
        return datePrelevemnt;
    }

    public void setDatePrelevemnt(Date datePrelevemnt) {
        this.datePrelevemnt = datePrelevemnt;
    }

    public Time getHeurePrelevemnt() {
        return heurePrelevemnt;
    }

    public void setHeurePrelevemnt(Time heurePrelevemnt) {
        this.heurePrelevemnt = heurePrelevemnt;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getResultFlag() {
        return resultFlag;
    }

    public void setResultFlag(Integer resultFlag) {
        this.resultFlag = resultFlag;
    }

    public Boolean getUrgent() {
        return urgent;
    }

    public void setUrgent(Boolean urgent) {
        this.urgent = urgent;
    }

    public Boolean getLivre() {
        return livre;
    }

    public void setLivre(Boolean livre) {
        this.livre = livre;
    }

    public Boolean getSigner() {
        return signer;
    }

    public void setSigner(Boolean signer) {
        this.signer = signer;
    }

    public Double getSolde() {
        return solde;
    }

    public void setSolde(Double solde) {
        this.solde = solde;
    }

    public Date getPromisLe() {
        return promisLe;
    }

    public void setPromisLe(Date promisLe) {
        this.promisLe = promisLe;
    }

    public Integer getNbrImpr() {
        return nbrImpr;
    }

    public void setNbrImpr(Integer nbrImpr) {
        this.nbrImpr = nbrImpr;
    }

    public Integer getEtatPublication() {
        return EtatPublication;
    }

    public void setEtatPublication(Integer etatPublication) {
        EtatPublication = etatPublication;
    }

    public ArrayList<DossierAnalyseDetail> getAnalyses() {
        return analyses;
    }

    public void setAnalyses(ArrayList<DossierAnalyseDetail> analyses) {
        this.analyses = analyses;
    }

    public String getAge() {
        String age = "";
        if (Ans == null) Ans = "";
        if (Mois == null) Mois = "";
        if (Jours == null) Jours = "";
        if (Ans.equals("")) Ans = "0";
        if (Mois.equals("")) Mois = "0";
        if (Jours.equals("")) Jours = "0";
        if (Integer.parseInt(Ans) >= 2)
            age = Ans + " ans";
        else if (Integer.parseInt(Ans) > 0) {
            age = "Un an";
            if (getMois() != null)
                if (Integer.parseInt(Mois) > 0)
                    age = age + " et " + Mois + " mois";
        } else if (getMois() != null)
            if (Integer.parseInt(Mois) > 0) {
                age = Mois + " mois";
                if (Integer.parseInt(Jours) > 0)
                    age = age + " et " + Jours + " jours";
            }
        return age;
    }
}
