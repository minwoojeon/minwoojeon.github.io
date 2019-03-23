package framework.src.missile;

import android.graphics.Rect;

import com.example.junminwoo.touhou_galaxy.R;

import framework.AppManager;
import framework.src.graphic_manager.GraphicObject;

/**
 * Created by junminwoo on 2016-06-05.
 */
public class inForce_missile extends Missile {
    public inForce_missile(){
        super(AppManager.getS_instance().getBitmap(R.drawable.bullet_ball_p), 1, 5, Missile.mov_pt1);

    }
    @Override
    public void Update(long GameTime) {
        super.Update(GameTime);
        mov();
    }

    @Override
    protected void mov() {
        switch (mov_type){
            case mov_pt1:
                //1패턴
                if(m_y >= 200) m_y -= speed; else m_y -= speed * 2;
                break;
            case mov_pt2:
                //2
                if(m_y >= 200) m_y -= speed; else{ m_x -= speed; m_y -= speed * 2;}
                break;
            case mov_pt3:
                //3
                if(m_y >= 200) m_y -= speed; else{ m_x += speed; m_y -= speed * 2;}
                break;
            default:
                break;
        }
        SetPosition(m_x,m_y);
        int left =0;
        int right = AppManager.getS_instance().getGameView().getWidth();
        if (m_x<left || m_x >right || m_y < 0){
            die = true;
        }
    }
}
