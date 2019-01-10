package com.ask.vitevents.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ask.vitevents.Activities.event_detailed;
import com.ask.vitevents.Classes.Event;
import com.ask.vitevents.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TrendingAdapter extends RecyclerView.Adapter<TrendingViewHolder> {

    private final LayoutInflater mInflater;
    private Context mcontext;
    private List<Event> mData;

    public TrendingAdapter(Context mcontext) {
        this.mcontext = mcontext;
        mInflater = LayoutInflater.from(mcontext);
    }

    @NonNull
    @Override
    public TrendingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.trend_event_cards,parent,false);
        return new TrendingViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull TrendingViewHolder holder, final int position) {

        final Event current = mData.get(position);

        holder.event_title.setText(current.getEventname());
        holder.event_image.setImageResource(R.drawable.eventthree);

        Picasso.with(mcontext)
                .load("https://i.imgur.com/"+current.getPosterurl())
                .into(holder.event_image);

        holder.trending_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mcontext,current.getIdevent(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mcontext,event_detailed.class);

                Uri detailed = Uri.parse("app://technovitchennai.com")
                        .buildUpon()
                        .appendQueryParameter("eventid",current.getIdevent())
                        .build();

                intent.setData(detailed);
                mcontext.startActivity(intent);
            }
        });
    }

    public void setTrendingEvents(List<Event> events)
    {
        mData = events;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mData != null)
            return mData.size();
        else return 0;

    }


}
