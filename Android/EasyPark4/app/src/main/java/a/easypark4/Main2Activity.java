package a.easypark4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(getIntent().getStringExtra("login"));

        TextView textView2 = (TextView) findViewById(R.id.textView4);
        textView2.setText(getIntent().getStringExtra("log"));
    }
}
