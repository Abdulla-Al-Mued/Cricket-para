package com.example.cricketpara.Database;


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

    @Query("update BatsMan set ball_played = ball_played + 1, t_run = t_run + :run where bat_id = :b_id and innings_id = :ing_id")
    void inc_batsman(int b_id, int ing_id, int run);

    @Query("update Innings set t_run = t_run + :runs, t_bowled = t_bowled + 1 where innings_id = :ing_id")
    void inc_innings(int ing_id, int runs);

    @Query("update Innings set t_run = t_run + :run where innings_id = :ing_id")
    void inc_innings_run(int ing_id, int run);

    @Query("update Bowler set balls = balls + 1 where innings_id = :ing_id and bow_id = :bow_id")
    void inc_bow(int ing_id, int bow_id);

    @Query("update Bowler set runs = runs + :run+1 where innings_id  = :ing_id and bow_id = :bow")
    void inc_bow_run(int ing_id, int bow, int run);

    @Query("update innings set t_wicket = t_wicket + 1 where innings_id = :ing_id")
    void inc_wicket(int ing_id);

    @Query("update Bowler set wickets = wickets + 1 where innings_id = :ing_id and bow_id = :b_id")
    void inc_bow_wicket(int ing_id, int b_id);

    @Query("update Bowler set runs = runs + :run where innings_id  = :ing_id and bow_id = :bow")
    void inc_bow_run_rt_ball(int ing_id, int bow, int run);

    @Query("update Innings set t_run = t_run + :run+1 where innings_id = :ing_id")
    void inc_ing_no_ball_out(int ing_id, int run);

    @Query("update BatsMan set t_run = t_run + :run where innings_id = :ing_id and bat_id = :b_id")
    void inc_bat_no_ball_out(int ing_id, int run, int b_id);

    @Insert
    void insertInnings(Innings... innings);

    @Insert
    void insertBatsman(BatsMan... batsMan);

    @Insert
    void insertBowler(Bowler... bowler);

    @Insert
    void insertLastBalls(Last_balls... last_balls);

    @Query("select bat_id from Last_balls Where id = (select MAX(id) from Last_balls) and innings_id = :ing_id")
    int getLastBats(int ing_id);

    @Query("select runs from Last_balls Where id = (select MAX(id) from Last_balls)")
    int getLastRuns();

    @Query("select bat_id from BatsMan where status='batting' and innings_id = :ing_id and bat_id != :bId")
    int getBatsMan(int bId, int ing_id);

    @Query("select t_bowled from Innings where innings_id = :ing_id")
    int getBalls(int ing_id);

    @Query("select COUNT(bowler_name) from Bowler where bowler_name = :b_Name and innings_id = :ing_id")
    int getBowlerNumber(String b_Name, int ing_id);

    @Query("select bow_id from Bowler where bow_id = (select Max(bow_id) from bowler) and innings_id = :ing_id")
    int getLastBowlerId(int ing_id);

    @Query("select bowler_name from Bowler where innings_id = :ing_id")
    List<String> getAllBowler(int ing_id);

    @Query("select bat_name from BatsMan where innings_id = :ing_id and status = 'batting'")
    List<String> getBatting(int ing_id);

    @Query("DELETE FROM Last_balls")
    void deleteLastBalls();

    @Query("select bow_id from bowler where bowler_name = :bName and innings_id = :ing_id")
    int getBowIdByName(int ing_id, String bName);

    @Query("select bat_id from BatsMan where bat_id = (select MAX(bat_id) from BatsMan)")
    int getMaxBatsId();

    @Query("update BatsMan set status = 'out' where bat_name = :b_name and innings_id = :ing_id")
    void setBatStatusOut(int ing_id, String b_name);

    @Query("select bat_status from Last_balls where id = (select MAX(id) from last_balls) and innings_id = :ing_id")
    String getLastBallsWhenOut(int ing_id);

    @Query("select bat_id from BatsMan where innings_id = :ing_id and bat_name = :bat_name")
    int getBatIdByName(int ing_id, String bat_name);

    @Query("select t_run from Batsman where innings_id = :ing_id and bat_id = :bat_id")
    int getBatRun(int ing_id, int bat_id);

    @Query("select six from Batsman where innings_id = :ing_id and bat_id = :bat_id")
    int getBatSix(int ing_id, int bat_id);

    @Query("select four from Batsman where innings_id = :ing_id and bat_id = :bat_id")
    int getBatFour(int ing_id, int bat_id);

    @Query("select ball_played from Batsman where innings_id = :ing_id and bat_id = :bat_id")
    int getBatBalls(int ing_id, int bat_id);

    @Query("select balls from Bowler where innings_id= :ing_id and bow_id = :b_id")
    int getBowBall(int ing_id, int b_id);

    @Query("select bat_name from BatsMan where innings_id = :ing_id and bat_id = :bat_id")
    String getBatNameById(int ing_id, int bat_id);

    @Query("select bowler_name from Bowler where innings_id = :ing_id and bow_id = :bo_id")
    String getBowNameById(int ing_id, int bo_id);

    @Query("select runs from Bowler where innings_id = :ing_id and bow_id = :b_id")
    int getBowRun(int ing_id, int b_id);

    @Query("select wickets from Bowler where innings_id = :ing_id and bow_id = :b_id")
    int getBowWick(int ing_id, int b_id);

    @Query("select t_run from innings where innings_id = :ing_id")
    int getIngRun(int ing_id);

    @Query("select t_wicket from innings where innings_id = :ing_id")
    int getIngWicket(int ing_id);

    @Query("select t_bowled from innings where innings_id = :ing_id")
    int getIngOver(int ing_id);
}
