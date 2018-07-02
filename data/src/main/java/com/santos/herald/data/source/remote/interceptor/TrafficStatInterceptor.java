package com.santos.herald.data.source.remote.interceptor;

import android.net.TrafficStats;
import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;
import timber.log.Timber;

public class TrafficStatInterceptor implements Interceptor {

    private int mTrafficTag;
    public TrafficStatInterceptor(int trafficTag) {
        mTrafficTag = trafficTag;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        if (mTrafficTag > 0) {
            TrafficStats.setThreadStatsTag(mTrafficTag);
        } else {
            Timber.w("invalid traffic tag " + mTrafficTag);
        }
        return chain.proceed(chain.request());
    }
}