package com.sama_consulting.prolabmobile5.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sama_consulting.prolabmobile5.Beans.DossierAnalyse;
import com.sama_consulting.prolabmobile5.DAO.DossierAnalyseDAO;
import com.sama_consulting.prolabmobile5.R;
import com.sama_consulting.prolabmobile5.ResultatDosActivity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;

public class ListActivityRvAdapter
        extends RecyclerView.Adapter<ListActivityRvAdapter.ViewHolder> {

    private List<DossierAnalyse> mValues;
    private Context mContext;
    private String ProlabVersion;

    public ListActivityRvAdapter(Context mContext, String ProlabVersion, List<DossierAnalyse> items) {
        mValues = items;
        this.mContext = mContext;
        this.ProlabVersion = ProlabVersion;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dossier_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final DossierAnalyse mItem = mValues.get(position);
        new mentionAControler(mContext, ProlabVersion, mItem.getNenreg(), holder).execute();
        holder.mNenreg.setText(mItem.getNenreg());
        holder.mPatient.setText(mItem.getPatient());
        holder.mAge.setText(mItem.getAge());
        if (mItem.getMedecin().trim().equals("")) {
            //holder.mMedecin.setText(R.string.nd);
            holder.lyMedecin.setVisibility(View.GONE);
        } else {
            holder.mMedecin.setText(mItem.getMedecin());
            holder.lyMedecin.setVisibility(View.VISIBLE);
        }

        if (mItem.getListeAnalysesEnCours() == null || mItem.getListeAnalysesEnCours().isEmpty()) {
            holder.mEncours.setVisibility(View.GONE);
        } else {
            holder.mEncours.setVisibility(View.VISIBLE);
            holder.mEncours.setText((mItem.getListeAnalysesEnCours()));
        }
        if (mItem.getListeAnalysesTermines() == null || mItem.getListeAnalysesTermines().isEmpty()) {
            holder.mTermines.setVisibility(View.GONE);
        } else {
            holder.mTermines.setVisibility(View.VISIBLE);
            holder.mTermines.setText((mItem.getListeAnalysesTermines()));
        }
        SimpleDateFormat sf = new SimpleDateFormat("HH:mm");
        holder.mDatePrelevement.setText(mItem.getDatePrelevemnt().toString());
        holder.mHeure.setText(sf.format(mItem.getHeurePrelevemnt()));
        holder.mDatePromis.setText(mItem.getResultFlag().toString());

        if (mItem.getUrgent()) {
            Glide.with(mContext)
                    .load(R.drawable.emergency)
                    .into(holder.mImgUrgent);
        } else {
            Glide.with(mContext)
                    .load(R.drawable.blank)
                    .into(holder.mImgUrgent);
        }

        if ((mItem.getStatus() & 4) > 0) {
            Glide.with(mContext)
                    .load(R.drawable.check)
                    .into(holder.mValide);
        } else {
            Glide.with(mContext)
                    .load(R.drawable.blank)
                    .into(holder.mValide);
        }
        if (mItem.getLivre()) {
            Glide.with(mContext)
                    .load(R.drawable.send)
                    .into(holder.mLivre);
        } else {
            Glide.with(mContext)
                    .load(R.drawable.blank)
                    .into(holder.mLivre);
        }
        if (mItem.getNbrImpr() > 0) {
            Glide.with(mContext)
                    .load(R.drawable.printer)
                    .into(holder.mImprime);
        } else {
            Glide.with(mContext)
                    .load(R.drawable.blank)
                    .into(holder.mImprime);
        }
        if (mItem.getTitre().equalsIgnoreCase("Mr"))
            Glide.with(mContext)
                    .load(R.drawable.patient_male)
                    .into(holder.mImgPatient);
        if (mItem.getTitre().equalsIgnoreCase("MME") || mItem.getTitre().equalsIgnoreCase("MLLE"))
            Glide.with(mContext)
                    .load(R.drawable.patient_female)
                    .into(holder.mImgPatient);
        if (mItem.getTitre().equalsIgnoreCase("Enf") || mItem.getTitre().equalsIgnoreCase("BB"))
            Glide.with(mContext)
                    .load(R.drawable.patient_child)
                    .into(holder.mImgPatient);
      //  if (mItem.getSolde()<=0) holder.mImgSolde.setVisibility(View.INVISIBLE);
           if (mItem.getSolde()>0) {
             holder.mImgSolde.setImageResource(R.drawable.red_dollar);
          }
          else
             holder.mImgSolde.setImageResource(R.drawable.green_dollar);

        /*if (mValues.get(position).getMedecin().isEmpty())
            holder.lyMedecin.setVisibility(View.GONE);*/
        holder.lyPatient.invalidate();
        holder.lyPatient.setBackgroundColor(mItem.getColor());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, ResultatDosActivity.class);
                //intent.putExtra(ResultatDosActivity.ARG_ITEM_ID, mItem);
                intent.putExtra("currIndice",position);
                Bundle args = new Bundle();
                args.putSerializable("list",(Serializable)mValues);
                intent.putExtra("BUNDLE",args);
                context.startActivity(intent);
            }
        });
        holder.mView.setClickable(true);
        if (mItem.getSigner().equals(true)) {
            Glide.with(mContext)
                    .load(R.drawable.certificate)
                    .into(holder.mSignee);
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }


    private class mentionAControler extends AsyncTask<Void, Void, Boolean> {
        private Context context;
        private String ProlabVersion;
        private String nenreg;
        private ViewHolder holder;

        public mentionAControler(Context mContext, String ProlabVersion, String nenreg, ViewHolder holder) {
            context = mContext;
            this.ProlabVersion = ProlabVersion;
            this.nenreg = nenreg;
            this.holder = holder;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return DossierAnalyseDAO.aControler(context, ProlabVersion, nenreg);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean == true) {
                Glide.with(context)
                        .load(R.drawable.warning)
                        .into(holder.mControler);
            } else {
                Glide.with(context)
                        .load(R.drawable.blank)
                        .into(holder.mControler);
            }
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mImgPatient;
        public final ImageView mImgUrgent;
        public final ImageView mImgSolde;

        public final TextView mNenreg;
        public final TextView mPatient;
        public final TextView mAge;
        public final TextView mMedecin;
        public final TextView mEncours;
        public final TextView mTermines;
        public final TextView mDatePrelevement;
        public final TextView mHeure;
        public final TextView mDatePromis;
        public final LinearLayout lyPatient, lyMedecin;
        public final ImageView mLivre;
        public final ImageView mValide;
        public final ImageView mImprime;
        public final ImageView mControler;
        public final ImageView mSignee;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImgPatient = (ImageView) view.findViewById(R.id.imageView);
            mImgSolde = (ImageView) view.findViewById(R.id.solde);
            mNenreg = (TextView) view.findViewById(R.id.nenreg);
            mPatient = (TextView) view.findViewById(R.id.patient);
            mAge = (TextView) view.findViewById(R.id.age);
            mMedecin = (TextView) view.findViewById(R.id.medecin);
            mDatePrelevement = (TextView) view.findViewById(R.id.dateprelevement);
            mHeure = (TextView) view.findViewById(R.id.txtHeure);
            mDatePromis = (TextView) view.findViewById(R.id.datepromis);
            lyMedecin = (LinearLayout) view.findViewById(R.id.lymedecin);
            lyPatient = (LinearLayout) view.findViewById(R.id.lypat);
            mImgUrgent = (ImageView) view.findViewById(R.id.urgent);
            mLivre = (ImageView) view.findViewById(R.id.livre);
            mValide = (ImageView) view.findViewById(R.id.valide);
            mImprime = (ImageView) view.findViewById(R.id.imprime);
            mEncours = (TextView) view.findViewById(R.id.analyseencours);
            mTermines = (TextView) view.findViewById(R.id.analysetermines);
            mControler = view.findViewById(R.id.controler);
            mSignee = view.findViewById(R.id.signee);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mPatient.getText() + "'";
        }
    }
}