package com.example.carrace.Activities;
/**
 * shimon Desta 203670286
 * HW01 - Car Game
 **/
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.carrace.control.GameRace;
import com.example.carrace.R;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

public class CarGame_Activity extends AppCompatActivity {

    public static final String SCORE = "POINTS";
    public static final String SENSOR_TYPE = "SENSOR_TYPE";
    private Button[] testButtons;
    private GameRace controlGame;
    private Timer timer = new Timer();
    private TextView points;
    private int counter = 0;
    private MediaPlayer music;
    private SensorManager sensorManager;
    private Sensor accSensor;
    private boolean testFlag;
    private ImageView[] lives;
    private ImageView[][] path;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        findViews();
        controlGame = new GameRace();
        controlGame.startGame(path.length, path[0].length,lives.length,300);
        Bundle bundle = getIntent().getBundleExtra(Main_Activity.BUNDLE);
        testFlag = bundle.getBoolean(SENSOR_TYPE);
        initSensor();
        if(testFlag){
            for (int i = 0; i < testButtons.length; i++){
                testButtons[i].setVisibility(View.INVISIBLE);
            }
        }
        else{
            initButton();
        }

    }
    private void startTicker() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        counter++;
                        points.setText(""+counter);
                        int status = controlGame.next(counter);
                        if(status == 1){
                            vibrate();
                            upString(controlGame.getLives()+" Lives!");
                            music = MediaPlayer.create(getApplicationContext(),R.raw.hit_sound);
                            music.start();
                            music.setOnCompletionListener(MediaPlayer::pause);
                        }
                        if(status == 2){ gameOver();}
                        if(status == 3){
                            counter += 100;
                            points.setText(""+counter);
                            music = MediaPlayer.create(getApplicationContext(),R.raw.coin_sound);
                            music.start();
                            music.setOnCompletionListener(MediaPlayer::pause);
                        }
                        gameUpdate();
                    }
                });
            }
        }, 0, controlGame.getSpeed());
    }
    private void stopTicker() {
        timer.cancel();
    }


    private void vibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            v.vibrate(500);
        }
    }
    private void upString(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void gameOver(){
        stopTicker();
        Intent Intent = new Intent(CarGame_Activity.this, EndGame_Activity.class);
        Intent.putExtra(SCORE, counter);
        startActivity(Intent);
        this.finish();
    }

    private void initSensor() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }
    private void gameUpdate() {
        int livesValue = controlGame.getLives();
        for (int i = 0; i <lives.length; i++) {
            ImageView im = lives[i];
            if(i<livesValue){
                im.setImageResource(R.drawable.img_heart);
            }else{
                im.setImageResource(R.drawable.img_heart_empty);
            }
        }
        for (int i = 0; i < path.length; i++) {
            for (int j = 0; j < path[i].length; j++) {
                ImageView im = path[i][j];
                if (controlGame.getArr()[i][j] == 0) {
                    im.setVisibility(View.INVISIBLE);
                } else if (controlGame.getArr()[i][j] == 1) {
                    im.setVisibility(View.VISIBLE);
                    im.setImageResource(R.drawable.img_rock);
                } else if (controlGame.getArr()[i][j] == 2) {
                    im.setVisibility(View.VISIBLE);
                    im.setImageResource(R.drawable.img_racing_car);
                } else if (controlGame.getArr()[i][j] == 3) {
                    im.setVisibility(View.VISIBLE);
                    im.setImageResource(R.drawable.img_crash);
                }else if (controlGame.getArr()[i][j] == 4) {
                    im.setVisibility(View.VISIBLE);
                    im.setImageResource(R.drawable.img_coin);
                }

            }
        }
    }

    private SensorEventListener accSensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent e) {
            DecimalFormat df = new DecimalFormat("##.##");
            float x = e.values[0];
            float y = e.values[1];
            float z = e.values[2];
            if(testFlag){
                if(x>2){ controlGame.moveCar(0); }
                else if(x<-2){ controlGame.moveCar(1); }
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(accSensorEventListener, accSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(accSensorEventListener);
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

    private void initButton() {
        for (int i = 0; i < testButtons.length; i++) {
            int finalI = i;
            testButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    controlGame.moveCar(finalI);
                }
            });
        }
    }
    private void findViews() {

        lives = new ImageView[] {findViewById(R.id.Heart_1),findViewById(R.id.Heart_2),findViewById(R.id.Heart_3)};
        points = findViewById(R.id.tv_score);
        path = new ImageView[][]{
                {findViewById(R.id.img_id_00), findViewById(R.id.img_id_01), findViewById(R.id.img_id_02), findViewById(R.id.img_id_03), findViewById(R.id.img_id_04)},
                {findViewById(R.id.img_id_10), findViewById(R.id.img_id_11), findViewById(R.id.img_id_12), findViewById(R.id.img_id_13), findViewById(R.id.img_id_14)},
                {findViewById(R.id.img_id_20), findViewById(R.id.img_id_21), findViewById(R.id.img_id_22), findViewById(R.id.img_id_23), findViewById(R.id.img_id_24)},
                {findViewById(R.id.img_id_30), findViewById(R.id.img_id_31), findViewById(R.id.img_id_32), findViewById(R.id.img_id_33), findViewById(R.id.img_id_34)},
                {findViewById(R.id.img_id_40), findViewById(R.id.img_id_41), findViewById(R.id.img_id_42), findViewById(R.id.img_id_43), findViewById(R.id.img_id_44)},
                {findViewById(R.id.img_id_50), findViewById(R.id.img_id_51), findViewById(R.id.img_id_52), findViewById(R.id.img_id_53), findViewById(R.id.img_id_54)}
                //{findViewById(R.id.img_id_60), findViewById(R.id.img_id_61), findViewById(R.id.img_id_62), findViewById(R.id.img_id_63), findViewById(R.id.img_id_64)}
        };
        testButtons = new Button[]{findViewById(R.id.Button_L), findViewById(R.id.Button_R)};
    }
}