package com.example.asimm.wallet.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.example.asimm.wallet.database.entities.Spending;

import java.util.List;

/**
 * Created by asimm on 2/1/2018.
 */

@Dao
public interface SpendingDao extends BaseDao<Spending> {

    @Query("SELECT * FROM " + Spending.TABLE_NAME + " WHERE " + Spending.COLUMN_DATE + " >=:start AND " +
            Spending.COLUMN_DATE + "<=:end ORDER BY " + Spending.COLUMN_DATE + " DESC ")
    LiveData<List<Spending>> getPartialSpendings(String start, String end);

    @Query("SELECT * FROM " + Spending.TABLE_NAME + " ORDER BY " + Spending.COLUMN_DATE + " DESC " +
            "" +
            "" +
            "")
    LiveData<List<Spending>> getAllSpendings();
}
