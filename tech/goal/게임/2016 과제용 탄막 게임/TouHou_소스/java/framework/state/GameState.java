package framework.state;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.example.junminwoo.touhou_galaxy.R;

import java.util.ArrayList;
import java.util.Random;


import framework.AppManager;
import framework.sql.SQLManager;
import framework.src.SoundManager.Game_sound;
import framework.src.background.stage_bg1;
import framework.src.charater.Enemy;
import framework.src.charater.Player;
import framework.src.charater.enemy.boss_f_coa;
import framework.src.charater.enemy.mob_f_2;
import framework.src.charater.enemy.mob_f_1;
import framework.src.charater.enemy.mob_f_3;
import framework.src.charater.enemy.mob_f_4;
import framework.src.graphic_manager.Effect_anime;
import framework.src.graphic_manager.GraphicObject;
import framework.src.missile.inForce_missile;
import framework.src.missile.outForce_missile;

/**
 * Created by junminwoo on 2016-05-03.
 */

public class GameState implements IState {
    private boolean endGame = false; //게임이 끝났는지
    private Player m_player; //player
    private stage_bg1 backbit;
    private GraphicObject keypad;

    private ArrayList<Enemy> list_enemy = new ArrayList<Enemy>();
    private ArrayList<inForce_missile> list_pm = new ArrayList<inForce_missile>();
    private ArrayList<outForce_missile> list_npm = new ArrayList<outForce_missile>();
    private ArrayList<Effect_anime> list_np_ef = new ArrayList<Effect_anime>();
    private ArrayList<Effect_anime> list_item = new ArrayList<Effect_anime>();
    private Enemy boss_mob = new Enemy(null);
    private long[] timer_update = new long[5];
    private boolean boss_create=false;
    private boolean player_att = false;
    private int circle_rect = 0;
    private int point;
    private Random rdEnem = new Random();
    private int a = AppManager.getS_instance().getGameView().getWidth();
    private int b = AppManager.getS_instance().getGameView().getHeight();

    /*
    * wjsalsdnrk7 code!
    * timer_update---
    * 0 - 충돌
    * 1 - 플레이어 샷
    * 2 - 몬스터 젠(일반)
    * 3 - 보스몹 젠
    * 4 - 전체 게임 시간
    * */

    //시간과 관련된 처리를 합니다.
    private void TimePurpose(){
        long timer = System.currentTimeMillis();

        if(timer - timer_update[1] >= 100.0f){
            timer_update[1] = System.currentTimeMillis();
            if (player_att){ //플레이어 탄은 0.1초보다 더 빨리 발사 할 수 없음
                Make_pShot();
                player_att = false;
            }
            Collision();
        }
        if (timer - timer_update[3] >= 45000.0f){
            if(!boss_create){
                boss_create = true;
                list_npm.clear();
                list_enemy.clear();
                MakeBoss();
                //보스가 죽을때까지 시간초는 리셋 x.
            }else if(boss_mob.die){ //보스가 쓰러지면
                timer_update[3] = System.currentTimeMillis();
                endGame = true; //결과창을 보여주기 위한 것
                point += 5000; //보스처치시 기본 보상
                if (timer_update[3] - timer_update[4] <= 180000){
                    //일정시간 (3분, 5분, 10분)이내 클리어시 추가보상 지급.
                    point += (Math.round((180000.0f - (timer_update[3] - timer_update[4]))/1000.0f)) * 3;
                    //보다 빨리 클리어한 초 만큼 추가 보상 지급.
                }else if (timer_update[3] - timer_update[4] <= 300000){
                    point += (Math.round((180000.0f - (timer_update[3] - timer_update[4]))/1000.0f)) * 2;
                }else if (timer_update[3] - timer_update[4] <= 600000){
                    point += (Math.round((180000.0f - (timer_update[3] - timer_update[4]))/1000.0f)) * 1;
                }
                boss_mob.die = false;
                //
                DB_insert("Guest", point);//결과를 저장(DB + 앱이나 전원을 다시켜도 볼수 있도록)
            }
            if (timer - timer_update[2] >= 1000.0f){ //보스 공격
                timer_update[2] = System.currentTimeMillis();
                if (boss_mob.att_pt == 1){//1 패턴은 플레이어의 좌표를 기준으로 떨어지는 5개의 탄
                    Make_npShot(5, m_player.GetX()-150, m_player.GetY(), 2);
                    Make_npShot(5, m_player.GetX()-70, m_player.GetY(), 2);
                    Make_npShot(5, m_player.GetX(), m_player.GetY(), 2);
                    Make_npShot(5, m_player.GetX()+70, m_player.GetY(), 2);
                    Make_npShot(5, m_player.GetX()+150, m_player.GetY(), 2);
                }else if(boss_mob.att_pt == 2){ //2패턴은 보스의 몸에서부터 사방으로 튀어나옴
                    Make_npShot(6, circle_rect, -100, 2);
                    Make_npShot(6, AppManager.getS_instance().getGameView().getWidth()-100, circle_rect*2-100, 2);
                    Make_npShot(6, -100, AppManager.getS_instance().getGameView().getHeight()-circle_rect*2-100, 2);
                    Make_npShot(6, AppManager.getS_instance().getGameView().getWidth()-circle_rect-100,
                            AppManager.getS_instance().getGameView().getHeight()-100, 2);
                    circle_rect += 20;
                    if (circle_rect >= 240){
                        circle_rect = 0;
                    }
                }else{ //다른 패턴은 그냥 던지기
                    Make_npShot(1, boss_mob.GetX(), m_player.GetY(), 3);
                }
            }
        }else if (timer - timer_update[2] >= 800.0f && !endGame){
            //보스몹 나올 시간 아니면 그냥 잡몹 소환
            timer_update[2] = System.currentTimeMillis();
            MakeEnemy();
            Make_npShot();
        }
    }

