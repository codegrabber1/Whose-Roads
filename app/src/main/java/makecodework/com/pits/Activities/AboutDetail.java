package makecodework.com.pits.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import makecodework.com.pits.Model.About;
import makecodework.com.pits.R;

public class AboutDetail extends AppCompatActivity {

    private TextView titleAbout, fullText;

    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toolbar toolbar = findViewById(R.id.about_toolbar);
        toolbar.setTitle("Про додаток");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AboutDetail.this, Home.class);
                startActivity(i);
            }
        });
        titleAbout = findViewById(R.id.title_about);
        fullText = findViewById(R.id.full_text);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("About");

        loadText();
    }

    private void loadText() {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                About about = dataSnapshot.getValue(About.class);
                if(about.getAboutTitle() != null ){
                    titleAbout.setText(about.getAboutTitle());
                }

                fullText.setText(about.getFullText());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
