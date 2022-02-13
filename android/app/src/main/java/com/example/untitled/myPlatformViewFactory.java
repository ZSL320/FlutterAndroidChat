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
    private final  MethodChannel methodChannel;
    private final Context context;
    public myPlatformViewFactory( Context context, MethodChannel methodChannel) {
        super(StandardMessageCodec.INSTANCE);
        this.context = context;
        this.methodChannel = methodChannel;
    }

    @Override
    public PlatformView create(Context context, int viewId, Object args) {
       return new MyPlatformView(context,methodChannel);
    }
}