package com.wallet.app.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.wallet.app.database.dao.IncomeDoa;
import com.wallet.app.database.dao.SpendingDao;
import com.wallet.app.database.entities.Income;
import com.wallet.app.database.entities.Spending;


/**
 * Created by asimm on 2/1/2018.
 */

@Database(entities = {Income.class, Spending.class}, version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract IncomeDoa getIncomeDao();

    public abstract SpendingDao getSpendingDao();
}
