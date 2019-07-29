package com.example.socialloginoauth2;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class loggedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged);

        // get intent:
        Intent intent = getIntent();

        // get user-name:
        String name = intent.getStringExtra("userName");
        TextView user = findViewById(R.id.username);
        user.setText("user: " + name);

        // get user first name:
        String givenName = intent.getStringExtra("userGivenName");
        TextView userName = findViewById(R.id.userGivenName);
        userName.setText("first name: " + givenName);

        // get user family name:
        String familyName = intent.getStringExtra("userFamilyName");
        TextView userFamily = findViewById(R.id.userFamilyName);
        userFamily.setText("family name: " + familyName);

        // get user email:
        String email = intent.getStringExtra("userEmail");
        TextView userEmail = findViewById(R.id.userEmail);
        userEmail.setText("email: " + email);

        // get user id:
        String id = intent.getStringExtra("userId");
        TextView userId = findViewById(R.id.userId);
        userId.setText("Id: " + id);

        // get user photo:
        String photo = intent.getStringExtra("userPhoto");
        ImageView userPhoto = findViewById(R.id.userPhoto);
        Picasso.with(this).load(photo).into(userPhoto); // use picasso library for picture

    }
}
