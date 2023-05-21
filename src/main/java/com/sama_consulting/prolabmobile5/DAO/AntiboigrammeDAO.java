package com.sama_consulting.prolabmobile5.DAO;

import android.content.Context;

import com.sama_consulting.prolabmobile5.Beans.Antibiogramme;
import com.sama_consulting.prolabmobile5.Beans.AntibiogrammeDetail;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static com.sama_consulting.prolabmobile5.Util.UtilFunctions.executeQuery;
import static com.sama_consulting.prolabmobile5.Util.UtilFunctions.executeUpdate;

public class AntiboigrammeDAO {
    public static List<Antibiogramme> getAll(Context mContext, String nenreg) {
        List<Antibiogramme> antibiogrammeDetailList = new ArrayList<Antibiogramme>();
        String SQL = "SELECT ATB.Num, Germe.Code As 'Code Germe', Germe.Libelle As 'Nom Germe', ATB.Prélèvement, ATB.Remarque FROM   ATB INNER JOIN Germe ON ATB.NumGerme = Germe.Num Where  ATB.NumDossier = '" + nenreg + "';";
        ResultSet rs = executeQuery(mContext, SQL);
        try {
            while (rs.next()) {
                antibiogrammeDetailList.add(new Antibiogramme(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Antibiogramme a : antibiogrammeDetailList) {
            a.setDetails(getAntibiogrammeDetails(mContext, a.getNumAtb()));
        }

        return antibiogrammeDetailList;
    }

    private static List<AntibiogrammeDetail> getAntibiogrammeDetails(Context mContext,String NumeroATB) {
        List<AntibiogrammeDetail> antibiogrammeDetailList = new ArrayList<AntibiogrammeDetail>();
        String SQL = "SELECT Antibiotique.Libelle, ATBDetail.DC, ATBDetail.Diametre, ATBDetail.ConcentrationCritique, ATBDetail.CMI, ATBDetail.Resultat, ATBDetail.ResultatInterpret FROM  ATBDetail INNER JOIN Antibiotique ON ATBDetail.NumAntibiotique = Antibiotique.Num WHERE ATBDetail.NumATB = " + NumeroATB + " ORDER BY ATBDetail.num asc;";
        ResultSet rs = executeQuery(mContext, SQL);
        try {
            while (rs.next()) {
                antibiogrammeDetailList.add(new AntibiogrammeDetail(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return antibiogrammeDetailList;
    }

    public static boolean setRemarque(Context mContext, String NumeroATB, String Remarque) {
        String SQL = "UPDATE ATB SET Remarque = '" + Remarque.replace("'", "''") + "' where num = " + NumeroATB + ";";
        return executeUpdate(mContext, SQL);
    }

}