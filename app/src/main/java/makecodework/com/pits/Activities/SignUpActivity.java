package makecodework.com.pits.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import makecodework.com.pits.Model.User;
import makecodework.com.pits.R;

public class SignUpActivity extends AppCompatActivity {

    private EditText enterPhone, enterPass, enterName, enterCode;
    private Button signUp, verifCode;

    private FirebaseAuth mAuth;

    private String verificationId;

    String num = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        enterPhone = (MaterialEditText) findViewById(R.id.enter_phone);
        enterPass  = (MaterialEditText) findViewById(R.id.enter_pass);
        enterName  = (MaterialEditText) findViewById(R.id.enter_name);
        enterCode  = (MaterialEditText) findViewById(R.id.secure_code);

        signUp = findViewById(R.id.sign_up);

        //FireBase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");
        mAuth = FirebaseAuth.getInstance();

        if(getIntent() != null){
             num = getIntent().getStringExtra("num");
        }

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog dialog = new ProgressDialog(SignUpActivity.this);
                dialog.setMessage("Будь ласка, чекайте....");
                dialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // Check if user already phone
                        if(dataSnapshot.child(enterPhone.getText().toString()).exists()){
                            dialog.dismiss();
                            Toast.makeText(SignUpActivity.this, "Номер телефону вже існує!", Toast.LENGTH_SHORT).show();

                        }else{
                            dialog.dismiss();
                            User user = new User(enterName.getText().toString(), enterPass.getText().toString(),enterCode.getText().toString());
                            table_user.child(enterPhone.getText().toString()).setValue(user);
                            Toast.makeText(SignUpActivity.this, "Реєстрація пройшла успішно!", Toast.LENGTH_SHORT).show();
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
