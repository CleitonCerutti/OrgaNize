package com.example.organize;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class ChatOrgaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_orga);

        // Referenciar os componentes
        VideoView videoView = findViewById(R.id.video_view);
        Button btnBack = findViewById(R.id.btn_back);

        // Caminho do vídeo (localizado na pasta raw)
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.class);

        // Configurar o vídeo
        videoView.setVideoURI(videoUri);
        videoView.setOnPreparedListener(mp -> {
            mp.setLooping(true); // Deixar o vídeo em loop, se desejado
        });

        // Reproduzir automaticamente o vídeo
        videoView.start();

        // Clique no botão "Voltar"
        btnBack.setOnClickListener(v -> {
            // Finalizar a tela e voltar
            finish();
        });
    }
}
