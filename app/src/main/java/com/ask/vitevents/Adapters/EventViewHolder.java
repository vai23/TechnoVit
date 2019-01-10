package com.ask.vitevents.Adapters;

/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ask.vitevents.RoomDb.itemClickListener;

import com.ask.vitevents.R;


class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView ad_event_name,ad_event_date, ad_event_venue, ad_team_size;
    public ImageView ad_school_logo, card_background, eventposter;

    private itemClickListener itemClickListener;


    public EventViewHolder(View itemView) {
        super(itemView);
        ad_event_name = itemView.findViewById(R.id.card_event_name);
        ad_event_date = itemView.findViewById(R.id.card_event_date);
        ad_event_venue = itemView.findViewById(R.id.card_event_venue);
        ad_team_size = itemView.findViewById(R.id.person_num);
        ad_school_logo = itemView.findViewById(R.id.school_logo);
        card_background = itemView.findViewById(R.id.card_bg);
        eventposter = itemView.findViewById(R.id.event_poster);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(itemClickListener itemClickListener)
    {
        this.itemClickListener=itemClickListener;
    }


    @Override
    public void onClick(View view) {
        itemClickListener.OnClick(view,getAdapterPosition());
    }
}


