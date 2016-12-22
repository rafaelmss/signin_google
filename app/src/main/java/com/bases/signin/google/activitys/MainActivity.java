package com.bases.signin.google.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bases.signin.google.R;
import com.bases.signin.google.tools.CircleTransform;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {

    private static final String TAG = "MainActivity";

    private GoogleApiClient mGoogleApiClient;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setBackgroundColor(android.R.color.transparent);

        Intent it = getIntent();
        Bundle extras = it.getExtras();
        if (extras != null){
            TextView holder;

            String extra = extras.getString("name");
            if (extra != null) {
                holder = (TextView) findViewById(R.id.name_text);
                holder.setText(extra);
            }

            extra = extras.getString("family");
            if (extra != null) {
                holder = (TextView) findViewById(R.id.family_text);
                holder.setText(extra);
            }

            extra = extras.getString("mail");
            if (extra != null) {
                holder = (TextView) findViewById(R.id.mail_text);
                holder.setText(extra);
            }

            extra = extras.getString("id");
            if (extra != null) {
                holder = (TextView) findViewById(R.id.token_text);
                holder.setText(extra);
            }

            extra = extras.getString("photo");
            if (extra != null){
                ImageView photo= (ImageView) findViewById(R.id.photo_image);
                Picasso.with(getApplicationContext())
                        .load(extra)
                        .transform(new CircleTransform())
                        .resize(500,500)
                        .centerCrop()
                        .into(photo);
            }
        }


        // Button listener
        findViewById(R.id.sign_out_button).setOnClickListener(this);

        // [START configure_signin]
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // [END configure_signin]

        // [START build_client]
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        // [END build_client]


    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        Intent it = new Intent(getApplicationContext(),SignInActivity.class);
                        startActivity(it);
                        finish();
                        // [END_EXCLUDE]
                    }
                });
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_out_button:
                signOut();
                break;
        }
    }

}
