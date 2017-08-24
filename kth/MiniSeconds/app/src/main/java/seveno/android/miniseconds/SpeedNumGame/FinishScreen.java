package seveno.android.miniseconds.SpeedNumGame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.content.Intent;

import java.util.Locale;

import seveno.android.miniseconds.BubbleShooter.BubbleGame;
import seveno.android.miniseconds.MainActivity;
//import seveno.android.miniseconds.PoppingBall.PoppingBallGameActivity;
import seveno.android.miniseconds.R;

public class FinishScreen extends AppCompatActivity {
    private static final int ERROR_PENALTY_SECONDS = 10;
    private TextView fin_speedy_score;
    private int T_score;
    private static long initialTime;
    private static int speedy_score;
    private static long elapsedTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_screen);
        fin_speedy_score = (TextView) findViewById(R.id.fin_speedy_score);

        initialTime = getIntent().getLongExtra("seveno.android.miniseconds.speednumgame.takenspeedyTime",0);
        int numErrors = getIntent().getIntExtra("seveno.android.miniseconds.speednumgame.numErrors",0);
        speedy_score = getIntent().getIntExtra("seveno.android.miniseconds.speednumgame.speedy_score", 0);
         elapsedTime = getIntent().getLongExtra("seveno.android.miniseconds.speednumgame.elapsedTime",0);
        //long elapsedTime = initialTime;

        T_score = speedy_score;
        fin_speedy_score.setText("SCORE : " + String.valueOf(speedy_score));
        fin_speedy_score.setTextSize(20);
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
        TextView initialTimeTextView = (TextView)findViewById(getResources().getIdentifier("textview_time","id",this.getPackageName()));
        String initialTimeString = convertToMinutesAndSeconds(initialTime);
        initialTimeTextView.setText("Your time was "+initialTimeString);
    }

    private void setupNumErrorsTextView(int numErrors){
        TextView errorTextView = (TextView)findViewById(getResources().getIdentifier("textview_errors","id",this.getPackageName()));
        int timePenalty = numErrors*ERROR_PENALTY_SECONDS;
        if(numErrors==1){
            errorTextView.setText("You made "+numErrors+" error for a time penalty of "+timePenalty+" seconds.");
        } else {
            errorTextView.setText("You made "+numErrors+" errors for a time penalty of "+timePenalty+" seconds.");
        }
    }

   /* private void setupFinalTimeTextView(long initialTime, int numErrors){
        TextView finalTimeTextView = (TextView)findViewById(getResources().getIdentifier("textview_finaltime","id",this.getPackageName()));
        String finalTime = convertToMinutesAndSeconds(initialTime + (numErrors*ERROR_PENALTY_SECONDS*1000));
        finalTimeTextView.setText("Your final time is "+finalTime);
    }*/
   private void setupFinalTimeTextView(long initialTime, long elapsedTime){
       TextView finalTimeTextView = (TextView)findViewById(getResources().getIdentifier("textview_finaltime","id",this.getPackageName()));
       String finalTime = convertToMinutesAndSeconds(initialTime + elapsedTime);
       finalTimeTextView.setText("Your final time is "+finalTime);
   }



    public void btn_ReturnMain(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("seveno.android.miniseconds.avoidstargame.initialTime",0);
        intent.putExtra("seveno.android.miniseconds.avoidstargame.numErrors",0);
        startActivity(intent);
        finish();
    }
    public void btn_NextGame(View view){
        Intent intent = new Intent(this, BubbleGame.class);
        intent.putExtra("seveno.android.miniseconds.BubbleShooter.BubbleGame.initialTime",initialTime);
        intent.putExtra("seveno.android.miniseconds.BubbleShooter.BubbleGame.tscore",T_score);
        intent.putExtra("seveno.android.miniseconds.BubbleShooter.BubbleGame.elapsedTime",elapsedTime);
        //startActivityForResult(intent, 0);
        startActivity(intent);
        finish();
    }


}
