package com.example.momo.client;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by momo on 18-6-22.
 */

public class UnionActivity extends Activity {


    Spinner sp1,sp2;
    List<Table> tableList;
    TableAdapter tableAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.union);
        sp1 = (Spinner)findViewById(R.id.spinner1);
        sp2 = (Spinner)findViewById(R.id.spinner2);
        tableList = new ArrayList<Table>();
        tableAdapter = new TableAdapter();
        sp1.setAdapter(tableAdapter);
        sp2.setAdapter(tableAdapter);

        String url = "http://10.0.2.2:8080/WLServer/TableServlet?flag=1";
        new MyTableTask().execute(url);
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


    class MyUnionTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            List<NameValuePair> list = new ArrayList<NameValuePair>();

            int index = sp1.getSelectedItemPosition();
            int index2 = sp2.getSelectedItemPosition();

            Table t1 = tableList.get(index);
            Table t2 = tableList.get(index2);


            NameValuePair p1 = new BasicNameValuePair("tid1", t1.getTid()+"");
            NameValuePair p2 = new BasicNameValuePair("tid2",t2.getTid()+"");

            list.add(p1);
            list.add(p2);
            return HttpUtil.doPost(params[0], list);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        }
    }

    public void union(View v){
        String url = "http://10.0.2.2:8080/WLServer/UnionServlet";
        new MyUnionTask().execute(url);
    }

}
