package seveno.android.miniseconds.SpeedNumGame;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import seveno.android.miniseconds.MainActivity;
import seveno.android.miniseconds.R;

/**
 * Created by Administrator on 2017-08-22.
 */

public class SpeedyController {

    private Handler countdownHandler;
    private Runnable countdownRunnable;

    private TextView countdownView;

    public boolean gameInSession;
    public boolean handlerStarted;
    protected CountDownTimer countdownTimer;
    private int countdown;

    Context context;
    private int view_id;

    public TextView getCountdownView() {
        return countdownView;
    }

    public void setCountdownView(TextView countdownView) {
        this.countdownView = countdownView;
    }

    public int getView_id() {
        return view_id;
    }

    public void setView_id(int view_id) {
        this.view_id = view_id;
    }

    //Egg Game Constructor
    public SpeedyController(Context context) {
        this.context = context;
        handlerStarted = false;
        gameInSession = false;
    }//end of constructor

    //starts the game
    protected void startGame() {

        //signals that the game started but no timers yet
        gameInSession = true;
        countdownView = getCountdownView();
        
        
        //initiate game variables


        startCountdown();


    }// end startGame

    //count down before eggs starts falling
    private void startCountdown() {

        //initialize the animation for flashing count down
        final Animation fadeOut = AnimationUtils.loadAnimation(context, R.anim.fadeout);

        countdown = 3;
        countdownHandler = new Handler();
        countdownRunnable = new Runnable(){

            @Override
            public void run() {
                if(countdown == 3 ){
                }
                if(countdown>0){
                   countdownView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 300);
                   countdownView.setText(String.valueOf(countdown));
                   countdownView.startAnimation(fadeOut);
                    countdownHandler.postDelayed(countdownRunnable, 1000);
                    countdown--;
                }else{
                    countdownView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 180);
                    countdownView.setText("Go!");
                    countdownView.startAnimation(fadeOut);
                    if(gameInSession){

                        countdownHandler.removeCallbacks(countdownRunnable);
                    }
                }
            }
        };

        countdownHandler.postDelayed(countdownRunnable, 300);
    }

}
