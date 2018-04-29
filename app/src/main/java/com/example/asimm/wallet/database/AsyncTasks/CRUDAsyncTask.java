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

public class CRUDAsyncTask<T> extends AsyncTask<Integer, Integer, Boolean> {


    public interface CRUDAsyncTaskCallback {
        public void onPreExecute();

        public void onProgressUpdate(int progress);

        public void onPostExecute(boolean done);
    }

    public static final int INSERT = 1;
    public static final int DELETE = 2;
    public static final int UPDATE = 3;

    private BaseDao<T> baseDao;

    private T[] entities;

    private CRUDAsyncTaskCallback crudAsyncTaskCallback;

    private MutableLiveData<Boolean> crudAsyncTaskLiveData;

    public CRUDAsyncTask(BaseDao genericDao, CRUDAsyncTaskCallback crudAsyncTaskCallback, T... entities) {
        this.baseDao = genericDao;
        this.entities = entities;
        this.crudAsyncTaskCallback = crudAsyncTaskCallback;
        this.crudAsyncTaskLiveData = new MutableLiveData<>();
    }

    public CRUDAsyncTask(BaseDao genericDao, CRUDAsyncTaskCallback crudAsyncTaskCallback, List<T> entities) {
        this.baseDao = genericDao;
        this.entities = (T[]) entities.toArray();
        this.crudAsyncTaskCallback = crudAsyncTaskCallback;
        this.crudAsyncTaskLiveData = new MutableLiveData<>();
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
            //getting operation
            int operation = operations[0];
            switch (operation) {
                case INSERT: {
                    this.baseDao.insert(this.entities);
                    return true;
                }

                case DELETE: {
                    this.baseDao.delete(this.entities);
                    return true;
                }

                case UPDATE: {
                    this.baseDao.update(this.entities);
                    return true;
                }

                default: {
                    return false;
                }
            }
        }

        return false;
    }

    public LiveData<Boolean> getAsyncTaskLiveData() {
        return this.crudAsyncTaskLiveData;
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
        if (this.crudAsyncTaskLiveData != null) {
            this.crudAsyncTaskLiveData.setValue(result);
        }

    }

}
