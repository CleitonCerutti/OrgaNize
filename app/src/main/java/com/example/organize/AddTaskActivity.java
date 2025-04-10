package com.example.organize;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity {

    private EditText inputTaskName, inputTaskDescription;
    private EditText inputTaskDate, inputTaskTime;
    private Spinner spinnerPriority, spinnerStatus, spinnerCategory;
    private Button btnSaveTask, btnDatePicker, btnTimePicker;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        // Inicializações
        dbHelper = new DatabaseHelper(this);
        initializeViews();
        setupSpinners();
        setupDateAndTimePickers();

        btnSaveTask.setOnClickListener(v -> saveTask());
    }

    private void initializeViews() {
        inputTaskName = findViewById(R.id.task_name_input);
        inputTaskDescription = findViewById(R.id.task_description_input);
        inputTaskDate = findViewById(R.id.task_date_input);
        inputTaskTime = findViewById(R.id.task_time_input);
        spinnerPriority = findViewById(R.id.spinner_priority);
        spinnerStatus = findViewById(R.id.spinner_status);
        spinnerCategory = findViewById(R.id.spinner_category);
        btnSaveTask = findViewById(R.id.btn_save_task);
        btnDatePicker = findViewById(R.id.btn_date_picker);
        btnTimePicker = findViewById(R.id.btn_time_picker);
    }

    private void setupSpinners() {
        ArrayAdapter<CharSequence> priorityAdapter = ArrayAdapter.createFromResource(this,
                R.array.task_priorities, android.R.layout.simple_spinner_item);
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPriority.setAdapter(priorityAdapter);

        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(this,
                R.array.task_statuses, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(statusAdapter);

        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this,
                R.array.task_categories, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);
    }

    private void setupDateAndTimePickers() {
        btnDatePicker.setOnClickListener(v -> showDatePickerDialog());
        btnTimePicker.setOnClickListener(v -> showTimePickerDialog());
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    inputTaskDate.setText(selectedDate);
                }, year, month, day);
        datePickerDialog.show();
    }

    private void showTimePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, selectedHour, selectedMinute) -> {
                    String selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute);
                    inputTaskTime.setText(selectedTime);
                }, hour, minute, true);
        timePickerDialog.show();
    }

    private void saveTask() {
        // Obter valores dos campos
        String nome = inputTaskName.getText().toString().trim();
        String descricao = inputTaskDescription.getText().toString().trim();
        String data = inputTaskDate.getText().toString().trim();
        String hora = inputTaskTime.getText().toString().trim();
        String prioridade = spinnerPriority.getSelectedItem().toString();
        String status = spinnerStatus.getSelectedItem().toString();
        String categoria = spinnerCategory.getSelectedItem().toString();

        // Validações
        if (nome.isEmpty()) {
            inputTaskName.setError("O nome da tarefa é obrigatório");
            return;
        }

        if (data.isEmpty()) {
            inputTaskDate.setError("A data é obrigatória");
            return;
        }

        if (hora.isEmpty()) {
            inputTaskTime.setError("O horário é obrigatório");
            return;
        }

        // Criar e salvar a tarefa
        Tarefa novaTarefa = new Tarefa(
                0, // ID será gerado automaticamente
                nome,
                descricao,
                data,
                hora,
                prioridade,
                status,
                categoria
        );

        long result = dbHelper.inserirTarefa(novaTarefa);

        if (result != -1) {
            Toast.makeText(this, "Tarefa salva com sucesso!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Erro ao salvar tarefa", Toast.LENGTH_SHORT).show();
        }
    }
}
