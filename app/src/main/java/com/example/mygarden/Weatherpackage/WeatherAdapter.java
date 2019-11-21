package com.example.mygarden.Weatherpackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mygarden.R;

import java.util.List;

public class WeatherAdapter extends ArrayAdapter<Future_weather> {
private TextView data,qingyin,tem,wind;
private int resourceId;
public WeatherAdapter(Context context, int textViewResourceId,
                      List<Future_weather> objects){
    super(context,textViewResourceId,objects);
    resourceId=textViewResourceId;
}
public View getView(int position, View convertView, ViewGroup parent){

    Future_weather future_weather=getItem(position);
    View view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
    data=(TextView)view.findViewById(R.id.data);
    qingyin=(TextView)view.findViewById(R.id.qingyin);
    tem=(TextView)view.findViewById(R.id.tmp);
    wind=(TextView)view.findViewById(R.id.fengji);
    data.setText(future_weather.getData());
    qingyin.setText(future_weather.getQingyin());
    tem.setText(future_weather.getTem());
    wind.setText(future_weather.getWind());

    return  view;
}

}
