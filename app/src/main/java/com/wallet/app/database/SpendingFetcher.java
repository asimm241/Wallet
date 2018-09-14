package com.wallet.app.database;

import android.arch.lifecycle.LiveData;

import com.wallet.app.application.App;
import com.wallet.app.database.AsyncTasks.CRUDAsyncTask;
import com.wallet.app.database.AsyncTasks.DeleteAllFromTables;
import com.wallet.app.database.dao.SpendingDao;
import com.wallet.app.database.entities.Spending;

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
