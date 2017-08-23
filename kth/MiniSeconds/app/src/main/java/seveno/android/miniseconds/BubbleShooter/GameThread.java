package seveno.android.miniseconds.BubbleShooter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Random;

import seveno.android.miniseconds.R;

/**
 * Created by Administrator on 2017-08-16.
 */

public class GameThread extends Thread {

    //-------------------------------------
    //  GameThread Class
    //-------------------------------------

        SurfaceHolder mHolder;                                    // SurfaceHolder를 저장할 변수
        Context mContext;
        Boolean bubbleRun =  false;
    public int BubbleScore;

    public int getBubbleScore() {
        return BubbleScore;
    }

    public void setBubbleScore(int bubbleScore) {
        BubbleScore = bubbleScore;
    }

    public void setRunning(boolean b) {
            bubbleRun = b;
        }

    public Boolean getBubbleRun() {
        return bubbleRun;
    }

    int width, height;
        Bitmap imgBack;
        ArrayList<Bubble> mBubble = new ArrayList<Bubble>();              // 큰방울
        ArrayList<SmallBubble> sBubble = new ArrayList<SmallBubble>();         // 작은방울
        private Handler mHandler;


        public GameThread(SurfaceHolder holder, Context context) {
            mHolder = holder;
            mContext = context;

            Display display = ((WindowManager) context.getSystemService
                    (Context.WINDOW_SERVICE)).getDefaultDisplay();

            width = display.getWidth();              // View의 가로 폭
            height = display.getHeight() - 50;     // View의 세로 높이

            imgBack = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.bubble_sky);
            imgBack = Bitmap.createScaledBitmap(imgBack, width, height, false);


        }

        public GameThread(Handler mHandler) {
            this.mHandler = mHandler;
        }


        //-------------------------------------
        //  비눗방울 만들기  - Touch Event에서 호출
        //-------------------------------------
       public void MakeBubble(int x, int y) {

            boolean flag = false;
            for (Bubble tmp :  mBubble) {
                if (Math.pow(tmp.x - x, 2) + Math.pow(tmp.y - y, 2)  <= Math.pow(tmp.radi, 2)) {
                    tmp.dead = true;                   // 비눗방울 Touch일 경우
                    flag = true;
                }
            }
            if (flag == false)                              // 비눗방울 Touch가 아니면 비눗방울 생성
                mBubble.add(new Bubble(mContext, x, y, width, height));
        }



         //-------------------------------------
         //  비눗방울 만들기  - Touch Event에서 호출
         //-------------------------------------*/
        public void MakeBubble() {
            synchronized (mHolder) {
                Random rnd1 = new Random();
                int x = rnd1.nextInt(width); //화면의 폭 안의 랜덤한 x지점
                int y = rnd1.nextInt(height); //화면의 높이 안의 랜덤한 y지점
                boolean flag = false;
                if (flag == false)                              // 비눗방울 Touch가 아니면 비눗방울 생성
                    mBubble.add(new Bubble(mContext, x, y, width, height));
            }
        }
        //비눗방울 터치
        public void TouchBubble(int x, int y) {
            boolean flag = false;
            //BubbleScore = 0 ;
            for (Bubble tmp :  mBubble) {
                if (Math.pow(tmp.x - x, 2) + Math.pow(tmp.y - y, 2)  <= Math.pow(tmp.radi, 2)) {
                    tmp.dead = true;                   // 비눗방울 Touch일 경우
                    flag = true;
                   // BubbleScore = 100 ;
                    //int bubScore = 100;
                   // setBubbleScore(BubbleScore);

                }else{
                    flag = false;

                }

            }
        }

        //-------------------------------------
        //  작은  비눗방울 만들기
        //-------------------------------------
        private void MakeSmallBall(int x, int y) {
            Random rand = new Random();
            int count = rand.nextInt(9) + 7;               // 7~15개
            for (int i = 1; i <= count; i++) {
                int ang = rand.nextInt(360);
                sBubble.add(new SmallBubble(mContext, x, y, ang, width, height));
            }
        }
        //-------------------------------------
        //  비눗방울 이동  - run에서 호출
        //-------------------------------------
        public void MoveBubble() {
            // 큰 비눗방울 이동
            for (int i = mBubble.size() - 1; i >= 0; i--) {
                mBubble.get(i).MoveBubble();
                if (mBubble.get(i).dead == true) {
                    // 작은 비눗방울을 만들고 큰 것은 터뜨림
                    MakeSmallBall(mBubble.get(i).x, mBubble.get(i).y); // 작은 방울
                    mBubble.remove(i);


                }
            }
            // 작은 비눗방울 이동
            for (int i = sBubble.size() - 1; i >= 0; i--) {
                sBubble.get(i).MoveSBubble();
                if (sBubble.get(i).dead == true)
                    sBubble.remove(i);
            }
        }
        //-------------------------------------
        //  Canvas에 그리기
        //-------------------------------------
        public void run() {
            // while (!Thread.currentThread().isInterrupted()) {
            //bubbleRun = getBubbleRun();
            while (bubbleRun) {
                bubbleRun = getBubbleRun();
                Canvas canvas = null;      // canvas를 만든다
                canvas = mHolder.lockCanvas();  // canvas를 잠그고 버퍼 할당
                boolean BubbleTurn  = true;
                try {
                    synchronized (mHolder) {  // 동기화 유지
                    if(BubbleTurn) {
                            MakeBubble();
                        }
                        BubbleTurn = !BubbleTurn;
                        MoveBubble();
                        canvas.drawBitmap(imgBack, 0, 0, null);
                        // 큰 비눗방울
                        for (Bubble tmp : mBubble) {
                            canvas.drawBitmap(tmp.imgBall, tmp.x - tmp.radi,  tmp.y - tmp.radi, null);
                        }
                        // 작은비눗방울
                        for (SmallBubble tmp : sBubble) {
                            canvas.drawBitmap(tmp.imgBall, tmp.x - tmp.radi,  tmp.y - tmp.radi, null);
                        }
                    } // sync
                } finally {       // 버퍼 작업이 끝나면
                    if (canvas != null)    // 버퍼의 내용을 View에 전송
                        mHolder.unlockCanvasAndPost(canvas);
                }
            } // while
        } // run

    }

     /* public void MakeBubble(int x, int y) {
        boolean flag = false;
        for (Bubble tmp :  mBubble) {
            if (Math.pow(tmp.x - x, 2) + Math.pow(tmp.y - y, 2)  <= Math.pow(tmp.radi, 2)) {
                tmp.dead = true;                   // 비눗방울 Touch일 경우
                flag = true;
            }
        }
        if (flag == false)                              // 비눗방울 Touch가 아니면 비눗방울 생성
            mBubble.add(new Bubble(mContext, x, y, width, height));
    }
*/
   /*
                Random rnd1 = new Random();
             x = rnd1.nextInt(width); //화면의 폭 안의 랜덤한 x지점
             y = rnd1.nextInt(height); //화면의 높이 안의 랜덤한 y지점
        * */