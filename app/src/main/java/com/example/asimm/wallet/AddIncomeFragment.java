package com.example.asimm.wallet;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asimm.wallet.Utilities.FunctionUtilities;
import com.example.asimm.wallet.Utilities.PreferencesUtilities;
import com.example.asimm.wallet.database.IncomeFetcher;
import com.example.asimm.wallet.database.entities.Income;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class AddIncomeFragment extends Fragment {

    Unbinder unbinder;

    @BindView(R.id.button_add_income)
    Button buttonAddIncome;

    @BindView(R.id.add_income_editext)
    EditText addIncomeEditText;
    private TextView incomeTextView;

    public AddIncomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_income, container, false);
        // Inflate the layout for this fragment
        unbinder = ButterKnife.bind(this, view);

        incomeTextView = getActivity().findViewById(R.id.tvIncome);

        return view;

    }

    @OnClick(R.id.button_add_income)
    public void onClickAddIncome(View view) {
        final long existingIncome = PreferencesUtilities.readIncome();
        final long enteredIncome = Long.parseLong(addIncomeEditText.getText().toString());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Add " + String.valueOf(enteredIncome) + " in your wallet?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        long result = existingIncome + enteredIncome;

                        PreferencesUtilities.writeIncome(result);

                        incomeTextView.setText("RS " + Long.toString(result));
                        addIncomeEditText.setText("");
                        addIncomeInDb(enteredIncome);
                        Toast.makeText(getContext(), "Total Income:" + result, Toast.LENGTH_LONG).show();
                    }
                }).setNegativeButton("NO", null).create().show();


    }

    private void addIncomeInDb(long result) {
        Income income = new Income();
        income.setAmount(result);
        income.setDate(FunctionUtilities.getCurrentTimeStamp());
        IncomeFetcher incomeFetcher = new IncomeFetcher();
        incomeFetcher.insertIncome(income);
    }


}
