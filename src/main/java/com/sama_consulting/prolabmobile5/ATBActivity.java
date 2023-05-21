package com.sama_consulting.prolabmobile5;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sama_consulting.prolabmobile5.Adapters.AtbAdapter;
import com.sama_consulting.prolabmobile5.Beans.AntibiogrammeListHolder;

public class ATBActivity extends AppCompatActivity {
    private AntibiogrammeListHolder antibiogramme;
    private RecyclerView recyclerView;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //setContentView(R.layout.activity_dossier_list);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atb);
        getSupportActionBar().setSubtitle(getString(R.string.atb));
        antibiogramme = (AntibiogrammeListHolder) getIntent().getSerializableExtra("details");
        recyclerView = findViewById(R.id.rvATB);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new AtbAdapter(antibiogramme.getAntibiogrammeList(), this));
        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        ViewCompat.setNestedScrollingEnabled(recyclerView, false);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
