package com.ask.vitevents.Activities;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.ask.vitevents.R;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.ask.vitevents.RoomDb.RootWork.server_ip;

public class AddTeamActivity extends AppCompatActivity {

    LinearLayout adder_layout, text_view_holder;
    int count;
    Button add_but,but_confirm;
    EditText teammem,teamname;
    Spinner team_count;
    List<EditText> allEds;

    ProgressDialog dialog;
    String[] strings;
    Handler update;
    SharedPreferences prefs;
    public static final String myprefs = "login.conf";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_team);
        adder_layout = findViewById(R.id.team_add);
        text_view_holder=findViewById(R.id.team_text_view);
        but_confirm = findViewById(R.id.confirm_team);
        teamname = findViewById(R.id.teamname);
        count = 0;
        prefs = getSharedPreferences(myprefs,MODE_PRIVATE);
        team_count = findViewById(R.id.teamcount_select);
        List<String> team = new ArrayList<String>();
        team.add("Click to select number of members");
        team.add("1");
        team.add("2");
        team.add("3");
        team.add("4");
        team.add("5");
        team.add("6");
        team.add("7");
        team.add("8");
        team.add("9");
        team.add("10");



        ArrayAdapter<String> dataadapter = new ArrayAdapter<String>(this,R.layout.spinner_item,team);
        dataadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        team_count.setAdapter(dataadapter);
        team_count.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                text_view_holder.removeAllViews();
                if(position == 0){
                    count = 0;
                }
                else {
                    count = Integer.parseInt(parent.getItemAtPosition(position).toString());
                }
                allEds = new ArrayList<EditText>();
                //Drawable drawable=getApplicationContext().getResources().getDrawable(R.drawable.team_text_view_border);

                for (int i = 0; i < count; i++) {

                    teammem = new EditText(AddTeamActivity.this);
                    //teammem.setCompoundDrawablesWithIntrinsicBounds(drawable,null,null,null);
                    allEds.add(teammem);

                    teammem.setId((int) id);
                    teammem.setTextColor(getResources().getColor(R.color.white));
                    teammem.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
                    text_view_holder.addView(teammem);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                count = 0;
            }
        });
        but_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size = allEds.size();
                strings = new String[size];

                for(int i=0; i < allEds.size(); i++){
                    strings[i] = allEds.get(i).getText().toString();
                }
                dialog= ProgressDialog.show(AddTeamActivity.this, "",
                        "Loading. Please wait...", true);
                register.start();
            }
        });

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
                        .appendPath("Appcreateteam.php")
                        .appendQueryParameter("teamname", teamname.getText().toString())
                        .appendQueryParameter("teamsize",Integer.toString(count))
                        .appendQueryParameter("userID",prefs.getString("username",""));
                for(int i = 0;i<allEds.size();i++)
                {
                    builder.appendQueryParameter("member"+Integer.toString(i+1),strings[i]);
                }

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

                            Toast.makeText(getApplicationContext(),"Done",Toast.LENGTH_LONG).show();
                        }
                    });

                    //finish();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Failed to add team",Toast.LENGTH_LONG).show();
                }



            } catch (MalformedURLException e) {
                e.printStackTrace();
            }catch (Exception e)
            {
                //Toast.makeText(AddTeamActivity.this, "Some Error Occured",Toast.LENGTH_SHORT).show();
                Log.d("error",getPackageName());
            }finally {
                dialog.dismiss();
            }

        }
    });
}
