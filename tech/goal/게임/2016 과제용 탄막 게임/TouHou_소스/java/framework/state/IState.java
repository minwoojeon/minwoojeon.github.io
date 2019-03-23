package framework.state;

import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * Created by junminwoo on 2016-04-14.
 */
public interface IState {
    //게임 상태를 일반화하는 인터페이스- 이걸 받아서 상세 구조화하여 사용
    public void Init();
    public void Destroy();
    public void Update();
    public void Render(Canvas canvas);

    public boolean onTouchEvent(MotionEvent event);
}
