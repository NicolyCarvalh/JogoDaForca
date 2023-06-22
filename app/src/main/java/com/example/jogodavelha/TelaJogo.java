package com.example.jogodavelha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    private int quantidadeDeErros = 0;
    private Button btTeste;
    private TextView tvApelido, tvPalavraSorteada, etLetrasCorretas, tvTimer;
    private ImageView ivAvatar;
    private EditText etLetra;
    private List<String> listaPalavras;
    private List<String> letrasDigitadas;
    private String palavraSorteada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_jogo);

        letrasDigitadas = new ArrayList<>();
        quantidadeDeErros = 0;
        tvApelido = findViewById(R.id.tvApelido);
        tvPalavraSorteada = findViewById(R.id.tvPalavraSorteada);
        btTeste = findViewById(R.id.btTeste);
        tvTimer = findViewById(R.id.tvTimer);
        etLetrasCorretas = findViewById(R.id.etLetrasCorretas);

        Intent i1 = getIntent();
        String valorRecebido = i1.getStringExtra("nick");
        tvApelido.setText(valorRecebido);

        //Intent i2 = getIntent();
        //int imagem = i2.getIntExtra("imagem", 0);
        //ivAvatar.setImageResource(imagem);

        listaPalavras = criarListaPalavras();

        palavraSorteada = sortearPalavraAleatoria();

        tvPalavraSorteada.setText("Palavra sorteada: " + palavraSorteada);
        etLetra = findViewById(R.id.etLetra);

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
            // create file not exist
            File file = new File(getFilesDir(), "palavras.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
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
        if (listaPalavras.isEmpty()) {
            return "teste";
        }
        int index = random.nextInt(listaPalavras.size());
        return listaPalavras.get(index);
    }

    private String construirPalavra() {
        String palavra = "";
        for (int i = 0; i < palavraSorteada.length(); i++) {
            String letra = palavraSorteada.substring(i, i + 1);
            if (letrasDigitadas.contains(letra)) {
                palavra += letra;
            } else {
                palavra += "_";
            }
        }
        return palavra;
    }

    private void verificarDerrota() {
        quantidadeDeErros++;
        Log.d("teste", "Quantidade de erros: " + quantidadeDeErros);
        if (quantidadeDeErros == 1) {
            ImageView ivForca = findViewById(R.id.imageView3);
            ivForca.setAlpha(1f);
        } else if (quantidadeDeErros == 2) {
            ImageView ivForca = findViewById(R.id.imageView4);
            ivForca.setAlpha(1f);
        } else if (quantidadeDeErros == 3) {
            ImageView ivForca = findViewById(R.id.imageView5);
            ivForca.setAlpha(1f);
        } else if (quantidadeDeErros == 4) {
            ImageView ivForca = findViewById(R.id.imageView10);
            ivForca.setAlpha(1f);
        } else if (quantidadeDeErros == 5) {
            ImageView ivForca = findViewById(R.id.imageView7);
            ivForca.setAlpha(1f);
        } else
        if (quantidadeDeErros == 6) {
            Toast.makeText(this, "Você perdeu", Toast.LENGTH_SHORT).show();
            // TODO: mostra activity de derrota
            return;
        }
    }

    private void verificarPalavraCompleta() {
        String palavra = construirPalavra();
        if (palavra.equals(palavraSorteada)) {
            Toast.makeText(this, "Palavra completa", Toast.LENGTH_SHORT).show();
            // TODO: mostra activity de vitória
        }
    }

    private void atualizarPalavra() {
        String palavra = construirPalavra();
        etLetrasCorretas.setText(palavra);
    }

    public void verificarLetra(View view) {
        String letra = etLetra.getText().toString();
        etLetra.setText("");
        Log.d("TelaJogo", "Letra clicada: " + letra);
        if (letrasDigitadas.contains(letra)) {
            Toast.makeText(this, "Letra já digitada", Toast.LENGTH_SHORT).show();
            return;
        }
        if (palavraSorteada.contains(letra)) {
            letrasDigitadas.add(letra);
            atualizarPalavra();
            verificarPalavraCompleta();
            Toast.makeText(this, "Letra encontrada", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Letra não encontrada", Toast.LENGTH_SHORT).show();
            verificarDerrota();
        }
    }

}