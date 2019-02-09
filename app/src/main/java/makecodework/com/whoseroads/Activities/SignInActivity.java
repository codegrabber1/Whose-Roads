package makecodework.com.whoseroads.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.google.firebase.database.*;
import com.rengwuxian.materialedittext.MaterialEditText;
import io.paperdb.Paper;
import makecodework.com.whoseroads.Common.Common;
import makecodework.com.whoseroads.Model.User;
import makecodework.com.whoseroads.R;

public class SignInActivity extends AppCompatActivity {

    private EditText enterPhone,enterPass;
    private TextView forgotPass;
    private Button signIn;
    private CheckBox chkRemember;

    private FirebaseDatabase database;
    private DatabaseReference table_user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        enterPhone = (MaterialEditText) findViewById(R.id.enter_phone);
        enterPass = (MaterialEditText) findViewById(R.id.enter_pass);
        forgotPass = findViewById(R.id.forgot_pass);
        signIn = (Button) findViewById(R.id.sign_in);

        chkRemember = findViewById(R.id.checkRemember);

        Paper.init(this);

        //FireBase
        database = FirebaseDatabase.getInstance();
        table_user = database.getReference("User");


        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showForgotPassDialog();
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(chkRemember.isChecked()){
                    Paper.book().write(Common.USER_KEY,enterPhone.getText().toString());
                    Paper.book().write(Common.USER_PSW,enterPass.getText().toString());
                }

                final ProgressDialog dialog = new ProgressDialog(SignInActivity.this);
                dialog.setMessage("Please waiting....");
                dialog.show();



                table_user.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        // Check if user not exist in database.
                        if(dataSnapshot.child(enterPhone.getText().toString()).exists()){

                            //Get User from Model/User.class
                        dialog.dismiss();

                        User user = dataSnapshot.child(enterPhone.getText().toString()).getValue(User.class);
                        user.setPhone(enterPhone.getText().toString());
                        if(user.getPassword().equals(enterPass.getText().toString())){

                            Intent homeIntent = new Intent(SignInActivity.this, Home.class);
                            Common.currentUser = user;
                            startActivity(homeIntent);
                            finish();

                            table_user.removeEventListener(this);

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

    private void showForgotPassDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Forgot your Password");
        builder.setMessage("Enter your Secure Code");

        LayoutInflater layoutInflater = this.getLayoutInflater();

        View forgot_view = layoutInflater.inflate(R.layout.forgot_pass_layout,null);
        builder.setView(forgot_view);

        final MaterialEditText enterPhone = forgot_view.findViewById(R.id.enter_phone);
        final MaterialEditText enterCode = forgot_view.findViewById(R.id.enter_secureCode);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.child(enterPhone.getText().toString()).getValue(User.class);

                        if (user.getSecureCode().equals(enterCode.getText().toString())) {
                            Toast.makeText(SignInActivity.this, "Your Password: " + user.getPassword(), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(SignInActivity.this, "Wrong Secure Code!", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }
}
