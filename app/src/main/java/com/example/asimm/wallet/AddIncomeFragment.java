package com.example.asimm.wallet;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.asimm.wallet.Utilities.FunctionUtilities;
import com.example.asimm.wallet.Utilities.PreferencesUtilities;
import com.example.asimm.wallet.Utilities.ViewsUtilities;
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
        if (addIncomeEditText.getText().toString() != null && !addIncomeEditText.getText().toString().equals("")) {
            final long existingIncome = PreferencesUtilities.readIncome();
            final long enteredIncome = Long.parseLong(addIncomeEditText.getText().toString());


            ViewsUtilities.showAlertDialog(getActivity(), "Add " + String.valueOf(enteredIncome) + " in your wallet?", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    long result = existingIncome + enteredIncome;

                    PreferencesUtilities.writeIncome(result);

                    incomeTextView.setText("Amount: " + Long.toString(result));
                    addIncomeEditText.setText("");
                    addIncomeInDb(enteredIncome);
                }
            });
        }
        else {
            ViewsUtilities.showToast(getActivity(), "Add an amount first");
        }

    }

    private void addIncomeInDb(long result) {
        Income income = new Income();
        income.setAmount(result);
        income.setDate(FunctionUtilities.getCurrentTimeStamp());
        IncomeFetcher incomeFetcher = new IncomeFetcher();
        incomeFetcher.insertIncome(income);
        ViewsUtilities.showToast(getActivity(), "Income added to the wallet");
    }


}
