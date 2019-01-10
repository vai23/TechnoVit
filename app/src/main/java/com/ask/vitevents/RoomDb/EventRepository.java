package com.ask.vitevents.RoomDb;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.ask.vitevents.Classes.Event;

import java.util.List;

/**
 * Created by suraj on 6/8/18.
 */

public class EventRepository {

    private EventDao mEventDao;
    private LiveData<List<Event>> mAllWords;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    EventRepository(Application application) {
        EventRoomDatabase db = EventRoomDatabase.getDatabase(application);
        mEventDao = db.eventDao();
        mAllWords = mEventDao.getAlphabetizedWords();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Event>> getAllEvents() {
        return mAllWords;
    }

    LiveData<List<Event>> getTrendingEvents() {
        return mEventDao.getTrendingEvents();
    }

    LiveData<Event> getEventById(String id)
    {
        return mEventDao.getEventById(id);
    }

    LiveData<List<Event>> getEventBySchool(String id)
    {
        return mEventDao.getEventBySchool(id);
    }

    LiveData<List<Event>> getEventByClub(String id)
    {
        return mEventDao.getEventByClub(id);
    }

    // You must call this on a non-UI thread or your app will crash.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    public void insert (Event word) {
        new insertAsyncTask(mEventDao).execute(word);
    }

    private static class insertAsyncTask extends AsyncTask<Event, Void, Void> {

        private EventDao mAsyncTaskDao;

        insertAsyncTask(EventDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Event... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
