package com.example.momo.client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by momo on 18-6-19.
 */

public class MainActivity extends Activity {
    GridView gv;
    Intent intent;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        gv = (GridView) findViewById(R.id.gridview);
        gv.setAdapter(new MyAdapter());

        gv.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position) {
                    case 0:
                        intent = new Intent(MainActivity.this,TableActivity.class);
                        startActivity(intent);
                       break;
                    case 1:
                        intent = new Intent(MainActivity.this,OrderActivity.class);
                        startActivity(intent);
                        break;
                    case 5:
                        intent = new Intent(MainActivity.this,UpdateActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), position+"", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
    }

    class MyAdapter extends BaseAdapter {

        int imageRes[] = {
                R.drawable.chatai,
                R.drawable.diancai,
                R.drawable.bingtai,
                R.drawable.zhuantai,
                R.drawable.jietai,
                R.drawable.shezhi,
                R.drawable.zhuxiao};

        @Override
        public int getCount() {
            return imageRes.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            ImageView  iv;
            if(convertView == null){
                iv = new ImageView(getApplicationContext());
            }else {
                iv = (ImageView)convertView;
            }

            iv.setLayoutParams(new GridView.LayoutParams(85, 85));
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            iv.setPadding(8, 8, 8, 8);

            iv.setImageResource(imageRes[position]);
            return iv;
        }

        @Nullable
        @Override
        public CharSequence[] getAutofillOptions() {
            return new CharSequence[0];
        }
    }
}
