package com.example.organize;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ConfigActivity extends AppCompatActivity {

    private EditText inputHeight, inputWeight, inputAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        // Referências aos campos de entrada

        inputHeight = findViewById(R.id.input_height);
        inputWeight = findViewById(R.id.input_weight);
        inputAge = findViewById(R.id.input_age);
        Button btnAdvance = findViewById(R.id.btn_avancar);
        Animation floatAnimation = AnimationUtils.loadAnimation(ConfigActivity.this, R.anim.flutuar);
        ImageView orgaImage = findViewById(R.id.orga_image);
        orgaImage.startAnimation(floatAnimation);


        // Clique no botão "Avançar"
        btnAdvance.setOnClickListener(v -> {
            // Validar as entradas
            String height = inputHeight.getText().toString().trim();
            String weight = inputWeight.getText().toString().trim();
            String age = inputAge.getText().toString().trim();


            if (validateInputs(height, weight, age)) {
                // Redirecionar para a tela principal
                Intent intent = new Intent(ConfigActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Finaliza a atividade de configuração
            } else {
                Toast.makeText(ConfigActivity.this, "Por favor, preencha todos os campos corretamente.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Valida as entradas do usuário.
     *
     * @param height Altura
     * @param weight Peso
     * @param age    Idade
     * @return true se todos os campos forem válidos, false caso contrário.
     */
    private boolean validateInputs(String height, String weight, String age) {
        // Remover espaços em branco no início e no fim
        height = height.trim();
        weight = weight.trim();
        age = age.trim();

        // Verificar se algum campo está vazio
        if (height.isEmpty() || weight.isEmpty() || age.isEmpty()) {
            return false;
        }

        try {
            int heightValue = Integer.parseInt(height);
            int weightValue = Integer.parseInt(weight);
            int ageValue = Integer.parseInt(age);

            // Verifica se os valores estão dentro de um intervalo razoável
            if (heightValue > 50 && heightValue < 250 &&
                    weightValue > 20 && weightValue < 300 &&
                    ageValue > 0 && ageValue < 120) {
                return true; // Todos os valores são válidos
            } else {
                return false; // Um ou mais valores estão fora do intervalo aceitável
            }
        } catch (NumberFormatException e) {
            // Retorna false caso a entrada não seja um número válido
            return false;
        }
    }
}
