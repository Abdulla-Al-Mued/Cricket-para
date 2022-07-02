package com.example.cricketpara.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cricketpara.Database.Bowler;
import com.example.cricketpara.R;

import java.util.List;

public class bowlListAdapter extends RecyclerView.Adapter<bowlListAdapter.viewHolder>{


    List<Bowler> batsMan;
    private Context ctx;

    public bowlListAdapter(List<Bowler> batsMan, Context ctx) {
        this.batsMan = batsMan;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public bowlListAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.bow_list_single_row,parent,false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull bowlListAdapter.viewHolder holder, int position) {

        holder.name.setText(batsMan.get(position).getBowler_name());

    }

    @Override
    public int getItemCount() {
        return batsMan.size();
    }

    class viewHolder extends RecyclerView.ViewHolder{


        TextView name;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);

        }

    }


}
