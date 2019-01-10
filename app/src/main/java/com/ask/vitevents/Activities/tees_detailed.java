package com.ask.vitevents.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ask.vitevents.Adapters.SliderAdapter;
import com.ask.vitevents.Classes.Tshirt;
import com.ask.vitevents.R;
import com.ask.vitevents.RoomDb.TshirtViewModel;

import java.util.ArrayList;

public class tees_detailed extends AppCompatActivity {


    ViewPager viewp;
    SliderAdapter sliderAdapter;
    ArrayList<String> dataurl;
    ProgressDialog dialog;
    TextView teename, teeprice;
    Button register_tee;
    TshirtViewModel tshirtViewModel;

    String tid;
    int position;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tees_detailed);

        tid = getIntent().getExtras().getString("tid");
        Log.d("===tid=", tid);

        teename = findViewById(R.id.tee_name);
        teeprice = findViewById(R.id.tee_price);
        register_tee = findViewById(R.id.register_tee);

        tshirtViewModel = ViewModelProviders.of(this).get(TshirtViewModel.class);

        dialog = ProgressDialog.show(this, "",
                "Loading. Please wait...", true);

        dataurl = new ArrayList<>();

        set_data_offline();

        viewp = findViewById(R.id.vp_slider);

        register_tee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("=====cl","hi");




                final CharSequence items[] = {"S","M","L","XL","XXL"};
                AlertDialog.Builder builder = new AlertDialog.Builder(tees_detailed.this);
                builder.setTitle("Size")
                        .setSingleChoiceItems(items, 0,new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                position = which;
                                //Toast.makeText(getApplicationContext(), items[which] + " is clicked", Toast.LENGTH_SHORT).show();
                            }
                        })

                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                final EditText qty = new EditText(tees_detailed.this);
                                qty.setText("1");

                                AlertDialog.Builder builder = new AlertDialog.Builder(tees_detailed.this);
                                builder.setTitle("Number of Tshirt")
                                        .setView(qty)
                                        .setPositiveButton("CASH", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Intent Reg = new Intent(tees_detailed.this,Register_tees.class);
                                                Reg.putExtra("tid",tid);
                                                int size=0;
                                                if (items[position].equals("S"))
                                                    size=0;
                                                else if (items[position].equals("M"))
                                                    size=1;
                                                else if (items[position].equals("L"))
                                                    size=2;
                                                else if (items[position].equals("XL"))
                                                    size=3;
                                                else
                                                    size=4;

                                                Reg.putExtra("size",Integer.toString(size));
                                                Reg.putExtra("qty",qty.getText().toString());

                                                Log.d("=====size",Integer.toString(size));
                                                Log.d("====qty", qty.getText().toString());

                                                startActivity(Reg);


                                            }
                                        });

                                //builder.setNeutralButton("NEUTRAL", null);
                                //builder.setPositiveButtonIcon(getResources().getDrawable(android.R.drawable.ic_menu_call, getTheme()));
                                //builder.setIcon(getResources().getDrawable(R.drawable.jd, getTheme()));

                                AlertDialog alertDialog = builder.create();

                                alertDialog.show();

                                Button btn_cash = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                                btn_cash.setBackgroundColor(Color.BLUE);
                                //btn_cash.setPadding(0, 0, 20, 0);
                                btn_cash.setTextColor(Color.WHITE);
                                btn_cash.setWidth(350);





                            }
                        })
                        .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                //builder.setNeutralButton("NEUTRAL", null);
                //builder.setPositiveButtonIcon(getResources().getDrawable(android.R.drawable.ic_menu_call, getTheme()));
                //builder.setIcon(getResources().getDrawable(R.drawable.jd, getTheme()));

                AlertDialog alertDialog = builder.create();

                alertDialog.show();


            }
        });
    }


    void set_data_offline()
    {

        try {

            tshirtViewModel.getTshirtById(tid).observe(this, new Observer<Tshirt>() {
                @Override
                public void onChanged(@Nullable Tshirt shirt) {

                    try{
                        teename.setText(shirt.getTshirtname());
                        teeprice.setText(shirt.getTshirtprice());
                        dataurl.add(get_url().appendPath(shirt.getTshirtfront().trim()).build().toString());
                        dataurl.add(get_url().appendPath(shirt.getTshirtback().trim()).build().toString());
                        dataurl.add(get_url().appendPath(shirt.getTshirtside().trim()).build().toString());

                        sliderAdapter = new SliderAdapter(tees_detailed.this,dataurl);
                        viewp.setAdapter(sliderAdapter);
                        sliderAdapter.notifyDataSetChanged();

                        Log.d("===tee det", get_url().appendPath(shirt.getTshirtfront().trim()).build().toString());

                        dialog.dismiss();
                    }catch (Exception ex) {
                        Toast.makeText(getApplicationContext(),"Please Check Your Internet Connection !",Toast.LENGTH_LONG).show();
                        Log.e(getPackageName(), ex.toString());
                        finish();
                    }

                }
            });

        } catch (Exception ex) {
            Toast.makeText(this,"Please Check Your Internet Connection !",Toast.LENGTH_LONG).show();
            Log.e(getPackageName(), ex.toString());
            finish();
        }


    }

    Uri.Builder get_url()
    {
        Uri.Builder tshirtbuilder = new Uri.Builder();
        tshirtbuilder.scheme("https")
                .authority("www.vitchennaievents.com")
                .appendPath("register")
                .appendPath("shirt")
                .appendPath("upload");




        return tshirtbuilder;
    }


}
