package com.example.asimm.wallet;


import android.app.DatePickerDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asimm.wallet.Utilities.FunctionUtilities;
import com.example.asimm.wallet.Utilities.ViewsUtilities;
import com.example.asimm.wallet.database.SpendingFetcher;
import com.example.asimm.wallet.database.entities.Spending;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {

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

        this.setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        startDateText = view.findViewById(R.id.start_date_text);
        endDateText = view.findViewById(R.id.end_date_text);

        spendings = new ArrayList<>();

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

        final SpendingFetcher spendingFetcher = new SpendingFetcher();
        LiveData<List<Spending>> spendingsLiveData = spendingFetcher.getAllSpendings();
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
            endCalendar.set(eYear, eMonth, eDay, 23, 59, 59);
        }

        SpendingFetcher spendingFetcher = new SpendingFetcher();

        LiveData<List<Spending>> spendingsLiveData = spendingFetcher.getPartialSpendings(startCalendar.getTimeInMillis()
                , endCalendar.getTimeInMillis());
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.history_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_history_item:
                ViewsUtilities.showAlertDialog(getActivity(), getString(R.string.app_name), getString(R.string.delete_history_alert_message), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // delete history here
                        SpendingFetcher spendingFetcher = new SpendingFetcher();
                        spendingFetcher.deleteAllSpendings();
                        ViewsUtilities.showToast(getActivity(), "History Deleted");
                    }
                });
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

        @Override
        public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_recyclerview_rowview, parent, false);
            return new RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
            holder.date.setText(FunctionUtilities.getDate(spendings.get(position).getEpochTimeStamp()));
            holder.category.setText(spendings.get(position).getCategory());
            holder.amount.setText(String.valueOf(spendings.get(position).getAmount()));


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (spendings.get(position).getDetail() != null && !spendings.get(position).getDetail().equals("")) {
                        ViewsUtilities.showAlertDialog(getActivity(), "Detail", spendings.get(position).getDetail(), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        });
                    }
                }
            });
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
