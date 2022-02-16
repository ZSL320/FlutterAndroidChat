package com.example.untitled;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import org.jetbrains.annotations.NotNull;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.platform.PlatformView;
import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class MyPlatformView implements PlatformView , DefaultLifecycleObserver,
        ActivityPluginBinding.OnSaveInstanceStateListener{
    private MapView mapView;
    private final Context context;
    private Activity activity;
    public AMapLocationClient client=null;
    private AMapLocationClientOption option=null;
    private AMap map;
    private TextView tvAdd;
    private View myView;
    private MethodChannel methodChannel;
    private Button myButton;


    private ListView listView;
    private View myListDataView;
    private List <ListData> data=new ArrayList<>();
    public MyPlatformView(Context context, MethodChannel methodChannel) {
        this.context = context;
        this.methodChannel=methodChannel;
        //listview
//        myListDataView=LayoutInflater.from(context).inflate(R.layout.activity_my_list_view,null);
//        for(int i =0;i<20;i++){
//            ListData listData=new ListData();
//            listData.setName("魁拔"+i);
//            data.add(listData);
//        }
//        listView= myListDataView.findViewById(R.id.lv);
//        listView.setAdapter(new adapter(data,context));
        //mapview 高德地图mapview
        myView=LayoutInflater.from(context).inflate(R.layout.basicmap_activity,null);
        mapView=myView.findViewById(R.id.map);
        mapView.onCreate(new Bundle());
        myButton=(Button)myView.findViewById(R.id.myButton);
        myButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        methodChannel.invokeMethod("SendMessageToFlutter","你好，我是来自Android端的消息");
                        myButton.setText("打卡成功");
                    }
                }
        );
    }

    @Override
    public View getView() {
        return myView;
    }
    private void initListener() {
        client.setLocationListener(new AMapLocationListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if(aMapLocation!=null){
                    if(aMapLocation.getErrorCode()==0){
                        aMapLocation.getLocationType();//获取当前结果来源,如网络定位.GPS定位
                        double lat=aMapLocation.getLatitude();//获取纬度
                        double lon=aMapLocation.getLongitude();//获取经度
                        aMapLocation.getAccuracy();//获取精度信息
//                        option.setOnceLocation(true);
                        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date=new Date(aMapLocation.getTime());//定位时间
                        LatLng latlon=new LatLng(lat,lon);
                        MarkerOptions marker=new MarkerOptions();
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlon,15));//显示在指定位置
                        marker.position(latlon);
                        marker.title("当前位置");
                        marker.visible(true);
                        BitmapDescriptor bitmapDescriptor= BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(Resources.getSystem(),R.drawable.launch_background));
                        marker.icon(bitmapDescriptor);
                        map.addMarker(marker);
                        tvAdd.setText("当前位置:"+aMapLocation.getSpeed()+"         "+format.format(date));
                    }else{
//							Toast.makeText(LocationGPSActivity.this,"定位失败",Toast.LENGTH_SHORT).show();
                    }
                }
                map.setMyLocationEnabled(true);
            }
        });
    }

    private void initLocation() {
        option=new AMapLocationClientOption();
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        option.setNeedAddress(true);
        option.setInterval(60000);
        client.setLocationOption(option);
        client.startLocation();
    }
    
    private void initView() throws Exception {
        client=new AMapLocationClient(context.getApplicationContext());
        initListener();
        map=mapView.getMap();
        map.setMapType(AMap.MAP_TYPE_NORMAL);
        initLocation();
        map.setLocationSource((LocationSource) this);// 设置定位监听
        map.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        map.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
    }
    @Override
    public void onCreate(@NonNull @NotNull LifecycleOwner owner) {
    }

    @Override
    public void dispose() {
        client.onDestroy();
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

    @Override
    public void onDestroy(@NonNull @NotNull LifecycleOwner owner) {
    }
}