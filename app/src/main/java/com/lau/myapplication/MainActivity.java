package com.lau.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import table.Table;
import table_1.CustomTable;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        Log.e("Hello", "World!!!");
        Table table = new Table(this);
        CustomTable table1 = new CustomTable(this);

        setContentView(table1);



    }
}
