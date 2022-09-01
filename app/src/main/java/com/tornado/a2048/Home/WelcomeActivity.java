package com.tornado.a2048.Home;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.View;

import com.tornado.a2048.Game2048.MainActivity;
import com.tornado.a2048.Game2048.MenuGame;
import com.tornado.a2048.GameMemory.HomeMemoryActivity;
import com.tornado.a2048.GameMemory.MemoryActivity;
import com.tornado.a2048.R;

public class WelcomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_layout);
    }
    //..................................................................................................

    //chuyển sang trang 2048
    public void callLoginFromWel(View view)
    {
        Intent intent = new Intent(getApplicationContext(), MenuGame.class);

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(findViewById(R.id.btn_2048),"transition_login");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(WelcomeActivity.this,pairs);
            startActivity(intent,options.toBundle());
        }
        else {
            startActivity(intent);
        }
    }
//..................................................................................................

    // chuyển sang trang memory
    public void callSignUpFromWel(View view)
    {
        Intent intent = new Intent(getApplicationContext(), HomeMemoryActivity.class);
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(findViewById(R.id.btn_memory),"transition_signup");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(WelcomeActivity.this,pairs);
            startActivity(intent,options.toBundle());
        }
        else {
            startActivity(intent);
        }
    }
    // đóng ứng dụng
    public void callExitFromWel(View view)
    {
        //Khoi tao lai Activity main
        Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
        startActivity(intent);

        // Tao su kien ket thuc app
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startActivity(startMain);
        finish();
    }

}
