package com.example.untitled;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

public class myListView extends Activity {
    private ListView listView;
    private List <ListData> data=new ArrayList<>();
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list_view);
        for(int i =0;i<20;i++){
            ListData listData=new ListData();
            listData.setName("魁拔"+i);
            data.add(listData);
        }
        listView=  findViewById(R.id.lv);
        listView.setAdapter(new adapter(data,this));
    }
}