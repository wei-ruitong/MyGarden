package com.example.mygarden.Weatherpackage;

public class Future_weather {
    private String data;//日期
    private String qingyin;//晴天、阴天
    private String tem;//温度
    private String wind;//风级
public Future_weather(String data,String qingyin,String tem,String wind){
    this.data=data;
    this.qingyin=qingyin;
    this.tem=tem;
    this.wind=wind;
}
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getQingyin() {
        return qingyin;
    }

    public void setQingyin(String qingyin) {
        this.qingyin = qingyin;
    }

    public String getTem() {
        return tem;
    }

    public void setTem(String tem) {
        this.tem = tem;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }
}
