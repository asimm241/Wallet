package com.example.asimm.wallet.database;

import android.arch.lifecycle.LiveData;

import com.example.asimm.wallet.App.App;
import com.example.asimm.wallet.database.AsyncTasks.CRUDAsyncTask;
import com.example.asimm.wallet.database.dao.SpendingDao;
import com.example.asimm.wallet.database.entities.Spending;

import java.util.List;

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
        insertExpense.execute(CRUDAsyncTask.INSERT);
    }

    public LiveData<List<Spending>> getAllSpendings() {

        return spendingDao.getAllSpendings();
    }

    public LiveData<List<Spending>> getPartialSpendings(String start,String end) {

        return spendingDao.getPartialSpendings(start, end);
    }
}
