package com.example.nanark.advance;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Activity_Transaction extends AppCompatActivity implements View.OnClickListener {

    DatabaseHelper myDB;

    EditText description_ex, amoun_ex;

    Button add_expen, add_incom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__transaction);
        this.setTitle("Transaction");
        myDB = new DatabaseHelper(this);


        description_ex = (EditText) findViewById(R.id.ed);

        amoun_ex = (EditText) findViewById(R.id.surname_txt);



        add_expen = (Button) findViewById(R.id.send_btn);


        add_expen.setOnClickListener(this);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

    }
}
