package seveno.android.miniseconds;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;


public class GameOver extends AppCompatActivity {

    private Button btn_Gameover_return;
    Intent intent1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        intent1 = getIntent();
        btn_Gameover_return = (Button) findViewById(R.id.btn_Gameover_return);



        btn_Gameover_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                /* intent.putExtra("game.speed.android.speed_number_game.numErrors",numErrors);
                intent.putExtra("game.speed.android.speed_number_game.position",highScorePosition);*/
                startActivity(intent);
                finish();

            }
        });
    }



}
