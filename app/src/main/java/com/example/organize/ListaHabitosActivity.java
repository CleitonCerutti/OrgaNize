package com.example.organize;

import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.content.Intent;

public class ListaHabitosActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HabitoAdapter habitoAdapter;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_habitos);

        recyclerView = findViewById(R.id.recycler_habitos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new DatabaseHelper(this);

        // Carregar os hábitos do banco de dados
        loadHabits();

        FloatingActionButton fabAdd = findViewById(R.id.fab_add_habito);
        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(this, AdicionarHabitoActivity.class);
            startActivity(intent);
        });
    }

    private void loadHabits() {
        Cursor cursor = dbHelper.getAllHabitsCursor();
        habitoAdapter = new HabitoAdapter(cursor);
        recyclerView.setAdapter(habitoAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Atualiza a lista ao voltar para a tela
        loadHabits();
        habitoAdapter.notifyDataSetChanged();
    }
}
