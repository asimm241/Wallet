package com.example.asimm.wallet.database.AsyncTasks;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.example.asimm.wallet.database.dao.BaseDao;
import com.example.asimm.wallet.database.entities.BaseEntity;

import java.util.List;

/**
 * Created by asimm on 4/5/2018.
 */

public class DeleteAllFromTables extends AsyncTask<Integer, Integer, Boolean> {


    public interface DeleteAllFromTablesCallback {
        public void onPreExecute();

        public void onProgressUpdate(int progress);

        public void onPostExecute(boolean done);
    }


    public static final int SPENDING = 1;
    public static final int INCOME = 2;

    private BaseDao baseDao;


    private DeleteAllFromTablesCallback crudAsyncTaskCallback;


    public DeleteAllFromTables(BaseDao genericDao, DeleteAllFromTablesCallback crudAsyncTaskCallback) {
        this.baseDao = genericDao;
        this.crudAsyncTaskCallback = crudAsyncTaskCallback;
    }


    @Override
    public void onPreExecute() {
        super.onPreExecute();
        if (this.crudAsyncTaskCallback != null) {
            this.crudAsyncTaskCallback.onPreExecute();
        }
    }

    @Override
    public Boolean doInBackground(Integer... operations) {
        if (this.baseDao != null) {
            switch (operations[0]) {
                case SPENDING:
                    baseDao.deleteAllSpendings();
                    return true;

                case INCOME:
                    baseDao.deleteAllIncome();
                    return true;

            }
            return false;
        }//getting operation
        return false;
    }


    @Override
    public void onProgressUpdate(Integer... progresses) {
        //getting progress
        if (progresses != null && progresses.length > 0) {
            int progress = progresses[0];
            if (this.crudAsyncTaskCallback != null) {
                this.crudAsyncTaskCallback.onProgressUpdate(progress);
            }
        }
    }

    @Override
    public void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        if (this.crudAsyncTaskCallback != null) {
            this.crudAsyncTaskCallback.onPostExecute(result);
        }

    }

}
