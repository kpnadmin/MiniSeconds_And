package seveno.android.miniseconds.duckcard;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import seveno.android.miniseconds.R;

public class CardMainActivity extends AppCompatActivity {

    ImageView mainCard, card1, card2, card3, card4, card5, card6, card7, card8, card9, card10, card11,
            card12, card13, card14, card15, card16, card17, card18, card19, card20, card21, card22,
            card23, card24, card25, card26, card27, card28, card29, card30= null;

    int[] img = {R.drawable.a_coka, R.drawable.a_dalma, R.drawable.a_shun, R.drawable.a_poodle, R.drawable.a_syong};

    ProgressBar drogBar;
    TextView countText,scoreText;

    int coka = R.drawable.a_coka;
    int dalma = R.drawable.a_dalma;
    int poodle = R.drawable.a_poodle;
    int shun = R.drawable.a_shun;
    int syong = R.drawable.a_syong;
    ArrayList<ImageView> imv2 = new ArrayList<ImageView>();
    Toast t = null;
    int ok = 0;

    Integer score = 0;

    Vibrator vibe;

    //카운트다운

    CountDownTimer timer;
    int millisInFuture = 40100 ;
    double fr = 4.0;

    //
    private static long initialTime;
    private static int duckcard_score;
    private static long elapsedTime;
      private static long startTime;
        private static long timeTakenMillis;
    private static double cardTakenTime;

