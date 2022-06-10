package com.example.cricketpara.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Innings {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "innings_id")
    public int innings_id;

    @ColumnInfo(name = "t_over")
    public int t_over;

    @ColumnInfo(name = "t_run")
    public int t_run;

    @ColumnInfo(name = "t_wicket")
    public int t_wicket;

    @ColumnInfo(name = "t_bowled")
    public int t_bowled;


    public Innings(int innings_id, int t_over, int t_run, int t_wicket, int t_bowled) {
        this.innings_id = innings_id;
        this.t_over = t_over;
        this.t_run = t_run;
        this.t_wicket = t_wicket;
        this.t_bowled = t_bowled;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInnings_id() {
        return innings_id;
    }

    public void setInnings_id(int innings_id) {
        this.innings_id = innings_id;
    }

    public int getT_over() {
        return t_over;
    }

    public void setT_over(int t_over) {
        this.t_over = t_over;
    }

    public int getT_run() {
        return t_run;
    }

    public void setT_run(int t_run) {
        this.t_run = t_run;
    }

    public int getT_wicket() {
        return t_wicket;
    }

    public void setT_wicket(int t_wicket) {
        this.t_wicket = t_wicket;
    }

    public int getT_bowled() {
        return t_bowled;
    }

    public void setT_bowled(int t_bowled) {
        this.t_bowled = t_bowled;
    }
}
