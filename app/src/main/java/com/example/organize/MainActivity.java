package com.example.organize;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private List<Message> messageList = new ArrayList<>();
    private MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Certifique-se de que o layout corresponde ao arquivo XML

        // Referências para os componentes da interface
        ImageView menuButton = findViewById(R.id.menu_button);
        Button btnAddTask = findViewById(R.id.add_task_button);
        Button btnTalkOrga = findViewById(R.id.chat_with_orga_button);
        progressBar = findViewById(R.id.daily_progress);
        TextView txtInfoCurrentDate = findViewById(R.id.current_date);

        // Coloca a data atual
        Date currentDate = new Date();
        SimpleDateFormat brazillianFormat = new SimpleDateFormat("dd/MM/yyyy");
        String currentDateFormatted = brazillianFormat.format(currentDate);
        txtInfoCurrentDate.setText(String.format("Hoje: %s", currentDateFormatted));

        // Configuração inicial do ProgressBar
        progressBar.setProgress(00); // Exemplo: Progresso inicial em 50%
        progressBar.setMax(100); // Progresso máximo (100%)

        // Clique no botão de menu
        menuButton.setOnClickListener(v -> {
            // Aqui você pode abrir uma nova tela ou exibir um menu lateral
            Toast.makeText(MainActivity.this, "Menu clicado", Toast.LENGTH_SHORT).show();
        });

        // Clique no botão "Adicionar Tarefa"
        btnAddTask.setOnClickListener(v -> {
            // Redirecionar para a tela de adicionar tarefa
            Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
            startActivity(intent);
        });

        // Clique no botão "Falar com Orga"
        btnTalkOrga.setOnClickListener(v -> {
            // Aqui você pode abrir uma interface de chat ou uma nova tela para a conversa
            Intent intent = new Intent(MainActivity.this, ChatOrgaActivity.class);
            startActivity(intent);
        });

        // Simulação de progresso para exemplo
        simulateProgress();

        // Animação da imagem do Orga
        Animation floatAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.flutuar);
        ImageView orgaImage = findViewById(R.id.orga_image);
        orgaImage.startAnimation(floatAnimation);




    }


    /**
     * Simula o progresso no ProgressBar, apenas para demonstração.
     */
    private void simulateProgress() {
        new Thread(() -> {
            try {
                for (int i = 50; i <= 100; i++) {
                    Thread.sleep(100); // Espera 100ms
                    final int progress = i;
                    runOnUiThread(() -> progressBar.setProgress(progress));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
      }
}
