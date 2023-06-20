package com.example.jogodavelha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TelaJogo extends AppCompatActivity {
    private TextView tvApelido, tvPalavraSorteada;
    private ImageView ivAvatar;
    private List<String> listaPalavras;
    private String palavraSorteada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_jogo);

        tvApelido = findViewById(R.id.tvApelido);
        tvPalavraSorteada = findViewById(R.id.tvPalavraSorteada);

        Intent i1 = getIntent();
        String valorRecebido = i1.getStringExtra("nick");
        tvApelido.setText(valorRecebido);

        //Intent i2 = getIntent();
        //int imagem = i2.getIntExtra("imagem", 0);
        //ivAvatar.setImageResource(imagem);

        listaPalavras = criarListaPalavras();
        palavraSorteada = sortearPalavraAleatoria();

        tvPalavraSorteada.setText("Palavra sorteada: " + palavraSorteada);
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
        palavras.add("Gato");
        palavras.add("Cachorro");
        palavras.add("Elefante");
        palavras.add("Leão");
        palavras.add("Girafa");
        palavras.add("Tigre");
        palavras.add("Macaco");
        palavras.add("Zebra");
        palavras.add("Cavalo");
        palavras.add("Pássaro");
        return palavras;
    }
    private String sortearPalavraAleatoria() {
        Random random = new Random();
        int index = random.nextInt(listaPalavras.size());
        return listaPalavras.get(index);
    }

}