package com.ask.vitevents.Activities;


import android.app.ActivityManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.ask.vitevents.R;
import com.ask.vitevents.Classes.Event;
import com.ask.vitevents.Adapters.EventListAdapter;
import com.ask.vitevents.RoomDb.EventViewModel;
import com.ask.vitevents.RoomDb.FetchBack;
import com.ask.vitevents.RoomDb.FetchBack_19;

import java.util.ArrayList;
import java.util.List;

import static com.ask.vitevents.RoomDb.RootWork.JOB_ID;

public class Events extends Fragment {

    private String CLUB_ID = "1";
    RecyclerView recyclerView;
    EventListAdapter adapter;
    EventViewModel mEventViewModel;

    Spinner school_chooser;
    ArrayAdapter<String> schooladapter;
    String SCHOOL_ID="0",schoolname;

    Intent mServiceIntent;
    FetchBack_19 fetchBack_19;

    //EditText SCHOOL_ID;
    Button go;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.events_fragment, container, false);

        recyclerView = rootView.findViewById(R.id.event_recyclerview);

        /*SCHOOL_ID = rootView.findViewById(R.id.tempSchoolid);
        go = rootView.findViewById(R.id.go_school);*/

        adapter = new EventListAdapter(getContext());

        List<String> school = new ArrayList<String>();
        school.add("All Schools");
        school.add("Qubit");
        school.add("Connectivitieee");
        school.add("Diseno");
        school.add("Vsplash");
        school.add("VITness");
        school.add("Tai:Kuun");
        school.add("Glitz");
        school.add("Techno Fiesta");
        schooladapter = new ArrayAdapter<String>(getContext(),R.layout.spinner_item,school);

        schooladapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        school_chooser = rootView.findViewById(R.id.school_chooser);


        return rootView;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mEventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);

        mEventViewModel.getAllEvents().observe(this, new Observer<List<Event>>() {
            @Override
            public void onChanged(@Nullable final List<Event> events) {
                // Update the cached copy of the words in the adapter.
                //adapter.setWords(events);
            }
        });

        school_chooser.setAdapter(schooladapter);

        school_chooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                schoolname = parent.getItemAtPosition(position).toString();
                if(schoolname.trim().equals("All Schools")){
                    SCHOOL_ID = "0";
                }
                else if (schoolname.trim().equals("Qubit")){
                    SCHOOL_ID = "2";
                }
                else if (schoolname.trim().equals("Vitness")){
                    SCHOOL_ID = "3";
                }
                else if (schoolname.trim().equals("Glitz")){
                    SCHOOL_ID = "5";
                }
                else if (schoolname.trim().equals("Diseno")){
                    SCHOOL_ID = "6";
                }
                else if (schoolname.trim().equals("Connectivitieee")){
                    SCHOOL_ID = "7";
                }
                else if (schoolname.trim().equals("Vsplash")){
                    SCHOOL_ID = "8";
                }
                else if (schoolname.trim().equals("Tai:Kuun")){
                    SCHOOL_ID = "23";
                }
                else if (schoolname.trim().equals("Techno Fiesta")){
                    SCHOOL_ID = "10";
                }
                else {
                    SCHOOL_ID = "0";
                }

                update();
                //Toast.makeText(getContext(),"School Id::"+SCHOOL_ID,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                SCHOOL_ID = "0";
            }
        });

        update();



        /*go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CLUB_ID = SCHOOL_ID.getText().toString().trim();

                update();
            }
        });*/

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Log.d("main","1 leve");
            if (!isJobScheduled(getContext())) {

                //Log.d("job finsihed","false retrn");
                ComponentName componentName = new ComponentName(getActivity(), FetchBack.class);
                JobInfo info = null;
                info = new JobInfo.Builder(JOB_ID, componentName)

                        .setPeriodic(15 * 60 * 1000)
                        .build();


                //Log.d("shcedec===","made");

                JobScheduler scheduler = (JobScheduler) getActivity().getSystemService(Context.JOB_SCHEDULER_SERVICE);


                int res = scheduler.schedule(info);
                if (res == JobScheduler.RESULT_SUCCESS)
                    Log.d("MAg", "Scheduled");
                else
                    Log.d("MAg", "Failed");


            }
        }else {

            fetchBack_19 = new FetchBack_19();
            mServiceIntent = new Intent(getContext(), fetchBack_19.getClass());

            if (!isMyServiceRunning(fetchBack_19.getClass()))
            {

                ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo info = cm.getActiveNetworkInfo();
                if (info != null) {
                    if (info.isConnected()) {
                        //start service

                        getActivity().startService(mServiceIntent);
                    }
                    else {
                        //stop service

                        getActivity().stopService(mServiceIntent);
                    }
                }

            }
            else
                getActivity().stopService(mServiceIntent);

        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass)
    {
        ActivityManager manager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.d ("isMyServiceRunning 19?", true+"");
                return true;
            }
        }
        Log.d ("isMyServiceRunning 19?", false+"");
        return false;
    }


    public static boolean isJobScheduled(Context context)
    {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            JobScheduler js = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
            if (js != null)
            {

                List<JobInfo> jobs = js.getAllPendingJobs();
                //Log.d("job finsihed","false"+jobs.size());
                for (int i = 0; i < jobs.size(); i++)
                {
                    //Log.d("job finsihed","false loop"+jobs.get(i).getId());
                    if (jobs.get(i).getId() == JOB_ID)
                    {

                        return true;
                    }

                    //Log.d("job finsihed","false end");
                }
            }
        }

        return false;
    }


    void update()
    {
        if (!SCHOOL_ID.equals("0")) {
            mEventViewModel.getEventByClub(SCHOOL_ID).observe(this, new Observer<List<Event>>() {
                @Override
                public void onChanged(@Nullable List<Event> events) {
                    adapter.setWords(events);
                }
            });
        }
        else
        {
            mEventViewModel.getAllEvents().observe(this, new Observer<List<Event>>() {
                @Override
                public void onChanged(@Nullable final List<Event> events) {
                    // Update the cached copy of the words in the adapter.
                    adapter.setWords(events);
                }
            });
        }
    }
}
