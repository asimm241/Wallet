package com.wallet.app.home.views.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wallet.app.R;
import com.wallet.app.Utilities.PreferencesUtilities;
import com.wallet.app.Utilities.ViewsUtilities;
import com.wallet.app.database.SpendingFetcher;
import com.wallet.app.database.entities.Spending;


public class HomeFragment extends Fragment implements View.OnClickListener {


    EditText mTotalEditText;
    Button mAddButton;
    Button mQuickButton1;
    Button mQuickButton2;
    Button mQuickButton3;
    Button mQuickButton4;
    Button mQuickButton5;
    Button mQuickButton6;
    Button mQuickButton7;
    Button mQuickButton8;

    TextView incomeTextView;

    View view;
    EditText editText;


    final int buttonValues[] = {50, 100, 500, 1000, 5000, 10000, 15000, 50000};


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        incomeTextView = getActivity().findViewById(R.id.tvIncome);
        mAddButton = view.findViewById(R.id.addButton);
        mQuickButton1 = view.findViewById(R.id.quickButton1);
        mQuickButton2 = view.findViewById(R.id.quickButton2);
        mQuickButton3 = view.findViewById(R.id.quickButton3);
        mQuickButton4 = view.findViewById(R.id.quickButton4);
        mQuickButton5 = view.findViewById(R.id.quickButton5);
        mQuickButton6 = view.findViewById(R.id.quickButton6);
        mQuickButton7 = view.findViewById(R.id.quickButton7);
        mQuickButton8 = view.findViewById(R.id.quickButton8);
        mTotalEditText = view.findViewById(R.id.total);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAddButton.setOnClickListener(this);
        mQuickButton1.setOnClickListener(this);
        mQuickButton2.setOnClickListener(this);
        mQuickButton3.setOnClickListener(this);
        mQuickButton4.setOnClickListener(this);
        mQuickButton5.setOnClickListener(this);
        mQuickButton6.setOnClickListener(this);
        mQuickButton7.setOnClickListener(this);
        mQuickButton8.setOnClickListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null)
            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }


    private void setIncome() {
        String text = mTotalEditText.getText().toString();
        if (text.equals("") || text == null) {
            Toast.makeText(getActivity(), R.string.no_spending_entered, Toast.LENGTH_LONG).show();
            return;
        } else {
            final int expense = Integer.parseInt(text);
            long prevIncome = PreferencesUtilities.readIncome();
            final long newIncome = prevIncome - expense;
            if (newIncome < 0) {
                ViewsUtilities.showAlertDialog(getActivity(), getString(R.string.app_name), "There is not enough money in your wallet. \nAre you sure you want to spend?", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showCategoryList(newIncome, expense);
                    }
                });
            } else {
                showCategoryList(newIncome, expense);
            }
        }

    }

    private void showCategoryList(final long newIncome, final int expense) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(getActivity(), R.style.DialogTheme);
        //builderSingle.setIcon(R.drawable.avd_show_password_1);
        builderSingle.setTitle("Select Category");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("Food");
        arrayAdapter.add("Shopping");
        arrayAdapter.add("Traveling");
        arrayAdapter.add("Utilities");
        arrayAdapter.add("Medication");
        arrayAdapter.add("Other");

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, final int which) {
                showCustomAlertDialog("Add " + mTotalEditText.getText().toString() + " in " + arrayAdapter.getItem(which) + " category?", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String categoryName = arrayAdapter.getItem(which);
                        PreferencesUtilities.writeIncome(newIncome);
                        incomeTextView.setText("Amount: " + Long.toString(newIncome));
                        mTotalEditText.setText("");
                        addExpenseInDb(expense, categoryName);
                    }
                });


            }
        });
        builderSingle.show();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addButton:
                setIncome();
                break;
            case R.id.quickButton1:
                addTotal(buttonValues[0]);
                break;
            case R.id.quickButton2:
                addTotal(buttonValues[1]);
                break;
            case R.id.quickButton3:
                addTotal(buttonValues[2]);
                break;
            case R.id.quickButton4:
                addTotal(buttonValues[3]);
                break;
            case R.id.quickButton5:
                addTotal(buttonValues[4]);
                break;
            case R.id.quickButton6:
                addTotal(buttonValues[5]);
                break;
            case R.id.quickButton7:
                addTotal(buttonValues[6]);
                break;
            case R.id.quickButton8:
                addTotal(buttonValues[7]);
                break;
            default:
                break;
        }
    }

    private void addExpenseInDb(int expense, String categoryName) {
        Spending spending = new Spending();
        spending.setCategory(categoryName);
        spending.setAmount(expense);
        spending.setEpochTimeStamp(System.currentTimeMillis());
        if (!editText.getText().toString().equals(""))
            spending.setDetail(editText.getText().toString());
        else {
            spending.setDetail(getString(R.string.no_detail_text));
        }

        SpendingFetcher expenseFetcher = new SpendingFetcher();
        expenseFetcher.insertSpendings(spending);
        ViewsUtilities.showToast(getActivity(), "Expense Added");
    }


    private void addTotal(int val) {
        String text = mTotalEditText.getText().toString();
        int newTotal;
        if (text.equals("")) {
            newTotal = val;
        } else {
            int existingTotal = Integer.parseInt(text);
            newTotal = existingTotal + val;
        }
        mTotalEditText.setText(Integer.toString(newTotal));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    public void showCustomAlertDialog(String message, final View.OnClickListener onClickListener) {
        view = getLayoutInflater().inflate(R.layout.edit_text_alert_dialog, null);
        editText = view.findViewById(R.id.editText_alert);
        editText.setHint(R.string.detail_hint);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.DialogTheme);
        builder.setTitle("Wallet");
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setView(view);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                onClickListener.onClick(null);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }
}
