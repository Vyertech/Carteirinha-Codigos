package com.example.aula2503;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity7 extends AppCompatActivity {
    boolean isNigthModeON;
    String cpfLogin;
    ListView listView;
    List<String> mensagens;

    private ArrayList<ContactsContract.CommonDataKinds.Phone> data;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);

        mensagens = new ArrayList();

        Intent intent = getIntent();
        cpfLogin = intent.getStringExtra("cpf");

        ImageButton imageButton = findViewById(R.id.btndark);
        listView = findViewById(R.id.lista);


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
        carregarDados();
    }

    public void home(View view) {
        finish();
    }


    public void Log_out(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void carregarDados() {
        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        Set<String> removedItems = prefs.getStringSet("RemovedItems", new HashSet<String>());

        String url = "https://5530d240-68d8-4a83-8d79-5bb6ff0cc5f6-00-7qwjdr7ifbdi.riker.replit.dev/notis";
        Log.i("APP", "CHAMANDO METODO");

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Log.i("APP", response.toString());

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                Log.i("APP", jsonObject.toString());
                                String Nome = jsonObject.getString("Nome_remetente");
                                String Mensagem = jsonObject.getString("Mensagem");
                                String Titulo = jsonObject.getString("Título");

                                mensagens.add(Nome + " - " + Titulo + " - " + Mensagem ); //mensagem e nome que vai chegar na aba de notificações e como vai chegar.

                            }

                            Push();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity7.this, "Erro ao processar os dados do servidor2", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity7.this, "Erro ao obter os dados do servidor", Toast.LENGTH_SHORT).show();
                    }
                });


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }


    void Push() {
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mensagens);
        listView.setAdapter(adapter); // ele so da push no metodo quando termina de fazer a request, se não, não funcionaria.
    }

    public void lixo(View view) {
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mensagens);
        listView.setAdapter(adapter);
        ImageButton removeSelected = findViewById(R.id.lixoo);
        removeSelected.setOnClickListener(v -> {
            if (!mensagens.isEmpty()) { // Verifica se há algum item selecionado
                int firstItemIndex = 0; // Índice do primeiro item na lista
                mensagens.remove(firstItemIndex); // Remove o primeiro item da lista
                ((ArrayAdapter) listView.getAdapter()).notifyDataSetChanged(); // Notifica o adaptador sobre a mudança
            }
        });

    }

}
