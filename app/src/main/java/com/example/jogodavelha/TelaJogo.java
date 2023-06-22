package com.example.jogodavelha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
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
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class TelaJogo extends AppCompatActivity {
    private static final long TEMPO_TOTAL_MS = 3 * 60 * 1000;
    private long tempoRestanteMs = TEMPO_TOTAL_MS;
    private Button btTeste, btVerificar;
    private TextView tvApelido, tvPalavraSorteada, tvTimer, tvLetrasCorretas, etLetrasIncorretas,tvResultado1;
    private ImageView ivAvatar;

    private EditText etLetra;
    private String palavraAleatoria, palavraOculta;
    private List<Character> tentativas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_jogo);

        tvApelido = findViewById(R.id.tvApelido);
        tvPalavraSorteada = findViewById(R.id.tvPalavraSorteada);
        btTeste = findViewById(R.id.btTeste);
        tvTimer = findViewById(R.id.tvTimer);
        tvLetrasCorretas = findViewById(R.id.tvLetrasCorretas);
        etLetrasIncorretas = findViewById(R.id.etLetrasIncorretas);
        etLetra = findViewById(R.id.etLetra);
        btVerificar = findViewById(R.id.btVerificar);
        tvResultado1 = findViewById(R.id.tvResultado1);

        Intent i1 = getIntent();
        String valorRecebido = i1.getStringExtra("nick");
        tvApelido.setText(valorRecebido);

        Intent i2 = getIntent();
        int selectedImageId = i2.getIntExtra("selectedImageId", 0);

        ImageView ivAvatar = findViewById(R.id.ivAvatar);

        if (selectedImageId != 0) {
            ivAvatar.setImageResource(selectedImageId);
        }

        btTeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaJogo.this, InserirPalavras.class);
                startActivity(intent);
            }
        });

       // File file = new File(getFilesDir(), "palavras.txt");

        try {
            FileInputStream fileInputStream = openFileInput("palavras.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            List<String> palavras = new ArrayList<>();

            String linha;
            while ((linha = reader.readLine()) != null) {
                palavras.add(linha);
            }

            reader.close();

            if (!palavras.isEmpty()) {
                Random random = new Random();
                int indicePalavraAleatoria = random.nextInt(palavras.size());
                palavraAleatoria = palavras.get(indicePalavraAleatoria);
            } else {
                Toast.makeText(this, "O arquivo de palavras está vazio", Toast.LENGTH_SHORT).show();
            }

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Erro ao ler o arquivo de palavras", Toast.LENGTH_SHORT).show();
        }

        btVerificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String letraInserida = etLetra.getText().toString().toLowerCase();

                if (letraInserida.isEmpty()) {
                    Toast.makeText(TelaJogo.this, "Digite uma letra.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (tvResultado1.getText().toString().contains(letraInserida)
                        || etLetrasIncorretas.getText().toString().contains(letraInserida)) {
                    Toast.makeText(TelaJogo.this, "Letra já utilizada.", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean letraEncontrada = false;
                StringBuilder resultado = new StringBuilder(tvResultado1.getText().toString());

                for (int i = 0; i < palavraAleatoria.length(); i++) {
                    char letraPalavra = palavraAleatoria.charAt(i);

                    if (Character.toString(letraPalavra).equalsIgnoreCase(letraInserida)) {
                        letraEncontrada = true;
                        resultado.setCharAt(i, letraPalavra);
                    }
                }

                if (letraEncontrada) {
                    tvResultado1.setText(resultado.toString());
                } else {
                    etLetrasIncorretas.append(letraInserida);
                }

                if (resultado.toString().equalsIgnoreCase(palavraAleatoria)) {
                    Toast.makeText(TelaJogo.this, "Letra correta!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TelaJogo.this, "Letra incorreta!", Toast.LENGTH_SHORT).show();
                }
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
}