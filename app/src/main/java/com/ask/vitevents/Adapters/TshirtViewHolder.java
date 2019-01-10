package com.ask.vitevents.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ask.vitevents.R;

public class TshirtViewHolder extends RecyclerView.ViewHolder {

    TextView tshirt_title, tshirt_desc, tshirt_price;
    CardView tshirt_card;
    ImageView tshirt;




    public TshirtViewHolder(View itemView) {
        super(itemView);

        tshirt = itemView.findViewById(R.id.tshirt_image);
        tshirt_card = itemView.findViewById(R.id.tshirt_cardview);
        tshirt_title = itemView.findViewById(R.id.card_tshirt_name);
        tshirt_price = itemView.findViewById(R.id.card_tshirt_cost);


    }

}
