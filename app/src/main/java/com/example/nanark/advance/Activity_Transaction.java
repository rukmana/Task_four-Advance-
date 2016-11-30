package com.example.nanark.advance;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static com.example.nanark.advance.Expenses.saveExpenses;
import static com.example.nanark.advance.Income.saveIncome;

public class Activity_Transaction extends AppCompatActivity implements View.OnClickListener {

    EditText    edtex_dateEx,
            edtex_desEx,
            edtex_amoEx,
            edtex_dateIn,
            edtex_desIn,
            edtex_amoIn;
    Button btn_addEx, btn_addIn;
    private SimpleDateFormat dateFormat;
    private DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__transaction);
        this.setTitle("Transaction");

        edtex_dateEx = (EditText) findViewById(R.id.edtex_date_ex);
        edtex_desEx = (EditText) findViewById(R.id.edtex_des_ex);
        edtex_amoEx = (EditText) findViewById(R.id.edtex_amo_ex);

        edtex_dateIn = (EditText) findViewById(R.id.edtex_date_in);
        edtex_desIn = (EditText) findViewById(R.id.edtex_des_in);
        edtex_amoIn = (EditText) findViewById(R.id.edtex_amo_in);

        btn_addEx = (Button) findViewById(R.id.btn_add_ex);
        btn_addEx.setOnClickListener(this);
        btn_addIn = (Button) findViewById(R.id.btn_add_in);
        btn_addIn.setOnClickListener(this);

        edtex_dateEx.setOnClickListener(this);
        edtex_dateEx.setInputType(InputType.TYPE_NULL);
        edtex_dateEx.setFocusableInTouchMode(false);


        edtex_dateIn.setOnClickListener(this);
        edtex_dateIn.setInputType(InputType.TYPE_NULL);
        edtex_dateIn.setFocusableInTouchMode(false);

        databaseHelper = new DatabaseHelper(Activity_Transaction.this);
        return ;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.edtex_date_ex:
                selectDate(edtex_dateEx);
                break;
            case R.id.edtex_date_in:
                selectDate(edtex_dateIn);
                break;
            case R.id.btn_add_in:
                Income income = new Income();
                income.setDate(edtex_dateIn.getText().toString());
                income.setDescription(edtex_desIn.getText().toString());
                income.setAmounth(Integer.valueOf(edtex_amoIn.getText().toString()));
                if(saveIncome(databaseHelper, income)) {
                    edtex_dateIn.setText("");
                    edtex_desIn.setText("");
                    edtex_amoIn.setText("");
                    Toast.makeText(Activity_Transaction.this, getResources().getString(R.string.save_success), Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(Activity_Transaction.this, getResources().getString(R.string.save_failed), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_add_ex:
                Expenses expenses = new Expenses();
                expenses.setDate(edtex_dateEx.getText().toString());
                expenses.setDescription(edtex_desEx.getText().toString());
                expenses.setAmounth(Integer.valueOf(edtex_amoEx.getText().toString()));
                if(saveExpenses(databaseHelper, expenses)) {
                    edtex_dateEx.setText("");
                    edtex_desEx.setText("");
                    edtex_amoEx.setText("");
                    Toast.makeText(Activity_Transaction.this, getResources().getString(R.string.save_success), Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(Activity_Transaction.this, getResources().getString(R.string.save_failed), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void hideKeyboard(EditText editText){
        InputMethodManager inputMethodManager = (InputMethodManager) Activity_Transaction.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(),0);
    }

    public void selectDate(final EditText editText){
        Calendar calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        DatePickerDialog datePickerDialog = new DatePickerDialog(Activity_Transaction.this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int month, int day) {
                Calendar c = Calendar.getInstance();
                c.set(year, month, day);
                editText.setText(dateFormat.format(c.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
}


