package com.example.jogodavelha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class TelaJogo extends AppCompatActivity {
    private TextView tvApelido;
    private ImageView ivAvatar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_jogo);

        tvApelido = findViewById(R.id.tvApelido);
        ivAvatar = findViewById(R.id.ivAvatar);

        Intent i1 = getIntent();
        String valorRecebido = i1.getStringExtra("nick");
        tvApelido.setText(valorRecebido);

        //Intent i2 = getIntent();
        //int imagem = i2.getIntExtra("imagem", 0);
        //ivAvatar.setImageResource(imagem);

    }
}