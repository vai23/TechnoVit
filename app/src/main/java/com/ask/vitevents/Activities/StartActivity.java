package com.ask.vitevents.Activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ask.vitevents.R;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import pl.droidsonroids.gif.GifTextView;

import static com.ask.vitevents.RoomDb.RootWork.server_ip;

public class StartActivity extends AppCompatActivity {

    Button log,reg,refresh;
    SharedPreferences prefs;
    public static final String myprefs = "login.conf";
    boolean connected;
    TextView message;
    GifTextView gifload;
    boolean conn = false;
    URL url;
    Dialog updatedialog;
    String currentVersion;
    TextView txtclose;
    Button updateapp;

    private static int TIME_OUT = 5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        log = findViewById(R.id.start_login_but);
        reg = findViewById(R.id.start_reg_but);
        message = findViewById(R.id.loadmessage);
        gifload = findViewById(R.id.waitload);
        refresh = findViewById(R.id.refresh_but);
        updatedialog = new Dialog(this);
        updatedialog.setContentView(R.layout.update_dialog);
        updatedialog.setCanceledOnTouchOutside(false);
        updatedialog.setCancelable(false);
        txtclose = updatedialog.findViewById(R.id.txtclose);
        updateapp = updatedialog.findViewById(R.id.btnupdate);
        prefs = getSharedPreferences(myprefs,MODE_PRIVATE);
        conn = isConnected();

