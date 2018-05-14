package com.example.asimm.wallet.database.dao;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.asimm.wallet.database.entities.Income;
import com.example.asimm.wallet.database.entities.Spending;

/**
 * Created by asimm on 4/5/2018.
 */


public interface BaseDao<T> {

    @Insert
    public void insert(T... entities);

    @Delete
    void delete(T... entities);

    @Update
    void update(T... entities);

    @Query("DELETE  FROM " + Spending.TABLE_NAME)
    void deleteAllSpendings();

    @Query("DELETE  FROM " + Income.TABLE_NAME)
    void deleteAllIncome();
}
