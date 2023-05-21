package com.sama_consulting.prolabmobile5.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sama_consulting.prolabmobile5.Beans.Antibiogramme;
import com.sama_consulting.prolabmobile5.DAO.AntiboigrammeDAO;
import com.sama_consulting.prolabmobile5.R;
import com.sama_consulting.prolabmobile5.Util.UtilFunctions;

public class AtbComment extends Dialog implements
        android.view.View.OnClickListener {

    private Context context;
    private Antibiogramme antibiogramme;
    private Button okbtn, cancBtn;
    private EditText commentaire;
    public AtbComment(@NonNull Context context,Antibiogramme antibiogramme) {
        super(context);
        this.context=context;
        this.antibiogramme=antibiogramme;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atbcomment_layout);
        okbtn = findViewById(R.id.confirmer);
        okbtn.setEnabled(false);
        commentaire = findViewById(R.id.commentaireContenu);
        commentaire.setText(UtilFunctions.rtftToPlain(antibiogramme.getRemarque()));
        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new updateComment(context).execute();
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

    public class updateComment extends AsyncTask<Void,Void,Boolean> {
        Context mContext;

        public updateComment(Context context) {
            mContext=context;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return AntiboigrammeDAO.setRemarque(mContext,antibiogramme.getNumAtb(),commentaire.getText().toString());
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                antibiogramme.setRemarque(commentaire.getText().toString());
                dismiss();
            } else {
                Toast.makeText(context, R.string.err3, Toast.LENGTH_LONG).show();
            }
        }
    }
}
