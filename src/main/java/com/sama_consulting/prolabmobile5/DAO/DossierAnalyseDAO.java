package com.sama_consulting.prolabmobile5.DAO;

import android.content.Context;

import com.sama_consulting.prolabmobile5.Beans.DossierAnalyse;
import com.sama_consulting.prolabmobile5.Beans.DossierAnalyseDetail;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.sama_consulting.prolabmobile5.DAO.ActionLogDAO.logAction;
import static com.sama_consulting.prolabmobile5.Util.UtilFunctions.executeQuery;
import static com.sama_consulting.prolabmobile5.Util.UtilFunctions.executeUpdate;
import static com.sama_consulting.prolabmobile5.Util.UtilFunctions.getColor;

public class DossierAnalyseDAO {

    public static void fillDossiers(Context mContext, List<DossierAnalyse> FullITEMS, List<DossierAnalyse> ITEMS, String prolabVersion, String filter) {
        ResultSet rs;
        String fields = " Nenreg,rtrim(titre) as titre,Nom+' '+ prenom as patient,Ans,Mois,Jours,DatePrelevement,isnull(Urgent,0) as Urgent,Statut,isnull(ResultFlag,0) as ResultFlag,cnam,livree,isnull(solde,0) as Solde,nbrimpr,typepatient,Signer,isnull(EtatPublication,0) as EtatPublication,isnull(Medecin.NomPrenom,'') as medecin,isnull(medecin.GSM,'') as gsmmedecin ,ListeAbreviation ";
        if (prolabVersion.equalsIgnoreCase("Prolab5"))
            fields = fields + ",PrintState,ListeAnalysesEnCours,ListeAnalysesTermines";
        else
            fields = fields + " ,0 as PrintState,SUBSTRING(REPLACE(ListeAbreviation, ';', '/'), 2, LEN(ListeAbreviation)) as ListeAnalysesEnCours,'' as ListeAnalysesTermines";
        String SQL;// = "select Top 100 " + fields + " from DossierAnalyse left outer join medecin on Codemedecin=Medecin.code";
        if (filter.isEmpty())
            SQL = "select Top 100 " + fields + " from DossierAnalyse left outer join medecin on Codemedecin=Medecin.code";
        else
            SQL = "select Top 300 " + fields + " from DossierAnalyse left outer join medecin on Codemedecin=Medecin.code" + " Where " + filter;
        SQL = SQL + " Order by DossierAnalyse.dateadd asc";
        try {
            rs = executeQuery(mContext, SQL);
            if (rs == null) {
                return;
            }
            ITEMS.clear();
            FullITEMS.clear();
            while (rs.next()) {
                ITEMS.add(createDossier(rs.getString("Nenreg"), rs.getString("patient"), rs.getString("Ans"), rs.getString("Mois"), rs.getString("Jours"), rs.getDate("DatePrelevement"), rs.getTime("DatePrelevement"),
                        rs.getBoolean("Urgent"), rs.getInt("Statut"), rs.getInt("ResultFlag"), rs.getBoolean("cnam"),
                        rs.getBoolean("livree"), rs.getBoolean("Signer"), rs.getInt("nbrimpr"),
                        rs.getInt("EtatPublication"), rs.getDouble("Solde"),
                        rs.getString("medecin"), rs.getString("titre"), rs.getString("gsmmedecin"),
                        rs.getString("ListeAbreviation"), rs.getString("ListeAnalysesEnCours"), rs.getString("ListeAnalysesTermines")
                ));
                FullITEMS.add(createDossier(rs.getString("Nenreg"), rs.getString("patient"), rs.getString("Ans"), rs.getString("Mois"), rs.getString("Jours"), rs.getDate("DatePrelevement"), rs.getTime("DatePrelevement"),
                        rs.getBoolean("Urgent"), rs.getInt("Statut"), rs.getInt("ResultFlag"), rs.getBoolean("cnam"),
                        rs.getBoolean("livree"), rs.getBoolean("Signer"), rs.getInt("nbrimpr"),
                        rs.getInt("EtatPublication"), rs.getDouble("Solde"),
                        rs.getString("medecin"), rs.getString("titre"), rs.getString("gsmmedecin"),
                        rs.getString("ListeAbreviation"), rs.getString("ListeAnalysesEnCours"), rs.getString("ListeAnalysesTermines")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void filter(List<DossierAnalyse> FullITEMS, List<DossierAnalyse> ITEMS, String PatientFilter) {
        String upperPatientFilter = PatientFilter.toUpperCase();
        ITEMS.clear();
        if (PatientFilter.isEmpty())
            for (DossierAnalyse dos : FullITEMS)
                ITEMS.add(dos);
        else
            for (DossierAnalyse dos : FullITEMS) {
                if (dos.getPatient().toUpperCase().contains(upperPatientFilter)||dos.getNenreg().toUpperCase().contains(upperPatientFilter)) {
                    ITEMS.add(dos);
                }
            }
    }

    public static DossierAnalyse createDossier(int position) {
        DossierAnalyse d = new DossierAnalyse();
        d.setNenreg(String.valueOf(position));
        d.setPatient("Patient ".concat(String.valueOf(position)));
        d.setDatePrelevemnt(new Date("12/12/2012"));// Quoi ?
        return d;
    }

    public static DossierAnalyse createDossier(String Nenreg, String patient, Date datePrelevement, String medecin, String titre, String gsmMedein) {
        DossierAnalyse d = new DossierAnalyse();
        d.setNenreg(Nenreg);
        d.setPatient(patient);
        d.setDatePrelevemnt(datePrelevement);
        d.setMedecin(medecin);
        d.setGsmMedecin(gsmMedein);
        d.setTitre(titre);
        return d;
    }

    public static DossierAnalyse createDossier(String Nenreg, String patient, String Ans, String Mois, String Jours, Date datePrelevement, Time heurePrelevement, Boolean urgent, Integer status, Integer resultFlag, Boolean cnam,
                                               Boolean livree, Boolean signer, Integer nbrimp, Integer EtatPublication, Double solde, String medecin, String titre, String gsmMedein, String listeAbreviation, String listeAnalysesEnCours, String listeAnalysesTermines) {
        DossierAnalyse d = new DossierAnalyse();
        d.setNenreg(Nenreg);
        d.setPatient(patient);
        d.setAns(Ans);
        d.setMois(Mois);
        d.setJours(Jours);
        d.setDatePrelevemnt(datePrelevement);
        d.setHeurePrelevemnt(heurePrelevement);
        d.setMedecin(medecin);
        d.setGsmMedecin(gsmMedein);
        d.setTitre(titre);
        d.setLivre(livree);
        d.setNbrImpr(nbrimp);
        d.setUrgent(urgent);
        d.setStatus(status);
        d.setSolde(solde);
        d.setSigner(signer);
        d.setEtatPublication(EtatPublication);
        d.setResultFlag(resultFlag);
        d.setColor(getColor(d.getResultFlag(), d.getStatus()));
        d.setListeAbreviation(listeAbreviation);
        d.setListeAnalysesEnCours(listeAnalysesEnCours);
        d.setListeAnalysesTermines(listeAnalysesTermines);
        return d;
    }

    public static DossierAnalyseDetail createDossierDetail(long numAnalyse, String numDossier, String numGroupe, String abreviation, String libelle, Boolean estGroupe, Boolean GT, String resultat, String unite1, String valeurUsuelle, String anteriotite, String commentaire, Long ordre,
                                                           Boolean HN, Boolean VT, String typeResultat, Boolean controle, Long etat, String flagResult, Boolean A_Controler,String unite2) {
        DossierAnalyseDetail da = new DossierAnalyseDetail();
        da.setNumDossier(numDossier);
        da.setNumAnalyse(numAnalyse);
        da.setNumGroupe(numGroupe);
        da.setAbreviation(abreviation);
        da.setLibelle(libelle);
        da.setResultat(resultat);
        da.setUnite1(unite1);
        da.setValeurUsuelle(valeurUsuelle);
        da.setAnteriotite(anteriotite);
        da.setCommentaire(commentaire);
        da.setEstGroupe(estGroupe);
        da.setHN(HN);
        da.setVT(VT);
        da.setTypeResultat(typeResultat);
        da.setControle(controle);
        da.setEtat(etat);
        da.setFlagResult(flagResult);
        da.setA_Controler(A_Controler);
        da.setUnite2(unite2);
        // da.setVUMax1(VUMin1);
        // da.setVUMin1(VUMax1);
        return da;
    }

    public static ArrayList<DossierAnalyseDetail> getAnalyses(Context mContext, String prolabVersion, String nenreg) {
        ArrayList<DossierAnalyseDetail> lda = new ArrayList<>();
        ResultSet rs;
        String SQL;// = "select Top 100 " + fields + " from DossierAnalyse left outer join medecin on Codemedecin=Medecin.code";
        if (prolabVersion.equalsIgnoreCase("Prolab5"))
            SQL = "select  Analyse.Num,NumDossier,Analyse.Abreviation,DossierAnalyseDetail.NumGroupe,DossierAnalyseDetail.A_Controler,Analyse.libelle,Analyse.CodeTube,Analyse.EtatPersonnalisee," +
                    "Analyse.DoubleSaisie,Analyse.NbrAnterioriteImp,Analyse.PrecisionVirguleUnite1,Analyse.PrecisionVirguleUnite2,Analyse.Section," +
                    " DossierAnalyseDetail.Resultat,DossierAnalyseDetail.GT,DossierAnalyseDetail.typeResultat,controle, DossierAnalyseDetail.Unite1, DossierAnalyseDetail.Unite2, DossierAnalyseDetail.VUMIN1, DossierAnalyseDetail.VUMAX1,DossierAnalyseDetail.ValeurUsuelle,DossierAnalyseDetail.Ordre,DossierAnalyseDetail.Commentaire,DossierAnalyseDetail.EstGroupe , DossierAnalyseDetail.HN, " +
                    "FormuleCalcul,Discipline.libelle as Discipline,isnull(Dossieranalysedetail.FlagResult,0) as FlagResultat ,isnull(VT,0) as VT, isnull(DossierAnalyseDetail.ETAT,0) as ETAT," +
                    " DispOrder= case DossierAnalyseDetail.NumGroupe  when '' then  Discipline.ordre  else  99999 end     ,  GRoupOrder =case DossierAnalyseDetail.NumGroupe  when '' then  ''  else  'ZZZZ' end \n" +
                    "   ,(SELECT  top 3  dd.Resultat +' (' +convert(varchar ,d1.DatePrelevement ,103)+')#'  \n" +
                    "FROM         DossierAnalyseDetail dd INNER JOIN\n" +
                    "                      DossierAnalyse d1 ON dd.NumDossier = d1.NEnreg\n" +
                    "WHERE     (d1.CodePatient = dossieranalyse.CodePatient ) and (d1.DatePrelevement < dossieranalyse.DatePrelevement ) AND (dd.NumAnalyse = DossierAnalyseDetail.NumAnalyse) and dd.resultat<>''\n" +
                    "ORDER BY d1.DatePrelevement Desc for xml path('')) as Anteriorite\n" +
                    "   \n" +
                    "   From DossierAnalyseDetail,Analyse,Discipline,DossierAnalyse\n" +
                    "   Where  DossierAnalyseDetail.NumAnalyse=Analyse.Num and discipline.code=analyse.codeDiscipline And\n" +
                    "         dossieranalyse.NEnreg=DossierAnalyseDetail.NumDossier and  DossierAnalyseDetail.numdossier='" + nenreg + "'\n" +
                    "Order by DispOrder,GRoupOrder,DossierAnalyseDetail.Ordre";
        else
            SQL = "select  Analyse.Num,NumDossier,Analyse.Abreviation,DossierAnalyseDetail.NumGroupe,0 as A_Controler,Analyse.libelle,Analyse.CodeTube," +
                    "Analyse.NbrAnterioriteImp," +
                    " DossierAnalyseDetail.Resultat,DossierAnalyseDetail.GT,DossierAnalyseDetail.typeResultat,controle, DossierAnalyseDetail.Unite1, DossierAnalyseDetail.Unite2,DossierAnalyseDetail.ValeurUsuelle,DossierAnalyseDetail.Ordre,DossierAnalyseDetail.Commentaire,DossierAnalyseDetail.EstGroupe , DossierAnalyseDetail.HN, " +
                    "FormuleCalcul,Discipline.libelle as Discipline,isnull(Dossieranalysedetail.FlagResult,0) as FlagResultat ,isnull(VT,0) as VT, 0 as ETAT," +
                    " DispOrder= case DossierAnalyseDetail.NumGroupe  when '' then  Discipline.ordre  else  99999 end     ,  GRoupOrder =case DossierAnalyseDetail.NumGroupe  when '' then  ''  else  'ZZZZ' end \n" +
                    "   ,(SELECT  top 3  dd.Resultat +' (' +convert(varchar ,d1.DatePrelevement ,103)+')#'  \n" +
                    "FROM         DossierAnalyseDetail dd INNER JOIN\n" +
                    "                      DossierAnalyse d1 ON dd.NumDossier = d1.NEnreg\n" +
                    "WHERE     (d1.CodePatient = dossieranalyse.CodePatient ) and (d1.DatePrelevement < dossieranalyse.DatePrelevement ) AND (dd.NumAnalyse = DossierAnalyseDetail.NumAnalyse) and dd.resultat<>''\n" +
                    "ORDER BY d1.DatePrelevement Desc for xml path('')) as Anteriorite\n" +
                    "   \n" +
                    "   From DossierAnalyseDetail,Analyse,Discipline,DossierAnalyse\n" +
                    "   Where  DossierAnalyseDetail.NumAnalyse=Analyse.Num and discipline.code=analyse.codeDiscipline And\n" +
                    "         dossieranalyse.NEnreg=DossierAnalyseDetail.NumDossier and  DossierAnalyseDetail.numdossier='" + nenreg + "'\n" +
                    "Order by DispOrder,GRoupOrder,DossierAnalyseDetail.Ordre";
        try {

            //Log.i("DATAx",con.toString() + con.getClientInfo().toString());

            rs = executeQuery( mContext, SQL);
            if (rs != null)
                while (rs.next()) {

                    lda.add(createDossierDetail(rs.getLong("Num"), rs.getString("NumDossier"), rs.getString("NumGroupe"), rs.getString("Abreviation"),
                            rs.getString("Libelle"), rs.getBoolean("EstGroupe"), rs.getBoolean("GT"), rs.getString("Resultat"), rs.getString("Unite1"),
                            rs.getString("Valeurusuelle"), rs.getString("Anteriorite"), rs.getString("Commentaire"), rs.getLong("Ordre"), rs.getBoolean("HN"), rs.getBoolean("VT"), rs.getString("typeResultat"), rs.getBoolean("controle"), rs.getLong("Etat"),
                            rs.getString("flagResultat"), rs.getBoolean("A_Controler"),rs.getString("unite2")));

                }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ArrayList<DossierAnalyseDetail> lda2 = new ArrayList<>();
        for (DossierAnalyseDetail da : lda

                ) {
            if (da.getNumGroupe().isEmpty())
                fillDa(lda, lda2, da);

        }
        return lda2;
    }

    public static void fillDa(ArrayList<DossierAnalyseDetail> src, ArrayList<DossierAnalyseDetail> dest, DossierAnalyseDetail da) {
        dest.add(da);
        if (da.getEstGroupe())

            for (DossierAnalyseDetail wda : src) {
                if (wda.getNumGroupe().equalsIgnoreCase(da.getNumGroupe() + da.getAbreviation()))
                    fillDa(src, dest, wda);
            }
    }

    public static boolean switchValider(Context mContext, DossierAnalyse da) {
        String SQL;
        if ((da.getStatus() & 4) == 0) {
            da.setStatus(da.getStatus() | 4);
            logAction(mContext, da.getNenreg(), "V", "Validation (mobile)");
        } else {
            da.setStatus(da.getStatus() & ~4);
            logAction(mContext, da.getNenreg(), "V", " Annuler Validation (mobile)");
        }
        SQL = "Update DossierAnalyse set Statut=" + da.getStatus() + " where nenreg='" + da.getNenreg() + "'";
        executeUpdate(mContext, SQL);
        // signature Automatique à la validation
        if (da.getSigner() && ((da.getStatus() & 4) == 0) || !da.getSigner() && ((da.getStatus() & 4) == 4))
            switchSignature(mContext,da);
        return true;
    }

    private static boolean switchSignature(Context mContext, DossierAnalyse da) {
        String SQL, SQLLog;
        if (!da.getSigner()) {
            da.setSigner(Boolean.TRUE);
            SQL = "Update DossierAnalyse Set signer=1 Where Nenreg='" + da.getNenreg() + "'";
            logAction(mContext, da.getNenreg(), "Signe", "Signer dossier (mobile)");
        } else {
            da.setSigner(Boolean.FALSE);
            logAction( mContext, da.getNenreg(), "Signe", " Hoter Signature dossier (mobile)");
            SQL = "Update DossierAnalyse Set signer=0 Where Nenreg='" + da.getNenreg() + "'";
        }
        return executeUpdate(mContext, SQL);

    }

    public static boolean aControler(Context mContext,String prolabVersion, String nenreg) {
        boolean res = false;
        for (DossierAnalyseDetail analyseDetail : getAnalyses(mContext, prolabVersion, nenreg)) {
            if (analyseDetail.getA_Controler() == true) {
                res = true;
                break;
            }
        }
        return res;
    }


}
