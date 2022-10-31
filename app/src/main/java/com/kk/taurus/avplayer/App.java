package com.kk.taurus.avplayer;

import android.app.Application;

import com.kk.taurus.exoplayer.ExoMediaPlayer;
import com.kk.taurus.ijkplayer.IjkPlayer;
import com.kk.taurus.playerbase.config.PlayerConfig;
import com.kk.taurus.playerbase.config.PlayerLibrary;
import com.kk.taurus.playerbase.entity.DecoderPlan;
import com.kk.taurus.playerbase.log.PlayerLog;
import com.kk.taurus.playerbase.record.PlayRecordManager;

/**
 * Created by Taurus on 2018/4/15.
 */

public class App extends Application {

    public static final int PLAN_ID_IJK = 1;
    public static final int PLAN_ID_EXO = 2;

    private static App instance;

    public static boolean ignoreMobile;

    public static App get() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        PlayerLog.ENABLE = true;

        // Default plan id = 0 // Decoder = MediaPlayer
        PlayerConfig.addDecoderPlan(new DecoderPlan(PLAN_ID_EXO, ExoMediaPlayer.class.getName(), "ExoPlayer"));
        PlayerConfig.addDecoderPlan(new DecoderPlan(PLAN_ID_IJK, IjkPlayer.class.getName(), "IjkPlayer")); // if add ijk libs
        PlayerConfig.setDefaultPlanId(0); // Use MediaPlayer
        // PlayerConfig.setDefaultPlanId(PLAN_ID_EXO); // Use ExoPlayer
        // PlayerConfig.setDefaultPlanId(PLAN_ID_IJK); // Use IjkPlayer
        PlayerLibrary.init(this);

        // 下面的初始化方式可以简化解码器相关设置
        // ExoMediaPlayer.init(this);
        // IjkPlayer.init(this);

        // use default NetworkEventProducer.
        // PlayerConfig.setUseDefaultNetworkEventProducer(true);

        PlayerConfig.playRecord(true);
        PlayRecordManager.RecordConfig recordConfig = new PlayRecordManager.RecordConfig.Builder()
                .setMaxRecordCount(100)
                .build();
        PlayRecordManager.setRecordConfig(recordConfig);
    }
}
