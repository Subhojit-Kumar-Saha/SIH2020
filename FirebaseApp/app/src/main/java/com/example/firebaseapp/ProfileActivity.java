package com.example.firebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private ImageView profilePic;
    private TextView name,dob,phone,address,city,state,pincode;

    private FirebaseAuth mAuth;
    private DatabaseReference myDB;
    private RelativeLayout main_layout;
    private Button edit,back;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profilePic = (ImageView) findViewById(R.id.dp);
        name = (TextView) findViewById(R.id.name);
        dob = (TextView) findViewById(R.id.dob);
        phone = (TextView) findViewById(R.id.phone);
        address = (TextView) findViewById(R.id.address);
        city = (TextView) findViewById(R.id.city);
        state = (TextView) findViewById(R.id.state);
        pincode = (TextView) findViewById(R.id.zip);

        edit = (Button) findViewById(R.id.edit);
        back = (Button) findViewById(R.id.back);
        main_layout = (RelativeLayout) findViewById(R.id.mainlayout);



        mAuth = FirebaseAuth.getInstance();

        myDB = FirebaseDatabase.getInstance().getReference();

        myDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String userID = mAuth.getCurrentUser().getUid();
                String Name = dataSnapshot.child("USERS").child(userID).child("name").getValue().toString();
                String Dob = dataSnapshot.child("USERS").child(userID).child("dob").getValue().toString();
                String Phone = dataSnapshot.child("USERS").child(userID).child("phone").getValue().toString();
                String Address = dataSnapshot.child("USERS").child(userID).child("address").getValue().toString();
                String City = dataSnapshot.child("USERS").child(userID).child("city").getValue().toString();
                String State = dataSnapshot.child("USERS").child(userID).child("state").getValue().toString();
                String Pincode = dataSnapshot.child("USERS").child(userID).child("pincode").getValue().toString();

                name.setText(Name);
                dob.setText(Dob);
                phone.setText(Phone);
                address.setText(Address);
                city.setText(City);
                state.setText(State);
                pincode.setText(Pincode);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this,EditProfileActivity.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });



    }
}
