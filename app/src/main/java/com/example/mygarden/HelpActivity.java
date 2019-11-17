package com.example.mygarden;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class HelpActivity extends AppCompatActivity {
private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        textView=(TextView)findViewById(R.id.text1);
        textView.setText("自动模式：后台根据土地各种状况进行判断，自行进行水肥药配比，然后喷洒。\n" +
                "\n" +
                "手动模式：人为进行配比，手动控制开关。");
    }
}
