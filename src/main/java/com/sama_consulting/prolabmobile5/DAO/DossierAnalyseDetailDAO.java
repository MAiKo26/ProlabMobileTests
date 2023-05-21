package com.sama_consulting.prolabmobile5.DAO;

import android.content.Context;

import static com.sama_consulting.prolabmobile5.Util.UtilFunctions.executeUpdate;

public class DossierAnalyseDetailDAO {
    public static boolean FeedBackAnalyse(Context mContext,Long numAlalyse, String NumDossier, String NumGroupe, String Commentaire) {
        boolean res = false;
        String SQL = "UPDATE DossierAnalyseDetail SET Commentaire = '" + Commentaire  + "' where NumAnalyse=" + numAlalyse + " AND NumDossier='" + NumDossier + "' AND NumGroupe='" + NumGroupe + "';";
        try {
            res = executeUpdate(mContext, SQL);
        } catch (Exception e) {

        }
        return res;
    }

    public static boolean UpdateResultat(Context mContext,Long numAlalyse, String NumDossier, String NumGroupe,  String Resultat) {
        boolean res = false;
        String SQL = "UPDATE DossierAnalyseDetail SET Resultat = '" + Resultat +  "' where NumAnalyse=" + numAlalyse + " AND NumDossier='" + NumDossier + "' AND NumGroupe='" + NumGroupe + "';";
        try {
            res = executeUpdate(mContext, SQL);
        } catch (Exception e) {

        }
        return res;
    }

    public static boolean FeedBackAnalyse(Context mContext,Long numAlalyse, String NumDossier, String NumGroupe, boolean aControler) {
        boolean res = false;
        String SQL = "UPDATE DossierAnalyseDetail SET A_Controler='" + aControler + "' where NumAnalyse=" + numAlalyse + " AND NumDossier='" + NumDossier + "' AND NumGroupe='" + NumGroupe + "';";
        try {
            res = executeUpdate(mContext, SQL);
        } catch (Exception e) {

        }
        return res;
    }
}
