package framework.state;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.junminwoo.touhou_galaxy.R;

import framework.AppManager;
import framework.sql.SQLManager;
import framework.src.SoundManager.Game_sound;
import framework.src.background.stage_bg1;

/**
 * Created by junminwoo on 2016-04-14.
 */
public class ScoreState implements IState {
    private stage_bg1 backbit;

    @Override
    public void Init() {
        backbit = new stage_bg1(1);
    }

    @Override
    public void Destroy() {

    }

    private long GameTime = System.currentTimeMillis();
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
        Paint mp = new Paint();
        canvas.drawBitmap(AppManager.getS_instance().getBitmap(R.drawable.rank_board), 90, 200, mp);
        mp.setColor(Color.CYAN);
        mp.setTextSize(14.0f);


        Cursor cs;
        SQLiteDatabase db = SQLManager.getDB_instance().getDB_ite().getReadableDatabase();
        //검색용으로 엽니다.
        cs = db.query("g_rank", new String[]{"name","score"}, null, null, null, null, "score desc");
        for (int i=0; i<10; i++){ //커서에 두었던 데이터 값을 보여줍니다.
            if (cs.moveToNext() == false) break;
            canvas.drawText(cs.getString(0)+"", 110, 325 + 15*i, mp);
            canvas.drawText(cs.getString(1)+"", 220, 325 + 15*i, mp);
        }
        db.close();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){

        if(event.getAction() == MotionEvent.ACTION_DOWN){//클릭하면 처음 인트로
            AppManager.getS_instance().setState(new IntroState());
        }else if (event.getAction() == MotionEvent.ACTION_UP){
        }
        return true;
    }
}
