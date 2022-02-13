package com.example.untitled;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;

public class myAndroidViewToFlutter extends Activity implements FlutterPlugin{
    private PlatformViewFactory myPlatformFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttachedToEngine(@NonNull @NotNull FlutterPlugin.FlutterPluginBinding binding) {
        myPlatformFactory=new PlatformViewFactory(StandardMessageCodec.INSTANCE) {
            @Override
            public PlatformView create(Context context, int viewId, Object args) {
                return new myAndroidViewPlatform(context);
            }
        };
        binding.getPlatformViewRegistry().registerViewFactory("GaoDeMapView",myPlatformFactory);
    }

    @Override
    public void onDetachedFromEngine(@NonNull @NotNull FlutterPlugin.FlutterPluginBinding binding) {

    }
}