package com.ask.vitevents.RoomDb;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.ask.vitevents.Classes.Tshirt;

import java.util.List;

public class TshirtRepositry {

    private TshirtDao mtshirtDao;
    private LiveData<List<Tshirt>> mAllTshirt;

    public TshirtRepositry(Application application) {

        TshirtRoomDatabase db = TshirtRoomDatabase.getDatabase(application);
        mtshirtDao = db.tshirtDao();
        mAllTshirt = mtshirtDao.getAllTshirt();
    }

    LiveData<List<Tshirt>> getAllTshirt(){return mAllTshirt;}

    LiveData<Tshirt> getTshirtById(String id){
        return mtshirtDao.getTshirtById(id);
    }



    String getTshirtFront(String id){
        return mtshirtDao.getTshirtFrontById(id);
    }

    String getTshirtBack(String id){
        return mtshirtDao.getTshirtBackById(id);
    }

    String getTshirtSide(String id){
        return mtshirtDao.getTshirtSideById(id);
    }

    public void insertTshirt(Tshirt tshirt)
    {
        new insertAsyncTask(mtshirtDao).execute(tshirt);
    }

    private static class insertAsyncTask extends AsyncTask<Tshirt, Void, Void> {

        private TshirtDao mAsyncTaskDao;

        insertAsyncTask(TshirtDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Tshirt... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

}
