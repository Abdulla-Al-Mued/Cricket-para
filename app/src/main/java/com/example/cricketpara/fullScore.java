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
import android.widget.TextView;

import com.example.cricketpara.Adapters.batListAdapter;
import com.example.cricketpara.Adapters.bowlListAdapter;
import com.example.cricketpara.Algorithms.BallToOver;
import com.example.cricketpara.Database.AppDatabase;
import com.example.cricketpara.Database.BatsMan;
import com.example.cricketpara.Database.Bowler;

import java.util.List;

public class fullScore extends AppCompatActivity {


    RecyclerView rec_viewBat, rec_viewBow;
    TextView runs, wicket, overs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_score);

        runs = findViewById(R.id.runs);
        wicket = findViewById(R.id.wickets);
        overs = findViewById(R.id.overs);

        SharedPreferences sp = getSharedPreferences("innings",MODE_PRIVATE);

        AppDatabase db = AppDatabase.getDb(this);

        runs.setText(String.valueOf(db.userDao().getIngRun(sp.getInt("innings_id",0))));
        wicket.setText(String.valueOf(db.userDao().getIngWicket(sp.getInt("innings_id",0))));
        overs.setText(BallToOver.convertBallToOver(db.userDao().getIngOver(sp.getInt("innings_id",0))));

        rec_viewBat = findViewById(R.id.bats_rec_view);
        rec_viewBow = findViewById(R.id.bow_rec_view);



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