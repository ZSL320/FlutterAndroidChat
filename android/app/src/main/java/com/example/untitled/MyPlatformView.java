package com.example.untitled;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.TextureMapView;

import org.jetbrains.annotations.NotNull;

import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.platform.PlatformView;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static com.example.untitled.MainActivity2.activityA;

public class MyPlatformView implements PlatformView , DefaultLifecycleObserver,
        ActivityPluginBinding.OnSaveInstanceStateListener{
    private MapView mapView;
    private final Context context;
    private Activity activity;
    public MyPlatformView(Bundle savedInstanceState,Context context, Activity activity) {
        this.activity=activity;
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context  //这是关键点
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout=inflater.inflate(R.layout.basicmap_activity,null);
        mapView=(MapView) layout.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
    }

    @Override
    public View getView() {
        return mapView;
    }

    @Override
    public void onCreate(@NonNull @NotNull LifecycleOwner owner) {
    }

    @Override
    public void dispose() {

    }

    @Override
    public void onResume(@NonNull @NotNull LifecycleOwner owner) {
        mapView.onResume();
    }

    @Override
    public void onPause(@NonNull @NotNull LifecycleOwner owner) {
        mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull @NotNull Bundle bundle) {
        mapView.onSaveInstanceState(bundle);
    }

    @Override
    public void onRestoreInstanceState(@Nullable @org.jetbrains.annotations.Nullable Bundle bundle) {
        mapView.onCreate(bundle);
    }
}