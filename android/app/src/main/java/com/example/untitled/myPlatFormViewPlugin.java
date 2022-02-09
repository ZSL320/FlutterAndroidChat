package com.example.untitled;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;

public class myPlatFormViewPlugin extends Activity implements FlutterPlugin , MethodChannel.MethodCallHandler {
    private MethodChannel methodChannel;
    private PlatformViewFactory platformViewFactory;
    private View view;
    private Button button;
    private TextView textView;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttachedToEngine(@NonNull FlutterPlugin.FlutterPluginBinding binding) {
        //创建通道对象，通道名称与Flutter端通道的名称一致
        methodChannel=new MethodChannel(binding.getBinaryMessenger(),"AndroidView");
        //注册此通道的监听
        methodChannel.setMethodCallHandler((MethodChannel.MethodCallHandler) this);
        //创建PlatformView的工厂对象
        platformViewFactory=new PlatformViewFactory(StandardMessageCodec.INSTANCE) {
            @Override
            public PlatformView create(Context context, int viewId, Object args) {
                view=LayoutInflater.from(context).inflate(R.layout.myplatview,null,false);
                button=(Button)view.findViewById(R.id.button);
                textView=(TextView)view.findViewById(R.id.myTxt);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,String> map=new HashMap<>();
                        map.put("send","ComeFormAndroid");
                        methodChannel.invokeMethod("androidSend",map);
                    }
                });
                return new PlatformView() {
                    @Override
                    public View getView() {
                        return view;
                    }

                    @Override
                    public void dispose() {
                    }
                };
            }
        };
        //在Flutter引擎上注册PlatformView的工厂对象
        binding.getPlatformViewRegistry().registerViewFactory("myAndroidView",platformViewFactory);
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPlugin.FlutterPluginBinding binding) {
        methodChannel.setMethodCallHandler(null);
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
       if(call.method.equals("flutterSend")){
           textView.setText("这是flutter端传过来的值:"+call.method);
       }
    }
}