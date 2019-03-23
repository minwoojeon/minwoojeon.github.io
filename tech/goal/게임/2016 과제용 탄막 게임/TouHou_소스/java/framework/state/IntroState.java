package framework.state;

import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.junminwoo.touhou_galaxy.R;

import framework.AppManager;
import framework.src.SoundManager.Game_sound;
import framework.src.background.stage_bg1;

/**
 * Created by junminwoo on 2016-04-14.
 */
public class IntroState implements IState {
    private stage_bg1 backbit;

    @Override
    public void Init() {
        backbit = new stage_bg1(1);
    }

    @Override
    public void Destroy() {

    }

    long GameTime = System.currentTimeMillis();
    @Override
    public void Update() {
        long GameTime = System.currentTimeMillis();
        backbit.Update(GameTime); // 추가

        if (GameTime - this.GameTime >= 6000){
            this.GameTime = System.currentTimeMillis();
            Game_sound.getS_Instance().Play(1);
        }
    }

    @Override
    public void Render(Canvas canvas) {
        backbit.Draw(canvas);
        canvas.drawBitmap(AppManager.getS_instance().getBitmap(R.drawable.newstate), 90, 200, null);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){

        if(event.getAction() == MotionEvent.ACTION_DOWN){//클릭하면 게임시작
            AppManager.getS_instance().setState(new GameState());
        }else if (event.getAction() == MotionEvent.ACTION_UP){
        }
        return true;
    }
}
