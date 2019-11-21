package com.example.mygarden;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.mygarden.Picture.GetEverydayBingAdress;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class Login extends AppCompatActivity {
    private TextView tv_version;
    private ImageView iv_background;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private EditText accountEdit;
    private EditText passwordEdit;
    private CheckBox rememberPass;
    private ImageView bingPicTmg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>=21){
            View decorView=getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_login);
        prefs= PreferenceManager.getDefaultSharedPreferences(this);
        accountEdit=(EditText)findViewById(R.id.edit1);
        passwordEdit=(EditText)findViewById(R.id.edit2);
        rememberPass=(CheckBox)findViewById(R.id.box);
      //  bingPicTmg=(ImageView)findViewById(R.id.bing);
       /* Glide.with(Login.this)
                .load("http://s.cn.bing.net/th?id=OHR.IchetuckneeRiver_ZH-CN1410417151_1920x1080.jpg&rf=LaDigue_1920x1080.jpg&pid=hp")
                //设置占位图
                .placeholder(R.mipmap.ic_launcher)
                //加载错误图
                .error(R.mipmap.ic_launcher)
                //磁盘缓存的处理
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(bingPicTmg);*/
/*String bingPic=prefs.getString("bing_pic",null);
        if(bingPic!=null){
            Glide.with(this).
                    load(bingPic) .
                    into(bingPicTmg);
        }else{
            loadBingPic();
    }*/




        final boolean isRememeber=prefs.getBoolean("rememeber_password",false);
        if(isRememeber){
            String account=prefs.getString("account","");
            String password=prefs.getString("password","");
            accountEdit.setText(account);
            passwordEdit.setText(password);
            rememberPass.setChecked(true);

        }


        Button button=(Button)findViewById(R.id.login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputMethodManager=(InputMethodManager)getApplicationContext().
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(passwordEdit.getWindowToken(),0);

                String account=accountEdit.getText().toString();
                String password=passwordEdit.getText().toString();
                if(account.equals("123456")&&password.equals("123456")){
                    editor=prefs.edit();
                    if(rememberPass.isChecked()){
                        editor.putBoolean("rememeber_password",true);
                        editor.putString("account",account);
                        editor.putString("password",password);
                    }else{
                        editor.clear();
                    }editor.apply();
                    try {
                        Thread.currentThread().sleep(500);//阻断1秒
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(Login.this,"登录成功",Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(Login.this,MainActivity.class);
                   startActivity(i);
                   /* Intent intent3=new Intent(Login.this,MainActivity.class);
                    startActivity(intent3, ActivityOptions.makeSceneTransitionAnimation(Login.this).toBundle());
*/
                    finish();
                }else{
                    Toast.makeText(Login.this,"账号或者是密码错误,请重新登录",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
private void loadBingPic(){

        String requestBingPic="http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    final String bingPic=response.body().string();
                    SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(Login.this
                        ).edit();
                    editor.putString("bing_pic",bingPic);
                    editor.apply();
                    runOnUiThread(new Runnable() {
                            @Override
                                         public void run() {

                                Glide.with(Login.this)
                                        .load(bingPic)
                                        //设置占位图
                                        .placeholder(R.mipmap.ic_launcher)
                                        //加载错误图
                                        .error(R.mipmap.ic_launcher)
                                        //磁盘缓存的处理
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(bingPicTmg);
    }
});
            }
        });
}

}
