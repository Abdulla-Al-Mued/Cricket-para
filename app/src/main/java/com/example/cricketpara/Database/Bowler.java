package com.example.cricketpara.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Bowler {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "innings_id")
    public int innings_id;

    @ColumnInfo(name = "bow_id")
    public int bow_id;

    @ColumnInfo(name = "bowler_name")
    public String bowler_name;

    @ColumnInfo(name = "balls")
    public int balls;

    @ColumnInfo(name = "runs")
    public int runs;

    @ColumnInfo(name = "wickets")
    public int wickets;

    public Bowler(int innings_id, int bow_id, String bowler_name, int balls, int runs, int wickets) {
        this.innings_id = innings_id;
        this.bow_id = bow_id;
        this.bowler_name = bowler_name;
        this.balls = balls;
        this.runs = runs;
        this.wickets = wickets;
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

    public int getBow_id() {
        return bow_id;
    }

    public void setBow_id(int bow_id) {
        this.bow_id = bow_id;
    }

    public String getBowler_name() {
        return bowler_name;
    }

    public void setBowler_name(String bowler_name) {
        this.bowler_name = bowler_name;
    }

    public int getBalls() {
        return balls;
    }

    public void setBalls(int balls) {
        this.balls = balls;
    }

    public int getRuns() {
        return runs;
    }

    public void setRuns(int runs) {
        this.runs = runs;
    }

    public int getWickets() {
        return wickets;
    }

    public void setWickets(int wickets) {
        this.wickets = wickets;
    }
}
