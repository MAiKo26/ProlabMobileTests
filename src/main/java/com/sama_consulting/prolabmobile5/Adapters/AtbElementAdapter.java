package com.sama_consulting.prolabmobile5.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sama_consulting.prolabmobile5.Beans.AntibiogrammeDetail;
import com.sama_consulting.prolabmobile5.R;

import java.util.List;

public class AtbElementAdapter extends RecyclerView.Adapter<AtbElementAdapter.ViewHolder> {

    private List<AntibiogrammeDetail> mValues;
    private Context mContext;

    public AtbElementAdapter(List<AntibiogrammeDetail> mValues, Context mContext) {
        this.mValues = mValues;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.atbelementsample, parent, false);
        return new AtbElementAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AntibiogrammeDetail atbd = mValues.get(position);
        holder.atbrs.setText(atbd.getResultat());
        holder.atbNom.setText(atbd.getLibelle());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView atbNom;
        public final TextView atbrs;

        public ViewHolder(View itemView) {
            super(itemView);
            atbNom = itemView.findViewById(R.id.atbNom);
            atbrs = itemView.findViewById(R.id.atbrs);
        }
    }
}
