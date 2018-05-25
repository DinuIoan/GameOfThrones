package com.got.bestapps.gameofthrones.rules;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.got.bestapps.gameofthrones.R;

import org.w3c.dom.Text;

public class RulesActivity extends AppCompatActivity {
    private TextView rulesTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
        rulesTextView = (TextView) findViewById(R.id.rules_text_view);
        String rules = R.string.line1 + "\n" + R.string.line2 + "\n" +
                R.string.line3 + "\n" + R.string.line4 + "\n" +
                R.string.line5 + "\n" + R.string.line6 + "\n" +
                R.string.line7 + "\n" + R.string.line8 + "\n" +
                R.string.line9 + "\n" + R.string.line10 + "\n" +
                R.string.line11 + "\n" + R.string.line12 + "\n" +
                R.string.line13 + "\n" + R.string.line14 + "\n";
        rulesTextView.setText(rules);
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