    private void DB_insert(String name, int score){ //DB에 값을 적용합니다.
        try{
            SQLiteDatabase insertData = SQLManager.getDB_instance().getDB_ite().getWritableDatabase(); //쓰기-읽기 모드로 DB 접근합니다.
            insertData.execSQL("insert into g_rank values('" +
                    name + "'," + score +
                    ");");
            insertData.close(); //다 썻으니 닫아줍시다.
        }catch (Exception e){
        }
    }
    private void Make_pShot(){//플레이어 탄
        Game_sound.getS_Instance().Play(2);
        inForce_missile pm = new inForce_missile();
        int x = m_player.GetX();
        int y = m_player.GetY();
        pm.SetPosition(x, y);
        list_pm.add(pm);
    }
    private void Make_npShot(){ //적 탄
        for(Enemy enemy : list_enemy){
            outForce_missile om = new outForce_missile();
            om.SetPosition(enemy.GetX(), enemy.GetY());
            list_npm.add(om);
        }
    }

    private void Make_npShot(int mov_type, int x, int y, int speed){//보스 탄
        Random ri = new Random();

        for(Enemy enemy : list_enemy){
            outForce_missile om = null;
            switch (ri.nextInt(7)){
                case 0:
                    om = new outForce_missile(AppManager.getS_instance().getBitmap(R.drawable.bullet_ball_r1), x, y, speed);
                    break;
                case 1:
                    om = new outForce_missile(AppManager.getS_instance().getBitmap(R.drawable.bullet_ball_b1), x, y, speed);
                    break;
                case 2:
                    om = new outForce_missile(AppManager.getS_instance().getBitmap(R.drawable.bullet_ball_g1), x, y, speed);
                    break;
                case 3:
                    om = new outForce_missile(AppManager.getS_instance().getBitmap(R.drawable.bullet_ball_j1), x, y, speed);
                    break;
                case 4:
                    om = new outForce_missile(AppManager.getS_instance().getBitmap(R.drawable.bullet_ball_sky1), x, y, speed);
                    break;
                case 5:
                    om = new outForce_missile(AppManager.getS_instance().getBitmap(R.drawable.bullet_ball_y1), x, y, speed);
                    break;
                case 6:
                    om = new outForce_missile(AppManager.getS_instance().getBitmap(R.drawable.bullet_ball_z1), x, y, speed);
                    break;
                default:
                    break;
            }
            om.SetPosition(enemy.GetX(), enemy.GetY());
            list_npm.add(om);
        }
    }

    private void MakeBoss() { //보스 생성
        boss_mob = new boss_f_coa();
        boss_mob.SetPosition(rdEnem.nextInt(a), -60);
        list_enemy.add(boss_mob);
    }

    private void MakeEnemy(){ //적 생성
            int e_type = rdEnem.nextInt(4);
            Enemy enemy = null;
            switch (e_type){
                case 0:
                    enemy = new mob_f_1();
                    break;
                case 1:
                    enemy = new mob_f_2();
                    break;
                case 2:
                    enemy = new mob_f_3();
                    break;
                case 3:
                    enemy = new mob_f_4();
                    break;
                default:
                    break;
            }
            enemy.SetPosition(rdEnem.nextInt(a), 20);
            list_enemy.add(enemy);
    }

