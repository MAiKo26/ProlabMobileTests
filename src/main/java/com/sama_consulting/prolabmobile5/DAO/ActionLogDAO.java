package com.sama_consulting.prolabmobile5.DAO;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static com.sama_consulting.prolabmobile5.Util.UtilFunctions.executeUpdate;

public class ActionLogDAO {
    public static void logAction(Context mContext, String numDossier, String action, String description) {
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(mContext);
        String userName = SP.getString("userName", "");
        String SQLLog = "INSERT INTO [ActionLog]([NumDossier],[DateUpdate],[User],[Poste],[Action],[Description],[ActionDate])\n" +
                "     VALUES('" + numDossier + "',GetDate(),'" + userName + "',Host_Name(),'" + action + "','" + description + "',GetDate()) ";
        try {
            executeUpdate(mContext, SQLLog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
