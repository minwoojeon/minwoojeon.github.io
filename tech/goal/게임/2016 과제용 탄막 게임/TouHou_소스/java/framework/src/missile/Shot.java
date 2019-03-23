package framework.src.missile;

import android.graphics.Rect;

import com.example.junminwoo.touhou_galaxy.R;

import java.util.ArrayList;
import java.util.Random;

import framework.AppManager;
import framework.src.charater.Enemy;
import framework.src.charater.Player;

/**
 * Created by junminwoo on 2016-06-07.
 */
public class Shot {



    public boolean Collision(Rect r1, Rect r2){
        if (r1.intersect(r2)){
            return true;
        }
        return false;
    }
}
