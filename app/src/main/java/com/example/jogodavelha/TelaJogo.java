package com.example.jogodavelha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class TelaJogo extends AppCompatActivity {
    private static final long TEMPO_TOTAL_MS = 3 * 60 * 1000;
    private long tempoRestanteMs = TEMPO_TOTAL_MS;
    private Button btTeste;
    private TextView tvApelido, tvPalavraSorteada, tvTimer;
    private ImageView ivAvatar;
    private List<String> listaPalavras;
    private String palavraSorteada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_jogo);

        tvApelido = findViewById(R.id.tvApelido);
        tvPalavraSorteada = findViewById(R.id.tvPalavraSorteada);
        btTeste = findViewById(R.id.btTeste);
        tvTimer = findViewById(R.id.tvTimer);

        Intent i1 = getIntent();
        String valorRecebido = i1.getStringExtra("nick");
        tvApelido.setText(valorRecebido);

        //Intent i2 = getIntent();
        //int imagem = i2.getIntExtra("imagem", 0);
        //ivAvatar.setImageResource(imagem);

        listaPalavras = criarListaPalavras();

        palavraSorteada = sortearPalavraAleatoria();

        tvPalavraSorteada.setText("Palavra sorteada: " + palavraSorteada);

        btTeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaJogo.this, InserirPalavras.class);
                startActivity(intent);
            }
        });

        Handler handler = new Handler();
        Runnable atualizarTempoRunnable = new Runnable() {
            @Override
            public void run() {
                atualizarTempo();
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (tempoRestanteMs > 0) {
                    try {
                        Thread.sleep(2000);
                        tempoRestanteMs -= 1000;

                        handler.post(atualizarTempoRunnable);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        atualizarTempo();
    }
    private void atualizarTempo() {
        long minutos = tempoRestanteMs / 60000;
        long segundos = (tempoRestanteMs % 60000) / 1000;

        String tempoFormatado = String.format(Locale.getDefault(), "%02d:%02d", minutos, segundos);
        tvTimer.setText(tempoFormatado);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_restart:
                Toast.makeText(this, "Partida reiniciada", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_new_game:
                Toast.makeText(this, "Nova partida iniciada", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_exit:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private List<String> criarListaPalavras() {
        List<String> palavras = new ArrayList<>();
        try {
            FileInputStream fileInputStream = openFileInput("palavras.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader br = new BufferedReader(inputStreamReader);

            String linha;
            while ((linha = br.readLine()) != null) {
                palavras.add(linha);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return palavras;
    }
    private String sortearPalavraAleatoria() {
        Random random = new Random();
        int index = random.nextInt(listaPalavras.size());
        return listaPalavras.get(index);
    }

}