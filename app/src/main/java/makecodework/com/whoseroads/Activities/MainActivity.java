package makecodework.com.whoseroads.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import makecodework.com.whoseroads.R;

public class MainActivity extends AppCompatActivity {

    private Button SIgnIn,SIgnUp;
    private TextView slogan;
    private ImageView logo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SIgnIn = findViewById(R.id.signin);
        SIgnUp = findViewById(R.id.signup);
        slogan = findViewById(R.id.slogan);
        logo = findViewById(R.id.logo_img);

        SIgnIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,SignInActivity.class);
                startActivity(i);

            }
        });

        SIgnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(i);

            }
        });
    }
}
