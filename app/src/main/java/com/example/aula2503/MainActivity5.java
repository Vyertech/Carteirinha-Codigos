package com.example.aula2503;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity5 extends AppCompatActivity {
    float x;

    boolean isNigthModeON;

    RatingBar avaliar;
    RatingBar avaliacao;

    EditText sugestao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        //INICIO DO CODIGO PARA ATIVAR O MODO NOTURNO E CLARO
        ImageButton imageButton = findViewById(R.id.btndark);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
            isNigthModeON = false;
            imageButton.setBackgroundResource(R.drawable.baseline_wb_sunny_24);
        } else if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            isNigthModeON = true;
            imageButton.setBackgroundResource(R.drawable.baseline_nightlight_24);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNigthModeON) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    isNigthModeON = false;
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    isNigthModeON = true;
                }
            }
        });
        //FIM DO CODIGO PARA ATIVAR O MODO NOTURNO E CLARO


        //puxa o id do xml para o java
        avaliacao = (RatingBar) findViewById(R.id.avaliacao);
        sugestao = (EditText) findViewById(R.id.sugestao);

    }

    //é o botão que envia os dados para o nodejs e depois para o phpMyAdmin
    public void buttao(View view) {
        x = avaliacao.getRating();
        Toast.makeText(this, "Estrelas: " + x, Toast.LENGTH_LONG).show();
        RequestQueue queue = Volley.newRequestQueue(MainActivity5.this);
        String url = "https://5530d240-68d8-4a83-8d79-5bb6ff0cc5f6-00-7qwjdr7ifbdi.riker.replit.dev/avaliacoes"; //faz a requisição para o servidor
        //String url= "https://carteirinha-digital.onrender.com/avaliacoes/";
        //abaixo ele puxa o valor para o nodejs
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity5.this);
                //alertDialog.setTitle("Resposta do servidor:");
                alertDialog.setMessage("Resposta: " + response);
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sugestao.setText("");
                        avaliacao.setRating(x);
                    }
                });
                AlertDialog alertDialog2 = alertDialog.create();
                alertDialog2.show();
                //mostra a mensagem na tela
            }
        },
                //imprime a mensagem na tela se tiver erro no código
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity5.this, "Erro!", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }) {
            //retorna os valores que o usuario digitou e salva no banco de dados
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("sugestao", sugestao.getText().toString());
                params.put("avaliacao", avaliacao.getRating() + "");
                return params;
            }
        };
        queue.add(stringRequest);
    }


    //é o icone da barra inferior para o usuario deslogar e ir para a pagina de login
    public void Log_out(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    //é o icone da barra inferior para voltar para a pagina inicial
    public void home(View view) {
        finish();
    }
}