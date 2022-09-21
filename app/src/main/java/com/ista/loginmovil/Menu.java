package com.ista.loginmovil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Menu extends AppCompatActivity {

    Spinner spinermenu=findViewById(R.id.spinner_menu);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.Opciones, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        spinermenu.setAdapter(adapter);
    }
}