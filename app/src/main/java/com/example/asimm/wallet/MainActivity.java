package com.example.asimm.wallet;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.example.asimm.wallet.Utilities.PreferencesUtilities;
import com.example.asimm.wallet.Utilities.ViewsUtilities;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    Unbinder unbinder;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tablayout)
    TabLayout tabLayout;

    @BindView(R.id.view_pager)
    ViewPager pager;

    @BindView(R.id.tvIncome)
    TextView incomeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(pager);

        pager.addOnPageChangeListener(this);

        tabLayout.getTabAt(1).select();

        incomeTextView.setText("Amount: " + Long.toString(PreferencesUtilities.readIncome()));
        incomeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewsUtilities.showAlertDialog(MainActivity.this, "Clear the Wallet?", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PreferencesUtilities.writeIncome(0);
                        incomeTextView.setText("Amount: " + Long.toString(PreferencesUtilities.readIncome()));
                        ViewsUtilities.showToast(MainActivity.this, "Youy wallet is empty now");
                    }
                });
            }
        });


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new AddIncomeFragment();
                case 1:
                    return new HomeFragment();
                case 2:
                    return new HistoryFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Add Income";
                case 1:
                    return "Add Expense";
                case 2:
                    return "History";
                default:
                    return null;
            }
        }
    }
}
