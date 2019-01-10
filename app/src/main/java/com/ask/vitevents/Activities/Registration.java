package com.ask.vitevents.Activities;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ask.vitevents.R;

import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import pl.droidsonroids.gif.GifTextView;

import static com.ask.vitevents.RoomDb.RootWork.server_ip;

public class Registration extends AppCompatActivity {

    String Eventid,teamid;
    Handler update;
    //ImageView loader;
    Button back;
    TextView message;
    GifTextView gif, gif1;
    //GifDrawable gifFromResource

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        Eventid = getIntent().getExtras().getString("eventid");
        teamid = getIntent().getExtras().getString("teamid");

        gif = findViewById(R.id.registerload);
        gif1 = findViewById(R.id.registerloadfinish);
        gif1.setVisibility(View.GONE);
        back = findViewById(R.id.register_back);
        back.setVisibility(View.GONE);
        message = findViewById(R.id.registerloadmsg);

        update = new Handler();
        //loader = findViewById(R.id.loader);

        //Glide.with(this).asGif().load(R.drawable.loading).into(loader);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //Toast.makeText(this,Eventid+"  "+teamid,Toast.LENGTH_LONG).show();
        register.start();
    }


    Thread register = new Thread(new Runnable() {
        @Override
        public void run()
        {
            try {

                Uri.Builder builder = new Uri.Builder();
                builder.scheme("https")
                        .authority(server_ip)
                        .appendPath("register")
                        .appendPath("android")
                        .appendPath("AppRegisterEvent.php")
                        .appendQueryParameter("eventid", Eventid)
                        .appendQueryParameter("tid",teamid);

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




                    if (All.equalsIgnoreCase("1"))
                    {

                        update.post(new Runnable() {
                            @Override
                            public void run() {
                                //TextView work = findViewById(R.id.status);
                                gif.setVisibility(View.GONE);
                                gif1.setVisibility(View.VISIBLE);
                                back.setVisibility(View.VISIBLE);
                                message.setText("REGISTERED");
                                Toast.makeText(getApplicationContext(),"Done",Toast.LENGTH_LONG).show();
                            }
                        });

                        //finish();
                    }else {
                        update.post(new Runnable() {
                            @Override
                            public void run() {
                                //TextView work = findViewById(R.id.status);

                                back.setVisibility(View.VISIBLE);
                                message.setText("FAILED, Try Again Later");
                                Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
                            }
                        });
                    }



            } catch (MalformedURLException e) {
                e.printStackTrace();
            }catch (Exception e)
            {
                Log.d("error",getPackageName());
            }

        }
    });


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
