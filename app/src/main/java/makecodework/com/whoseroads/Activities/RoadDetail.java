package makecodework.com.whoseroads.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.google.firebase.database.*;
import makecodework.com.whoseroads.Model.Roads;
import makecodework.com.whoseroads.R;

public class RoadDetail extends AppCompatActivity {

    private TextView roadName, roadStreet, defDescription;
    private ImageView imageRoad;
    CollapsingToolbarLayout collapsingToolbarLayout;

    private String roadId="";

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_road_detail);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        reference = database.getReference("Roads");

        roadName = findViewById(R.id.road_name);
        roadStreet = findViewById(R.id.road_street);
        defDescription = findViewById(R.id.defect_description);
        imageRoad = findViewById(R.id.img_road);

        collapsingToolbarLayout = findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);

        if(getIntent() != null){
            roadId = getIntent().getStringExtra("RoadId");
        }
        if(!roadId.isEmpty() && roadId !=null){
            loadDetail(roadId);
        }

    }

    private void loadDetail(String roadId) {
        reference.child(roadId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Roads roads = dataSnapshot.getValue(Roads.class);

                Glide.with(getBaseContext()).load(roads.getImage()).into(imageRoad);

                collapsingToolbarLayout.setTitle(roads.getDefectName());

                roadName.setText(roads.getDefectName());
                roadStreet.setText(roads.getStreet());

                defDescription.setText(roads.getDescription());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
