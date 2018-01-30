package com.example.asimm.wallet;

import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asimm.wallet.Utilities.PreferencesUtilities;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import butterknife.internal.Utils;

public class MainActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViews();
    }

    private void setupViews() {
        unbinder = ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        setupDrawer();
    }

    private void setupDrawer() {


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
        String text = mTotalEditText.getText().toString();
        if (text.equals("") || text == null) {
            Toast.makeText(getApplicationContext(), "No Spending Entered", Toast.LENGTH_LONG).show();
            return;
        } else {
            float expense = Float.parseFloat(text);
            double prevIncome = PreferencesUtilities.readIncome();
            final double newIncome = prevIncome - expense;
            if (newIncome < 0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Your Income is less than you spending. \n are you sure you want to spend")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                PreferencesUtilities.writeIncome(newIncome);
                                incomeTextView.setText(Double.toString(newIncome));
                                mTotalEditText.setText("");
                            }
                        }).setNegativeButton("NO", null).create().show();
            } else {
                PreferencesUtilities.writeIncome(newIncome);
                incomeTextView.setText(Double.toString(newIncome));
                mTotalEditText.setText("");

            }
        }

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
