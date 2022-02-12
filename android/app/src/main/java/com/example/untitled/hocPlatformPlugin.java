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

import static com.example.untitled.MainActivity2.activityA;

public class hocPlatformPlugin extends Activity implements FlutterPlugin, ActivityAware  {
    private FlutterPluginBinding pluginBinding;
    private Lifecycle lifecycle;
    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        pluginBinding = flutterPluginBinding;
    }

    @Override
    public void onDetachedFromEngine(@NonNull @NotNull FlutterPlugin.FlutterPluginBinding binding) {
  
    }


    @Override
    public void onAttachedToActivity(@NonNull @NotNull ActivityPluginBinding binding) {
        HiddenLifecycleReference reference = (HiddenLifecycleReference) binding.getLifecycle();
        lifecycle = reference.getLifecycle();
        pluginBinding.getPlatformViewRegistry().registerViewFactory(
                "view_zsl",
                new myPlatformViewFactory( binding.getActivity(), lifecycle));
        
    }


    @Override
    public void onDetachedFromActivityForConfigChanges() {

    }

    @Override
    public void onReattachedToActivityForConfigChanges(@NonNull @NotNull ActivityPluginBinding binding) {
        HiddenLifecycleReference reference = (HiddenLifecycleReference) binding.getLifecycle();
        lifecycle = reference.getLifecycle();
        //lifecycle = binding.getLifecycle();
        lifecycle.addObserver((LifecycleObserver) this);
    }

    @Override
    public void onDetachedFromActivity() {
        lifecycle.removeObserver((LifecycleObserver) this);
    }
}