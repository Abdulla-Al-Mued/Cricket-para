package com.example.cricketpara;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class scoreBoard extends AppCompatActivity {

    Button full_score;

    private AlertDialog.Builder dialogueBuilder;
    private AlertDialog dialog;
    private EditText over, bat_1, bat_2, bowler;
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

        submit = matchPopupView.findViewById(R.id.add);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


    }
}