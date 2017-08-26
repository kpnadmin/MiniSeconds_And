package seveno.android.miniseconds.colorbrick;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

import seveno.android.miniseconds.R;
import seveno.android.miniseconds.duckcard.BackPressCloseSystem;

public class CBOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b_activity_overcb);

        backPressCloseSystem = new BackPressCloseSystem(this);

        Button mainBtn = (Button) findViewById(R.id.mainBtn);
        Button reBtn = (Button) findViewById(R.id.reBtn);

        Intent intent = getIntent();


        reBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), CBStartActivity.class);
                startActivity(intent);
            }
        });
        mainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainintent = new Intent(getApplicationContext(), CBStartActivity.class);
                startActivity(mainintent);
            }
        });
    }

    private String convertToMinutesAndSeconds(long toConvert){
        int seconds = (int) (toConvert / 1000);
        int minutes = seconds / 60;
        seconds     = seconds % 60;
        String minutesAndSeconds = String.format(Locale.ENGLISH,"%d:%02d", minutes, seconds);
        return minutesAndSeconds;
    }


    private void setupPlayTimeTextView(long elapsedTime){
        TextView initialTimeTextView = (TextView)findViewById(R.id.txt_card_playtime);
        String initialTimeString = convertToMinutesAndSeconds(elapsedTime);
        initialTimeTextView.setText("Your time was "+initialTimeString);
    }

    private BackPressCloseSystem backPressCloseSystem;

    @Override
    public void onBackPressed() {
        backPressCloseSystem.onBackPressed();
    }
}
