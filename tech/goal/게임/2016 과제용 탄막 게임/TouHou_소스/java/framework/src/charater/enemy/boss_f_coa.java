package framework.src.charater.enemy;

import android.graphics.Rect;

import com.example.junminwoo.touhou_galaxy.R;
import java.util.Random;
import framework.AppManager;
import framework.src.charater.Enemy;

/**
 * Created by junminwoo on 2016-06-06.
 */
public class boss_f_coa extends Enemy {
    private Random rd_num = new Random();
    private int rx, ry;
    public boolean mov = true;
    long gt = System.currentTimeMillis();

    public boss_f_coa() {
        super(AppManager.getS_instance().getBitmap(R.drawable.coa));
        this.InitSpriteData(4, 7);
        hp = 100;
        die = false;
        speed = 2;
        mov_type = 1;
        name = "COA";
        att_pt = 0;
    }

    protected void mov(){
        //super.mov();

        if ((System.currentTimeMillis() - gt) >= 4500.0f){
            gt = System.currentTimeMillis();
            mov = true;
            att_pt = rd_num.nextInt(3);
            rx = rd_num.nextInt(240);
            ry = rd_num.nextInt(100);
            return;
        }
        switch (mov_type){
            case mov_pt1:
                //1패턴 싸돌아다니기
                if (mov){
                    if (m_x >= rx-10 && m_x <= rx+10 && m_y >= ry+90 && m_y <= ry+100){
                        mov = false;
                    }else{
                        if (m_y <= ry) {
                            m_y += speed;
                        }else{
                            m_y -= speed;
                        }
                        if (m_x >= rx){
                            m_x -= speed;
                        }else{
                            m_x += speed;
                        }
                    }
                }else{
                    mov_type = rd_num.nextInt(3);
                    rx = rd_num.nextInt(240);
                    ry = rd_num.nextInt(100);
                    mov=false;
                }
                break;
            case mov_pt2:
                //2패턴
                if (mov){
                    if (m_x >= rx-10 && m_x <= rx+10 && m_y >= ry+90 && m_y <= ry+100){
                        mov = false;
                    }else{
                        if (m_y <= ry) {
                            m_y += speed;
                        }else{
                            m_y -= speed;
                        }
                        if (m_x >= rx){
                            m_x -= speed;
                        }else{
                            m_x += speed;
                        }
                    }
                }else{
                    mov_type = rd_num.nextInt(3);
                    rx = rd_num.nextInt(240);
                    ry = rd_num.nextInt(100);
                    mov=false;
                }
                break;
            case mov_pt3:
            default:
                break;
        }
        SetPosition(m_x,m_y);
        int left =-200;
        int right = AppManager.getS_instance().getGameView().getWidth();
        int height = AppManager.getS_instance().getGameView().getHeight();
        if (m_x<left || m_x >right || m_y > height || hp <= 0){
            die = true;
        }
    }
}
