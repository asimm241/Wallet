package com.example.asimm.wallet.database;

import com.example.asimm.wallet.App.App;
import com.example.asimm.wallet.database.AsyncTasks.CRUDAsyncTask;
import com.example.asimm.wallet.database.dao.SpendingDao;
import com.example.asimm.wallet.database.entities.Spending;

/**
 * Created by asimm on 4/5/2018.
 */

public class SpendingsFetcher {
    SpendingDao spendingDao;

    public SpendingsFetcher() {
        this.spendingDao = App.getAppDatabase().getSpendingDao();
    }

    public void insertSpendings(Spending spending) {
        CRUDAsyncTask<Spending> insertExpense = new CRUDAsyncTask<Spending>(spendingDao, null, spending);
    }
}
