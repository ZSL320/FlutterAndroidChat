package com.example.untitled;

import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public class MainActivity extends FlutterActivity {
    private  EventChannel eventChannel;
    private MethodChannel methodChannel;
    private EventChannel.EventSink eventSink;

    @Override
    public void configureFlutterEngine(@NonNull @NotNull FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);
        methodChannel=new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(),"myDemo");
        methodChannel.setMethodCallHandler(new MethodChannel.MethodCallHandler() {
            @Override
            public void onMethodCall(@NonNull @NotNull MethodCall call, @NonNull @NotNull MethodChannel.Result result) {
                if(call.method.equals("open")){
                    startActivity(new Intent(MainActivity.this,MainActivity2.class));
                }else if(call.method.equals("toast")){
                    Toast.makeText(MainActivity.this,"我是来自原生Android的toast",Toast.LENGTH_SHORT).show();
                }else if(call.method.equals("sendToFlutter")){
                    eventSink.success("我是来自原生Android的消息展示在flutter页面中");
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
}
