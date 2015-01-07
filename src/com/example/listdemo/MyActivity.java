package com.example.listdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyActivity extends Activity {

    private GradientListView gradientListView;
    private MyListAdapter myListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        gradientListView = (GradientListView) findViewById(R.id.listview);
        myListAdapter = new MyListAdapter();
        gradientListView.setAdapter(myListAdapter);
    }

    class MyListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object getItem(int i) {
            return (Integer) i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            if (convertView == null) {
                convertView = new TextView(MyActivity.this);
                convertView.setPadding(30, 30, 30, 30);
            }
            ((TextView) convertView).setText(getItem(i).toString());
            return convertView;
        }
    }
}
