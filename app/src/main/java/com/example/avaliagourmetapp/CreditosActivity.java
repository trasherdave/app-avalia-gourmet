package com.example.avaliagourmetapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CreditosActivity extends AppCompatActivity {
    private Button buttonInicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creditos);

        buttonInicio = findViewById(R.id.buttonInicio);

    }//onCreate


    public void inicio(View view) {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }//inicio

}