package com.msadev.cmms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Reminder extends AppCompatActivity {

    TextView title, subtitle, endpage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        title = findViewById(R.id.title);
        subtitle = findViewById(R.id.subtitle);
        endpage = findViewById(R.id.endpage);
    }
}
