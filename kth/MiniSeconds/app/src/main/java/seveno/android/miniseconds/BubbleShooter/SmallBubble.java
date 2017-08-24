package seveno.android.miniseconds.BubbleShooter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

import seveno.android.miniseconds.R;

/**
 * Created by Administrator on 2017-08-16.
 */

public class SmallBubble {

    public int x, y, radi;                            // 좌표, 반지름
    public  boolean dead = false;            // 터뜨림 여부
    public Bitmap imgBall;                       // 비트맵 이미지

    private int width, height;                  // View의 크기
    private int cx, cy;                           // 원의 중심점
    private int cr;                                  // 원의 반지름
    private double r;                             // 이동 각도 (radian)
    private int speed;                            // 이동 속도
    private int num;                               // 이미지 번호
    private int life;                                 // 생명 주기

    //-------------------------------------
    //  생성자
    //-------------------------------------
    public SmallBubble(Context context, int x_, int y_, int ang, int width_, int height_) {
        cx = x_;                            // 중심점
        cy = y_;
        width = width_;
        height = height_;
        r = ang * Math.PI / 180;       // 각도 radian

        Random rand = new Random();
        speed = rand.nextInt(5) + 4;            // 속도     : 4~10
        radi = rand.nextInt(6) + 2;                 // 반지름   : 2~7
        num = rand.nextInt(6);                    // 이미지  : 0~5
        life = rand.nextInt(31) + 20; // 20~50

        imgBall = BitmapFactory.decodeResource(context.getResources(), R.drawable.bubble_b0+ num);
        imgBall = Bitmap.createScaledBitmap(imgBall, radi * 2, radi * 2, false);
        cr = 10;      // 원의 초기 반지름
        MoveSBubble();
    }
    //-------------------------------------
    //  MoveBall
    //-------------------------------------
    public void MoveSBubble() {
        life--;
        cr += speed;
        x = (int) (cx + Math.cos(r) * cr);//Math.sin은 각도를 반영해 x축의 좌표를 담당하고,
        y = (int) (cy - Math.sin(r) * cr);//Math.cos은 각도를 반영해 y축의 좌표를 담당합니다.
        if (x < -radi || x > width + radi ||
                y < -radi || y > height + radi || life <= 0)
            dead = true;
    }

}
