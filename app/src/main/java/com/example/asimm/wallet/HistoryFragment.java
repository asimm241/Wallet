package com.example.asimm.wallet;


import android.app.DatePickerDialog;
import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.asimm.wallet.Utilities.FunctionUtilities;
import com.example.asimm.wallet.database.SpendingsFetcher;
import com.example.asimm.wallet.database.entities.Spending;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.internal.Utils;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends LifecycleFragment {

    TextView startDateText;
    TextView endDateText;
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    List<Spending> spendings;

    int sYear, sMonth, sDay, eYear, eMonth, eDay;

    Calendar calendar;

    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        startDateText = view.findViewById(R.id.start_date_text);
        endDateText = view.findViewById(R.id.end_date_text);

        spendings = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Spending spending = new Spending();
            spending.setAmount(10 + i);
            spending.setCategory("Food");
            spending.setDate("15-03-2017");
            spendings.add(spending);
        }

        recyclerView = view.findViewById(R.id.history_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        adapter = new RecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        calendar = Calendar.getInstance();
        sYear = calendar.get(Calendar.YEAR);
        sMonth = calendar.get(Calendar.MONTH);
        sDay = calendar.get(Calendar.DAY_OF_MONTH);
        eYear = calendar.get(Calendar.YEAR);
        eMonth = calendar.get(Calendar.MONTH);
        eDay = calendar.get(Calendar.DAY_OF_MONTH);

        startDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showStartDatePicker();
            }
        });

        endDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEndDatePicker();
            }
        });

        final SpendingsFetcher spendingsFetcher = new SpendingsFetcher();
        LiveData<List<Spending>> spendingsLiveData = spendingsFetcher.getAllSpendings();
        spendingsLiveData.observe(this, new Observer<List<Spending>>() {
            @Override
            public void onChanged(@Nullable List<Spending> spendingsList) {
                if (spendingsList != null) {
                    spendings.clear();
                    spendings.addAll(spendingsList);
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }

    private void showStartDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                setDateUI(year, month, dayOfMonth, true);
            }


        }, sYear, sMonth, sDay);
        datePickerDialog.show();
    }

    private void showEndDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                setDateUI(year, month, dayOfMonth, false);
            }


        }, eYear, eMonth, eDay);
        datePickerDialog.show();
    }

    private void setDateUI(int year, int month, int dayOfMonth, boolean isStartDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth, 0, 0, 0);
        if (isStartDate) {
            startDateText.setText(FunctionUtilities.getDate(FunctionUtilities.dateEnum.MONTH, calendar.getTime()) + " " + FunctionUtilities.getDate(FunctionUtilities.dateEnum.DAY, calendar.getTime()) + ", " + FunctionUtilities.getDate(FunctionUtilities.dateEnum.YEAR, calendar.getTime()));
            sYear = year;
            sMonth = month;
            sDay = dayOfMonth;
        } else {
            endDateText.setText(FunctionUtilities.getDate(FunctionUtilities.dateEnum.MONTH, calendar.getTime()) + " " + FunctionUtilities.getDate(FunctionUtilities.dateEnum.DAY, calendar.getTime()) + ", " + FunctionUtilities.getDate(FunctionUtilities.dateEnum.YEAR, calendar.getTime()));
            eYear = year;
            eMonth = month;
            eDay = dayOfMonth;
        }


        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(sYear, sMonth, sDay, 0, 0, 0);

        Calendar endCalendar = Calendar.getInstance();

        if (eYear > 0) {
            endCalendar.set(eYear, eMonth, eDay, 0, 0, 0);
        }

        SpendingsFetcher spendingsFetcher = new SpendingsFetcher();

        LiveData<List<Spending>> spendingsLiveData = spendingsFetcher.getPartialSpendings(FunctionUtilities.getTimeStamp(startCalendar)
                , FunctionUtilities.getTimeStamp(endCalendar));
        spendingsLiveData.observe(this, new Observer<List<Spending>>() {
            @Override
            public void onChanged(@Nullable List<Spending> spendingsList) {
                if (spendingsList != null) {
                    spendings.clear();
                    spendings.addAll(spendingsList);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

        @Override
        public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_recyclerview_rowview, parent, false);
            return new RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerViewHolder holder, int position) {
            holder.date.setText(spendings.get(position).getDate());
            holder.category.setText(spendings.get(position).getCategory());
            holder.amount.setText(String.valueOf(spendings.get(position).getAmount()));
        }

        @Override
        public int getItemCount() {
            return spendings.size();
        }
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView date, amount, category;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date_rowview);
            amount = itemView.findViewById(R.id.amount_rowview);
            category = itemView.findViewById(R.id.category_rowview);

        }
    }
}
