package com.example.momo.client;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by momo on 18-6-18.
 */

public class LoginActivity extends Activity {

    EditText usernameEt,passwordEt;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        usernameEt = (EditText)findViewById(R.id.editText);
        passwordEt = (EditText)findViewById(R.id.editText3);

    }

    String doLogin(String url){

        String username = usernameEt.getText().toString();
        String password = passwordEt.getText().toString();

        //1.appache客户端
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        NameValuePair p1 = new BasicNameValuePair("username",username);
        NameValuePair p2 = new BasicNameValuePair("password",password);

        list.add(p1);
        list.add(p2);

        String msg = HttpUtil.doPost(url,list);
        return msg;

        //3.

    }


    class Mytask extends AsyncTask<String,Integer,String>{

        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
            String result = doLogin(url);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //1,保存数据
            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
        }
    }

    public void login(View v){
        //2.多线程

        String url = "http://10.0.2.2:8080/WLServer/DemoServlet";
        new Mytask().execute(url);
    }

}

