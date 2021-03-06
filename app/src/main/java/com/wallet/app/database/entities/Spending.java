package com.wallet.app.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by asimm on 2/1/2018.
 */

@Entity(tableName = "spending")
public class Spending extends BaseEntity {


    @Ignore
    public static final String TABLE_NAME = "spending";

    @Ignore
    public static final String COLUMN_ID = "id";

    @Ignore
    public static final String COLUMN_AMOUNT = "spending_amount";

    @Ignore
    public static final String COLUMN_CATEGORY = "category";

    @Ignore
    public static final String COLUMN_EPOCH_TIME_STAMP = "epoch_time_stamp";

    @Ignore
    public static final String COLUMN_DETAIL = "detail";


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    private long id;

    @ColumnInfo(name = COLUMN_AMOUNT)
    private double amount;

    @ColumnInfo(name = COLUMN_EPOCH_TIME_STAMP)
    private long epochTimeStamp;

    @ColumnInfo(name = COLUMN_CATEGORY)
    private String category;

    @ColumnInfo(name = COLUMN_DETAIL)
    private String detail;

    public long getEpochTimeStamp() {
        return epochTimeStamp;
    }

    public void setEpochTimeStamp(long epochTimeStamp) {
        this.epochTimeStamp = epochTimeStamp;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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


}
