package makecodework.com.whoseroads.Activities;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.facebook.CallbackManager;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.firebase.database.*;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import makecodework.com.whoseroads.Common.Common;
import makecodework.com.whoseroads.Model.Roads;
import makecodework.com.whoseroads.R;

public class RoadDetail extends AppCompatActivity implements OnMapReadyCallback {

    private TextView roadName, roadStreet, defDescription;
    private ImageView imageRoad;
    private FloatingActionButton fab;
    private MapView mapView;

    private GoogleMap gmap;
    CollapsingToolbarLayout collapsingToolbarLayout;

    private String roadId="";

    DatabaseReference reference;

    CallbackManager callbackManager;
    ShareDialog shareDialog;

    Roads road;


    // Get Bitmap from pictures.
    Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            SharePhoto photo = new SharePhoto.Builder().setBitmap(bitmap).build();
            if(shareDialog.canShow(SharePhotoContent.class)){
                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .build();
                shareDialog.show(content);
            }
        }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };


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
        fab = findViewById(R.id.floatingfb);

        collapsingToolbarLayout = findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);

        mapView = findViewById(R.id.set_mapView);

        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);


        if(getIntent() != null){
            roadId = getIntent().getStringExtra("RoadId");
        }
        if(!roadId.isEmpty() && roadId !=null){
            loadDetail(roadId);
        }

        initGoogleMap(savedInstanceState);

    }


    private void initGoogleMap(Bundle savedInstanceState) {
        Bundle bundle = null;
        if (savedInstanceState != null) {
            bundle = savedInstanceState.getBundle(Common.MAPVIEW_BUNDLE_KEY);

        }
        mapView = findViewById(R.id.set_mapView);
        mapView.onCreate(bundle);

        mapView.getMapAsync(this);


    }

    private void loadDetail(String roadId) {
        reference.child(roadId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final Roads roads = dataSnapshot.getValue(Roads.class);

                if(roads.getImage() != null){
                    Glide.with(getBaseContext()).load(roads.getImage()).into(imageRoad);
                }



                collapsingToolbarLayout.setTitle(roads.getDefectName());

                roadName.setText(roads.getDefectName());
                roadStreet.setText(roads.getStreet());

                defDescription.setText(roads.getDescription());
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Picasso.get()
                                .load(roads.getImage()).into(target);
                    }
                });

                loadMap(roadStreet);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadMap(TextView roadStreet) {
        final String map = roadStreet.getText().toString();
//        Toast.makeText(this, "Street: "+map, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;

//        Toast.makeText(this, "Map is ready", Toast.LENGTH_SHORT).show();
    }
}
