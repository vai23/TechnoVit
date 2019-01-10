package com.ask.vitevents.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.ask.vitevents.Classes.Event;
import com.ask.vitevents.RoomDb.EventViewModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.ask.vitevents.R;
import com.squareup.picasso.Picasso;

import static com.ask.vitevents.RoomDb.RootWork.server_ip;

public class event_detailed extends AppCompatActivity {


    private TextView eventname,eventdesc, eventvenue, price, eventteamsize;
    EventViewModel mEventViewModel ;
    private ImageView btn_cash_back;
    Button register;
    Handler update;
    String event_id ;
    ProgressDialog dialog;
    Dialog register_click;
    int position;
    ImageView poster;
    SharedPreferences prefs;
    String uid;
    private static final String myprefs="login.conf";

    String min, max;

    ArrayList<String> team_name, team_id,team_size;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detailed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        team_id = new ArrayList<String>();
        team_name = new ArrayList<String>();
        team_size = new ArrayList<String>();
        prefs = getSharedPreferences(myprefs,MODE_PRIVATE);

        uid = prefs.getString("username","");

        if(uid==""){
            startActivity(new Intent(event_detailed.this,StartActivity.class));
            prefs.edit().clear().apply();
            finish();
        }

        poster = findViewById(R.id.event_det_poster);

        mEventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);
        eventdesc = findViewById(R.id.detail_eventdesc);
        eventname = findViewById(R.id.detail_eventname);
        btn_cash_back = findViewById(R.id.detail_back);
        eventvenue = findViewById(R.id.detail_venue);
        register = findViewById(R.id.event_detail_register_btn);
        price = findViewById(R.id.event_price);
        eventteamsize = findViewById(R.id.team_size_event_detail);
        //register_click = new Dialog(R.layout)

        update = new Handler();

        final Uri event_detail = getIntent().getData();
        event_id = event_detail.getQueryParameter("eventid");


        btn_cash_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null) {
            if (info.isConnected()) {
                //Log.d("baclkground fetch","starte");
                fetch.start();

            }
            else {
                //Log.d("local","start");
                set_data_offline();

            }
        }
        else
            set_data_offline();

        dialog = ProgressDialog.show(this, "",
                "Loading. Please wait...", true);
        //Log.d("now working","asc");

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("=====cl","hi");



                final CharSequence items[] = team_name.toArray(new CharSequence[team_name.size()]);
                AlertDialog.Builder builder = new AlertDialog.Builder(event_detailed.this);
                builder.setTitle("List of Team")
                        .setSingleChoiceItems(items, 0,new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                position = which;
                                //Toast.makeText(getApplicationContext(), items[which] + " is clicked", Toast.LENGTH_SHORT).show();
                            }
                        })

                        .setPositiveButton("CASH", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(items.length>0) {
                                    Intent Reg = new Intent(event_detailed.this, Registration.class);
                                    Reg.putExtra("eventid", event_id);
                                    Reg.putExtra("teamid", team_id.get(position));
                                    Log.d("=====id", Integer.toString(position));

                                    startActivity(Reg);
                                }
                                else {
                                    Toast.makeText(event_detailed.this, "Create team first", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNeutralButton("ONLINE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if(items.length>0) {
                                    //Intent Reg = new Intent(event_detailed.this,AppPayment.class);
                                    Intent Reg = new Intent(event_detailed.this, CheckoutActivity.class);
                                    Reg.putExtra("eventId", event_id);
                                    Reg.putExtra("tid", team_id.get(position));
                                    Log.d("=====id", Integer.toString(position));

                                    startActivity(Reg);
                                }
                                else {
                                    Toast.makeText(event_detailed.this, "Create team first", Toast.LENGTH_SHORT).show();
                                }

                            }
                        })
                        .setNegativeButton("Create Team", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent= new Intent(event_detailed.this,AddTeamActivity.class);
                                startActivity(intent);
                            }
                        });
                //builder.setNeutralButton("NEUTRAL", null);
                //builder.setPositiveButtonIcon(getResources().getDrawable(android.R.drawable.ic_menu_call, getTheme()));
                //builder.setIcon(getResources().getDrawable(R.drawable.jd, getTheme()));

                AlertDialog alertDialog = builder.create();

                alertDialog.show();

                Button btn_cash = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                btn_cash.setBackgroundColor(getResources().getColor(R.color.register));

                Button create = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                create.setTextColor(getResources().getColor(R.color.black));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 0, 5, 0);
                //btn_cash.setPadding(0, 0, 20, 0);
                create.setLayoutParams(params);
                btn_cash.setTextColor(getResources().getColor(R.color.white));


                Button btn_online = alertDialog.getButton(DialogInterface.BUTTON_NEUTRAL);
                btn_online.setBackgroundColor(getResources().getColor(R.color.bluelight));

                //btn_online.setPadding(0, 0, 20, 0);
                btn_online.setTextColor(Color.WHITE);

            }
        });


        /**eventdesc.setText("Lorem Ipsum is simply dummy text of the printing and typesetting industry. " +
         "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an " +
         "unknown printer took a galley of type and scrambled it to make a type specimen book. It " +
         "has survived not only five centuries, but also the leap into electronic typesetting, remaining" +
         " essentially unchanged. It was popularised in the 1960s with the release of Letraset" +
         " sheets containing Lorem Ipsum passages, and more recently with " +
         "desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."+
         "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an " +
         "unknown printer took a galley of type and scrambled it to make a type specimen book. It " +
         "has survived not only five centuries, but also the leap into electronic typesetting, remaining" +
         " essentially unchanged. It was popularised in the 1960s with the release of Letraset" +
         " sheets containing Lorem Ipsum passages, and more recently with " +
         "desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."+
         "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an " +
         "unknown printer took a galley of type and scrambled it to make a type specimen book. It " +
         "has survived not only five centuries, but also the leap into electronic typesetting, remaining" +
         " essentially unchanged. It was popularised in the 1960s with the release of Letraset" +
         " sheets containing Lorem Ipsum passages, and more recently with " +
         "desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");**/





    }


    void set_data_offline()
    {

        try {

            mEventViewModel.getEventById(event_id).observe(this, new Observer<Event>() {
                @Override
                public void onChanged(@Nullable Event event) {

                    try{
                        Picasso.with(event_detailed.this)
                                .load("https://i.imgur.com/"+event.getPosterurl().trim())
                                .error(R.drawable.disenologo)
                                .into(poster);
                        eventname.setText(event.getEventname());
                        eventdesc.setText(event.getEventdesc());
                        eventvenue.setText(event.getVenue());

                        Log.d("===========id==",event.getPosterurl());
                        min= event.getMinteamsize();
                        max = event.getMaxteamsize();
                        eventteamsize.setText(""+max);
                        price.setText("₹"+event.getFee());
                        //register.setTag("REGISTER NOW ₹"+event.getFee());
                        dialog.dismiss();
                    }catch (Exception ex) {
                        Toast.makeText(getApplicationContext(),"Please Check Your Internet Connection !",Toast.LENGTH_LONG).show();
                        Log.e(getPackageName(), ex.toString());

                        finish();
                    }

                }
            });

        } catch (Exception ex) {
            Toast.makeText(this,"Please Check Your Internet Connection !",Toast.LENGTH_LONG).show();
            Log.e(getPackageName(), ex.toString());
            finish();
        }


    }


    Thread fetch = new Thread(new Runnable() {
        @Override
        public void run()
        {
            try {

                Uri.Builder builder = new Uri.Builder();
                builder.scheme("https")
                        .authority(server_ip)
                        .appendPath("register")
                        .appendPath("android")
                        .appendPath("allevent.php");

                URL datalink=new URL(builder.build().toString());


                OkHttpClient client = new OkHttpClient();
                Log.d("URL ",datalink.toString());

                //httpConnection.connect();
                Request.Builder builder2 = new Request.Builder();
                builder2.url(datalink);
                Request request = builder2.build();

                String All="";

                Response response = client.newCall(request).execute();
                if( response.body() != null)
                {
                    All+= response.body().string().trim();

                }
                Log.d("working inner 1",Integer.toString(All.length()));


                int flag=0;
                if(All!=null && All.length()>2)
                {

                    JSONArray mainObj = new JSONArray(All);

                    for (int i=0;i<mainObj.length();i++)
                    {
                        JSONObject tempObj = new JSONObject(mainObj.get(i).toString());

                        //String temp_idevent = tempObj.getString("idevent");
                        //String temp_body = tempObj.getString("data");
                        //String temp_date = tempObj.getString("date");


                        String temp_idevent = tempObj.getString("idevent");
                        final String temp_eventname = tempObj.getString("eventname");
                        final String temp_eventdesc = tempObj.getString("eventdesc");
                        String temp_eventtime = tempObj.getString("eventtime");
                        String temp_eventdate = tempObj.getString("eventdate");
                        final String temp_venue = tempObj.getString("venue");
                        final String temp_fee = tempObj.getString("fee");
                        String temp_schoolid = tempObj.getString("schoolid");
                        String temp_clubid = tempObj.getString("clubid");
                        String temp_istrending = tempObj.getString("istrending");
                        String temp_status = tempObj.getString("status");
                        final String temp_minteamsize = tempObj.getString("minteamsize");
                        final String temp_maxteamsize = tempObj.getString("maxteamsize");
                        String temp_eventtype = tempObj.getString("eventtype");
                        final String temp_posterurl = tempObj.getString("posterurl");
                        //id_stor.add(event_id);

                        //Log.d("working inner 1",temp_eventname);

                        /**Calendar temp_cal = Calendar.getInstance();
                         try
                         {
                         @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf =
                         new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                         temp_cal.setTime(sdf.parse(temp_date));// all done
                         }catch(Exception e)
                         {
                         Log.e(getClass().toString(),"Date parsing failure");
                         }**/
                        if (event_id.equalsIgnoreCase(temp_idevent))
                        {
                            flag = 1;
                            update.post(new Runnable() {
                                @Override
                                public void run() {

                                    Picasso.with(event_detailed.this)
                                            .load("https://i.imgur.com/"+temp_posterurl.trim())
                                            .error(R.drawable.disenologo)
                                            .into(poster);
                                    Log.d("===========id==",temp_posterurl);
                                    eventname.setText(temp_eventname);
                                    eventdesc.setText(temp_eventdesc);
                                    price.setText("₹"+temp_fee);
                                    eventvenue.setText(temp_venue);
                                    eventteamsize.setText(temp_maxteamsize);

                                    max = temp_maxteamsize;
                                    min=temp_minteamsize;

                                    //register.setTag("REGISTER NOW ₹"+temp_fee);


                                    Log.d("working inner 2",temp_fee);
                                }
                            });
                        }


                    }

                    if (flag==0)
                    {
                        update.post(new Runnable() {
                            @Override
                            public void run() {

                                Toast.makeText(getApplicationContext(),"NO such Event",Toast.LENGTH_LONG).show();
                            }
                        });

                        finish();
                    }

                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }catch (Exception e)
            {
                Log.d("error",getPackageName());
            }finally {
                getTeam.start();
                dialog.dismiss();

            }

        }
    });


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
                        .appendQueryParameter("pid",uid);

                URL datalink=new URL(builder.build().toString());


                OkHttpClient client = new OkHttpClient();
                Log.d("URL ",datalink.toString());

                //httpConnection.connect();
                Request.Builder builder2 = new Request.Builder();
                builder2.url(datalink);
                Request request = builder2.build();

                String All="";

                Response response = client.newCall(request).execute();
                if( response.body() != null)
                {
                    All+= response.body().string().trim();

                }
                Log.d("working inner 1",Integer.toString(All.length()));


                int flag=0;
                if(All!=null && All.length()>2)
                {

                    JSONArray mainObj = new JSONArray(All);

                    for (int i=0;i<mainObj.length();i++)
                    {
                        JSONObject tempObj = new JSONObject(mainObj.get(i).toString());

                        //String temp_idevent = tempObj.getString("idevent");
                        //String temp_body = tempObj.getString("data");
                        //String temp_date = tempObj.getString("date");


                        if ((Integer.parseInt(tempObj.getString("teamsize"))>= Integer.parseInt(min)) &&
                                (Integer.parseInt(tempObj.getString("teamsize"))<= Integer.parseInt(max)) ) {

                            team_id.add(tempObj.getString("teamid"));
                            team_name.add(tempObj.getString("teamname"));
                            team_size.add(tempObj.getString("teamsize"));
                        }



                    }


                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }catch (Exception e)
            {
                Log.d("error Team",getPackageName());
            }finally {
                dialog.dismiss();
            }

        }
    });

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
