package com.ask.vitevents.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ask.vitevents.R;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import java.net.URL;
import java.util.HashMap;

import static com.ask.vitevents.RoomDb.RootWork.server_ip;

public class LoginActivity extends AppCompatActivity {

    EditText login_id,login_pass;
    Button login_but;
    TextView not_registered,forgot_pass;
    boolean connected;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    public static final String myprefs = "login.conf";
    URL url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        try {


            Uri.Builder eventbuilder = new Uri.Builder();
            eventbuilder.scheme("https")
                    .authority(server_ip)
                    .appendPath("register")
                    .appendPath("android")
                    .appendPath("AppLogin.php");

            url = new URL(eventbuilder.build().toString());

            login_id = findViewById(R.id.loginid);
            login_pass = findViewById(R.id.loginpass);
            login_but = findViewById(R.id.login_but);
            not_registered = findViewById(R.id.text_not_reg);
            forgot_pass = findViewById(R.id.text_forgot_pass);
            prefs = getSharedPreferences(myprefs,MODE_PRIVATE);
            editor = prefs.edit();
            not_registered.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent launch = new Intent(LoginActivity.this, SelectActivity.class);
                    startActivity(launch);
                }
            });
            connected = false;
            login_but.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                        //we are connected to a network
                        connected = true;
                    } else {
                        connected = false;
                    }
                    if (connected) {
                        if (!login_id.getText().toString().equals("") && !login_pass.getText().toString().equals("")) {
                            HashMap data = new HashMap();
                            data.put("username", login_id.getText().toString());
                            data.put("password", login_pass.getText().toString());

                            PostResponseAsyncTask task = new PostResponseAsyncTask(LoginActivity.this, data, new AsyncResponse() {
                                @Override
                                public void processFinish(String s) {
                                    if (s.trim().equalsIgnoreCase(login_id.getText().toString())) {
                                        editor.putString("username",login_id.getText().toString());
                                        editor.putString("password",login_pass.getText().toString());
                                        editor.apply();
                                        Intent launchNextActivity;
                                        launchNextActivity = new Intent(LoginActivity.this, MainActivity.class);
                                        launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(launchNextActivity);
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Wrong Credentials"+s, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            task.execute(url.toString());
                        } else {
                            Toast.makeText(LoginActivity.this, "Please Fill all the fields !", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Please Connect to the internet and try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            forgot_pass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(LoginActivity.this,ForgotPassword.class));
                }
            });

        } catch (Exception e)
        {
            System.out.print("error");
        }

    }
}
