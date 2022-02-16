package com.example.untitled;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;


public class adapter extends BaseAdapter {
  private List<ListData> data;
  private Context context;

    public adapter(List<ListData> data,Context context) {
        this.data = data;
        this.context=context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MyViewHolder viewHolder = new MyViewHolder();
        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.my_text_view,viewGroup,false);
            viewHolder.textView=view.findViewById(R.id.tv);
            view.setTag(viewHolder);
        }else{
          viewHolder=(MyViewHolder)view.getTag();
        }
        viewHolder.textView.setText(data.get(i).getName());
        return view;
    }
    private final class MyViewHolder{
        TextView textView;
    }
}
