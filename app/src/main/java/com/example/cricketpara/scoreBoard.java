package com.example.cricketpara;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cricketpara.Adapters.lastBallAdapter;
import com.example.cricketpara.Algorithms.BallToOver;
import com.example.cricketpara.Algorithms.Economy;
import com.example.cricketpara.Database.AppDatabase;
import com.example.cricketpara.Database.BatsMan;
import com.example.cricketpara.Database.Bowler;
import com.example.cricketpara.Database.Innings;
import com.example.cricketpara.Database.Last_balls;
import com.example.cricketpara.Database.Match;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class scoreBoard extends AppCompatActivity {

    Button full_score;
    TextInputLayout over, bat1, bat2, bowler, newBowler, bat_name;
    LinearLayout add_bowler_layout,select_bowler_layout;

    private AlertDialog.Builder dialogueBuilder;
    private AlertDialog dialog;
    private Button submit, dot, one, two, three, four, six, wide, no, out, change_bowler, addNewBowlerBtn, addNewBowler, cancelAddBowler,
            bowlerSelectOkBtn, addNewBatBtn, add_new_bats_man_cancel, no_ball_submit, wideSubmit, overThrow, overThrowCancel,
            ingEndButton;

    private TextView overs, runs, wickets, bat_man1, bat_man2, Bowler,
            p1_runs, p1_balls, p1_4s, p1_6s,p2_runs, p2_balls, p2_4s, p2_6s,
            bow_over, bow_runs, bow_eco, bow_wicket,or;

    private Spinner selectBowler, selectBatsMan, out_runs;

    ArrayAdapter<String> adapter;
    ArrayAdapter selectRunAdapter;
    List<String> list;

    RadioGroup radioGroup, radioGroupBall, radioScoreType, radioOverThrowType;
    RadioButton radioButton, radioButtonBall, radioButtonScoreType, radioButtonOverThrowType;

    RecyclerView rec_view;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_boad);

        SharedPreferences sp = getSharedPreferences("innings",MODE_PRIVATE);
        //SharedPreferences bow = getSharedPreferences("Bowler",MODE_PRIVATE);

        //buttons
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
        overThrow = findViewById(R.id.overThrow);
        change_bowler = findViewById(R.id.change_bower);
        overs = findViewById(R.id.overs);

        //text views
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

        //recyclerview
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL)
        AppDatabase db = AppDatabase.getDb(this);


        rec_view = findViewById(R.id.rec_view);
        rec_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        List<Last_balls> lastBalls = db.userDao().getLastBalls(sp.getInt("innings_id",0));
        lastBallAdapter adapter = new lastBallAdapter(lastBalls, this);
        rec_view.setAdapter(adapter);

        String ing_status = db.userDao().getIngStatus(sp.getInt("innings_id",0));


        setTexViews();

        Innings data = db.userDao().selectAllItemIng();

        if(data==null){
            createNewMatchDialog();
        }
        else if(ing_status == null){
            createNewMatchDialog();
        }
        else if(!ing_status.equals("Running"))
            createNewMatchDialog();

        setAllOperation();


        full_score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), fullScore.class));
            }
        });


    }

    private void inningsEndsDialog(){

        SharedPreferences sp = getSharedPreferences("innings",MODE_PRIVATE);

        dialogueBuilder = new AlertDialog.Builder(this);
        final View IngEndPopupView = getLayoutInflater().inflate(R.layout.innings_end, null);
        dialogueBuilder.setView(IngEndPopupView);



        dialogueBuilder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                Toast.makeText(getApplicationContext(), "Press Cancel", Toast.LENGTH_SHORT).show();
                return i == keyEvent.KEYCODE_BACK;

            }
        });



        dialog = dialogueBuilder.create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);

        AppDatabase db = AppDatabase.getDb(this);


        ingEndButton = IngEndPopupView.findViewById(R.id.ing_over_btn);

        ingEndButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db.userDao().setIngFinish(sp.getInt("innings_id",0));
                dialog.dismiss();
                startActivity(new Intent(getApplicationContext(), fullScore.class));

            }
        });





    }


    private void setTexViews(){

        SharedPreferences sp = getSharedPreferences("innings",MODE_PRIVATE);
        SharedPreferences bow = getSharedPreferences("Bowler",MODE_PRIVATE);

        AppDatabase db = AppDatabase.getDb(this);
        int last_player_id = db.userDao().getLastBats(sp.getInt("innings_id",0));
        int other_player_id = db.userDao().getBatsMan(last_player_id, sp.getInt("innings_id",0));


        runs.setText(String.valueOf(db.userDao().getIngRun(sp.getInt("innings_id",0))));
        wickets.setText(String.valueOf(db.userDao().getIngWicket(sp.getInt("innings_id",0))));
        overs.setText(BallToOver.convertBallToOver(db.userDao().getIngOver(sp.getInt("innings_id",0))));



        bat_man1.setText(db.userDao().getBatNameById(sp.getInt("innings_id",0), last_player_id));
        p1_balls.setText(String.valueOf(db.userDao().getBatBalls(sp.getInt("innings_id",0), last_player_id)));
        p1_runs.setText(String.valueOf(db.userDao().getBatRun(sp.getInt("innings_id",0), last_player_id)));
        p1_4s.setText(String.valueOf(db.userDao().getBatFour(sp.getInt("innings_id",0), last_player_id)));
        p1_6s.setText(String.valueOf(db.userDao().getBatSix(sp.getInt("innings_id",0), last_player_id)));


        bat_man2.setText(db.userDao().getBatNameById(sp.getInt("innings_id",0), other_player_id));
        p2_balls.setText(String.valueOf(db.userDao().getBatBalls(sp.getInt("innings_id",0), other_player_id)));
        p2_runs.setText(String.valueOf(db.userDao().getBatRun(sp.getInt("innings_id",0), other_player_id)));
        p2_4s.setText(String.valueOf(db.userDao().getBatFour(sp.getInt("innings_id",0), other_player_id)));
        p2_6s.setText(String.valueOf(db.userDao().getBatSix(sp.getInt("innings_id",0), other_player_id)));


        Bowler.setText(db.userDao().getBowNameById(sp.getInt("innings_id",0), bow.getInt("bowler",0)));
        bow_over.setText(BallToOver.convertBallToOver(db.userDao().getBowBall(sp.getInt("innings_id",0), bow.getInt("bowler",0))));
        bow_runs.setText(String.valueOf(db.userDao().getBowRun(sp.getInt("innings_id",0), bow.getInt("bowler",0))));
        bow_wicket.setText(String.valueOf(db.userDao().getBowWick(sp.getInt("innings_id",0), bow.getInt("bowler",0))));
        bow_eco.setText(
                Economy.bowlerEconomy(db.userDao().getBowRun(sp.getInt("innings_id",0), bow.getInt("bowler",0)) , BallToOver.convertBallToOver(db.userDao().getBowBall(sp.getInt("innings_id",0), bow.getInt("bowler",0))))
        );

        List<Last_balls> lastBalls = db.userDao().getLastBalls(sp.getInt("innings_id",0));
        lastBallAdapter adapter = new lastBallAdapter(lastBalls, getApplicationContext());
        rec_view.setAdapter(adapter);

    }


    private void setAllOperation() {
        SharedPreferences sp = getSharedPreferences("innings",MODE_PRIVATE);
        SharedPreferences bow = getSharedPreferences("Bowler",MODE_PRIVATE);
        AppDatabase db = AppDatabase.getDb(this);

        dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int lastBat,lastRun, bat_id, balls, t_balls;
                lastBat = db.userDao().getLastBats(sp.getInt("innings_id",0));
                lastRun = db.userDao().getLastRuns();
                balls = db.userDao().getBalls(sp.getInt("innings_id",0));
                String outCase = db.userDao().getLastBallsWhenOut(sp.getInt("innings_id",0));


                if(outCase.equals("out")){
                    bat_id = lastBat;
                }
                else if(lastRun % 2 == 0 && balls % 6 !=0)
                    bat_id = lastBat;
                else if(lastRun % 2 != 0 && balls % 6 ==0)
                    bat_id = lastBat;
                else
                    bat_id = db.userDao().getBatsMan(lastBat,sp.getInt("innings_id",0));


                if(balls % 6 == 0){

                    db.userDao().deleteLastBalls();

                }

                db.userDao().inc_innings(sp.getInt("innings_id",0),0);
                db.userDao().inc_batsman(bat_id,sp.getInt("innings_id",0),0);
                db.userDao().inc_bow(sp.getInt("innings_id",0),bow.getInt("bowler",0));
                db.userDao().insertLastBalls(new Last_balls(bow.getInt("bowler",0),bat_id,0,0,sp.getInt("innings_id",0),"batting" ));

                balls = db.userDao().getBalls(sp.getInt("innings_id",0));

                if(bat_id == db.userDao().getBatIdByName(sp.getInt("innings_id",0), bat_man1.getText().toString().trim())){


                    p1_balls.setText(String.valueOf(db.userDao().getBatBalls(sp.getInt("innings_id",0), bat_id)));
                    bat_man1.setTextColor(getResources().getColor(R.color.black));
                    bat_man2.setTextColor(getResources().getColor(R.color.white));

                }
                else{

                    p2_balls.setText(String.valueOf(db.userDao().getBatBalls(sp.getInt("innings_id",0), bat_id)));
                    bat_man2.setTextColor(getResources().getColor(R.color.black));
                    bat_man1.setTextColor(getResources().getColor(R.color.white));

                }

                bow_over.setText(BallToOver.convertBallToOver(db.userDao().getBowBall(sp.getInt("innings_id",0), bow.getInt("bowler",0))));
                bow_eco.setText(
                        Economy.bowlerEconomy(db.userDao().getBowRun(sp.getInt("innings_id",0), bow.getInt("bowler",0)) , BallToOver.convertBallToOver(db.userDao().getBowBall(sp.getInt("innings_id",0), bow.getInt("bowler",0))))

                );

                overs.setText(BallToOver.convertBallToOver(db.userDao().getIngOver(sp.getInt("innings_id",0))));

                //adapter.notifyDataSetChanged();
                List<Last_balls> lastBalls = db.userDao().getLastBalls(sp.getInt("innings_id",0));
                lastBallAdapter adapter = new lastBallAdapter(lastBalls, getApplicationContext());
                rec_view.setAdapter(adapter);


                t_balls = db.userDao().getTotalOver(sp.getInt("innings_id",0));
                t_balls = t_balls * 6;

                if(balls >= t_balls){
                    inningsEndsDialog();
                }
                else if(balls % 6 == 0){

                    changeBowlerDialog();

                }

            }
        });

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                int lastBat,lastRun, bat_id, balls, t_balls;
                lastBat = db.userDao().getLastBats(sp.getInt("innings_id",0));
                lastRun = db.userDao().getLastRuns();
                balls = db.userDao().getBalls(sp.getInt("innings_id",0));
                String outCase = db.userDao().getLastBallsWhenOut(sp.getInt("innings_id",0));


                if(outCase.equals("out")){
                    bat_id = lastBat;
                }
                else if(lastRun % 2 == 0 && balls % 6 !=0)
                    bat_id = lastBat;
                else if(lastRun % 2 != 0 && balls % 6 ==0)
                    bat_id = lastBat;
                else
                    bat_id = db.userDao().getBatsMan(lastBat,sp.getInt("innings_id",0));


                if(balls % 6 == 0){

                    db.userDao().deleteLastBalls();

                }


                db.userDao().inc_innings(sp.getInt("innings_id",0),1);
                db.userDao().inc_batsman(bat_id,sp.getInt("innings_id",0),1);
                db.userDao().inc_bow(sp.getInt("innings_id",0),bow.getInt("bowler",0));
                db.userDao().inc_bow_run_rt_ball(sp.getInt("innings_id",0),bow.getInt("bowler",0), 1);
                db.userDao().insertLastBalls(new Last_balls(bow.getInt("bowler",0),bat_id,1,0,sp.getInt("innings_id",0),"batting" ));

                balls = db.userDao().getBalls(sp.getInt("innings_id",0));

                if(bat_id == db.userDao().getBatIdByName(sp.getInt("innings_id",0), bat_man1.getText().toString().trim())){

                    p1_runs.setText(String.valueOf(db.userDao().getBatRun(sp.getInt("innings_id",0), bat_id)));
                    p1_balls.setText(String.valueOf(db.userDao().getBatBalls(sp.getInt("innings_id",0), bat_id)));
                    bat_man2.setTextColor(getResources().getColor(R.color.black));
                    bat_man1.setTextColor(getResources().getColor(R.color.white));

                }
                else{

                    p2_runs.setText(String.valueOf(db.userDao().getBatRun(sp.getInt("innings_id",0), bat_id)));
                    p2_balls.setText(String.valueOf(db.userDao().getBatBalls(sp.getInt("innings_id",0), bat_id)));
                    bat_man1.setTextColor(getResources().getColor(R.color.black));
                    bat_man2.setTextColor(getResources().getColor(R.color.white));

                }

                bow_over.setText(BallToOver.convertBallToOver(db.userDao().getBowBall(sp.getInt("innings_id",0), bow.getInt("bowler",0))));

                bow_eco.setText(
                        Economy.bowlerEconomy(db.userDao().getBowRun(sp.getInt("innings_id",0), bow.getInt("bowler",0)) , BallToOver.convertBallToOver(db.userDao().getBowBall(sp.getInt("innings_id",0), bow.getInt("bowler",0))))
                );

                bow_runs.setText(String.valueOf(db.userDao().getBowRun(sp.getInt("innings_id",0), bow.getInt("bowler",0))));

                runs.setText(String.valueOf(db.userDao().getIngRun(sp.getInt("innings_id",0))));
                overs.setText(BallToOver.convertBallToOver(db.userDao().getIngOver(sp.getInt("innings_id",0))));

                List<Last_balls> lastBalls = db.userDao().getLastBalls(sp.getInt("innings_id",0));
                lastBallAdapter adapter = new lastBallAdapter(lastBalls, getApplicationContext());
                rec_view.setAdapter(adapter);

                t_balls = db.userDao().getTotalOver(sp.getInt("innings_id",0));
                t_balls = t_balls * 6;

                if(balls >= t_balls){
                    inningsEndsDialog();
                }
                else if(balls % 6 == 0){

                    changeBowlerDialog();

                }


            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int lastBat,lastRun, bat_id, balls, t_balls;
                lastBat = db.userDao().getLastBats(sp.getInt("innings_id",0));
                lastRun = db.userDao().getLastRuns();
                balls = db.userDao().getBalls(sp.getInt("innings_id",0));
                String outCase = db.userDao().getLastBallsWhenOut(sp.getInt("innings_id",0));


                if(outCase.equals("out")){
                    bat_id = lastBat;
                }
                else if(lastRun % 2 == 0 && balls % 6 !=0)
                    bat_id = lastBat;
                else if(lastRun % 2 != 0 && balls % 6 ==0)
                    bat_id = lastBat;
                else
                    bat_id = db.userDao().getBatsMan(lastBat,sp.getInt("innings_id",0));


                if(balls % 6 == 0){

                    db.userDao().deleteLastBalls();

                }


                db.userDao().inc_innings(sp.getInt("innings_id",0),2);
                db.userDao().inc_batsman(bat_id,sp.getInt("innings_id",0),2);
                db.userDao().inc_bow(sp.getInt("innings_id",0),bow.getInt("bowler",0));
                db.userDao().inc_bow_run_rt_ball(sp.getInt("innings_id",0),bow.getInt("bowler",0), 2);
                db.userDao().insertLastBalls(new Last_balls(bow.getInt("bowler",0),bat_id,2,0,sp.getInt("innings_id",0),"batting" ));

                balls = db.userDao().getBalls(sp.getInt("innings_id",0));

                if(bat_id == db.userDao().getBatIdByName(sp.getInt("innings_id",0), bat_man1.getText().toString().trim())){

                    p1_runs.setText(String.valueOf(db.userDao().getBatRun(sp.getInt("innings_id",0), bat_id)));
                    p1_balls.setText(String.valueOf(db.userDao().getBatBalls(sp.getInt("innings_id",0), bat_id)));
                    bat_man1.setTextColor(getResources().getColor(R.color.black));
                    bat_man2.setTextColor(getResources().getColor(R.color.white));

                }
                else{

                    p2_runs.setText(String.valueOf(db.userDao().getBatRun(sp.getInt("innings_id",0), bat_id)));
                    p2_balls.setText(String.valueOf(db.userDao().getBatBalls(sp.getInt("innings_id",0), bat_id)));
                    bat_man2.setTextColor(getResources().getColor(R.color.black));
                    bat_man1.setTextColor(getResources().getColor(R.color.white));

                }

                bow_over.setText(BallToOver.convertBallToOver(db.userDao().getBowBall(sp.getInt("innings_id",0), bow.getInt("bowler",0))));

                bow_eco.setText(
                        Economy.bowlerEconomy(db.userDao().getBowRun(sp.getInt("innings_id",0), bow.getInt("bowler",0)) , BallToOver.convertBallToOver(db.userDao().getBowBall(sp.getInt("innings_id",0), bow.getInt("bowler",0))))
                );

                bow_runs.setText(String.valueOf(db.userDao().getBowRun(sp.getInt("innings_id",0), bow.getInt("bowler",0))));

                runs.setText(String.valueOf(db.userDao().getIngRun(sp.getInt("innings_id",0))));
                overs.setText(BallToOver.convertBallToOver(db.userDao().getIngOver(sp.getInt("innings_id",0))));

                List<Last_balls> lastBalls = db.userDao().getLastBalls(sp.getInt("innings_id",0));
                lastBallAdapter adapter = new lastBallAdapter(lastBalls, getApplicationContext());
                rec_view.setAdapter(adapter);

                t_balls = db.userDao().getTotalOver(sp.getInt("innings_id",0));
                t_balls = t_balls * 6;

                if(balls >= t_balls){
                    inningsEndsDialog();
                }
                else if(balls % 6 == 0){

                    changeBowlerDialog();

                }

            }
        });

        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int lastBat,lastRun, bat_id, balls, t_balls;
                lastBat = db.userDao().getLastBats(sp.getInt("innings_id",0));
                lastRun = db.userDao().getLastRuns();
                balls = db.userDao().getBalls(sp.getInt("innings_id",0));
                String outCase = db.userDao().getLastBallsWhenOut(sp.getInt("innings_id",0));


                if(outCase.equals("out")){
                    bat_id = lastBat;
                }
                else if(lastRun % 2 == 0 && balls % 6 !=0)
                    bat_id = lastBat;
                else if(lastRun % 2 != 0 && balls % 6 ==0)
                    bat_id = lastBat;
                else
                    bat_id = db.userDao().getBatsMan(lastBat,sp.getInt("innings_id",0));


                if(balls % 6 == 0){

                    db.userDao().deleteLastBalls();

                }


                db.userDao().inc_innings(sp.getInt("innings_id",0),3);
                db.userDao().inc_batsman(bat_id,sp.getInt("innings_id",0),3);
                db.userDao().inc_bow(sp.getInt("innings_id",0),bow.getInt("bowler",0));
                db.userDao().inc_bow_run_rt_ball(sp.getInt("innings_id",0),bow.getInt("bowler",0), 3);
                db.userDao().insertLastBalls(new Last_balls(bow.getInt("bowler",0),bat_id,3,0,sp.getInt("innings_id",0),"batting" ));

                balls = db.userDao().getBalls(sp.getInt("innings_id",0));

                if(bat_id == db.userDao().getBatIdByName(sp.getInt("innings_id",0), bat_man1.getText().toString().trim())){

                    p1_runs.setText(String.valueOf(db.userDao().getBatRun(sp.getInt("innings_id",0), bat_id)));
                    p1_balls.setText(String.valueOf(db.userDao().getBatBalls(sp.getInt("innings_id",0), bat_id)));
                    bat_man2.setTextColor(getResources().getColor(R.color.black));
                    bat_man1.setTextColor(getResources().getColor(R.color.white));

                }
                else{

                    p2_runs.setText(String.valueOf(db.userDao().getBatRun(sp.getInt("innings_id",0), bat_id)));
                    p2_balls.setText(String.valueOf(db.userDao().getBatBalls(sp.getInt("innings_id",0), bat_id)));
                    bat_man1.setTextColor(getResources().getColor(R.color.black));
                    bat_man2.setTextColor(getResources().getColor(R.color.white));

                }

                bow_over.setText(BallToOver.convertBallToOver(db.userDao().getBowBall(sp.getInt("innings_id",0), bow.getInt("bowler",0))));

                bow_eco.setText(
                        Economy.bowlerEconomy(db.userDao().getBowRun(sp.getInt("innings_id",0), bow.getInt("bowler",0)) , BallToOver.convertBallToOver(db.userDao().getBowBall(sp.getInt("innings_id",0), bow.getInt("bowler",0))))
                );

                bow_runs.setText(String.valueOf(db.userDao().getBowRun(sp.getInt("innings_id",0), bow.getInt("bowler",0))));

                runs.setText(String.valueOf(db.userDao().getIngRun(sp.getInt("innings_id",0))));
                overs.setText(BallToOver.convertBallToOver(db.userDao().getIngOver(sp.getInt("innings_id",0))));

                List<Last_balls> lastBalls = db.userDao().getLastBalls(sp.getInt("innings_id",0));
                lastBallAdapter adapter = new lastBallAdapter(lastBalls, getApplicationContext());
                rec_view.setAdapter(adapter);

                t_balls = db.userDao().getTotalOver(sp.getInt("innings_id",0));
                t_balls = t_balls * 6;

                if(balls >= t_balls){
                    inningsEndsDialog();
                }
                else if(balls % 6 == 0){

                    changeBowlerDialog();

                }

            }
        });

        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int lastBat,lastRun, bat_id, balls, t_balls;
                lastBat = db.userDao().getLastBats(sp.getInt("innings_id",0));
                lastRun = db.userDao().getLastRuns();
                balls = db.userDao().getBalls(sp.getInt("innings_id",0));
                String outCase = db.userDao().getLastBallsWhenOut(sp.getInt("innings_id",0));


                if(outCase.equals("out")){
                    bat_id = lastBat;
                }
                else if(lastRun % 2 == 0 && balls % 6 !=0)
                    bat_id = lastBat;
                else if(lastRun % 2 != 0 && balls % 6 ==0)
                    bat_id = lastBat;
                else
                    bat_id = db.userDao().getBatsMan(lastBat,sp.getInt("innings_id",0));


                if(balls % 6 == 0){

                    db.userDao().deleteLastBalls();

                }


                db.userDao().inc_innings(sp.getInt("innings_id",0),4);
                db.userDao().inc_batsman(bat_id,sp.getInt("innings_id",0),4);
                db.userDao().inc_bow(sp.getInt("innings_id",0),bow.getInt("bowler",0));
                db.userDao().inc_bow_run_rt_ball(sp.getInt("innings_id",0),bow.getInt("bowler",0), 4);
                db.userDao().incBatFour(sp.getInt("innings_id",0), bat_id);
                db.userDao().insertLastBalls(new Last_balls(bow.getInt("bowler",0),bat_id,4,0,sp.getInt("innings_id",0),"batting" ));

                balls = db.userDao().getBalls(sp.getInt("innings_id",0));

                if(bat_id == db.userDao().getBatIdByName(sp.getInt("innings_id",0), bat_man1.getText().toString().trim())){

                    p1_runs.setText(String.valueOf(db.userDao().getBatRun(sp.getInt("innings_id",0), bat_id)));
                    p1_balls.setText(String.valueOf(db.userDao().getBatBalls(sp.getInt("innings_id",0), bat_id)));
                    p1_4s.setText(String.valueOf(db.userDao().getBatFour(sp.getInt("innings_id",0), bat_id)));
                    bat_man1.setTextColor(getResources().getColor(R.color.black));
                    bat_man2.setTextColor(getResources().getColor(R.color.white));


                }
                else{

                    p2_runs.setText(String.valueOf(db.userDao().getBatRun(sp.getInt("innings_id",0), bat_id)));
                    p2_balls.setText(String.valueOf(db.userDao().getBatBalls(sp.getInt("innings_id",0), bat_id)));
                    p2_4s.setText(String.valueOf(db.userDao().getBatFour(sp.getInt("innings_id",0), bat_id)));
                    bat_man2.setTextColor(getResources().getColor(R.color.black));
                    bat_man1.setTextColor(getResources().getColor(R.color.white));

                }

                bow_over.setText(BallToOver.convertBallToOver(db.userDao().getBowBall(sp.getInt("innings_id",0), bow.getInt("bowler",0))));

                bow_eco.setText(
                        Economy.bowlerEconomy(db.userDao().getBowRun(sp.getInt("innings_id",0), bow.getInt("bowler",0)) , BallToOver.convertBallToOver(db.userDao().getBowBall(sp.getInt("innings_id",0), bow.getInt("bowler",0))))
                );

                bow_runs.setText(String.valueOf(db.userDao().getBowRun(sp.getInt("innings_id",0), bow.getInt("bowler",0))));

                runs.setText(String.valueOf(db.userDao().getIngRun(sp.getInt("innings_id",0))));
                overs.setText(BallToOver.convertBallToOver(db.userDao().getIngOver(sp.getInt("innings_id",0))));

                List<Last_balls> lastBalls = db.userDao().getLastBalls(sp.getInt("innings_id",0));
                lastBallAdapter adapter = new lastBallAdapter(lastBalls, getApplicationContext());
                rec_view.setAdapter(adapter);

                t_balls = db.userDao().getTotalOver(sp.getInt("innings_id",0));
                t_balls = t_balls * 6;

                if(balls >= t_balls){
                    inningsEndsDialog();
                }
                else if(balls % 6 == 0){

                    changeBowlerDialog();

                }


            }
        });

        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int lastBat,lastRun, bat_id, balls, t_balls;
                lastBat = db.userDao().getLastBats(sp.getInt("innings_id",0));
                lastRun = db.userDao().getLastRuns();
                balls = db.userDao().getBalls(sp.getInt("innings_id",0));
                String outCase = db.userDao().getLastBallsWhenOut(sp.getInt("innings_id",0));


                if(outCase.equals("out")){
                    bat_id = lastBat;
                }
                else if(lastRun % 2 == 0 && balls % 6 !=0)
                    bat_id = lastBat;
                else if(lastRun % 2 != 0 && balls % 6 ==0)
                    bat_id = lastBat;
                else
                    bat_id = db.userDao().getBatsMan(lastBat,sp.getInt("innings_id",0));


                if(balls % 6 == 0){

                    db.userDao().deleteLastBalls();

                }


                db.userDao().inc_innings(sp.getInt("innings_id",0),6);
                db.userDao().inc_batsman(bat_id,sp.getInt("innings_id",0),6);
                db.userDao().inc_bow(sp.getInt("innings_id",0),bow.getInt("bowler",0));
                db.userDao().inc_bow_run_rt_ball(sp.getInt("innings_id",0),bow.getInt("bowler",0), 6);
                db.userDao().incBatSix(sp.getInt("innings_id",0), bat_id);
                db.userDao().insertLastBalls(new Last_balls(bow.getInt("bowler",0),bat_id,6,0,sp.getInt("innings_id",0),"batting" ));

                balls = db.userDao().getBalls(sp.getInt("innings_id",0));

                if(bat_id == db.userDao().getBatIdByName(sp.getInt("innings_id",0), bat_man1.getText().toString().trim())){

                    p1_runs.setText(String.valueOf(db.userDao().getBatRun(sp.getInt("innings_id",0), bat_id)));
                    p1_balls.setText(String.valueOf(db.userDao().getBatBalls(sp.getInt("innings_id",0), bat_id)));
                    p1_6s.setText(String.valueOf(db.userDao().getBatSix(sp.getInt("innings_id",0), bat_id)));
                    bat_man1.setTextColor(getResources().getColor(R.color.black));
                    bat_man2.setTextColor(getResources().getColor(R.color.white));

                }
                else{

                    p2_runs.setText(String.valueOf(db.userDao().getBatRun(sp.getInt("innings_id",0), bat_id)));
                    p2_balls.setText(String.valueOf(db.userDao().getBatBalls(sp.getInt("innings_id",0), bat_id)));
                    p2_6s.setText(String.valueOf(db.userDao().getBatSix(sp.getInt("innings_id",0), bat_id)));
                    bat_man2.setTextColor(getResources().getColor(R.color.black));
                    bat_man1.setTextColor(getResources().getColor(R.color.white));

                }

                bow_over.setText(BallToOver.convertBallToOver(db.userDao().getBowBall(sp.getInt("innings_id",0), bow.getInt("bowler",0))));

                bow_eco.setText(
                        Economy.bowlerEconomy(db.userDao().getBowRun(sp.getInt("innings_id",0), bow.getInt("bowler",0)) , BallToOver.convertBallToOver(db.userDao().getBowBall(sp.getInt("innings_id",0), bow.getInt("bowler",0))))
                );

                bow_runs.setText(String.valueOf(db.userDao().getBowRun(sp.getInt("innings_id",0), bow.getInt("bowler",0))));

                runs.setText(String.valueOf(db.userDao().getIngRun(sp.getInt("innings_id",0))));
                overs.setText(BallToOver.convertBallToOver(db.userDao().getIngOver(sp.getInt("innings_id",0))));

                List<Last_balls> lastBalls = db.userDao().getLastBalls(sp.getInt("innings_id",0));
                lastBallAdapter adapter = new lastBallAdapter(lastBalls, getApplicationContext());
                rec_view.setAdapter(adapter);

                t_balls = db.userDao().getTotalOver(sp.getInt("innings_id",0));
                t_balls = t_balls * 6;

                if(balls >= t_balls){
                    inningsEndsDialog();
                }
                else if(balls % 6 == 0){

                    changeBowlerDialog();

                }

            }
        });

        wide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                wideBallDialog();

            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                noBallDialog();

            }
        });

        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                changeBatsManDialog();

            }
        });

        overThrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                overThrowDialog();
            }
        });

    }


    public void overThrowDialog(){

        AppDatabase db = AppDatabase.getDb(this);
        SharedPreferences innings = getSharedPreferences("innings",MODE_PRIVATE);
        SharedPreferences bow = getSharedPreferences("Bowler",MODE_PRIVATE);

        dialogueBuilder = new AlertDialog.Builder(this);
        final View overThrowPopupView = getLayoutInflater().inflate(R.layout.over_throw, null);
        dialogueBuilder.setView(overThrowPopupView);



        dialogueBuilder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                Toast.makeText(getApplicationContext(), "Press Cancel", Toast.LENGTH_SHORT).show();
                return i == keyEvent.KEYCODE_BACK;

            }
        });



        dialog = dialogueBuilder.create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);


        out_runs = overThrowPopupView.findViewById(R.id.out_runs);
        overThrow = overThrowPopupView.findViewById(R.id.over_throw_submit);
        overThrowCancel = overThrowPopupView.findViewById(R.id.cancel);
        radioOverThrowType = overThrowPopupView.findViewById(R.id.throw_type);



        String[] run = {"0", "1", "2", "3", "4", "5", "6"};
        selectRunAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, run);
        selectRunAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        out_runs.setAdapter(selectRunAdapter);

        overThrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int radioIdScoreType = radioOverThrowType.getCheckedRadioButtonId();
                radioButtonOverThrowType = (RadioButton) overThrowPopupView.findViewById(radioIdScoreType);



                int lastBat,lastRun, bat_id, balls;
                lastBat = db.userDao().getLastBats(innings.getInt("innings_id",0));
                lastRun = db.userDao().getLastRuns();
                balls = db.userDao().getBalls(innings.getInt("innings_id",0));
                String outCase = db.userDao().getLastBallsWhenOut(innings.getInt("innings_id",0));


                if(outCase.equals("out")){
                    bat_id = lastBat;
                }
                else if(lastRun % 2 == 0 && balls % 6 !=0)
                    bat_id = lastBat;
                else if(lastRun % 2 != 0 && balls % 6 ==0)
                    bat_id = lastBat;
                else
                    bat_id = db.userDao().getBatsMan(lastBat,innings.getInt("innings_id",0));



                if(radioButtonOverThrowType.getText().toString().equals("Right-Ball")){

                    //increment innings run
                    db.userDao().inc_innings(innings.getInt("innings_id",0), Integer.parseInt(out_runs.getSelectedItem().toString()));
                    // increment bowler ball
                    db.userDao().inc_bow(innings.getInt("innings_id",0), bow.getInt("bowler",0));

                }
                else{
                    // increment innings run
                    db.userDao().inc_innings_run(innings.getInt("innings_id",0), Integer.parseInt(out_runs.getSelectedItem().toString()));


                }
                //insert last ball
                db.userDao().insertLastBalls(new Last_balls(bow.getInt("bowler",0), bat_id, Integer.parseInt(out_runs.getSelectedItem().toString()),
                        Integer.parseInt(out_runs.getSelectedItem().toString()),
                        innings.getInt("innings_id",0), "over throw"));

                bow_over.setText(String.valueOf(db.userDao().getBowBall(innings.getInt("innings_id",0), bow.getInt("bowler",0))));

                bow_eco.setText(
                        Economy.bowlerEconomy(db.userDao().getBowRun(innings.getInt("innings_id",0), bow.getInt("bowler",0)) , BallToOver.convertBallToOver(db.userDao().getBowBall(innings.getInt("innings_id",0), bow.getInt("bowler",0))))
                );

                runs.setText(String.valueOf(db.userDao().getIngRun(innings.getInt("innings_id",0))));
                overs.setText(BallToOver.convertBallToOver(db.userDao().getIngOver(innings.getInt("innings_id",0))));

                dialog.dismiss();

                balls = db.userDao().getBalls(innings.getInt("innings_id",0));

                if(balls % 6 == 0){

                    changeBowlerDialog();

                }

            }
        });


        overThrowCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }


    public void wideBallDialog(){

        AppDatabase db = AppDatabase.getDb(this);
        SharedPreferences innings = getSharedPreferences("innings",MODE_PRIVATE);
        SharedPreferences bow = getSharedPreferences("Bowler",MODE_PRIVATE);

        dialogueBuilder = new AlertDialog.Builder(this);
        final View wideBallPopupView = getLayoutInflater().inflate(R.layout.wide_ball, null);
        dialogueBuilder.setView(wideBallPopupView);

        //noBallDialogue.setCanceledOnTouchOutside(false);
        //dialogueBuilder.setCancelable(false);
        //dialog.setCanceledOnTouchOutside(false);



        dialogueBuilder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                Toast.makeText(getApplicationContext(), "Press Cancel", Toast.LENGTH_SHORT).show();
                return i == keyEvent.KEYCODE_BACK;

            }
        });



        dialog = dialogueBuilder.create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);


        out_runs = wideBallPopupView.findViewById(R.id.out_runs);
        wideSubmit = wideBallPopupView.findViewById(R.id.wideSubmit);



        String[] run = {"0", "1", "2", "3", "4", "5", "6"};
        selectRunAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, run);
        selectRunAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        out_runs.setAdapter(selectRunAdapter);

        wideSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int lastBat,lastRun, bat_id, balls;
                lastBat = db.userDao().getLastBats(innings.getInt("innings_id",0));
                lastRun = db.userDao().getLastRuns();
                balls = db.userDao().getBalls(innings.getInt("innings_id",0));
                String outCase = db.userDao().getLastBallsWhenOut(innings.getInt("innings_id",0));


                if(outCase.equals("out")){
                    bat_id = lastBat;
                }
                else if(lastRun % 2 == 0 && balls % 6 !=0)
                    bat_id = lastBat;
                else if(lastRun % 2 != 0 && balls % 6 ==0)
                    bat_id = lastBat;
                else
                    bat_id = db.userDao().getBatsMan(lastBat,innings.getInt("innings_id",0));

                //increment bowler runs
                db.userDao().inc_bow_run(innings.getInt("innings_id",0), bow.getInt("bowler",0) , 0);

                //increment innings run
                db.userDao().inc_ing_no_ball_out(innings.getInt("innings_id",0), Integer.parseInt(out_runs.getSelectedItem().toString()) );

                //last balls
                db.userDao().insertLastBalls(new Last_balls(bow.getInt("bowler",0), bat_id, Integer.parseInt(out_runs.getSelectedItem().toString()), 1, innings.getInt("innings_id",0), "no ball"));


                bow_over.setText(BallToOver.convertBallToOver(db.userDao().getBowBall(innings.getInt("innings_id",0), bow.getInt("bowler",0))));
                bow_eco.setText(
                        Economy.bowlerEconomy(db.userDao().getBowRun(innings.getInt("innings_id",0), bow.getInt("bowler",0)) , BallToOver.convertBallToOver(db.userDao().getBowBall(innings.getInt("innings_id",0), bow.getInt("bowler",0))))
                );
                bow_runs.setText(String.valueOf(db.userDao().getBowRun(innings.getInt("innings_id",0), bow.getInt("bowler",0))));

                runs.setText(String.valueOf(db.userDao().getIngRun(innings.getInt("innings_id",0))));

                dialog.dismiss();
            }
        });

    }


    public void noBallDialog(){

        AppDatabase db = AppDatabase.getDb(this);
        SharedPreferences innings = getSharedPreferences("innings",MODE_PRIVATE);
        SharedPreferences bow = getSharedPreferences("Bowler",MODE_PRIVATE);

        dialogueBuilder = new AlertDialog.Builder(this);
        final View noBallPopupView = getLayoutInflater().inflate(R.layout.no_ball, null);
        dialogueBuilder.setView(noBallPopupView);



        dialogueBuilder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                Toast.makeText(getApplicationContext(), "Press Cancel", Toast.LENGTH_SHORT).show();
                return i == keyEvent.KEYCODE_BACK;

            }
        });



        dialog = dialogueBuilder.create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);


        out_runs = noBallPopupView.findViewById(R.id.out_runs);
        no_ball_submit = noBallPopupView.findViewById(R.id.no_ball_submit);
        radioScoreType = noBallPopupView.findViewById(R.id.score_type);



        String[] run = {"0", "1", "2", "3", "4", "5", "6"};
        selectRunAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, run);
        selectRunAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        out_runs.setAdapter(selectRunAdapter);

        no_ball_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int radioIdScoreType = radioScoreType.getCheckedRadioButtonId();
                radioButtonScoreType = (RadioButton) noBallPopupView.findViewById(radioIdScoreType);


                int lastBat,lastRun, bat_id, balls;
                lastBat = db.userDao().getLastBats(innings.getInt("innings_id",0));
                lastRun = db.userDao().getLastRuns();
                balls = db.userDao().getBalls(innings.getInt("innings_id",0));
                String outCase = db.userDao().getLastBallsWhenOut(innings.getInt("innings_id",0));


                if(outCase.equals("out")){
                    bat_id = lastBat;
                }
                else if(lastRun % 2 == 0 && balls % 6 !=0)
                    bat_id = lastBat;
                else if(lastRun % 2 != 0 && balls % 6 ==0)
                    bat_id = lastBat;
                else
                    bat_id = db.userDao().getBatsMan(lastBat,innings.getInt("innings_id",0));


                if(radioButtonScoreType.getText().toString().equals("Player")){

                    //increment bowler run
                    db.userDao().inc_bow_run(innings.getInt("innings_id",0), bow.getInt("bowler",0) , Integer.parseInt(out_runs.getSelectedItem().toString()));
                    //increment innings run
                    db.userDao().inc_ing_no_ball_out(innings.getInt("innings_id",0), Integer.parseInt(out_runs.getSelectedItem().toString()) );
                    // increment batsman run
                    db.userDao().inc_bat_no_ball_out(innings.getInt("innings_id",0), Integer.parseInt(out_runs.getSelectedItem().toString()), bat_id);


                }
                else{

                    //increment bowler runs
                    db.userDao().inc_bow_run(innings.getInt("innings_id",0), bow.getInt("bowler",0) , 0);

                    //increment innings run
                    db.userDao().inc_ing_no_ball_out(innings.getInt("innings_id",0), Integer.parseInt(out_runs.getSelectedItem().toString()) );


                }


                if(bat_id == db.userDao().getBatIdByName(innings.getInt("innings_id",0), bat_man1.getText().toString().trim())){

                    p1_runs.setText(String.valueOf(db.userDao().getBatRun(innings.getInt("innings_id",0), bat_id)));
                    p1_4s.setText(String.valueOf(db.userDao().getBatFour(innings.getInt("innings_id",0), bat_id)));
                    p1_6s.setText(String.valueOf(db.userDao().getBatSix(innings.getInt("innings_id",0), bat_id)));
                    p1_balls.setText(String.valueOf(db.userDao().getBatBalls(innings.getInt("innings_id",0), bat_id)));
                    bat_man1.setTextColor(getResources().getColor(R.color.black));

                }
                else{

                    p2_runs.setText(String.valueOf(db.userDao().getBatRun(innings.getInt("innings_id",0), bat_id)));
                    p2_4s.setText(String.valueOf(db.userDao().getBatFour(innings.getInt("innings_id",0), bat_id)));
                    p2_6s.setText(String.valueOf(db.userDao().getBatSix(innings.getInt("innings_id",0), bat_id)));
                    p2_balls.setText(String.valueOf(db.userDao().getBatBalls(innings.getInt("innings_id",0), bat_id)));
                    bat_man2.setTextColor(getResources().getColor(R.color.black));

                }

                //last balls
                db.userDao().insertLastBalls(new Last_balls(bow.getInt("bowler",0), bat_id, Integer.parseInt(out_runs.getSelectedItem().toString()), 1, innings.getInt("innings_id",0), "no ball"));

                bow_eco.setText(
                        Economy.bowlerEconomy(db.userDao().getBowRun(innings.getInt("innings_id",0), bow.getInt("bowler",0)) , BallToOver.convertBallToOver(db.userDao().getBowBall(innings.getInt("innings_id",0), bow.getInt("bowler",0))))
                );
                bow_over.setText(BallToOver.convertBallToOver(db.userDao().getBowBall(innings.getInt("innings_id",0), bow.getInt("bowler",0))));
                bow_runs.setText(String.valueOf(db.userDao().getBowRun(innings.getInt("innings_id",0), bow.getInt("bowler",0))));


                runs.setText(String.valueOf(db.userDao().getIngRun(innings.getInt("innings_id",0))));
                wickets.setText(String.valueOf(db.userDao().getIngWicket(innings.getInt("innings_id",0))));

                dialog.dismiss();


            }
        });



    }


    public void changeBatsManDialog(){

        AppDatabase db = AppDatabase.getDb(this);
        SharedPreferences innings = getSharedPreferences("innings",MODE_PRIVATE);
        SharedPreferences bow = getSharedPreferences("Bowler",MODE_PRIVATE);

        dialogueBuilder = new AlertDialog.Builder(this);
        final View bowPopupView = getLayoutInflater().inflate(R.layout.change_bats_man, null);
        dialogueBuilder.setView(bowPopupView);


        //dialogueBuilder.setCancelable(false);



        dialogueBuilder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                Toast.makeText(getApplicationContext(), "Press Cancel", Toast.LENGTH_SHORT).show();
                return i == keyEvent.KEYCODE_BACK;

            }
        });



        dialog = dialogueBuilder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        radioGroup = bowPopupView.findViewById(R.id.change_bat_radio_group);
        radioGroupBall = bowPopupView.findViewById(R.id.ball_type);
        radioScoreType = bowPopupView.findViewById(R.id.score_type);
        addNewBatBtn = bowPopupView.findViewById(R.id.add_new_bats_man);
        selectBatsMan = bowPopupView.findViewById(R.id.bats_man_name);
        bat_name = bowPopupView.findViewById(R.id.bat_name);
        out_runs = bowPopupView.findViewById(R.id.out_runs);
        add_new_bats_man_cancel = bowPopupView.findViewById(R.id.add_new_bats_man_cancel);


        String[] run = {"0", "1", "2", "3", "4", "5", "6"};
        selectRunAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, run);
        selectRunAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        out_runs.setAdapter(selectRunAdapter);

        list = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,list);
        selectBatsMan.setAdapter(adapter);

        list = db.userDao().getBatting(innings.getInt("innings_id",0));
        adapter.addAll(list);
        adapter.notifyDataSetChanged();

        RadioButton radio3 = bowPopupView.findViewById(R.id.wide);
        RadioButton radio4 = bowPopupView.findViewById(R.id.no_ball);
        RadioButton radio5 = bowPopupView.findViewById(R.id.right);
        RadioButton radio = bowPopupView.findViewById(R.id.player);
        RadioButton radio2 = bowPopupView.findViewById(R.id.extra);
        RadioButton radio6 = bowPopupView.findViewById(R.id.run_out);

        radio4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radio.setVisibility(View.VISIBLE);
                radio.setChecked(true);
                radio6.setVisibility(View.GONE);
            }
        });

        radio5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radio.setVisibility(View.VISIBLE);
                radio.setChecked(true);
                radio6.setVisibility(View.VISIBLE);
            }
        });

        radio3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                radio.setVisibility(View.GONE);
                radio2.setChecked(true);
                radio6.setVisibility(View.GONE);

            }
        });




        addNewBatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int prevBatCheck = db.userDao().getBatsManNumber(bat_name.getEditText().getText().toString().trim(), innings.getInt("innings_id",0));

                if(bat_name.getEditText().getText().toString().isEmpty()){
                    bat_name.getEditText().setError("Field Cant be empty");

                    return;
                }
                if(prevBatCheck>0){
                    bat_name.getEditText().setError("Batsman already exist");

                    return;
                }

                int radioId = radioGroup.getCheckedRadioButtonId();
                int radioIdBall = radioGroupBall.getCheckedRadioButtonId();
                int radioIdScoreType = radioScoreType.getCheckedRadioButtonId();
                radioButton = (RadioButton) bowPopupView.findViewById(radioId);
                radioButtonBall = (RadioButton) bowPopupView.findViewById(radioIdBall);
                radioButtonScoreType = (RadioButton) bowPopupView.findViewById(radioIdScoreType);
                //Toast.makeText(getApplicationContext(),radioButton.getText().toString(), Toast.LENGTH_SHORT).show();

                int lastBat,lastRun, bat_id, balls;
                lastBat = db.userDao().getLastBats(innings.getInt("innings_id",0));
                lastRun = db.userDao().getLastRuns();
                balls = db.userDao().getBalls(innings.getInt("innings_id",0));
                String outCase = db.userDao().getLastBallsWhenOut(innings.getInt("innings_id",0));


                if(outCase.equals("out")){
                    bat_id = lastBat;
                }
                else if(lastRun % 2 == 0 && balls % 6 !=0)
                    bat_id = lastBat;
                else if(lastRun % 2 != 0 && balls % 6 ==0)
                    bat_id = lastBat;
                else
                    bat_id = db.userDao().getBatsMan(lastBat,innings.getInt("innings_id",0));


                if(radioButtonBall.getText().toString().equals("no ball")){

                    if(radioButtonScoreType.getText().toString().equals("Player")){

                        db.userDao().inc_ing_no_ball_out(innings.getInt("innings_id",0), Integer.parseInt(out_runs.getSelectedItem().toString()) );
                        db.userDao().inc_bat_no_ball_out(innings.getInt("innings_id",0), Integer.parseInt(out_runs.getSelectedItem().toString()), bat_id);
                        db.userDao().inc_bow_run(innings.getInt("innings_id",0), bow.getInt("bowler",0) , Integer.parseInt(out_runs.getSelectedItem().toString()));

                    }
                    else{
                        db.userDao().inc_ing_no_ball_out(innings.getInt("innings_id",0), Integer.parseInt(out_runs.getSelectedItem().toString()) );
                        db.userDao().inc_bow_run(innings.getInt("innings_id",0), bow.getInt("bowler",0) , 0);
                    }
                    db.userDao().inc_wicket(innings.getInt("innings_id",0));
                }
                else if(radioButtonBall.getText().toString().equals("Wide")){

                    db.userDao().inc_ing_no_ball_out(innings.getInt("innings_id",0), Integer.parseInt(out_runs.getSelectedItem().toString()) );
                    db.userDao().inc_bow_run(innings.getInt("innings_id",0), bow.getInt("bowler",0) , 0);
                    db.userDao().inc_wicket(innings.getInt("innings_id",0));


                }
                else if(radioButtonBall.getText().toString().equals("Right Ball")){

                    if(radioButtonScoreType.getText().toString().equals("Player")){

                        db.userDao().inc_innings(innings.getInt("innings_id",0),Integer.parseInt(out_runs.getSelectedItem().toString()));
                        db.userDao().inc_wicket(innings.getInt("innings_id",0));
                        db.userDao().inc_bow(innings.getInt("innings_id",0), bow.getInt("bowler",0));
                        db.userDao().inc_bow_run_rt_ball(innings.getInt("innings_id",0), bow.getInt("bowler",0) , Integer.parseInt(out_runs.getSelectedItem().toString()));
                        //will increase bat_run played ball
                        db.userDao().inc_batsman(bat_id, innings.getInt("innings_id",0), Integer.parseInt(out_runs.getSelectedItem().toString()));
                        //db.userDao().insertLastBalls(new Last_balls(bow.getInt("bowler",0),bat_id,0,0,innings.getInt("innings_id",0),"batting" ));
                        db.userDao().inc_bow_wicket(innings.getInt("innings_id",0), bow.getInt("bowler",0));

                    }
                    else if(radioButtonScoreType.getText().toString().equals("Run-Out")){

                        db.userDao().inc_innings(innings.getInt("innings_id",0),Integer.parseInt(out_runs.getSelectedItem().toString()));
                        db.userDao().inc_wicket(innings.getInt("innings_id",0));
                        db.userDao().inc_bow(innings.getInt("innings_id",0), bow.getInt("bowler",0));
                        db.userDao().inc_bow_run_rt_ball(innings.getInt("innings_id",0), bow.getInt("bowler",0) , Integer.parseInt(out_runs.getSelectedItem().toString()));
                        db.userDao().inc_batsman(bat_id, innings.getInt("innings_id",0), Integer.parseInt(out_runs.getSelectedItem().toString()));

                    }
                    else{

                        db.userDao().inc_innings(innings.getInt("innings_id",0), Integer.parseInt(out_runs.getSelectedItem().toString()) );
                        db.userDao().inc_bow_run_rt_ball(innings.getInt("innings_id",0), bow.getInt("bowler",0) , 0);
                        db.userDao().inc_bow(innings.getInt("innings_id",0), bow.getInt("bowler",0));
                        db.userDao().inc_wicket(innings.getInt("innings_id",0));

                    }


                }

                bow_over.setText(BallToOver.convertBallToOver(db.userDao().getBowBall(innings.getInt("innings_id",0), bow.getInt("bowler",0))));



                // out batsman
                db.userDao().setBatStatusOut(innings.getInt("innings_id",0), selectBatsMan.getSelectedItem().toString());

                //inserting new batsman
                int maxBatId = db.userDao().getMaxBatsId(innings.getInt("innings_id",0));
                db.userDao().insertBatsman(new BatsMan(innings.getInt("innings_id",0),bat_name.getEditText().getText().toString().trim(),
                        maxBatId+1,0,0,0,0, "batting"));


                int batId = db.userDao().getBatIdByName(innings.getInt("innings_id",0), bat_man1.getText().toString().trim());

                // compare bat_man1 with old batsman
                if(db.userDao().getBatsMan(maxBatId+1, innings.getInt("innings_id",0)) == batId){

                    bat_man2.setText(bat_name.getEditText().getText().toString().trim());

                    if(radioButton.getText().toString().equals("Striker"))
                        bat_man2.setTextColor(getResources().getColor(R.color.black));

                    p2_balls.setText("0");
                    p2_runs.setText("0");
                    p2_4s.setText("0");
                    p2_6s.setText("0");
                }

                else{

                    p1_balls.setText("0");
                    p1_runs.setText("0");
                    p1_4s.setText("0");
                    p1_6s.setText("0");
                    bat_man1.setText(bat_name.getEditText().getText().toString().trim());

                    if (radioButton.getText().toString().equals("Striker"))
                        bat_man1.setTextColor(getResources().getColor(R.color.black));

                }

                if(radioButton.getText().toString().equals("Striker"))
                    db.userDao().insertLastBalls(new Last_balls(bow.getInt("bowler",0),maxBatId+1,0,0,innings.getInt("innings_id",0), "out" ));
                else {
                    int a = db.userDao().getBatsMan(maxBatId+1,innings.getInt("innings_id",0));
                    db.userDao().insertLastBalls(new Last_balls(bow.getInt("bowler",0), a,0,0,innings.getInt("innings_id",0), "out" ));
                }


                bow_over.setText(BallToOver.convertBallToOver(db.userDao().getBowBall(innings.getInt("innings_id",0), bow.getInt("bowler",0))));
                bow_wicket.setText(String.valueOf(db.userDao().getBowWick(innings.getInt("innings_id",0), bow.getInt("bowler",0))));

                //db.userDao().insertLastBalls(new Last_balls(bow.getInt("bowler",0), bat_id, Integer.parseInt(out_runs.getSelectedItem().toString()), 1, innings.getInt("innings_id",0), "no ball"));

                bow_eco.setText(
                        Economy.bowlerEconomy(db.userDao().getBowRun(innings.getInt("innings_id",0), bow.getInt("bowler",0)) , BallToOver.convertBallToOver(db.userDao().getBowBall(innings.getInt("innings_id",0), bow.getInt("bowler",0))))
                );
                bow_runs.setText(String.valueOf(db.userDao().getBowRun(innings.getInt("innings_id",0), bow.getInt("bowler",0))));

                runs.setText(String.valueOf(db.userDao().getIngRun(innings.getInt("innings_id",0))));
                wickets.setText(String.valueOf(db.userDao().getIngWicket(innings.getInt("innings_id",0))));
                overs.setText(BallToOver.convertBallToOver(db.userDao().getIngOver(innings.getInt("innings_id",0))));





                dialog.dismiss();

                balls = db.userDao().getBalls(innings.getInt("innings_id",0));


                if(balls % 6 == 0){

                    changeBowlerDialog();

                }


            }
        });

        add_new_bats_man_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


    }


    public void changeBowlerDialog(){

        AppDatabase db = AppDatabase.getDb(this);
        SharedPreferences innings = getSharedPreferences("innings",MODE_PRIVATE);
        SharedPreferences bow = getSharedPreferences("Bowler",MODE_PRIVATE);

        dialogueBuilder = new AlertDialog.Builder(this);
        final View bowPopupView = getLayoutInflater().inflate(R.layout.change_bowler, null);
        dialogueBuilder.setView(bowPopupView);


        //dialogueBuilder.setCancelable(false);



        dialogueBuilder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                    Toast.makeText(getApplicationContext(), "Press Ok", Toast.LENGTH_SHORT).show();
                    return i == keyEvent.KEYCODE_BACK;

            }
        });




        dialog = dialogueBuilder.create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);




        newBowler = bowPopupView.findViewById(R.id.bow_name);
        addNewBowlerBtn = bowPopupView.findViewById(R.id.addNewBowler_button);
        addNewBowler = bowPopupView.findViewById(R.id.addBowler);
        selectBowler = bowPopupView.findViewById(R.id.bowler_name);
        or = bowPopupView.findViewById(R.id.or_txt_view);
        select_bowler_layout = bowPopupView.findViewById(R.id.select_bowler_layout);
        add_bowler_layout = bowPopupView.findViewById(R.id.add_bowler_layout);
        cancelAddBowler = bowPopupView.findViewById(R.id.cancel);
        bowlerSelectOkBtn  = bowPopupView.findViewById(R.id.okBtn);

        add_bowler_layout.setVisibility(View.GONE);

        list = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,list);
        selectBowler.setAdapter(adapter);

        list = db.userDao().getAllBowler(innings.getInt("innings_id",0));
        adapter.addAll(list);
        adapter.notifyDataSetChanged();



        bowlerSelectOkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String selectedBowler = selectBowler.getSelectedItem().toString();
                int selectedBowId = db.userDao().getBowIdByName(innings.getInt("innings_id",0),selectedBowler);
                SharedPreferences.Editor bow1 = bow.edit();
                bow1.putInt("bowler",selectedBowId);
                bow1.commit();
                Bowler.setText(selectedBowler);
                bow_over.setText(BallToOver.convertBallToOver(db.userDao().getBowBall(innings.getInt("innings_id",0), bow.getInt("bowler",0))));
                bow_wicket.setText(String.valueOf(db.userDao().getBowWick(innings.getInt("innings_id",0), selectedBowId)));
                bow_eco.setText(
                        Economy.bowlerEconomy(db.userDao().getBowRun(innings.getInt("innings_id",0), bow.getInt("bowler",0)) , BallToOver.convertBallToOver(db.userDao().getBowBall(innings.getInt("innings_id",0), bow.getInt("bowler",0))))
                );
                bow_runs.setText(String.valueOf(db.userDao().getBowRun(innings.getInt("innings_id",0), bow.getInt("bowler",0))));
                dialog.dismiss();

            }
        });


        addNewBowlerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                select_bowler_layout.setVisibility(View.GONE);
                addNewBowlerBtn.setVisibility(View.GONE);

                add_bowler_layout.setVisibility(View.VISIBLE);

            }
        });

        addNewBowler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int prevBowCheck = db.userDao().getBowlerNumber(newBowler.getEditText().getText().toString(),innings.getInt("innings_id",0));

                if(newBowler.getEditText().getText().toString().isEmpty()){
                    newBowler.getEditText().setError("Field Cannot be empty");
                    return;
                }
                if(prevBowCheck>0){
                    newBowler.getEditText().setError("Name Already Exist");
                    return;
                }
                else {

                    int lastBowId = db.userDao().getLastBowlerId(innings.getInt("innings_id",0));

                    db.userDao().insertBowler(new Bowler(innings.getInt("innings_id",0), lastBowId+1,newBowler.getEditText().getText().toString(),
                            0,0,0));

                    add_bowler_layout.setVisibility(View.GONE);
                    select_bowler_layout.setVisibility(View.VISIBLE);
                    addNewBowlerBtn.setVisibility(View.VISIBLE);
                    list = db.userDao().getAllBowler(innings.getInt("innings_id",0));
                    adapter.clear();
                    adapter.addAll(list);
                    adapter.notifyDataSetChanged();

                }

            }
        });

        cancelAddBowler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                add_bowler_layout.setVisibility(View.GONE);
                select_bowler_layout.setVisibility(View.VISIBLE);
                addNewBowlerBtn.setVisibility(View.VISIBLE);
            }
        });



    }


    public void createNewMatchDialog(){

        dialogueBuilder = new AlertDialog.Builder(this);
        final View matchPopupView = getLayoutInflater().inflate(R.layout.popup, null);
        dialogueBuilder.setView(matchPopupView);

        dialogueBuilder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                Toast.makeText(getApplicationContext(), "Press Cancel", Toast.LENGTH_SHORT).show();
                return i == keyEvent.KEYCODE_BACK;

            }
        });


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

                    db.userDao().insertInnings(new Innings(innings.getInt("innings_id",0),Integer.parseInt(over.getEditText().getText().toString().trim()),
                            0,0,0, "Running"));
                    db.userDao().insertBatsman(new BatsMan(innings.getInt("innings_id",0),bat1.getEditText().getText().toString().trim(),
                            1,0,0,0,0, "batting"));
                    db.userDao().insertBatsman(new BatsMan(innings.getInt("innings_id",0),bat2.getEditText().getText().toString().trim(),
                            2,0,0,0,0,"batting"));
                    db.userDao().insertBowler(new Bowler(innings.getInt("innings_id",0), 1,bowler.getEditText().getText().toString().trim(),
                            0,0,0));

                    bat_man1.setText(bat1.getEditText().getText().toString());
                    bat_man1.setTextColor(getResources().getColor(R.color.black));
                    bat_man2.setText(bat2.getEditText().getText().toString());
                    Bowler.setText(bowler.getEditText().getText().toString().trim());

                    SharedPreferences bow = getSharedPreferences("Bowler",MODE_PRIVATE);
                    SharedPreferences.Editor bow1 = bow.edit();
                    bow1.putInt("bowler",1);
                    bow1.commit();

                    db.userDao().insertLastBalls(new Last_balls(1,1,1,0,innings.getInt("innings_id",0),"batting" ));

                    dialog.dismiss();

                }


            }
        });


    }


}
