package framework.src.missile;

import android.graphics.Bitmap;

import com.example.junminwoo.touhou_galaxy.R;

import framework.AppManager;
import framework.src.graphic_manager.SpriteAnimation;

/**
 * Created by junminwoo on 2016-05-19.
 */
public class Missile extends SpriteAnimation {
    protected int speed = 11;

    public static final int mov_pt1 = 0;
    public static final int mov_pt2 = 1;
    public static final int mov_pt3 = 2;
    public boolean die = false;
    protected int mov_type = 0;//move 방식은 각각 따로 주는 방법 -> 책 / 또는 랜덤으로 줄 수 있습니다.

    protected void mov(){
        SetPosition(m_x,m_y);
    }

    public Missile() {
        super(AppManager.getS_instance().getBitmap(R.drawable.bullet_ball_r1));
        this.InitSpriteData(5, 5);
        speed = 2;
        mov_type = mov_pt2;
    }
    public Missile(Bitmap whatyousee, int bul_r, int speed, int _mov_type) {
        super(whatyousee);
        this.InitSpriteData(5, bul_r);
        this.speed = speed;
        mov_type = _mov_type;
    }

    @Override
    public void Update(long GameTime) {
        super.Update(GameTime);
    }
}
