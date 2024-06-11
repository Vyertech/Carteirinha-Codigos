package com.example.aula2503;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;
import android.view.View;

public class MainActivity9 extends AppCompatActivity {
    boolean isNigthModeON;

    String cpfAluno;

    String cpfLogin;
    String cpflogin2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main9);

        //pegar o conteúdo da intent
        Intent intent = getIntent();
        cpfLogin = intent.getStringExtra("cpf");

        ImageButton imageButton = findViewById(R.id.btndark);

        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO){
            isNigthModeON = false;
            imageButton.setBackgroundResource(R.drawable.baseline_wb_sunny_24);
        }

        else if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            isNigthModeON = true;
            imageButton.setBackgroundResource(R.drawable.baseline_nightlight_24);
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNigthModeON){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    isNigthModeON = false;
                }
                else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    isNigthModeON = true;
                }
            }
        });


    }

    public void Qrcode(View view) {
        Intent intent = new Intent(this, MainActivity3.class);
        startActivity(intent);
    }

    public void foto(View view) {
        Intent intent = new Intent(this, MainActivity4.class);
        startActivity(intent);
    }

    public void avalie(View view) {
        Intent intent = new Intent(this, MainActivity5.class);
        startActivity(intent);
    }

    public void Log_out(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public void home(View view) {
        finish();
    }

    public void cadastro(View view) {
        Intent intent = new Intent(this, MainActivity6.class);
        intent.putExtra("cpf", cpfLogin);
        Log.i("APP", "valor cpf antes d activity " + cpfLogin);
        startActivity(intent);
    }

    public void notificacao(View view) {
        Intent intent = new Intent(this, MainActivity10.class);
        startActivity(intent);
    }

    public void carteirinha(View view) {
        Intent intent = new Intent(this, MainActivity8.class);
        intent.putExtra("cpf", cpfLogin);
        startActivity(intent);
    }
}