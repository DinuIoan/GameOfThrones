package com.got.bestapps.gameofthrones.stats;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.got.bestapps.gameofthrones.R;
import com.got.bestapps.gameofthrones.database.DatabaseData;
import com.got.bestapps.gameofthrones.model.Rankings;

import java.util.List;

public class StatsActivity extends AppCompatActivity {
    private List<Rankings> rankingsList;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        rankingsList = DatabaseData.getRankings();
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(rankingsList);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
