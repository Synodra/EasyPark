package com.example.seynabouba.navbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class CreationCompteActivity extends AppCompatActivity {

    ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_compte);

        logo = (ImageView)findViewById(R.id.imageView);
        logo.setImageResource(R.drawable.logo);
    }
}
