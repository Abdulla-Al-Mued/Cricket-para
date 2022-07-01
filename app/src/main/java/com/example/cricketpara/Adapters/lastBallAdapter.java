package com.example.cricketpara.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cricketpara.Database.Last_balls;
import com.example.cricketpara.R;

import java.util.List;

public class lastBallAdapter extends RecyclerView.Adapter<lastBallAdapter.viewHolder>{


    List<Last_balls> lastBalls;
    private Context ctx;

    public lastBallAdapter(List<Last_balls> lastBalls, Context ctx) {
        this.lastBalls = lastBalls;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public lastBallAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.last_ball_single_row,parent,false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull lastBallAdapter.viewHolder holder, int position) {

        holder.t1.setText(String.valueOf(lastBalls.get(position).getRuns()+lastBalls.get(position).getExtra()));

    }

    @Override
    public int getItemCount() {
        return lastBalls.size();
    }

    class viewHolder extends RecyclerView.ViewHolder{


        TextView t1;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            t1 = itemView.findViewById(R.id.activity);

        }
    }



}
