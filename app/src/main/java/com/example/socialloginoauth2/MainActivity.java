package com.example.socialloginoauth2;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "signin google fail-code";
    // add logout button and textview for account info when logged:
    Button logoutButton, showInfoButton;
    TextView accountInfo;
    // arraylist for account-info:
    ArrayList<String> accountInfos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // OPTIONAL: Set the dimensions of the sign-in button.
        // SignInButton signInButton = findViewById(R.id.sign_in_button);
        // signInButton.setSize(SignInButton.SIZE_STANDARD);

        // register your button's OnClickListener to sign in the user when clicked:
        findViewById(R.id.sign_in_button).setOnClickListener(this);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // add textview to show user account if logged (invisible before login):
        accountInfo = findViewById(R.id.account_info);
        accountInfo.setVisibility(View.INVISIBLE);

        // add logout button (invisible before login):
        logoutButton = findViewById(R.id.button_back);
        logoutButton.setVisibility(View.INVISIBLE);
        logoutButton.setOnClickListener(this);

        // add more info button (switch activity):
        showInfoButton = findViewById(R.id.more_info_button);
        showInfoButton.setVisibility(View.INVISIBLE);
        showInfoButton.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }

    // update activity after login (by result)
    private void updateUI(GoogleSignInAccount account) {
        // get profile info if account is not null:
        if (account!=null){
            // get default info from account:
            String personName = account.getDisplayName();
            String personGivenName = account.getGivenName();
            String personFamilyName = account.getFamilyName();
            String personEmail = account.getEmail();
            String personId = account.getId();
            Uri personPhoto = account.getPhotoUrl();
            // add arraylist for account info:
            accountInfos.add(personName);
            accountInfos.add(personGivenName);
            accountInfos.add(personFamilyName);
            accountInfos.add(personEmail);
            accountInfos.add(personId);
            accountInfos.add(personPhoto.toString());

            // set visibility and content for user info and logout button:
            findViewById(R.id.sign_in_button).setVisibility(View.INVISIBLE);
            logoutButton.setVisibility(View.VISIBLE);
            accountInfo.setVisibility(View.VISIBLE);
            accountInfo.setText(personName);
            showInfoButton.setVisibility(View.VISIBLE);

//            // set intent switch activity:
//            Intent intent = new Intent(MainActivity.this, loggedActivity.class);
//            // add intent extra with account infos:
//            intent.putExtra("userName", personName);
//            intent.putExtra("userGivenName", personGivenName);
//            intent.putExtra("userFamilyName", personFamilyName);
//            intent.putExtra("userEmail", personEmail);
//            intent.putExtra("userId", personId);
//            intent.putExtra("userPhoto", personPhoto.toString());
//            // start activity:
//            startActivity(intent);
        }
        else{
            // set visibility for activity items:
            accountInfo.setText("no info");
            accountInfo.setVisibility(View.INVISIBLE);
            logoutButton.setVisibility(View.INVISIBLE);
            showInfoButton.setVisibility(View.INVISIBLE);
            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);

        }
    }

    // on click listener buttons.
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            case R.id.button_back:
                signOut();
                break;
            case R.id.more_info_button:
                switchActivity();
        }
    }

    // signin function: handle sign-in button taps
    // by creating a sign-in intent with the getSignInIntent method,
    // and starting the intent with startActivityForResult.
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }

    private void switchActivity(){
        // set intent switch activity:
        Intent intent = new Intent(MainActivity.this, loggedActivity.class);
        // add intent extra with account infos:
        intent.putExtra("accountInfo", accountInfos);
        // start activity:
        startActivity(intent);

    }
    // get a GoogleSignInAccount object for the user in the activity's onActivityResult method.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    // try-catch signin result:
    // The GoogleSignInAccount object contains information about the signed-in user,
    // such as the user's name.
    // (You can also get the user's email address with getEmail,
    // the user's Google ID (for client-side use) with getId,
    // and an ID token for the user with getIdToken.
    // If you need to pass the currently signed-in user to a backend server,
    // send the ID token to your backend server and validate the token on the server.)
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }
}
