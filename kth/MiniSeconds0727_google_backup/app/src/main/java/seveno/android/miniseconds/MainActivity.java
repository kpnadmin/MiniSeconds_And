package seveno.android.miniseconds;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {
    private TextView title_txt1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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



}