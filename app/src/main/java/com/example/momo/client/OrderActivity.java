package com.example.momo.client;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class OrderActivity extends Activity{
    Spinner tableSp;
    List<Table> tableList;
    TableAdapter tableAdapter;
    String ctime;
    int uid;
    int persons;
    EditText numEditText;
    ConfigUtil configUtil;
    DbAdapter dbAdapter;
    List<Menu> menuList;
    List<MenuTemp> menuTempList;
    ListView lv;
    MenuListAdapter menuListAdapter;
    Gson gson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order);
        configUtil = new ConfigUtil(this);
        gson = new Gson();
        dbAdapter = new DbAdapter(this);
        tableSp = (Spinner)findViewById(R.id.spinner);
        numEditText = (EditText)findViewById(R.id.number_editText2);
        menuList = new ArrayList<Menu>();
        menuTempList  = new ArrayList<MenuTemp>();
        menuListAdapter = new MenuListAdapter();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd HH:mm");

        ctime = sdf.format(date);
        System.out.println("timeStr="+ctime);
        String userJson = configUtil.getUserJson();
        Type type = new TypeToken<User>(){}.getType();
        User u = gson.fromJson(userJson, type);
        uid = u.getId();
        System.out.println("uid="+uid);
        tableList = new ArrayList<Table>();
        tableAdapter = new TableAdapter();
        tableSp.setAdapter(tableAdapter);

        String tableUrl = "http://10.0.2.2:8080/WLServer/TableServlet?flag=0";
        new MyTableTask().execute(tableUrl);


        lv = (ListView)findViewById(R.id.listView1);
        lv.setAdapter(menuListAdapter);
    }

//    // 下订单
//    public void order(View v){
//        Order o = new Order();
//        o.setCtime(ctime);
//        o.setUid(uid);
//        int index = tableSp.getSelectedItemPosition();
//        Table t = tableList.get(index);
//        o.setTid(t.getTid());
//        persons = Integer.parseInt(numEditText.getText().toString());
//        o.setPersonNum(persons);
//        o.setDesc("desc");
//        o.setList(menuTempList);
//
//
//        Gson gson = new Gson();
//        Type type = new TypeToken<Order>(){}.getType();
//        String json = gson.toJson(o, type);
//
//        String url = "http://10.0.2.2:8080/WL_Server/OrderServlet";
//
//        new MyOrderTask().execute(url,json);
//    }

    class MyOrderTask extends AsyncTask<String, Integer, String>{

        @Override
        protected String doInBackground(String... params) {
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            NameValuePair p1 = new BasicNameValuePair("order_json", params[1]);
            list.add(p1);
            return HttpUtil.doPost(params[0], list);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Toast.makeText(getApplicationContext(), result, 1).show();
        }

    }

    class MenuListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return menuTempList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v;
            if(convertView==null){
                v = View.inflate(getApplicationContext(), R.layout.menu_item, null);
            }else{
                v = convertView;
            }

            MenuTemp mt = menuTempList.get(position);
            TextView idTv = (TextView)v.findViewById(R.id.id_textView1);
            TextView numTv = (TextView)v.findViewById(R.id.num_textView1);
            TextView nameTv = (TextView)v.findViewById(R.id.name_textView1);

            idTv.setText(position+1+"");
            numTv.setText(mt.getNum()+"");
            nameTv.setText(mt.getName());
            return v;
        }
    }


    class MenuAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return menuList.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tv;
            if(convertView==null){
                tv = new TextView(getApplicationContext());
            }else{
                tv = (TextView)convertView;
            }
            Menu m = menuList.get(position);
            tv.setText(m.getName());
            return tv;
        }

    }

    // 点菜
    public void diancai(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("点菜");
        builder.setNegativeButton("取消", null);
        final View v1 = View.inflate(this, R.layout.diancai, null);
        builder.setView(v1);

        final Spinner sp = (Spinner)v1.findViewById(R.id.spinner1);
        final EditText numEt = (EditText)v1.findViewById(R.id.num_editText1);
        final EditText descEt = (EditText)v1.findViewById(R.id.desc_editText2);

        builder.setPositiveButton("确定", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MenuTemp mt = new MenuTemp();
                //String name = sp.getSelectedItem().toString();
                int index = sp.getSelectedItemPosition();
                Menu m = menuList.get(index);
                int num = Integer.parseInt(numEt.getText().toString());
                String desc = descEt.getText().toString();
                mt.setName(m.getName());
                mt.setDesc(desc);
                mt.setNum(num);
                mt.setMid(m.getId());
                menuTempList.add(mt);
                menuListAdapter.notifyDataSetChanged();
            }
        });



        MenuAdapter ma = new MenuAdapter();
        sp.setAdapter(ma);


        menuList = dbAdapter.query();
        ma.notifyDataSetChanged();

        builder.create().show();
    }



    // 1. 获得空位桌号

    class TableAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return tableList.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tv;
            if(convertView==null){
                tv = new TextView(getApplicationContext());
            }else{
                tv = (TextView)convertView;
            }
            Table t = tableList.get(position);
            tv.setText(t.getTid()+"");
            return tv;
        }
    }


    class MyTableTask extends AsyncTask<String, Integer, String>{

        @Override
        protected String doInBackground(String... params) {
            String json = HttpUtil.doPost(params[0], null);
            return json;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Type type = new TypeToken<List<Table>>(){}.getType();
            tableList = gson.fromJson(result, type);
            tableAdapter.notifyDataSetChanged();
        }

    }
}