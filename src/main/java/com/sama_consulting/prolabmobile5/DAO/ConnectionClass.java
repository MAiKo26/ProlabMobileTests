package com.sama_consulting.prolabmobile5.DAO;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionClass {
    private static int TIMEOUT = 5;
    static Connection cn;

    public static Connection connectTodatabase(Context mContext) {
        String dbuser, serverip, dbname, dbpassword, port, prolabVersion, instance, userName, userPassword;
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(mContext);
        String driver = "net.sourceforge.jtds.jdbc.Driver";
        serverip = SP.getString("server_address", "41.230.30.215");
        prolabVersion = SP.getString("prolabVersion", "Prolab5");
        instance = SP.getString("instance", "");
        dbuser = SP.getString("dbuser", "sa");
        dbname = SP.getString("dbname", "Prolab");
        dbpassword = SP.getString("dbpassword", "");
        port = SP.getString("port", "7733");
        userName = SP.getString("userName", "Biologiste");
        userPassword = SP.getString("userPassword", "");
        String url = "jdbc:jtds:sqlserver://" + serverip + ":" + port
                + ";DatabaseName=" + dbname
                + ";Instance=" + instance;
        Connection con = null;
        try {
            Class.forName(driver);
            DriverManager.setLoginTimeout(TIMEOUT);
            con = DriverManager.getConnection(url, dbuser, dbpassword);
        } catch (Exception ex) {
            Log.e("DATAx", "connectTodatabase " + ex.getMessage());
        }

        return con;
    }

    public static Connection getConnection(Context mContext) {
        if (cn == null) {
            cn = ConnectionClass.connectTodatabase(mContext);
            if (cn == null) {
                return null;
            }
        }
        try {
            if (cn.isClosed()) {
                //cn.close();
                cn = ConnectionClass.connectTodatabase(mContext);
                if (cn == null || cn.isClosed()) {
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cn;
    }

    public static boolean isConnectedNetwork(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        if (networkInfo != null && networkInfo.isConnected()) {
            NetworkInfo.State networkState = networkInfo.getState();
            return (networkState.compareTo(NetworkInfo.State.CONNECTED) == 0);
        } else {
            return false;
        }
    }
}
