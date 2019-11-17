package com.example.mygarden;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AboutWe extends AppCompatActivity {
private TextView textView1;
private Button button1;
    public static final int REQUEST_CALL_PERMISSION = 10111; //拨号请求码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_we);
        textView1=(TextView)findViewById(R.id.text1);
        textView1.setText("软件声明：此软件为自主开发完成\n" +
                "开发团队：河南科技学院物联网软件开发工作室\n" +
                "团队地址：河南省新乡市河南科技学院\n" +
                "联系电话：17637386487\n" +
                "QQ邮箱:873516365@qq.com\n" +
                "QQ邮箱:1214631990@qq.com\n" +
                "QQ:873516365\n" +
                "QQ:1214631990\n");
        button1=(Button)findViewById(R.id.call);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               call("tel:"+"17637386487");
            }
        });

    }
    public boolean checkReadPermission(String string_permission,int request_code) {
        boolean flag = false;
        if (ContextCompat.checkSelfPermission(this, string_permission) == PackageManager.PERMISSION_GRANTED) {//已有权限
            flag = true;
        } else {//申请权限
            ActivityCompat.requestPermissions(this, new String[]{string_permission}, request_code);
        }
        return flag;
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CALL_PERMISSION: //拨打电话
                if (permissions.length != 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {//失败
                    Toast.makeText(this,"请允许拨号权限后再试", Toast.LENGTH_SHORT).show();
                } else {//成功
                    call("tel:"+"17637386487");
                }
                break;
        }
    }

    /**
     * 拨打电话（直接拨打）
     * @param telPhone 电话
     */
    public void call(String telPhone){
        if(checkReadPermission(Manifest.permission.CALL_PHONE,REQUEST_CALL_PERMISSION)){
            Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse(telPhone));
            startActivity(intent);
        }
    }


}
