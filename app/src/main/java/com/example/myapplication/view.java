package com.example.myapplication;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class view extends AppCompatActivity {

    ListView lv_customerList;
    ArrayAdapter customerArrayAdapter;
    int theme = MainActivity.theme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(theme);
        setContentView(R.layout.activity_view);
        lv_customerList = findViewById(R.id.listView);

        // receive the value by getStringExtra() method
        // and key must be same which is send by first activity
        ArrayList<String> myList = (ArrayList<String>) getIntent().getSerializableExtra("mylist");
        assert myList != null;
        customerArrayAdapter = new ArrayAdapter<String>(view.this, android.R.layout.simple_list_item_1, myList);
        lv_customerList.setAdapter(customerArrayAdapter);
        // display the string into textView
    }
}