package com.ask.vitevents.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ask.vitevents.R;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import java.util.HashMap;

public class RegisterActivityOthers extends AppCompatActivity {

    Button register_btn;
    EditText fullname,username,email,phone,password,coll;
    String f_name,u_name,e_mail,p_phone,pass,college;
    boolean connected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_others);
        fullname = findViewById(R.id.full_name_others);
        username = findViewById(R.id.username_others);
        email = findViewById(R.id.email_others);
        phone = findViewById(R.id.phone_others);
        password = findViewById(R.id.password_others);
        coll = findViewById(R.id.college_others);
        register_btn = findViewById(R.id.register_btn_others);
        connected=false;
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                f_name = fullname.getText().toString();
                u_name = username.getText().toString();
                e_mail = email.getText().toString();
                p_phone = phone.getText().toString();
                pass = password.getText().toString();
                college = coll.getText().toString();
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
                            && !p_phone.equals("") && !e_mail.equals("") && !college.equals("")) {
                        HashMap data = new HashMap();
                        data.put("name", f_name);
                        data.put("username", u_name);
                        data.put("password", pass);
                        data.put("phone", p_phone);
                        data.put("email", e_mail);
                        data.put("college", college);
                        PostResponseAsyncTask task = new PostResponseAsyncTask(RegisterActivityOthers.this, data, new AsyncResponse() {
                            @Override
                            public void processFinish(String s) {
                                if (s.trim().equals("user")) {
                                    Toast.makeText(RegisterActivityOthers.this, "Username exists", Toast.LENGTH_SHORT).show();

                                } else if (s.trim().equals(("server"))) {
                                    Toast.makeText(RegisterActivityOthers.this, "Server Error Plz try again later", Toast.LENGTH_SHORT).show();
                                } else if (s.trim().equals(("phone"))) {
                                    Toast.makeText(RegisterActivityOthers.this, "Phone Number already in use", Toast.LENGTH_SHORT).show();
                                } else if (s.trim().equals(("email"))) {
                                    Toast.makeText(RegisterActivityOthers.this, "Email already in use", Toast.LENGTH_SHORT).show();
                                } else if (s.trim().equals("success")) {
                                    Toast.makeText(RegisterActivityOthers.this, "Account Created", Toast.LENGTH_SHORT).show();
                                    Intent launchNextActivity;
                                    launchNextActivity = new Intent(RegisterActivityOthers.this, LoginActivity.class);
                                    launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(launchNextActivity);
                                }
                            }
                        });
                        task.execute("https://www.vitchennaievents.com/register/android/Appregister.php");
                    }
                    else{
                        Toast.makeText(RegisterActivityOthers.this,"Please fill all fields !",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(RegisterActivityOthers.this,"Please connect to the internet and try again",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
