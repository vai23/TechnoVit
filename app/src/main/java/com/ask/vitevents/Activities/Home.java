package com.ask.vitevents.Activities;
/*
import android.content.Context;
import android.nfc.cardemulation.HostNfcFService;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ask.technovit.Adapters.TrendingAdapter;
import com.ask.technovit.Adapters.homeoffersAdapter;
import com.ask.technovit.Classes.EventsDetails;
import com.ask.technovit.Classes.ItemOffsetDecoration;
import com.ask.technovit.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;*/

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ask.vitevents.Adapters.TrendingAdapter;
import com.ask.vitevents.Adapters.homeoffersAdapter;
import com.ask.vitevents.Classes.Event;
import com.ask.vitevents.Classes.EventsDetails;
import com.ask.vitevents.Classes.ItemOffsetDecoration;
import com.ask.vitevents.R;
import com.ask.vitevents.RoomDb.EventViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Home extends Fragment {

    ViewPager offers;
    LinearLayout Dotspanel,trend_linear;
    int dotscount=0;
    ImageView[] dots;
    int pos,width,flag;
    List<EventsDetails> lstevents;

    RecyclerView trendingRecycler;
    TrendingAdapter trendingAdapter;
    EventViewModel meventViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        flag=0;
        View rootView = inflater.inflate(R.layout.home_fragment, container, false);
        trendingRecycler = rootView.findViewById(R.id.home_trending_recycler);
        trend_linear = rootView.findViewById(R.id.Sliderdots);
        trendingAdapter = new TrendingAdapter(getContext());
        ViewTreeObserver viewTreeObserver = trend_linear.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                width = trend_linear.getMeasuredWidth();
                if (flag==0) {
                    int spanCount = (int) Math.ceil(trend_linear.getWidth() / convertDPToPixels(Colwidth));
                    //((GridLayoutManager) trendview.getLayoutManager()).setSpanCount(spanCount);
                    Log.d("====Span====", Integer.toString(spanCount));
                    trendingRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
                    ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getContext(), R.dimen.item_offset);
                    trendingRecycler.addItemDecoration(itemDecoration);
                    flag=1;
                }
            }
        });


        //trendview.setLayoutManager(new GridLayoutManager(getContext(),3));

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        //Toast.makeText(getContext(),Integer.toString(width),Toast.LENGTH_SHORT).show();

       trendingRecycler.setAdapter(trendingAdapter);
      //trendingRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));

        meventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);
        meventViewModel.getTrendingEvents().observe(this, new android.arch.lifecycle.Observer<List<Event>>() {
            @Override
            public void onChanged(@Nullable List<Event> events) {

                trendingAdapter.setTrendingEvents(events);

            }
        });

    }



    @Override
    public void onStart() {
        offers = getActivity().findViewById(R.id.home_offers);
        offers.setVisibility(View.GONE);
        homeoffersAdapter offersAdapter = new homeoffersAdapter(getContext());
        offers.setAdapter(offersAdapter);
        lstevents = new ArrayList<>();
        lstevents.add(new EventsDetails("Event 1","cat","trending",R.drawable.eventone));
        lstevents.add(new EventsDetails("Event 2","table","trending",R.drawable.eventtwo));
        lstevents.add(new EventsDetails("Event 3","catalog","trending",R.drawable.eventthree));
        lstevents.add(new EventsDetails("Event 4","caig","trending",R.drawable.eventtwo));
        lstevents.add(new EventsDetails("Event 5","cagh","trending",R.drawable.eventone));
        lstevents.add(new EventsDetails("Event 6","catdfd","trending",R.drawable.eventthree));
        lstevents.add(new EventsDetails("Event 7","cathjk","trending",R.drawable.eventone));
        lstevents.add(new EventsDetails("Event 8","catijk","trending",R.drawable.eventtwo));
        lstevents.add(new EventsDetails("Event 9","catyht","trending",R.drawable.eventtwo));
        lstevents.add(new EventsDetails("Event 10","catyhj","trending",R.drawable.eventone));
        lstevents.add(new EventsDetails("Event 11","cathjk","trending",R.drawable.eventone));
        lstevents.add(new EventsDetails("Event 12","catijk","trending",R.drawable.eventtwo));
        /**RecyclerView trendview = getActivity().findViewById(R.id.home_events_recycler);
         TrendingAdapter trendingAdapter = new TrendingAdapter(getContext(),lstevents);
         trendview.setLayoutManager(new GridLayoutManager(getContext(),3));
         trendview.setAdapter(trendingAdapter);**/
        //trendingRecycler.setAdapter(trendingAdapter);
        dotscount = offersAdapter.getCount();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(),4000,4000);
        /*for(int j=0;j<dotscount;j++){
            dots[j] = new ImageView(getContext());
            dots[j].setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.nonactive_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8,0,8,0);
            Dotspanel.addView(dots[j],params);
        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.active_dot));*/
        offers.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                /*for(int j=0;j<dotscount;j++) {
                    dots[j].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.nonactive_dot));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.active_dot));*/
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        super.onStart();
    }

    public class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            if(getActivity()==null)
                return;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    pos = offers.getCurrentItem();
                    if(pos==dotscount-1){
                        offers.setCurrentItem(0,true);
                    }
                    else{
                        offers.setCurrentItem(pos+1,true);
                    }
                }
            });
        }
    }
    private static final int Colwidth = 120; // assume cell width of 140dp
    private void calculateSize() {

    }

    private float convertDPToPixels(int dp) {
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float logicalDensity = metrics.density;
        return dp * logicalDensity;
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}

