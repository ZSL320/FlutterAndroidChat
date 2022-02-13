package com.example.untitled;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;

import android.app.Activity;
import android.os.Bundle;

import org.jetbrains.annotations.NotNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.embedding.engine.plugins.lifecycle.HiddenLifecycleReference;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.StandardMessageCodec;


public class hocPlatformPlugin extends Activity implements FlutterPlugin, MethodChannel.MethodCallHandler  {
   private MethodChannel methodChannel;
   private myPlatformViewFactory platformViewFactory;

    @Override
    public void onAttachedToEngine(@NonNull @NotNull FlutterPlugin.FlutterPluginBinding binding) {
        methodChannel=new MethodChannel(binding.getBinaryMessenger(),"viewPlatform");
        methodChannel.setMethodCallHandler(this::onMethodCall);
        platformViewFactory=new myPlatformViewFactory(binding.getApplicationContext(),methodChannel);
        binding.getPlatformViewRegistry().registerViewFactory("view_zsl",platformViewFactory);
    }

    @Override
    public void onDetachedFromEngine(@NonNull @NotNull FlutterPlugin.FlutterPluginBinding binding) {

    }

    @Override
    public void onMethodCall(@NonNull @NotNull MethodCall call, @NonNull @NotNull MethodChannel.Result result) {
        System.out.println(6666666);
        System.out.println(call.method);
        System.out.println(call.arguments);
    }
}