package makecodework.com.pits.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;
import dmax.dialog.SpotsDialog;
import io.paperdb.Paper;
import makecodework.com.pits.Common.Common;
import makecodework.com.pits.Interface.ItemClickListener;
import makecodework.com.pits.Model.Category;
import makecodework.com.pits.Model.User;
import makecodework.com.pits.R;
import makecodework.com.pits.viewHolder.MenuViewHolder;

import java.util.HashMap;
import java.util.Map;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "hash" ;
    private FirebaseDatabase database;
    private DatabaseReference category;

    private TextView menuUserName;
    private ImageView menuUserLogo;
    private RecyclerView menu_list;

    User user;
    FirebaseRecyclerAdapter<Category, MenuViewHolder> adapter;

    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Меню");
        setSupportActionBar(toolbar);

        // Init Firebase
        database = FirebaseDatabase.getInstance();
        category = database.getReference("Posts");

        Paper.init(this);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        menuUserName = headerView.findViewById(R.id.user_fullName);
        menuUserLogo = headerView.findViewById(R.id.user_img);
        if(menuUserName != null){
            menuUserName.setText(Common.currentUser.getName());
        }

        if(Common.currentUser.getImage() != null){
            Glide.with(getBaseContext()).load(R.drawable.logo).into(menuUserLogo);
        }

        //Load menu
        menu_list = findViewById(R.id.menu_list);
        menu_list.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        menu_list.setLayoutManager(layoutManager);
        
        loadDefectList();


        // Call Service
    }

    private void loadDefectList() {

        adapter = new FirebaseRecyclerAdapter<Category, MenuViewHolder>(
                Category.class,R.layout.menu_item,MenuViewHolder.class,category
        ) {
            @Override
            protected void populateViewHolder(MenuViewHolder viewHolder, Category model, int position) {

                viewHolder.objName.setText(model.getObjectName());

                viewHolder.objPerform.setText(model.getPerformer());

                viewHolder.objPrice.setText(model.getPrice());

                viewHolder.objRelease.setText(model.getDateRelease());

                final Category clickItem = model;

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        String postId = adapter.getRef(position).getKey();

                        // Get PostId and send to new Activity
                        Intent roadList = new Intent(Home.this, InfoDetail.class);

                        roadList.putExtra("PostId", adapter.getRef(position).getKey());

                        startActivity(roadList);
                    }
                });

            }
        };

        menu_list.setAdapter(adapter);
        }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent i = new Intent(Home.this,AddDefect.class);
            startActivity(i);
            // Handle the camera action
        } else if (id == R.id.nav_info) {


        } else if (id == R.id.nav_defect) {
            Intent roadList = new Intent(Home.this, RoadsList.class);
            startActivity(roadList);
            finish();

        } else if (id == R.id.nav_account) {
            
            changeUserData();

        }  else if (id == R.id.nav_signOut) {

            // Delete remember
            Paper.book().destroy();

            //LogOut
            Intent i = new Intent(Home.this, SignInActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void changeUserData() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Налаштування аккаунта");
        builder.setMessage("Оновіть пароль!");

        LayoutInflater inflate = LayoutInflater.from(this);
        View layout_data = inflate.inflate(R.layout.activity_account_setting,null);

        ImageView accCam;
        final MaterialEditText yourPass, yourNewPass,repNewPass;
        Button selectPhoto, uploadPhoto, changeBtn;

        yourPass = layout_data.findViewById(R.id.your_pass);
        yourNewPass = layout_data.findViewById(R.id.your_newpass);
        repNewPass = layout_data.findViewById(R.id.repeat_newpass);

        builder.setView(layout_data);

        builder.setPositiveButton("Змінити", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               final android.app.AlertDialog waitingDialog = new SpotsDialog.Builder().setContext(Home.this).build();
               waitingDialog.show();

               if(yourPass.getText().toString().equals(Common.currentUser.getPassword())){
                   if(yourNewPass.getText().toString().equals(repNewPass.getText().toString())){
                       Map<String,Object> dataUpdate = new HashMap<>();
                       dataUpdate.put("password", yourNewPass.getText().toString());


                       DatabaseReference user = FirebaseDatabase.getInstance().getReference("User");
                       user.child(Common.currentUser.getPhone()).updateChildren(dataUpdate)
                               .addOnCompleteListener(new OnCompleteListener<Void>() {
                                   @Override
                                   public void onComplete(@NonNull Task<Void> task) {
                                       waitingDialog.dismiss();
                                       Toast.makeText(Home.this, "Дані було оновлено!", Toast.LENGTH_SHORT).show();
                                   }
                               }).addOnFailureListener(new OnFailureListener() {
                           @Override
                           public void onFailure(@NonNull Exception e) {
                               Toast.makeText(Home.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                           }
                       });
                   }else{
                       Toast.makeText(Home.this, "Новий пароль не збігається", Toast.LENGTH_SHORT).show();
                   }
               }else{
                   waitingDialog.dismiss();
                   Toast.makeText(Home.this, "Неправильний пароль", Toast.LENGTH_SHORT).show();
               }


            }
        });
        builder.setNegativeButton("Відміна", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();

    }


}
