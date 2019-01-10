package com.ask.vitevents.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ask.vitevents.R;

public class SelectActivity extends AppCompatActivity {

    Button vitans,nvitans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        vitans = findViewById(R.id.vitans);
        nvitans = findViewById(R.id.nvitans);
        vitans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchnew = new Intent(SelectActivity.this,RegisterActivity.class);
                startActivity(launchnew);
            }
        });
        nvitans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchnew = new Intent(SelectActivity.this,RegisterActivityOthers.class);
                startActivity(launchnew);
            }
        });
    }
}
