package framework;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import framework.state.IState;

/**
 * Created by junminwoo on 2016-04-12.
 */
public class AppManager {
    private static AppManager s_instance;
    private Resources m_resouces;
    private GameView m_gameview;
    private IState state;

    public void setGameView(GameView _gameview){m_gameview=_gameview;}
    public void setResouces(Resources _resouces){m_resouces = _resouces;}
    public GameView getGameView (){return m_gameview;}
    public Resources getResouces(){return m_resouces;}

    public void setState(IState state){
        if (this.state != null) this.state.Destroy();
        state.Init();
        this.state = state;
    }
    public IState getState(){return state;}

    public static AppManager getS_instance(){
        if (s_instance ==null) s_instance = new AppManager();
        return s_instance;
    }

    public Bitmap getBitmap(int r){
        return BitmapFactory.decodeResource(m_resouces,r);
    }
}
