package com.example.organize;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AdicionarHabitoActivity extends AppCompatActivity {

    private EditText editTextHabitName, editTextHabitTime;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_habito);

        dbHelper = new DatabaseHelper(this);
        editTextHabitName = findViewById(R.id.habit_name_input);
        editTextHabitTime = findViewById(R.id.habit_time_input);
        Button btnSaveHabit = findViewById(R.id.btn_save_habit);

        btnSaveHabit.setOnClickListener(v -> {
            String name = editTextHabitName.getText().toString().trim();
            String time = editTextHabitTime.getText().toString().trim();

            if (name.isEmpty() || time.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            long result = dbHelper.insertHabit(name, time);

            if (result != -1) {
                Toast.makeText(this, "Hábito salvo!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Erro ao salvar hábito", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
