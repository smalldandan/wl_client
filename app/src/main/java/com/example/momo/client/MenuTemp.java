package com.example.momo.client;

/**
 * Created by momo on 18-6-21.
 */

public class MenuTemp {


    int mid;
    String name;
    String desc;
    int num;



    public MenuTemp() {
        super();
    }

    public MenuTemp(int mid, String name, String desc, int num) {
        super();
        this.mid = mid;
        this.name = name;
        this.desc = desc;
        this.num = num;
    }

    public int getMid() {
        return mid;
    }
    public void setMid(int mid) {
        this.mid = mid;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public int getNum() {
        return num;
    }
    public void setNum(int num) {
        this.num = num;
    }

}
