package com.example.cricketpara.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Last_balls {

    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "bowler_id")
    int bowler_id;

    @ColumnInfo(name = "bat_id")
    int bat_id;

    @ColumnInfo(name = "runs")
    int runs;

    @ColumnInfo(name = "extra")
    int extra;

    public Last_balls(int bowler_id, int bat_id, int runs, int extra) {
        this.bowler_id = bowler_id;
        this.bat_id = bat_id;
        this.runs = runs;
        this.extra = extra;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBowler_id() {
        return bowler_id;
    }

    public void setBowler_id(int bowler_id) {
        this.bowler_id = bowler_id;
    }

    public int getBat_id() {
        return bat_id;
    }

    public void setBat_id(int bat_id) {
        this.bat_id = bat_id;
    }

    public int getRuns() {
        return runs;
    }

    public void setRuns(int runs) {
        this.runs = runs;
    }

    public int getExtra() {
        return extra;
    }

    public void setExtra(int extra) {
        this.extra = extra;
    }
}
