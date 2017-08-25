package seveno.android.miniseconds.duckcard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import seveno.android.miniseconds.R;

public class CardOverActivity extends AppCompatActivity {

    Button mainBtn, reBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_overcard);

        backPressCloseSystem = new BackPressCloseSystem(this);

        mainBtn = (Button) findViewById(R.id.mainBtn);
        reBtn = (Button) findViewById(R.id.reBtn);

        reBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), CardStartActivity.class);
                startActivity(intent);
            }
        });
        mainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainintent = new Intent(getApplicationContext(), CardStartActivity.class);
                startActivity(mainintent);
            }
        });
    }

    private BackPressCloseSystem backPressCloseSystem;

    @Override
    public void onBackPressed() {
        backPressCloseSystem.onBackPressed();
    }
}
