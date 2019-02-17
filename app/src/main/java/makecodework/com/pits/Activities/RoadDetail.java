package makecodework.com.pits.Activities;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
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
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.util.List;

import makecodework.com.pits.Model.Roads;
import makecodework.com.pits.R;

public class RoadDetail extends AppCompatActivity implements OnMapReadyCallback {

    private TextView roadName, roadStreet, defDescription;
    private ImageView imageRoad;
    private FloatingActionButton fab;

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
            if(ShareDialog.canShow(SharePhotoContent.class)){
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
        if(googleServiceAvilable()){

            setContentView(R.layout.activity_road_detail);
            
            initMap();
        }else{
            // No google map
        }


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        reference = database.getReference("Roads");

        roadName = findViewById(R.id.road_name);
        roadStreet = findViewById(R.id.road_street);
        defDescription = findViewById(R.id.defect_description);
        imageRoad = findViewById(R.id.img_road);
        fab = findViewById(R.id.floatingfb);

        collapsingToolbarLayout = findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);

        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);


        if(getIntent() != null){
            roadId = getIntent().getStringExtra("RoadId");
        }
        if(!roadId.isEmpty() && roadId !=null){
            loadDetail(roadId);
        }



    }

    private void initMap() {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.set_mapView);
        mapFragment.getMapAsync(this);
    }

    public boolean googleServiceAvilable(){
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvalibale = api.isGooglePlayServicesAvailable(this);
        if(isAvalibale == ConnectionResult.SUCCESS){
            return true;
        }else if(api.isUserResolvableError(isAvalibale)){
            Dialog dialog = api.getErrorDialog(this, isAvalibale, 0);
            dialog.show();

        }else{
            Toast.makeText(this, "Cannot connect to play services", Toast.LENGTH_SHORT).show();
        }
        return false;
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

                try {
                    getLocation(roads.getStreet(),roads.getDefectName());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getLocation(String street, String name) throws IOException {
        gmap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        Geocoder gc = new Geocoder(this);
        List<Address> list = gc.getFromLocationName(street,1);
        Address address = list.get(0);
        String locality = address.getLocality();
        double lat = address.getLatitude();
        double lng = address.getLongitude();
        goToLocationZoom(lat,lng,16);

        MarkerOptions options = new MarkerOptions()
                .title(street)
                .position(new LatLng(lat,lng))
                .snippet(name);
        gmap.addMarker(options);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        goToLocationZoom(49.4312234,31.9790181,16f);
    }

    private void goToLocation(double lat, double lng) {
        LatLng ll = new LatLng(lat,lng);
        CameraUpdate update = CameraUpdateFactory.newLatLng(ll);
        gmap.moveCamera(update);
    }

    private void goToLocationZoom(double lat, double lng, float zoom) {
        LatLng ll = new LatLng(lat,lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll,zoom);
        gmap.moveCamera(update);
    }
}
