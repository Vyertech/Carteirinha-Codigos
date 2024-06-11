package com.example.aula2503;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity8 extends AppCompatActivity {
    boolean isNigthModeOn;
    int idaluno;
    TextView textViewNOME;
    TextView textViewNASCI;
    TextView textViewCUR;
    TextView textViewCPF;
    TextView textViewTUR;

    String cpfLogin;
    ImageView imageView4;

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main8);

        //recebe o id do aluno da outra activity
        Intent intent = getIntent();
        cpfLogin = intent.getStringExtra("cpf");

        textViewNOME = findViewById(R.id.textViewNOME);
        textViewNASCI = findViewById(R.id.textViewNASCI);
        textViewCUR = findViewById(R.id.textViewCUR);
        textViewCPF = findViewById(R.id.textViewCPF);
        textViewTUR = findViewById(R.id.textViewTUR);
        imageView4 = findViewById(R.id.imageView4);

        ImageButton imageButton = findViewById(R.id.btndark);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO){
            isNigthModeOn=false;
            imageButton.setBackgroundResource(R.drawable.baseline_wb_sunny_24);
        }else if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            isNigthModeOn=true;
            imageButton.setBackgroundResource(R.drawable.baseline_nightlight_24);
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNigthModeOn){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    isNigthModeOn=false;
                }
                else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    isNigthModeOn=true;
                }
            }
        });

        carregarDados();
        Imagemcarteirinha();
    }



    private void carregarDados() {
        //String url = "https://edcb93ec-3811-4c77-8aff-856dfb23bcdd-00-1ged6n93rzxjj.picard.replit.dev/livros";

        String url = "https://5530d240-68d8-4a83-8d79-5bb6ff0cc5f6-00-7qwjdr7ifbdi.riker.replit.dev/listaalunos/"+cpfLogin;
        //String url= "https://carteirinha-digital.onrender.com/listaalunos/" + cpfLogin;
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
                                String Foto = jsonObject.getString("Foto");
                                String Cpf = jsonObject.getString("Login");
                                String ano = jsonObject.getString("Data_nascimento");
                                String Turma = jsonObject.getString("Turma");
                                String Curso = jsonObject.getString("CursoNome");
                                String Aluno = "Nome: " + Nome + ", Login: " + Cpf + ", Ano: " + ano + "Turma" + Turma + "Curso" + Curso + "Foto" + Foto;
                                Log.i("retorno", Aluno);
                                textViewNOME.setText(Nome);
                                textViewNASCI.setText(ano);
                                textViewCUR.setText(Curso);
                                textViewCPF.setText(Cpf);
                                textViewTUR.setText(Turma);
                                //imageView4.setImageBitmap(Foto);
                            //}

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity8.this, "Erro ao processar os dados do servidor", Toast.LENGTH_SHORT).show();
                            //Toast.makeText(MainActivity8.this,cpfLogin, Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity8.this, "Erro ao obter os dados do servidor", Toast.LENGTH_SHORT).show();
                    }
                });



        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }


    //mostra a imagem irada da camera para a carteirinha
    public void Imagemcarteirinha(){
        String url = "https://5530d240-68d8-4a83-8d79-5bb6ff0cc5f6-00-7qwjdr7ifbdi.riker.replit.dev/fotos/"+cpfLogin+".jpeg";

        Picasso.get().load(url).into(imageView4);

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

}