package com.example.cricketpara;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.cricketpara.Database.AppDatabase;
import com.example.cricketpara.Database.BatsMan;
import com.example.cricketpara.Database.Bowler;
import com.example.cricketpara.Database.Innings;
import com.example.cricketpara.Database.Last_balls;
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
            bowlerSelectOkBtn, addNewBatBtn, add_new_bats_man_cancel, no_ball_submit, wideSubmit;
    private TextView balls, runs, wickets, bat_man1, bat_man2, Bowler,
            p1_runs, p1_balls, p1_4s, p1_6s,p2_runs, p2_balls, p2_4s, p2_6s,
            bow_over, bow_runs, bow_eco, bow_wicket,or;

    private Spinner selectBowler, selectBatsMan, out_runs;

    ArrayAdapter<String> adapter;
    ArrayAdapter selectRunAdapter;
    List<String> list;

    RadioGroup radioGroup, radioGroupBall, radioScoreType;
    RadioButton radioButton, radioButtonBall, radioButtonScoreType;



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
        SharedPreferences bow = getSharedPreferences("Bowler",MODE_PRIVATE);
        AppDatabase db = AppDatabase.getDb(this);

        dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int lastBat,lastRun, bat_id, balls;
                lastBat = db.userDao().getLastBats();
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
                db.userDao().insertLastBalls(new Last_balls(bow.getInt("bowler",0),bat_id,1,0,sp.getInt("innings_id",0),"batting" ));

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
                lastBat = db.userDao().getLastBats();
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


                if(radioButtonScoreType.getText().toString().equals("Player")){

                    int lastBat,lastRun, bat_id, balls;
                    lastBat = db.userDao().getLastBats();
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


                    //increment bowler run
                    db.userDao().inc_bow_run(innings.getInt("innings_id",0), bow.getInt("bowler",0) , Integer.parseInt(out_runs.getSelectedItem().toString()));
                    //increment innings run
                    db.userDao().inc_ing_no_ball_out(innings.getInt("innings_id",0), Integer.parseInt(out_runs.getSelectedItem().toString()) );
                    // increment batsman run
                    db.userDao().inc_bat_no_ball_out(innings.getInt("innings_id",0), Integer.parseInt(out_runs.getSelectedItem().toString()), bat_id);
                    //last balls
                    db.userDao().insertLastBalls(new Last_balls(bow.getInt("bowler",0), bat_id, Integer.parseInt(out_runs.getSelectedItem().toString()), 1, innings.getInt("innings_id",0), "no ball"));



                }
                else{

                    int lastBat,lastRun, bat_id, balls;
                    lastBat = db.userDao().getLastBats();
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

                }

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

        dialog.setCanceledOnTouchOutside(false);
        //dialogueBuilder.setCancelable(false);



        dialogueBuilder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                Toast.makeText(getApplicationContext(), "Press Cancel", Toast.LENGTH_SHORT).show();
                return i == keyEvent.KEYCODE_BACK;

            }
        });



        dialog = dialogueBuilder.create();
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

                if(bat_name.getEditText().getText().toString().isEmpty()){
                    bat_name.getEditText().setError("Field Cant be empty");

                    return;
                }

                int radioId = radioGroup.getCheckedRadioButtonId();
                int radioIdBall = radioGroupBall.getCheckedRadioButtonId();
                int radioIdScoreType = radioScoreType.getCheckedRadioButtonId();
                radioButton = (RadioButton) bowPopupView.findViewById(radioId);
                radioButtonBall = (RadioButton) bowPopupView.findViewById(radioIdBall);
                radioButtonScoreType = (RadioButton) bowPopupView.findViewById(radioIdScoreType);
                //Toast.makeText(getApplicationContext(),radioButton.getText().toString(), Toast.LENGTH_SHORT).show();


                if(radioButtonBall.getText().toString().equals("no ball")){

                    if(radioButtonScoreType.getText().toString().equals("Player")){

                        int lastBat,lastRun, bat_id, balls;
                        lastBat = db.userDao().getLastBats();
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


                        //db.userDao().inc_innings(innings.getInt("innings_id",0),0);
                        db.userDao().inc_ing_no_ball_out(innings.getInt("innings_id",0), Integer.parseInt(out_runs.getSelectedItem().toString()) );
                        db.userDao().inc_bat_no_ball_out(innings.getInt("innings_id",0), Integer.parseInt(out_runs.getSelectedItem().toString()), bat_id);
                        db.userDao().inc_bow_run(innings.getInt("innings_id",0), bow.getInt("bowler",0) , Integer.parseInt(out_runs.getSelectedItem().toString()));
                        db.userDao().inc_wicket(innings.getInt("innings_id",0));
                        //db.userDao().inc_bow(innings.getInt("innings_id",0),bow.getInt("bowler",0));
                        //db.userDao().insertLastBalls(new Last_balls(bow.getInt("bowler",0),bat_id,0,0,innings.getInt("innings_id",0),"batting" ));



                    }
                    else{

                        //db.userDao().inc_innings(innings.getInt("innings_id",0),0);
                        db.userDao().inc_ing_no_ball_out(innings.getInt("innings_id",0), Integer.parseInt(out_runs.getSelectedItem().toString()) );
                        db.userDao().inc_bow_run(innings.getInt("innings_id",0), bow.getInt("bowler",0) , 0);
                        db.userDao().inc_wicket(innings.getInt("innings_id",0));
                        //db.userDao().inc_bat_no_ball_out(innings.getInt("innings_id",0), Integer.parseInt(out_runs.getSelectedItem().toString()), bat_id);
                        //db.userDao().inc_bow(innings.getInt("innings_id",0),bow.getInt("bowler",0));
                        //db.userDao().insertLastBalls(new Last_balls(bow.getInt("bowler",0),bat_id,0,0,innings.getInt("innings_id",0),"batting" ));


                    }
                }
                else if(radioButtonBall.getText().toString().equals("Wide")){

                    db.userDao().inc_ing_no_ball_out(innings.getInt("innings_id",0), Integer.parseInt(out_runs.getSelectedItem().toString()) );
                    db.userDao().inc_bow_run(innings.getInt("innings_id",0), bow.getInt("bowler",0) , 0);
                    db.userDao().inc_wicket(innings.getInt("innings_id",0));


                }
                else if(radioButtonBall.getText().toString().equals("Right Ball")){

                    if(radioButtonScoreType.getText().toString().equals("Player")){

                        int lastBat,lastRun, bat_id, balls;
                        lastBat = db.userDao().getLastBats();
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

                        int lastBat,lastRun, bat_id, balls;
                        lastBat = db.userDao().getLastBats();
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



                db.userDao().setBatStatusOut(innings.getInt("innings_id",0), selectBatsMan.getSelectedItem().toString());
                int maxBatId = db.userDao().getMaxBatsId();
                db.userDao().insertBatsman(new BatsMan(innings.getInt("innings_id",0),bat_name.getEditText().getText().toString().trim(),
                        maxBatId+1,0,0,0,0, "batting"));

                if(radioButton.getText().toString().equals("Striker"))
                    db.userDao().insertLastBalls(new Last_balls(bow.getInt("bowler",0),maxBatId+1,0,0,innings.getInt("innings_id",0), "out" ));
                else {
                    int a = db.userDao().getBatsMan(maxBatId+1,innings.getInt("innings_id",0));
                    db.userDao().insertLastBalls(new Last_balls(bow.getInt("bowler",0), a,0,0,innings.getInt("innings_id",0), "out" ));
                }


                dialog.dismiss();

                int balls = db.userDao().getBalls(innings.getInt("innings_id",0));


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

        dialog.setCanceledOnTouchOutside(false);
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

                    int lastBowId = db.userDao().getLastBowlerId();

                    db.userDao().insertBowler(new Bowler(innings.getInt("innings_id",0), lastBowId+1,newBowler.getEditText().getText().toString(),
                            0,0,0));

                    add_bowler_layout.setVisibility(View.GONE);
                    select_bowler_layout.setVisibility(View.VISIBLE);
                    addNewBowlerBtn.setVisibility(View.VISIBLE);
                    list = db.userDao().getAllBowler(innings.getInt("innings_id",0));
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
                    edit.commit();*/

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
