package com.example.asimm.wallet.database;

import com.example.asimm.wallet.App.App;
import com.example.asimm.wallet.database.dao.IncomeDoa;
import com.example.asimm.wallet.database.entities.Income;

/**
 * Created by asimm on 4/5/2018.
 */

public class IncomeFetcher {
    private IncomeDoa incomeDoa;

    public IncomeFetcher() {
        incomeDoa = App.getAppDatabase().getIncomeDao();
    }

    public void InsertIncome(Income... incomes){
        incomeDoa.insert(incomes);
    }
}
