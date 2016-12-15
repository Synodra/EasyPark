package com.example.seynabouba.navbar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.Timer;
import java.util.TimerTask;

public class LogoActivity extends AppCompatActivity {

    ImageButton logoEntree;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        logoEntree = (ImageButton) findViewById(R.id.imageButton);
        logoEntree.setImageResource(R.drawable.logo);
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(LogoActivity.this, ConnexionActivity.class);
                startActivity(intent);
            }
        }, 2000);
        logoEntree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pageConnexion = new Intent(LogoActivity.this, ConnexionActivity.class);
                startActivity(pageConnexion);


            }
        });
    }}