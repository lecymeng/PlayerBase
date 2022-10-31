package com.kk.taurus.avplayer.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kk.taurus.avplayer.HomeActivity;
import com.kk.taurus.avplayer.R;
import com.permissionx.guolindev.PermissionX;

/**
 * Created by Taurus on 2018/4/19.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            PermissionX.init(this)
                    .permissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.RECORD_AUDIO,
                            Manifest.permission.MODIFY_AUDIO_SETTINGS)
                    .request((allGranted, grantedList, deniedList) -> {
                        if (allGranted) {
                            intentToMainPage();
                        }
                    });
        }, 500L);
    }

    private void intentToMainPage() {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        finish();
    }

}
