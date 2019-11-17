package com.example.mygarden;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class KnowledgeActivity extends AppCompatActivity {
private TextView textView1;
private Button button1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowledge);
        textView1=(TextView)findViewById(R.id.text1);
        button1=(Button)findViewById(R.id.button1);
        textView1.setText("一、黑星病  主要为害苹果、梨、桃、柑橘、葡萄、香蕉等，引起早期大量落叶，果实畸形，不能正常膨大，第二年病果增多。从落花期到果实近成熟期均可发病，主要为害鳞片、叶片、叶柄、叶痕、新梢、花器、果实等梨树地上部所有绿色幼嫩组织。其主要特征是在病部形成显著的黑色霉层，很像一层霉烟。\n" +
                "\n" +
                "防治方法\n" +
                "⑴ 清除病源。秋末冬初清除落叶和落果早春果树发芽前结合修剪清除病梢，集中烧毁或在果芽膨大期用碱式硫酸铜500-800倍或50%氯溴异氰脲酸2000倍液喷洒枝条。并于发病初期摘除病梢或病花簇。\n" +
                "\n" +
                "⑵ 药剂防治。可用福金600-800倍液或一点金1000-1500倍液每隔7-10天喷药一次，连喷2-3次。掌握好喷药时间和喷药次数，是发挥药剂保护作用的关键，掌握在果树萌芽前和病菌孢子飞散前的5-7月份喷药能收到很好的防治效果，病害发生以后，应每隔7-10天喷药一次，连喷2-3次，控制蔓延。\n" +
                "\n" +
                "二、溃疡病   主要为害苹果、桃、柑橘、葡萄等的果实。幼果染病，果面暗褐色，发育停滞，逐渐萎缩硬化，形成僵果残留于枝上。落花后喷洒50%氯溴异氰脲酸1000倍液，隔15天喷1次药剂保护果实，可选一点金2500倍液喷雾。\n" +
                "\n" +
                "三、炭疽病   主要为害的果树有苹果、桃、梨、柑橘、葡萄等。落花后可喷洒一点金1000倍液行防治。");
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://zhidao.baidu.com/question/1445753660855552340.html"));
                startActivity(intent);
            }
        });
    }
}
