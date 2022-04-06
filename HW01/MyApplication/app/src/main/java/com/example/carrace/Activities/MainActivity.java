package com.example.carrace.Activities;
/**
 * shimon Desta 203670286
 * HW01 - Car Game
 **/
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.carrace.CarGame;
import com.example.carrace.R;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private int score = 0;
    private CarGame gameApp;
    private LinearLayout layout;
    private LinearLayout gameLayout;
    private ImageView[] Hearts;
    private ImageView[][] path;
    private Button[] buttons;
    private Timer timer = new Timer();


    public void tryAgain(View view){
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }

    private void startTicker() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                setScore(score);
                runOnUiThread(new Runnable() {
                    public void run() {
                        int status = gameApp.next();
                        if(status == 1){
                            vibrate();
                            popUpMsg(gameApp.getLives()+" lives left!!");
                        }
                        if(status == 2){ gameOver();}//end game
                        updateUI();
                    }
                });
            }
        }, 0, gameApp.getSpeed());




    }

    private void upScore(){
        TextView scoreLabel = (TextView)findViewById(R.id.scoreLabel);
        score = getIntent().getIntExtra("Score:",getScore());
        scoreLabel.setText("score: " + getScore());
        TextView scoreLabel1 = (TextView)findViewById(R.id.scoreLabel1);
        score = getIntent().getIntExtra("Score:",getScore());
        scoreLabel1.setText("THE SCORE: " + getScore());
        score++;
        setScore(score);
    }





    private void stopTicker() {
        timer.cancel();
    }

    private void updateUI() {//up date the game
        int livesValue = gameApp.getLives();
        //setScore(score);
        for (int i = 0; i <Hearts.length; i++) {
            //score++;
            ImageView im = Hearts[i];
            if(i<livesValue){
                im.setImageResource(R.drawable.img_heart);
            }else{
                im.setImageResource(R.drawable.img_heart_empty);
            }
        }
        upScore();//up date score
        for (int i = 0; i < path.length; i++) {
            for (int j = 0; j < path[i].length; j++) {
                ImageView im = path[i][j];
                if (gameApp.getVals()[i][j] == 0) {
                    im.setVisibility(View.INVISIBLE);
                } else if (gameApp.getVals()[i][j] == 1) {
                    im.setVisibility(View.VISIBLE);
                    im.setImageResource(R.drawable.img_rock);
                } else if (gameApp.getVals()[i][j] == 2) {
                    im.setVisibility(View.VISIBLE);
                    im.setImageResource(R.drawable.img_racing_car);
                } else if (gameApp.getVals()[i][j] == 3) {
                    im.setVisibility(View.VISIBLE);
                    im.setImageResource(R.drawable.img_crash2);
                }
            }
        }
    }

    private void vibrate() {  // Vibrate
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(600, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            v.vibrate(500);
        }
    }
    private void popUpMsg(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    private void gameOver(){
        stopTicker();
        layout.setVisibility(View.INVISIBLE);
        gameLayout.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        initButton();
        gameApp = new CarGame();
        gameApp.startGame(path.length, path[0].length,Hearts.length,500);

    }



    private void initButton() {
        for (int i = 0; i < buttons.length; i++) {
            int finalI = i;
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gameApp.moveCar(finalI);
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        startTicker();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopTicker();
    }
    public int getScore() {
        return score;
    }

    public int setScore(int score) {
        this.score = score;
        return score;
    }
    private void findViews() {
        //layouts
        layout = findViewById(R.id.game_layout);
        gameLayout = findViewById(R.id.game_over);
        //find lives
        Hearts = new ImageView[] {
                findViewById(R.id.img_heart1),
                findViewById(R.id.img_heart2),
                findViewById(R.id.img_heart3)
        };
        //find path
        path = new ImageView[][]{//matrix fo the game
                {findViewById(R.id.img_id_00),findViewById(R.id.img_id_01), findViewById(R.id.img_id_02)},
                {findViewById(R.id.img_id_10), findViewById(R.id.img_id_11), findViewById(R.id.img_id_12)},
                {findViewById(R.id.img_id_20), findViewById(R.id.img_id_21), findViewById(R.id.img_id_22)},
                {findViewById(R.id.img_id_30), findViewById(R.id.img_id_31), findViewById(R.id.img_id_32)}
                //{findViewById(R.id.img_id_40), findViewById(R.id.img_id_41), findViewById(R.id.img_id_42)}
        };
        //buttons
        buttons = new Button[]{findViewById(R.id.btn_left), findViewById(R.id.btn_right)};
    }
}