    private void Collision(){
        //충돌
        for (int i=list_item.size()-1; i>=0; i--){
            if (m_player.hit_m.intersect(list_item.get(i).hit_m)){

                if(list_item.get(i).wharani ==0){
                    list_item.remove(i);
                    //list_np_ef.get(i).die = true;
                    point += 20;
                    return;
                }else if (list_item.get(i).wharani ==1) {
                    list_item.remove(i);
                    //list_np_ef.get(i).die = true;
                    m_player.setHp(2);
                    return;
                }else if (list_item.get(i).wharani ==2) {
                    list_item.remove(i);
                    //list_np_ef.get(i).die = true;
                    point += 200;
                    return;
                }
            }
        }
        for (int i = list_pm.size()-1; i>=0; i--){
            for (int j = list_enemy.size()-1; j>=0; j--){
                if (list_pm.get(i).hit_m.intersect(list_enemy.get(j).hit_m)){
                    //아군 탄막 vs 적군
                    list_pm.get(i).die = true;
                    list_enemy.get(j).setHp(-1);
                    return;
                    //point += 3;
                }else if (list_enemy.get(j).hit_m.intersect(m_player.hit_m)){
                    //적군 vs 아군
                    list_enemy.get(j).setHp(-2);
                    m_player.setHp(-1);
                    point--;
                    return;
                }
            }
            for (int j = list_npm.size()-1; j>=0; j--){
                if (list_npm.get(j).hit_m.intersect(m_player.hit_m)){
                    //적군 탄막 vs 아군
                    list_npm.get(j).die = true;
                    m_player.setHp(-1);
                    point -= 2;
                    break;
                }else if (list_npm.get(j).hit_m.intersect(list_pm.get(i).hit_m)){
                    //적군 탄막 vs 아군 탄막
                    list_npm.get(j).die = true;
                    list_pm.get(i).die = true;
                    point += 2;
                    return;
                }
            }
        }
    }

