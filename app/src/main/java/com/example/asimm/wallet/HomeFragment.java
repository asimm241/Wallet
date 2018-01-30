package com.example.asimm.wallet;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class HomeFragment extends Fragment {


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

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.income)
    TextView incomeTextView;

    Unbinder unbinder;


    final int buttonValues[] = {50, 100, 500, 1000, 5000, 10000, 15000, 50000};


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews();
    }

    private void setupViews() {
        unbinder = ButterKnife.bind(getActivity());
    }

    @OnClick({R.id.addButton, R.id.quickButton1, R.id.quickButton2, R.id.quickButton3, R.id.quickButton4, R.id.quickButton5, R.id.quickButton6, R.id.quickButton7, R.id.quickButton8})
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

    private void setIncome() {
        String text = incomeTextView.getText().toString();

    }

    private void addTotal(float val) {
        String text = mTotalEditText.getText().toString();
        float newTotal;
        if (text.equals("") || text == null) {
            newTotal = val;
        } else {
            float existingToatl = Float.parseFloat(text);

            newTotal = existingToatl + val;
        }
        mTotalEditText.setText(Float.toString(newTotal));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();

    }

}