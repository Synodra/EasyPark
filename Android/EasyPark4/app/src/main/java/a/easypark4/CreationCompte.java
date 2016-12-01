package a.easypark4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class CreationCompte extends AppCompatActivity {

    ImageView logo;
    Button creercompte;
    EditText nom, prenom, login, password, email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_compte);
        logo = (ImageView)findViewById(R.id.imageView);
        logo.setImageResource(R.drawable.logo);
    }
}
