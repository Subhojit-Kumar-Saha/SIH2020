package com.example.firebaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class UserDetailActivity extends AppCompatActivity {

    private EditText name, phone, dob, address, city, state, pincode;
    private Button submit;
    private FirebaseAuth mAuth;
    DatabaseReference myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        name = (EditText) findViewById(R.id.name);
        phone = (EditText) findViewById(R.id.phone);
        dob = (EditText) findViewById(R.id.dob);
        address = (EditText) findViewById(R.id.address);
        city = (EditText) findViewById(R.id.city);
        state = (EditText) findViewById(R.id.state);
        pincode = (EditText) findViewById(R.id.zip);
        submit = (Button) findViewById(R.id.submit);

        mAuth = FirebaseAuth.getInstance();
        myDatabase = FirebaseDatabase.getInstance().getReference();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(UserDetailActivity.this);
                progressDialog.setMessage("Storing Data...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();

                String Name = name.getText().toString();
                String Dob = dob.getText().toString();
                String Phone = phone.getText().toString();
                String Address = address.getText().toString();
                String City = city.getText().toString();
                String State = state.getText().toString();
                String Pincode = pincode.getText().toString();

                user User = new user(Name,Dob,Phone,Address,City,State,Pincode);

                String userID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

                myDatabase.child("USERS").child(userID).setValue(User);
                progressDialog.dismiss();
                Intent intent = new Intent(UserDetailActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

    }
}
