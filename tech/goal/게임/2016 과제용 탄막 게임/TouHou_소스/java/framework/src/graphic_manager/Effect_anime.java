package framework.src.graphic_manager;

import android.graphics.Bitmap;

import com.example.junminwoo.touhou_galaxy.R;

import framework.AppManager;

/**
 * Created by junminwoo on 2016-06-08.
 */
public class Effect_anime extends SpriteAnimation {
    public boolean die = false;
    public int wharani = -1;

    public Effect_anime(int x, int y) { //이건 죽을때
        super(AppManager.getS_instance().getBitmap(R.drawable.explosion));
        this.InitSpriteData(3, 6);
        m_x = x; m_y = y;
        anim_repl = false;
    }
    public Effect_anime(int val, int x, int y) { //이건 아이템
        super(null);
        switch (val){
            case 0:
                m_bitmap = AppManager.getS_instance().getBitmap(R.drawable.item1);
                wharani =0;
                break;
            case 1:
                m_bitmap = AppManager.getS_instance().getBitmap(R.drawable.item2);
                wharani =1;
                break;
            case 2:
                m_bitmap = AppManager.getS_instance().getBitmap(R.drawable.item3);
                wharani =2;
                break;
            default:
                break;
        }
        this.InitSpriteData(3, 4);
        m_x = x; m_y = y;
        anim_repl = true;
    }

    public boolean getAnimeEnd(){
        return anim_end;
    }

    @Override
    public void Update(long GameTime) {
        super.Update(GameTime);

        m_y += 1;
        int left =0;
        int right = AppManager.getS_instance().getGameView().getWidth();
        int height = AppManager.getS_instance().getGameView().getHeight();
        if (m_x<left || m_x >right || m_y > height || die ==true){
            anim_end = true;
            anim_repl = false;
        }
        SetPosition(m_x, m_y);
    }
}
