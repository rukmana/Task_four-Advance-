package com.example.nanark.advance;


import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.nanark.advance.CurrencyFormat.setCurrency;

import static com.example.nanark.advance.Income.deleteIncome;
import static com.example.nanark.advance.Income.totalIncome;
import static com.example.nanark.advance.Income.updateIncome;
import static com.example.nanark.advance.Income.saveIncome;

import static com.example.nanark.advance.Expenses.deleteExpenses;
import static com.example.nanark.advance.Expenses.totalExpenses;
import static com.example.nanark.advance.Expenses.updateExpenses;
import static com.example.nanark.advance.Expenses.saveExpenses;



public class Activity_Dashboard extends Fragment {

    private RecyclerView rv_incomes, rv_expensesr;
    private DatabaseHelper databaseHelper;
    private TextView tv_total_income;
    private TextView tv_total_expenses;
    private TextView tv_balance;
    private int income, expenses, balance;
    private View rootView;
    private incomeAdapter adapter_income;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity__dashboard, container, false);
        getActivity().setTitle(getResources().getString(R.string.dashboard));

        databaseHelper = new DatabaseHelper(getActivity());


        rv_incomes = (RecyclerView) rootView.findViewById(R.id.rv_incomes);
        rv_incomes.setHasFixedSize(true);
        LinearLayoutManager rv_layout_income = new LinearLayoutManager(getActivity());
        rv_incomes.setLayoutManager(rv_layout_income);



        tv_total_income = (TextView) rootView.findViewById(R.id.tv_total_income);


        tv_balance = (TextView) rootView.findViewById(R.id.tv_balance);

        refreshData();

        return rootView;


    }

    private void refreshData() {

        Cursor incomes = Income.listIncome(databaseHelper);
        adapter_income = new incomeAdapter(incomes);
        rv_incomes.setAdapter(adapter_income);



        income = totalIncome(databaseHelper);

        balance = income;

        tv_total_income.setText(setCurrency(getActivity(), income));

        tv_balance.setText(setCurrency(getActivity(), balance));

    }


    public class incomeAdapter extends RecyclerView.Adapter<incomeAdapter.ViewHolder> implements View.OnClickListener {

        private Cursor incomes;

        public incomeAdapter(Cursor incomes) {
            this.incomes = incomes;
        }

        @Override
        public incomeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_expenses, parent, false);
            v.setOnClickListener(this);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            incomes.moveToPosition(position);
            holder.tv_date.setText(incomes.getString(incomes.getColumnIndex(DatabaseHelper.COL_DATE)));
            holder.tv_description.setText(incomes.getString(incomes.getColumnIndex(DatabaseHelper.COL_DESCRIPTION)));
            holder.tv_amounth.setText(setCurrency(getActivity(), incomes.getInt(incomes.getColumnIndex(DatabaseHelper.COL_AMOUNTH))));
        }

        @Override
        public int getItemCount() {
            return incomes.getCount();
        }

        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()) {
            };
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View view = inflater.inflate(R.layout.dialog_show, null);
            builder.setView(view).setCancelable(false).create();
            final AlertDialog alertDialog = builder.show();

            TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
            final EditText et_date = (EditText) view.findViewById(R.id.et_date);
            final EditText et_description = (EditText) view.findViewById(R.id.et_description);
            final EditText et_amounth = (EditText) view.findViewById(R.id.et_amounth);
            Button btn_update = (Button) view.findViewById(R.id.btn_update);
            Button btn_delete = (Button) view.findViewById(R.id.btn_delete);
            Button btn_cancle = (Button) view.findViewById(R.id.btn_cancle);
            incomes.moveToPosition(rv_incomes.getChildLayoutPosition(v));
            tv_title.setText(getResources().getString(R.string.income));
            et_date.setText(incomes.getString(incomes.getColumnIndex(DatabaseHelper.COL_DATE)));
            et_description.setText(incomes.getString(incomes.getColumnIndex(DatabaseHelper.COL_DESCRIPTION)));
            et_amounth.setText(incomes.getString(incomes.getColumnIndex(DatabaseHelper.COL_AMOUNTH)));
            btn_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Income income = new Income();
                    income.setId(incomes.getString(incomes.getColumnIndex(DatabaseHelper.COL_ID)));
                    income.setDate(et_date.getText().toString());
                    income.setDescription(et_description.getText().toString());
                    income.setAmounth(Integer.valueOf(et_amounth.getText().toString()));
                    if (updateIncome(databaseHelper, income)) {
                        alertDialog.dismiss();
                        refreshData();
                        Toast.makeText(getActivity(), getResources().getString(R.string.update_success), Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(getActivity(), getResources().getString(R.string.update_failed), Toast.LENGTH_SHORT).show();
                }
            });
            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (deleteIncome(databaseHelper, incomes.getString(incomes.getColumnIndex(DatabaseHelper.COL_ID)))) {
                        alertDialog.dismiss();
                        refreshData();
                        Toast.makeText(getActivity(), getResources().getString(R.string.delete_success), Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(getActivity(), getResources().getString(R.string.delete_failed), Toast.LENGTH_SHORT).show();
                }
            });
            btn_cancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });
        }


        public class ViewHolder extends RecyclerView.ViewHolder {

            public CardView cv_income;
            public TextView tv_date, tv_amounth, tv_description;

            public ViewHolder(View v) {
                super(v);
                cv_income = (CardView) v.findViewById(R.id.cv_expenses);
                tv_date = (TextView) v.findViewById(R.id.tv_date);
                tv_description = (TextView) v.findViewById(R.id.tv_description);
                tv_amounth = (TextView) v.findViewById(R.id.tv_amounth);
            }
        }

    }
}
