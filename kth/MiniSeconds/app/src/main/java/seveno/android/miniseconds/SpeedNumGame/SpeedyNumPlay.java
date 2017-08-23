package seveno.android.miniseconds.SpeedNumGame;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Random;

import seveno.android.miniseconds.BubbleShooter.BubbleGame;
import seveno.android.miniseconds.GameOver;
import seveno.android.miniseconds.R;

public class SpeedyNumPlay extends AppCompatActivity  {

    private static final int ERROR_PENALTY_SECONDS = 5;
    private static int t_end_num =0;   // 1이라면 반복문 빠져나감
    private static Sequence sequence;
    private static long startTime;
    private static long timeTakenMillis;
    private static long elapsedTime =0;
    private static boolean timerRunning;
    private static TextView timerTextView;
    private TextView txtView;
    private static TextView errorsMadeTextView;
    private static int numErrors = 0;
    private static Handler h2 = new Handler();
    private static Runnable run;
    private TextView txt_speedy_time;
    private TextView txt_speedy_score;
    private static Thread t1;
    private Integer[] speedyNumBtn;
    private ProgressBar bar_speedyNum;
    Handler handler_progress = new Handler();
    private int end_speedyNum_bar = 100;
    private int gameType =0;
    private Intent speedyIntent;
    private TextView txt_speedyError;
    private int speedy_score = 100;
    private MediaPlayer mp;
    private SpeedyController controller;
    private TextView countdown_view;
    private GridLayout gridLayout1;
    private boolean Start_Ok;
    Handler sHandler = new Handler();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speedy_num_play);

        txt_speedy_time = (TextView) findViewById(R.id.txt_speedy_time);
        txt_speedy_score = (TextView) findViewById(R.id.txt_speedy_score);
        txt_speedyError = (TextView) findViewById(R.id.txt_speedyError);
        bar_speedyNum = (ProgressBar) findViewById(R.id.bar_speedyNum);
        countdown_view = (TextView) findViewById(R.id.countdown_view);
        gridLayout1 = (GridLayout) findViewById(R.id.gridLayout1);

        speedyNumBtn = new Integer[9];
        Random rand2 = new Random();

        gameType = rand2.nextInt(4);

        if (savedInstanceState == null) {//On first startup, creates the sequence, begins the timer and does some cleanup work.
            //sequence = new Sequence(getIntent().getIntExtra("seveno.android.miniseconds.speednumgame.currentGameType", 0));
            speedyIntent = getIntent();
            int gameT = speedyIntent.getIntExtra("seveno.android.miniseconds.speednumgame.currentGameType", 0);
            sequence = new Sequence(gameType);

            numErrors = 0;

            bar_speedyNum.setProgress(bar_speedyNum.getMax());
            //첫 시작한 현재시간
            final long start = System.currentTimeMillis();
            //시간포맷팅을 위한 포맷설정
            final SimpleDateFormat sdf = new SimpleDateFormat("mm:ss:SSS");

            addSequenceToButtons();

            gridLayout1.setVisibility(View.GONE);
            controller = new SpeedyController(this);
            controller.setCountdownView(countdown_view);
            controller.startGame();


// 3초 지연후 실행
            sHandler.postDelayed(new Runnable()  {
                public void run() {
                    //#명령어
                    t1.start();
                    startTime = System.currentTimeMillis();
                    run = new Runnable() {
                        @Override
                        public void run() {
                            timeTakenMillis = System.currentTimeMillis() - startTime;
                            txt_speedy_time.setText("Time: "+(convertToMinutesAndSeconds(timeTakenMillis)));
                            h2.postDelayed(this, 500);
                        }
                    };
                    gridLayout1.setVisibility(View.VISIBLE);
                    timerRunning = true;
                    if(timerRunning){
                        h2.postDelayed(run, 0);
                    } else {
                        txt_speedy_time.setText(convertToMinutesAndSeconds(timeTakenMillis));

                    }
                    sHandler.removeMessages(0);
                }
            }, 3000);



       //
            handler_progress = new Handler();
            t1 = new Thread(new Runnable() {
                @Override
                public void run() { // Thread 로 작업할 내용을 구현
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for(int i =  bar_speedyNum.getProgress(); i >= 0; i=i-1){
                        if(t_end_num ==1) {break;};
                        handler_progress.post(new Runnable() {
                            @Override
                            public void run() {
                                bar_speedyNum.setProgress(bar_speedyNum.getProgress()-1);

                            }
                        });
                        //1초동안 멈춤
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //SystemClock.sleep(100);
                        end_speedyNum_bar = bar_speedyNum.getProgress();
                        if(end_speedyNum_bar == 0){
                            Game_Over(end_speedyNum_bar);
                        }
                    }
                }
            });

             // 쓰레드 시작

        }//onCreate



       // setupActionBar();
        //addSequenceToButtons();

    }
    public void Game_Over(int end_speedyNum_bar){
        h2.removeCallbacks(run);
        timerRunning = false;
        t1.interrupt();
        Intent intent = new Intent(this, GameOver.class);
        intent.putExtra("seveno.android.miniseconds.speednumgame.initialTime",timeTakenMillis);
        intent.putExtra("seveno.android.miniseconds.speednumgame.numErrors",numErrors);
        startActivity(intent);
        finish();
    }


    //버튼을 클릭 한 후 올바른지 확인. 올바른 경우 버튼을 누르지 않고 반투명하게
    //모든 버튼이 바르게 눌려 졌는지 확인. 그완료 화면을 시작
    public void btn_SpeedyClick(View v) throws InterruptedException{
        if(sequence.isCorrect(Integer.parseInt((String)v.getTag()))){
            ((Button)v).setAlpha((float)0.2);
            ((Button)v).setClickable(false);
            speedy_score += 200;
            txt_speedy_score.setText(String.valueOf(speedy_score));
            if(sequence.allCorrect()){
                t_end_num = 1;
                timerRunning = false;
                if(mp != null){
                    isPlaying();
                }else{
                    mp = MediaPlayer.create(SpeedyNumPlay.this, R.raw.s_correct_answer);
                    isPlaying();
                }
                long finalTime = timeTakenMillis + (numErrors*ERROR_PENALTY_SECONDS*1000);
                t1.interrupt();
                h2.removeCallbacks(run);
                 Intent intent = new Intent(this, FinishScreen.class);
                //Intent intent = new Intent(this, BubbleGame.class);
                intent.putExtra("seveno.android.miniseconds.speednumgame.takenspeedyTime",timeTakenMillis);
                intent.putExtra("seveno.android.miniseconds.speednumgame.numErrors",numErrors);
                intent.putExtra("seveno.android.miniseconds.speednumgame.speedy_score",speedy_score);
                intent.putExtra("seveno.android.miniseconds.speednumgame.elapsedTime",elapsedTime);
              /*  intent.putExtra("game.speed.android.speed_number_game.numErrors",numErrors);
                intent.putExtra("game.speed.android.speed_number_game.position",highScorePosition);*/

                startActivity(intent);
                finish();
            }
        } else {
            numErrors++;
            txt_speedyError.setText("Errors: "+numErrors);
            speedy_score -= 100;
            txt_speedy_score.setText(String.valueOf(speedy_score));
        }
    }



    private void setupActionBar(){
        ActionBar actionBar = getActionBar();
        timerTextView = new TextView(this);
        timerTextView.setTextColor(Color.BLACK);
        LinearLayout.LayoutParams timerTextViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
        timerTextViewParams.weight = 1;
        timerTextViewParams.gravity = Gravity.RIGHT|Gravity.CENTER_VERTICAL;
        LinearLayout actionBarLayout = new LinearLayout(this);
        actionBarLayout.addView(timerTextView,timerTextViewParams);
        ActionBar.LayoutParams actionBarLayoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        actionBarLayout.setLayoutParams(actionBarLayoutParams);
        actionBar.setCustomView(actionBarLayout);
        actionBar.setDisplayShowCustomEnabled(true);

    }

    public String convertToMinutesAndSeconds(long toConvert){
        int seconds = (int) (toConvert / 1000);
        int minutes = seconds / 60;
        seconds     = seconds % 60;
        String minutesAndSeconds = String.format(Locale.ENGLISH,"%d:%02d", minutes, seconds);
        return minutesAndSeconds;
    }

    private void addSequenceToButtons(){
        for(int i = 1; i<=9 ; i++){
         String buttonName ="SpeedyBtn_" + i ;
            Button currentButton = (Button) findViewById(getResources().getIdentifier(buttonName, "id",this.getPackageName()));
          int buttonSequenceNumber = sequence.getIntegerAt(i-1);
            currentButton.setText(""+buttonSequenceNumber);
            currentButton.setTag(""+buttonSequenceNumber);
            if(sequence.isDone(sequence.getIntegerAt(i-1))){
                currentButton.setAlpha((float)0.2);
                currentButton.setClickable(false);
            }
        }
    }



private  void isPlaying(){
    if(!mp.isPlaying()){
        mp.start();
    }else{
        mp.stop();
        mp.release();
        mp = null;
    }
}

}
  /* t = new Thread(new Runnable() {
                public void run() {
                    //첫 시작한 현재시간
                    final long start = System.currentTimeMillis();
                    //시간포맷팅을 위한 포맷설정
                    final SimpleDateFormat sdf = new SimpleDateFormat("mm:ss:SSS");

                    while (!(t.isInterrupted())) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                //쓰레드가 돌때마다 계속 현재시간 갱신
                                long end = System.currentTimeMillis();
                                //진행된시간을 계산하여 포맷에 맞게 TextView에 뿌리기
                                txt_speedy_time.setText(sdf.format(end - start).substring(0, 8));
                            }
                        });
                        //0.01초마다 Thread돌리기
                        SystemClock.sleep(10);
                    }
                }
            });
            t.start();*/