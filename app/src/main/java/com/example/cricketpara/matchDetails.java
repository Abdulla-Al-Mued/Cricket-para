package com.example.cricketpara;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.cricketpara.Database.AppDatabase;
import com.example.cricketpara.Database.Innings;
import com.example.cricketpara.Database.Match;

public class matchDetails extends AppCompatActivity {

    Button ing1, ing2;
    Match ob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_details);

        SharedPreferences innings = getSharedPreferences("innings",MODE_PRIVATE);
        SharedPreferences.Editor editInnings = innings.edit();
        SharedPreferences sp = getSharedPreferences("Match",MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();


        AppDatabase db = AppDatabase.getDb(this);


        ing1 = findViewById(R.id.ing_1);
        ing2 = findViewById(R.id.ing_2);

        ob = (Match) getIntent().getSerializableExtra("matches");


        edit.putInt("Match_ID",ob.match_id);
        edit.commit();



        ing1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editInnings.putInt("innings_id",ob.innings_1);
                editInnings.commit();

                String ms = db.userDao().getIngStatus(ob.innings_1);


                if(ms.equals("Finished")){

                    startActivity(new Intent(getApplicationContext(), fullScore.class));

                }
                else{
                    startActivity(new Intent(getApplicationContext(), scoreBoard.class));
                }
            }
        });

        ing2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ms2 = db.userDao().getIngStatus(ob.innings_2);
                editInnings.putInt("innings_id",ob.innings_2);
                editInnings.commit();
                String ms = db.userDao().getIngStatus(ob.innings_1);


                if(ms2 == null && ms.equals("Finished")){
                    startActivity(new Intent(getApplicationContext(), scoreBoard.class));
                }
                else if(ms.equals("Finished")){
                    startActivity(new Intent(getApplicationContext(), fullScore.class));
                }
                else
                    Toast.makeText(getApplicationContext(), "Please End first innings first", Toast.LENGTH_SHORT).show();


            }
        });

    }
}