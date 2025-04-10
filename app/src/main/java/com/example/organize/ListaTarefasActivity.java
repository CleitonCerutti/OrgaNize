package com.example.organize;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ListaTarefasActivity extends AppCompatActivity {

    private RecyclerView recyclerViewTarefas;
    private TarefaAdapter tarefaAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_tarefas);

        recyclerViewTarefas = findViewById(R.id.recyclerViewTarefas);

        databaseHelper = new DatabaseHelper(this);

        // Configurar RecyclerView
        recyclerViewTarefas.setLayoutManager(new LinearLayoutManager(this));
        tarefaAdapter = new TarefaAdapter();
        recyclerViewTarefas.setAdapter(tarefaAdapter);

        // Carregar tarefas
        carregarTarefas();


        // Configurar clique nos itens
        tarefaAdapter.setOnItemClickListener(tarefa -> {
            Intent intent = new Intent(ListaTarefasActivity.this, DetalhesTarefaActivity.class);
            intent.putExtra("TAREFA_ID", tarefa.getId());
            startActivity(intent);
        });
    }

    private void carregarTarefas() {
        List<Tarefa> tarefas = databaseHelper.getTodasTarefas();

        if (tarefas.isEmpty()) {
            Toast.makeText(this, "Nenhuma tarefa cadastrada.", Toast.LENGTH_SHORT).show();
        } else {
            tarefaAdapter.setTarefas(tarefas);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarTarefas(); // Recarrega quando a activity retorna ao foco
    }
}
