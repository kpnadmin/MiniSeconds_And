package com.android.projectcard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class ClearActivity extends AppCompatActivity {

    TextView scoreA, timerA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear);

        scoreA = (TextView) findViewById(R.id.scoreA);
        timerA = (TextView) findViewById(R.id.timerA);

        Intent intent = getIntent();

        String scoreB = intent.getStringExtra("scoreA");
        scoreA.setText("Score : "+scoreB);

        String timerB = intent.getStringExtra("timerA");
        timerA.setText("Timer : "+timerB);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.mainBtn:
                Intent mainIntent = new Intent(getApplicationContext(), StartActivity.class);
                startActivity(mainIntent);
                break;

            case R.id.reBtn:
                Intent reIntent = new Intent(getApplicationContext(), StartActivity.class);
                startActivity(reIntent);
                break;

            case R.id.nextBtn:

                break;
        }
    }
}
