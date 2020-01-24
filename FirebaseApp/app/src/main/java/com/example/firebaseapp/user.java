package com.example.firebaseapp;

public class user {
    public String name,dob,phone,address,city,state,pincode;

    public user(){}
    public user(String name, String dob, String phone, String address, String city, String state, String pincode){
        this.name = name;
        this.dob = dob;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
    }
}
