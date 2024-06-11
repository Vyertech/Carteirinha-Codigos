package com.example.aula2503;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.*;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    boolean isNigthModeON;
    EditText cpf, senha;
    String n1, n2;

    String cpfLogin, senhaLogin;
    String cpflogin2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //INICIO DO CODIGO PARA ATIVAR O MODO NOTURNO E CLARO
        ImageButton imageButton = findViewById(R.id.btndark);

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO){
            isNigthModeON=false;
            imageButton.setBackgroundResource(R.drawable.baseline_wb_sunny2_24);
        } else if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            isNigthModeON=true;
            imageButton.setBackgroundResource(R.drawable.baseline_nightlight_24);
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNigthModeON){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    isNigthModeON=false;
                }
                else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    isNigthModeON=true;
                }
            }
        });
        //FIM DO CODIGO PARA ATIVAR O MODO NOTURNO E CLARO


        //vai puxar o id do xml e trazer para o java
        cpf = findViewById(R.id.tvusuario);
        senha = findViewById(R.id.tvsenha);

    }

    public void Entrar(View view) {

        //pega as variaveis e transforma o texto em string
        cpfLogin =cpf.getText().toString();

        //mostra na tela como teste o CPF do usuario
        //Toast.makeText(MainActivity.this, "cpf login" + cpfLogin, Toast.LENGTH_SHORT).show();
        senhaLogin = senha.getText().toString();

        /*enviar ao servidor para localizar o cpf cadastrado*/
        String url = "https://5530d240-68d8-4a83-8d79-5bb6ff0cc5f6-00-7qwjdr7ifbdi.riker.replit.dev/alunoslogin/"+cpfLogin;
        //String url= "https://carteirinha-digital.onrender.com/alunoslogin/" + cpfLogin;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String senhaRecebida) {
                        // mostra se a requisição foi bem-sucedida ou não,e exibi uma mensagem
                        //Toast.makeText(MainActivity.this, "recebido " + senhaRecebida + " digitado " + senhaLogin, Toast.LENGTH_SHORT).show();
                        //Toast.makeText(MainActivity.this, "perfil" + senhaRecebida.substring(5), Toast.LENGTH_SHORT).show();
                        //Toast.makeText(MainActivity.this, "senha" + senhaRecebida.substring(0,5), Toast.LENGTH_SHORT).show();

                        String senhac, perfilc;
                        senhac = senhaRecebida.substring(0,5);
                        perfilc = senhaRecebida.substring(5);


                        //vai verificar se o login está correto ou não, para efetuar a entrada do usuario no aplicativo
                        if(senhac.equals('"'+senhaLogin)) {

                            if(perfilc.equals("1" + '"')){
                                Toast.makeText(MainActivity.this, "Acesso permitido, aluno " , Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                                intent.putExtra("cpf", cpfLogin);

                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(MainActivity.this, "Acesso permitido, professor", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, MainActivity9.class);
                                intent.putExtra("cpf", cpfLogin);
                                startActivity(intent);
                            }
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Acesso negado", Toast.LENGTH_SHORT).show();
                        }
                            finish(); // Fecha a atividade após efetuar a verificação
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Ocorreu um erro na requisição, exibir uma mensagem de erro
                        Toast.makeText(MainActivity.this, "Erro no login: " + error.toString(), Toast.LENGTH_SHORT).show();
                        Log.d("banco", error.toString());
                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                // Parâmetros da requisição POST (dados do livro/cadastro)
                Map<String, String> params = new HashMap<>();
                params.put("Login", cpfLogin);

                // Log do conteúdo da requisição antes de ser enviada
                Log.d("Aplicativo", "Corpo da requisição POST: " + params.toString());
                return params;
            }
        };

        // Adicionar a requisição à fila de requisições do Volley
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);

    }
}

