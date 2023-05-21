package com.sama_consulting.prolabmobile5;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.sama_consulting.prolabmobile5.Adapters.ListActivityRvAdapter;
import com.sama_consulting.prolabmobile5.Beans.DossierAnalyse;
import com.sama_consulting.prolabmobile5.DAO.ConnectionClass;
import com.sama_consulting.prolabmobile5.DAO.DossierAnalyseDAO;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * An activity representing a list of Dossiers. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link DossierDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class DossierListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    //private boolean mTwoPane;
    private ImageView female;
    private ImageView child;
    private ImageView male;
    private SearchView searchView;
    private Calendar calendar;
    private Calendar calendarAu;
    private TextView dateView;
    private TextView dateAu;
    private List<DossierAnalyse> ITEMS;
    private List<DossierAnalyse> FullITEMS;
    private DatePickerDialog.OnDateSetListener myDateListener1, myDateListener2;
    private SharedPreferences SP;
    private String ProlabVersion;
    private Integer year = -1, month = -1, day = -1, yearAu = -1, monthAu = -1, dayAu = -1;
    private String du, au;
    //private boolean isFirstStart = true;
    private String lastQuery = "";

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //setContentView(R.layout.activity_dossier_list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            recyclerView.invalidate();
            new prepareEverything(this).execute();
        }
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dossier_list);
        getSupportActionBar().setSubtitle(getString(R.string.online));
        SP = PreferenceManager.getDefaultSharedPreferences(this);
        recyclerView = (RecyclerView) findViewById(R.id.dossier_list);
        dateView = (TextView) findViewById(R.id.txtDate);
        dateAu = findViewById(R.id.txtDateAu);
        ITEMS = new ArrayList<DossierAnalyse>();
        FullITEMS = new ArrayList<DossierAnalyse>();
        calendar = Calendar.getInstance();
        calendarAu = Calendar.getInstance();
        ProlabVersion = SP.getString("prolabVersion", "Prolab5");
        searchView = (SearchView) findViewById(R.id.searchFilter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                DossierAnalyseDAO.filter(FullITEMS, ITEMS, query);
                recyclerView.setAdapter(new ListActivityRvAdapter(getBaseContext(), ProlabVersion, ITEMS));
                //Toast.makeText( getApplicationContext(),"onQueryTextSubmit",Toast.LENGTH_LONG).show();
                lastQuery = query;
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                DossierAnalyseDAO.filter(FullITEMS, ITEMS, newText);
                recyclerView.setAdapter(new ListActivityRvAdapter(getBaseContext(), ProlabVersion, ITEMS));
                lastQuery = newText;
                return false;
            }
        });

        female = new ImageView(recyclerView.getContext());
        female.setImageResource(R.drawable.patient_female);
        child = new ImageView(recyclerView.getContext());
        child.setImageResource(R.drawable.patient_child);
        male = new ImageView(recyclerView.getContext());
        male.setImageResource(R.drawable.patient_male);
        recyclerView.setAdapter(new ListActivityRvAdapter(getBaseContext(), ProlabVersion, ITEMS));
        myDateListener1 = new
                DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
                        calendar.set(arg1, arg2, arg3);
                        new prepareEverything(DossierListActivity.this).execute();
                    }
                };
        myDateListener2 = new
                DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
                        calendarAu.set(arg1, arg2, arg3);
                        new prepareEverything(DossierListActivity.this).execute();
                    }
                };
        //new prepareEverything(this).execute();

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 1) {
            if (day == -1) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT, myDateListener1, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(calendarAu.getTimeInMillis());
                return datePickerDialog;
            } else {
                DatePickerDialog datePickerDialog = new DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT, myDateListener1, year, month - 1, day);
                datePickerDialog.getDatePicker().setMaxDate(calendarAu.getTimeInMillis());
                return datePickerDialog;
            }

        } else if (id == 2) {
            if (dayAu == -1) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT, myDateListener2, calendarAu.get(Calendar.YEAR), calendarAu.get(Calendar.MONTH), calendarAu.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
                return datePickerDialog;

            } else {
                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        AlertDialog.THEME_HOLO_LIGHT, myDateListener2, yearAu, monthAu - 1, dayAu);
                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
                return datePickerDialog;
            }
        }
        return null;
    }

    public void setDate(View view) {
        showDialog(1);
    }

    public void setDateAu(View view) {
        showDialog(2);
    }

    @Override
    public void onBackPressed() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setMessage(R.string.exitmsg);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                System.exit(0);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        android.support.v7.app.AlertDialog alert = builder.create();
        alert.show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        /*if (isFirstStart == true) {
            //Toast.makeText(this,"First Start",Toast.LENGTH_LONG).show();
            isFirstStart = false;
        } else {
            Toast.makeText(this,"NOT First Start",Toast.LENGTH_LONG).show();*/
        recyclerView.invalidate();
        new prepareEverything(this).execute();
        //}
    }

    private class prepareEverything extends AsyncTask<Void, Void, Boolean> {
        Context mContext;
        ProgressDialog pd;

        public prepareEverything(Context mContext) {
            this.mContext = mContext;
            pd = new ProgressDialog(mContext);
            pd.setTitle(getString(R.string.please_wait));
            pd.setMessage(getString(R.string.loading));
            pd.setIndeterminate(false);
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.setCancelable(false);
            //--------------
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH) + 1;
            day = calendar.get(Calendar.DAY_OF_MONTH);
            yearAu = calendarAu.get(Calendar.YEAR);
            monthAu = calendarAu.get(Calendar.MONTH) + 1;
            dayAu = calendarAu.get(Calendar.DAY_OF_MONTH);
            du = new StringBuilder().append(day).append("/")
                    .append(month).append("/").append(year).toString();
            au = new StringBuilder().append(dayAu).append("/")
                    .append(monthAu).append("/").append(yearAu).toString();
        }

        @Override
        protected void onPreExecute() {
            pd.show();
            dateView.setText(du);
            dateAu.setText(au);
        }

        @Override
        protected void onCancelled() {
            pd.dismiss();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            boolean res = false;
            try {
                    DossierAnalyseDAO.fillDossiers(mContext, FullITEMS, ITEMS, ProlabVersion, " Dateprelevement between '" + du + "'  And dateadd(day,1,'" + au + "') ");
                    res = true;
            } catch (Exception e) {
            res = false;
            }
            return res;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {

                DossierAnalyseDAO.filter(FullITEMS, ITEMS, lastQuery);
                recyclerView.setAdapter(new ListActivityRvAdapter(mContext, ProlabVersion, ITEMS));
                ViewCompat.setNestedScrollingEnabled(recyclerView, false);
                Snackbar.make(findViewById(R.id.dossier_list_Activity), getString(R.string.nbrdoss) + " " + du + " " + getString(R.string.au) + " " + au + " : " + ITEMS.size() + " " + getString(R.string.doss), Snackbar.LENGTH_LONG).show();
            } else {
                Snackbar.make(findViewById(R.id.dossier_list_Activity), R.string.err1, Snackbar.LENGTH_LONG).show();
            }

            pd.dismiss();
            super.onPostExecute(aBoolean);
        }
    }
}
