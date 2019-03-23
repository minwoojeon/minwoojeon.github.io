package framework.src.charater;

import android.graphics.Bitmap;

import framework.AppManager;
import framework.src.graphic_manager.SpriteAnimation;

/**
 * Created by junminwoo on 2016-05-19.
 */
public class Enemy extends SpriteAnimation {
    protected int hp;
    protected int speed = 2;
    public String name;

    public static final int mov_pt1 = 0;
    public static final int mov_pt2 = 1;
    public static final int mov_pt3 = 2;
    public boolean mov = false;
    public boolean die = false;
    public int att_pt;
    protected int mov_type = 0;//move 방식은 각각 따로 주는 방법 -> 책 / 또는 랜덤으로 줄 수 있습니다.

    public Enemy(Bitmap _bitmap){
        super(_bitmap);
    }
    public Enemy(Bitmap _bitmap, int hp, int speed){
        super(_bitmap);
        this.hp = hp;
    }

    public void setHp(int hp){
        this.hp += hp;
    }
    public int getHp(){
        return hp;
    }

    protected void mov(){
        //무브
        switch (mov_type){
            case mov_pt1:
                //1패턴
                if(m_y <= 200) m_y += speed; else m_y += speed * 2;
                break;
            case mov_pt2:
                //2
                if(m_y <= 200) m_y += speed; else{ m_x += speed; m_y += speed * 2;}
                break;
            case mov_pt3:
                //2
                if(m_y <= 200) m_y += speed; else{ m_x -= speed; m_y += speed * 2;}
                break;
            default:
                break;
        }
        SetPosition(m_x,m_y);
        int left =0;
        int right = AppManager.getS_instance().getGameView().getWidth();
        int height = AppManager.getS_instance().getGameView().getHeight();
        if (m_x<left || m_x >right || m_y > height || hp <= 0){
            die = true;
        }
    }

    @Override
    public void Update(long GameTime) {
        super.Update(GameTime);
        mov();
    }

}
