package framework;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import framework.sql.SQLManager;
import framework.state.IntroState;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private GameViewThread m_thread;

    public GameView(Context context){
        super(context);
        setFocusable(true);
        SQLManager.getDB_instance().setDB_ite(context);//DB생성
        AppManager.getS_instance().setGameView(this);//겜뷰 생성
        AppManager.getS_instance().setResouces(getResources());
        AppManager.getS_instance().setState(new IntroState());//처음화면은 IntroState
        getHolder().addCallback(this);
        m_thread = new GameViewThread(getHolder(), this);//thread 생성
    }

    public void draw(Canvas canvas){
        super.draw(canvas);
        canvas.drawColor(Color.BLACK);
        AppManager.getS_instance().getState().Render(canvas);
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }
    public void surfaceCreated(SurfaceHolder arg0){
        m_thread.setRunning(true);
        m_thread.start();
    }
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        m_thread.setRunning(false);
        while(retry){
            try{
                //스레드 스탑!
                m_thread.join();
                retry = false;
            }catch (InterruptedException e){
                //스레드가 종료되도록 계속 시도합니다.
            }
        }
    }
    public void Update(){
        AppManager.getS_instance().getState().Update();
    }

    public boolean onTouchEvent(MotionEvent motionEvent){
        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
        }
        return AppManager.getS_instance().getState().onTouchEvent(motionEvent);
    }
    //public static void ChangeGameState(IState iState){
        //->App manager 의 setState로 변경
    //}
}
