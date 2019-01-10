package com.ask.vitevents.Activities;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ask.vitevents.R;

import java.net.MalformedURLException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import pl.droidsonroids.gif.GifTextView;

import static com.ask.vitevents.RoomDb.RootWork.server_ip;

public class Register_tees extends AppCompatActivity {


    String tid,size,qty;
    Handler update;
    Button back;
    TextView message;
    GifTextView gif, gif1;

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_tees);


        gif = findViewById(R.id.registerloadtee);
        gif1 = findViewById(R.id.registerloadfinishtee);
        gif1.setVisibility(View.GONE);
        back = findViewById(R.id.register_backtee);
        back.setVisibility(View.GONE);
        message = findViewById(R.id.registerloadmsgtee);


        tid = getIntent().getExtras().getString("tid");
        size = getIntent().getExtras().getString("size");
        qty = getIntent().getExtras().getString("qty");
        update = new Handler();

        sharedPreferences = getSharedPreferences("login.conf",MODE_PRIVATE);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        book_tee.start();

    }

    Thread book_tee = new Thread(new Runnable() {
        @Override
        public void run() {

            try{
            Uri.Builder tshirtbuilder = new Uri.Builder();
            tshirtbuilder.scheme("https")
                    .authority(server_ip)
                    .appendPath("register")
                    .appendPath("android")
                    .appendPath("AppTeesRegister.php")
                    .appendQueryParameter("tid",tid)
                    .appendQueryParameter("uid",sharedPreferences.getString("username",""))
                    .appendQueryParameter("size",size)
                    .appendQueryParameter("quan",qty);

            String datalink = tshirtbuilder.build().toString();

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




            if (All.equalsIgnoreCase("success"))
            {
                update.post(new Runnable() {
                    @Override
                    public void run() {
                        //TextView work = findViewById(R.id.status);
                        gif.setVisibility(View.GONE);
                        gif1.setVisibility(View.VISIBLE);
                        back.setVisibility(View.VISIBLE);
                        message.setText("BOOKED");
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
}
