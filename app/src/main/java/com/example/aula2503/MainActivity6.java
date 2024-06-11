package com.example.aula2503;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity6 extends AppCompatActivity {
    boolean isNigthModeOn;
    String cpfLogin;
    TextView nome;
    TextView textViewNOME;
    TextView data;
    TextView curso;
    TextView turma;
    TextView telefone;
    TextView email;
    TextView endereco;
    TextView matricula;
    TextView cpf;
    TextView rg;
    ImageView foto;

    ArrayAdapter<String> adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        //recebe o id do aluno da outra activity
        Intent intent = getIntent();
        cpfLogin = intent.getStringExtra("cpf");

        nome = findViewById(R.id.nome);
        data = findViewById(R.id.data);
        cpf = findViewById(R.id.cpf);
        rg = findViewById(R.id.rg);
        matricula = findViewById(R.id.matricula);
        endereco = findViewById(R.id.endereco);
        email = findViewById(R.id.email);
        telefone = findViewById(R.id.telefone);
        turma = findViewById(R.id.turma);
        curso = findViewById(R.id.curso);
        foto = findViewById(R.id.foto);
        //textViewNOME = findViewById(R.id.textViewNOME);

        ImageButton imageButton = findViewById(R.id.btndark);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
            isNigthModeOn = false;
            imageButton.setBackgroundResource(R.drawable.baseline_wb_sunny_24);
        } else if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            isNigthModeOn = true;
            imageButton.setBackgroundResource(R.drawable.baseline_nightlight_24);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNigthModeOn) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    isNigthModeOn = false;
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    isNigthModeOn = true;
                }
            }
        });

        carregarDados();
        Imagemcarteirinha();
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
        String url = "https://5530d240-68d8-4a83-8d79-5bb6ff0cc5f6-00-7qwjdr7ifbdi.riker.replit.dev/ficha/"+cpfLogin;
        //String url= "https://carteirinha-digital.onrender.com/ficha/" + cpfLogin;
        Log.i("APP", "CHAMANDO METODO");

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Log.i("APP", "CHAMANDO REQUEST");

                            //  for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObject = response.getJSONObject(0);
                            Log.i("APP", jsonObject.toString());
                            String Nome = jsonObject.getString("Nome");
                            String Cpf = jsonObject.getString("Login");
                            String ano = jsonObject.getString("Data_nascimento");
                            String Turma = jsonObject.getString("Turma");
                            String Curso = jsonObject.getString("CursoNome");
                            String Telefone = jsonObject.getString("Telefone");
                            String Email = jsonObject.getString("Email");
                            String Endereço = jsonObject.getString("Endereço");
                            String Matricula = jsonObject.getString("idmatricula");
                            String Rg = jsonObject.getString("Rg");
                            String Aluno = "Nome: " + Nome + ", Login: " + Cpf + ", Ano: " + ano + "Turma" + Turma + "Curso" + Curso + "Telefone" + Telefone + "Email" + Email + "Endereço" + Endereço + "Rg" + Rg + "idmatricula" + Matricula;
                            Log.i("retorno", Aluno);
                            nome.setText(Nome);
                            data.setText(ano);
                            curso.setText(Curso);
                            turma.setText(Turma);
                            telefone.setText(Telefone);
                            email.setText(Email);
                            endereco.setText(Endereço);
                            matricula.setText(Matricula);
                            rg.setText(Rg);
                            cpf.setText(Cpf);

                            //}


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity6.this, "Erro ao processar os dados do servidor", Toast.LENGTH_SHORT).show();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity6.this, "Erro ao obter os dados do servidor", Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    //mostra a imagem irada da camera para a carteirinha
    public void Imagemcarteirinha(){
        String url = "https://5530d240-68d8-4a83-8d79-5bb6ff0cc5f6-00-7qwjdr7ifbdi.riker.replit.dev/fotos/"+cpfLogin+".jpeg";

        Picasso.get().load(url).into(foto);
    }
}

