package com.example.asimm.wallet.database.dao;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Update;

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
}
