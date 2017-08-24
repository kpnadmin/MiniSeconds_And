package seveno.android.miniseconds;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;

import java.util.Locale;

public class GameEnding extends AppCompatActivity implements View.OnClickListener{
        private TextView text_end_title, text_end_playtime, text_end_Score;
        private Button btn_end_Return, btn_end_List;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_ending);

        text_end_title = (TextView) findViewById(R.id.text_end_title);
        text_end_playtime = (TextView) findViewById(R.id.text_end_playtime);
        text_end_Score = (TextView) findViewById(R.id.text_end_Score);
        btn_end_Return = (Button) findViewById(R.id.btn_end_Return);
        btn_end_List = (Button)findViewById(R.id.btn_end_List);

        Intent intent = getIntent();

        long elapsedTime = intent.getLongExtra("seveno.android.miniseconds.gameEnding.elapsedTime", 0);
        int finalScore = intent.getIntExtra("seveno.android.miniseconds.gameEnding.tscore",0);
        setupInitialTimeTextView(elapsedTime);
        text_end_Score.setText(String.valueOf(finalScore));

       /* intent.putExtra("seveno.android.miniseconds.gameEnding.elapsedTime",elapsedTime);
        intent.putExtra("seveno.android.miniseconds.gameEnding.tscore",T_score);*/


        btn_end_Return.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_end_Return :
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("seveno.android.miniseconds.initialTime",0);
                intent.putExtra("seveno.android.miniseconds.numErrors",0);
                startActivityForResult(intent, 0);
                finish();
                break;
        }
    }


    private String convertToMinutesAndSeconds(long toConvert){
        int seconds = (int) (toConvert / 1000);
        int minutes = seconds / 60;
        seconds     = seconds % 60;
        String minutesAndSeconds = String.format(Locale.ENGLISH,"%d:%02d", minutes, seconds);
        return minutesAndSeconds;
    }
    private void setupInitialTimeTextView(long elapsedTime){
        String initialTimeString = convertToMinutesAndSeconds(elapsedTime);
        text_end_playtime.setText("Your time was "+initialTimeString);
    }

}
