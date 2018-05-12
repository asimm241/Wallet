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

    @Query("SELECT * FROM " + Spending.TABLE_NAME + " WHERE " + Spending.COLUMN_EPOCH_TIME_STAMP + " >=:start AND " +
            Spending.COLUMN_EPOCH_TIME_STAMP + "<=:end" + " ORDER BY " + Spending.COLUMN_EPOCH_TIME_STAMP + " DESC")
    LiveData<List<Spending>> getPartialSpendings(long start, long end);

    @Query("SELECT * FROM " + Spending.TABLE_NAME + " ORDER BY " + Spending.COLUMN_EPOCH_TIME_STAMP + " DESC")
    LiveData<List<Spending>> getAllSpendings();


}
