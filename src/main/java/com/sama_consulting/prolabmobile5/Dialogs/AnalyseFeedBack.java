package com.sama_consulting.prolabmobile5.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sama_consulting.prolabmobile5.Beans.DossierAnalyseDetail;
import com.sama_consulting.prolabmobile5.DAO.DossierAnalyseDetailDAO;
import com.sama_consulting.prolabmobile5.R;
import com.sama_consulting.prolabmobile5.Util.UtilFunctions;

public class AnalyseFeedBack extends Dialog implements
        android.view.View.OnClickListener {

    private DossierAnalyseDetail analyseDetail;
    private Context context;
    private Button okbtn, cancBtn;
    private boolean aVerifier, commentaireBl;
    private AppCompatCheckBox aVerifierCb, commentaireCb;
    private EditText commentaire;
    private int mode;

    public AnalyseFeedBack(@NonNull Context context, DossierAnalyseDetail analyseDetail, int mode) {
        super(context);
        this.analyseDetail = analyseDetail;
        this.context = context;
        this.mode = mode;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.analysefeedback_layout);
        okbtn = findViewById(R.id.confirmer);
        okbtn.setEnabled(false);
        commentaire = findViewById(R.id.commentaireContenu);
        if (mode == 1) {
            this.setTitle("Commentaire ["+analyseDetail.getLibelle()+"]");
            try
            {
                commentaire.setText(UtilFunctions.rtftToPlain(analyseDetail.getCommentaire()));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        } else {
            this.setTitle("Résultat ["+analyseDetail.getLibelle()+"]");
            commentaire.setHint(context.getString(R.string.result));
            try
            {
                commentaire.setText(UtilFunctions.rtftToPlain(analyseDetail.getResultat()));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mode == 1) {
                    new updateFeedBack(context).execute();
                } else {
                    new updateResultat(context).execute();
                }
            }
        });
        cancBtn = findViewById(R.id.quitter);
        cancBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        commentaire.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                okbtn.setEnabled(true);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    private class updateFeedBack extends AsyncTask<Void, Void, Boolean> {

        private Context context;

        public updateFeedBack(Context context) {
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            boolean res = false;
            try {
                res = DossierAnalyseDetailDAO.FeedBackAnalyse(context,
                        analyseDetail.getNumAnalyse(),
                        analyseDetail.getNumDossier(),
                        analyseDetail.getNumGroupe(),
                        commentaire.getText().toString());
            } catch (Exception e) {

            }
            return res;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                dismiss();
            } else {
                Toast.makeText(context, R.string.err3, Toast.LENGTH_LONG).show();
            }
        }
    }

    private class updateResultat extends AsyncTask<Void, Void, Boolean> {

        private Context context;

        public updateResultat(Context context) {
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            boolean res = false;
            try {
                res = DossierAnalyseDetailDAO.UpdateResultat(context,
                        analyseDetail.getNumAnalyse(),
                        analyseDetail.getNumDossier(),
                        analyseDetail.getNumGroupe(),
                        commentaire.getText().toString());
            } catch (Exception e) {

            }
            return res;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                dismiss();
            } else {
                Toast.makeText(context, R.string.err3, Toast.LENGTH_LONG).show();
            }
        }
    }
}
