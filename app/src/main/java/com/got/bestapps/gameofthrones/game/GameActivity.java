package com.got.bestapps.gameofthrones.game;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.renderscript.Sampler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
    private AnimationDrawable rocketAnimation;
    private ImageView heart1;
    private ImageView heart2;
    private ImageView heart3;
    private TextView question;
    private TextView timer;
    private TextView pointsTextView;

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

        back_button = (Button) findViewById(R.id.back_button);
        answear1 = (Button) findViewById(R.id.button_answear1);
        answear2 = (Button) findViewById(R.id.button_answear2);
        answear3 = (Button) findViewById(R.id.button_answear3);
        answear4 = (Button) findViewById(R.id.button_answear4);
        question = (TextView) findViewById(R.id.question_text_view);
        timer = (TextView) findViewById(R.id.timer);
        heart1 = (ImageView) findViewById(R.id.heart1);
        heart2 = (ImageView) findViewById(R.id.heart2);
        heart3 = (ImageView) findViewById(R.id.heart3);
        pointsTextView = (TextView) findViewById(R.id.score_text_view);
        pointsTextView.setText("0p");

        DatabaseData.getGame().setGames_number(DatabaseData.getGame().getGames_number() - 1);
        databaseHandler.modifyGameObject( DatabaseData.getGame().getGames_number(), DatabaseData.getGame().getPlayer_state_id());

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO dialog questioning if you are sure you want to exit
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        //TODO toast message with small instructions before starting the game
        initData();
        startGame();
    }


    public void startGame() {
        heart1.setVisibility(View.VISIBLE);
        heart2.setVisibility(View.VISIBLE);
        heart3.setVisibility(View.VISIBLE);
        loadQuestion();
        time = 20;
        countDownTimer.start();
        //startTimer();
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
                looseLife(null);
            }
        };
    }

    public void reloadGame() {
        enableButtons();
        loadQuestion();
        countDownTimer.start();
    }

    public void pauseGame() {

    }

    public void loadQuestion() {
        if (questions.size() != 0) {
            Random random = new Random();
            int maximum = questions.size();
            int randomQuestion = random.nextInt(maximum);
            final Question rQuestion = questions.remove(randomQuestion);
            questionsAnsweared.add(rQuestion);
            question.setText("~" + rQuestion.getType() + "~\n" + rQuestion.getText());
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
                    disableButton();
                    if (answear1.getText().toString().contains(correctAnswear)) {
                        points += rQuestion.getPoints();
                        pointsTextView.setText("" + points);
                        makeCorrectAnim(answear1);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                reloadGame();
                            }
                        }, 900);
//                        reloadGame();
                    } else {
                        looseLife(answear1);
                    }
                }
            });

            answear2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    disableButton();
                    if (answear2.getText().toString().contains(correctAnswear)) {
                        points += rQuestion.getPoints();
                        pointsTextView.setText("" + points);
                        makeCorrectAnim(answear2);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                reloadGame();
                            }
                        }, 900);
//                        reloadGame();
                    } else {
                        looseLife(answear2);
                    }
                }
            });

            answear3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    disableButton();
                    if (answear3.getText().toString().contains(correctAnswear)) {
                        points += rQuestion.getPoints();
                        pointsTextView.setText("" + points);
                        makeCorrectAnim(answear3);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                reloadGame();
                            }
                        }, 900);
//                        reloadGame();
                    } else {
                        looseLife(answear3);
                    }
                }
            });

            answear4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    disableButton();
                    if (answear4.getText().toString().contains(correctAnswear)) {
                        points += rQuestion.getPoints();
                        pointsTextView.setText("" + points);
                        makeCorrectAnim(answear4);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                reloadGame();
                            }
                        }, 900);
