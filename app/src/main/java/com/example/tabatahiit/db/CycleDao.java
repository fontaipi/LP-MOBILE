package com.example.tabatahiit.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CycleDao {
    @Query("SELECT * FROM cycle")
    List<Cycle> getAll();

    @Insert
    void InsertAll(Cycle... cycle);

    @Query("DELETE FROM cycle")
    void nukeTable();
}
