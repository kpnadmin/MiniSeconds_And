package seveno.android.miniseconds.BubbleShooter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import seveno.android.miniseconds.R;

/**
 * Created by Administrator on 2017-08-16.
 */

public class BubbleGameView extends SurfaceView implements SurfaceHolder.Callback {
    public GameThread mThread;
    SurfaceHolder mHolder;
    public int BubbleScore = 0;

    public int getBubbleScore() {
        return BubbleScore;
    }

    public void setBubbleScore(int bubbleScore) {
        BubbleScore = bubbleScore;
    }

    public BubbleGameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        mHolder = holder;
        mThread = new GameThread(holder, context);
        setFocusable(true);  // View가 포커스를 받을 수 있도록 설정
    }

    public BubbleGameView(Context context) {
        super(context);
    }

    public BubbleGameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //-------------------------------------
    //  SurfaceView가 생성될 때 실행되는 부분
    //-------------------------------------
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mThread.setRunning(true);
        mThread.setDaemon(true);
        setFocusable(true);
        mThread.start();
      // MakeBubble();
    }

    //-------------------------------------
    //  SurfaceView가 바뀔 때 실행되는 부분
    //-------------------------------------
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    //-------------------------------------
    //  SurfaceView가 해제될 때 실행되는 부분
    //-------------------------------------
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean done = true;
        mThread.setRunning(false);
        while (done) {
            try {
                mThread.join();                        // 스레드가 현재 step을 끝낼 때 까지 대기
                done = false;
            } catch (InterruptedException e) {  // 인터럽트 신호가 오면?
                // 그 신호 무시 - 아무것도 않음
            }
        } // while
        setFocusable(false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // return super.onTouchEvent(event);
        BubbleScore = 0;
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            synchronized (mHolder) {
                int x = (int) event.getX();
                int y = (int) event.getY();
                mThread.TouchBubble(x, y);
                // mThread.MakeBubble(x, y);
                BubbleScore += 100;
                setBubbleScore(BubbleScore);
            }
        } else {
            return false;
        }
        return true;

    }
 public void MakeBubble() {
        synchronized (mHolder) {
            Random rnd1 = new Random();
            int x = rnd1.nextInt(mThread.width); //화면의 폭 안의 랜덤한 x지점
            int y = rnd1.nextInt(mThread.height); //화면의 높이 안의 랜덤한 y지점
            boolean flag = false;
            if (flag == false)                              // 비눗방울 Touch가 아니면 비눗방울 생성
                mThread.mBubble.add(new Bubble(mThread.mContext, x, y, mThread.width, mThread.height));
        }

    }

}


//-------------------------------------
//  GameThread Class
//-------------------------------------
  /*  class GameThread extends Thread {
        SurfaceHolder mHolder;                                    // SurfaceHolder를 저장할 변수
        Context mContext;
        Boolean bubbleRun =  false;

        public void setRunning(boolean b) {
            bubbleRun = b;
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

            imgBack = BitmapFactory.decodeResource(getResources(), R.drawable.bubble_sky);
            imgBack = Bitmap.createScaledBitmap(imgBack, width, height, false);


        }

        public GameThread(Handler mHandler) {
            this.mHandler = mHandler;
        }

       //-------------------------------------
         //  비눗방울 만들기  - Touch Event에서 호출
         //-------------------------------------
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
            BubbleScore = 0 ;
            for (Bubble tmp :  mBubble) {
                if (Math.pow(tmp.x - x, 2) + Math.pow(tmp.y - y, 2)  <= Math.pow(tmp.radi, 2)) {
                    tmp.dead = true;                   // 비눗방울 Touch일 경우
                    flag = true;
                    BubbleScore += 100 ;
                    //int bubScore = 100;

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
            while (bubbleRun) {
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

    }*/

