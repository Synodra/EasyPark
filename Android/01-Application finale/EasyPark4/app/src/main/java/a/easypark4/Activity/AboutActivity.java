package a.easypark4.Activity;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import a.easypark4.R;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.google.android.gms.analytics.internal.zzy.n;

public class AboutActivity extends AppCompatActivity {

    private ImageButton imageLogo, imageEquipe;
    private TextView textAbout;
    Button buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);


        final ImageButton imageLogo = (ImageButton) findViewById(R.id.imageButtonLogo);
        final ImageButton imageEquipe = (ImageButton) findViewById(R.id.imageButtonequipe);
        final TextView textAbout = (TextView) findViewById(R.id.textAbout);
        final Button buttonBack = (Button) findViewById(R.id.buttonBack);

        textAbout.setVisibility(GONE);
        buttonBack.setVisibility(GONE);

        imageLogo.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                imageLogo.setVisibility(GONE);
                imageEquipe.setVisibility(GONE);
                textAbout.setVisibility(VISIBLE);
                buttonBack.setVisibility(VISIBLE);
                textAbout.setText("Easypark is an engineering project developped by students in ESIGELEC of Rouen. This application will change your way to search for a parking spot ! ");

            }});

        imageEquipe.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                imageLogo.setVisibility(GONE);
                imageEquipe.setVisibility(GONE);
                textAbout.setVisibility(VISIBLE);
                buttonBack.setVisibility(VISIBLE);
                textAbout.setText("Antoine Mayslisch: ESAA \n" +
                        "Abdirahim Aden: ISE OC \n" +
                        "Dingjie Ma: ISE OC \n" +
                        "Guillaume Sanchez: IA-IR \n" +
                        "Nicolas Cailleux: ISN \n" +
                        "Seynabou Bâ: ISE OC \n");

            }});

        buttonBack.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AboutActivity.this, MainActivity.class);
                startActivity(intent);

            }});
    }
}
