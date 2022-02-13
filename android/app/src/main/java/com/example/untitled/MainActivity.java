package com.example.untitled;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.amap.api.maps.MapsInitializer;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public class MainActivity extends FlutterActivity {
    private  EventChannel eventChannel;
    private MethodChannel methodChannel;
    private EventChannel.EventSink eventSink;
    private String path;
    static Activity mainActivity;
    private Map <String ,String>map=new HashMap<>();
    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            MapsInitializer.updatePrivacyAgree(this,true);
            MapsInitializer.updatePrivacyShow(this,true,true);
        }catch (Exception e){}
        Intent intent=getIntent();
        path=intent.getStringExtra("path");
        map.put("message",null);
        map.put("path",null);
        mainActivity=this;
    }

    @Override
    public void configureFlutterEngine(@NonNull @NotNull FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);
        flutterEngine.getPlugins().add(new myPlatFormViewPlugin());
        flutterEngine.getPlugins().add(new hocPlatformPlugin());
        flutterEngine.getPlugins().add(new myAndroidViewToFlutter());
        methodChannel=new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(),"myDemo");
        methodChannel.setMethodCallHandler(new MethodChannel.MethodCallHandler() {
            @Override
            public void onMethodCall(@NonNull @NotNull MethodCall call, @NonNull @NotNull MethodChannel.Result result) {
                if(call.method.equals("open")){
                    startActivity(new Intent(MainActivity.this,MainActivity2.class));
                }else if(call.method.equals("toast")){
                    Toast.makeText(MainActivity.this,"我是来自原生Android的toast",Toast.LENGTH_SHORT).show();
                }else if(call.method.equals("sendToFlutter")){
                    map.put("message","我是来自原生Android的消息展示在flutter页面中");
                    eventSink.success(map);
                }else if(call.method.equals("sendPhotoToFlutter")){
                    map.put("path",path);
                    eventSink.success(map);
                }else if(call.method.equals("openGdClock")){
                    startActivity(new Intent(MainActivity.this,Gd_clock.class));
                }
            }
        });
        eventChannel=new EventChannel(flutterEngine.getDartExecutor().getBinaryMessenger(),"myEvent");
        eventChannel.setStreamHandler(new EventChannel.StreamHandler() {
            @Override
            public void onListen(Object arguments, EventChannel.EventSink events) {
                eventSink=events;
            }

            @Override
            public void onCancel(Object arguments) {

            }
        });
    }


    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("=======onStop");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("=======onResume");
        System.out.println("========="+path);
        if (isNeedCheck) {
            checkPermissions(needPermissions);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("=======onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("=======onDestroy");
    }

    protected String[] needPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };

    private static final int PERMISSON_REQUESTCODE = 0;

    /**
     * 判断是否需要检测，防止不停的弹框
     */
    private boolean isNeedCheck = true;



    /**
     * @since 2.5.0
     */
    private void checkPermissions(String... permissions) {
        List<String> needRequestPermissonList = findDeniedPermissions(permissions);
        if (null != needRequestPermissonList
                && needRequestPermissonList.size() > 0) {
            ActivityCompat.requestPermissions(this,
                    needRequestPermissonList.toArray(
                            new String[needRequestPermissonList.size()]),
                    PERMISSON_REQUESTCODE);
        }
    }

    /**
     * 获取权限集中需要申请权限的列表
     *
     * @param permissions
     * @return
     * @since 2.5.0
     */
    private List<String> findDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissonList = new ArrayList<String>();
        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(this,
                    perm) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    this, perm)) {
                needRequestPermissonList.add(perm);
            }
        }
        return needRequestPermissonList;
    }

    /**
     * 检测是否说有的权限都已经授权
     *
     * @param grantResults
     * @return
     * @since 2.5.0
     */
    private boolean verifyPermissions(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] paramArrayOfInt) {
        if (requestCode == PERMISSON_REQUESTCODE) {
            if (!verifyPermissions(paramArrayOfInt)) {
                showMissingPermissionDialog();
                isNeedCheck = false;
            }
        }
    }

    /**
     * 显示提示信息
     *
     * @since 2.5.0
     */
    private void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("当前应用缺少必要权限。请点击设置权限打开所需权限");

        // 拒绝, 退出应用
        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

        builder.setPositiveButton("设置",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings();
                    }
                });

        builder.setCancelable(false);

        builder.show();
    }

    /**
     * 启动应用的设置
     *
     * @since 2.5.0
     */
    private void startAppSettings() {
        Intent intent = new Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }
}
