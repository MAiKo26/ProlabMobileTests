package com.sama_consulting.prolabmobile5.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sama_consulting.prolabmobile5.Beans.Antibiogramme;
import com.sama_consulting.prolabmobile5.Dialogs.AnalyseFeedBack;
import com.sama_consulting.prolabmobile5.Dialogs.AtbComment;
import com.sama_consulting.prolabmobile5.R;
import com.sama_consulting.prolabmobile5.ResultatDosActivity;
import com.sama_consulting.prolabmobile5.Util.UtilFunctions;

import java.util.List;

public class AtbAdapter extends RecyclerView.Adapter<AtbAdapter.ViewHolder> {

    List<Antibiogramme> antibiogrammeList;
    private Context mContext;

    public AtbAdapter(List<Antibiogramme> antibiogrammeList, Context mContext) {
        this.antibiogrammeList = antibiogrammeList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public AtbAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.atbsample, parent, false);
        return new AtbAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AtbAdapter.ViewHolder holder, int position) {
        final Antibiogramme antibiogramme = antibiogrammeList.get(position);
        holder.nomgerme.setText(antibiogramme.getLibelle() + " : " + antibiogramme.getPrelevement());
        if (!UtilFunctions.rtftToPlain(antibiogramme.getRemarque()).equals("")) {
            holder.remarques.setText(UtilFunctions.rtftToPlain(antibiogramme.getRemarque()));
        }
        else
        {
            holder.remarques.setText(" ");
        }
        holder.remarques.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AtbComment atbComment = new AtbComment(mContext,antibiogramme);
                atbComment.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        holder.remarques.setText(UtilFunctions.rtftToPlain(antibiogramme.getRemarque()));
                    }
                });
                atbComment.show();
            }
        });
        holder.elemnts.setLayoutManager(new LinearLayoutManager(mContext));
        holder.elemnts.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
        holder.elemnts.addItemDecoration(dividerItemDecoration);
        holder.elemnts.setAdapter(new AtbElementAdapter(antibiogramme.getDetails(), mContext));
        ViewCompat.setNestedScrollingEnabled(holder.elemnts, false);
    }

    @Override
    public int getItemCount() {
        return antibiogrammeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView nomgerme;
        public final TextView remarques;
        public final RecyclerView elemnts;

        public ViewHolder(View itemView) {
            super(itemView);
            nomgerme = itemView.findViewById(R.id.nomgerme);
            remarques = itemView.findViewById(R.id.rqs);
            elemnts = itemView.findViewById(R.id.elementsRv);
        }
    }
}
