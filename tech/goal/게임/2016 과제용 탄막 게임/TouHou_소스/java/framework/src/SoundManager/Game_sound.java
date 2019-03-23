package framework.src.SoundManager;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;

/**
 * Created by junminwoo on 2016-06-08.
 */
public class Game_sound {
    private SoundPool soundpool;
    private HashMap soundHash;
    private AudioManager audioManager;
    private Context act_m;

    public void Init(Context con){
        soundpool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
        soundHash = new HashMap();
        audioManager = (AudioManager) con.getSystemService(Context.AUDIO_SERVICE);
        act_m = con;
    }
    public void AddSound(int idx, int s_id){
        int id = soundpool.load(act_m, s_id, 1);
        soundHash.put(idx, id);
    }
    public void Play(int idx){
        float st_vol = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        float sm_vol = st_vol / audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        soundpool.play((Integer) soundHash.get(idx), st_vol, sm_vol, 1, 0, 1f);
    }
    public void playlooped(int idx){
        float st_vol = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        st_vol = st_vol / audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        soundpool.play((Integer) soundHash.get(idx), st_vol, st_vol, 1, -1, 1f);
    }
    public static Game_sound S_Instance;
    public static Game_sound getS_Instance(){
        if (S_Instance == null){ S_Instance = new Game_sound(); }
        return S_Instance;
    }
}
