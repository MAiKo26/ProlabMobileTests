package com.sama_consulting.prolabmobile5;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.sama_consulting.prolabmobile5.DAO.ConnectionClass;
import com.sama_consulting.prolabmobile5.DAO.UserLoginDAO;

import java.sql.Connection;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class Splash extends AppCompatActivity {

    private SharedPreferences SP;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //setContentView(R.layout.activity_dossier_list);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().setSubtitle(getString(R.string.se_connecter));
        SP = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        checkCredentials(null);
    }

    @Override
    protected void onResume() {
        checkCredentials(null);
        super.onResume();
    }

    public void checkCredentials(View V) {
        String serverip = SP.getString("server_address", "NONE");
        String dbuser = SP.getString("dbuser", "NONE");
        String port = SP.getString("port", "NONE");
        String userName = SP.getString("userName", "NONE");
        if ((serverip.equalsIgnoreCase("NONE"))
                || (dbuser.equalsIgnoreCase("NONE"))
                || (port.equalsIgnoreCase("NONE"))
                || (userName.equalsIgnoreCase("NONE"))) {
            Snackbar.make(findViewById(R.id.splashScreenLayout), R.string.info1, Snackbar.LENGTH_LONG).show();
        } else {
            /**
             * 1- Check DB Connection
             * 2- IF OK, Check User Credentials
             * 3- IF OK, Connect to {@link DossierListActivity}
             */
            new ConnectionTask(this).execute();
        }
    }

    private class ConnectionTask extends AsyncTask<Void, Void, Integer> {
        ProgressDialog dialog;
        Context appContext;

        public ConnectionTask(Context mContext) {
            appContext = mContext;
            dialog = new ProgressDialog(appContext);
            dialog.setMessage(getString(R.string.connect_encours));
            dialog.setIndeterminate(true);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setCancelable(false);
        }

        @Override
        protected void onPreExecute() {
            dialog.show();
        }


        @Override
        protected Integer doInBackground(Void... voids) {
            /**
             * RETURN 0 : SUCCESS
             * RETURN 1 : NULL CONNECTION
             * RETURN 2 : USER & PWD ARE INCORRECT
             * RETURN 3 : EXCEPTION OCCURED
             */
            int res = 3;
            Connection c;
            try {
                c = ConnectionClass.getConnection(getApplicationContext());
                if (c != null) {
                    if (UserLoginDAO.Login(SP.getString("userName", ""), SP.getString("userPassword", ""))) {
                        res = 0;
                    } else {
                        res = 2;
                    }
                } else {
                    res = 1;
                }
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return res;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            try {
                this.dialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPostExecute(Integer result) {
            try {
                this.dialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
            switch (result) {
                case 0:
                    startActivity(new Intent(getApplicationContext(), DossierListActivity.class));
                    try {
                        this.dialog.dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    this.dialog=null;
                    ((Activity) appContext).finish();
                    break;
                case 1:
                    if(ConnectionClass.isConnectedNetwork(getApplicationContext())) {
                        Snackbar.make(findViewById(R.id.splashScreenLayout), R.string.err1, Snackbar.LENGTH_LONG).show();
                        break;
                    }else {
                        Snackbar.make(findViewById(R.id.splashScreenLayout), R.string.erreur_internet, Snackbar.LENGTH_LONG).show();
                        break;
                    }
                case 2:
                    Snackbar.make(findViewById(R.id.splashScreenLayout), R.string.err2, Snackbar.LENGTH_LONG).show();
                    break;
                case 3:
                    Snackbar.make(findViewById(R.id.splashScreenLayout), R.string.err3, Snackbar.LENGTH_LONG).show();
                    break;
            }
        }

    }

}
