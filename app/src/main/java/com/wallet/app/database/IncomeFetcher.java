package com.wallet.app.database;

import com.wallet.app.application.App;
import com.wallet.app.database.AsyncTasks.CRUDAsyncTask;
import com.wallet.app.database.dao.IncomeDoa;
import com.wallet.app.database.entities.Income;

/**
 * Created by asimm on 4/5/2018.
 */

public class IncomeFetcher {
    private IncomeDoa incomeDoa;

    public IncomeFetcher() {
        incomeDoa = App.getAppDatabase().getIncomeDao();
    }

    public void insertIncome(Income... incomes) {
        CRUDAsyncTask<Income> crudAsyncTask = new CRUDAsyncTask<Income>(incomeDoa, null, incomes);
        crudAsyncTask.execute(CRUDAsyncTask.INSERT);
    }
}
