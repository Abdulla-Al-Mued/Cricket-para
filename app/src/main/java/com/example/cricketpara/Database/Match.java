package com.example.cricketpara.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Match implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int match_id;

    @ColumnInfo(name = "innings_1")
    public int innings_1;

    @ColumnInfo(name = "innings_2")
    public int innings_2;

    public Match(int innings_1, int innings_2) {
        this.innings_1 = innings_1;
        this.innings_2 = innings_2;
    }


    public int getMatch_id() {
        return match_id;
    }

    public void setMatch_id(int match_id) {
        this.match_id = match_id;
    }

    public int getInnings_1() {
        return innings_1;
    }

    public void setInnings_1(int innings_1) {
        this.innings_1 = innings_1;
    }

    public int getInnings_2() {
        return innings_2;
    }

    public void setInnings_2(int innings_2) {
        this.innings_2 = innings_2;
    }
}
