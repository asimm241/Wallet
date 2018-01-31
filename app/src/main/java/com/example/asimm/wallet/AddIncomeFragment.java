package com.example.asimm.wallet;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asimm.wallet.Utilities.PreferencesUtilities;

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
        long existingIncome = PreferencesUtilities.readIncome();
        long enteredIncome = Long.parseLong(addIncomeEditText.getText().toString());

        long result = existingIncome + enteredIncome;

        PreferencesUtilities.writeIncome(result);

        incomeTextView.setText(Long.toString(result));
        addIncomeEditText.setText("");
        Toast.makeText(getContext(), "Total Income:" + result, Toast.LENGTH_LONG).show();
    }

}
