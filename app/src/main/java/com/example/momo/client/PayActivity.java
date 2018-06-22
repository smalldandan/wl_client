package com.example.momo.client;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by momo on 18-6-22.
 */

public class PayActivity extends Activity {

    Spinner tableSp;
    WebView wv;
    List<Table> tableList;
    TableAdapter tableAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay);

        wv = (WebView)findViewById(R.id.webView1);

        tableSp = (Spinner)findViewById(R.id.spinner1);
        tableList = new ArrayList<Table>();
        tableAdapter = new TableAdapter();
        tableSp.setAdapter(tableAdapter);

        String url = "http://10.0.2.2:8080/WLServer/TableServlet?flag=1";
        new MyTableTask().execute(url);


    }

    class MyQueryTask extends AsyncTask<String, Integer, String>{

        @Override
        protected String doInBackground(String... params) {
            String json = HttpUtil.doPost(params[0], null);
            return json;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);




            wv.loadData(result, "text/html","gbk");
        }

    }

    class TableAdapter extends BaseAdapter {

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
            Gson gson = new Gson();
            tableList = gson.fromJson(result, type);
            tableAdapter.notifyDataSetChanged();
        }

    }

    public void query(View v){
        int index = tableSp.getSelectedItemPosition();
        Table t = tableList.get(index);


        Toast.makeText(getApplicationContext(), t+"", Toast.LENGTH_LONG).show();


        String url = "http://10.0.2.2:8080/WLServer/QueryQrderServlet?tid="+t.getTid();
        new MyQueryTask().execute(url);
    }

    class MyPayTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            String json = HttpUtil.doPost(params[0], null);
            return json;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();

        }

    }
    public void pay(View v){
        int index = tableSp.getSelectedItemPosition();
        Table t = tableList.get(index);
        String url = "http://10.0.2.2:8080/WLServer/PaidServlet?tid="+t.getTid();
        new MyPayTask().execute(url);
    }

}
