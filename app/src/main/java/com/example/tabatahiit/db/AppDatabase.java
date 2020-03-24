package com.example.tabatahiit.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Cycle.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CycleDao cycleDao();
}
