package com.example.asimm.wallet;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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


public class AddIncomeFragment extends Fragment implements View.OnClickListener {

    Button buttonAddIncome;
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
        incomeTextView = getActivity().findViewById(R.id.tvIncome);
        buttonAddIncome = view.findViewById(R.id.button_add_income);
        addIncomeEditText = view.findViewById(R.id.add_income_editext);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buttonAddIncome.setOnClickListener(this);
    }

    private void addIncomeInDb(long result) {
        Income income = new Income();
        income.setAmount(result);
        income.setDate(FunctionUtilities.getCurrentTimeStamp());
        IncomeFetcher incomeFetcher = new IncomeFetcher();
        incomeFetcher.insertIncome(income);
        ViewsUtilities.showToast(getActivity(), "Income added to the wallet");
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_add_income:
                if (addIncomeEditText.getText().toString() != null && !addIncomeEditText.getText().toString().equals("")) {
                    final long existingIncome = PreferencesUtilities.readIncome();
                    final long enteredIncome = Long.parseLong(addIncomeEditText.getText().toString());


                    ViewsUtilities.showAlertDialog(getActivity(),getString(R.string.app_name), "Add " + String.valueOf(enteredIncome) + " in your wallet?", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            long result = existingIncome + enteredIncome;

                            PreferencesUtilities.writeIncome(result);

                            incomeTextView.setText("Amount: " + Long.toString(result));
                            addIncomeEditText.setText("");
                            addIncomeInDb(enteredIncome);
                        }
                    });
                } else {
                    ViewsUtilities.showToast(getActivity(), "Add an amount first");
                }
                break;
            default:
                break;
        }
    }
}
