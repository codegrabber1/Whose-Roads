package makecodework.com.pits.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.concurrent.TimeUnit;

import makecodework.com.pits.R;

public class AuthActivity extends AppCompatActivity {

    private MaterialEditText phoneNumber,getCode;
    private Button sendSMS,verfy;

    private FirebaseAuth mAuth;

    private String verificationId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        phoneNumber = findViewById(R.id.number);
        getCode = findViewById(R.id.get_code);
        sendSMS = findViewById(R.id.send_sms);
        verfy = findViewById(R.id.vrf);

        mAuth = FirebaseAuth.getInstance();

       verfy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog dialog = new ProgressDialog(AuthActivity.this);
                dialog.setMessage("Будь ласка, чекайте....");
                dialog.show();

                String code = getCode.getText().toString().trim();
                String number = phoneNumber.getText().toString().trim();
                if(code.isEmpty() || code.length() < 6){
                    dialog.dismiss();
                    getCode.setError("Уведіть код...");
                    getCode.requestFocus();
                    return;
                }
                dialog.dismiss();

                verifyCode(code);
            }
        });

        sendSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog dialog = new ProgressDialog(AuthActivity.this);
                dialog.setMessage("Будь ласка, чекайте....");
                dialog.show();

                String number = phoneNumber.getText().toString().trim();
                if(number.isEmpty()){
                    dialog.dismiss();
                    phoneNumber.setError("Номер необхідний");
                    phoneNumber.requestFocus();
                    return;
                }else{
                    dialog.dismiss();
                    sendVerificationCode(number);
                }

            }
        });
    }

    private void verifyCode(String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent i = new Intent(AuthActivity.this, SignUpActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);

                }else{
                    Toast.makeText(AuthActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendVerificationCode(String phoneNum){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNum,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallback


        );
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;

        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if(code != null){
                getCode.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(AuthActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    };
}
