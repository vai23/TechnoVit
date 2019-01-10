package com.ask.vitevents.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.ask.vitevents.Adapters.RegisteredEventAdapter;
import com.ask.vitevents.Adapters.RegisteredTeesAdapter;
import com.ask.vitevents.Adapters.TeamAdapter;
import com.ask.vitevents.Classes.AddedTeamClass;
import com.ask.vitevents.Classes.RegisteredEventClass;
import com.ask.vitevents.Classes.RegisteredTeesClass;
import com.ask.vitevents.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.ask.vitevents.RoomDb.RootWork.server_ip;

public class Accounts extends Fragment {


    private RecyclerView R_e_recyclerView;
    private GridLayoutManager R_e_gridLayoutManager;
    private RecyclerView R_team_recyclerView;
    private TeamAdapter R_team_adapter;
    private RegisteredEventAdapter R_e_adapter;
    private RecyclerView R_t_recyclerView;
    private GridLayoutManager R_t_gridLayoutManager;
    private GridLayoutManager R_team_gridLayoutManager;
    private RegisteredTeesAdapter R_t_adapter;
    ProgressDialog dialog;



    TextView name,eventNT,profileT,teesT;
    TextView email;
    TextView phn;
    TextView eventscount;
    Button addteambut;
    TextView teescount;
    Button logout;
    CardView cardView;

    String team_id, team_name, team_size;


    TextView detcolo, eventcolo, teecolor, teamcolo, teamcount;
    LinearLayout teamL;

    Handler update;

    LinearLayout eventN,teesN,profile,userdet,eventdet,teesdet, teamN;

    SharedPreferences prefs;

    private List<RegisteredEventClass> data_Reg_E;
    private List<RegisteredTeesClass> data_Reg_T;
    private List<AddedTeamClass> data_Reg_Team;
    public  String user;
    public  String mail;
    public  String phone;
    public  int countevents;
    public  int counttees;


