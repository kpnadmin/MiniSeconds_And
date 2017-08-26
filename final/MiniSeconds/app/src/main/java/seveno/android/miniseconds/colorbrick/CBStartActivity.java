package seveno.android.miniseconds.colorbrick;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import seveno.android.miniseconds.R;
import seveno.android.miniseconds.duckcard.BackPressCloseSystem;

public class CBStartActivity extends AppCompatActivity {

    CountDownTimer threeTimer;
    TextView countThree;
    LinearLayout startLinear;
    private static long initialTime;
    private static int cb_score;
    private static long elapsedTime;
    private int T_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b_activity_startcb);

        backPressCloseSystem = new BackPressCloseSystem(this);

        final Button startBtn = (Button) findViewById(R.id.startBtn);
        countThree = (TextView) findViewById(R.id.countThree);
        startLinear = (LinearLayout) findViewById(R.id.startLinear);
        final int millisInFuture = 3100;
        /*

        *
        * */
        Intent intent = getIntent();

        initialTime = intent.getLongExtra("seveno.android.miniseconds.colorbrick.cbstart.initialTime",0);
        cb_score = intent.getIntExtra("seveno.android.miniseconds.colorbrick.cbstart.tscore", 0);
        elapsedTime = intent.getLongExtra("seveno.android.miniseconds.colorbrick.cbstart.elapsedTime",0);
        T_score = cb_score;

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startLinear.setVisibility(View.INVISIBLE);
                countThree.setVisibility(View.VISIBLE);
                threeTimer = new CountDownTimer(millisInFuture,1000){

                    @Override
                    public void onTick(long millisUntilFinished) {

                        String aa = String.valueOf(millisUntilFinished);

                        if      (aa.length() == 4)
                            countThree.setText(aa.substring(0,1));
                        else if (aa.length() == 3)
                            countThree.setText("시작!");

                    }
                    @Override
                    public void onFinish() {
                        countThree.setText(String.valueOf("시작!"));
                        Intent intent2 = new Intent(CBStartActivity.this, CBMainActivity.class);
                        intent2.putExtra("seveno.android.miniseconds.colorbrick.cbmain.initialTime",initialTime);
                        intent2.putExtra("seveno.android.miniseconds.colorbrick.cbmain.tscore",elapsedTime);
                        intent2.putExtra("seveno.android.miniseconds.colorbrick.cbmain.elapsedTime",T_score);
                        startActivity(intent2);
                        overridePendingTransition(android.R.anim.fade_in,
                                android.R.anim.fade_out);
                    }
                };
                threeTimer.start();
            }
        });
    }

    private BackPressCloseSystem backPressCloseSystem;

    @Override
    public void onBackPressed() {
        backPressCloseSystem.onBackPressed();
    }

}
