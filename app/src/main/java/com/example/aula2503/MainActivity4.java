package com.example.aula2503;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import VolleyMultipartRequest.java.VolleyMultipartRequest;

public class MainActivity4 extends AppCompatActivity {
    boolean isNigthModeOn;
    ImageView imageView;
    EditText editText;

    Button button;
    Bitmap bitmap;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        //INICIO DO CODIGO PARA ATIVAR O MODO NOTURNO E CLARO
        ImageButton imageButton = findViewById(R.id.btndark);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO){
            isNigthModeOn=false;
            imageButton.setBackgroundResource(R.drawable.baseline_wb_sunny_24);
        } else if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
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
        //FIM DO CODIGO PARA ATIVAR O MODO NOTURNO E CLARO


        imageView = findViewById(R.id.imageview);
        button = findViewById(R.id.button);
        editText = findViewById(R.id.verificacao);


        //permissão para acessar a camera
        if(ContextCompat.checkSelfPermission(MainActivity4.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity4.this, new String[]{
                    Manifest.permission.CAMERA
            }, 100);
        }
        //intenção de abrir a camera para tirar a foto
        button.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 100);
            }
        });
    }
    //tira a foto e armazena numa variavel
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100){
            bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
        }
    }

    public void Salvarimage(View view) {
        //cria um é capaz de enviar dados a um determinado Stream
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        //serve para compactar uma imagem em formato JPEG
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        //pega os dados da imagem
        byte[] imageBytes = stream.toByteArray();
        //transforma a imagem em base64 para string
        String foto = Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT);
        Log.i("l", "recebeu");

        String url = "https://5530d240-68d8-4a83-8d79-5bb6ff0cc5f6-00-7qwjdr7ifbdi.riker.replit.dev/upload/"+editText.getText().toString();
        //String url= "https://carteirinha-digital.onrender.com/upload/" + editText.getText().toString();
        VolleyMultipartRequest stringRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject s) {

                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Toast.makeText(MainActivity4.this, "Erro" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            public Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                // file name could found file base or direct access from real path
                // for now just get bitmap data from ImageView
                params.put("avatar", new DataPart("fotinha", imageBytes, "image/jpeg"));

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity4.this);
        requestQueue.add(stringRequest);

    }


    //intenção de sair da conta e voltar para a tela de login
    public void Log_out(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    //intenção de voltar para a tela inicial
    public void home(View view) {
        finish();
    }
}