/*
public class Home extends Fragment {

    ViewPager offers;
    LinearLayout Dotspanel,trend_linear;
    int dotscount=0;
    ImageView[] dots;
    int pos,width,flag;
    RecyclerView trendview;
    List<EventsDetails> lstevents;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        flag=0;
        View rootView = inflater.inflate(R.layout.home_fragment, container, false);
        trendview = rootView.findViewById(R.id.home_events_recycler);
        trend_linear = rootView.findViewById(R.id.Sliderdots);
        ViewTreeObserver viewTreeObserver = trend_linear.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                width = trend_linear.getMeasuredWidth();
                if (flag==0) {
                    int spanCount = (int) Math.ceil(trend_linear.getWidth() / convertDPToPixels(Colwidth));
                    //((GridLayoutManager) trendview.getLayoutManager()).setSpanCount(spanCount);
                    Log.d("====Span====", Integer.toString(spanCount));
                    trendview.setLayoutManager(new GridLayoutManager(getContext(), spanCount-1));
                    ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getContext(), R.dimen.item_offset);
                    trendview.addItemDecoration(itemDecoration);
                    flag=1;
                }
            }
        });


            //trendview.setLayoutManager(new GridLayoutManager(getContext(),3));

        return rootView;

    }

    @Override
    public void onStart() {
        offers = getActivity().findViewById(R.id.home_offers);
        homeoffersAdapter offersAdapter = new homeoffersAdapter(getContext());
        offers.setAdapter(offersAdapter);
        lstevents = new ArrayList<>();
        lstevents.add(new EventsDetails("Event 1","cat","trending",R.drawable.eventone));
        lstevents.add(new EventsDetails("Event 2","table","trending",R.drawable.eventtwo));
        lstevents.add(new EventsDetails("Event 3","catalog","trending",R.drawable.eventthree));
        lstevents.add(new EventsDetails("Event 4","caig","trending",R.drawable.eventtwo));
        lstevents.add(new EventsDetails("Event 5","cagh","trending",R.drawable.eventone));
        lstevents.add(new EventsDetails("Event 6","catdfd","trending",R.drawable.eventthree));
        lstevents.add(new EventsDetails("Event 7","cathjk","trending",R.drawable.eventone));
        lstevents.add(new EventsDetails("Event 8","catijk","trending",R.drawable.eventtwo));
        lstevents.add(new EventsDetails("Event 9","catyht","trending",R.drawable.eventtwo));
        lstevents.add(new EventsDetails("Event 10","catyhj","trending",R.drawable.eventone));
        lstevents.add(new EventsDetails("Event 11","cathjk","trending",R.drawable.eventone));
        lstevents.add(new EventsDetails("Event 12","catijk","trending",R.drawable.eventtwo));
        TrendingAdapter trendingAdapter = new TrendingAdapter(getContext(), lstevents);
        //calculateSize();
        //trendview.setAdapter(trendingAdapter);


       //TrendingAdapter trendingAdapter = new TrendingAdapter(getContext(),lstevents);



        //trendview.setLayoutManager(new GridLayoutManager(getContext(),3));
        trendview.setAdapter(trendingAdapter);
        dotscount = offersAdapter.getCount();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(),4000,4000);
        /*for(int j=0;j<dotscount;j++){
            dots[j] = new ImageView(getContext());
            dots[j].setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.nonactive_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8,0,8,0);
            Dotspanel.addView(dots[j],params);
        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.active_dot));*/
       /* ## START FROM HERE ##
       offers.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                /*for(int j=0;j<dotscount;j++) {
                    dots[j].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.nonactive_dot));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.active_dot));*/
            /*}

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        super.onStart();
    }
    private static final int Colwidth = 120; // assume cell width of 140dp
    private void calculateSize() {

    }

    private float convertDPToPixels(int dp) {
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float logicalDensity = metrics.density;
        return dp * logicalDensity;
    }
    public class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            if(getActivity()==null)
                return;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    pos = offers.getCurrentItem();
                    if(pos==dotscount-1){
                        offers.setCurrentItem(0,true);
                    }
                    else{
                        offers.setCurrentItem(pos+1,true);
                    }
                }
            });
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
*/