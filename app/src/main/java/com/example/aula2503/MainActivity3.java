package com.example.aula2503;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class MainActivity3 extends AppCompatActivity {
    boolean isNigthModeON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);


        //ele puxa o ID dos itens do activity_main3.xml
        EditText editText = findViewById(R.id.esit_text);
        Button button = findViewById(R.id.button);
        ImageView imageView = findViewById(R.id.qr_code);
        ImageButton imageButton = findViewById(R.id.btndark);

        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO){
            isNigthModeON = false;
            imageButton.setBackgroundResource(R.drawable.baseline_wb_sunny_24);
        }
        else if ( AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
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


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // MultiFormatWriter é uma classe de fábrica que encontra a subclasse Writer apropriada
                // para o BarcodeFormat solicitado e codifica o código de barras com o conteúdo fornecido
                MultiFormatWriter multiformatwriter = new MultiFormatWriter();

                try{
                    // BitMatrix cria um quadrado vazio
                    BitMatrix bitMatrix = multiformatwriter.encode(editText.getText().toString(), BarcodeFormat.QR_CODE, 300,300);

                    //Barcode obtém um ou mais códigos de barras de uma imagem ou de um arquivo PDF
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    //Codifica um código de barras usando as configurações padrão

                    imageView.setImageBitmap(bitmap);


                } catch(WriterException e){
                    throw new RuntimeException(e);
                    //RuntimeException é usado para erros quando seu aplicativo não pode recuperar
                    // é usado para capturar e lidar com a exceção se ela for lançada dentro do bloco try
                }
            }
        });
    }

    public void casaa(View view) {
        finish();
    }

    public void Log_out(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}

