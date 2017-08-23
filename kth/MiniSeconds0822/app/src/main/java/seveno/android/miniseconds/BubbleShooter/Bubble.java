package seveno.android.miniseconds.BubbleShooter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

import seveno.android.miniseconds.R;

/**
 * Created by Administrator on 2017-08-16.
 */

public class Bubble {
    public int x, y, radi;                      // 좌표, 반지름
    public Bitmap imgBall;                 // 비트맵 이미지
    public  boolean dead = false;        // 터뜨림 여부

    private int radi_;                             // 원래의 반지름
    private int sx, sy;                          // 이동 방향 및 속도
    private int width, height;                 // View의 크기
    private Bitmap Bubbles[] = new Bitmap[6]; // 풍선 애니메이션 용 이미지
    private int imgNum = 0;                  // 이미지 번호
    private int loop = 0;                       // 루프 카운터
    private int counter = 0;                   // 벽과 충돌 회수

    public Bubble() {
    }

    public Bubble(Context context, int x_, int y_, int width_, int height_) {
        this.x = x_;
        this.y = y_;
        this.width = width_;
        this.height = height_;


        imgBall = BitmapFactory.decodeResource(context.getResources(), R.drawable.bubble_1);
        Random rand = new Random();
        radi_ = rand.nextInt(11) + 30;
        radi = radi_;
        Random rand2 = new Random();
        int speedrand = rand.nextInt(5) +4;

        // 반지름이 2 간격으로 커졌자 작아지는 공 6개 만들기
        for (int i = 0 ; i <=3 ; i++) {
            Bubbles[i] = Bitmap.createScaledBitmap(imgBall, radi_ * 2 + i * 2, radi_ * 2 + i * 2, false);
            Bubbles[4] = Bubbles[2];
            Bubbles[5] = Bubbles[1];
            imgBall = Bubbles[0];

            sx = rand.nextInt(2) == 0 ? -1 * speedrand : 1 * speedrand;
            sy = rand.nextInt(2) == 0 ? -2 * speedrand : 2 * speedrand;
            MoveBubble();
        }
    }
    //-----------------------------------
    //  Move
    //-----------------------------------
    public void MoveBubble() {
        loop++;
        if (loop % 3 == 0) {
            imgNum++;                               // 비눗방울 번호
            if (imgNum > 5) imgNum = 0;
            imgBall = Bubbles[imgNum];
            // 비눗방울의 반지름 설정
            radi = radi_ + (imgNum <= 3 ? imgNum : 6 - imgNum) * 2 ;
        }
        x += sx;
        y += sy;
        if(x <= radi || x >= width - radi){
            sx = -sx;
            x += sx;
            counter ++;
        }
        if( y <= radi || y >= height - radi){
            sy = -sy;
            y += sy;
            counter ++;
        }
        if (counter >= 3) dead = true;  // 벽과 충돌 횟수
    }

    }
