package com.example.cricketpara;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.cricketpara.Database.Match;

public class matchDetails extends AppCompatActivity {

    Button ing1, ing2;
    Match ob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_details);


        ing1 = findViewById(R.id.ing_1);
        ing2 = findViewById(R.id.ing_2);

        ob = (Match) getIntent().getSerializableExtra("matches");

        ing1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), scoreBoard.class));
            }
        });

    }
}