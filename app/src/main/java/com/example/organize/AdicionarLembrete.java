package com.example.organize;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class AdicionarLembrete extends AppCompatActivity {

    private EditText inputTitle;
    private TextView txtSelectedDate, txtSelectedTime;
    private Button btnSelectDate, btnSelectTime, btnSaveReminder;
    private String selectedDate = "", selectedTime = "";
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_lembrete);

        // Inicializações
        dbHelper = new DatabaseHelper(this);
        inputTitle = findViewById(R.id.input_title);
        txtSelectedDate = findViewById(R.id.txt_selected_date);
        txtSelectedTime = findViewById(R.id.txt_selected_time);
        btnSelectDate = findViewById(R.id.btn_select_date);
        btnSelectTime = findViewById(R.id.btn_select_time);
        btnSaveReminder = findViewById(R.id.btn_save_reminder);

        // Seleção de Data
        btnSelectDate.setOnClickListener(v -> showDatePicker());

        // Seleção de Hora
        btnSelectTime.setOnClickListener(v -> showTimePicker());

        // Salvar Lembrete
        btnSaveReminder.setOnClickListener(v -> saveReminder());
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(
                this,
                (view, year, month, day) -> {
                    selectedDate = String.format("%02d/%02d/%04d", day, month+1, year);
                    txtSelectedDate.setText("Data: " + selectedDate);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    private void showTimePicker() {
        Calendar calendar = Calendar.getInstance();
        new TimePickerDialog(
                this,
                (view, hour, minute) -> {
                    selectedTime = String.format("%02d:%02d", hour, minute);
                    txtSelectedTime.setText("Horário: " + selectedTime);
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
        ).show();
    }

    private void saveReminder() {
        String title = inputTitle.getText().toString().trim();

        if (title.isEmpty() || selectedDate.isEmpty() || selectedTime.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        long result = dbHelper.inserirLembrete(String.valueOf(inputTitle), selectedDate, selectedTime, "true");

        if (result != -1) {
            Toast.makeText(this, "Lembrete salvo!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Erro ao salvar lembrete", Toast.LENGTH_SHORT).show();
        }
    }
}
