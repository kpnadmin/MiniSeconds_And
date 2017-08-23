package com.android.projectcard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OverActivity extends AppCompatActivity {

    Button mainBtn, reBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_over);

        mainBtn = (Button) findViewById(R.id.mainBtn);
        reBtn = (Button) findViewById(R.id.reBtn);

        reBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), StartActivity.class);
                startActivity(intent);
            }
        });
        mainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainintent = new Intent(getApplicationContext(), StartActivity.class);
                startActivity(mainintent);
            }
        });
    }
}
