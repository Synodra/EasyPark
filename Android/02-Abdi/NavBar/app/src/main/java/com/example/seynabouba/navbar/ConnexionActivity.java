package com.example.seynabouba.navbar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ConnexionActivity extends AppCompatActivity {

    EditText user, password;

    //ImageView logo;

    Button connexion, nouveauCompte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);


        user = (EditText) findViewById(R.id.editText);
        password = (EditText) findViewById(R.id.editText2);
        password.setTransformationMethod(new PasswordTransformationMethod());

        /*logo = (ImageView) findViewById(R.id.imageView);
        logo.setImageResource(R.drawable.logo);*/

        nouveauCompte = (Button) findViewById(R.id.button2);
        nouveauCompte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConnexionActivity.this, CreationCompteActivity.class);
                startActivity(intent);
            }
        });


        connexion = (Button) findViewById(R.id.button);
        connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = user.getText().toString();
                String pass = password.getText().toString();
                if (username.equalsIgnoreCase("admin") && pass.equalsIgnoreCase("admin")) {

                    Intent intent = new Intent(ConnexionActivity.this, MainActivity.class);

                    startActivity(intent);
                } else if (!(pass.equalsIgnoreCase("admin")) || !(username.equalsIgnoreCase("admin"))) {
                    Toast.makeText(getApplicationContext(), "ERROR : Identification impossible.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}
