package com.ask.vitevents.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ask.vitevents.Classes.AddedTeamClass;
import com.ask.vitevents.R;

import java.util.List;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.ViewHolder>{

    private Context context;
    private List<AddedTeamClass> my_data;


    public TeamAdapter(Context context, List<AddedTeamClass> data_list) {
        this.context=context;
        this.my_data=data_list;
    }

    @Override
    public TeamAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview= LayoutInflater.from(parent.getContext()).inflate(R.layout.team_card,parent,false);

        return new TeamAdapter.ViewHolder(itemview);
    }



    @Override
    public void onBindViewHolder(TeamAdapter.ViewHolder holder, int position) {
        holder.name.setText(my_data.get(position).getName());
        //holder.venue.setText(my_data.get(position).getVenue());
        String size = my_data.get(position).getSize();
        String id = my_data.get(position).getID();
        holder.teamid.setText("Team ID: "+id);
        holder.teamsize.setText("Size: "+size);
    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView name,teamsize,teamid;

        public ViewHolder(View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.team_name_detail);
            //venue=itemView.findViewById(R.id.R_e_venue);
            teamsize=itemView.findViewById(R.id.teamsize);
            teamid=itemView.findViewById(R.id.teamID);


        }
    }


}