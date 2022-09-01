package com.tornado.a2048.Game2048;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.tornado.a2048.R;

public class MainActivity extends AppCompatActivity {

    private static final String WIDTH = "width";
    private static final String HEIGHT = "height";
    private static final String SCORE = "score";
    private static final String HIGH_SCORE = "high score temp";
    private static final String HIGH_SCORE1 = "high score temp1";
    private static final String HIGH_SCORE2 = "high score temp2";
    private static final String UNDO_SCORE = "undo score";
    private static final String CAN_UNDO = "can undo";
    private static final String UNDO_GRID = "undo";
    private static final String GAME_STATE = "game state";
    private static final String UNDO_GAME_STATE = "undo game state";
    private MainView view;
    int type;
    boolean sound;
    LinearLayout content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        content = this.findViewById(R.id.content);

        type = getIntent().getIntExtra("type",0);
        int newgame = getIntent().getIntExtra("new",0);
         sound = getIntent().getBooleanExtra("sound",true);
        View decorView = getWindow().getDecorView();
// Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        view = new MainView(this,type,newgame,sound);

        if(newgame ==1) {
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
            view.hasSaveState = settings.getBoolean("save_state", false);

            if (savedInstanceState != null) {
                if (savedInstanceState.getBoolean("hasState")) {
                    load();
                }
            }
        }
        else
        {
            save();
        }
        content.addView(view);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            //Do nothing
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
            view.game.move(2);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
            view.game.move(0);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            view.game.move(3);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            view.game.move(1);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean("hasState", true);
        save();
        super.onSaveInstanceState(savedInstanceState);
    }

    protected void onPause() {
        super.onPause();
        save();
        if(sound == true) {
            Intent intent = new Intent(MainActivity.this, BackgroundSoundService.class);
            stopService(intent);
        }
    }

    @Override
    public void onBackPressed()
    {
        save();
        super.onBackPressed();
        Intent intent = new Intent(MainActivity.this, BackgroundSoundService.class);
        stopService(intent);
       /* new AlertDialog.Builder(this)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       // Intent intent = new Intent(  MainActivity.this, MenuGame.class);
                       // startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton(R.string.continue_game, null)
                .setTitle(R.string.GOTO_MENU)
                .setMessage(R.string.are_you_sure)
                .show();*/
    }

    private void save() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = settings.edit();
        Tile[][] field = view.game.grid.field;
        Tile[][] undoField = view.game.grid.undoField;
        editor.putInt(WIDTH, field.length);
        editor.putInt(HEIGHT, field.length);
        for (int xx = 0; xx < field.length; xx++) {
            for (int yy = 0; yy < field[0].length; yy++) {
                if (field[xx][yy] != null) {
                    editor.putInt(xx + " " + yy, field[xx][yy].getValue());
                } else {
                    editor.putInt(xx + " " + yy, 0);
                }

                if (undoField[xx][yy] != null) {
                    editor.putInt(UNDO_GRID + xx + " " + yy, undoField[xx][yy].getValue());
                } else {
                    editor.putInt(UNDO_GRID + xx + " " + yy, 0);
                }
            }
        }
        editor.putLong(SCORE, view.game.score);
        if(type ==0)
            editor.putLong(HIGH_SCORE, view.game.highScore);
        else if (type ==1)
            editor.putLong(HIGH_SCORE1, view.game.highScore);
        else if(type ==2)
            editor.putLong(HIGH_SCORE2, view.game.highScore);
        editor.putLong(UNDO_SCORE, view.game.lastScore);
        editor.putBoolean(CAN_UNDO, view.game.canUndo);
        editor.putInt(GAME_STATE, view.game.gameState);
        editor.putInt(UNDO_GAME_STATE, view.game.lastGameState);
        editor.putInt("type", type);
        editor.commit();
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    protected void onResume() {
        super.onResume();
        load();
        if( sound == true) {
            if(isMyServiceRunning( BackgroundSoundService.class)==false) {
                Intent intent = new Intent(MainActivity.this, BackgroundSoundService.class);
                startService(intent);
            }
        }



    }
    public void onDestroy() {

        super.onDestroy();
        if( sound == true) {
          //  Intent intent = new Intent(MainActivity.this, BackgroundSoundService.class);
            //stopService(intent);
        }

    }

    private void load() {
        //Stopping all animations
        view.game.aGrid.cancelAnimations();

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        for (int xx = 0; xx < view.game.grid.field.length; xx++) {
            for (int yy = 0; yy < view.game.grid.field[0].length; yy++) {
                int value = settings.getInt(xx + " " + yy, -1);
                if (value > 0) {
                    view.game.grid.field[xx][yy] = new Tile(xx, yy, value);
                } else if (value == 0) {
                    view.game.grid.field[xx][yy] = null;
                }

                int undoValue = settings.getInt(UNDO_GRID + xx + " " + yy, -1);
                if (undoValue > 0) {
                    view.game.grid.undoField[xx][yy] = new Tile(xx, yy, undoValue);
                } else if (value == 0) {
                    view.game.grid.undoField[xx][yy] = null;
                }
            }
        }

        view.game.score = settings.getLong(SCORE, view.game.score);
        if(type==0)
        view.game.highScore = settings.getLong(HIGH_SCORE, view.game.highScore);
        else if(type==1)
            view.game.highScore = settings.getLong(HIGH_SCORE1, view.game.highScore);
             else if(type==2)
            view.game.highScore = settings.getLong(HIGH_SCORE2, view.game.highScore);
        view.game.lastScore = settings.getLong(UNDO_SCORE, view.game.lastScore);
        view.game.canUndo = settings.getBoolean(CAN_UNDO, view.game.canUndo);
        view.game.gameState = settings.getInt(GAME_STATE, view.game.gameState);
        view.game.lastGameState = settings.getInt(UNDO_GAME_STATE, view.game.lastGameState);
    }
}
