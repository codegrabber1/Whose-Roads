package makecodework.com.pits.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;

import makecodework.com.pits.R;

public class StartActivity extends AppCompatActivity {
    private static int SPLAH_TIME_OUT = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent homeIntent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(homeIntent);
                finish();
            }
        },SPLAH_TIME_OUT);
    }
}
