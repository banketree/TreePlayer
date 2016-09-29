package com.eotu.testtreeplayer;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.eotu.testtreeplayer.bean.LiveBean;
import com.eotu.testtreeplayer.utlis.MediaUtils;
import com.eotu.treeplayer.VideoView;
import com.eotu.treeplayer.core.PlayStateParams;

import java.util.List;


public class PlayerLiveActivity extends Activity {

    private Context mContext;
    private List<LiveBean> list;
    private String url = "rtmp://live.hkstv.hk.lxdns.com/live/hks";
    private String title = "标题";
    private PowerManager.WakeLock wakeLock;
    private VideoView mPlayer1, mPlayer2, mPlayer3;


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            if (list.size() > 1) {
//                url = list.get(1).getLiveStream();
//                title = list.get(1).getNickname();
//            }

            mPlayer1.setVideoPath(url);
            mPlayer2.setVideoPath(url);
            mPlayer3.setVideoPath(url);

            mPlayer1.start();
//            mPlayer2.start();
//            mPlayer3.start();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        setContentView(R.layout.activity_h);

        /**常亮*/
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "liveTAG");
        wakeLock.acquire();

        mPlayer1 = (VideoView) findViewById(R.id.VideoView1);
        mPlayer2 = (VideoView) findViewById(R.id.VideoView2);
        mPlayer3 = (VideoView) findViewById(R.id.VideoView3);

        new Thread() {
            @Override
            public void run() {
                //这里多有得罪啦，网上找的直播地址，如有不妥之处，可联系删除
//                list = ApiServiceUtils.getLiveList();
                mHandler.sendEmptyMessage(0);
            }
        }.start();

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mPlayer1 != null) {
            mPlayer1.onPause();
        }
        if (mPlayer2 != null) {
            mPlayer2.onPause();
        }
        if (mPlayer3 != null) {
            mPlayer3.onPause();
        }

        MediaUtils.muteAudioFocus(mContext, true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPlayer1 != null) {
            mPlayer1.onResume();
        }

        if (mPlayer2 != null) {
            mPlayer2.onResume();
        }

        if (mPlayer3 != null) {
            mPlayer3.onResume();
        }
        MediaUtils.muteAudioFocus(mContext, false);
        if (wakeLock != null) {
            wakeLock.acquire();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPlayer1 != null) {
            mPlayer1.stopPlayback();
        }

        if (mPlayer2 != null) {
            mPlayer2.stopPlayback();
        }

        if (mPlayer3 != null) {
            mPlayer3.stopPlayback();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (wakeLock != null) {
            wakeLock.release();
        }
    }

}
