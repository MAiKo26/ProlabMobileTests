package com.sama_consulting.prolabmobile5.Beans;

/**
 * Created by MED ALI on 10/05/2017.
 */

public class DossierAnalyseDetail {

    private long numAnalyse;
    private String numDossier;
    private String numGroupe;
    private String abreviation;
    private String libelle;
    private Boolean estGroupe;
    private Boolean GT;
    private Boolean HN;
    private Boolean VT;
    private String resultat;
    private String unite1;
    private String unite2;
    private String valeurUsuelle;
    private String anteriotite;
    private String commentaire;
    private Long ordre;
    private String typeResultat;
    private Boolean controle;
    private Long etat;
    private String VUMin1;
    private String VUMax1;
    private String flagResult;
    private boolean A_Controler;

    public DossierAnalyseDetail(long numAnalyse, String numDossier, String numGroupe, String abreviation, String libelle, Boolean estGroupe, Boolean GT, Boolean HN, Boolean VT, String resultat, String unite1, String valeurUsuelle, String anteriotite, String commentaire, Long ordre, String typeResultat, Boolean controle, Long etat, String VUMin1, String VUMax1, String flagResult, boolean a_Controler) {
        this.numAnalyse = numAnalyse;
        this.numDossier = numDossier;
        this.numGroupe = numGroupe;
        this.abreviation = abreviation;
        this.libelle = libelle;
        this.estGroupe = estGroupe;
        this.GT = GT;
        this.HN = HN;
        this.VT = VT;
        this.resultat = resultat;
        this.unite1 = unite1;
        this.valeurUsuelle = valeurUsuelle;
        this.anteriotite = anteriotite;
        this.commentaire = commentaire;
        this.ordre = ordre;
        this.typeResultat = typeResultat;
        this.controle = controle;
        this.etat = etat;
        this.VUMin1 = VUMin1;
        this.VUMax1 = VUMax1;
        this.flagResult = flagResult;
        A_Controler = a_Controler;
    }

    public DossierAnalyseDetail() {
    }

    public DossierAnalyseDetail(long numAnalyse, String numDossier, String numGroupe, String abreviation, String libelle, Boolean estGroupe, Boolean GT, Boolean HN, Boolean VT, String resultat, String unite1, String valeurUsuelle, String anteriotite, String commentaire, Long ordre, String typeResultat, Boolean controle, Long etat, String VUMin1, String VUMax1, String flagResult,String unite2) {
        this.numAnalyse = numAnalyse;
        this.numDossier = numDossier;
        this.numGroupe = numGroupe;
        this.abreviation = abreviation;
        this.libelle = libelle;
        this.estGroupe = estGroupe;
        this.GT = GT;
        this.HN = HN;
        this.VT = VT;
        this.resultat = resultat;
        this.unite1 = unite1;
        this.valeurUsuelle = valeurUsuelle;
        this.anteriotite = anteriotite;
        this.commentaire = commentaire;
        this.ordre = ordre;
        this.typeResultat = typeResultat;
        this.controle = controle;
        this.etat = etat;
        this.VUMin1 = VUMin1;
        this.VUMax1 = VUMax1;
        this.flagResult = flagResult;
        this.unite2=unite2;
    }

    public String getUnite2() {
        return unite2;
    }

    public void setUnite2(String unite2) {
        this.unite2 = unite2;
    }

    public boolean isA_Controler() {
        return A_Controler;
    }

    public boolean getA_Controler() {
        return A_Controler;
    }

    public void setA_Controler(boolean a_Controler) {
        A_Controler = a_Controler;
    }

    public long getNumAnalyse() {
        return numAnalyse;
    }

    public void setNumAnalyse(long numAnalyse) {
        this.numAnalyse = numAnalyse;
    }

    public String getNumDossier() {
        return numDossier;
    }

    public void setNumDossier(String numDossier) {
        this.numDossier = numDossier;
    }

    public String getNumGroupe() {
        return numGroupe;
    }

    public void setNumGroupe(String numGroupe) {
        this.numGroupe = numGroupe;
    }

    public String getAbreviation() {
        return abreviation;
    }

    public void setAbreviation(String abreviation) {
        this.abreviation = abreviation;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Boolean getEstGroupe() {
        return estGroupe;
    }

    public void setEstGroupe(Boolean estGroupe) {
        this.estGroupe = estGroupe;
    }

    public Boolean getGT() {
        return GT;
    }

    public void setGT(Boolean GT) {
        this.GT = GT;
    }

    public Boolean getHN() {
        return HN;
    }

    public void setHN(Boolean HN) {
        this.HN = HN;
    }

    public Boolean getVT() {
        return VT;
    }

    public void setVT(Boolean VT) {
        this.VT = VT;
    }

    public String getResultat() {
        return resultat;
    }

    public void setResultat(String resultat) {
        this.resultat = resultat;
    }

    public String getUnite1() {
        return unite1;
    }

    public void setUnite1(String unite1) {
        this.unite1 = unite1;
    }

    public String getValeurUsuelle() {
        return valeurUsuelle;
    }

    public void setValeurUsuelle(String valeurUsuelle) {
        this.valeurUsuelle = valeurUsuelle;
    }

    public String getAnteriotite() {
        return anteriotite;
    }

    public void setAnteriotite(String anteriotite) {
        this.anteriotite = anteriotite;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Long getOrdre() {
        return ordre;
    }

    public void setOrdre(Long ordre) {
        this.ordre = ordre;
    }

    public String getTypeResultat() {
        return typeResultat;
    }

    public void setTypeResultat(String typeResultat) {
        this.typeResultat = typeResultat;
    }

    public Boolean getControle() {
        return controle;
    }

    public void setControle(Boolean controle) {
        this.controle = controle;
    }

    public Long getEtat() {
        return etat;
    }

    public void setEtat(Long etat) {
        this.etat = etat;
    }

    public String getVUMin1() {
        return VUMin1;
    }

    public void setVUMin1(String VUMin1) {
        this.VUMin1 = VUMin1;
    }

    public String getVUMax1() {
        return VUMax1;
    }

    public void setVUMax1(String VUMax1) {
        this.VUMax1 = VUMax1;
    }

    public String getFlagResult() {
        return flagResult;
    }

    public void setFlagResult(String flagResult) {
        this.flagResult = flagResult;
    }

    @Override
    public String toString() {
        return "DossierAnalyseDetail{" +
                "numAnalyse=" + numAnalyse +
                ", numDossier='" + numDossier + '\'' +
                ", numGroupe='" + numGroupe + '\'' +
                ", abreviation='" + abreviation + '\'' +
                ", libelle='" + libelle + '\'' +
                ", estGroupe=" + estGroupe +
                ", GT=" + GT +
                ", HN=" + HN +
                ", VT=" + VT +
                ", resultat='" + resultat + '\'' +
                ", unite1='" + unite1 + '\'' +
                ", valeurUsuelle='" + valeurUsuelle + '\'' +
                ", anteriotite='" + anteriotite + '\'' +
                ", commentaire='" + commentaire + '\'' +
                ", ordre=" + ordre +
                ", typeResultat='" + typeResultat + '\'' +
                ", controle=" + controle +
                ", etat=" + etat +
                ", VUMin1='" + VUMin1 + '\'' +
                ", VUMax1='" + VUMax1 + '\'' +
                ", flagResult='" + flagResult + '\'' +
                ", A_Controler=" + A_Controler +
                '}';
    }
}