   public void Init(){ //최초
       backbit = new stage_bg1(1);
       m_player = new Player(AppManager.getS_instance().getBitmap(R.drawable.player));
       keypad = new GraphicObject(AppManager.getS_instance().getBitmap(R.drawable.keypad));
       keypad.SetPosition(0, b - 120); //키패드
       //초기화
       list_npm.clear();
       list_pm.clear();
       list_enemy.clear();
       for (int i = 0; i<5; i++){
           timer_update[i] = System.currentTimeMillis();
       }
       point = 0;
       Game_sound.getS_Instance().playlooped(1);
   }
    public void Render(Canvas canvas){ //그림
        Paint mp = new Paint();
        backbit.Draw(canvas);//백그라운드 삽입
        mp.setColor(Color.BLACK);
        if(!endGame){ //겜 안끝났으면 상태창 출력
            Rect r = new Rect(a-180, b-400, a, b - 180);
            canvas.drawRect(r, mp);
            mp.setColor(Color.CYAN); //푸른 빛 넘 좋아해요.
            mp.setTextSize(18.0f);
            int ts = Math.round((System.currentTimeMillis() - timer_update[4]) /1000.0f);
            canvas.drawText(" 진행 시간 : " + ts/3600 + " : " + ts/60 +" : " + ts%60, a - 150, b - 380, mp);
            //3600 -> 시 | 60 -> 분 | 나머지 -> 초
            canvas.drawText(" 남은 목숨 : " + m_player.getHp(), a - 150, b - 200, mp);
            canvas.drawText(" 획득 점수 : " + point, a-150, b - 180, mp);
            canvas.drawText(" 남은 몹 : " + list_enemy.size(), a - 150, b - 300, mp);
            //이건 나중에 몹수를 이용해서 일정 수 넘으면 포인트를 감점한다던지 하면 좋을듯
            canvas.drawText(" boss 체력 : " + boss_mob.getHp(), a - 150, b - 320, mp);
            canvas.drawText(" Debug : L-" + boss_mob.hit_m.left + " R-" +
                    boss_mob.hit_m.right + " T-" + boss_mob.hit_m.top + " B-" +
                    boss_mob.hit_m.bottom + "", a - 150, b - 220, mp);
            //디버그용 출력 - 논리오류가 어디인지 확인하기 위한 출력부분.
        }else{ //겜 끝나면 결과창
            canvas.drawBitmap(AppManager.getS_instance().getBitmap(R.drawable.gamestate), 90, 200, null);
            mp.setColor(Color.WHITE);
            mp.setTextSize(14.0f);
            int ts = Math.round((timer_update[3] - timer_update[4]) /1000.0f);
            canvas.drawText(ts/3600 + " : " + ts/60 +" : " + ts%60 + "", 240, 300, mp);
            canvas.drawText(Math.round(point) + "", 240, 315, mp);
            canvas.drawText(m_player.getHp() + "", 240, 330, mp);
        }

        m_player.Draw(canvas); //플 그리기 및 아래는 그리기

        for(Enemy enemy : list_enemy){
            enemy.Draw(canvas);
        }
        for(inForce_missile pm: list_pm){
            pm.Draw(canvas);
        }
        for(outForce_missile npm: list_npm){
            npm.Draw(canvas);
        }
        for(Effect_anime np_ef: list_np_ef){
            np_ef.Draw(canvas);
        }
        for(Effect_anime np_ef: list_item){
            np_ef.Draw(canvas);
        }
        keypad.Draw(canvas);
    }
    public void Update(){
        long GameTime = System.currentTimeMillis();
        backbit.Update(GameTime); // 배경
        m_player.Update(GameTime); //플레이어
        try{

            for(int i=list_enemy.size()-1; i>=0; i--){
                Enemy enemy = list_enemy.get(i);
                enemy.Update(GameTime);
                if(enemy.die == true){ //죽었다면
                    Game_sound.getS_Instance().Play(3); //잡았으니까 사운드
                    list_np_ef.add(new Effect_anime(list_enemy.get(i).GetX(), list_enemy.get(i).GetY()));
                    int item_no = rdEnem.nextInt(6); //0~5 중 0~2는 아이템, 나머지는 꽝
                    if (item_no == 0){
                        list_item.add(new Effect_anime(0, list_enemy.get(i).GetX(), list_enemy.get(i).GetY()));
                    }else if (item_no == 1){
                        list_item.add(new Effect_anime(1, list_enemy.get(i).GetX(), list_enemy.get(i).GetY()));
                    }else if (item_no == 2){
                        list_item.add(new Effect_anime(2, list_enemy.get(i).GetX(), list_enemy.get(i).GetY()));
                    }
                    list_enemy.remove(i);
                    break;
                }
            }
            for(int i=list_pm.size()-1; i>=0; i--){
                inForce_missile pm = list_pm.get(i);
                pm.Update(GameTime);
                if(pm.die == true){
                    list_pm.remove(i); break;
                }
            }
            for(int i=list_npm.size()-1; i>=0; i--){
                outForce_missile npm = list_npm.get(i);
                npm.Update(GameTime);
                if(npm.die == true){
                    list_npm.remove(i);
                    break;
                }
            }
            for(int i=list_np_ef.size()-1; i>=0; i--){
                Effect_anime np_ef = list_np_ef.get(i);
                np_ef.Update(GameTime);
                if (np_ef.getAnimeEnd()){
                    list_np_ef.remove(i);
                }
            }
            for(int i=list_item.size()-1; i>=0; i--){
                Effect_anime np_ef = list_item.get(i);
                np_ef.Update(GameTime);
                if (np_ef.getAnimeEnd() || np_ef.die){
                    list_item.remove(i);
                }
            }
            if (m_player.getHp() <= 0){
                boss_mob.die = true;
                endGame = true;
            }
            TimePurpose();
        }catch (Exception e){
            //ignore
        }
    }

    public void Destroy(){}

    public boolean onTouchEvent(MotionEvent event){
        int ex = (int)event.getX();
        int ey = (int)event.getY();
        int x = m_player.GetX();
        int y = m_player.GetY();
        Rect r = new Rect();

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            r.set(10, b-95, 50, b-55);
            if (r.contains(ex, ey)){
                //좌
                m_player.mov = true;
                m_player.mov_point = 1;
            }
            r.set(80, b-95, 120, b-55);
            if (r.contains(ex, ey)){
                //우
                m_player.mov = true;
                m_player.mov_point = 2;
            }
            r.set(40, b-135, 80, b-95);
            if (r.contains(ex, ey)){
                //상
                m_player.mov = true;
                m_player.mov_point = 3;
            }
            r.set(40, b-55, 80, b-15);
            if (r.contains(ex, ey)){
                //하
                m_player.mov = true;
                m_player.mov_point = 4;
            }
            player_att = true; //플레이어 공격 가능상태
            if (endGame){ //겜 끝나고 화면 누르면 스코어로 ㄱㄱ
                AppManager.getS_instance().setState(new ScoreState());
            }
        }else if (event.getAction() == MotionEvent.ACTION_UP){
            m_player.mov = false;
        }
        return true;
    }
}
