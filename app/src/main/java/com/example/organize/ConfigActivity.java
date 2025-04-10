package com.example.organize;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ConfigActivity extends AppCompatActivity {

    private EditText inputName, inputAge, inputWeight, inputHeight, inputSleepTime, inputWakeTime;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        dbHelper = new DatabaseHelper(this);

        inputName = findViewById(R.id.input_name);
        inputAge = findViewById(R.id.input_age);
        inputWeight = findViewById(R.id.input_weight);
        inputHeight = findViewById(R.id.input_height);
        inputSleepTime = findViewById(R.id.input_sleep_time);
        inputWakeTime = findViewById(R.id.input_wake_time);
        Button btnAdvance = findViewById(R.id.btn_avancar);

        configurarFormatacaoHorario(inputSleepTime);
        configurarFormatacaoHorario(inputWakeTime);

        btnAdvance.setOnClickListener(v -> salvarDadosUsuario());
    }

    private void configurarFormatacaoHorario(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 2 && before == 0 && !s.toString().contains(":")) {
                    editText.append(":");
                } else if (s.length() == 2 && before == 1 && s.toString().contains(":")) {
                    editText.getText().delete(2, 3);
                }
            }
        });
    }

    private void salvarDadosUsuario() {
        String nome = inputName.getText().toString().trim();
        String idadeStr = inputAge.getText().toString().trim();
        String pesoStr = inputWeight.getText().toString().trim();
        String alturaStr = inputHeight.getText().toString().trim();
        String horaDormir = inputSleepTime.getText().toString().trim();
        String horaAcordar = inputWakeTime.getText().toString().trim();

        if (camposVazios(nome, idadeStr, pesoStr, alturaStr, horaDormir, horaAcordar)) {
            showToast("Preencha todos os campos!");
            return;
        }

        if (!validarHorario(horaDormir) || !validarHorario(horaAcordar)) {
            showToast("Formato de horário inválido! Use HH:MM");
            return;
        }

        try {
            int idade = Integer.parseInt(idadeStr);
            double peso = Double.parseDouble(pesoStr);
            double altura = Double.parseDouble(alturaStr);

            if (valoresInvalidos(idade, peso, altura)) {
                showToast("Valores devem ser maiores que zero!");
                return;
            }

            if (dbHelper.inserirUsuario(nome, idade, peso, altura, horaDormir, horaAcordar)) {
                showToast("Dados salvos com sucesso!");
                irParaMainActivity();
            } else {
                showToast("Erro ao salvar dados!");
            }
        } catch (NumberFormatException e) {
            showToast("Digite valores numéricos válidos!");
        }
    }

    private void showToast(String mensagem) {
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
    }

    private boolean camposVazios(String... campos) {
        for (String campo : campos) {
            if (campo.isEmpty()) return true;
        }
        return false;
    }

    private boolean valoresInvalidos(int idade, double peso, double altura) {
        return idade <= 0 || peso <= 0 || altura <= 0;
    }

    private boolean validarHorario(String horario) {
        return horario.matches("^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$");
    }

    private void irParaMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
