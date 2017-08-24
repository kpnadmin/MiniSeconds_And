package seveno.android.miniseconds.BubbleShooter;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import seveno.android.miniseconds.GameEnding;
import seveno.android.miniseconds.R;
import seveno.android.miniseconds.SpeedNumGame.SpeedyController;

public class BubbleGame extends AppCompatActivity {

    private LinearLayout linear_1, linear_text;
    private TextView txt_Bubble_score;
    private ProgressBar bar_BubbleGame;
    private BubbleGameView mBubbleGameView;
    private GameThread threadExGame;
    private int BubbleScore;
    private int T_score;
    private Boolean TouchOnOff;
    private FrameLayout frame1;
    private static long startTime;
    private static long timeTakenMillis;
    private static long elapsedTime;
    private Boolean BubbleTurn = false;
    private GameThread mExGame;
    private TextView countdown_view;
    private BubbleController controller;
    Handler bHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bubble_game);

        frame1 = (FrameLayout) findViewById(R.id.frame1);
        bar_BubbleGame = (ProgressBar) findViewById(R.id.bar_BubbleGame);
        linear_1 = (LinearLayout) findViewById(R.id.linear_1);
        linear_text = (LinearLayout) findViewById(R.id.linear_text);
        txt_Bubble_score = (TextView) findViewById(R.id.txt_Bubble_score);
        mBubbleGameView = (BubbleGameView) findViewById(R.id.mBubbleGameView);
        countdown_view = (TextView) findViewById(R.id.countdown_view_b);


        if (savedInstanceState == null) {//On first startup, creates the sequence, begins the timer and does some cleanup work.

            linear_1.bringToFront();
            linear_text.bringToFront();
            countdown_view.bringToFront();
            mBubbleGameView.setVisibility(View.GONE);
            Intent intent = getIntent();

            long speedyTime = intent.getLongExtra("seveno.android.miniseconds.BubbleShooter.BubbleGame.initialTime", 0);
            int speedy_score = intent.getIntExtra("seveno.android.miniseconds.BubbleShooter.BubbleGame.tscore", T_score);
            elapsedTime = intent.getLongExtra("seveno.android.miniseconds.BubbleShooter.BubbleGame.elapsedTime", elapsedTime);
            T_score = speedy_score;
            elapsedTime += speedyTime;


            controller = new BubbleController(this);
            controller.setCountdownView(countdown_view);
            controller.startGame();

            //프로그래스바
            bar_BubbleGame.setProgress(bar_BubbleGame.getMax());
            txt_Bubble_score.setText("Score : " + T_score);
            txt_Bubble_score.setTextSize(20);


            bHandler.postDelayed(new Runnable()  {
                public void run() {
                    //#명령어
                    mBubbleGameView.setVisibility(View.VISIBLE);
                    startTime = System.currentTimeMillis();
                    // asyncTask 실행
                    new BubbleProgressTask().execute(bar_BubbleGame.getProgress());
                    bHandler.removeMessages(0);
                }
            }, 4000);







        }
    }

    class BubbleProgressTask extends AsyncTask<Integer, Integer, Boolean> {
        private boolean isPerformed = false;
        private boolean isCancelled = false;
        boolean bubbleRun = true;

        @Override
        protected Boolean doInBackground(Integer... params) {

            for (int i = params[0]; i >= 0; i--) {
                publishProgress(i);
                SystemClock.sleep(100);
            }
            if (isCancelled) {
                return isCancelled;
            }
            timeTakenMillis = System.currentTimeMillis() - startTime;
            //ViewCompleted(mBubbleGameView);
            //BubbleGameView.GameThread.interrupted()

            return isPerformed;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            bar_BubbleGame.setProgress(values[0]);
            if (bar_BubbleGame.getProgress() > 0) {
                BubbleScore = mBubbleGameView.getBubbleScore();
                if (BubbleScore != 0) {
                    T_score += BubbleScore;
                    updateScore();
                }
            } else if (bar_BubbleGame.getProgress() == 0) {
                isPerformed = true;
            }
        }

        @Override
        protected void onCancelled(Boolean aBoolean) {
            super.onCancelled(aBoolean);
        }

        @Override
        protected void onPostExecute(Boolean performed) {
            super.onPostExecute(performed);
            if (performed) {
                 mBubbleGameView.setVisibility(View.GONE);
                // SystemClock.sleep(2000);
                PlayNextGame();
            }
        }
    }

    private void updateScore() {
        txt_Bubble_score.setText("Score : " + T_score);
    }

    private void PlayNextGame() {
        //t2.interrupt();
        Intent intent = new Intent(this, FinishScreenBubble.class);
        intent.putExtra("seveno.android.miniseconds.bubbleshooter.bubbleGame.bubbleTime", timeTakenMillis);
        intent.putExtra("seveno.android.miniseconds.bubbleshooter.bubbleGame.tscore2", T_score);
        intent.putExtra("seveno.android.miniseconds.BubbleShooter.BubbleGame.elapsedTime", elapsedTime);

        startActivity(intent);
        finish();
    }
}

