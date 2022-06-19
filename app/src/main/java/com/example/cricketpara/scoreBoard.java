package com.example.cricketpara;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cricketpara.Database.AppDatabase;
import com.example.cricketpara.Database.BatsMan;
import com.example.cricketpara.Database.Bowler;
import com.example.cricketpara.Database.Innings;
import com.example.cricketpara.Database.Last_balls;
import com.google.android.material.textfield.TextInputLayout;

public class scoreBoard extends AppCompatActivity {

    Button full_score;
    TextInputLayout over, bat1, bat2, bowler;

    private AlertDialog.Builder dialogueBuilder;
    private AlertDialog dialog;
    private Button submit, dot, one, two, three, four, six, wide, no, out, change_bowler;
    private TextView balls, runs, wickets, bat_man1, bat_man2, Bowler,
            p1_runs, p1_balls, p1_4s, p1_6s,p2_runs, p2_balls, p2_4s, p2_6s,
            bow_over, bow_runs, bow_eco, bow_wicket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_boad);

        full_score = findViewById(R.id.full_score);
        dot = findViewById(R.id.dot);
        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);
        six = findViewById(R.id.six);
        wide = findViewById(R.id.wide);
        no = findViewById(R.id.no);
        out = findViewById(R.id.out);
        change_bowler = findViewById(R.id.change_bower);

        //text views
        balls = findViewById(R.id.balls);
        runs = findViewById(R.id.runs);
        wickets = findViewById(R.id.wickets);
        bat_man1 = findViewById(R.id.bat1);
        bat_man2 = findViewById(R.id.bat2);
        Bowler = findViewById(R.id.bowler);

        //player one
        p1_runs = findViewById(R.id.p1_runs);
        p1_balls = findViewById(R.id.p1_balls);
        p1_4s = findViewById(R.id.p1_4s);
        p1_6s = findViewById(R.id.p1_6s);

        //player two
        p2_runs = findViewById(R.id.p2_runs);
        p2_balls = findViewById(R.id.p2_balls);
        p2_4s = findViewById(R.id.p2_4s);
        p2_6s = findViewById(R.id.p2_6s);

        //bowler
        bow_over = findViewById(R.id.bow_over);
        bow_runs = findViewById(R.id.bow_runs);
        bow_eco = findViewById(R.id.bow_eco);
        bow_wicket = findViewById(R.id.bow_wicket);

        SharedPreferences sp = getSharedPreferences("innings",MODE_PRIVATE);

        AppDatabase db = AppDatabase.getDb(this);
        String b1 = db.userDao().batInfo(1, sp.getInt("innings_id",0));
        String b2 = db.userDao().batInfo(2, sp.getInt("innings_id",0));

        bat_man1.setText(b1);
        bat_man2.setText(b2);


        createNewMatchDialog();

        setAllOperation();


        full_score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), fullScore.class));
            }
        });


    }

    private void setAllOperation() {
        SharedPreferences sp = getSharedPreferences("innings",MODE_PRIVATE);
        AppDatabase db = AppDatabase.getDb(this);

        dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int lastBat,lastRun, bat_id, balls;
                lastBat = db.userDao().getLastBats();
                lastRun = db.userDao().getLastRuns();
                balls = db.userDao().getBalls(sp.getInt("innings_id",0));


                if(lastRun % 2 == 0 && balls % 6 !=0)
                    bat_id = lastBat;
                else if(lastRun % 2 != 0 && balls % 6 ==0)
                    bat_id = lastBat;
                else
                    bat_id = db.userDao().getBatsMan(lastBat,sp.getInt("innings_id",0));



                db.userDao().inc_innings(sp.getInt("innings_id",0),0);
                db.userDao().inc_batsman(bat_id,sp.getInt("innings_id",0),0);
                db.userDao().inc_bow(sp.getInt("innings_id",0),1);
                db.userDao().insertLastBalls(new Last_balls(1,bat_id,0,0));

                balls = db.userDao().getBalls(sp.getInt("innings_id",0));


                if(balls % 6 == 0){

                    changeBowlerDialog();

                }

            }
        });

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                int lastBat,lastRun, bat_id, balls;
                lastBat = db.userDao().getLastBats();
                lastRun = db.userDao().getLastRuns();
                balls = db.userDao().getBalls(sp.getInt("innings_id",0));


                if(lastRun % 2 == 0 && balls % 6 !=0)
                    bat_id = lastBat;
                else if(lastRun % 2 != 0 && balls % 6 ==0)
                    bat_id = lastBat;
                else
                    bat_id = db.userDao().getBatsMan(lastBat,sp.getInt("innings_id",0));


                db.userDao().inc_innings(sp.getInt("innings_id",0),1);
                db.userDao().inc_batsman(bat_id,sp.getInt("innings_id",0),1);
                db.userDao().inc_bow(sp.getInt("innings_id",0),1);
                db.userDao().insertLastBalls(new Last_balls(1,bat_id,1,0));

                balls = db.userDao().getBalls(sp.getInt("innings_id",0));


                if(balls % 6 == 0){

                    changeBowlerDialog();

                }


            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        wide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }


    public void changeBowlerDialog(){

        dialogueBuilder = new AlertDialog.Builder(this);
        final View matchPopupView = getLayoutInflater().inflate(R.layout.change_bowler, null);
        dialogueBuilder.setView(matchPopupView);
        dialog = dialogueBuilder.create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);

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
                            1,0,0,0,0, "batting"));
                    db.userDao().insertBatsman(new BatsMan(innings.getInt("innings_id",0),bat2.getEditText().getText().toString(),
                            2,0,0,0,0,"batting"));
                    db.userDao().insertBowler(new Bowler(innings.getInt("innings_id",0), 1,bowler.getEditText().getText().toString(),
                            0,0,0));

                    /*SharedPreferences bat = getSharedPreferences("striker",MODE_PRIVATE);
                    SharedPreferences.Editor edit = bat.edit();
                    edit.putInt("strike",1);
                    edit.commit();

                    SharedPreferences bow = getSharedPreferences("Bowler",MODE_PRIVATE);
                    SharedPreferences.Editor bow1 = bow.edit();
                    bow1.putInt("bowler",1);
                    bow1.commit();*/

                    db.userDao().insertLastBalls(new Last_balls(1,1,0,0));

                    dialog.dismiss();

                }


            }
        });


    }
}
