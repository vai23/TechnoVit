package com.ask.vitevents.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ask.vitevents.R;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import static com.ask.vitevents.RoomDb.RootWork.server_ip;

public class ForgotPassword extends AppCompatActivity {

    EditText email;
    Button submit;
    URL url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        email = findViewById(R.id.forgotpassemail);
        submit = findViewById(R.id.butforget);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap data=new HashMap();
                data.put("email",email.getText().toString());
                Uri.Builder eventbuilder = new Uri.Builder();
                eventbuilder.scheme("https")
                        .authority(server_ip)
                        .appendPath("register")
                        .appendPath("android")
                        .appendPath("AppForgotPassword.php");

                try {
                    url = new URL(eventbuilder.build().toString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                PostResponseAsyncTask task = new PostResponseAsyncTask(ForgotPassword.this, data, new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {
                        if(s.equals("success")){
                            Toast.makeText(ForgotPassword.this,"Account details sent to your mail::"+s,Toast.LENGTH_LONG).show();
                            Intent launchNextActivity;
                            launchNextActivity = new Intent(ForgotPassword.this, LoginActivity.class);
                            launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(launchNextActivity);
                        }
                        else{
                            Toast.makeText(ForgotPassword.this, "User with the email does not exist::"+s, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                task.execute(url.toString());
            }
        });


    }

}

