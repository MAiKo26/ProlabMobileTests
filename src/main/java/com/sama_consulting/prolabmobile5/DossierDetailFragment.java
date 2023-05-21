package com.sama_consulting.prolabmobile5;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.QuickContactBadge;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sama_consulting.prolabmobile5.Beans.DossierAnalyse;
import com.sama_consulting.prolabmobile5.Beans.DossierAnalyseDetail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A fragment representing a single Dossier detail screen.
 * This fragment is either contained in a {@link DossierListActivity}
 * in two-pane mode (on tablets) or a {@link DossierDetailActivity}
 * on handsets.
 */
public class DossierDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    public ImageView mImgPatient;
    public ImageView mImgSolde;
    public ImageView mLivre;
    public ImageView mValide;
    public ImageView mImprime;
    public TextView mNenreg;
    public TextView mPatient;
    public TextView mMedecin;
    public TextView mGsmMedecin;
    public TextView mDatePrelevement;
    public TextView mEncours;
    public TextView mTermines;
    public RelativeLayout lyMedecin;
    public QuickContactBadge qcbGsmMedein;
    public Map<String, DossierAnalyse> ITEM_MAP;
    CollapsingToolbarLayout appBarLayout;
    /**
     * The dummy content this fragment is presenting.
     */
    private DossierAnalyse dossierAnalyse;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DossierDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_dossier_list);
        ITEM_MAP = new HashMap<String, DossierAnalyse>();
        /**
         * TODO : INIT ITEM_MAP
         */
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.

            dossierAnalyse = ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);

            if (appBarLayout != null) {
                appBarLayout.setTitle(dossierAnalyse.getNenreg());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_resultat_dos, container, false);
        mImgPatient = (ImageView) appBarLayout.findViewById(R.id.imageView);
        //mImgSolde = (ImageView) appBarLayout.findViewById(R.id.solde);
        mNenreg = (TextView) appBarLayout.findViewById(R.id.nenreg);
        mPatient = (TextView) appBarLayout.findViewById(R.id.patient);
        mMedecin = (TextView) appBarLayout.findViewById(R.id.medecin);
        mDatePrelevement = (TextView) appBarLayout.findViewById(R.id.dateprelevement);
        mGsmMedecin = (TextView) appBarLayout.findViewById(R.id.gsmmedecin);
        mEncours = (TextView) appBarLayout.findViewById(R.id.analyseencours);
        mTermines = (TextView) appBarLayout.findViewById(R.id.analysetermines);
        lyMedecin = (RelativeLayout) appBarLayout.findViewById(R.id.lymedecin);
        mLivre = (ImageView) appBarLayout.findViewById(R.id.livre);
        mValide = (ImageView) appBarLayout.findViewById(R.id.valide);
        mImprime = (ImageView) appBarLayout.findViewById(R.id.imprime);
        RecyclerView recyclerView;
        recyclerView = (RecyclerView) rootView.findViewById(R.id.dossierdetail_list);

        if (dossierAnalyse != null) {
            mPatient.setText(dossierAnalyse.getPatient());
            mNenreg.setText(dossierAnalyse.getNenreg());
            mMedecin.setText(dossierAnalyse.getMedecin());
            if (dossierAnalyse.getTitre().equalsIgnoreCase("Mr"))
                mImgPatient.setImageResource(R.drawable.patient_male); //setImageResource(R.drawable.patient_female);
            if (dossierAnalyse.getTitre().equalsIgnoreCase("MME") || dossierAnalyse.getTitre().equalsIgnoreCase("MLLE"))
                mImgPatient.setImageResource(R.drawable.patient_female); //setImageResource(R.drawable.patient_female);
            if (dossierAnalyse.getTitre().equalsIgnoreCase("Enf") || dossierAnalyse.getTitre().equalsIgnoreCase("BB"))
                mImgPatient.setImageResource(R.drawable.patient_child);
            if (dossierAnalyse.getMedecin().isEmpty()) lyMedecin.setVisibility(View.INVISIBLE);
            if ((dossierAnalyse.getStatus() & 4) > 0)
                mValide.setVisibility(View.VISIBLE);
            else
                mValide.setVisibility(View.INVISIBLE);

            if (dossierAnalyse.getLivre())
                mLivre.setVisibility(View.VISIBLE);
            else
                mLivre.setVisibility(View.INVISIBLE);
            if (dossierAnalyse.getNbrImpr() > 0)
                mImprime.setVisibility(View.VISIBLE);
            else
                mImprime.setVisibility(View.INVISIBLE);

            mGsmMedecin.setText(dossierAnalyse.getGsmMedecin());
            mDatePrelevement.setText(dossierAnalyse.getDatePrelevemnt().toString());
            mEncours.setText(dossierAnalyse.getListeAnalysesEnCours());
            mTermines.setText(dossierAnalyse.getListeAnalysesTermines());
            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(llm);
            recyclerView.setAdapter(new DetailViewAdapter(dossierAnalyse.getAnalyses()));
        }


        return rootView;
    }


    public class DetailViewAdapter
            extends RecyclerView.Adapter<DetailViewAdapter.DetailViewHolder> {

        private final List<DossierAnalyseDetail> mValues;

        public DetailViewAdapter(List<DossierAnalyseDetail> items) {
            mValues = items;
        }

        @Override
        public DetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.dossierdetail_list_content, parent, false);
            return new DetailViewAdapter.DetailViewHolder(view);
        }

        @Override
        public void onBindViewHolder(DetailViewHolder holder, int position) {

            holder.mItem = dossierAnalyse.getAnalyses().get(position);
            holder.mAbreviation.setText(holder.mItem.getAbreviation());
            holder.mLibelle.setText(holder.mItem.getLibelle());
            holder.mResultat.setText(holder.mItem.getResultat());
            holder.mUnite.setText(holder.mItem.getUnite1());
            holder.mVu.setText(holder.mItem.getValeurUsuelle());
            holder.mAnteriorite.setText(holder.mItem.getAnteriotite());

        }


        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class DetailViewHolder extends RecyclerView.ViewHolder {
            public View mView;
            public TextView mAbreviation;
            public TextView mLibelle;
            public TextView mResultat;
            public TextView mUnite;
            public TextView mVu;
            public TextView mAnteriorite;
            public TextView mCommentaire;
            public LinearLayout lyResultat;

            public DossierAnalyseDetail mItem;

            public DetailViewHolder(View view) {
                super(view);
                mView = view;
                mAbreviation = (TextView) view.findViewById(R.id.abreviation);
                mLibelle = (TextView) view.findViewById(R.id.libelle);
                mResultat = (TextView) view.findViewById(R.id.resultat);
                mUnite = (TextView) view.findViewById(R.id.unite);
                mVu = (TextView) view.findViewById(R.id.vu);
                mAnteriorite = (TextView) view.findViewById(R.id.ant);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mPatient.getText() + "'";
            }
        }
    }
}
