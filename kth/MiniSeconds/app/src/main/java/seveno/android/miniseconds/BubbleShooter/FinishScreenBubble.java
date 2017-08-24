package seveno.android.miniseconds.BubbleShooter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

import seveno.android.miniseconds.GameEnding;
import seveno.android.miniseconds.R;

public class FinishScreenBubble extends AppCompatActivity {

    private TextView fin_bubble_score;
    private int T_score;
    private static long initialTime;
    private static int bubble_score;
    private static long elapsedTime;
    private static final int ERROR_PENALTY_SECONDS = 10;
    private long final_elapsedTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_screen_bubble);
        fin_bubble_score = (TextView) findViewById(R.id.fin_bubble_score);
        Intent  intent = getIntent();

        initialTime = intent.getLongExtra("seveno.android.miniseconds.bubbleshooter.bubbleGame.bubbleTime",0);
        bubble_score = intent.getIntExtra("seveno.android.miniseconds.bubbleshooter.bubbleGame.tscore2", 0);
        elapsedTime = intent.getLongExtra("seveno.android.miniseconds.BubbleShooter.BubbleGame.elapsedTime",0);
        final_elapsedTime = initialTime+elapsedTime;

       /* intent.putExtra("seveno.android.miniseconds.bubbleshooter.bubbleGame.bubbleTime", timeTakenMillis);
        intent.putExtra("seveno.android.miniseconds.bubbleshooter.bubbleGame.tscore2", T_score);
        intent.putExtra("seveno.android.miniseconds.BubbleShooter.BubbleGame.elapsedTime",elapsedTime);*/
        T_score = bubble_score;
        fin_bubble_score.setText("SCORE : " + String.valueOf(T_score));
        fin_bubble_score.setTextSize(20);
        setupInitialTimeTextView(initialTime);
        setupFinalTimeTextView(initialTime, elapsedTime);
    }

    private String convertToMinutesAndSeconds(long toConvert){
        int seconds = (int) (toConvert / 1000);
        int minutes = seconds / 60;
        seconds     = seconds % 60;
        String minutesAndSeconds = String.format(Locale.ENGLISH,"%d:%02d", minutes, seconds);
        return minutesAndSeconds;
    }
    private void setupInitialTimeTextView(long initialTime){
        TextView initialTimeTextView = (TextView)findViewById(getResources().getIdentifier("txt_b_time","id",this.getPackageName()));
        String initialTimeString = convertToMinutesAndSeconds(initialTime);
        initialTimeTextView.setText("Your time was "+initialTimeString);
    }

   /* private void setupNumErrorsTextView(int numErrors){
        TextView errorTextView = (TextView)findViewById(getResources().getIdentifier("textview_errors","id",this.getPackageName()));
        int timePenalty = numErrors*ERROR_PENALTY_SECONDS;
        if(numErrors==1){
            errorTextView.setText("You made "+numErrors+" error for a time penalty of "+timePenalty+" seconds.");
        } else {
            errorTextView.setText("You made "+numErrors+" errors for a time penalty of "+timePenalty+" seconds.");
        }
    }*/
   private void setupFinalTimeTextView(long initialTime, long elapsedTime){
       TextView finalTimeTextView = (TextView)findViewById(getResources().getIdentifier("txt_b_finaltime","id",this.getPackageName()));
       String finalTime = convertToMinutesAndSeconds(initialTime + elapsedTime);
       finalTimeTextView.setText("Your final time is "+finalTime);
   }

    public void btn_bubble_next(View view){
        Intent intent = new Intent(this, GameEnding.class);
        intent.putExtra("seveno.android.miniseconds.BubbleShooter.BubbleGame.initialTime",initialTime);
        intent.putExtra("seveno.android.miniseconds.gameEnding.tscore",T_score);
        intent.putExtra("seveno.android.miniseconds.gameEnding.elapsedTime",final_elapsedTime);
        /*
            long elapsedTime = intent.getLongExtra("seveno.android.miniseconds.gameEnding.elapsedTime", 0);
        int finalScore = intent.getIntExtra("seveno.android.miniseconds.gameEnding.tscore",0);

        */
        //startActivityForResult(intent, 0);
        startActivity(intent);
        finish();
    }

}
