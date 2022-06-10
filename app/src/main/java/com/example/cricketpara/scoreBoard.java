package com.example.cricketpara;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.cricketpara.Database.AppDatabase;
import com.example.cricketpara.Database.BatsMan;
import com.example.cricketpara.Database.Bowler;
import com.example.cricketpara.Database.Innings;
import com.google.android.material.textfield.TextInputLayout;

public class scoreBoard extends AppCompatActivity {

    Button full_score;
    TextInputLayout over, bat1, bat2, bowler;

    private AlertDialog.Builder dialogueBuilder;
    private AlertDialog dialog;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_boad);

        full_score = findViewById(R.id.full_score);

        createNewMatchDialog();


        full_score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), fullScore.class));
            }
        });


    }


    public void createNewMatchDialog(){

        dialogueBuilder = new AlertDialog.Builder(this);
        final View matchPopupView = getLayoutInflater().inflate(R.layout.popup, null);
        dialogueBuilder.setView(matchPopupView);
        dialog = dialogueBuilder.create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);

        over = matchPopupView.findViewById(R.id.total_over);
        bat1 = matchPopupView.findViewById(R.id.p1);
        bat2 = matchPopupView.findViewById(R.id.p2);
        bowler = matchPopupView.findViewById(R.id.bow_name);
        submit = matchPopupView.findViewById(R.id.add);

        AppDatabase db = AppDatabase.getDb(this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(over.getEditText().getText().toString().trim().isEmpty()){
                    over.getEditText().setError("Enter Over");
                    return;
                }
                if(bat1.getEditText().getText().toString().trim().isEmpty()){
                    bat1.getEditText().setError("Enter First Batsman Name");

                    return;
                }
                if(bat2.getEditText().getText().toString().trim().isEmpty()){
                    bat2.getEditText().setError("Enter Second Batsman name");

                    return;
                }
                if(bowler.getEditText().getText().toString().trim().isEmpty()){
                    bowler.getEditText().setError("Enter Bowler Name");

                    return;
                }
                else {
                    SharedPreferences innings = getSharedPreferences("innings",MODE_PRIVATE);

                    db.userDao().insertInnings(new Innings(innings.getInt("innings_id",0),Integer.parseInt(over.getEditText().getText().toString()),
                            0,0,0));
                    db.userDao().insertBatsman(new BatsMan(innings.getInt("innings_id",0),bat1.getEditText().getText().toString(),
                            1,0,0,0,0));
                    db.userDao().insertBatsman(new BatsMan(innings.getInt("innings_id",0),bat2.getEditText().getText().toString(),
                            2,0,0,0,0));
                    db.userDao().insertBowler(new Bowler(innings.getInt("innings_id",0), 1,bowler.getEditText().getText().toString(),
                            0,0,0));

                    dialog.dismiss();

                }


            }
        });


    }
}
