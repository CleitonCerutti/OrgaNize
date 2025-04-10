package com.example.organize;

import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.content.Intent;

import java.util.List;

public class ListaLembretesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LembreteAdapter lembreteAdapter;
    private DatabaseHelper dbHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_lembretes);

        recyclerView = findViewById(R.id.recycler_lembretes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new DatabaseHelper(this);

        // Carregar os hábitos do banco de dados
        carregarLembretes();

        FloatingActionButton fabAdd = findViewById(R.id.fab_add_lembrete);
        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(this, AdicionarLembrete.class);
            startActivity(intent);
        });
    }

    private void carregarLembretes() {
        Cursor cursor = dbHelper.getAllLembretesCursor();
        lembreteAdapter = new LembreteAdapter(cursor);
        recyclerView.setAdapter(lembreteAdapter);
    }



    @Override
    protected void onResume() {
        super.onResume();
        // Atualiza a lista ao voltar para a tela
        carregarLembretes();

    }
}
