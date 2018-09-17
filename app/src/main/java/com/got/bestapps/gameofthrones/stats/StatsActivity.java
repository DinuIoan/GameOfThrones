package com.got.bestapps.gameofthrones.stats;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.got.bestapps.gameofthrones.MainActivity;
import com.got.bestapps.gameofthrones.R;
import com.got.bestapps.gameofthrones.database.DatabaseData;
import com.got.bestapps.gameofthrones.model.Rankings;

import java.util.List;

public class StatsActivity extends AppCompatActivity {
    private List<Rankings> rankingsList;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView rankingsNotFoundTextView;
    private Button backButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        rankingsNotFoundTextView = (TextView) findViewById(R.id.rankings_not_found_text_view);
        backButton2 = findViewById(R.id.back_button2);

//        rankingsNotFoundTextView.setVisibility(View.INVISIBLE);
        rankingsNotFoundTextView.setTextSize(40);
        rankingsList = DatabaseData.getRankings();
        if (rankingsList.size() == 0) {
            recyclerView.setVisibility(View.INVISIBLE);
            rankingsNotFoundTextView.setVisibility(View.VISIBLE);
        } else {
            int maxPoints = 0;
            for (Rankings ranking: rankingsList) {
                if (ranking.getPoints() > maxPoints) {
                    maxPoints = ranking.getPoints();
                }
            }
//            rankingsNotFoundTextView.setVisibility(View.INVISIBLE);
            rankingsNotFoundTextView.setText("Your best score is: \n" + maxPoints + " points");
            //recyclerView.setVisibility(View.VISIBLE);



//            mLayoutManager = new LinearLayoutManager(this);
//            recyclerView.setLayoutManager(mLayoutManager);
//            recyclerViewAdapter = new RecyclerViewAdapter(rankingsList);
//            recyclerView.setAdapter(recyclerViewAdapter);
        }
        backButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(StatsActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
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
