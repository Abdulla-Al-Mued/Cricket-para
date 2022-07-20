package com.example.cricketpara;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.cricketpara.Adapters.batListAdapter;
import com.example.cricketpara.Adapters.bowlListAdapter;
import com.example.cricketpara.Database.AppDatabase;
import com.example.cricketpara.Database.BatsMan;
import com.example.cricketpara.Database.Bowler;

import java.util.List;

public class fullScore extends AppCompatActivity {


    RecyclerView rec_viewBat, rec_viewBow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_score);

        SharedPreferences sp = getSharedPreferences("innings",MODE_PRIVATE);


        rec_viewBat = findViewById(R.id.bats_rec_view);
        rec_viewBow = findViewById(R.id.bow_rec_view);

        AppDatabase db = AppDatabase.getDb(this);

        rec_viewBat.setLayoutManager(new LinearLayoutManager(this));
        rec_viewBow.setLayoutManager(new LinearLayoutManager(this));

        List<BatsMan> allBat = db.userDao().getAllBatsMan(sp.getInt("innings_id",0));
        List<Bowler> allBow = db.userDao().getBowlers(sp.getInt("innings_id",0));

        batListAdapter batAdapter = new batListAdapter(allBat, this);
        bowlListAdapter bowAdapter = new bowlListAdapter(allBow, this);

        rec_viewBat.setAdapter(batAdapter);
        rec_viewBow.setAdapter(bowAdapter);


    }

    @Override
    public void onBackPressed(){
        SharedPreferences sp = getSharedPreferences("innings",MODE_PRIVATE);
        AppDatabase db = AppDatabase.getDb(this);
        String ms = db.userDao().getIngStatus(sp.getInt("innings_id",0));

        if(ms.equals("Finished")){

            startActivity(new Intent(getApplicationContext(), allMatches.class));

        }
        else
            startActivity(new Intent(getApplicationContext(), scoreBoard.class));


    }



}