// 스레드 생성하고 시작
        /*GameThread g_thread = new GameThread(handler);
        g_thread.setDaemon(true);
        g_thread.start();*/
//txt_Bubble_score.setText(String.valueOf(T_score));
/*
  private void ViewCompleted(View view){
        try {
            view.setVisibility(View.GONE);
          GameThread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
*
* */


    /*Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 0){   // Message id 가 0 이면
                //backValue = msg.getData().getInt("backValue");
               *//* BubbleScore = msg.arg1;
                T_score += BubbleScore;
                txt_Bubble_score.setText("Score : " + T_score);*//*

                mExGame.setRunning(false);
                //backText.setText("BackValue:" + backValue); // 메인스레드의 UI 내용 변경
            }
        }
    };*/

//intent.putExtra("game.speed.android.speed_number_game.numErrors",numErrors);
//intent.putExtra("game.speed.android.speed_number_game.position",highScorePosition);
/*
*
  Intent  intent = getIntent();
        //long initialTime =  intent.getLongExtra("seveno.android.miniseconds.BubbleShooter.BubbleGame.initialTime",0);
        //int speedy_score =  intent.getIntExtra("seveno.android.miniseconds.BubbleShooter.BubbleGame.tscore",T_score);

        bar_BubbleGame = (ProgressBar) findViewById(R.id.bar_BubbleGame);
        linear_1 = (LinearLayout) findViewById(R.id.linear_1);
       // linear_text = (LinearLayout) findViewById(R.id.linear_text);
        //txt_Bubble_score = (TextView) findViewById(R.id.txt_Bubble_score);

        //프로그래스바
        bar_BubbleGame.setProgress(bar_BubbleGame.getMax());
        linear_1.bringToFront();

       // T_score = speedy_score;

       long initialTime = getIntent().getLongExtra("seveno.android.miniseconds.speednumgame.initialTime",0);
        int numErrors = getIntent().getIntExtra("seveno.android.miniseconds.speednumgame.numErrors",0);
        int speedy_score = getIntent().getIntExtra("seveno.android.miniseconds.speednumgame.speedy_score", 0);

//linear_text.bringToFront();


// txt_Bubble_score.setText(String.valueOf(T_score));
// txt_Bubble_score.setTextSize(20);

        //mBubbleGameView = (BubbleGameView) findViewById(R.id.mBubbleGameView);


*/
 /* @Override
    protected void onStop() {
        super.onStop();
        if( threadExGame != null && threadExGame.isAlive()){
            threadExGame.interrupt();
        }

    }*/

    /**/
    /*
    *  BubbleScore = mBubbleGameView.getBubbleScore();

            if(BubbleScore != 0){
                T_score += BubbleScore;
            updateScore();
            }
    *
    * */
     /* long initialTime =  intent.getLongExtra("seveno.android.miniseconds.speednumgame.initialTime",0);
        int speedy_score =  intent.getIntExtra("seveno.android.miniseconds.speednumgame.speedy_score",T_score);*/

//intent.putExtra("seveno.android.miniseconds.speednumgame.initialTime",timeTakenMillis);
//intent.putExtra("seveno.android.miniseconds.speednumgame.numErrors",numErrors);
//intent.putExtra("seveno.android.miniseconds.speednumgame.speedy_score",speedy_score);