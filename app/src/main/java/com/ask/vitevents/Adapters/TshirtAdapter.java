package com.ask.vitevents.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ask.vitevents.Activities.tees_detailed;
import com.ask.vitevents.Classes.Tshirt;
import com.ask.vitevents.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TshirtAdapter extends RecyclerView.Adapter<TshirtViewHolder> {

    private final LayoutInflater mInflater;
    private Context mcontext;
    private List<Tshirt> mData;

    public TshirtAdapter(Context mcontext) {
        this.mcontext = mcontext;
        mInflater = LayoutInflater.from(mcontext);
    }

    @NonNull
    @Override
    public TshirtViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.tshirt_card,parent,false);
        return new TshirtViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull TshirtViewHolder holder, final int position) {

        final Tshirt current = mData.get(position);

        holder.tshirt_title.setText(current.getTshirtname());
        //holder.tshirt_desc.setText(current.getTshirtdesc());
        holder.tshirt_price.setText(current.getTshirtprice());

        Picasso.with(mcontext)
                .load(get_url().appendPath(current.getTshirtfront().trim()).build())
                .placeholder(R.drawable.test_tshirt_thumbnail)
                .into(holder.tshirt);

        Log.d("===adapter tee","working");



        holder.tshirt_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mcontext, tees_detailed.class);

                intent.putExtra("tid",(current.getIdtshirt()));
                mcontext.startActivity(intent);

                Toast.makeText(mcontext,current.getTshirtname(),Toast.LENGTH_SHORT).show();

            }
        });
    }

    Uri.Builder get_url()
    {
        Uri.Builder tshirtbuilder = new Uri.Builder();
        tshirtbuilder.scheme("https")
                .authority("www.vitchennaievents.com")
                .appendPath("register")
                .appendPath("shirt")
                .appendPath("upload");

        return tshirtbuilder;
    }

    public void setTshirt(List<Tshirt> events)
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
