package com.example.jogodavelha;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

public class InserirPalavras extends AppCompatActivity {

    private Button btInserir;
    private EditText etNovaPalavra;

    MediaPlayer mp;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserir_palavras);

        btInserir = findViewById(R.id.btInserir);
        etNovaPalavra = findViewById(R.id.etNovaPalavra);

        btInserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp = MediaPlayer.create(InserirPalavras.this, R.raw.click);
                mp.start();

                String novaPalavra = etNovaPalavra.getText().toString();
                //palavrasDoBanco();
                addNovaPalavra(novaPalavra);

                etNovaPalavra.setText("");
                Toast.makeText(InserirPalavras.this, "Palavra adicionada com sucesso", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void addNovaPalavra(String novaPalavra) {
        try {
            File file = new File(getFilesDir(), "palavras.txt");
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.newLine();
            bufferedWriter.write(novaPalavra);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
