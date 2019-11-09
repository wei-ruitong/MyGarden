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


public class OneFragment extends Fragment {
    String []s={"地块一","地块二","地块三","地块四","地块五"};
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_one,container,false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ListView listView=(ListView)getView().findViewById(R.id.list1);
        ArrayAdapter arrayAdapter=new ArrayAdapter(getContext(),R.layout.item1,R.id.dikuai,s);
        listView.setAdapter(arrayAdapter);

    }
}