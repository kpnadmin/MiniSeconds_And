package seveno.android.miniseconds;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;


import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;

import seveno.android.miniseconds.SpeedNumGame.SpeedyNumPlay;

public class MainActivity extends Activity {
    private TextView title_txt1;
    private Button btn_game_start, btn_logout;
    private static int RES_CODE = 255;
    private final int RES_OK = 0;
    private Intent  Mainintent;
    private int currentGameType = 0;
    private TextView google_txt1,google_txt2, google_txt3,google_txt4,google_txt5,google_txt6;
    private ImageView img_profile;
    private Uri photo_url;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       Mainintent = getIntent();
        setTheme(R.style.AppTheme);

        String charsetname = "UTF-8";
        String googlePhoto = null;




        btn_game_start = (Button) findViewById(R.id.btn_game_start);
        //btn_logout = (Button) findViewById(R.id.btn_logout);



        btn_game_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SpeedyNumPlay.class);
                int GameNum = 1;
                intent.putExtra("var1", RES_OK);
                intent.putExtra("seveno.android.miniseconds.speednumgame.currentGameType", 0);
                startActivityForResult(intent,RES_CODE);
            }
        });




}

}
