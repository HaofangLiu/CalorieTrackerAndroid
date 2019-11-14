package com.example.ass3final;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface StepsDao {

    @Query("SELECT * FROM steps")
    List<Steps> getAll();

    @Query("SELECT * FROM steps WHERE id = :id LIMIT 1")
    Steps findByID(int id);

    @Query("SELECT * FROM steps WHERE userId = :userId")
    List<Steps> findByuserId(int userId);

    @Insert
    void insertAll(Steps... steps);

    @Insert
    long insert(Steps steps);

    @Delete
    void delete(Steps steps);

    @Update(onConflict = REPLACE)
    public void updateUsers(Steps... steps);

    @Query("DELETE FROM steps")
    void deleteAll();


}
