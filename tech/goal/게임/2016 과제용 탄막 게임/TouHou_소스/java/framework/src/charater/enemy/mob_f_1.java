package framework.src.charater.enemy;

import com.example.junminwoo.touhou_galaxy.R;

import framework.AppManager;
import framework.src.charater.Enemy;

/**
 * Created by junminwoo on 2016-05-19.
 */
public class mob_f_1 extends Enemy {
    public mob_f_1() {
        super(AppManager.getS_instance().getBitmap(R.drawable.mob1));
        this.InitSpriteData(4, 8);
        hp = 3;
        speed = 2;
        mov_type = Enemy.mov_pt2;
    }
}
