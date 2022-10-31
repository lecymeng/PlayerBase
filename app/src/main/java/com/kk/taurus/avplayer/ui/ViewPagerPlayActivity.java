package com.kk.taurus.avplayer.ui;

import android.Manifest;
import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.kk.taurus.avplayer.R;
import com.kk.taurus.avplayer.bean.VideoBean;
import com.kk.taurus.playerbase.assist.RelationAssist;
import com.kk.taurus.playerbase.entity.DataSource;
import com.kk.taurus.playerbase.event.EventKey;
import com.kk.taurus.playerbase.event.OnPlayerEventListener;
import com.kk.taurus.playerbase.log.PlayerLog;
import com.permissionx.guolindev.PermissionX;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerPlayActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private ViewPager.OnPageChangeListener mOnPageChangeListener;
    private TextView mTvTitle;

    private RelationAssist mRelationAssist;

    private List<VideoBean> mItems = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_play);
        mViewPager = findViewById(R.id.viewPager);
        mTvTitle = findViewById(R.id.tv_title_darkMode);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        findViewById(R.id.iv_back_darkMode).setOnClickListener(v -> finish());

        mRelationAssist = new RelationAssist(this);
        mRelationAssist.setOnPlayerEventListener((eventCode, bundle) -> {
            if (eventCode == OnPlayerEventListener.PLAYER_EVENT_ON_TIMER_UPDATE) {
                if (bundle != null) {
                    PlayerLog.d("timerUpdate", "curr = " + bundle.getInt(EventKey.INT_ARG1) + ",duration = " + bundle.getInt(EventKey.INT_ARG2));
                }
            }
        });
        mRelationAssist.getSuperContainer().setBackgroundColor(Color.BLACK);

        mViewPager.addOnPageChangeListener(mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                playPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        PermissionX.init(this)
                .permissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .request((allGranted, grantedList, deniedList) -> {
                    if (allGranted) {
                        permissionSuccess();
                    } else {
                        permissionFailure();
                    }
                });
    }

    private void playPosition(int position) {
        VideoBean bean = mItems.get(position);
        mTvTitle.setText(bean.getDisplayName());
        FrameLayout container = mViewPager.findViewWithTag(bean.getPath());
        mRelationAssist.attachContainer(container, true);
        mRelationAssist.setDataSource(new DataSource(bean.getPath()));
        mRelationAssist.play();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mRelationAssist.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRelationAssist.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRelationAssist.destroy();
    }


    public void permissionSuccess() {
        // MediaLoader.getLoader().loadVideos(this, new OnVideoLoaderCallBack() {
        //     @Override
        //     public void onResult(VideoResult result) {
        //         List<VideoItem> items = result.getItems();
        //         if (items == null || items.size() <= 0) {
        //             Toast.makeText(ViewPagerPlayActivity.this, "无本地视频", Toast.LENGTH_SHORT).show();
        //             return;
        //         }
        //         Collections.sort(items, new MCompartor());
        //         mItems.addAll(DataUtils.transList(items));
        //         PlayPagerAdapter pagerAdapter = new PlayPagerAdapter(ViewPagerPlayActivity.this, mItems);
        //         mViewPager.setAdapter(pagerAdapter);
        //         mViewPager.post(new Runnable() {
        //             @Override
        //             public void run() {
        //                 if (mOnPageChangeListener != null) {
        //                     mOnPageChangeListener.onPageSelected(0);
        //                 }
        //             }
        //         });
        //     }
        // });
    }

    public void permissionFailure() {
        Toast.makeText(this, "权限拒绝,无法正常使用", Toast.LENGTH_LONG).show();
    }

    // public static class MCompartor implements Comparator<VideoItem> {
    //     @Override
    //     public int compare(VideoItem lhs, VideoItem rhs) {
    //         if (lhs.getSize() > rhs.getSize()) {
    //             return -1;
    //         }
    //         if (lhs.getSize() < rhs.getSize()) {
    //             return 1;
    //         }
    //         return 0;
    //     }
    // }
}
