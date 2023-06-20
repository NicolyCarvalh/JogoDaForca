package com.example.jogodavelha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
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

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            // Extraia a imagem do Bundle
            Bitmap selectedImageBitmap = bundle.getParcelable("selectedImage");

            // Exiba a imagem em um ImageView
            ImageView imageView = findViewById(R.id.ivAvatar);
            imageView.setImageBitmap(selectedImageBitmap);
        }
    }
}