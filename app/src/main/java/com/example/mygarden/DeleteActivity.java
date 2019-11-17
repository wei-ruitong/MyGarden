package com.example.mygarden;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hikvision.netsdk.NET_DVR_VIDEO_WALL_INFO;

public class DeleteActivity extends AppCompatActivity {
private EditText editText1;
private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        editText1=(EditText)findViewById(R.id.edit1);
        button=(Button)findViewById(R.id.delete);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DeleteActivity.this,"您删除了"+editText1.getText(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
