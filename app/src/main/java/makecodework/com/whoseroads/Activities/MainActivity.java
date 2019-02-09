package makecodework.com.whoseroads.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.*;
import io.paperdb.Paper;
import makecodework.com.whoseroads.Common.Common;
import makecodework.com.whoseroads.Model.User;
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


        Paper.init(this);

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

        String user = Paper.book().read(Common.USER_KEY);
        String pwd = Paper.book().read(Common.USER_PSW);

        if(user != null && pwd != null){
            if(user.isEmpty() && pwd.isEmpty()){
                login(user,pwd);
            }
        }
    }

    private void login(final String phone, final String pwd) {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        final ProgressDialog dialog = new ProgressDialog(MainActivity.this);
        dialog.setMessage("Please waiting....");
        dialog.show();

        table_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // Check if user not exist in database.
                if(dataSnapshot.child(phone).exists()){

                    //Get User from Model/User.class
                    dialog.dismiss();

                    User user = dataSnapshot.child(phone).getValue(User.class);
                    if(user.getPassword().equals(pwd)){

                        Intent homeIntent = new Intent(MainActivity.this, Home.class);
                        Common.currentUser = user;
                        startActivity(homeIntent);
                        finish();

                    }else{

                        Toast.makeText(MainActivity.this, "Sign In failed!", Toast.LENGTH_SHORT).show();
                    }
                }else{

                    dialog.dismiss();
                    Toast.makeText(MainActivity.this, "User not exist in Database", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
