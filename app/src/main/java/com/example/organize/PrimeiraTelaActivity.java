package com.example.organize;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PrimeiraTelaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primeira_tela);

        ImageView orgaImage = findViewById(R.id.orga_image);
        RelativeLayout balloonContainer = findViewById(R.id.orga_balloon_container);
        TextView orgaBalloon = findViewById(R.id.orga_balloon);
        Button btnContinuar = findViewById(R.id.continuar);

        // Configurar mensagens do balão
        String firstMessage = "Olá! Eu sou o Orga e estou aqui para ajudar!";
        String secondMessage = "Vamos organizar sua rotina?Clique em avançar!";

        // Carregar animação de jump
        Animation jumpAnimation = AnimationUtils.loadAnimation(this, R.anim.jump);

        // Listener para exibir o balão e gerenciar as mensagens
        jumpAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Não precisa fazer nada aqui
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Tornar o balão visível com a primeira mensagem
                balloonContainer.setVisibility(View.VISIBLE);
                orgaBalloon.setText(firstMessage);

                // Trocar para a segunda mensagem após 3 segundos
                orgaBalloon.postDelayed(() -> {
                    orgaBalloon.setText(secondMessage);
                }, 3000);

                // Ocultar o balão completamente após 6 segundos
                orgaBalloon.postDelayed(() -> {
                    balloonContainer.setVisibility(View.GONE);
                }, 6000);

                // Iniciar animação de flutuação do Orga
                Animation floatAnimation = AnimationUtils.loadAnimation(PrimeiraTelaActivity.this, R.anim.flutuar);
                orgaImage.startAnimation(floatAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Não precisa fazer nada aqui
            }
        });

        // Iniciar animação de jump
        orgaImage.startAnimation(jumpAnimation);
        btnContinuar.setOnClickListener(v -> {
            // Ir para a tela de configuração
            Intent intent = new Intent(PrimeiraTelaActivity.this, ConfigActivity.class);
            startActivity(intent);
        });
    }
}
