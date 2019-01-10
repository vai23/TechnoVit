package com.ask.vitevents.Activities;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.ask.vitevents.R;


import static com.ask.vitevents.RoomDb.RootWork.server_ip;

public class Payment_Activity extends AppCompatActivity {

    Button backhome, proceedpayment;
    LinearLayout web;
    SharedPreferences prefs;
    public static final String myprefs = "login.conf";
    String eventId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activ_payment);
        web = findViewById(R.id.weblinear);
        backhome = findViewById(R.id.gobackhome);
        proceedpayment = findViewById(R.id.proceedpay);
        prefs = getSharedPreferences(myprefs, Context.MODE_PRIVATE);

        eventId = getIntent().getStringExtra("EventId");


        final String uid = prefs.getString("username", "");


        backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchNextActivity;
                launchNextActivity = new Intent(Payment_Activity.this, MainActivity.class);
                launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(launchNextActivity);
            }
        });
        proceedpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String urlString= server_ip + "/android/AppPayment.php?username=" + uid.trim() + "&event=" + eventId.trim()+"&teamID";
                Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse(urlString));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage("com.android.chrome");
                try {
                    Payment_Activity.this.startActivity(intent);
                } catch (ActivityNotFoundException ex) {
                    // Chrome browser presumably not installed so allow user to choose instead
                    intent.setPackage(null);
                    Payment_Activity.this.startActivity(intent);
                }
            }
        });

    }

}
