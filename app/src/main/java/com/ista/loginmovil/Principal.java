package com.ista.loginmovil;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class Principal extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener {

    public  GoogleApiClient apiClient;
    public static final String TAG = Principal.class.getSimpleName();
    TextView email,nombre;
    ImageView photo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        nombre=(TextView) findViewById(R.id.tv_nombre);
        email=(TextView) findViewById(R.id.tv_email);
        photo=(ImageView) findViewById(R.id.photo);

        findViewById(R.id.salir).setOnClickListener(this);

        nombre.setText(getIntent().getStringExtra("nombre"));
        email.setText(getIntent().getStringExtra("email"));
        String URL=getIntent().getStringExtra("photo");

        ImageRequest imageRequest=new ImageRequest(URL, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                RoundedBitmapDrawable rounded= RoundedBitmapDrawableFactory
                        .create(getResources(),bitmap);
                rounded.setCornerRadius(bitmap.getWidth());
                photo.setImageDrawable(rounded);
            }
        }, 0, 0, null, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.w(TAG,"onErrorResponse: "+error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(imageRequest,"LoadingImage");

        GoogleSignInOptions gso= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        apiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.salir:
                signOut();
                break;
        }
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(apiClient)
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        Log.i(TAG,"onResult: Disconnect with google");
                        Intent intent=new Intent(Principal.this,MainActivity.class);
                        finish();
                        startActivity(intent);
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}