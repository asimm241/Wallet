package com.wallet.app.home.views.activities;

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

import com.wallet.app.R;
import com.wallet.app.Utilities.PreferencesUtilities;
import com.wallet.app.Utilities.ViewsUtilities;
import com.wallet.app.home.views.fragments.AddIncomeFragment;
import com.wallet.app.home.views.fragments.HistoryFragment;
import com.wallet.app.home.views.fragments.HomeFragment;


public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager pager;
    TextView incomeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tablayout);
        incomeTextView = findViewById(R.id.tvIncome);
        pager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(pager);

        incomeTextView.setOnClickListener(this);

        pager.addOnPageChangeListener(this);

        tabLayout.getTabAt(1).select();

        incomeTextView.setText("Amount: " + Long.toString(PreferencesUtilities.readIncome()));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvIncome:
                ViewsUtilities.showAlertDialog(MainActivity.this, getString(R.string.app_name), "Clear the Wallet?", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PreferencesUtilities.writeIncome(0);
                        incomeTextView.setText("Amount: " + Long.toString(PreferencesUtilities.readIncome()));
                        ViewsUtilities.showToast(MainActivity.this, "Youy wallet is empty now");
                    }
                });
                break;
            default:
                break;
        }
    }


    public class PagerAdapter extends FragmentPagerAdapter {

        PagerAdapter(FragmentManager fm) {
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
