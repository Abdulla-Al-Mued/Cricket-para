package com.example.cricketpara.Database;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class BatsMan {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "innings_id")
    public int innings_id;

    @ColumnInfo(name = "bat_name")
    public String bat_name;

    @ColumnInfo(name = "bat_id")
    public int bat_id;

    @ColumnInfo(name = "ball_played")
    public int ball_played;

    @ColumnInfo(name = "t_run")
    public int t_run;

    @ColumnInfo(name = "six")
    public int six;

    @ColumnInfo(name = "four")
    public int four;

    @ColumnInfo(name = "status")
    public String status;

    public BatsMan(int innings_id, String bat_name, int bat_id, int ball_played, int t_run, int six, int four, String status) {
        this.innings_id = innings_id;
        this.bat_name = bat_name;
        this.bat_id = bat_id;
        this.ball_played = ball_played;
        this.t_run = t_run;
        this.six = six;
        this.four = four;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getBat_name() {
        return bat_name;
    }

    public void setBat_name(String bat_name) {
        this.bat_name = bat_name;
    }

    public int getBat_id() {
        return bat_id;
    }

    public void setBat_id(int bat_id) {
        this.bat_id = bat_id;
    }

    public int getBall_played() {
        return ball_played;
    }

    public void setBall_played(int ball_played) {
        this.ball_played = ball_played;
    }

    public int getT_run() {
        return t_run;
    }

    public void setT_run(int t_run) {
        this.t_run = t_run;
    }

    public int getSix() {
        return six;
    }

    public void setSix(int six) {
        this.six = six;
    }

    public int getFour() {
        return four;
    }

    public void setFour(int four) {
        this.four = four;
    }
}
