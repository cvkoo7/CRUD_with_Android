package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements android.view.View.OnClickListener {
    SQLiteDatabase db;
    ArrayList<String> rerturnList;
    EditText editsearchname,editempname,editempmail,editempsalary;
    Button Add, Delete, Modify, View,search ;
    static int theme = R.style.AppTheme;
    ImageButton imageButton;
    static int f = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(theme);
        setContentView(R.layout.activity_main);
        //Create database,EmployeeDB database name
        db=openOrCreateDatabase("EmployeeDB", Context.MODE_PRIVATE, null);
        //create table Employee
        db.execSQL("CREATE TABLE IF NOT EXISTS Employee(EmpId INTEGER PRIMARY KEY AUTOINCREMENT,EmpName VARCHAR,EmpMail VARCHAR,EmpSalary VARCHAR);");
        editsearchname = (EditText) findViewById(R.id.edtemployeename);
        editempname = (EditText) findViewById(R.id.editText);
        editempmail = (EditText) findViewById(R.id.editText2);
        editempsalary = (EditText) findViewById(R.id.editText3);
        Add = (Button) findViewById(R.id.btnsave);
        Delete= (Button) findViewById(R.id.btndel);
        Modify= (Button) findViewById(R.id.btnupdate);
        View= (Button) findViewById(R.id. btnselect);
        search=(Button) findViewById(R.id. btnselectperticular);
        Add.setOnClickListener(this);
        Delete.setOnClickListener(this);
        Modify.setOnClickListener(this);
        View.setOnClickListener(this);
        search.setOnClickListener(this);

        imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(f==0){
                    changeTheme(R.style.AppTheme1);
                    f=1;
                }
                else{
                    changeTheme(R.style.AppTheme);
                    f = 0;
                }

            }
        });


        Switch sw = (Switch) findViewById(R.id.switch1);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                RelativeLayout root=(RelativeLayout)findViewById(R.id.root);
                if (isChecked) {
                    root.setBackgroundColor(Color.parseColor("#6200EE"));
                } else {
                    root.setBackgroundColor(Color.parseColor("#ffffff"));
                }
            }
        });

        final ToggleButton toggle = (ToggleButton) findViewById(R.id.togglebutton);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                RelativeLayout root=(RelativeLayout)findViewById(R.id.root);
                if (isChecked) {
                    Intent intent = new Intent(getApplicationContext(), Screen.class);
                    startActivity(intent);
                    //root.setBackgroundResource(R.drawable.index);
                }
            }
        });
    }

    public void msg(Context context,String str)
    {
        Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnsave)
        {
            // code for save data
            if(editempname.getText().toString().trim().length()==0||
                    editempmail.getText().toString().trim().length()==0||
                    editempsalary.getText().toString().trim().length()==0)
            {
                msg(this, "Please enter all values");
                return;
            }
            db.execSQL("INSERT INTO Employee(EmpName,EmpMail,EmpSalary)VALUES('"+ editempname.getText()+"','"+ editempmail.getText()+ "','"+    editempsalary.getText()+"');");
            msg(this, "Record added");
            clearInput();
        }

        else if(v.getId()==R.id.btnupdate)
        {
            //code for update data
            if(editsearchname.getText().toString().trim().length()==0)
            {
                msg(this, "Enter Employee  Name");
                return;
            }
            Cursor c=db.rawQuery("SELECT * FROM Employee WHERE EmpName='"+ editsearchname.getText()+"'", null);
            if(c.moveToFirst()) {
                db.execSQL("UPDATE Employee  SET EmpName ='"+ editempname.getText()+"', EmpMail='"+ editempmail.getText()+"',EmpSalary='"+      editempsalary.getText()+"' WHERE EmpName ='"+editsearchname.getText()+"'");
                msg(this, "Record Modified");
            }
            else
            {
                msg(this, "Invalid Employee Name");
            }
            clearInput();
        }
        else if(v.getId()==R.id.btndel)
        {
            //code for delete data
            if(editsearchname.getText().toString().trim().length()==0)
            {
                msg(this, " Please enter Employee  Name ");
                return;
            }
            Cursor c=db.rawQuery("SELECT * FROM Employee WHERE EmpName ='"+ editsearchname.getText()+"'", null);
            if(c.moveToFirst())
            {
                db.execSQL("DELETE FROM Employee WHERE EmpName ='"+ editsearchname.getText()+"'");
                msg(this, "Record Deleted");
            }
            else
            {
                msg(this, "Invalid Employee Name ");
            }
            clearInput();
        }
        else if (v.getId() == R.id.btnselect)
        {
            //code for select all data
            Cursor c=db.rawQuery("SELECT * FROM Employee", null);
            rerturnList = new ArrayList<String>();
            if(c.getCount()==0)
            {
                msg(this, "No records found");
                return;
            }
            StringBuffer buffer=new StringBuffer();
            while(c.moveToNext())
            {
                rerturnList.add("Employee Id: "+c.getInt(0)+"\n");
                rerturnList.add("Employee Name: "+c.getString(1)+"\n");
                rerturnList.add("Employee Mail: "+c.getString(2)+"\n");
                rerturnList.add("Employee Salary: "+c.getString(3)+"\n\n");
            }
            Intent intent = new Intent(getApplicationContext(), view.class);

            // now by putExtra method put the value in key, value pair
            // key is message_key by this key we will receive the value, and put the string

            intent.putExtra("mylist", rerturnList);

            // start the Intent
            clearInput();
            startActivity(intent);
        }
        else if(v.getId()==R.id.btnselectperticular)
        {
            //code for select particular data
            if(editsearchname.getText().toString().trim().length()==0)
            {
                msg(this, "Enter Employee Name");
                return;
            }
            Cursor c=db.rawQuery("SELECT * FROM Employee WHERE EmpName='"+editsearchname.getText()+"'", null);
            if(c.moveToFirst())
            {
                editempname.setText(c.getString(1));
                editempmail.setText(c.getString(2));
                editempsalary.setText(c.getString(3));
            }
            else
            {
                msg(this, "Invalid Employee Name");
            }
        }
    }
    public void changeTheme(int newTheme) {
        theme = newTheme;
        recreate();
    }
    public void clearInput() {
        editempname.setText("");
        editsearchname.setText("");
        editempmail.setText("");
        editempsalary.setText("");
    }

}