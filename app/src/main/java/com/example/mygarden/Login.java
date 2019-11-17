package com.example.mygarden;

import android.app.ActivityOptions;
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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private EditText accountEdit;
    private EditText passwordEdit;
    private CheckBox rememberPass;
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
}
