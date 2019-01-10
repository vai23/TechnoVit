package com.ask.vitevents.Activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ask.vitevents.Adapters.TshirtAdapter;
import com.ask.vitevents.Classes.Tshirt;
import com.ask.vitevents.R;
import com.ask.vitevents.RoomDb.TshirtViewModel;

import java.util.List;

public class Tshirts extends Fragment {


    RecyclerView recyclerView;
    TshirtAdapter adapter;
    TshirtViewModel mTshirtViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tshirts_fragment, container, false);
        recyclerView = rootView.findViewById(R.id.tshirt_recyclerview);
        adapter = new TshirtAdapter(getContext());

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mTshirtViewModel = ViewModelProviders.of(this).get(TshirtViewModel.class);

        mTshirtViewModel.getAllTshirt().observe(this, new Observer<List<Tshirt>>() {
            @Override
            public void onChanged(@Nullable List<Tshirt> tshirts) {
                adapter.setTshirt(tshirts);
            }
        });
    }
}
