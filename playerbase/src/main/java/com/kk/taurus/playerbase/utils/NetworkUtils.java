/*
 * Copyright 2017 jiajunhui<junhui_jia@163.com>
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.kk.taurus.playerbase.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import static com.kk.taurus.playerbase.config.PConst.NETWORK_STATE_CONNECTING;
import static com.kk.taurus.playerbase.config.PConst.NETWORK_STATE_MOBILE;
import static com.kk.taurus.playerbase.config.PConst.NETWORK_STATE_NONE;
import static com.kk.taurus.playerbase.config.PConst.NETWORK_STATE_WIFI;

/**
 * Created by Taurus on 2018/5/27.
 */
public class NetworkUtils {

    /**
     * get current network connected type
     *
     * @param context context
     * @return int
     */
    public static int getNetworkState(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE); // 获取网络服务
        if (null == connManager) {
            return NETWORK_STATE_NONE;
        }
        @SuppressLint("MissingPermission")
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return NETWORK_STATE_NONE;
        }else {
            NetworkInfo.State networkInfoState = networkInfo.getState();
            if(networkInfoState == NetworkInfo.State.CONNECTING){
                return NETWORK_STATE_CONNECTING;
            }
            if(!networkInfo.isAvailable()){
                return NETWORK_STATE_NONE;
            }
        }

        @SuppressLint("MissingPermission") // require android.permission.ACCESS_NETWORK_STATE
        NetworkInfo wifiInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (null != wifiInfo) {
            NetworkInfo.State state = wifiInfo.getState();
            if (null != state) {
                if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
                    return NETWORK_STATE_WIFI;
                }
            }
        }

        return NETWORK_STATE_MOBILE;
    }

    public static boolean isMobile(int networkState){
        return networkState > NETWORK_STATE_WIFI;
    }

    /**
     * whether or not network connect.
     *
     * @param context context
     * @return true/false
     */
    public static boolean isNetConnected(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            @SuppressLint("MissingPermission")
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * is wifi ?
     *
     * @param context context
     * @return true/false
     */
    public static synchronized boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            @SuppressLint("MissingPermission")
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null) {
                int networkInfoType = networkInfo.getType();
                if (networkInfoType == ConnectivityManager.TYPE_WIFI || networkInfoType == ConnectivityManager.TYPE_ETHERNET) {
                    return networkInfo.isConnected();
                }
            }
        }
        return false;
    }

}