        txtclose = updatedialog.findViewById(R.id.txtclose);
        updateapp = updatedialog.findViewById(R.id.btnupdate);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatedialog.dismiss();
                finish();
            }
        });
        updateapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.ask.technovit"));
                startActivity(intent);
            }
        });

        try {
            currentVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            updatecheck();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if(conn){
            updatecheck();
        }
        else{
            message.setText("Connect to internet and try again!");
            refresh.setVisibility(View.VISIBLE);
            gifload.setVisibility(View.GONE);
        }


        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activity = new Intent(StartActivity.this,LoginActivity.class);
                startActivity(activity);
            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activity = new Intent(StartActivity.this,SelectActivity.class);
                startActivity(activity);
            }
        });


    }

    public boolean isConnected() {
        final ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else {
            connected = false;
            message.setText("Please Check your internet connection and try again....");
            gifload.setVisibility(View.GONE);
            refresh.setVisibility(View.VISIBLE);
        }
        return connected;
    }
    public void updatecheck(){
        @SuppressLint("StaticFieldLeak") AsyncTask<Void, String, String> checkupdate = new AsyncTask<Void, String, String>() {

            @Override
            protected void onPreExecute() {
                gifload.setVisibility(View.VISIBLE);
                message.setVisibility(View.VISIBLE);
                message.setText("Checking for Update and logging in...");
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(Void... voids) {
                String newVersion = null;


                try {
                    Uri.Builder eventbuilder = new Uri.Builder();
                    eventbuilder.scheme("https")
                            .authority("androwork2k.000webhostapp.com")
                            //.appendPath("register")
                            .appendPath("android")
                            .appendPath("Appupdate.php");
                    URL datalink = new URL(eventbuilder.build().toString());
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
                    if( response1.body() != null) {
                        line += response1.body().string().trim();

                        Log.d("----version",line);
                    }
                    /*newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + getBaseContext().getPackageName() + "&hl=it")
                            .timeout(30000)
                            .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                            .referrer("http://www.google.com")
                            .get()
                            .select("div[itemprop=softwareVersion]")
                            .first()
                            .ownText();*/
                    return line;
                } catch (Exception e) {
                    return newVersion;
                }
            }

            @Override
            protected void onPostExecute(String onlineVersion) {
                gifload.setVisibility(View.GONE);
                super.onPostExecute(onlineVersion);
                if (onlineVersion != null && !onlineVersion.isEmpty()) {
                    Log.d("---CuurVersion::::",currentVersion);
                    Log.d("----Link::",onlineVersion);
                    if (!currentVersion.equalsIgnoreCase(onlineVersion)) {
                        updatedialog.show();
                    }
                    else{
                        updatedialog.dismiss();
                        afterupdate();
                    }
                }
            }
        };
        checkupdate.execute();
    }
    public void login(){
        try {


            Uri.Builder eventbuilder = new Uri.Builder();
            eventbuilder.scheme("https")
                    .authority(server_ip)
                    .appendPath("register")
                    .appendPath("android")
                    .appendPath("AppLogin.php");
            url = new URL(eventbuilder.build().toString());

            @SuppressLint("StaticFieldLeak") AsyncTask<String, Void, String> task = new AsyncTask<String, Void, String>() {

                @Override
                protected String doInBackground(String... urlW) {


                    try {
                        HttpURLConnection.setFollowRedirects(false);
                        HttpURLConnection con = (HttpURLConnection) new URL(urlW[0]).openConnection();
                        con.setRequestMethod("HEAD");
                        //Log.d("...response : ","456");
                        con.setConnectTimeout(TIME_OUT);
                        System.out.println(con.getResponseCode());
                        //Log.d("...response : ",Integer.toString(con.getResponseCode()));
                        if (!(con.getResponseCode() == HttpURLConnection.HTTP_OK)) {
                            connected = false;

                        }
                    } catch (Exception e) {
                        connected=false;
                        //System.out.print("Error");
                    }

                    if (connected) {
                        OkHttpClient client = new OkHttpClient();

                        RequestBody formBody = new FormBody.Builder()
                                .add("username", prefs.getString("username",""))
                                .add("password", prefs.getString("password",""))
                                .build();

                        Request requestc = new Request.Builder()
                                .url(url)
                                .post(formBody)
                                .build();
                        //Request requestc = new Request.Builder().url(url[0].trim() + "?username=" + username.trim() + "&password=" + password.trim()).build();
                        //Log.d("....Wellcome Web...", requestc.toString());
                        try {
                            Response response = client.newCall(requestc).execute();
                            String feed = (response.body().string());
                            //Log.d("...opening", feed);
                            return feed;

                        } catch (Exception e) {
                            e.printStackTrace();
                            return null;
                        }
                    }
                    else
                    {
                        return "not";
                    }
                }
                @Override
                protected void onPostExecute(String s) {

                    //Log.d("..Server :",s);
                    Log.d("+++USERNAME+++",s);
                    if(s.equals("not") ){
                        message.setText("Server not responding. Try again in some time !");
                        gifload.setVisibility(View.GONE);
                        refresh.setVisibility(View.VISIBLE);

                    }
                    else if (s.equalsIgnoreCase(prefs.getString("username",""))) {

                        Intent launchNextActivity;
                        launchNextActivity = new Intent(StartActivity.this, MainActivity.class);
                        launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(launchNextActivity);
                    }
                    else {
                        Toast.makeText(StartActivity.this, "Wrong Credentials", Toast.LENGTH_SHORT).show();
                        Intent launchNextActivity;
                        launchNextActivity = new Intent(StartActivity.this, LoginActivity.class);
                        launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(launchNextActivity);
                    }

                }
            };

            task.execute(url.toString());

        }
        catch (Exception e){
            System.out.print("error");
        }
    }
    public void afterupdate(){
        if(!(prefs.getString("username","")=="" || prefs.getString("password","")=="")) {

            if (conn) {
                Log.d("++++NAME::--",prefs.getString("username",""));
                Log.d("++++PASS::--",prefs.getString("password",""));
                login();
            } else {
                refresh.setVisibility(View.VISIBLE);
                gifload.setVisibility(View.GONE);
                message.setText("Connect to the internet and try again!");
            }
        }
        else {
            gifload.setVisibility(View.GONE);
            message.setVisibility(View.GONE);
            log.setVisibility(View.VISIBLE);
            reg.setVisibility(View.VISIBLE);
        }
    }
}
