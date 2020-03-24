package com.example.tabatahiit.db;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cycle")
public class Cycle {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "time_set")
    private int time_set;

    @ColumnInfo(name = "time_rest")
    private int time_rest;

    @ColumnInfo(name = "nb_serie")
    private int nb_serie;

    public Cycle(int time_set, int time_rest, int nb_serie) {
        this.time_set = time_set;
        this.time_rest = time_rest;
        this.nb_serie = nb_serie;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTime_set() {
        return time_set;
    }

    public void setTime_set(int time_set) {
        this.time_set = time_set;
    }

    public int getTime_rest() {
        return time_rest;
    }

    public void setTime_rest(int time_rest) {
        this.time_rest = time_rest;
    }

    public int getNb_serie() {
        return nb_serie;
    }

    public void setNb_serie(int nb_serie) {
        this.nb_serie = nb_serie;
    }
}
