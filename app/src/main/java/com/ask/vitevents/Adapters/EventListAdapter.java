package com.ask.vitevents.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ask.vitevents.Activities.event_detailed;
import com.ask.vitevents.R;
import com.ask.vitevents.Classes.Event;
import com.ask.vitevents.RoomDb.itemClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EventListAdapter extends RecyclerView.Adapter<EventViewHolder> {



    private final LayoutInflater mInflater;
    private Context context;
    private List<Event> mWords; // Cached copy of words
    String persons,personmin,personmax,sid;



    public EventListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context=context;
    }


    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.event_card, parent, false);
        return new EventViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        final Event current = mWords.get(position);
        personmin = current.getMinteamsize();
        personmax = current.getMaxteamsize();
        if(personmax.equalsIgnoreCase(personmin))
            persons = personmax;
        else
            persons = personmin + "-" + personmax;
        holder.ad_team_size.setText(persons);
        sid = current.getSchoolid();
        if(sid.equals("2")) {
            holder.ad_school_logo.setImageResource(R.drawable.qubitlogo);
            holder.card_background.setImageResource(R.drawable.qubit);
        }
        else if(sid.equals("3")) {
            holder.ad_school_logo.setImageResource(R.drawable.vitnesslogo);
            holder.card_background.setImageResource(R.drawable.vitness);
        }
        else if(sid.equals("5")) {
            holder.ad_school_logo.setImageResource(R.drawable.glitzlogo);
            holder.card_background.setImageResource(R.drawable.glitz);
        }
        else if(sid.equals("6")) {
            holder.ad_school_logo.setImageResource(R.drawable.disenologo);
            holder.card_background.setImageResource(R.drawable.diseno);
        }
        else if(sid.equals("7")) {
            holder.ad_school_logo.setImageResource(R.drawable.connectiviteelogo);
            holder.card_background.setImageResource(R.drawable.connectiviteee);
        }
        else if(sid.equals("8")) {
            holder.ad_school_logo.setImageResource(R.drawable.vsplashlogo);
            holder.card_background.setImageResource(R.drawable.vsplash);
        }
        else if(sid.equals("9")) {
            holder.ad_school_logo.setImageResource(R.drawable.taikoonlogo);
            holder.card_background.setImageResource(R.drawable.taikunn);
        }
        else if(sid.equals("10")) {
            holder.ad_school_logo.setImageResource(R.drawable.technofiestalogo);
            holder.card_background.setImageResource(R.drawable.technofiesta);
        }
        holder.ad_event_name.setText(current.getEventname());
        holder.ad_event_date.setText(current.getEventdate());
        holder.ad_event_venue.setText(current.getVenue());

        Picasso.with(context)
                .load("https://i.imgur.com/"+current.getPosterurl())
                .error(R.drawable.disenologo)
                .into(holder.eventposter);

        holder.setItemClickListener(new itemClickListener() {
            @Override
            public void OnClick(View view, int position) {
                //Toast.makeText(context,current.getIdevent(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,event_detailed.class);

                Uri detailed = Uri.parse("https://www.vitchennaievents.com")
                        .buildUpon()
                        .appendPath("register")
                        .appendPath("ïndex.php")
                        .appendQueryParameter("eventid",current.getIdevent())
                        .build();

                intent.setData(detailed);
                context.startActivity(intent);
            }
        });


    }

    public void setWords(List<Event> words){
        mWords = words;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mWords != null)
            return mWords.size();
        else return 0;
    }
}
