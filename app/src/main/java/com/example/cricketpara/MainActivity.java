package com.example.cricketpara;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.content.Intent;
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

                Match data = db.userDao().selectAllItem();
                if(data==null){
                    db.userDao().insertM(new Match(101,102));
                }
                else{
                    int maxId = db.userDao().maxId();
                    db.userDao().insertM(new Match(maxId+2, maxId+3));
                }

                startActivity(new Intent(getApplicationContext(), scoreBoard.class));
            }
        });

        load_match.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), scoreBoard.class));
            }
        });

    }
}