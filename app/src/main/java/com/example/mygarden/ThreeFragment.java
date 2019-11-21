package com.example.mygarden;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class ThreeFragment extends Fragment {
    String []s={"地块一","地块二","地块三","地块四","地块五","地块六"};
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_three,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ListView listView=(ListView)getView().findViewById(R.id.list3);
        ArrayAdapter arrayAdapter=new ArrayAdapter(getContext(),R.layout.item3,R.id.y1,s);
        listView.setAdapter(arrayAdapter);
    }
}
