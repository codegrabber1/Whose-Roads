package makecodework.com.whoseroads.Activities;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.*;
import com.rengwuxian.materialedittext.MaterialEditText;
import makecodework.com.whoseroads.Model.User;
import makecodework.com.whoseroads.R;

public class SignUpActivity extends AppCompatActivity {

    private EditText enterPhone, enterPass, enterName;
    private Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        enterPhone = (MaterialEditText) findViewById(R.id.enter_phone);
        enterPass = (MaterialEditText) findViewById(R.id.enter_pass);
        enterName = (MaterialEditText) findViewById(R.id.enter_name);
        signUp = findViewById(R.id.sign_up);

        //FireBase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog dialog = new ProgressDialog(SignUpActivity.this);
                dialog.setMessage("Please waiting....");
                dialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // Check if user already phone
                        if(dataSnapshot.child(enterPhone.getText().toString()).exists()){
                            dialog.dismiss();
                            Toast.makeText(SignUpActivity.this, "The phone number already exist!", Toast.LENGTH_SHORT).show();

                        }else{
                            dialog.dismiss();
                            User user = new User(enterName.getText().toString(), enterPass.getText().toString());
                            table_user.child(enterPhone.getText().toString()).setValue(user);
                            Toast.makeText(SignUpActivity.this, "Sign Up successfully!", Toast.LENGTH_SHORT).show();
                            finish();

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
