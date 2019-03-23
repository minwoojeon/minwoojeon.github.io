package framework.src.graphic_manager;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by junminwoo on 2016-04-14.
 */
public class SpriteAnimation extends GraphicObject {
    private Rect m_rect;
    private int m_fps;
    private int m_iFrames;
    private long ftime;
    private int m_Cframe;
    private int m_SpWidth;
    private int m_SpHeight;
    protected Bitmap m_bitmap;
    public Rect hit_m = new Rect(0,0,0,0);
    protected boolean anim_end = false;
    protected boolean anim_repl = true;

    public void Draw(Canvas canvas){
        Rect rect = new Rect(m_x, m_y, m_x + m_SpWidth, m_y + m_SpHeight);
        canvas.drawBitmap(m_bitmap, m_rect, rect, null);
    }

    public SpriteAnimation(Bitmap bitmap) {
        super(bitmap);
        m_rect = new Rect(0,0,0,0);
        ftime = m_Cframe = 0; //단순 초기화는 이렇게 묶었습니다.
        m_bitmap = bitmap;
    }
    public void InitSpriteData(int fp, int f){
        m_SpWidth = m_bitmap.getWidth() / f;
        m_SpHeight = m_bitmap.getHeight();
        m_rect.right = m_SpWidth;
        m_rect.bottom = m_SpHeight;
        m_rect.top = m_rect.left = 0;
        hit_m.set(m_x, m_y, m_x + m_SpWidth, m_y + m_SpHeight);

        m_fps = 1000/ fp;
        m_iFrames = f;
    }
    public void Update(long GameTime){
        if (!anim_end){
            if(GameTime > ftime + m_fps){
                ftime = GameTime;
                m_Cframe += 1;
                if(m_Cframe >= m_iFrames){
                    if (anim_repl){
                        m_Cframe =0;
                    }else{
                        anim_end = true;
                    }
                }
            }
        }
        m_rect.left = m_Cframe * m_SpWidth;
        m_rect.right = m_rect.left + m_SpWidth;

    }

    @Override
    public void SetPosition(int x, int y) {
        super.SetPosition(x, y);
        hit_m.set(m_x, m_y, m_x + m_SpWidth, m_y + m_SpHeight);
    }
}
