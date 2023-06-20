package com.example.jogodavelha;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView tvJogo, tvNick, tvAvatar;
    private EditText etNick;
    private Button btJogar;
    private ImageButton ibCoala, ibFlamingo, ibTouro, ibCachorro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvJogo = findViewById(R.id.tvJogo);
        tvNick = findViewById(R.id.tvNick);
        tvAvatar = findViewById(R.id.tvAvatar);
        etNick = findViewById(R.id.etNick);
        btJogar = findViewById(R.id.btJogar);
        ibCoala = findViewById(R.id.ibCoala);
        ibFlamingo = findViewById(R.id.ibFlamingo);
        ibTouro = findViewById(R.id.ibTouro);
        ibCachorro = findViewById(R.id.ibCachorro);

        ibCoala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(MainActivity.this, TelaJogo.class);
                i2.putExtra("imagem", R.drawable.coala);
            }
        });


        ibFlamingo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ibTouro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ibCachorro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btJogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nick = etNick.getText().toString();

                Intent intent = new Intent(MainActivity.this, TelaJogo.class);
                intent.putExtra("nick", nick);
                startActivity(intent);
            }
        });
    }
}

