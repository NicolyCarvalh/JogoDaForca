package com.example.jogodavelha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TelaJogo extends AppCompatActivity {
    private TextView tvApelido, textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_jogo);

        textView = findViewById(R.id.textView);

        tvApelido = findViewById(R.id.tvApelido);
        //ivAvatar = findViewById(R.id.ivAvatar);

        Intent i1 = getIntent();
        String valorRecebido = i1.getStringExtra("nick");
        tvApelido.setText(valorRecebido);

        int teste = getIntent().getIntExtra("imagem", 0);
        //ivAvatar.setImageResource(teste);
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