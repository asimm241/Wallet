package com.wallet.app.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by asimm on 2/1/2018.
 */

@Entity(tableName = "income")
public class Income extends BaseEntity{

    @Ignore
    public static final String TABLE_NAME = "income";

    @Ignore
    public static final String COLUMN_ID = "id";

    @Ignore
    public static final String COLUMN_AMOUNT = "amount";

    @Ignore
    public static final String COLUMN_DATE = "date";


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    private long id;

    @ColumnInfo(name = COLUMN_AMOUNT)
    private double amount;

    @ColumnInfo(name = COLUMN_DATE)
    private String date;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
