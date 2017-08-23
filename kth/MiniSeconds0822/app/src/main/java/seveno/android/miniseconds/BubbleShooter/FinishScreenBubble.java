package seveno.android.miniseconds.BubbleShooter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

import seveno.android.miniseconds.GameEnding;
import seveno.android.miniseconds.MainActivity;
import seveno.android.miniseconds.R;

public class FinishScreenBubble extends AppCompatActivity {
    private static final int ERROR_PENALTY_SECONDS = 10;
    private TextView txt_b_score, txt_b_finished;
    private int T_score;
    private static long bubbleTime;
    private static int bubblescore;
    private static long elapsedTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_screen_bubble);
        txt_b_score = (TextView) findViewById(R.id.txt_b_score);
        txt_b_finished = (TextView) findViewById(R.id.txt_b_finished);


       /* intent.putExtra("seveno.android.miniseconds.bubbleshooter.bubbleGame.BubbleTime", timeTakenMillis);
        intent.putExtra("seveno.android.miniseconds.bubbleshooter.bubbleGame.tscore2", T_score);
        intent.putExtra("seveno.android.miniseconds.BubbleShooter.BubbleGame.elapsedTime",elapsedTime);*/

        bubbleTime = getIntent().getLongExtra("seveno.android.miniseconds.bubbleshooter.bubbleGame.bubbleTime",0);
        //int numErrors = getIntent().getIntExtra("seveno.android.miniseconds.speednumgame.numErrors",0);
        int t2score = getIntent().getIntExtra("seveno.android.miniseconds.bubbleshooter.bubbleGame.tscore2", 0);
        elapsedTime = getIntent().getLongExtra("seveno.android.miniseconds.bubbleshooter.bubbleGame.elapsedTime",0);


        T_score = t2score;
        txt_b_score.setText(String.valueOf(T_score));
        txt_b_score.setTextSize(20);
        setupInitialTimeTextView(bubbleTime);
        setupFinalTimeTextView(bubbleTime, elapsedTime);
    }


    private String convertToMinutesAndSeconds(long toConvert){
        int seconds = (int) (toConvert / 1000);
        int minutes = seconds / 60;
        seconds     = seconds % 60;
        String minutesAndSeconds = String.format(Locale.ENGLISH,"%d:%02d", minutes, seconds);
        return minutesAndSeconds;
    }
    private void setupInitialTimeTextView(long bubbleTime){
        TextView initialTimeTextView = (TextView)findViewById(getResources().getIdentifier("txt_b_time","id",this.getPackageName()));
        String initialTimeString = convertToMinutesAndSeconds(bubbleTime);
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

    private void setupFinalTimeTextView(long bubbleTime, long elapsedTime){
        TextView finalTimeTextView = (TextView)findViewById(getResources().getIdentifier("txt_b_finaltime","id",this.getPackageName()));
        String finalTime = convertToMinutesAndSeconds(bubbleTime + elapsedTime);
        finalTimeTextView.setText("Your final time is "+finalTime);
    }

  /*  public void btn_ReturnMain(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("seveno.android.miniseconds.avoidstargame.initialTime",0);
        intent.putExtra("seveno.android.miniseconds.avoidstargame.numErrors",0);
        startActivity(intent);
        finish();
    }*/
    public void btn_Bubble_Next(View view){
        Intent intent = new Intent(this, GameEnding.class);
        intent.putExtra("seveno.android.miniseconds.gameEnding.elapsedTime",elapsedTime);
        intent.putExtra("seveno.android.miniseconds.gameEnding.tscore",T_score);
        //startActivityForResult(intent, 0);
        startActivity(intent);
        finish();
    }

}
