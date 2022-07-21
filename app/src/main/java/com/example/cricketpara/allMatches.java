package com.example.cricketpara;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.cricketpara.Adapters.allMatchAdapter;
import com.example.cricketpara.Database.AppDatabase;
import com.example.cricketpara.Database.Match;

import java.util.List;

public class allMatches extends AppCompatActivity {


    RecyclerView rec_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_matches);

        rec_view = findViewById(R.id.rec_view);
        AppDatabase db = AppDatabase.getDb(this);
        rec_view.setLayoutManager(new LinearLayoutManager(this));
        List<Match> allMatch = db.userDao().getAllMatch();

        allMatchAdapter adapter = new allMatchAdapter(allMatch, this);
        rec_view.setAdapter(adapter);


    }

    @Override
    public void onBackPressed(){

        startActivity(new Intent(getApplicationContext(), MainActivity.class));

    }

}