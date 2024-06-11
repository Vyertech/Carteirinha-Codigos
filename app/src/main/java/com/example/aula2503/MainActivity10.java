package com.example.aula2503;

import android.content.DialogInterface;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity10 extends AppCompatActivity  {

    boolean isNigthModeON;
    EditText remetente, titulo, mensagem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main10);

        remetente = (EditText) findViewById(R.id.remetente);
        mensagem = (EditText) findViewById(R.id.mensagem);
        titulo = (EditText) findViewById(R.id.titulo);

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

    public void buttao2(View view) {
        RequestQueue queue = Volley.newRequestQueue(MainActivity10.this);

        String url = "https://5530d240-68d8-4a83-8d79-5bb6ff0cc5f6-00-7qwjdr7ifbdi.riker.replit.dev/notifica";
        //String url= "https://carteirinha-digital.onrender.com/notifica";
        Log.i("Ta puxando", "bia");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override

            public void onResponse(String response) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity10.this);
                //alertDialog.setTitle("Resposta do servidor:");
                alertDialog.setMessage("Resposta: " + response);
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        remetente.setText("");
                        mensagem.setText("");
                        titulo.setText("");
                    }
                });
                AlertDialog alertDialog2 = alertDialog.create();
                alertDialog2.show();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity10.this, error.toString(), Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("mensagem", mensagem.getText().toString());
                params.put("titulo", titulo.getText().toString());
                params.put("remetente", remetente.getText().toString());
                return params;
            }
        };
        queue.add(stringRequest);
    }
}
