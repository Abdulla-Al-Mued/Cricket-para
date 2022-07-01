package com.example.cricketpara.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cricketpara.Database.Match;
import com.example.cricketpara.R;
import com.example.cricketpara.matchDetails;

import java.util.List;

public class allMatchAdapter extends RecyclerView.Adapter<allMatchAdapter.viewHolder> {


    List<Match> matches;
    private Context ctx;

    public allMatchAdapter(List<Match> matches, Context ctx) {
        this.matches = matches;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.match_single_row,parent,false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        holder.t1.setText(String.valueOf(matches.get(position).getMatch_id()));

    }

    @Override
    public int getItemCount() {
        return matches.size();
    }

    class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        TextView t1;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.match_no);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            Match product1 = matches.get(getAdapterPosition());
            Intent intent = new Intent(ctx, matchDetails.class);
            intent.putExtra("matches", product1);
            ctx.startActivity(intent);

        }
    }

}
