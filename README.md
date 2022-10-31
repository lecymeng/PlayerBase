![image](https://github.com/jiajunhui/PlayerBase/raw/master/screenshot/playerbase_top_slogen.png)

## PlayerBase

### PlayerBase-Core

![maven-central PlayerBase-Core ](https://img.shields.io/maven-central/v/com.weicools/player-base.svg)

### PlayerBase-IjkPlayer

![maven-central PlayerBase-IjkPlayer ](https://img.shields.io/maven-central/v/com.weicools/player-base.svg)

## introduction

**博文地址** ：[Android播放器基础封装库PlayerBase](https://juejin.im/post/5b0d4e6bf265da090f7376d2)

### [提issue注意事项](https://github.com/jiajunhui/PlayerBase/wiki/Issue-Attention)

### [有问题先看介绍和wiki文档](https://github.com/jiajunhui/PlayerBase/wiki)

### [项目介绍](https://github.com/jiajunhui/PlayerBase/wiki/Related-introduction)

### [Demo下载](http://d.firim.info/lmhz)

## 使用及依赖

### 请求的权限

如果需要监听网络状态，则需要添加权限：

```xml

<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

### 集成

#### 使用 MediaPlayer + ExoPlayer

```groovy
buildTypes {
    // ExoPlayer require Java8
    compileOptions {
        targetCompatibility JavaVersion.VERSION_1_8
    }
}

dependencies {
    // 包含 ExoPlayer & MediaPlayer 解码
    implementation "com.weicools:player-base:3.5.0"
}
```

#### 使用 MediaPlayer + ExoPlayer + IjkPlayer

```groovy
buildTypes {
    // ExoPlayer require Java8
    compileOptions {
        targetCompatibility JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation "com.weicools:player-base:3.5.0"
    implementation "com.weicools:player-ijkplayer:3.5.0"
    // ijk 官方的解码库依赖，较少格式版本且不支持 HTTPS
    implementation 'tv.danmaku.ijk.media:ijkplayer-armv7a:0.8.8'
    // Other ABIs: optional
    implementation 'tv.danmaku.ijk.media:ijkplayer-armv5:0.8.8'
    implementation 'tv.danmaku.ijk.media:ijkplayer-arm64:0.8.8'
    implementation 'tv.danmaku.ijk.media:ijkplayer-x86:0.8.8'
    implementation 'tv.danmaku.ijk.media:ijkplayer-x86_64:0.8.8'
}
```

### 使用

#### 初始化

```java
public class App extends Application {

    @Override
    public void onCreate() {

        // Default plan id = 0 // Decoder = MediaPlayer
        PlayerConfig.addDecoderPlan(new DecoderPlan(PLAN_ID_EXO, ExoMediaPlayer.class.getName(), "ExoPlayer"));
        PlayerConfig.addDecoderPlan(new DecoderPlan(PLAN_ID_IJK, IjkPlayer.class.getName(), "IjkPlayer")); // if add ijk libs
        PlayerConfig.setDefaultPlanId(0); // Use MediaPlayer
        // PlayerConfig.setDefaultPlanId(PLAN_ID_EXO); // Use ExoPlayer
        // PlayerConfig.setDefaultPlanId(PLAN_ID_IJK); // Use IjkPlayer
        PlayerLibrary.init(this);

        // 下面的初始化方式可以简化解码器相关设置
        // ExoMediaPlayer.init(this);
        // IjkPlayer.init(this); // if add ijk libs

        // 如果您想使用默认的网络状态事件生产者，请添加此行配置。
        // 并需要添加权限 android.permission.ACCESS_NETWORK_STATE
        // PlayerConfig.setUseDefaultNetworkEventProducer(true);

        // 开启播放记录
        PlayerConfig.playRecord(true);
        PlayRecordManager.RecordConfig recordConfig = new PlayRecordManager.RecordConfig.Builder()
                .setMaxRecordCount(100)
                .build();
        PlayRecordManager.setRecordConfig(recordConfig);
    }
}
```

## 交流

联系方式：junhui_jia@163.com

QQ群：600201778

<img src="https://github.com/jiajunhui/PlayerBase/raw/master/screenshot/qrcode_qq_group.jpg" width="270" height="370">

## License

```license
Copyright 2017 jiajunhui<junhui_jia@163.com>

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0
   
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
