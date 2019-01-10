package com.ask.vitevents.RoomDb;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.ask.vitevents.Classes.Tshirt;

@Database(entities = {Tshirt.class}, version = 1)
public abstract class TshirtRoomDatabase extends RoomDatabase {

    public abstract TshirtDao tshirtDao();

    private static TshirtRoomDatabase INSTANCE;

    static TshirtRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TshirtRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TshirtRoomDatabase.class, "tshirt_database")
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            // Migration is not part of this codelab.
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Override the onOpen method to populate the database.
     * For this sample, we clear the database every time it is created or opened.
     */
    private static Callback sRoomDatabaseCallback = new Callback(){

        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
            // If you want to keep the data through app restarts,
            // comment out the following line.
            //new PopulateDbAsync(INSTANCE).execute();
        }
    };


    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final TshirtDao mDao;

        PopulateDbAsync(TshirtRoomDatabase db) {
            mDao = db.tshirtDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            mDao.deleteAll();

            return null;
        }
    }
}
