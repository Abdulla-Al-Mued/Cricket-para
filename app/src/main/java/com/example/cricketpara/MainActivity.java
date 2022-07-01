package com.example.cricketpara;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.cricketpara.Database.AppDatabase;
import com.example.cricketpara.Database.Match;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button new_match, load_match;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new_match = findViewById(R.id.new_match);
        load_match = findViewById(R.id.load_match);

        AppDatabase db = AppDatabase.getDb(this);

        new_match.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sp = getSharedPreferences("Match",MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                SharedPreferences innings = getSharedPreferences("innings",MODE_PRIVATE);
                SharedPreferences.Editor editInnings = innings.edit();

                Match data = db.userDao().selectAllItem();
                if(data==null){
                    db.userDao().insertM(new Match(101,102));
                    edit.putInt("Match_ID",1);
                    editInnings.putInt("innings_id",101);

                }
                else{
                    int maxId = db.userDao().maxId();
                    int MaxId = db.userDao().MaxId();
                    db.userDao().insertM(new Match(maxId+2, maxId+3));
                    edit.putInt("Match_ID",MaxId);
                    editInnings.putInt("innings_id",maxId+2);

                }
                edit.commit();
                editInnings.commit();

                startActivity(new Intent(getApplicationContext(), scoreBoard.class));
            }
        });

        load_match.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), allMatches.class));
            }
        });

    }
}