package makecodework.com.whoseroads.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.google.firebase.database.*;
import makecodework.com.whoseroads.Model.Category;
import makecodework.com.whoseroads.R;

public class InfoDetail extends AppCompatActivity {

    private TextView ObName,ObPerformer,ObDescription,ObPrice,ObRelease;

    private String infoId="";

    DatabaseReference infoRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_detail);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        infoRef = database.getReference("Posts");

        ObName = findViewById(R.id.objname);
        ObPerformer = findViewById(R.id.objectperformer);
        ObPrice = findViewById(R.id.objprice);
        ObRelease = findViewById(R.id.objectrelease);
        ObDescription = findViewById(R.id.objectdescription);

        if(getIntent() != null){
            infoId = getIntent().getStringExtra("PostId");
        }

        if(!infoId.isEmpty() && infoId != null){
            loadInfo(infoId);
        }
    }

    private void loadInfo(String infoId) {
        infoRef.child(infoId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Category category = dataSnapshot.getValue(Category.class);

                ObName.setText(category.getObjectName());
                ObPerformer.setText(category.getPerformer());
                ObPrice.setText(category.getPrice());
                ObRelease.setText(category.getDataRelease());
                ObDescription.setText(category.getDescription());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
