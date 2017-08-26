package seveno.android.miniseconds;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import seveno.android.miniseconds.duckcard.BackPressCloseSystem;
import seveno.android.miniseconds.duckcard.CardStartActivity;

public class MainActivity extends AppCompatActivity {


    private TextView title_txt1;
    private Button btn_game_start, btn_logout;
    private static int RES_CODE = 255;
    private final int RES_OK = 0;
    private Intent Mainintent;
    private int currentGameType = 0;
    private TextView google_txt1,google_txt2, google_txt3,google_txt4,google_txt5,google_txt6;
    private ImageView img_profile;
    private Uri photo_url;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backPressCloseSystem = new BackPressCloseSystem(this);

        Mainintent = getIntent();


        btn_game_start = (Button) findViewById(R.id.btn_game_start);
        //btn_logout = (Button) findViewById(R.id.btn_logout);



        btn_game_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),CardStartActivity.class);
                int GameNum = 1;
                intent.putExtra("var1", RES_OK);
                intent.putExtra("seveno.android.miniseconds.duckcard.currentGameType", 0);
                startActivityForResult(intent,RES_CODE);
            }
        });

    }

    private BackPressCloseSystem backPressCloseSystem;

    @Override
    public void onBackPressed() {
        backPressCloseSystem.onBackPressed();
    }
}
