package framework.src.charater;

import android.graphics.Bitmap;
import android.media.AudioManager;

import framework.AppManager;
import framework.src.graphic_manager.SpriteAnimation;

/**
 * Created by junminwoo on 2016-05-03.
 */
public class Player extends SpriteAnimation {
    protected int hp;
    public int mov_point =0;

    public Player(Bitmap bitmap){
        super(bitmap);
        this.InitSpriteData(3,1);
        this.SetPosition((int)AppManager.getS_instance().getGameView().getWidth()/2, AppManager.getS_instance().getGameView().getHeight()-120);
        hp = 100;
    }

    public void setHp(int hp){
        this.hp += hp;
    }
    public int getHp(){
        return hp;
    }

    public boolean mov = false;

    public void mov(){
        int a = AppManager.getS_instance().getGameView().getWidth();
        int b = AppManager.getS_instance().getGameView().getHeight();

        switch (mov_point){
            case 1: //좌로 이동
                if (m_x >= 5){
                    SetPosition(m_x - 3, m_y);
                }
                break;
            case 2: //우로 이동
                if (m_x <= a-5){
                    SetPosition(m_x + 3, m_y);
                }
                break;
            case 3: //상
                if (m_y >= 20){
                    SetPosition(m_x, m_y-3);
                }
                break;
            case 4: //하
                if (m_y <= b-20){
                    SetPosition(m_x, m_y+3);
                }
                break;
            default:
                break;
        }
    }
    public void Update(long GameTime){
        super.Update(GameTime);
        if (mov){
            mov();
        }
    }
}
