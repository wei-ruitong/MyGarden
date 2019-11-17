package com.example.mygarden;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.litepal.LitePal;

public class AddEarthActivity extends AppCompatActivity {
private Button button,button1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_earth);
         button=(Button)findViewById(R.id.button_add);
         button1=(Button)findViewById(R.id.createdb);
         button1.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 //LitePal.getDatabase();
                 Ground ground=new Ground();
                 ground.setId(1);
                 ground.setName("地块一");
                 ground.setIp("192.168.1.2");
                 ground.setPort("8000");
                 ground.setFl("稀少");
                 ground.setSd("68%");
                 ground.setYl("中等");
                 ground.save();
             }
         });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddEarthActivity.this,"该功能正在调试中",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}
