package com.example.mygarden;


import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.heweather.plugin.view.HeContent;
import com.heweather.plugin.view.HeWeatherConfig;

import com.heweather.plugin.view.LeftLargeView;

import java.util.ArrayList;
import java.util.List;

import interfaces.heweather.com.interfacesmodule.bean.air.now.AirNow;
import interfaces.heweather.com.interfacesmodule.bean.air.now.AirNowCity;
import interfaces.heweather.com.interfacesmodule.bean.weather.forecast.Forecast;
import interfaces.heweather.com.interfacesmodule.bean.weather.forecast.ForecastBase;
import interfaces.heweather.com.interfacesmodule.bean.weather.now.Now;
import interfaces.heweather.com.interfacesmodule.bean.weather.now.NowBase;
import interfaces.heweather.com.interfacesmodule.view.HeConfig;
import interfaces.heweather.com.interfacesmodule.view.HeWeather;
import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.view.LineChartView;


public class TwoFragment extends Fragment {
    private Button b;
        private TextView tianqi,wendu,tiganwendu,fengxiang,
        fengli,fengsu,xiangduishidu,jiangshuiliang,daqiyaqiang,nengjiandu
        ,qing,qing2,yunliang,fengxiangjiao,data,data2,htmp,htmp2,ltmp,ltmp2,youwufeng,
                youwufeng2,pm2,pm10,co,no2,o3,so2;
        private SwipeRefreshLayout swipeRefreshLayout;
        private LeftLargeView llView;
    private LineChartView lineChart;
    String[] weeks = {"周一","周二","周三","周四","周五","周六","周日"};//X轴的标注
    int[] weather = {9,7,6,7,8,6,8};
    private List<PointValue> mPointValues = new ArrayList<PointValue>();
    private List<AxisValue> mAxisValues = new ArrayList<AxisValue>();

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_two, container, false);
        data=(TextView) view.findViewById(R.id.data1);
        data2=(TextView) view.findViewById(R.id.data2);
        htmp=(TextView) view.findViewById(R.id.htmp);
        htmp2=(TextView) view.findViewById(R.id.htmp2);
        ltmp=(TextView) view.findViewById(R.id.ltmp);
        ltmp2=(TextView) view.findViewById(R.id.ltmp2);
        youwufeng=(TextView) view.findViewById(R.id.youwufengli);
        youwufeng2=(TextView) view.findViewById(R.id.youwufengli2);
        qing=(TextView)view.findViewById(R.id.qing);
        qing2=(TextView)view.findViewById(R.id.qing2);
         tianqi =(TextView) view.findViewById(R.id.tianqi);
        wendu =(TextView)view.findViewById(R.id.wendu);
        tiganwendu =(TextView)view.findViewById(R.id.tiganwendu);
        fengxiang =(TextView)view.findViewById(R.id.fengxiang);
        fengli =(TextView)view.findViewById(R.id.fengli);
        fengsu =(TextView)view.findViewById(R.id.fengsu);
        xiangduishidu =(TextView)view.findViewById(R.id.xiangdushidu);
        jiangshuiliang =(TextView)view.findViewById(R.id.jiangshuiliang);
        daqiyaqiang =(TextView)view.findViewById(R.id.daqiyaqiang);
        nengjiandu =(TextView)view.findViewById(R.id.nengjiandu);
        yunliang =(TextView)view.findViewById(R.id.yunliang);
        fengxiangjiao=(TextView)view.findViewById(R.id.fengxiangjiao);
        swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.shuaxin);
        llView = view.findViewById(R.id.ll_view);
        lineChart = (LineChartView)view.findViewById(R.id.zhexiantu);
        pm2 =(TextView)view.findViewById(R.id.pm2);
        pm10 =(TextView)view.findViewById(R.id.pm10);
        so2 =(TextView)view.findViewById(R.id.so2);
        no2 =(TextView)view.findViewById(R.id.no2);
        co =(TextView)view.findViewById(R.id.co);
        o3 =(TextView)view.findViewById(R.id.o3);
       b=(Button)view.findViewById(R.id.aqi);


        getAxisLables();//获取x轴的标注
        getAxisPoints();//获取坐标点
        initLineChart();//初始化


        HeWeatherConfig.init("15330e96c9284d1ab48594bccceaaafd", "CN101180301");
        HeConfig.switchToFreeServerNode();
        //获取实况天气NOw
        HeConfig.init("HE1911161417421501", "9c14f38fde85468588465dcd2dfc53aa");

        HeWeather.getWeatherForecast(getContext(), "", new HeWeather.OnResultWeatherForecastBeanListener() {
            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onSuccess(Forecast forecast) {
                Toast.makeText(getContext(),"nihao",Toast.LENGTH_SHORT).show();
                List<ForecastBase> f=forecast.getDaily_forecast();
                data.setText(f.get(1).getDate());
                htmp.setText(f.get(1).getTmp_max());
                ltmp.setText(f.get(1).getTmp_min());
                youwufeng.setText(f.get(0).getWind_sc());
                qing.setText(f.get(1).getCond_code_d());
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
           @Override
           public void onRefresh() {
               init();
               swipeRefreshLayout.setRefreshing(false);
           }
       });
init();
        //左侧大布局右侧双布局控件


//获取左侧大布局
        LinearLayout leftLayout = llView.getLeftLayout();
//获取右上布局
        LinearLayout rightTopLayout = llView.getRightTopLayout();
//获取右下布局
        LinearLayout rightBottomLayout = llView.getRightBottomLayout();

//设置布局的背景圆角角度（单位：dp），颜色，边框宽度（单位：px），边框颜色
        llView.setStroke(5, Color.parseColor("#313a44"), 1, Color.BLACK);

//添加温度描述到左侧大布局
//第一个参数为需要加入的布局
//第二个参数为文字大小，单位：sp
//第三个参数为文字颜色，默认白色
        llView.addTemp(leftLayout, 40, Color.WHITE);
//添加温度图标到右上布局，第二个参数为图标宽高（宽高1：1，单位：dp）
        llView.addWeatherIcon(rightTopLayout, 14);
//添加预警图标到右上布局
        llView.addAlarmIcon(rightTopLayout, 14);
//添加预警描述到右上布局
        llView.addAlarmTxt(rightTopLayout, 14);
//添加文字AQI到右上布局
        llView.addAqiText(rightTopLayout, 14);
//添加空气质量到右上布局
        llView.addAqiQlty(rightTopLayout, 14);
//添加空气质量数值到右上布局
        llView.addAqiNum(rightTopLayout, 14);
//添加地址信息到右上布局
        llView.addLocation(rightTopLayout, 14, Color.WHITE);
//添加天气描述到右下布局
        llView.addCond(rightBottomLayout, 14, Color.WHITE);
//添加风向图标到右下布局
        llView.addWindIcon(rightBottomLayout, 14);
//添加风力描述到右下布局
        llView.addWind(rightBottomLayout, 14, Color.WHITE);
//添加降雨图标到右下布局
        llView.addRainIcon(rightBottomLayout, 14);
//添加降雨描述到右下布局
        llView.addRainDetail(rightBottomLayout, 10, Color.WHITE);
//设置控件的对齐方式，默认居中
        llView.setViewGravity(HeContent.GRAVITY_LEFT);
//显示布局
        llView.show();



       // HeConfig.init("HE1911161535531023", "fc845b96968b46ceafb69f1c9c152224");


        return view;
    }

    private void init(){
//获取实况天气
        HeWeather.getWeatherNow(getContext(), "CN101180301", new HeWeather.OnResultWeatherNowBeanListener() {
            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onSuccess(Now now) {
                final NowBase n=now.getNow();
                // Toast.makeText(getActivity(),n.getCloud(),Toast.LENGTH_SHORT).show();
                // Log.i("123",n.getCloud());
                //tianqi.setText(n.getCond_txt());
                if(!(n.getCond_txt().endsWith("?"))) {
                    tianqi.setText(n.getCond_txt());
                }
                wendu.setText("温度 "+n.getTmp()+"℃");
                tiganwendu.setText("体感温度 "+n.getFl()+"℃");
                if(!(n.getWind_dir().endsWith("?"))) {
                    fengxiang.setText("风向 " + n.getWind_dir());
                }
                fengli.setText("风力 "+n.getWind_sc()+"级");
                fengsu.setText("风速 "+n.getWind_spd()+"KM/H");
                xiangduishidu.setText("相对湿度 "+n.getHum()+"%");
                jiangshuiliang.setText("降水量 "+n.getPcpn()+"mm");
                daqiyaqiang.setText("大气压强 "+n.getPres()+"HPA");
                nengjiandu.setText("能见度 "+n.getVis()+"KM");
                yunliang.setText("云量 "+n.getCloud());
                fengxiangjiao.setText("风向角 "+n.getWind_deg()+"°");


            }
        });
        //获取空气质量指数
        HeWeather.getAirNow(getContext(), "CN101180301", new HeWeather.OnResultAirNowBeansListener() {
            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onSuccess(AirNow airNow) {
                AirNowCity a=airNow.getAir_now_city();
                    pm2.setText("PM2.5 "+a.getPm25());
                    pm10.setText("PM10 "+a.getPm10());
                    so2.setText("So2 "+a.getSo2());
                    no2.setText("No2 "+a.getNo2());
                    co.setText("CO " +a.getCo());
                    o3.setText("O3 "+a.getO3());
                    if(!(a.getQlty().endsWith("?"))) {
                        b.setText(a.getAqi() + " " + a.getQlty());
                    }

            }
        });
        /**
         * 获取未来几天的天气
         */
        HeWeather.getWeatherForecast(getContext(), "CN101180301", new HeWeather.OnResultWeatherForecastBeanListener() {
            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onSuccess(Forecast forecast) {
List<ForecastBase> f=forecast.getDaily_forecast();

data.setText(f.get(1).getDate());
data2.setText(f.get(2).getDate());
if(!(f.get(1).getCond_txt_d()).endsWith("?")&&!(f.get(2).getCond_txt_d()).endsWith("?")) {
    qing.setText(f.get(1).getCond_txt_d());
    qing2.setText(f.get(2).getCond_txt_d());
}
htmp.setText(f.get(1).getTmp_max()+"℃");
htmp2.setText(f.get(2).getTmp_max()+"℃");
ltmp.setText(f.get(1).getTmp_min()+"℃");
ltmp2.setText(f.get(2).getTmp_min()+"℃");
youwufeng.setText(f.get(1).getWind_sc()+"级");
youwufeng2.setText(f.get(2).getWind_sc()+"级");

            }
        });

    }
    private void initLineChart(){
        Line line = new Line(mPointValues).setColor(Color.BLUE).setCubic(false);  //折线的颜色
        List<Line> lines = new ArrayList<Line>();
        line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.SQUARE）
        line.setCubic(true);//曲线是否平滑
        line.setFilled(false);//是否填充曲线的面积
		line.setHasLabels(true);//曲线的数据坐标是否加上备注
        line.setHasLabelsOnlyForSelected(false);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
        line.setHasLines(true);//是否用直线显示。如果为false 则没有曲线只有点显示
        line.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);

        //坐标轴
        Axis axisX = new Axis(); //X轴
        axisX.setHasTiltedLabels(true);
        axisX.setTextColor(Color.BLACK);  //设置字体颜色
        axisX.setName("未来几天的天气");  //表格名称
        axisX.setTextSize(10);//设置字体大小
        axisX.setLineColor(Color.RED);
        axisX.setMaxLabelChars(7);  //最多几个X轴坐标
        axisX.setValues(mAxisValues);  //填充X轴的坐标名称
        data.setAxisXBottom(axisX); //x 轴在底部
        //data.setAxisXTop(axisX);  //x 轴在顶部

        Axis axisY = new Axis();  //Y轴
        axisY.setMaxLabelChars(7); //默认是3，只能看最后三个数字
        axisY.setName("温度");//y轴标注
        axisY.setLineColor(Color.BLACK);
        axisY.setTextColor(Color.RED);
        axisY.setTextSize(10);//设置字体大小

        data.setAxisYLeft(axisY);  //Y轴设置在左边
//	    data.setAxisYRight(axisY);  //y轴设置在右边

        //设置行为属性，支持缩放、滑动以及平移
        lineChart.setInteractive(true);
        lineChart.setZoomType(ZoomType.HORIZONTAL_AND_VERTICAL);
        lineChart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        lineChart.setLineChartData(data);
        lineChart.setVisibility(View.VISIBLE);
    }


    /**
     * X 轴的显示
     */
    private void getAxisLables(){
        for (int i = 0; i < weeks.length; i++) {
            mAxisValues.add(new AxisValue(i).setLabel(weeks[i]));
        }
    }

    /**
     * 图表的每个点的显示
     */
    private void getAxisPoints(){
        for (int i = 0; i < weather.length; i++) {
            mPointValues.add(new PointValue(i, weather[i]));
        }
    }


}
