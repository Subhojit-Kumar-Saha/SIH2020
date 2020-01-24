package com.example.firebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private LottieAnimationView logout,submit,next;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth = FirebaseAuth.getInstance();
        logout = (LottieAnimationView) findViewById(R.id.logout);

        submit = (LottieAnimationView) findViewById(R.id.submit);
        next = (LottieAnimationView) findViewById(R.id.animation_view1);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        navigationView = (NavigationView) findViewById(R.id.navview);

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        mToggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.open, R.string.close);
        mToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        final String userID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        /*
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String myName = dataSnapshot.child("USERS").child(userID).child("name").getValue().toString();
                String myAge = dataSnapshot.child("USERS").child(userID).child("dob").getValue().toString();
                String myEmail = dataSnapshot.child("USERS").child(userID).child("city").getValue().toString();
                String myPhone = dataSnapshot.child("USERS").child(userID).child("phone").getValue().toString();
                name.setText(myName);
                age.setText(myAge);
                email.setText(myEmail);
                phone.setText(myPhone);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        */



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(HomeActivity.this, MapsActivity.class);
                startActivity(intent);



            }
        });
        navigationView.setNavigationItemSelectedListener(this);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.setting, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();
        if(id == R.id.profile){
            Intent intent = new Intent(HomeActivity.this,ProfileActivity.class);
            startActivity(intent);
        }
        if(id == R.id.about){
            Toast.makeText(this, "Not yet ready", Toast.LENGTH_SHORT).show();
        }
        if(id == R.id.logout){
            mAuth.signOut();
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
        }
        return false;
    }
}
