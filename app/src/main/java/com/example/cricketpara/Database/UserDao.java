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

}
