package framework;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by junminwoo on 2016-04-07.
 */
public class GameViewThread extends Thread {
    private SurfaceHolder m_surfaceHolder;
    private GameView m_gameview;

    private boolean m_run = false;

    public GameViewThread(SurfaceHolder surfaceHolder, GameView gameView){
        m_gameview = gameView;
        m_surfaceHolder = surfaceHolder;

    }
    public void setRunning(boolean run){
        m_run = run;
    }
    public void run(){

        Canvas _canvas;
        while(m_run){
            _canvas = null;
            try{
                m_gameview.Update();
                //서페이스홀더를 통해 서페이스에 접근, 가져옴

                _canvas = m_surfaceHolder.lockCanvas(null);
                synchronized (m_surfaceHolder){
                    m_gameview.draw(_canvas);
                }
            }finally {
                if (_canvas != null){
                    //서페이스 표시!
                    m_surfaceHolder.unlockCanvasAndPost(_canvas);
                }

            }
        }
    }

}
