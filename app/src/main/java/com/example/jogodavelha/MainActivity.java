package com.example.jogodavelha;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tvJogo, tvNick, tvAvatar;
    private EditText etNick;
    private Button btJogar;
    private ImageButton ibCoala, ibFlamingo, ibTouro, ibCachorro;

    private ImageButton selectedImageButton;

    private ImageButton selecionarImagem;

    private int selectedImageResource = 0;
    private int selectedImageId = 0;



    //MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mp = MediaPlayer.create(MainActivity.this, R.raw.musicaJogoForca);

        tvJogo = findViewById(R.id.tvJogo);
        tvNick = findViewById(R.id.tvNick);
        tvAvatar = findViewById(R.id.tvAvatar);
        etNick = findViewById(R.id.etNick);
        btJogar = findViewById(R.id.btJogar);
        ibCoala = findViewById(R.id.ibCoala);
        ibFlamingo = findViewById(R.id.ibFlamingo);
        ibTouro = findViewById(R.id.ibTouro);
        ibCachorro = findViewById(R.id.ibCachorro);

        //mp.start();

        ibCoala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecionarImagem(ibCoala);
                selectedImageId = R.id.ibCoala;
            }
        });

        ibFlamingo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecionarImagem(ibFlamingo);
                selectedImageId = R.id.ibFlamingo;
            }
        });

        ibTouro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecionarImagem(ibTouro);
                selectedImageId = R.id.ibTouro;
            }
        });

        ibCachorro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecionarImagem(ibCachorro);
                selectedImageId = R.id.ibCachorro;
            }
        });

        btJogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nick = etNick.getText().toString();

                Intent intent = new Intent(MainActivity.this, TelaJogo.class);
                intent.putExtra("nick", nick);
                if (selectedImageId != 0) {
                    intent.putExtra("selectedImageId", selectedImageId);
                }
                startActivity(intent);
            }
        });

    }

    private void selecionarImagem(ImageButton imageButton) {
        ibCoala.setSelected(false);
        ibFlamingo.setSelected(false);
        ibTouro.setSelected(false);
        ibCachorro.setSelected(false);

        // Define a imagem clicada como selecionada
        imageButton.setSelected(true);
        selecionarImagem = imageButton;
    }
}
