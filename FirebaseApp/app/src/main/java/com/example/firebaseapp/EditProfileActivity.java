package com.example.firebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class EditProfileActivity extends AppCompatActivity {

    private EditText editname,editdob,editphone,editaddress,editcity,editstate,editpincode;
    private Button edit;
    FirebaseAuth mAuth;
    DatabaseReference myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        edit = (Button) findViewById(R.id.submit);
        editname = (EditText) findViewById(R.id.editname);
        editdob = (EditText) findViewById(R.id.editdob);
        editphone = (EditText) findViewById(R.id.editphone);
        editaddress = (EditText) findViewById(R.id.editaddress);
        editcity = (EditText) findViewById(R.id.editcity);
        editstate = (EditText) findViewById(R.id.editstate);
        editpincode = (EditText) findViewById(R.id.editzip);

        mAuth = FirebaseAuth.getInstance();
        myDatabase = FirebaseDatabase.getInstance().getReference();


        myDatabase.addValueEventListener(new ValueEventListener() {
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

                editname.setText(Name);
                editdob.setText(Dob);
                editphone.setText(Phone);
                editaddress.setText(Address);
                editcity.setText(City);
                editstate.setText(State);
                editpincode.setText(Pincode);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(EditProfileActivity.this);
                progressDialog.setMessage("Storing Data...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();

                String Name = editname.getText().toString();
                String Dob = editdob.getText().toString();
                String Phone = editphone.getText().toString();
                String Address = editaddress.getText().toString();
                String City = editcity.getText().toString();
                String State = editstate.getText().toString();
                String Pincode = editpincode.getText().toString();

                user User = new user(Name,Dob,Phone,Address,City,State,Pincode);

                String userID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

                myDatabase.child("USERS").child(userID).setValue(User);
                progressDialog.dismiss();
                Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                startActivity(intent);

            }
        });

    }
}
