package com.example.momo.client;

/**
 * Created by momo on 18-6-21.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class DbAdapter {
    public static final String DB_NAME = "wl.db";

    public static final String TABLE_NAME = "MenuTbl";

    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String PRICE = "price";
    public static final String TID = "tid";
    public static final String DESCRIPTION = "description";

    public static final int VERSION = 1;

    private Context context;
    MyHelper myHelper;
    SQLiteDatabase db;
    public DbAdapter(Context context){
        this.context = context;
    }

    class MyHelper extends SQLiteOpenHelper {
        // 创建库
        public MyHelper() {
            super(context, DB_NAME, null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String sql = " create table MenuTbl(_id integer primary key, name text,price integer,tid integer ,description text) ";
            db.execSQL(sql);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table MenuTbl");
            onCreate(db);
        }
    }

    public void open() {
        myHelper = new MyHelper();
        db = myHelper.getWritableDatabase();
    }

    // 添加菜单
    public void addMenu(int id, String name, int tid, int price,
                        String description) {
        open();
        try {
            ContentValues values = new ContentValues();
            values.put(ID, id);
            values.put(NAME, name);
            values.put(DESCRIPTION, description);
            values.put(TID, tid);
            values.put(PRICE, price);
            db.insert(TABLE_NAME, NAME, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    // 删除菜单
    public void deleteMenu() {
        open();
        try {
            db.delete(TABLE_NAME, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }


    public List<Menu> query(){
        open();
        String[] columns = {ID,TID,NAME,PRICE,DESCRIPTION};
        Cursor c = db.query(TABLE_NAME, columns, null, null, null, null, null);
        int count = c.getCount();
        List<Menu> list = new ArrayList<Menu>();
        for(int i=0;i<count;i++){
            c.moveToPosition(i);
            int id = c.getInt(0);
            int tid = c.getInt(1);
            String name = c.getString(2);
            int price = c.getInt(3);
            String desc = c.getString(4);
            Menu m = new Menu();
            m.setId(id);
            m.setTid(tid);
            m.setName(name);
            m.setPrice(price);
            m.setDesc(desc);

            list.add(m);
        }
        db.close();
        return list;
    }


}