                                     //
    private int T_score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_maincard);

        backPressCloseSystem = new BackPressCloseSystem(this);


        //img 랜덤선언
        Random random = new Random();

        // img.length전체중 랜덤
        int ran = random.nextInt(img.length);

        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        scoreText = (TextView) findViewById(R.id.scoreText);




        mainCard = (ImageView) findViewById(R.id.mainCard);

        Intent intent = getIntent();

        initialTime = intent.getLongExtra("seveno.android.miniseconds.duckcard.cardmain.initialTime",0);
        duckcard_score = intent.getIntExtra("seveno.android.miniseconds.duckcard.cardmain.tscore", 0);
        elapsedTime = intent.getLongExtra("seveno.android.miniseconds.duckcard.cardmain.elapsedTime",0);
            score   = duckcard_score;
               scoreText.setText(String.valueOf(score).toString());


        card1  = (ImageView) findViewById(R.id.card1);
        card2  = (ImageView) findViewById(R.id.card2);
        card3  = (ImageView) findViewById(R.id.card3);
        card4  = (ImageView) findViewById(R.id.card4);
        card5  = (ImageView) findViewById(R.id.card5);
        card6  = (ImageView) findViewById(R.id.card6);
        card7  = (ImageView) findViewById(R.id.card7);
        card8  = (ImageView) findViewById(R.id.card8);
        card9  = (ImageView) findViewById(R.id.card9);
        card10 = (ImageView) findViewById(R.id.card10);
        card11 = (ImageView) findViewById(R.id.card11);
        card12 = (ImageView) findViewById(R.id.card12);
        card13 = (ImageView) findViewById(R.id.card13);
        card14 = (ImageView) findViewById(R.id.card14);
        card15 = (ImageView) findViewById(R.id.card15);
        card16 = (ImageView) findViewById(R.id.card16);
        card17 = (ImageView) findViewById(R.id.card17);
        card18 = (ImageView) findViewById(R.id.card18);
        card19 = (ImageView) findViewById(R.id.card19);
        card20 = (ImageView) findViewById(R.id.card20);
        card21 = (ImageView) findViewById(R.id.card21);
        card22 = (ImageView) findViewById(R.id.card22);
        card23 = (ImageView) findViewById(R.id.card23);
        card24 = (ImageView) findViewById(R.id.card24);
        card25 = (ImageView) findViewById(R.id.card25);
        card26 = (ImageView) findViewById(R.id.card26);
        card27 = (ImageView) findViewById(R.id.card27);
        card28 = (ImageView) findViewById(R.id.card28);
        card29 = (ImageView) findViewById(R.id.card29);
        card30 = (ImageView) findViewById(R.id.card30);

        drogBar = (ProgressBar) findViewById(R.id.drogBar);

        //랜덤으로 섞기
        imv2.add(card1);
        imv2.add(card2);
        imv2.add(card3);
        imv2.add(card4);
        imv2.add(card5);
        imv2.add(card6);
        imv2.add(card7);
        imv2.add(card8);
        imv2.add(card9);
        imv2.add(card10);
        imv2.add(card11);
        imv2.add(card12);
        imv2.add(card13);
        imv2.add(card14);
        imv2.add(card15);
        imv2.add(card16);
        imv2.add(card17);
        imv2.add(card18);
        imv2.add(card19);
        imv2.add(card20);
        imv2.add(card21);
        imv2.add(card22);
        imv2.add(card23);
        imv2.add(card24);
        imv2.add(card25);
        imv2.add(card26);
        imv2.add(card27);
        imv2.add(card28);
        imv2.add(card29);
        imv2.add(card30);
        Collections.shuffle(imv2);

        //이미지뷰에 이미지 삽입
        mainCard     . setImageResource(img[ran]);
        imv2.get(0)  . setImageResource(coka);
        imv2.get(1)  . setImageResource(coka);
        imv2.get(2)  . setImageResource(coka);
        imv2.get(3)  . setImageResource(coka);
        imv2.get(4)  . setImageResource(coka);
        imv2.get(5)  . setImageResource(coka);
        imv2.get(6)  . setImageResource(dalma);
        imv2.get(7)  . setImageResource(dalma);
        imv2.get(8)  . setImageResource(dalma);
        imv2.get(9)  . setImageResource(dalma);
        imv2.get(10) . setImageResource(dalma);
        imv2.get(11) . setImageResource(dalma);
        imv2.get(12) . setImageResource(poodle);
        imv2.get(13) . setImageResource(poodle);
        imv2.get(14) . setImageResource(poodle);
        imv2.get(15) . setImageResource(poodle);
        imv2.get(16) . setImageResource(poodle);
        imv2.get(17) . setImageResource(poodle);
        imv2.get(18) . setImageResource(shun);
        imv2.get(19) . setImageResource(shun);
        imv2.get(20) . setImageResource(shun);
        imv2.get(21) . setImageResource(shun);
        imv2.get(22) . setImageResource(shun);
        imv2.get(23) . setImageResource(shun);
        imv2.get(24) . setImageResource(syong);
        imv2.get(25) . setImageResource(syong);
        imv2.get(26) . setImageResource(syong);
        imv2.get(27) . setImageResource(syong);
        imv2.get(28) . setImageResource(syong);
        imv2.get(29) . setImageResource(syong);

        //drawble로 변환 후
        Drawable main = mainCard.getDrawable();
        Drawable c1  = imv2.get(0) .getDrawable();
        Drawable c2  = imv2.get(1) .getDrawable();
        Drawable c3  = imv2.get(2) .getDrawable();
        Drawable c4  = imv2.get(3) .getDrawable();
        Drawable c5  = imv2.get(4) .getDrawable();
        Drawable c6  = imv2.get(5) .getDrawable();
        Drawable c7  = imv2.get(6) .getDrawable();
        Drawable c8  = imv2.get(7) .getDrawable();
        Drawable c9  = imv2.get(8) .getDrawable();
        Drawable c10 = imv2.get(9) .getDrawable();
        Drawable c11 = imv2.get(10).getDrawable();
        Drawable c12 = imv2.get(11).getDrawable();
        Drawable c13 = imv2.get(12).getDrawable();
        Drawable c14 = imv2.get(13).getDrawable();
        Drawable c15 = imv2.get(14).getDrawable();
        Drawable c16 = imv2.get(15).getDrawable();
        Drawable c17 = imv2.get(16).getDrawable();
        Drawable c18 = imv2.get(17).getDrawable();
        Drawable c19 = imv2.get(18).getDrawable();
        Drawable c20 = imv2.get(19).getDrawable();
        Drawable c21 = imv2.get(20).getDrawable();
        Drawable c22 = imv2.get(21).getDrawable();
        Drawable c23 = imv2.get(22).getDrawable();
        Drawable c24 = imv2.get(23).getDrawable();
        Drawable c25 = imv2.get(24).getDrawable();
        Drawable c26 = imv2.get(25).getDrawable();
        Drawable c27 = imv2.get(26).getDrawable();
        Drawable c28 = imv2.get(27).getDrawable();
        Drawable c29 = imv2.get(28).getDrawable();
        Drawable c30 = imv2.get(29).getDrawable();

        //bitmap으로 변환(이미지 비교를 위해)
        final Bitmap mainbit = ((BitmapDrawable)main).getBitmap();
        final Bitmap c1bit  = ((BitmapDrawable)c1) .getBitmap();
        final Bitmap c2bit  = ((BitmapDrawable)c2) .getBitmap();
        final Bitmap c3bit  = ((BitmapDrawable)c3) .getBitmap();
        final Bitmap c4bit  = ((BitmapDrawable)c4) .getBitmap();
        final Bitmap c5bit  = ((BitmapDrawable)c5) .getBitmap();
        final Bitmap c6bit  = ((BitmapDrawable)c6) .getBitmap();
        final Bitmap c7bit  = ((BitmapDrawable)c7) .getBitmap();
        final Bitmap c8bit  = ((BitmapDrawable)c8) .getBitmap();
        final Bitmap c9bit  = ((BitmapDrawable)c9) .getBitmap();
        final Bitmap c10bit = ((BitmapDrawable)c10).getBitmap();
        final Bitmap c11bit = ((BitmapDrawable)c11).getBitmap();
        final Bitmap c12bit = ((BitmapDrawable)c12).getBitmap();
        final Bitmap c13bit = ((BitmapDrawable)c13).getBitmap();
        final Bitmap c14bit = ((BitmapDrawable)c14).getBitmap();
        final Bitmap c15bit = ((BitmapDrawable)c15).getBitmap();
        final Bitmap c16bit = ((BitmapDrawable)c16).getBitmap();
        final Bitmap c17bit = ((BitmapDrawable)c17).getBitmap();
        final Bitmap c18bit = ((BitmapDrawable)c18).getBitmap();
        final Bitmap c19bit = ((BitmapDrawable)c19).getBitmap();
        final Bitmap c20bit = ((BitmapDrawable)c20).getBitmap();
        final Bitmap c21bit = ((BitmapDrawable)c21).getBitmap();
        final Bitmap c22bit = ((BitmapDrawable)c22).getBitmap();
        final Bitmap c23bit = ((BitmapDrawable)c23).getBitmap();
        final Bitmap c24bit = ((BitmapDrawable)c24).getBitmap();
        final Bitmap c25bit = ((BitmapDrawable)c25).getBitmap();
        final Bitmap c26bit = ((BitmapDrawable)c26).getBitmap();
        final Bitmap c27bit = ((BitmapDrawable)c27).getBitmap();
        final Bitmap c28bit = ((BitmapDrawable)c28).getBitmap();
        final Bitmap c29bit = ((BitmapDrawable)c29).getBitmap();
        final Bitmap c30bit = ((BitmapDrawable)c30).getBitmap();



        imv2.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //이미지 비교
                if (mainbit.equals(c1bit)) {
                    
                    //클릭한 이미지뷰 어둡게 표시
                    yesMethod(imv2.get(0));
                }
                else if (!mainbit.equals(c1bit)){
                    noMethod(imv2.get(0));
                }
            }
        });
        imv2.get(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainbit.equals(c2bit)) {

                    yesMethod(imv2.get(1));
                }
                else if (!mainbit.equals(c2bit)){
                    noMethod(imv2.get(1));
                }
            }
        });
        imv2.get(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainbit.equals(c3bit)) {

                    yesMethod(imv2.get(2));
                }
                else if (!mainbit.equals(c3bit)){
                    noMethod(imv2.get(2));
                }
            }
        });
        imv2.get(3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainbit.equals(c4bit)) {

                    yesMethod(imv2.get(3));
                }
                else if (!mainbit.equals(c4bit)){
                    noMethod(imv2.get(3));
                }
            }
        });
        imv2.get(4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainbit.equals(c5bit)) {

                    yesMethod(imv2.get(4));
                }
                else if (!mainbit.equals(c5bit)){
                    noMethod(imv2.get(4));
                }
            }
        });
        imv2.get(5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainbit.equals(c6bit)) {

                    yesMethod(imv2.get(5));
                }
                else if (!mainbit.equals(c6bit)){
                    noMethod(imv2.get(5));
                }
            }
        });
        imv2.get(6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainbit.equals(c7bit)) {

                    yesMethod(imv2.get(6));
                }
                else if (!mainbit.equals(c7bit)){
                    noMethod(imv2.get(6));
                }
            }
        });
        imv2.get(7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainbit.equals(c8bit)) {

                    yesMethod(imv2.get(7));
                }
                else if (!mainbit.equals(c8bit)){
                    noMethod(imv2.get(7));
                }
            }
        });
        imv2.get(8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainbit.equals(c9bit)) {

                    yesMethod(imv2.get(8));
                }
                else if (!mainbit.equals(c9bit)){
                    noMethod(imv2.get(8));
                }
            }
        });
        imv2.get(9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainbit.equals(c10bit)) {

                    yesMethod(imv2.get(9));
                }
                else if (!mainbit.equals(c10bit)){
                    noMethod(imv2.get(9));
                }
            }
        });
        imv2.get(10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainbit.equals(c11bit)) {

                    yesMethod(imv2.get(10));
                }
                else if (!mainbit.equals(c11bit)){
                    noMethod(imv2.get(10));
                }
            }
        });
        imv2.get(11).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainbit.equals(c12bit)) {

                    yesMethod(imv2.get(11));
                }
                else if (!mainbit.equals(c12bit)){
                    noMethod(imv2.get(11));
                }
            }
        });
        imv2.get(12).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainbit.equals(c13bit)) {

                    yesMethod(imv2.get(12));
                }
                else if (!mainbit.equals(c13bit)){
                    noMethod(imv2.get(12));
                }
            }
        });
        imv2.get(13).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainbit.equals(c14bit)) {

                    yesMethod(imv2.get(13));
                }
                else if (!mainbit.equals(c14bit)){
                    noMethod(imv2.get(13));
                }
            }
        });
        imv2.get(14).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainbit.equals(c15bit)) {

                    yesMethod(imv2.get(14));
                }
                else if (!mainbit.equals(c15bit)){
                    noMethod(imv2.get(14));
                }
            }
        });
        imv2.get(15).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainbit.equals(c16bit)) {

                    yesMethod(imv2.get(15));
                }
                else if (!mainbit.equals(c16bit)){
                    noMethod(imv2.get(15));
                    
                }
            }
        });
        imv2.get(16).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainbit.equals(c17bit)) {

                    yesMethod(imv2.get(16));
                }
                else if (!mainbit.equals(c17bit)){
                    noMethod(imv2.get(16));
                    
                }
            }
        });
        imv2.get(17).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainbit.equals(c18bit)) {

                    yesMethod(imv2.get(17));
                }
                else if (!mainbit.equals(c18bit)){
                    noMethod(imv2.get(17));
                    
                }
            }
        });
        imv2.get(18).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainbit.equals(c19bit)) {

                    yesMethod(imv2.get(18));
                }
                else if (!mainbit.equals(c19bit)){
                    noMethod(imv2.get(18));
                    
                }
            }
        });
        imv2.get(19).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainbit.equals(c20bit)) {

                    yesMethod(imv2.get(19));
                }
                else if (!mainbit.equals(c20bit)){
                    noMethod(imv2.get(19));
                    
                }
            }
        });
        imv2.get(20).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainbit.equals(c21bit)) {

                    yesMethod(imv2.get(20));
                }
                else if (!mainbit.equals(c21bit)){
                    noMethod(imv2.get(20));
                    
                }
            }
        });
        imv2.get(21).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainbit.equals(c22bit)) {

                    yesMethod(imv2.get(21));
                }
                else if (!mainbit.equals(c22bit)){
                    noMethod(imv2.get(21));
                    
                }
            }
        });
        imv2.get(22).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainbit.equals(c23bit)) {

                    yesMethod(imv2.get(22));
                }
                else if (!mainbit.equals(c23bit)){
                    noMethod(imv2.get(22));
                    
                }
            }
        });
        imv2.get(23).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainbit.equals(c24bit)) {

                    yesMethod(imv2.get(23));
                }
                else if (!mainbit.equals(c24bit)){
                    noMethod(imv2.get(23));
                    
                }
            }
        });
        imv2.get(24).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainbit.equals(c25bit)) {

                    yesMethod(imv2.get(24));
                }
                else if (!mainbit.equals(c25bit)){
                    noMethod(imv2.get(24));
                    
                }
            }
        });
        imv2.get(25).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainbit.equals(c26bit)) {

                    yesMethod(imv2.get(25));
                }
                else if (!mainbit.equals(c26bit)){
                    noMethod(imv2.get(25));

                }
            }
        });
        imv2.get(26).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainbit.equals(c27bit)) {

                    yesMethod(imv2.get(26));
                }
                else if (!mainbit.equals(c27bit)){
                    noMethod(imv2.get(26));

                }
            }
        });
        imv2.get(27).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainbit.equals(c28bit)) {

                    yesMethod(imv2.get(27));
                }
                else if (!mainbit.equals(c28bit)){
                    noMethod(imv2.get(27));

                }
            }
        });
        imv2.get(28).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainbit.equals(c29bit)) {

                    yesMethod(imv2.get(28));
                }
                else if (!mainbit.equals(c29bit)){
                    noMethod(imv2.get(28));

                }
            }
        });
        imv2.get(29).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainbit.equals(c30bit)) {

                    yesMethod(imv2.get(29));
                }
                else if (!mainbit.equals(c30bit)){
                    noMethod(imv2.get(29));

                }
            }
        });
        countText = (TextView) findViewById(R.id.countText);
        final long sec = 0;

         startTime = System.currentTimeMillis();





        //타이머 카운트다운
        timer = new CountDownTimer(millisInFuture,100){

            @Override
            public void onTick(long millisUntilFinished) {

                String aa = String.valueOf(millisUntilFinished);
                countText.setText(aa.substring(0,1)+"."+ aa.substring(1,2));
                if (aa.length() <4)
                    countText.setText("0."+ aa.substring(0,1));

                int bar = (int) (millisUntilFinished/100);
                drogBar.setProgress(bar);
            }
            @Override
            public void onFinish() {
                countText.setText(String.valueOf("0.0"));
                drogBar.setProgress(0);
                if (sec == 0){
                    Intent Overintent = new Intent(getApplicationContext(), CardOverActivity.class);
                    T_score += score;
                    Overintent.putExtra("seveno.android.miniseconds.duckcard.cardover.cardTime",timeTakenMillis);
                    Overintent.putExtra("seveno.android.miniseconds.duckcard.cardover.tscore", T_score);
                    Overintent.putExtra("seveno.android.miniseconds.duckcard.cardover.elapsedTime",elapsedTime);
                    startActivity(Overintent);
                }
            }
        };
        timer.start();
    }



    private void yesMethod(ImageView yes) {
        score +=100;
        ok+=1;
        scoreText.setText(String.valueOf(score).toString());

        t = Toast.makeText(CardMainActivity.this,"yes",Toast.LENGTH_SHORT);
        int offsetX = 150;
        int offsetY = 0;
        t.setGravity(Gravity.CENTER, offsetX, offsetY);
        t.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                t.cancel();
            }
        },150);
        if (ok == 6){
            timer.cancel();
            double cotxt = fr -Double.parseDouble(String.valueOf(countText.getText()));
            //double cotxt = Math.round(((fr -Double.parseDouble(countText.getText().toString()))*10)/10);
            cotxt = Math.round(cotxt*100/100.0);
            //Math.round(67.655*100)/100.0);

            //String cot = String.format("%.1f", cotxt);

            String cot = String.valueOf(cotxt);
            countText.setText(cot);
            T_score += score;
             timeTakenMillis = System.currentTimeMillis() - startTime;
            elapsedTime += timeTakenMillis;
            Intent clearIntent = new Intent(getApplicationContext(), CardClearActivity.class);

            //clearIntent.putExtra("scoreA", scoreText.getText().toString());
            //clearIntent.putExtra("timerA", countText.getText().toString());
            clearIntent.putExtra("seveno.android.miniseconds.duckcard.cardclear.tscore", T_score);
            clearIntent.putExtra("seveno.android.miniseconds.duckcard.cardclear.timerA", cotxt);
            clearIntent.putExtra("seveno.android.miniseconds.duckcard.cardclear.cardTime",timeTakenMillis);
            clearIntent.putExtra("seveno.android.miniseconds.duckcard.cardclear.elapsedTime",elapsedTime);
            //clearIntent.putExtra("seveno.android.miniseconds.duckcard.cardclear.cardTime",timeTakenMillis);

          /* clearIntent.putExtra("seveno.android.miniseconds.duckcard.cardclear.cardTime",timeTakenMillis);
            clearIntent.putExtra("seveno.android.miniseconds.duckcard.cardclear.tscore", T_score);
            clearIntent.putExtra("seveno.android.miniseconds.duckcard.cardclear.elapsedTime",elapsedTime);*/
            startActivity(clearIntent);
        }
        yes.setColorFilter(R.color.colorPrimaryDark);
        //버튼기능 상실
        yes.setClickable(false);
    }

    private void noMethod(ImageView no) {
        t = Toast.makeText(CardMainActivity.this,"No",Toast.LENGTH_SHORT);
        int offsetX = 100;
        int offsetY = 100;
        t.setGravity(Gravity.CENTER, offsetX, offsetY);
        t.show();
        t.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                t.cancel();
            }
        },150);
        score -=50;
        scoreText.setText(String.valueOf(score).toString());
        no.setColorFilter(R.color.colorPrimaryDark);
        no.setClickable(false);
    }

    private BackPressCloseSystem backPressCloseSystem;
        // 뒤로 가기 버튼 이벤트

    @Override
    public void onBackPressed() {
        backPressCloseSystem.onBackPressed();
    }

}
