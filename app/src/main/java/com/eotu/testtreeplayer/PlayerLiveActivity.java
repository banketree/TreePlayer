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
import com.eotu.testtreeplayer.module.ApiServiceUtils;
import com.eotu.testtreeplayer.utlis.MediaUtils;
import com.eotu.treeplayer.listener.OnShowThumbnailListener;
import com.eotu.treeplayer.widget.PlayStateParams;
import com.eotu.treeplayer.widget.PlayerView;

import java.util.List;


public class PlayerLiveActivity extends Activity {

    private PlayerView player,player1,player2;
    private Context mContext;
    private View rootView;
    private List<LiveBean> list;
    private String url = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
    private String title = "标题";
    private PowerManager.WakeLock wakeLock;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            if (list.size() > 1) {
//                url = list.get(1).getLiveStream();
//                title = list.get(1).getNickname();
//            }
            player.setPlaySource(url)
                    .startPlay();
            player1.setPlaySource(url)
                    .startPlay();
            player2.setPlaySource(url)
                    .startPlay();

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        rootView = getLayoutInflater().from(this).inflate(R.layout.player_view_player, null);
        setContentView(rootView);

        /**常亮*/
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "liveTAG");
        wakeLock.acquire();

        player = new PlayerView(this, rootView,"video_view")
                .setTitle(title)
                .setScaleType(PlayStateParams.fitparent)
                .hideMenu(true)
                .hideSteam(true)
                .setForbidDoulbeUp(true)
                .hideCenterPlayer(true)
                .hideControlPanl(true)
                .showThumbnail(new OnShowThumbnailListener() {
                    @Override
                    public void onShowThumbnail(ImageView ivThumbnail) {
//                        Glide.with(mContext)
//                                .load("http://pic2.nipic.com/20090413/406638_125424003_2.jpg")
//                                .placeholder(R.color.cl_default)
//                                .error(R.color.cl_error)
//                                .into(ivThumbnail);
                    }
                });
        player1 = new PlayerView(this, rootView,"video_view1")
                .setTitle(title)
                .setScaleType(PlayStateParams.fitparent)
                .hideMenu(true)
                .hideSteam(true)
                .setForbidDoulbeUp(true)
                .hideCenterPlayer(true)
                .hideControlPanl(true)
                .showThumbnail(new OnShowThumbnailListener() {
                    @Override
                    public void onShowThumbnail(ImageView ivThumbnail) {
//                        Glide.with(mContext)
//                                .load("http://pic2.nipic.com/20090413/406638_125424003_2.jpg")
//                                .placeholder(R.color.cl_default)
//                                .error(R.color.cl_error)
//                                .into(ivThumbnail);
                    }
                });
        player2 = new PlayerView(this, rootView,"video_view2")
                .setTitle(title)
                .setScaleType(PlayStateParams.fitparent)
                .hideMenu(true)
                .hideSteam(true)
                .setForbidDoulbeUp(true)
                .hideCenterPlayer(true)
                .hideControlPanl(true)
                .showThumbnail(new OnShowThumbnailListener() {
                    @Override
                    public void onShowThumbnail(ImageView ivThumbnail) {
//                        Glide.with(mContext)
//                                .load("http://pic2.nipic.com/20090413/406638_125424003_2.jpg")
//                                .placeholder(R.color.cl_default)
//                                .error(R.color.cl_error)
//                                .into(ivThumbnail);
                    }
                });

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
        if (player != null) {
            player.onPause();
        }

        if (player1 != null) {
            player1.onPause();
        }

        if (player2 != null) {
            player2.onPause();
        }
        MediaUtils.muteAudioFocus(mContext, true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (player != null) {
            player.onResume();
        }

        if (player1 != null) {
            player1.onResume();
        }

        if (player2 != null) {
            player2.onResume();
        }
        MediaUtils.muteAudioFocus(mContext, false);
        if (wakeLock != null) {
            wakeLock.acquire();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.onDestroy();
        }

        if (player1 != null) {
            player1.onDestroy();
        }

        if (player2 != null) {
            player2.onDestroy();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (player != null) {
            player.onConfigurationChanged(newConfig);
        }

        if (player1 != null) {
            player1.onConfigurationChanged(newConfig);
        }

        if (player2 != null) {
            player2.onConfigurationChanged(newConfig);
        }
    }

    @Override
    public void onBackPressed() {
        if (player != null && player.onBackPressed()) {
            return;
        }
        super.onBackPressed();
        if (wakeLock != null) {
            wakeLock.release();
        }
    }

}