//                        reloadGame();
                    } else {
                        looseLife(answear4);
                    }
                }
            });
        } else {
            //TODO NO MORE QUESTIONS, WHO GET'S HERE IS THE BOSS
        }
    }

    private void disableButton() {
        answear1.setClickable(false);
        answear2.setClickable(false);
        answear3.setClickable(false);
        answear4.setClickable(false);
    }

    private void enableButtons() {
        answear1.setClickable(true);
        answear2.setClickable(true);
        answear3.setClickable(true);
        answear4.setClickable(true);
    }


   public void looseLife(Button answear) {
       Handler handler = new Handler();
       if (answear != null ){
           makeWrongAnim(answear);
       }
       if (lifes > 0) {
            lifes -= 1;
            if (lifes == 2) {
                heart3.setVisibility(View.INVISIBLE);
//                heart3.setImageResource(R.drawable.heartblack);
            }
            if (lifes == 1) {
                heart2.setVisibility(View.INVISIBLE);
//                heart3.setImageResource(R.drawable.heartblack);

            }
            if (lifes == 0) {
                heart1.setVisibility(View.INVISIBLE);
//                heart3.setImageResource(R.drawable.heartblack);
            }
           handler.postDelayed(new Runnable() {
               @Override
               public void run() {
                   reloadGame();
               }
           }, 900);
       } else {
           endGame();
       }
    }

    public void endGame() {
        Handler handler = new Handler();
        countDownTimer.cancel();

        //Update rankings with new score in database and databaseData
        updateRankingIfNecessary();

        //TODO what should happen if game ends
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                makeAlertDialog();
            }
        }, 900);

    }

    private void updateRankingIfNecessary() {
        int maxPoints = points;
        List<Rankings> rankings = DatabaseData.getRankings();
        for (Rankings oldRanking: rankings) {
            if (oldRanking.getPoints() > points)
                maxPoints = oldRanking.getPoints();
        }
        if (maxPoints == points) {
            rankings.add(new Rankings(0, points, 0));
            databaseHandler.updateRankings(rankings);
            DatabaseData.setRankings(databaseHandler.getAllRankings());
        }
    }

    public void makeWrongAnim(Button answear) {
        answear.setBackgroundResource(R.drawable.wrong_answear_transition);
        rocketAnimation = (AnimationDrawable) answear.getBackground();
        if (rocketAnimation.isRunning()) {
            rocketAnimation.stop();
        }
        rocketAnimation.start();
    }

    public void makeCorrectAnim(Button answear) {
        answear.setBackgroundResource(R.drawable.correct_answear_anim);
        rocketAnimation = (AnimationDrawable) answear.getBackground();
        if (rocketAnimation.isRunning()) {
            rocketAnimation.stop();
        }
        rocketAnimation.start();
    }

    public void makeAlertDialog() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(GameActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(GameActivity.this);
        }
        if (DatabaseData.getGame().getGames_number() > 0) {
            builder.setTitle("Game over")
                    .setMessage("Great! You scored: " + points + ".\nDon't give up. Do you want to try to do better?")
                    .setPositiveButton(R.string.retry, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent countdownActivityIntent = new Intent(GameActivity.this, CountdownActivity.class);
                            startActivity(countdownActivityIntent);
                            finish();
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent mainActivityIntent = new Intent(GameActivity.this, MainActivity.class);
                            startActivity(mainActivityIntent);
                            finish();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert);
        } else {
            builder.setTitle("Game over")
                    .setMessage("Great! You scored: " + points + ".\nBuy more games or wait about 1 hour to recharge games.")
                    .setPositiveButton(R.string.buyGames, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent mainActivityIntent = new Intent(GameActivity.this, MainActivity.class);
                            startActivity(mainActivityIntent);
                            finish();
                        }
                    })
                    .setNegativeButton(R.string.goToMenu, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent mainActivityIntent = new Intent(GameActivity.this, MainActivity.class);
                            startActivity(mainActivityIntent);
                            finish();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert);
        }
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        //TODO dialog questioning if you are sure you want to exit
        countDownTimer.cancel();
        Intent intent = new Intent(GameActivity.this, MainActivity.class);
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
