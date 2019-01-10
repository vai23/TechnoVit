package com.ask.vitevents.Activities;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.ask.vitevents.R;


import static com.ask.vitevents.RoomDb.RootWork.server_ip;


public class AppPayment extends AppCompatActivity {

    Button backhome, proceedpayment;
    LinearLayout web;
    SharedPreferences prefs;
    public static final String myprefs = "login.conf";
    String eventId = "";
    String tid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        web = findViewById(R.id.weblinear);
        backhome = findViewById(R.id.gobackhome);
        proceedpayment = findViewById(R.id.proceedpay);
        prefs = getSharedPreferences(myprefs, Context.MODE_PRIVATE);

        try {
            eventId = getIntent().getExtras().getString("eventId");
            tid = getIntent().getExtras().getString("tid");
        }catch (Exception e)
        {
            Log.d(getPackageName(),"Null Pointer in intent");
            finish();
        }


        final String uid = prefs.getString("username", "");


        backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchNextActivity;
                launchNextActivity = new Intent(AppPayment.this, MainActivity.class);
                launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(launchNextActivity);
            }
        });
        proceedpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String urlString= "https://"+server_ip + "/register/android/AppPayment.php?username=" + uid.trim() + "&event=" + eventId.trim()+"&teamID="+tid.trim();
                Log.d("====payment url",urlString);
                Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse(urlString));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage("com.android.chrome");
                try {
                    AppPayment.this.startActivity(intent);
                } catch (ActivityNotFoundException ex) {
                    // Chrome browser presumably not installed so allow user to choose instead
                    intent.setPackage(null);
                    AppPayment.this.startActivity(intent);
                }
            }
        });

    }

}
