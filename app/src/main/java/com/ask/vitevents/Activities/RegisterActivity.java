package com.ask.vitevents.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ask.vitevents.R;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    Spinner campus_chooser;
    Button register_btn;
    EditText fullname,username,email,phone,password;
    String f_name,u_name,e_mail,p_phone,pass,campi;
    boolean connected;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    public static final String myprefs = "login.conf";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fullname = findViewById(R.id.full_name);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        register_btn = findViewById(R.id.register_btn);
        campus_chooser = findViewById(R.id.college_campus);
        prefs = getSharedPreferences(myprefs, Context.MODE_PRIVATE);
        editor = prefs.edit();
        List<String> campus = new ArrayList<String>();
        campus.add("Click to select college");
        campus.add("VIT Vellore");
        campus.add("VIT Chennai");
        campus.add("VIT Bhopal");
        campus.add("VIT AP");
        ArrayAdapter<String> dataadapter = new ArrayAdapter<String>(this,R.layout.spinner_item,campus);
        dataadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        campus_chooser.setAdapter(dataadapter);
        connected = false;
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                f_name = fullname.getText().toString();
                u_name = username.getText().toString();
                e_mail = email.getText().toString();
                p_phone = phone.getText().toString();
                pass = password.getText().toString();
                campi = String.valueOf(campus_chooser.getSelectedItem());
                final ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                    //we are connected to a network
                    connected = true;
                }
                else {
                    connected = false;
                }
                Log.d("...Msg..",f_name);
                if(connected) {
                    Log.d("...Msg..",u_name);
                    if(!f_name.equals("") && !u_name.equals("") && !pass.equals("")
                            && !p_phone.equals("") && !e_mail.equals("") && !campi.equals("")) {
                        HashMap data = new HashMap();
                        data.put("name", f_name);
                        data.put("username", u_name);
                        data.put("password", pass);
                        data.put("phone", p_phone);
                        data.put("email", e_mail);
                        data.put("college", campi);

                        PostResponseAsyncTask task = new PostResponseAsyncTask(RegisterActivity.this, data, new AsyncResponse() {
                            @Override
                            public void processFinish(String s) {
                                Log.d("=====msg===",s);
                                if (s.trim().equals("user")) {
                                    Toast.makeText(RegisterActivity.this, "Username exists", Toast.LENGTH_SHORT).show();

                                } else if (s.trim().equals(("server"))) {
                                    Toast.makeText(RegisterActivity.this, "Server Error Plz try again later", Toast.LENGTH_SHORT).show();
                                } else if (s.trim().equals(("phone"))) {
                                    Toast.makeText(RegisterActivity.this, "Phone Number already in use", Toast.LENGTH_SHORT).show();
                                } else if (s.trim().equals(("email"))) {
                                    Toast.makeText(RegisterActivity.this, "Email already in use", Toast.LENGTH_SHORT).show();
                                } else if (s.trim().equals("success")) {
                                    Toast.makeText(RegisterActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                                    editor.putString("username",u_name);
                                    editor.putString("password",pass);
                                    editor.putString("Name",f_name);
                                    editor.apply();
                                    Intent launchNextActivity;
                                    launchNextActivity = new Intent(RegisterActivity.this, LoginActivity.class);
                                    launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(launchNextActivity);
                                }
                            }
                        });
                        task.execute("https://www.vitchennaievents.com/register/android/Appregister.php");
                    }
                    else{
                        Toast.makeText(RegisterActivity.this,"Please fill all fields !",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(RegisterActivity.this,"Please connect to the internet and try again",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}