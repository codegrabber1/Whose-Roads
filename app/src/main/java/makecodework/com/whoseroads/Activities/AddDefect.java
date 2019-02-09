package makecodework.com.whoseroads.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import makecodework.com.whoseroads.Common.Common;
import makecodework.com.whoseroads.Model.Roads;
import makecodework.com.whoseroads.Model.User;
import makecodework.com.whoseroads.R;

import java.util.UUID;

public class AddDefect extends AppCompatActivity {

    private MaterialEditText defectRoad, defectStreet, defDesc, setDefect;
    private Button selectBtn, uploadBtn, addNewBtn;
    private ImageView photoCam;

    private FirebaseDatabase database;
    private DatabaseReference reference;

    private FirebaseStorage storage;
    private StorageReference storageReference;

    Uri postImageUri;

    Roads newRoad;

    private String author;

    CoordinatorLayout addRoadLayout;

    private Toolbar rtoobar;

    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_defect);

        addRoadLayout = findViewById(R.id.addRoadLayout);

        rtoobar = findViewById(R.id.add_road_toolbar);
        rtoobar.setTitle("Add new defect");
        setSupportActionBar(rtoobar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        rtoobar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddDefect.this, Home.class);
                startActivity(i);
            }
        });




        defectRoad = findViewById(R.id.defect_road);
        defectStreet = findViewById(R.id.defect_street);
        setDefect = findViewById(R.id.defect);
        defDesc = findViewById(R.id.defect_description);


//        selectBtn = findViewById(R.id.select_photo);
        uploadBtn = findViewById(R.id.upload_photo);
        addNewBtn = findViewById(R.id.add_new_road);

        photoCam = findViewById(R.id.photo_camera);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Roads");

        storage = FirebaseStorage.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();




        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImg();
            }
        });


        photoCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setMinCropResultSize(512,512)
                        .setAspectRatio(1,1)
                        .start(AddDefect.this);

            }

        });

        addNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String defectName = defectRoad.getText().toString();
                final String street = defectStreet.getText().toString();

                final String desc = defDesc.getText().toString();
                final String author = Common.currentUser.getName();



                if( !TextUtils.isEmpty(defectName) &&
                    !TextUtils.isEmpty(street) &&
                    !TextUtils.isEmpty(desc) &&
                    !TextUtils.isEmpty(author) && postImageUri != null){

                    reference.push().setValue(newRoad);
                    Snackbar.make(addRoadLayout,"New post "+newRoad.getDefectName()+" was added", Snackbar.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(AddDefect.this, "Object is null", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void uploadImg() {
        if(postImageUri != null ){
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Uploading...");
            progressDialog.show();

            String randomName = UUID.randomUUID().toString();
            final StorageReference filePath = storageReference.child("image/"+randomName);
            filePath.putFile(postImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Toast.makeText(AddDefect.this, "Uploaded !!!", Toast.LENGTH_SHORT).show();
                    filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            newRoad = new Roads();
                            newRoad.setAuthor(Common.currentUser.getPhone());
                            newRoad.setDefectName(defectRoad.getText().toString());
                            newRoad.setDescription(defDesc.getText().toString());
                            newRoad.setStreet(defectStreet.getText().toString());
                            newRoad.setImage(uri.toString());



                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(AddDefect.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded "+progress+"%");

                }
            });

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                postImageUri = result.getUri();
                photoCam.setImageURI(postImageUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}