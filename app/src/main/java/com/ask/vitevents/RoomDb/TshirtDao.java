package com.ask.vitevents.RoomDb;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.ask.vitevents.Classes.Tshirt;

import java.util.List;

@Dao
public interface TshirtDao {

    @Query("SELECT * from tshirt_table ORDER BY idtshirt ASC")
    LiveData<List<Tshirt>> getAllTshirt();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Tshirt tshirt);

    @Query("DELETE FROM tshirt_table")
    void deleteAll();

    @Query("Select * from tshirt_table where idtshirt = :tshirt")
    LiveData<Tshirt> getTshirtById(String tshirt);

    @Query("Select tshirtside from tshirt_table where idtshirt = :tshirt")
    String getTshirtSideById(String tshirt);

    @Query("Select tshirtfront from tshirt_table where idtshirt = :tshirt")
    String getTshirtFrontById(String tshirt);

    @Query("Select tshirtback from tshirt_table where idtshirt = :tshirt")
    String getTshirtBackById(String tshirt);

}
