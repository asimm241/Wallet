package com.example.asimm.wallet.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.asimm.wallet.database.dao.IncomeDoa;
import com.example.asimm.wallet.database.dao.SpendingDao;
import com.example.asimm.wallet.database.entities.Income;
import com.example.asimm.wallet.database.entities.Spending;


/**
 * Created by asimm on 2/1/2018.
 */

@Database(entities = {Income.class, Spending.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract IncomeDoa getIncomeDao();

    public abstract SpendingDao getSpendingDao();
}
