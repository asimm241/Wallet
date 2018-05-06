package com.example.asimm.wallet;


import android.arch.lifecycle.LifecycleFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asimm.wallet.Utilities.FunctionUtilities;
import com.example.asimm.wallet.Utilities.PreferencesUtilities;
import com.example.asimm.wallet.Utilities.ViewsUtilities;
import com.example.asimm.wallet.database.SpendingsFetcher;
import com.example.asimm.wallet.database.entities.Spending;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class HomeFragment extends LifecycleFragment {


    Unbinder unbinder;

    @BindView(R.id.total)
    EditText mTotalEditText;

    @BindView(R.id.addButton)
    Button mAddButton;

    @BindView(R.id.quickButton1)
    Button mQuickButton1;

    @BindView(R.id.quickButton2)
    Button mQuickButton2;

    @BindView(R.id.quickButton3)
    Button mQuickButton3;

    @BindView(R.id.quickButton4)
    Button mQuickButton4;

    @BindView(R.id.quickButton5)
    Button mQuickButton5;

    @BindView(R.id.quickButton6)
    Button mQuickButton6;

    @BindView(R.id.quickButton7)
    Button mQuickButton7;

    @BindView(R.id.quickButton8)
    Button mQuickButton8;


    TextView incomeTextView;


    final int buttonValues[] = {50, 100, 500, 1000, 5000, 10000, 15000, 50000};


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        incomeTextView = getActivity().findViewById(R.id.tvIncome);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    private void setIncome() {
        String text = mTotalEditText.getText().toString();
        if (text.equals("") || text == null) {
            Toast.makeText(getActivity(), "No Spending Entered", Toast.LENGTH_LONG).show();
            return;
        } else {
            final int expense = Integer.parseInt(text);
            long prevIncome = PreferencesUtilities.readIncome();
            final long newIncome = prevIncome - expense;
            if (newIncome < 0) {
                /*AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("There is not enough money in your wallet. \nAre you sure you want to spend?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                PreferencesUtilities.writeIncome(newIncome);
                                incomeTextView.setText(Long.toString(newIncome));
                                mTotalEditText.setText("");
                            }
                        }).setNegativeButton("NO", null).create().show();*/
                ViewsUtilities.showAlertDialog(getActivity(), "There is not enough money in your wallet. \nAre you sure you want to spend?", new View.OnClickListener() {
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


    @OnClick({R.id.addButton, R.id.quickButton1, R.id.quickButton2, R.id.quickButton3,
            R.id.quickButton4, R.id.quickButton5, R.id.quickButton6, R.id.quickButton7,
            R.id.quickButton8})
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

    private void showCategoryList(final long newIncome, final int expense) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(getActivity());
        builderSingle.setIcon(R.drawable.avd_show_password_1);
        builderSingle.setTitle("Select Category");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("Food");
        arrayAdapter.add("Shopping");
        arrayAdapter.add("Traveling");
        arrayAdapter.add("Utilities");
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
                ViewsUtilities.showAlertDialog(getActivity(), "Add Rs " + mTotalEditText.getText().toString() + " in " + arrayAdapter.getItem(which) + " category?", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String categoryName = arrayAdapter.getItem(which);
                        PreferencesUtilities.writeIncome(newIncome);
                        incomeTextView.setText("RS " + Long.toString(newIncome));
                        mTotalEditText.setText("");
                        addExpenseInDb(expense, categoryName);
                    }
                });


            }
        });
        builderSingle.show();

    }

    private void addExpenseInDb(int expense, String categoryName) {
        ;

        Spending spending = new Spending();
        spending.setCategory(categoryName);
        spending.setAmount(expense);
        spending.setDate(FunctionUtilities.getCurrentTimeStamp());

        SpendingsFetcher expenseFetcher = new SpendingsFetcher();
        expenseFetcher.insertSpendings(spending);
        ViewsUtilities.showToast(getActivity(), "Expense Added");
    }


    private void addTotal(int val) {
        String text = mTotalEditText.getText().toString();
        int newTotal;
        if (text.equals("") || text == null) {
            newTotal = val;
        } else {
            int existingToatl = Integer.parseInt(text);

            newTotal = existingToatl + val;
        }
        mTotalEditText.setText(Integer.toString(newTotal));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();

    }

}
