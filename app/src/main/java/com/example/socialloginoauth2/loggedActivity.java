package com.example.socialloginoauth2;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class loggedActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged);

        // get intent:
        Intent intent = getIntent();
        ArrayList<String> accountInfo = intent.getStringArrayListExtra("accountInfo");

        // get user-name:
        String name = accountInfo.get(0);
        TextView user = findViewById(R.id.username);
        user.setText("user: " + name);

        // get user first name:
        String givenName = accountInfo.get(1);
        TextView userName = findViewById(R.id.userGivenName);
        userName.setText("first name: " + givenName);

        // get user family name:
        String familyName = accountInfo.get(2);
        TextView userFamily = findViewById(R.id.userFamilyName);
        userFamily.setText("family name: " + familyName);

        // get user email:
        String email = accountInfo.get(3);
        TextView userEmail = findViewById(R.id.userEmail);
        userEmail.setText("email: " + email);

        // get user id:
        String id = accountInfo.get(4);
        TextView userId = findViewById(R.id.userId);
        userId.setText("Id: " + id);

        // get user photo:
        String photo = accountInfo.get(5);
        ImageView userPhoto = findViewById(R.id.userPhoto);
        Picasso.with(this).load(photo).into(userPhoto); // use picasso library for picture

        // add back to main-activity button:
        Button backButton = findViewById(R.id.button_back);
        backButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent backActivity = new Intent(this, MainActivity.class);
        startActivity(backActivity);
    }
}
