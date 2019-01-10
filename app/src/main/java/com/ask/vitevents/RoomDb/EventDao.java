package com.ask.vitevents.RoomDb;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.ask.vitevents.Classes.Event;

import java.util.List;

/**
 * Created by suraj on 6/8/18.
 */
@Dao
public interface EventDao {

    // LiveData is a data holder class that can be observed within a given lifecycle.
    // Always holds/caches latest version of data. Notifies its active observers when the
    // data has changed. Since we are getting all the contents of the database,
    // we are notified whenever any of the database contents have changed.
    @Query("SELECT * from event_table ORDER BY event ASC")
    LiveData<List<Event>> getAlphabetizedWords();

    @Query("SELECT * from event_table where istrending = 1 ")
    LiveData<List<Event>> getTrendingEvents();



    // We do not need a conflict strategy, because the word is our primary key, and you cannot
    // add two items with the same primary key to the database. If the table has more than one
    // column, you can use @Insert(onConflict = OnConflictStrategy.REPLACE) to update a row.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Event event);

    @Query("DELETE FROM event_table")
    void deleteAll();

    @Query("Select * from event_table where event = :eventid")
    LiveData<Event> getEventById(String eventid);

    @Query("Select * from event_table where schoolid = :id")
    LiveData<List<Event>> getEventBySchool(String id);

    @Query("Select * from event_table where clubid = :id")
    LiveData<List<Event>> getEventByClub(String id);
}
