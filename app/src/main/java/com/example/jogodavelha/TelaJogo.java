package com.example.jogodavelha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class TelaJogo extends AppCompatActivity {
    private TextView tvApelido;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_jogo);

        tvApelido = findViewById(R.id.tvApelido);

        Intent intent = getIntent();
        String valorRecebido = intent.getStringExtra("nick");
        tvApelido.setText(valorRecebido);
    }
}