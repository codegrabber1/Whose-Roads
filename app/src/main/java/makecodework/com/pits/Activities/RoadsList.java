package makecodework.com.pits.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import makecodework.com.pits.Interface.ItemClickListener;
import makecodework.com.pits.Model.Roads;
import makecodework.com.pits.R;
import makecodework.com.pits.viewHolder.RoadsViewHolder;

public class RoadsList extends AppCompatActivity {

    private RecyclerView roadList;
    private DatabaseReference dataRef;

    private Toolbar listToolbar;

    FirebaseRecyclerAdapter<Roads, RoadsViewHolder> recAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roads_list);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        dataRef = database.getReference("Roads");


        listToolbar = findViewById(R.id.listToolbar);
        setSupportActionBar(listToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        listToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RoadsList.this, Home.class);
                startActivity(i);
            }
        });


        roadList = findViewById(R.id.road_list);
        roadList.setHasFixedSize(true);
        roadList.setLayoutManager(new LinearLayoutManager(this));

        loadList();

    }


    private void loadList() {
        recAdapter = new FirebaseRecyclerAdapter<Roads, RoadsViewHolder>(
                Roads.class,
                R.layout.road_item,
                RoadsViewHolder.class,
                dataRef
        ) {
            @Override
            protected void populateViewHolder(RoadsViewHolder viewHolder, Roads model, int position) {
                viewHolder.defName.setText(model.getDefectName());
                viewHolder.defStreet.setText(model.getStreet());
                Glide.with(getBaseContext()).load(model.getImage()).into(viewHolder.defImage);
                final Roads local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent roadDetail = new Intent(RoadsList.this, RoadDetail.class);

                        roadDetail.putExtra("RoadId",recAdapter.getRef(position).getKey());

                        startActivity(roadDetail);
                    }
                });
            }
        };
        roadList.setAdapter(recAdapter);
    }
}
