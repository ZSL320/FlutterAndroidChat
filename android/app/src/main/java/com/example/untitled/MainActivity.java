package com.example.untitled;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
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
}
