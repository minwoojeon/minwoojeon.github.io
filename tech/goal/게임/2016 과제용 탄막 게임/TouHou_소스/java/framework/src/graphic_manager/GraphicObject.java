package framework.src.graphic_manager;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by junminwoo on 2016-04-14.
 */
public class GraphicObject {
    protected Bitmap m_bitmap;
    protected int m_x,  m_y;

    public GraphicObject(Bitmap bitmap){
        m_bitmap = bitmap;
        m_x = m_y =0;
    }
    public void Draw(Canvas canvas){
        canvas.drawBitmap(m_bitmap, m_x, m_y, null);
    }
    public void SetPosition(int x, int y){ m_x = x;  m_y = y; }
    public int GetX(){ return m_x; }
    public int GetY(){
        return m_y;
    }
}
