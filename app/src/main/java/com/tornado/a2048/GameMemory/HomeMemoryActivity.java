package com.tornado.a2048.GameMemory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.startapp.android.publish.ads.splash.SplashConfig;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.StartAppSDK;
import com.tornado.a2048.Game2048.BackgroundSoundService;
import com.tornado.a2048.Game2048.MainActivity;
import com.tornado.a2048.Game2048.MenuGame;
import com.tornado.a2048.Game2048.MenuImageAdapter;
import com.tornado.a2048.Home.WelcomeActivity;
import com.tornado.a2048.R;

import java.util.ArrayList;
import java.util.List;

public class HomeMemoryActivity extends AppCompatActivity {
    private MenuImageAdapter adapter;
    private RecyclerView rcy;
    private List<Integer> id = new ArrayList<>();
    private List<String> text = new ArrayList<>();
    private Button startgame ;
    private Button loadgame;
    private Button exit;
    private ImageButton rating ;
    private ImageButton music;
    private ImageView left;
    private ImageView right;
    int position;
    boolean pause = true;
    Boolean ismusic=true;
//..................................................................................................
    public void onDestroy() {

        super.onDestroy();
        if( ismusic == true) {
            //  Intent intent = new Intent(MenuGame.this, BackgroundSoundService.class);
            // stopService(intent);
        }

    }
//..................................................................................................
    protected void onPause() {
        super.onPause();
        if(ismusic==true) {
            if(pause == true) {
                Intent intent = new Intent(HomeMemoryActivity.this, BackgroundSoundService.class);
                stopService(intent);
            }
        }
    }
//..................................................................................................
    protected void onResume() {
        super.onResume();
        pause = true;
        if (ismusic == true) {
            Intent intent = new Intent(HomeMemoryActivity.this, BackgroundSoundService.class);
            startService(intent);
        }
    }
//..................................................................................................
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(HomeMemoryActivity.this, BackgroundSoundService.class);
        stopService(intent);
    }
//..................................................................................................
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_memory_layout);
//        StartAppSDK.init(this,"198985741", "208890338", true);
//        StartAppAd.showSplash(this, savedInstanceState,
//                new SplashConfig()
//                        .setTheme(SplashConfig.Theme.OCEAN)
//                        .setAppName("2048 Plus")
//                        .setLogo(R.mipmap.ic_launcher)   // resource ID
//                        .setOrientation(SplashConfig.Orientation.PORTRAIT)
//        );
        position= 0;
        rcy = (RecyclerView)this.findViewById(R.id.rcy);
        startgame = (Button)this.findViewById(R.id.startgame) ;
        loadgame = (Button)this.findViewById(R.id.loadgame) ;
        exit = (Button)this.findViewById(R.id.exit);
        music = (ImageButton)this.findViewById(R.id.sound);
        left = (ImageView)this.findViewById(R.id.left);
        right  = (ImageView)this.findViewById(R.id.right);
        rating = (ImageButton)this.findViewById(R.id.rating) ;
        rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String appPackage = HomeMemoryActivity.this.getPackageName();
                String url = "market://details?id=" + appPackage;
                try
                {
                    HomeMemoryActivity.this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    HomeMemoryActivity.this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + HomeMemoryActivity.this.getPackageName())));
                }
            }
        });

        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ismusic==true)
                {
                    music.setBackgroundResource(R.drawable.soundoff);
                    Intent intent = new Intent(HomeMemoryActivity.this, BackgroundSoundService.class);
                    stopService(intent);
                    ismusic=false;
                }
                else
                {
                    music.setBackgroundResource(R.drawable.soundon);
                    Intent svc=new Intent(HomeMemoryActivity.this, BackgroundSoundService.class);
                    startService(svc);
                    ismusic= true;
                }
            }
        });

        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);

        if(settings.getInt("type",-1) == position) {
            loadgame.setEnabled(true);
        }
        else
        {
            loadgame.setEnabled(false);
        }

        if (savedInstanceState != null) {
            if (savedInstanceState.getBoolean("hasState")) {
                loadgame.setEnabled(false);
            }
            else
            {
                loadgame.setEnabled(false);

            }
        }

//..................................................................................................
        startgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pause =false;
                Intent intent = new Intent(HomeMemoryActivity.this , MemoryActivity.class);
                intent.putExtra("type",position);
                intent.putExtra("new",0);
                intent.putExtra("sound",ismusic);
                startActivity(intent);
                //  finish();
            }
        });
//..................................................................................................
        loadgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pause =false;
                Intent intent = new Intent(HomeMemoryActivity.this ,MemoryActivity.class);
                intent.putExtra("type",position);
                intent.putExtra("new",1);
                intent.putExtra("sound",ismusic);
                startActivity(intent);
                // finish();
            }
        });
//..................................................................................................
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Khoi tao lai Activity main
                Intent intent = new Intent(HomeMemoryActivity.this, WelcomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
