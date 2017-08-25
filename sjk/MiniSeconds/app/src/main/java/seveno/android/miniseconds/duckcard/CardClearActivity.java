package seveno.android.miniseconds.duckcard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import seveno.android.miniseconds.R;
import seveno.android.miniseconds.colorbrick.CBStartActivity;

public class CardClearActivity extends AppCompatActivity {

    TextView scoreA, timerA;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_clearcard);

        backPressCloseSystem = new BackPressCloseSystem(this);

        /*backPressCloseSystem = new BackPressCloseSystem(this);*/

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
                Intent mainIntent = new Intent(getApplicationContext(), CardStartActivity.class);
                startActivity(mainIntent);
                break;

            case R.id.reBtn:
                Intent reIntent = new Intent(getApplicationContext(), CardStartActivity.class);
                startActivity(reIntent);
                break;

            case R.id.nextBtn:
                Intent nextIntent = new Intent(getApplicationContext(), CBStartActivity.class);
                startActivity(nextIntent);
                break;
        }
    }
    private BackPressCloseSystem backPressCloseSystem;

    @Override
    public void onBackPressed() {
        backPressCloseSystem.onBackPressed();
    }

    /*private BackPressCloseSystem backPressCloseSystem;

    @Override
    public void onBackPressed() {
        backPressCloseSystem.onBackPressed();
    }*/
}
