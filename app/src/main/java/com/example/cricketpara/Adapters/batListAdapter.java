package com.example.cricketpara.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cricketpara.Database.BatsMan;
import com.example.cricketpara.R;
import java.util.List;

public class batListAdapter extends RecyclerView.Adapter<batListAdapter.viewHolder>{


    List<BatsMan> batsMan;
    private Context ctx;

    public batListAdapter(List<BatsMan> batsMan, Context ctx) {
        this.batsMan = batsMan;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.bat_list_single_row,parent,false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        holder.name.setText(batsMan.get(position).getBat_name());
        holder.runs.setText(String.valueOf(batsMan.get(position).getT_run()));
        holder.fours.setText(String.valueOf(batsMan.get(position).getFour()));
        holder.sixes.setText(String.valueOf(batsMan.get(position).getSix()));
        holder.balls.setText(String.valueOf(batsMan.get(position).getBall_played()));

    }

    @Override
    public int getItemCount() {
        return batsMan.size();
    }

    class viewHolder extends RecyclerView.ViewHolder{


        TextView name, runs, fours, sixes, balls;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            runs = itemView.findViewById(R.id.runs);
            fours = itemView.findViewById(R.id.fours);
            sixes = itemView.findViewById(R.id.sixes);
            balls = itemView.findViewById(R.id.balls);


        }

    }

}
