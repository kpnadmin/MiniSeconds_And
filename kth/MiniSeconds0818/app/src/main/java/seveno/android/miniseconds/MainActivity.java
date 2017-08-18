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
       Mainintent = getIntent();/*
        String googlename = Mainintent.getStringExtra("username");
        String googleEmail = Mainintent.getStringExtra("userEmail");
        String googleId = Mainintent.getStringExtra("userId");*/
        String charsetname = "UTF-8";
        String googlePhoto = null;
        /*try {
            googlePhoto = URLDecoder.decode(Mainintent.getStringExtra("userPhoto"), charsetname);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/
        //String googleIdToken = Mainintent.getStringExtra("userIdToken");
       /* try {
            photo_url = Uri.parse(googlePhoto);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
       // String googleServerCode = Mainintent.getStringExtra("userServerCode");




        btn_game_start = (Button) findViewById(R.id.btn_game_start);
        //btn_logout = (Button) findViewById(R.id.btn_logout);

     /* google_txt1 = (TextView) findViewById(R.id.txt_google1);
        google_txt2 = (TextView) findViewById(R.id.txt_google2);
        google_txt3 = (TextView) findViewById(R.id.txt_google3);
        google_txt4 = (TextView) findViewById(R.id.txt_google4);
        google_txt5 = (TextView) findViewById(R.id.txt_google5);
        google_txt6 = (TextView) findViewById(R.id.txt_google6);

        img_profile = (ImageView) findViewById(R.id.img_profile);
        google_txt1.setText(googlename);
        google_txt2.setText(googleEmail);
        google_txt3.setText(googleId);
        google_txt4.setText(googlePhoto);
        google_txt5.setText(googleIdToken);
        google_txt6.setText(googleServerCode);

        google_txt1.setTextColor(Color.WHITE);
        google_txt1.setTextSize(20);
        google_txt2.setTextColor(Color.WHITE);
        google_txt2.setTextSize(20);
        google_txt3.setTextColor(Color.WHITE);
        google_txt3.setTextSize(20);
        google_txt4.setTextColor(Color.WHITE);
        google_txt4.setTextSize(20);
        google_txt5.setTextColor(Color.WHITE);
        google_txt5.setTextSize(20);
        google_txt6.setTextColor(Color.WHITE);
        google_txt6.setTextSize(20);*/

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

   /*     final String finalGooglePhoto = googlePhoto;
        Thread proThread = new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL(finalGooglePhoto);
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setDoInput(true);
                    conn.connect();

                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        proThread.start();




            try {
                proThread.join();

                img_profile.setImageBitmap(bitmap);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/




      /*  btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int logoutNum = -2;
                Mainintent.putExtra("logoutNum", logoutNum);
                setResult(RES_OK, Mainintent);
                finish();
            }
        });*/


     /*   boolean isRunIntro = getIntent().getBooleanExtra("intro", true);
        if(isRunIntro) {
            beforeIntro();
        } else {
            afterIntro(savedInstanceState);
        }*/
    }
    // 인트로 화면
    private void beforeIntro() {
     /*   // 약 2초간 인트로 화면을 출력.
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("intro", false);
                startActivity(intent);
                // 액티비티 이동시 페이드인/아웃 효과를 보여준다. 즉, 인트로
                //    화면에 부드럽게 사라진다.
                overridePendingTransition(android.R.anim.fade_in,
                        android.R.anim.fade_out);
            }
        }, 2000);*/
    }

    // 인트로 화면 이후.
    private void afterIntro(Bundle savedInstanceState) {
      /*  // 기본 테마를 지정한다.
        setTheme(R.style.AppBaseTheme);
        setContentView(R.layout.activity_main);

        title_txt1 = (TextView) findViewById(R.id.title_txt1);
        title_txt1.setTextColor(getResources().getColor(R.color.White));*/

    }

    public void btn_SpeedyClick(){

    }
}