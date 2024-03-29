package com.example.cricketpara;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cricketpara.Database.AppDatabase;
import com.example.cricketpara.Database.Match;

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

    @Override
    public void onBackPressed(){

        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                        //System.exit(0);
                    }
                })
                .setNegativeButton("No",null)
                .show();

    }
}