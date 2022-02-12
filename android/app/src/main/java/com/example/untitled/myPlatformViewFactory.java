package com.example.untitled;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MessageCodec;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;

public class myPlatformViewFactory extends PlatformViewFactory {
    private final Activity activity;
    private final Lifecycle lifecycle;
    public myPlatformViewFactory( Activity activity, Lifecycle lifecycle) {
        super(StandardMessageCodec.INSTANCE);
        this.activity = activity;
        this.lifecycle = lifecycle;
    }

    @Override
    public PlatformView create(Context context, int viewId, Object args) {
        Bundle savedInstanceState = new Bundle();
       return new MyPlatformView(savedInstanceState,context,activity);
    }
}