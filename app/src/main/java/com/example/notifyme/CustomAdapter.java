package com.example.notifyme;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.List;



public class CustomAdapter extends ArrayAdapter<NotiFormat>{
    public CustomAdapter(@NonNull Context context, @LayoutRes int resource, List<NotiFormat> objects) {
        super(context, resource,objects);


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null){
            convertView=((Activity)getContext()).getLayoutInflater().inflate(R.layout.custom_row,parent,false);

        }

        TextView title=(TextView)convertView.findViewById(R.id.title);
        TextView content=(TextView)convertView.findViewById(R.id.content);

        TextView sendername=(TextView)convertView.findViewById(R.id.sendername);

        NotiFormat notification=getItem(position);

        title.setText(notification.getTitle());
        content.setText(notification.getContent());
        sendername.setText("-"+notification.getSendername());



        return convertView;
    }
}
