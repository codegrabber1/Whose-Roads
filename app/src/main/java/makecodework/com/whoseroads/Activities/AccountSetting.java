package makecodework.com.whoseroads.Activities;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import makecodework.com.whoseroads.Model.User;
import makecodework.com.whoseroads.R;

public class AccountSetting extends AppCompatActivity {

    CoordinatorLayout accountSetting;

    private FirebaseDatabase database;
    private DatabaseReference reference;

    private FirebaseStorage storage;

//    private ImageView accCam;
//    private MaterialEditText yourName, yourPass,yourCode;
//    private Button selectPhoto, uploadPhoto, changeBtn;

    User user;
    private String key="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);

//        accountSetting = findViewById(R.id.accountSetting);

//        accCam = findViewById(R.id.account_camera);
//        yourName = findViewById(R.id.your_name);
//        yourPass = findViewById(R.id.your_pass);
//        yourCode = findViewById(R.id.your_scode);

//        selectPhoto = findViewById(R.id.choose_photo);
//        //uploadPhoto = findViewById(R.id.up_photo);
//        changeBtn = findViewById(R.id.change_my_data);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("User");


//        yourName.setText(Common.currentUser.getName());
//        yourCode.setText(Common.currentUser.getSecureCode());
//        yourPass.setText(Common.currentUser.getPassword());
//        storage = FirebaseStorage.getInstance();

        //Toast.makeText(this, "Phone: "+Common.currentUser.getPhone(), Toast.LENGTH_LONG).show();

        //getData();




    }
}
