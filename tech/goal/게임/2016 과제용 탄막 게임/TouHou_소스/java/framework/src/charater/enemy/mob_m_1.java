package framework.src.charater.enemy;

import com.example.junminwoo.touhou_galaxy.R;

import framework.AppManager;
import framework.src.charater.Enemy;

/**
 * Created by junminwoo on 2016-06-07.
 */
public class mob_m_1 extends Enemy {
    public mob_m_1() {
        super(AppManager.getS_instance().getBitmap(R.drawable.mob5));
        this.InitSpriteData(4,8);
        hp = 6;
        speed = 1;
        mov_type = Enemy.mov_pt1;
    }
}
