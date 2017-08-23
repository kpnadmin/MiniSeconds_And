package com.android.colorbrick;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private ListAdapter adapter = null;
    TextView countText, scoreText, toastText;
    ImageView colorBtn1, colorBtn2, colorBtn3;
    ProgressBar drogBar;
    ListView colorList;
    ArrayList<Integer> blickClr = new ArrayList<>();

    int score = 0;

    CountDownTimer timer;

    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countText = (TextView) findViewById(R.id.countText);
        scoreText = (TextView) findViewById(R.id.scoreText);
        drogBar   = (ProgressBar) findViewById(R.id.progressBar);
        colorBtn1 = (ImageView) findViewById(R.id.colorBtn1);
        colorBtn2 = (ImageView) findViewById(R.id.colorBtn2);
        colorBtn3 = (ImageView) findViewById(R.id.colorBtn3);
        colorList = (ListView) findViewById(R.id.colorList);

        colorList.setDivider(null);

        colorBtn1.setImageResource(R.drawable.bluesky);
        colorBtn2.setImageResource(R.drawable.red);
        colorBtn3.setImageResource(R.drawable.yellow);

        toastText = (TextView) findViewById(R.id.toastText);

        // 1.어댑터에서 사용할 데이터 설정
        for (int i = 0; i<10; i++) {
            blickClr.add(R.drawable.bluesky);
            blickClr.add(R.drawable.red);
            blickClr.add(R.drawable.yellow);
            Collections.shuffle(blickClr);
        }

        // 2.어댑터를 생성하고 데이터 설정
        adapter = new ListAdapter(this, R.layout.activity_list_adapter, R.id.txt, blickClr);

        //리스트뷰에 어댑터 연결
        colorList.setAdapter(adapter);

        final int p = colorList.getCount()-1; // ListView 마지막 아이템

        final Integer pp =
                (Integer)colorList.getItemAtPosition(p);  // ListView 마지막 아이템의 값

        colorBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorMethod(blickClr, R.drawable.bluesky);
            }
        });
        colorBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorMethod(blickClr, R.drawable.red);
            }
        });
        colorBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorMethod(blickClr, R.drawable.yellow);
            }
        });

        countText = (TextView) findViewById(R.id.countText);
        final long sec = 0;
        final int millisInFuture = 15100 ;

        //타이머 카운트다운
        timer = new CountDownTimer(millisInFuture,100){

            @Override
            public void onTick(long millisUntilFinished) {

                String aa = String.valueOf(millisUntilFinished);
                if      (aa.length() == 5)
                    countText.setText(aa.substring(0,1)+aa.substring(1,2) +"."+aa.substring(2,3));
                else if (aa.length() == 4)
                    countText.setText(aa.substring(0,1) +"."+aa.substring(1,2));
                else if (aa.length() == 3)
                    countText.setText("0."+ aa.substring(0,1));

                int bar = (int) (millisUntilFinished/100);
                drogBar.setProgress(bar);
            }
            @Override
            public void onFinish() {
                countText.setText(String.valueOf("0.0"));
                drogBar.setProgress(0);
                if (sec == 0){

                    Intent overIntent = new Intent(MainActivity.this, OverActivity.class);
                    startActivity(overIntent);
                    timer.cancel();
                }
            }
        };
        timer.start();
    }

    private void colorMethod(ArrayList<Integer> blickClr, int color) {

        int p = colorList.getCount()-1; // ListView 마지막 아이템
        Integer pp = (Integer)colorList.getItemAtPosition(p);  // ListView 마지막 아이템의 값

        // 같은 색깔이면 리스트뷰에 마지막 row를 삭제
        if (color == pp ) {

            blickClr.remove(p); // arrayList의 p(마지막 아이템) 삭제
            adapter.notifyDataSetChanged();
            score += 50;
            scoreText.setText(String.valueOf(score).toString());

            i++;
            toastText.setTypeface(null, Typeface.BOLD);
            toastText.setText(i+"Combo!");

            // combo가 5 이상일 경우 (combo - 4)  * 10 추가scroe 획득
            if (i >= 4){
                score +=(i - 4) * 10;
            }

            // blickClr가 0 되면 클리어
            else if (p == 0) {
                timer.cancel();
                Intent clearIntent = new Intent(MainActivity.this, ClearActivity.class);

                // score, timer 결과를 clearactivity로 넘김
                clearIntent.putExtra("scoreA", scoreText.getText().toString());
                clearIntent.putExtra("timerA", countText.getText().toString());
                startActivity(clearIntent);
            }
        }

        // 다른 color를 클릭할 경우
        if (color != pp){
            score -= 100;
            scoreText.setText(String.valueOf(score).toString());
            toastText.setText("");

            // combo = 0
            i = 0;

            // score가 -300점미만 이면 실패
            if (score <= -300){

                // countdown cancle
                timer.cancel();

                // over액티비티로 이동
                Intent overIntent = new Intent(MainActivity.this, OverActivity.class);
                startActivity(overIntent);
            }
        }
    }
}