package com.sama_consulting.prolabmobile5;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sama_consulting.prolabmobile5.Beans.Antibiogramme;
import com.sama_consulting.prolabmobile5.Beans.AntibiogrammeListHolder;
import com.sama_consulting.prolabmobile5.Beans.DossierAnalyse;
import com.sama_consulting.prolabmobile5.Beans.DossierAnalyseDetail;
import com.sama_consulting.prolabmobile5.DAO.AntiboigrammeDAO;
import com.sama_consulting.prolabmobile5.DAO.DossierAnalyseDAO;
import com.sama_consulting.prolabmobile5.DAO.DossierAnalyseDetailDAO;
import com.sama_consulting.prolabmobile5.Dialogs.AnalyseFeedBack;
import com.sama_consulting.prolabmobile5.Util.UtilFunctions;

import java.util.ArrayList;
import java.util.List;

import static com.sama_consulting.prolabmobile5.DAO.DossierAnalyseDAO.switchValider;

public class ResultatDosActivity extends AppCompatActivity {
    public static final String ARG_ITEM_ID = "DOSSIER";
    private Menu menu;
    private ImageView mImgPatient;
    private TextView mAge;
    private ImageView mLivre;
    private ImageView mValide;
    private ImageView mImprime;
    private ImageView mSignee;
    private TextView mNenreg;
    private TextView mPatient;
    private TextView mMedecin;
    private TextView mGsmMedecin;
    private TextView mDatePrelevement;
    private LinearLayout lyMedecin, toolbar_layout;
    private QuickContactBadge qcbGsmMedein;
    private RecyclerView recyclerView;
    private CollapsingToolbarLayout appBarLayout;
    private ProgressDialog pd;
    private SharedPreferences SP;
    private String ProlabVersion;
    private boolean estVerrouille;
    private FloatingActionButton fab, fabcheck;
    /**
     * specify @state value
     * -1 : UNSPECIFIED (onInit)
     * 0: NOT SIGNED - NOT CHECKED
     * 1: NOT SIGNED - CHECKED
     * 2: SIGNED - CHECKED
     */
    private int state;
    private DossierAnalyse dossierAnalyse;
    private List<DossierAnalyse> ITEMS;
    ArrayList<DossierAnalyseDetail> Details;
    private int currIndice;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mInflater = getMenuInflater();
        mInflater.inflate(R.menu.menu_resultat, menu);
        this.menu = menu;
        /**
         * To avoid menu initialisation exception
         */
        new InitTask(ResultatDosActivity.this).execute();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        fab.setVisibility(View.GONE);
        if (id == R.id.action_arriere) {
            recyclerView.setAdapter(new DetailActivityRvAdapter(getApplicationContext(), new ArrayList<DossierAnalyseDetail>(), "", ""));
            currIndice--;
            new InitTask(ResultatDosActivity.this).execute();
        } else if (id == R.id.action_avant) {
            recyclerView.setAdapter(new DetailActivityRvAdapter(getApplicationContext(), new ArrayList<DossierAnalyseDetail>(), "", ""));
            currIndice++;
            new InitTask(ResultatDosActivity.this).execute();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultat_dos);
        getSupportActionBar().setSubtitle(getString(R.string.online));
        currIndice = getIntent().getIntExtra("currIndice", 0);
        Bundle args = getIntent().getBundleExtra("BUNDLE");
        ITEMS = (ArrayList<DossierAnalyse>) args.getSerializable("list");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //setContentView(R.layout.activity_dossier_list);
    }

    private void changeStates() {
        switch (state) {
            case 0:
                estVerrouille = false;
                mValide.setVisibility(View.GONE);
                mSignee.setVisibility(View.GONE);
                fabcheck.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
                break;
            case 1:
                estVerrouille = true;
                mValide.setVisibility(View.VISIBLE);
                mSignee.setVisibility(View.VISIBLE);
                fabcheck.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                break;
        }
    }

    private class ValiderBackgroundTask extends AsyncTask<Void, Void, Boolean> {

        Context mContext;
        View view;

        public ValiderBackgroundTask(Context mContext, View V) {
            this.mContext = mContext;
            view = V;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return switchValider(mContext, dossierAnalyse);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean != null) {
                if (dossierAnalyse != null)
                    if ((dossierAnalyse.getStatus() & 4) == 0)
                        if (aBoolean) Snackbar.make(view, R.string.msg3, Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                        else
                            Snackbar.make(view, R.string.msg4, Snackbar.LENGTH_SHORT)
                                    .setAction("Action", null).show();
                    else if (aBoolean)
                        Snackbar.make(view, R.string.msg1, Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                    else
                        Snackbar.make(view, R.string.msg2, Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                if ((dossierAnalyse.getStatus() & 4) > 0) {
                    state = 1;
                    changeStates();
                } else {
                    state = 0;
                    changeStates();
                }
                recyclerView.setAdapter(new DetailActivityRvAdapter(mContext, Details, ProlabVersion, dossierAnalyse.getNenreg()));
            } else {
                Toast.makeText(mContext, R.string.err1, Toast.LENGTH_LONG).show();
            }
            super.onPostExecute(aBoolean);
        }
    }

    public class InitTask extends AsyncTask<Void, Void, ArrayList<DossierAnalyseDetail>> {
        Context mContext;
        ProgressDialog pd;

        public InitTask(Context mContext) {
            this.mContext = mContext;
            pd = new ProgressDialog(mContext);
            pd.setTitle(getString(R.string.please_wait));
            pd.setMessage(getString(R.string.loading));
            pd.setIndeterminate(false);
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.setCancelable(false);
        }

        @Override
        protected ArrayList<DossierAnalyseDetail> doInBackground(Void... voids) {
            ArrayList<DossierAnalyseDetail> res = new ArrayList<DossierAnalyseDetail>();
            res = DossierAnalyseDAO.getAnalyses(mContext, ProlabVersion, dossierAnalyse.getNenreg());
            return res;
        }

        @Override
        protected void onPreExecute() {
            pd.show();
            SP = PreferenceManager.getDefaultSharedPreferences(mContext);
            ProlabVersion = SP.getString("prolabVersion", "Prolab5");
            mImgPatient = (ImageView) findViewById(R.id.imageView);
            mNenreg = (TextView) findViewById(R.id.nenreg);
            mPatient = (TextView) findViewById(R.id.patient);
            mAge = (TextView) findViewById(R.id.age);
            mMedecin = (TextView) findViewById(R.id.medecin);
            mDatePrelevement = (TextView) findViewById(R.id.dateprelevement);
            mGsmMedecin = (TextView) findViewById(R.id.gsmmedecin);
            lyMedecin = (LinearLayout) findViewById(R.id.lymedecin);
            recyclerView = (RecyclerView) findViewById(R.id.dossierdetail_list);
            mLivre = (ImageView) findViewById(R.id.livre);
            mValide = (ImageView) findViewById(R.id.valide);
            mValide.setVisibility(View.INVISIBLE);
            mImprime = (ImageView) findViewById(R.id.imprime);
            toolbar_layout = findViewById(R.id.toolbar_layout);
            //dossierAnalyse = (DossierAnalyse) getIntent().getSerializableExtra(ResultatDosActivity.ARG_ITEM_ID);
            dossierAnalyse = ITEMS.get(currIndice);
            fab = findViewById(R.id.fab);
            fabcheck = findViewById(R.id.fab2);
            mSignee = findViewById(R.id.signe);
            mImprime.setVisibility(View.GONE);
            mLivre.setVisibility(View.GONE);
            mValide.setVisibility(View.GONE);
            mSignee.setVisibility(View.GONE);
            toolbar_layout.setVisibility(View.GONE);
            state = -1;
            estVerrouille = false;
        }

        @Override
        protected void onCancelled() {
            pd.dismiss();
        }

        @Override
        protected void onPostExecute(ArrayList<DossierAnalyseDetail> analyseDetails) {

            if (analyseDetails != null) {
                if (dossierAnalyse != null) {
                    Details = analyseDetails;
                    if (currIndice <= 0) {
                        menu.getItem(0).setVisible(false);
                    } else {
                        menu.getItem(0).setVisible(true);
                    }
                    if (currIndice >= ITEMS.size() - 1) {
                        menu.getItem(1).setVisible(false);
                    } else {
                        menu.getItem(1).setVisible(true);
                    }

                    toolbar_layout.setVisibility(View.VISIBLE);
                    dossierAnalyse.setAnalyses(analyseDetails);
                    recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                    recyclerView.setAdapter(new DetailActivityRvAdapter(mContext, analyseDetails, ProlabVersion, dossierAnalyse.getNenreg()));
                    ViewCompat.setNestedScrollingEnabled(recyclerView, false);
                    new FabTask(mContext).execute();
                    mPatient.setText(dossierAnalyse.getPatient());
                    mAge.setText(dossierAnalyse.getAge());
                    mNenreg.setText(dossierAnalyse.getNenreg());
                    if (dossierAnalyse.getMedecin().trim().equals("")) {
                        mMedecin.setVisibility(View.INVISIBLE);
                    } else {
                        mMedecin.setVisibility(View.VISIBLE);
                        mMedecin.setText(dossierAnalyse.getMedecin());
                    }
                    if (dossierAnalyse.getTitre().equalsIgnoreCase(getString(R.string.mr)))
                        mImgPatient.setImageResource(R.drawable.patient_male);
                    else if (dossierAnalyse.getTitre().equalsIgnoreCase(getString(R.string.mme)) || dossierAnalyse.getTitre().equalsIgnoreCase("MLLE"))
                        mImgPatient.setImageResource(R.drawable.patient_female);
                    else if (dossierAnalyse.getTitre().equalsIgnoreCase(getString(R.string.enf)) || dossierAnalyse.getTitre().equalsIgnoreCase("BB"))
                        mImgPatient.setImageResource(R.drawable.patient_child);
                    else mImgPatient.setImageResource(R.drawable.patient_male);
                    /*if (dossierAnalyse.getMedecin().isEmpty())
                        lyMedecin.setVisibility(View.GONE);*/
                    lyMedecin.setVisibility(View.VISIBLE);

                    if (dossierAnalyse.getGsmMedecin().trim().equals("")) {
                        mGsmMedecin.setVisibility(View.INVISIBLE);
                        lyMedecin.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Snackbar.make(view, R.string.nd, Snackbar.LENGTH_SHORT)
                                        .setAction("Action", null).show();
                            }
                        });
                    } else {
                        mGsmMedecin.setVisibility(View.VISIBLE);
                        mGsmMedecin.setText(dossierAnalyse.getGsmMedecin());
                        lyMedecin.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                callMedecin();
                            }
                        });
                    }
                    mDatePrelevement.setText(dossierAnalyse.getDatePrelevemnt().toString());
                    if ((dossierAnalyse.getStatus() & 4) > 0) {
                        state = 1;
                    } else {
                        state = 0;
                    }
                    changeStates();
                    if (dossierAnalyse.getLivre())
                        mLivre.setVisibility(View.VISIBLE);
                    else
                        mLivre.setVisibility(View.INVISIBLE);
                    if (dossierAnalyse.getNbrImpr() > 0)
                        mImprime.setVisibility(View.VISIBLE);
                    else
                        mImprime.setVisibility(View.INVISIBLE);
                } else {
                    Toast.makeText(mContext, R.string.err, Toast.LENGTH_LONG).show();
                }
                fabcheck.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new ValiderBackgroundTask(getBaseContext(), findViewById(R.id.resultatdos)).execute();
                    }
                });
            } else {
                Toast.makeText(mContext, R.string.err1, Toast.LENGTH_LONG).show();
            }
            pd.dismiss();
        }
    }

    private class FabTask extends AsyncTask<Void, Void, List<Antibiogramme>> {

        Context mContext;

        public FabTask(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        protected List<Antibiogramme> doInBackground(Void... voids) {
            return AntiboigrammeDAO.getAll(mContext, dossierAnalyse.getNenreg());

        }

        @Override
        protected void onPostExecute(List<Antibiogramme> details) {
            if (details != null) {
                if (details.size() > 0) {
                    final AntibiogrammeListHolder atb = new AntibiogrammeListHolder(details);
                    fab.setVisibility(View.VISIBLE);
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(getBaseContext(), ATBActivity.class);
                            i.putExtra("details", atb);
                            startActivity(i);
                        }
                    });
                }
            }
        }
    }

    private class DetailActivityRvAdapter extends RecyclerView.Adapter<DetailActivityRvAdapter.DetailViewHolder> {

        private List<DossierAnalyseDetail> mValues;
        private Context mContext;
        private String ProlabVersion;
        private String nenrg;

        public DetailActivityRvAdapter(Context c, List<DossierAnalyseDetail> items, String ProlabVersion, String nenrg) {
            mValues = items;
            mContext = c;
            this.ProlabVersion = ProlabVersion;
            this.nenrg = nenrg;
        }

        @Override
        public DetailActivityRvAdapter.DetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.dossierdetail_list_content, parent, false);
            return new DetailActivityRvAdapter.DetailViewHolder(view);
        }


        @Override
        public void onBindViewHolder(final DetailActivityRvAdapter.DetailViewHolder holder, final int position) {
            holder.mVT.setImageDrawable(null);
            final DossierAnalyseDetail mItem = mValues.get(position);
            if (mItem.getA_Controler()) {
                Glide.with(mContext)
                        .load(R.drawable.warning)
                        .into(holder.mVT);
            } else if ((mItem.getEtat() & 4) == 4) {
                Glide.with(mContext)
                        .load(R.drawable.check)
                        .into(holder.mVT);
            } else {
                Glide.with(mContext)
                        .load(R.drawable.trans)
                        .into(holder.mVT);
            }
            holder.mAbreviation.setText(mItem.getAbreviation());

            if (mItem.getLibelle() == null || mItem.getLibelle().isEmpty()) {
                holder.mLibelle.setVisibility(View.GONE);
            } else {
                holder.mLibelle.setVisibility(View.VISIBLE);
                holder.mLibelle.setText(mItem.getLibelle().trim());
            }

            if (mItem.getHN()) {
                holder.lyDetailResultat.setBackgroundColor(Color.argb(100, 250, 150, 150));
            } else {
                holder.lyDetailResultat.setBackgroundColor(Color.argb(100, 255, 255, 255));
            }

            if (mItem.getResultat() == null || mItem.getResultat().isEmpty()) {
                holder.mResultat.setVisibility(View.GONE);
                holder.mResultat.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        return true;
                    }
                });
            } else {
                holder.mResultat.setVisibility(View.VISIBLE);
                if (mItem.getResultat() == "") {
                    holder.mResultat.setText("#");
                } else {
                    holder.mResultat.setText(mItem.getResultat());
                }
                holder.mResultat.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        if (estVerrouille == false && (mItem.getUnite2() == null || mItem.getUnite2().isEmpty())) {
                            AnalyseFeedBack analyseFeedBack = new AnalyseFeedBack(ResultatDosActivity.this, mItem, 2);
                            analyseFeedBack.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    new DetailActivityRvAdapter.ReloadTask(mContext).execute();
                                }
                            });
                            analyseFeedBack.show();
                        } else {
                            //Toast.makeText(mContext, R.string.dossvalid, Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                });
            }
            if (mItem.getUnite1() == null || mItem.getUnite1().isEmpty()) {
                holder.mUnite.setVisibility(View.GONE);
            } else {
                holder.mUnite.setVisibility(View.VISIBLE);
                holder.mUnite.setText(mItem.getUnite1());
            }
            /*
            if ((dossierAnalyse.getStatus() & 4) == 0)
                holder.mView.setBackgroundColor(Color.argb(100, 250, 50, 50));
            else
                holder.mView.setBackgroundColor(Color.argb(100, 50, 250, 50));
*/
            if (mItem.getValeurUsuelle() == null || mItem.getValeurUsuelle().isEmpty()) {
                holder.mVu.setVisibility(View.GONE);
            } else {
                holder.mVu.setVisibility(View.VISIBLE);
                holder.mVu.setText(UtilFunctions.rtftToPlain(mItem.getValeurUsuelle()));
            }

            if (mItem.getAnteriotite() == null || mItem.getAnteriotite().isEmpty()) {
                holder.mAnteriorite.setVisibility(View.GONE);
            } else {
                holder.mAnteriorite.setVisibility(View.VISIBLE);
                holder.mAnteriorite.setText(mItem.getAnteriotite().replace("#", "\n"));
            }

            if (mItem.getCommentaire() == null || mItem.getCommentaire().isEmpty()) {
                holder.mCommentaire.setVisibility(View.GONE);
            } else {
                holder.mCommentaire.setVisibility(View.VISIBLE);
                holder.mCommentaire.setText(UtilFunctions.rtftToPlain(mItem.getCommentaire()));
            }

            /*if (mItem.getNumGroupe() == null || mItem.getNumGroupe().isEmpty()) {
                holder.separator.setVisibility(View.VISIBLE);
            } else {
                holder.separator.setVisibility(View.GONE);
            }*/

            if (mItem.getAnteriotite() == null || mItem.getAnteriotite().isEmpty()) {
                holder.lyAnteriorite.setVisibility(View.GONE);
            } else {
                holder.lyAnteriorite.setVisibility(View.VISIBLE);
            }

            if (mItem.getEstGroupe()) {
                holder.lyResultat.setVisibility(View.GONE);
                holder.mLibelleGroupe.setVisibility(View.VISIBLE);
                if (mItem.getNumGroupe() == null || mItem.getNumGroupe().isEmpty()) {
                    holder.mLibelleGroupe.setText(mItem.getLibelle());
                } else {
                    holder.mLibelleGroupe.setText("\t" + mItem.getLibelle()+ "   ");
                    holder.mLibelleGroupe.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.lightblue));
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    params.setMargins(10, 1, 1, 1);
                    holder.mLibelleGroupe.setLayoutParams(params);
                }
            } else {
                holder.mLibelleGroupe.setVisibility(View.GONE);
                holder.lyResultat.setVisibility(View.VISIBLE);
                holder.mVT.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new updateFeedBack(ResultatDosActivity.this, mItem).execute();
                    }
                });
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                if (mItem.getNumGroupe() != null && !mItem.getNumGroupe().isEmpty()) {
                    params.setMargins(40, 0, 1, 0);
                    holder.lyResultat.setLayoutParams(params);
                }
                else
                {
                    params.setMargins(1, 0, 1, 0);
                    holder.lyResultat.setLayoutParams(params);
                }

                holder.mLibelle.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        if (estVerrouille == false) {
                            AnalyseFeedBack analyseFeedBack = new AnalyseFeedBack(ResultatDosActivity.this, mItem, 1);
                            analyseFeedBack.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    new DetailActivityRvAdapter.ReloadTask(mContext).execute();
                                }
                            });
                            analyseFeedBack.show();
                        } else {
                            Toast.makeText(mContext, R.string.dossvalid, Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                });

            }
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class DetailViewHolder extends RecyclerView.ViewHolder {
            public View mView;
            public ImageView mVT;
            public TextView mAbreviation;
            public TextView mLibelle;
            public TextView mLibelleGroupe;
            public TextView mResultat;
            public TextView mUnite;
            public TextView mVu;
            public TextView mAnteriorite;
            public TextView mCommentaire;
            public FrameLayout lyResultat;
            public LinearLayout lyAnteriorite;
            public LinearLayout lyDetailResultat;
            public FrameLayout separator;
            public FrameLayout row;
            public LinearLayout mainRow;

            public DetailViewHolder(View view) {
                super(view);
                mView = view;
                mVT = (ImageView) view.findViewById(R.id.imgVerif);
                mAbreviation = (TextView) view.findViewById(R.id.abreviation);
                mLibelle = (TextView) view.findViewById(R.id.libelle);
                mLibelleGroupe = (TextView) view.findViewById(R.id.libelleGroupe);
                mResultat = (TextView) view.findViewById(R.id.resultat);
                mUnite = (TextView) view.findViewById(R.id.unite);
                mVu = (TextView) view.findViewById(R.id.vu);
                mAnteriorite = (TextView) view.findViewById(R.id.ant);
                mCommentaire = (TextView) view.findViewById(R.id.commentaire);
                lyResultat = (FrameLayout) view.findViewById(R.id.lyResultat);
                lyDetailResultat = (LinearLayout) view.findViewById(R.id.lydetailres);
                lyAnteriorite = (LinearLayout) view.findViewById(R.id.lyAnteriorite);
                separator = (FrameLayout) view.findViewById(R.id.separator);
                row = (FrameLayout) view.findViewById(R.id.separator);
                mainRow = view.findViewById(R.id.mainRow);
            }
        }

        public class ReloadTask extends AsyncTask<Void, Void, ArrayList<DossierAnalyseDetail>> {

            Context mContext;

            public ReloadTask(Context mContext) {
                this.mContext = mContext;
            }

            @Override
            protected ArrayList<DossierAnalyseDetail> doInBackground(Void... voids) {
                ArrayList<DossierAnalyseDetail> res = new ArrayList<DossierAnalyseDetail>();
                res = DossierAnalyseDAO.getAnalyses(mContext, ProlabVersion, nenrg);
                return res;
            }

            @Override
            protected void onPostExecute(ArrayList<DossierAnalyseDetail> analyseDetails) {
                if (analyseDetails != null) {
                    mValues = analyseDetails;
                    Details = analyseDetails;
                    ITEMS.get(currIndice).setAnalyses(analyseDetails);
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(mContext, R.string.err1, Toast.LENGTH_LONG).show();
                }
            }
        }

        private class updateFeedBack extends AsyncTask<Void, Void, Boolean> {
            private Context context;
            private DossierAnalyseDetail mItem;

            public updateFeedBack(Context context, DossierAnalyseDetail Item) {
                this.context = context;
                mItem = Item;
            }

            @Override
            protected Boolean doInBackground(Void... voids) {
                boolean res = false;
                try {
                    res = DossierAnalyseDetailDAO.FeedBackAnalyse(context,
                            mItem.getNumAnalyse(),
                            mItem.getNumDossier(),
                            mItem.getNumGroupe(),
                            !mItem.getA_Controler());
                } catch (Exception e) {

                }
                return res;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                Snackbar.make(findViewById(R.id.resultatdos), R.string.updsuccs, Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                new DetailActivityRvAdapter.ReloadTask(mContext).execute();
            }
        }
    }

    private void callMedecin() {
        try {
            Intent my_callIntent = new Intent(Intent.ACTION_CALL);
            my_callIntent.setData(Uri.parse("tel:" + mGsmMedecin.getText()));
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                //  return;
            } else
                startActivity(my_callIntent);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_LONG).show();
        }
    }
}

