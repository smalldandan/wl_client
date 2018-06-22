package com.example.momo.client;

import java.util.List;

/**
 * Created by momo on 18-6-22.
 */

public class Order {

    String ctime;
    int uid;
    int tid;
    String desc;
    int personNum;
    List<MenuTemp> list;

    public String getCtime() {
        return ctime;
    }
    public void setCtime(String ctime) {
        this.ctime = ctime;
    }
    public int getUid() {
        return uid;
    }
    public void setUid(int uid) {
        this.uid = uid;
    }
    public int getTid() {
        return tid;
    }
    public void setTid(int tid) {
        this.tid = tid;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public int getPersonNum() {
        return personNum;
    }
    public void setPersonNum(int personNum) {
        this.personNum = personNum;
    }
    public List<MenuTemp> getList() {
        return list;
    }
    public void setList(List<MenuTemp> list) {
        this.list = list;
    }

}

