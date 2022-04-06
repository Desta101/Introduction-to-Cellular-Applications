package com.example.carrace.control;
/**
 * shimon Desta 203670286
 * HW01 - Car Game
 **/
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;

public class GameUtilities {
    private static GameUtilities instance;
    private static Context appCtext;



    public static GameUtilities initHelper(Context context) {
        if (instance == null) instance = new GameUtilities(context);
        return instance;
    }
    public void vibrate(int speedcont) {
        Vibrator v = (Vibrator) appCtext.getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(speedcont, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            v.vibrate(speedcont);
        }
    }
    private void popUpMsg(String str){
        Toast.makeText(appCtext, str, Toast.LENGTH_SHORT).show();
    }
    public static GameUtilities getInstance() {
        return instance;
    }
    private GameUtilities(Context context) {
        appCtext = context;
    }
    public void playSound(int music) {
        final MediaPlayer mp;
        mp = MediaPlayer.create(appCtext, music);
        mp.start();
        mp.setOnCompletionListener(MediaPlayer::pause);

    }
}
