package com.example.mygarden;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class TwoFragment extends Fragment {
    private SwipeRefreshLayout swipeRefreshLayout;
    String s[]={"今天","明天","后天"};
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_two,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout=(SwipeRefreshLayout)getView().findViewById(R.id.swipe);

        final ListView listView=(ListView)getView().findViewById(R.id.list2);
        ArrayAdapter arrayAdapter=new ArrayAdapter(getContext(),R.layout.item2,R.id.t1,s);
        listView.setAdapter(arrayAdapter);
        swipeRefreshLayout.setColorSchemeResources(R.color.swipeColor1,R.color.swipeColor2,R.color.swipeColor3,
                R.color.swipeColor4,R.color.swipeColor5);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //需要进行的操作

                //工作完成后进行关闭
              // swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getContext(),"nihao",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
