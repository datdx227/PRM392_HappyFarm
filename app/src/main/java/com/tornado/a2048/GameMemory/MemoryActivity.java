package com.tornado.a2048.GameMemory;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.tornado.a2048.R;

public class MemoryActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView curView = null, IMG_memory_backbtn;
    private int countPair= 0;
    final int [] drawable = new int [] {R.drawable.cho1,R.drawable.cho2,R.drawable.cho3,
            R.drawable.cho4,R.drawable.cho5,R.drawable.cho6,R.drawable.cho7,R.drawable.cho8};
    int [] pos = {0,1,2,3,4,5,6,7,0,1,2,3,4,5,6,7};
    int currentPos = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memory_layout);
        IMG_memory_backbtn = (ImageView) findViewById(R.id.img_memory_back);
        GridView gridView = (GridView) findViewById(R.id.GridView);
        ImageAdapter imageAdapter = new ImageAdapter(this);
        gridView.setAdapter(imageAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(currentPos <0){
                    currentPos = position;
                    curView = (ImageView) view;
                    ((ImageView)view).setImageResource(drawable[pos[position]]);

                }else
                {
                    if(currentPos == position){
                        ((ImageView)view).setImageResource(R.drawable.hidden);

                    }else if(pos[currentPos] != pos[position])
                    {
                        curView.setImageResource(R.drawable.hidden);
                        Toast.makeText(getApplicationContext(),"Not match",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        ((ImageView)view).setImageResource(drawable[pos[position]]);
                        countPair++;
                        if(countPair == 0){
                            Toast.makeText(getApplicationContext(),"You win",Toast.LENGTH_SHORT).show();

                        }
                    }
                    currentPos = -1;
                }
            }
        });
        IMG_memory_backbtn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){

            case R.id.img_memory_back:
                finish();
                break;
        }
    }
}
