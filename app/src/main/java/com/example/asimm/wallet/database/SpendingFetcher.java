package com.example.asimm.wallet.database;

import android.arch.lifecycle.LiveData;

import com.example.asimm.wallet.App.App;
import com.example.asimm.wallet.database.AsyncTasks.CRUDAsyncTask;
import com.example.asimm.wallet.database.AsyncTasks.DeleteAllFromTables;
import com.example.asimm.wallet.database.dao.SpendingDao;
import com.example.asimm.wallet.database.entities.Spending;

import java.util.List;

/**
 * Created by asimm on 4/5/2018.
 */

public class SpendingFetcher {
    SpendingDao spendingDao;

    public SpendingFetcher() {
        this.spendingDao = App.getAppDatabase().getSpendingDao();
    }

    public void insertSpendings(Spending spending) {
        CRUDAsyncTask<Spending> insertExpense = new CRUDAsyncTask<Spending>(spendingDao, null, spending);
        insertExpense.execute(CRUDAsyncTask.INSERT);
    }

    public LiveData<List<Spending>> getAllSpendings() {

        return spendingDao.getAllSpendings();
    }

    public LiveData<List<Spending>> getPartialSpendings(long start, long end) {

        return spendingDao.getPartialSpendings(start, end);
    }

    public void deleteAllSpendings() {
        DeleteAllFromTables deleteAllFromTables = new DeleteAllFromTables(spendingDao, null);
        deleteAllFromTables.execute(DeleteAllFromTables.SPENDING);
    }
}
