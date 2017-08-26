package seveno.android.miniseconds.colorbrick;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

import seveno.android.miniseconds.R;
import seveno.android.miniseconds.duckcard.BackPressCloseSystem;

public class CBClearActivity extends AppCompatActivity {

    TextView scoreA, timerA;

    //
    private int T_score;
    private static long initialTime;
    private static int card_score;
    private static long elapsedTime;
    private static double cardTakenTime;

    private TextView txt_cb_playtime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b_activity_clearcb);

        Intent intent = getIntent();

        backPressCloseSystem = new BackPressCloseSystem(this);

        scoreA = (TextView) findViewById(R.id.scoreA);
        timerA = (TextView) findViewById(R.id.timerA);
        txt_cb_playtime = (TextView) findViewById(R.id.txt_cb_playtime);

       /* clearIntent.putExtra("seveno.android.miniseconds.colorbrick.cbclear.tscore", T_score);
        clearIntent.putExtra("seveno.android.miniseconds.colorbrick.cbclear.timerA", cot);
        clearIntent.putExtra("seveno.android.miniseconds.colorbrick.cbclear.cbPlayTime",timeTakenMillis);
        clearIntent.putExtra("seveno.android.miniseconds.colorbrick.cbclear.elapsedTime",elapsedTime);*/

        int scoreB = intent.getIntExtra("seveno.android.miniseconds.colorbrick.cbclear.tscore", 0);
        String timerB = intent.getStringExtra("seveno.android.miniseconds.colorbrick.cbclear.timerA");
        cardTakenTime = intent.getLongExtra("seveno.android.miniseconds.colorbrick.cbclear.cardTime",0);
        elapsedTime = intent.getLongExtra("seveno.android.miniseconds.colorbrick.cbclear.elapsedTime",0);


        //String scoreB = intent.getStringExtra("scoreA");
        scoreA.setText("Score : "+scoreB);

        //String timerB = intent.getStringExtra("timerA");
        timerA.setText("Time : "+timerB);

        setupPlayTimeTextView(elapsedTime);

    }
    private String convertToMinutesAndSeconds(long toConvert){
        int seconds = (int) (toConvert / 1000);
        int minutes = seconds / 60;
        seconds     = seconds % 60;
        String minutesAndSeconds = String.format(Locale.ENGLISH,"%d:%02d", minutes, seconds);
        return minutesAndSeconds;
    }


    private void setupPlayTimeTextView(long elapsedTime){
        TextView initialTimeTextView = (TextView)findViewById(R.id.txt_cb_playtime);
        String initialTimeString = convertToMinutesAndSeconds(elapsedTime);
        initialTimeTextView.setText("Your time was "+initialTimeString);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.mainBtn:
                Intent mainIntent = new Intent(getApplicationContext(), CBStartActivity.class);
                startActivity(mainIntent);
                break;

            case R.id.reBtn:
                Intent reIntent = new Intent(getApplicationContext(), CBStartActivity.class);
                startActivity(reIntent);
                break;

            case R.id.nextBtn:

                break;
        }
    }

    private BackPressCloseSystem backPressCloseSystem;

    @Override
    public void onBackPressed() {
        backPressCloseSystem.onBackPressed();
    }
}
