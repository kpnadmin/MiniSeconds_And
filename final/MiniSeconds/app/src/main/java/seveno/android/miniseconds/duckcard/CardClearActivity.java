package seveno.android.miniseconds.duckcard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

import seveno.android.miniseconds.R;
import seveno.android.miniseconds.colorbrick.CBStartActivity;

public class CardClearActivity extends AppCompatActivity {

    TextView scoreA, timerA;
    TextView txt_card_playtime;
    private int T_score;
    private static long initialTime;
    private static int card_score;
    private static long elapsedTime;
    private static double cardTakenTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_clearcard);

        backPressCloseSystem = new BackPressCloseSystem(this);

        /*backPressCloseSystem = new BackPressCloseSystem(this);*/

        scoreA = (TextView) findViewById(R.id.scoreA);
        timerA = (TextView) findViewById(R.id.timerA);
        txt_card_playtime = (TextView) findViewById(R.id.txt_card_playtime);

        Intent intent = getIntent();
   /*     initialTime = intent.getLongExtra("seveno.android.miniseconds.duckcard.cardclear.cardTime",0);
        card_score = intent.getIntExtra("seveno.android.miniseconds.duckcard.cardclear.tscore", 0);
        elapsedTime = intent.getLongExtra("seveno.android.miniseconds.duckcard.cardclear.elapsedTime",0);*/

        /*clearIntent.putExtra("seveno.android.miniseconds.duckcard.cardclear.tscore", T_score);
        clearIntent.putExtra("seveno.android.miniseconds.duckcard.cardclear.timerA", cot);*/
        //String scoreB = intent.getStringExtra("scoreA");
        //String scoreB = String.valueOf(card_score);
        // cardTakenTime = intent.getLongExtra("seveno.android.miniseconds.duckcard.cardclear.cardTime",0);


        int scoreB = intent.getIntExtra("seveno.android.miniseconds.duckcard.cardclear.tscore", 0);
        Double timerclear = intent.getDoubleExtra("seveno.android.miniseconds.duckcard.cardclear.timerA",-1.0);
         //String timerB = intent.getStringExtra("seveno.android.miniseconds.duckcard.cardclear.timerA");
        cardTakenTime = intent.getLongExtra("seveno.android.miniseconds.duckcard.cardclear.cardTime",0);
        elapsedTime = intent.getLongExtra("seveno.android.miniseconds.duckcard.cardclear.elapsedTime",0);

        scoreA.setText("Score : "+scoreB);
        timerA.setText("Time : "+timerclear);
        setupPlayTimeTextView(elapsedTime);

    }

    private void setupPlayTimeTextView(long elapsedTime){
        TextView initialTimeTextView = (TextView)findViewById(R.id.txt_card_playtime);
        String initialTimeString = convertToMinutesAndSeconds(elapsedTime);
        initialTimeTextView.setText("Your time was "+initialTimeString);
    }

    private String convertToMinutesAndSeconds(long toConvert){
        int seconds = (int) (toConvert / 1000);
        int minutes = seconds / 60;
        seconds     = seconds % 60;
        String minutesAndSeconds = String.format(Locale.ENGLISH,"%d:%02d", minutes, seconds);
        return minutesAndSeconds;
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
                nextIntent.putExtra("seveno.android.miniseconds.colorbrick.cbstart.initialTime",cardTakenTime);
                nextIntent.putExtra("seveno.android.miniseconds.colorbrick.cbstart.tscore", T_score);
                nextIntent.putExtra("seveno.android.miniseconds.colorbrick.cbstart.elapsedTime",elapsedTime);
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
