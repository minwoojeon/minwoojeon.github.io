package framework.src.background;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.junminwoo.touhou_galaxy.R;

import framework.AppManager;
import framework.src.graphic_manager.GraphicObject;

/**
 * Created by junminwoo on 2016-05-17.
 */
public class stage_bg1 extends GraphicObject {
    static final float speed = 0.4f;
    static final float speed2 = 0.9f;

    float scroll = -760 , scroll2 = -760;
    Bitmap m_layer2;

    public stage_bg1(){
        super(AppManager.getS_instance().getBitmap(R.drawable.background1));
        m_layer2 = AppManager.getS_instance().getBitmap(R.drawable.background_2);
        SetPosition(0, (int)scroll);
    }
    public stage_bg1(int type){
        super(null);
        if(type == 0){
            m_bitmap = AppManager.getS_instance().getBitmap(R.drawable.background1);
        }else if(type == 1){
            m_bitmap = AppManager.getS_instance().getBitmap(R.drawable.backbit);
        }
        m_layer2 = AppManager.getS_instance().getBitmap(R.drawable.background_2);
        SetPosition(0, (int)scroll);
    }
    public void Draw(Canvas canvas){
        canvas.drawBitmap(m_bitmap, m_x, m_y, null);
        canvas.drawBitmap(m_layer2, m_x, scroll2, null);
    }
    public void Update(long gt){
        scroll = scroll + speed;
        scroll2 = scroll2 + speed2;

        if(scroll >= 0){
            scroll = -760;
            //멈출때, 다시 초기화 무하반복
        }
        if(scroll2 >= 0){
            scroll2 = -760;
        }
        SetPosition(0, (int)scroll);
    }
    public void SetPosition(int x, int y){ m_x = x;  m_y = y; }
    public int GetX(){ return m_x; }
    public int GetY(){
        return m_y;
    }
}
