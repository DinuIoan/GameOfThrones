package com.got.bestapps.gameofthrones.rules;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.got.bestapps.gameofthrones.MainActivity;
import com.got.bestapps.gameofthrones.R;
import com.got.bestapps.gameofthrones.game.GameActivity;

import org.w3c.dom.Text;

public class RulesActivity extends AppCompatActivity {
    private TextView rulesTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
        rulesTextView = (TextView) findViewById(R.id.rules_text_view);
        String rules = getString(R.string.line1) + "\n" + getString(R.string.line2) + "\n" +
                getString(R.string.line3) + "\n" + getString(R.string.line4) + "\n" +
                getString(R.string.line5) + "\n" + getString(R.string.line6) + "\n" +
                getString(R.string.line7) + "\n" + getString(R.string.line8) + "\n" +
                getString(R.string.line9) + "\n" + getString(R.string.line10) + "\n" +
                getString(R.string.line11) + "\n" + getString(R.string.line12) + "\n" +
                getString(R.string.line13) + "\n" + getString(R.string.line14) + "\n";
        rulesTextView.setText(rules);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RulesActivity.this, MainActivity.class);
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
