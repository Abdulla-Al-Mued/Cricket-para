package com.example.cricketpara.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Match.class, Innings.class, BatsMan.class, Bowler.class},version = 2)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "score";
    private static AppDatabase instance;

    public static synchronized AppDatabase getDb(Context context){

        if(instance == null){
            instance = Room.databaseBuilder(context, AppDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }

        return instance;

    }

    public abstract UserDao userDao();

}