    ImageView profile_img;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.account_fragment, container, false);




        addteambut = rootView.findViewById(R.id.addteambut);

        teamcount = rootView.findViewById(R.id.teamscount);

        detcolo = rootView.findViewById(R.id.profilecolor);
        eventcolo = rootView.findViewById(R.id.eventcolor);
        teecolor = rootView.findViewById(R.id.merchcolor);
        teamcolo = rootView.findViewById(R.id.teamcolor);

        teamL = rootView.findViewById(R.id.userTeamsDetails);
        teamN = rootView.findViewById(R.id.teamNum);

        eventNT=rootView.findViewById(R.id.eventN_txt);
        profileT=rootView.findViewById(R.id.profile_txt);
        teesT=rootView.findViewById(R.id.teesN_txt);

        eventN=rootView.findViewById(R.id.eventNum);
        teesN=rootView.findViewById(R.id.teesNum);
        profile=rootView.findViewById(R.id.profileNum);
        userdet=rootView.findViewById(R.id.userDetails);
        eventdet=rootView.findViewById(R.id.userEventDetails);
        teesdet=rootView.findViewById(R.id.userTeesDetails);

        email=rootView.findViewById(R.id.dispmail);
        teescount=rootView.findViewById(R.id.teescount);
        phn=rootView.findViewById(R.id.dispphn);
        logout = rootView.findViewById(R.id.logoutprofile);
        eventscount=rootView.findViewById(R.id.eventscount);
        cardView=rootView.findViewById(R.id.cardViewprofile);
        prefs = this.getActivity().getSharedPreferences("login.conf", Context.MODE_PRIVATE);
        name=rootView.findViewById(R.id.dispuser);

        update = new Handler();
        getTeam();

        return rootView;

    }


    private void setGoneFrame()
    {
        logout.setVisibility(View.GONE);

        eventcolo.setVisibility(View.GONE);
        detcolo.setVisibility(View.GONE);
        teecolor.setVisibility(View.GONE);
        teamcolo.setVisibility(View.GONE);

        userdet.setVisibility(View.VISIBLE);
        eventdet.setVisibility(View.GONE);
        teesdet.setVisibility(View.GONE);
        teamL.setVisibility(View.GONE);
    }


    @Override
    public void onStart() {
        super.onStart();
        profile_img = getActivity().findViewById(R.id.profile_photo);



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefs.edit().clear().apply();
                Intent intent = new Intent(getActivity(),StartActivity.class);
                startActivity(intent);

                getActivity().finish();


            }
        });


        data_Reg_E=new ArrayList<RegisteredEventClass>();
        data_Reg_T=new ArrayList<RegisteredTeesClass>();
        data_Reg_Team = new ArrayList<AddedTeamClass>();

        get_data(prefs.getString("username",""));


        R_e_gridLayoutManager = new GridLayoutManager(getActivity(),1);
        R_t_gridLayoutManager = new GridLayoutManager(getActivity(),1);
        R_team_gridLayoutManager = new GridLayoutManager(getActivity(),1);

        R_e_recyclerView = getView().findViewById(R.id.EventsRegisteredRecycler);
        R_team_recyclerView = getView().findViewById(R.id.TeamsRegisteredRecycler);
        R_team_recyclerView.setLayoutManager(R_team_gridLayoutManager);
        R_team_recyclerView.setHasFixedSize(true);
        R_team_adapter = new TeamAdapter(getActivity(),data_Reg_Team);

        R_e_recyclerView.setLayoutManager(R_e_gridLayoutManager);
        R_e_recyclerView.setHasFixedSize(true);
        R_e_adapter = new RegisteredEventAdapter(getActivity(),data_Reg_E);

        R_t_recyclerView = getView().findViewById(R.id.TeesBookedRecycler);
        R_t_recyclerView.setLayoutManager(R_t_gridLayoutManager);
        R_t_recyclerView.setHasFixedSize(true);
        R_t_adapter = new RegisteredTeesAdapter(getActivity(),data_Reg_T);

        R_e_recyclerView.destroyDrawingCache();
        R_t_recyclerView.destroyDrawingCache();
        R_team_recyclerView.destroyDrawingCache();
        addteambut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenteam = new Intent(getContext(),AddTeamActivity.class);
                startActivity(intenteam);
            }
        });

        eventN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setGoneFrame();
                //eventN.setBackgroundDrawable(getResources().getDrawable(R.drawable.live_tab));
                //eventNT.setTextColor(getResources().getColor(R.color.font_card_col));
                eventcolo.setVisibility(View.VISIBLE);
                userdet.setVisibility(View.GONE);
                R_e_recyclerView.setAdapter(R_e_adapter);
                eventdet.setVisibility(View.VISIBLE);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setGoneFrame();
                //profile.setBackgroundDrawable(getResources().getDrawable(R.drawable.live_tab));
                //profileT.setTextColor(getResources().getColor(R.color.font_card_col));
                detcolo.setVisibility(View.VISIBLE);
                logout.setVisibility(View.VISIBLE);
                userdet.setVisibility(View.VISIBLE);
            }
        });
        teesN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setGoneFrame();
                //teesN.setBackgroundDrawable(getResources().getDrawable(R.drawable.live_tab));
                //teesT.setTextColor(getResources().getColor(R.color.font_card_col));
                teecolor.setVisibility(View.VISIBLE);
                R_t_recyclerView.setAdapter(R_t_adapter);
                teesdet.setVisibility(View.VISIBLE);
                userdet.setVisibility(View.GONE);
            }
        });

        teamN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGoneFrame();
                Log.d("====activt ", "color");
                teamcolo.setVisibility(View.VISIBLE);
                teamL.setVisibility(View.VISIBLE);
                R_team_recyclerView.setAdapter(R_team_adapter);
                userdet.setVisibility(View.GONE);
            }
        });


    }

    void getTeam() {


        Thread getTeam = new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    Uri.Builder builder = new Uri.Builder();
                    builder.scheme("https")
                            .authority(server_ip)
                            .appendPath("register")
                            .appendPath("android")
                            .appendPath("getTeam.php")
                            .appendQueryParameter("pid", prefs.getString("username", ""));

                    URL datalink = new URL(builder.build().toString());


                    OkHttpClient client = new OkHttpClient();
                    Log.d("URL ", datalink.toString());

                    //httpConnection.connect();
                    Request.Builder builder2 = new Request.Builder();
                    builder2.url(datalink);
                    Request request = builder2.build();

                    String All = "";

                    Response response = client.newCall(request).execute();
                    if (response.body() != null) {
                        All += response.body().string().trim();

                    }
                    Log.d("working inner 1", Integer.toString(All.length()));


                    int flag = 0;
                    if (All != null && All.length() > 2) {

                        final JSONArray mainObj = new JSONArray(All);

                        for (int i = 0; i < mainObj.length(); i++) {
                            JSONObject tempObj = new JSONObject(mainObj.get(i).toString());

                            team_id = (tempObj.getString("teamid"));
                            team_name = (tempObj.getString("teamname"));
                            team_size = (tempObj.getString("teamsize"));

                            AddedTeamClass te = new AddedTeamClass(team_id, team_name, team_size);

                            data_Reg_Team.add(te);

                        }

                        R_team_adapter.notifyDataSetChanged();
                        update.post(new Runnable() {
                            @Override
                            public void run() {
                                teamcount.setText(Integer.toString(mainObj.length()));
                            }
                        });

                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    Log.d("error Team", e.toString());
                } finally {

                    //dialog.dismiss();
                }

            }
        });

        getTeam.start();
    }


    private void get_data(final String id) {


        Thread get_data= new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    Uri.Builder eventlink = new Uri.Builder();
                    eventlink.scheme("https")
                            .authority(server_ip)
                            .appendPath("register")
                            .appendPath("android")
                            .appendPath("Appdetails.php")
                            .appendQueryParameter("id",id)
                            .build();

                    Uri.Builder teeslink = new Uri.Builder();
                    teeslink.scheme("https")
                            .authority(server_ip)
                            .appendPath("register")
                            .appendPath("android")
                            .appendPath("Appdetailstee.php")
                            .appendQueryParameter("id",id)
                            .build();

                    String urlevent = eventlink.toString();
                    String urltee = teeslink.toString();

                    OkHttpClient client = new OkHttpClient();
                    Log.d("URL ",urlevent.toString());
                    Log.d("URL ",urltee.toString());

                    //httpConnection.connect();
                    Request.Builder builder1 = new Request.Builder();
                    builder1.url(urlevent);
                    Request request1 = builder1.build();

                    String line="";

                    Request request = new Request.Builder().url(urlevent).build();
                    Request request2 = new Request.Builder().url(urltee).build();


                        Response response = client.newCall(request).execute();
                        Response response2 = client.newCall(request2).execute();
                        JSONArray array = new JSONArray(response.body().string());
                        JSONArray arraytee = new JSONArray(response2.body().string());
                        countevents = array.length() - 1;
                        counttees = arraytee.length();

                        int i = 0;
                        for (i = 0; i < 1; i++) {
                            JSONObject object = array.getJSONObject(i);

                            user = object.getString("name");
                            mail = object.getString("email");
                            phone = object.getString("phno");
                            Log.d("id", Integer.toString(array.length()));
                            Log.d("My List  ", array.toString());
                            //Log.d("......times....", Integer.toString(i));
                        }
                        for (i = 1; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            //Log.d("....check data..",object.getString("idevent"));
                            RegisteredEventClass data = new RegisteredEventClass(object.getString("eventname"), object.getString("venue"),
                                    object.getString("eventdate"), object.getString("eventtime"), "0");

                            data_Reg_E.add(data);
                        }
                        //Log.d("My Registered E List  ", Arrays.asList(data_Reg_E).toString());

                        for (i = 0; i < arraytee.length(); i++) {
                            JSONObject object = arraytee.getJSONObject(i);
                            //Log.d("....check data..",object.getString("idevent"));
                            RegisteredTeesClass data = new RegisteredTeesClass(object.getString("name"), object.getString("front"),
                                    object.getString("size"), object.getString("qty"), object.getString("status"));

                            data_Reg_T.add(data);
                        }


                        update.post(new Runnable() {
                            @Override
                            public void run() {
                                R_e_adapter.notifyDataSetChanged();
                                R_t_adapter.notifyDataSetChanged();
                                name.setText(user);
                                email.setText(mail);
                                phn.setText(phone);
                                eventscount.setText(Integer.toString(countevents));
                                teescount.setText(Integer.toString(counttees));
                            }
                        });



                }catch (Exception e) {
                    Log.d("===error account", e.toString());
                }

            }
        });

        get_data.start();
        }





}
