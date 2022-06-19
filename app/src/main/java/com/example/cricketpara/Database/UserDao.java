package com.example.cricketpara.Database;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insertM(Match... match);

    @Query("select * from `Match` limit 1")
    Match selectAllItem();

    @Query("select innings_1 from `Match` where match_id = (select MAX(match_id) from `Match`)")
    int maxId();

    @Query("select MAX(match_id) from `Match`")
    int MaxId();

    @Query("select bat_name from BatsMan where bat_id = :b_id and innings_id = :ings_id")
    String batInfo(int b_id, int ings_id);

    @Query("update BatsMan set ball_played = ball_played + 1, t_run = t_run + :run where bat_id = :b_id and innings_id = :ing_id")
    void inc_batsman(int b_id, int ing_id, int run);

    @Query("update Innings set t_run = t_run + :runs, t_bowled = t_bowled + 1 where innings_id = :ing_id")
    void inc_innings(int ing_id, int runs);

    @Query("update Bowler set balls = balls + 1 where innings_id = :ing_id and bow_id = :bow_id")
    void inc_bow(int ing_id, int bow_id);

    @Insert
    void insertInnings(Innings... innings);

    @Insert
    void insertBatsman(BatsMan... batsMan);

    @Insert
    void insertBowler(Bowler... bowler);

    @Insert
    void insertLastBalls(Last_balls... last_balls);

    @Query("select bat_id from Last_balls Where id = (select MAX(id) from Last_balls)")
    int getLastBats();

    @Query("select runs from Last_balls Where id = (select MAX(id) from Last_balls)")
    int getLastRuns();

    @Query("select bat_id from BatsMan where status='batting' and innings_id = :ing_id and bat_id != :bId")
    int getBatsMan(int bId, int ing_id);

    @Query("select t_bowled from Innings where innings_id = :ing_id")
    int getBalls(int ing_id);


}
