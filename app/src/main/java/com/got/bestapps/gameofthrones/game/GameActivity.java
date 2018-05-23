package com.got.bestapps.gameofthrones.game;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import com.got.bestapps.gameofthrones.MainActivity;
import com.got.bestapps.gameofthrones.R;
import com.got.bestapps.gameofthrones.database.DatabaseData;
import com.got.bestapps.gameofthrones.database.DatabaseHandler;
import com.got.bestapps.gameofthrones.model.Game;
import com.got.bestapps.gameofthrones.model.Question;
import com.got.bestapps.gameofthrones.model.Rankings;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {
    private Button back_button;
    private Button answear1;
    private Button answear2;
    private Button answear3;
    private Button answear4;
    private TextView question;
    private TextView timer;
    private List<Question> questions;
    private List<Question> questionsAnsweared;
    private Timer t;
    private TimerTask task;
    private int time;
    private int points;
    private String correctAnswear;
    private int lifes;
    private DatabaseHandler databaseHandler;
    private CountDownTimer countDownTimer;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("time", time);
        outState.putInt("points", points);
        outState.putInt("lifes", lifes);
        outState.putString("correctAnswear", correctAnswear);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        time = savedInstanceState.getInt("time");
        points = savedInstanceState.getInt("points");
        lifes = savedInstanceState.getInt("lifes");
        correctAnswear = savedInstanceState.getString("correctAnswear");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        databaseHandler = new DatabaseHandler(this);

        initData();

        back_button = (Button) findViewById(R.id.back_button);
        answear1 = (Button) findViewById(R.id.button_answear1);
        answear2 = (Button) findViewById(R.id.button_answear2);
        answear3 = (Button) findViewById(R.id.button_answear3);
        answear4 = (Button) findViewById(R.id.button_answear4);
        question = (TextView) findViewById(R.id.question_text_view);
        timer = (TextView) findViewById(R.id.timer);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        //TODO toast message with small instructions before starting the game
        startGame();
    }

    public void initData() {
        questions = DatabaseData.getQuestions();
        Collections.shuffle(questions);
        questionsAnsweared = new ArrayList<>();
        points = 0;
        lifes = 3;
        time = 20;
        this.countDownTimer = new CountDownTimer(21 * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                String remaining = "" + millisUntilFinished / 1000;
                timer.setText(remaining);
            }
            public void onFinish() {
                looseLife();
            }
        };
    }

    public void startGame() {
        loadQuestion();
        time = 20;
        countDownTimer.start();
        //startTimer();
    }

    public void reloadGame() {
        loadQuestion();
        countDownTimer.start();
    }

    public void pauseGame() {

    }

    public void looseLife() {
        if (lifes > 0) {
            lifes -= 1;
            reloadGame();
        } else {
            endGame();
        }
    }

    public void endGame() {
        //TODO NUMARUL DE JOCURI DISPONIBILE
        //Update lives in database and DatabaseData;
        DatabaseData.getGame().setGames_number(DatabaseData.getGame().getGames_number() - 1);
        databaseHandler.modifyGameObject(DatabaseData.getGame().getId(), DatabaseData.getGame().getGames_number(), DatabaseData.getGame().getPlayer_state_id());

        //Update rankings with new score in database and databaseData
        Rankings ranking = new Rankings(0, points, 0);
        List<Rankings> rankings = DatabaseData.getRankings();
        rankings.add(ranking);
        databaseHandler.updateRankings(rankings);
        DatabaseData.setRankings(databaseHandler.getAllRankings());

        //TODO what should happen if game ends
        Intent intent = new Intent(getApplicationContext(), GameOverActivity.class);
        intent.putExtra("score", points);
        startActivity(intent);
    }

    public void loadQuestion() {
        if (questions.size() != 0) {
            Random random = new Random();
            int maximum = questions.size();
            int randomQuestion = random.nextInt(maximum);
            final Question rQuestion = questions.remove(randomQuestion);
            questionsAnsweared.add(rQuestion);
            question.setText(rQuestion.getText());
            List<String> answears = new ArrayList<>();
            answears.add(rQuestion.getAnswear1());
            answears.add(rQuestion.getAnswear2());
            answears.add(rQuestion.getAnswear3());
            answears.add(rQuestion.getCorrect_answear());
            this.correctAnswear = rQuestion.getCorrect_answear();
            Collections.shuffle(answears);
            answear1.setText(answears.get(0));
            answear2.setText(answears.get(1));
            answear3.setText(answears.get(2));
            answear4.setText(answears.get(3));

            answear1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (answear1.getText().equals(correctAnswear)) {
                        points += rQuestion.getPoints();
                        reloadGame();
                    } else {
                        looseLife();
                    }
                }
            });

            answear2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (answear2.getText().equals(correctAnswear)) {
                        points += rQuestion.getPoints();
                        reloadGame();
                    } else {
                        looseLife();
                    }
                }
            });

            answear3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (answear3.getText().equals(correctAnswear)) {
                        points += rQuestion.getPoints();
                        reloadGame();
                    } else {
                        looseLife();
                    }
                }
            });

            answear4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (answear3.getText().equals(correctAnswear)) {
                        points += rQuestion.getPoints();
                        reloadGame();
                    } else {
                        looseLife();
                    }
                }
            });
        } else {
            //TODO NO MORE QUESTIONS, WHO GET'S HERE IS THE BOSS
        }
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
