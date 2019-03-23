package framework.src.charater.enemy;

import com.example.junminwoo.touhou_galaxy.R;

import framework.AppManager;
import framework.src.charater.Enemy;

/**
 * Created by junminwoo on 2016-06-07.
 */
public class mob_f_4 extends Enemy {
    public mob_f_4() {
        super(AppManager.getS_instance().getBitmap(R.drawable.mob4));
        this.InitSpriteData(4, 8);
        hp = 4;
        speed = 2;
        mov_type = Enemy.mov_pt3;
    }
}
