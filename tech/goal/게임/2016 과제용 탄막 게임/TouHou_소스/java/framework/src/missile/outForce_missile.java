package framework.src.missile;

import android.graphics.Bitmap;

import com.example.junminwoo.touhou_galaxy.R;

import framework.AppManager;

/**
 * Created by junminwoo on 2016-06-05.
 */
public class outForce_missile extends Missile {
    public int outX, outY;
    @Override
    protected void mov() {
        switch (mov_type){
            case mov_pt1:
                //1패턴
                if(m_y <= 200) m_y += speed; else m_y += speed * 2;
                break;
            case mov_pt2:
                //2
                if(m_y <= 200) m_y += speed; else{ m_x -= speed; m_y += speed * 2;}
                break;
            case mov_pt3:
                //3
                if(m_y <= 200) m_y += speed; else{ m_x += speed; m_y += speed * 2;}
                break;

            //
            case 5:
                //5 추적
                if(m_x <= outX) {
                    m_x += speed;
                } else{
                    m_x -= speed;
                }
                if(m_x <= outY) {
                    m_y += speed;
                } else{
                    m_y -= speed;
                }
                break;
            case 6:
                //6
                if(m_x <= outX) {
                    m_x += speed;
                } else{
                    m_x -= speed;
                }
                if(m_x <= outY) {
                    m_y += speed;
                } else{
                    m_y -= speed;
                }
                break;
            case 7:
                //7
                if(m_y <= 200) m_y += speed; else{ m_x += speed; m_y += speed * 2;}
                break;
            case 8:
                //8
                if(m_y <= 200) m_y += speed; else{ m_x += speed; m_y += speed * 2;}
                break;
            default:
                break;
        }
        SetPosition(m_x,m_y);
        int left =0;
        int right = AppManager.getS_instance().getGameView().getWidth();
        int height = AppManager.getS_instance().getGameView().getHeight();
        if (m_x<left || m_x >right || m_y > height){
            die = true;
        }
    }

    public outForce_missile() {
        super(AppManager.getS_instance().getBitmap(R.drawable.bullet_ball_r1), 5, 4, Missile.mov_pt1);
    }
    public outForce_missile(int i) {
        super(AppManager.getS_instance().getBitmap(R.drawable.bullet_ball_r1), 5, 4, i);
    }
    public outForce_missile(Bitmap _bitmap, int x, int y, int speed) {
        super(_bitmap, 5, 4, 5);
        outX = x; outY = y;
        this.speed = speed;
    }

    @Override
    public void Update(long GameTime) {
        super.Update(GameTime);
        mov();
    }
}
