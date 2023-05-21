package com.sama_consulting.prolabmobile5.DAO;

import com.sama_consulting.prolabmobile5.Util.UtilFunctions;

import java.sql.ResultSet;
import java.sql.Statement;

public class UserLoginDAO {
    public static boolean Login(String Username, String Password) {
        boolean res = false;
        try {
            Statement statement = ConnectionClass.getConnection(null).createStatement();
            String queryString = "SELECT COUNT(*) FROM Utilisateur where Nom = " + UtilFunctions.getSqlParamText(Username) + " and Password = " + UtilFunctions.getSqlParamText(UtilFunctions.encrypt(Password));
            ResultSet rs = statement.executeQuery(queryString);
            while (rs.next()) {
                if (rs.getInt(1) == 1) {
                    res = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}
