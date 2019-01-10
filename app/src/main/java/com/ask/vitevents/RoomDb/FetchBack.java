package com.ask.vitevents.RoomDb;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import com.ask.vitevents.Classes.Event;
import com.ask.vitevents.Classes.Tshirt;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.ask.vitevents.RoomDb.RootWork.server_ip;


@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class FetchBack extends JobService {

    private final static String TAG= "In Service ";
    private Boolean jobCancelled=false;
    URL datalink, tshirtlink;
    Handler show;
    String All;
    Context context=this;


    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d(TAG,"Starting");

        All=null;
        show = new Handler();


        try {

            Uri.Builder eventbuilder = new Uri.Builder();
            eventbuilder.scheme("https")
                    .authority(server_ip)
                    .appendPath("register")
                    .appendPath("android")
                    .appendPath("allevent.php");

            datalink = new URL(eventbuilder.build().toString());

            Uri.Builder tshirtbuilder = new Uri.Builder();
            tshirtbuilder.scheme("https")
                    .authority("www.vitchennaievents.com")
                    .appendPath("register")
                    .appendPath("android")
                    .appendPath("AppAllTees.php");

            tshirtlink = new URL(tshirtbuilder.build().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        getdataThread(jobParameters);


        return true;
    }

    public void getdataThread(final JobParameters params)
    {
        Thread EventfetchTask = new Thread(new Runnable() {

            @Override
            public void run() {
                try {


                    OkHttpClient client = new OkHttpClient.Builder()
                            .connectTimeout(10, TimeUnit.SECONDS)
                            .writeTimeout(10, TimeUnit.SECONDS)
                            .readTimeout(30, TimeUnit.SECONDS)
                            .build();

                    Log.d("URL ",datalink.toString());

                    //httpConnection.connect();
                    Request.Builder builder1 = new Request.Builder();
                    builder1.url(datalink);
                    Request request1 = builder1.build();

                    String line="";

                    Response response1 = client.newCall(request1).execute();
                    if( response1.body() != null)
                    {
                        line+= response1.body().string().trim();
                        All= line;
                        Log.d(TAG+"1",Integer.toString(All.length())+line);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

                if(All!=null && All.length()>2)
                {




                    try
                    {
                        //EventRoomDatabase db = EventRoomDatabase.getDatabase(getApplication());
                        //EventDao w=db.eventDao();

                        EventViewModel model = new EventViewModel(getApplication());
                        JSONArray mainObj = new JSONArray(All);

                        for (int i=0;i<mainObj.length();i++)
                        {
                            JSONObject tempObj = new JSONObject(mainObj.get(i).toString());

                            //String temp_idevent = tempObj.getString("idevent");
                            //String temp_body = tempObj.getString("data");
                            //String temp_date = tempObj.getString("date");


                            String temp_idevent = tempObj.getString("idevent");
                            String temp_eventname = tempObj.getString("eventname");
                            String temp_eventdesc = tempObj.getString("eventdesc");
                            String temp_eventtime = tempObj.getString("eventtime");
                            String temp_eventdate = tempObj.getString("eventdate");
                            String temp_venue = tempObj.getString("venue");
                            String temp_fee = tempObj.getString("fee");
                            String temp_schoolid = tempObj.getString("schoolid");
                            String temp_clubid = tempObj.getString("clubid");
                            String temp_istrending = tempObj.getString("istrending");
                            String temp_status = tempObj.getString("status");
                            String temp_minteamsize = tempObj.getString("minteamsize");
                            String temp_maxteamsize = tempObj.getString("maxteamsize");
                            String temp_eventtype = tempObj.getString("eventtype");
                            String temp_posterurl = tempObj.getString("posterurl");
                            //id_stor.add(temp_id);



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

                            model.insert(new Event(temp_idevent, temp_eventname, temp_eventdesc,
                                    temp_eventtime, temp_eventdate, temp_venue, temp_fee,
                                    temp_schoolid, temp_clubid, temp_istrending, temp_status,
                                    temp_minteamsize, temp_maxteamsize, temp_eventtype, temp_posterurl));

                        }
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }



                    //Log.d("insert :::::",All);

                }

            }
        });

        EventfetchTask.start();


        Thread TshirtfetchTask = new Thread(new Runnable() {

            @Override
            public void run() {
                try {


                    OkHttpClient client = new OkHttpClient();
                    Log.d("URL ",tshirtlink.toString());

                    //httpConnection.connect();
                    Request.Builder builder = new Request.Builder();
                    builder.url(tshirtlink);
                    Request request = builder.build();

                    String line="";

                    Response response = client.newCall(request).execute();
                    if( response.body() != null)
                    {
                        line+= response.body().string().trim();
                        All= line;
                        Log.d(TAG+"1",Integer.toString(All.length())+line);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

                if(All!=null && All.length()>2)
                {




                    try
                    {
                        //EventRoomDatabase db = EventRoomDatabase.getDatabase(getApplication());
                        //EventDao w=db.eventDao();

                        TshirtViewModel model = new TshirtViewModel(getApplication());
                        JSONArray mainObjtshirt = new JSONArray(All);

                        for (int i=0;i<mainObjtshirt.length();i++)
                        {
                            JSONObject tempObj = new JSONObject(mainObjtshirt.get(i).toString());

                            //String temp_idevent = tempObj.getString("idevent");
                            //String temp_body = tempObj.getString("data");
                            //String temp_date = tempObj.getString("date");


                            String temp_id = tempObj.getString("id");
                            String temp_Tshirtname = tempObj.getString("name");
                            String temp_Tshirtfront = tempObj.getString("front");
                            String temp_Tshirtback = tempObj.getString("back");
                            String temp_Tshirtside = tempObj.getString("side");
                            String temp_Tshirtdesc = tempObj.getString("description");
                            String temp_fee = tempObj.getString("price");

                            //id_stor.add(temp_id);



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

                            model.insert(new Tshirt(temp_id, temp_Tshirtname, temp_Tshirtfront,
                                    temp_Tshirtback, temp_Tshirtside, temp_Tshirtdesc, temp_fee));

                        }
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }



                    //Log.d("insert :::::",All);

                }

            }
        });

        TshirtfetchTask.start();

    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        stopSelf();
        Log.d("Back Fetch","stopped");
        return false;
    }
}

