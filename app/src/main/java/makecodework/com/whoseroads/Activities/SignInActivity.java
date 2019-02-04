package makecodework.com.whoseroads.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.*;
import com.rengwuxian.materialedittext.MaterialEditText;
import makecodework.com.whoseroads.Common.Common;
import makecodework.com.whoseroads.Model.User;
import makecodework.com.whoseroads.R;

public class SignInActivity extends AppCompatActivity {

    private EditText enterPhone,enterPass;
    private Button signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(makecodework.com.whoseroads.R.layout.activity_sign_in);

        enterPhone = (MaterialEditText) findViewById(makecodework.com.whoseroads.R.id.enter_phone);
        enterPass = (MaterialEditText) findViewById(makecodework.com.whoseroads.R.id.enter_pass);
        signIn = (Button) findViewById(R.id.sign_in);

        //FireBase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog dialog = new ProgressDialog(SignInActivity.this);
                dialog.setMessage("Please waiting....");
                dialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        // Check if user not exist in database.
                        if(dataSnapshot.child(enterPhone.getText().toString()).exists()){

                            //Get User from Model/User.class
                        dialog.dismiss();

                        User user = dataSnapshot.child(enterPhone.getText().toString()).getValue(User.class);
                        if(user.getPassword().equals(enterPass.getText().toString())){

                            Intent homeIntent = new Intent(SignInActivity.this, Home.class);
                            Common.currentUser = user;
                            startActivity(homeIntent);
                            finish();

                        }else{

                            Toast.makeText(SignInActivity.this, "Sign In failed!", Toast.LENGTH_SHORT).show();
                        }
                        }else{

                            dialog.dismiss();
                            Toast.makeText(SignInActivity.this, "User not exist in Database", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


    }
}
