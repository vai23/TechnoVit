package com.ask.vitevents.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ask.vitevents.R;


public class TrendingViewHolder extends RecyclerView.ViewHolder {

    TextView event_title;
    ImageView event_image;
    CardView trending_card;


    public TrendingViewHolder(View itemView) {
        super(itemView);
        event_title =  itemView.findViewById(R.id.event_title);
        event_image =  itemView.findViewById(R.id.event_image);
        trending_card = itemView.findViewById(R.id.trend_event_cards);

    }

}