package com.example.organize;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class TarefaAdapter extends RecyclerView.Adapter<TarefaAdapter.TarefaViewHolder> {

    private List<Tarefa> tarefas = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public TarefaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_item_tarefa, parent, false);
        return new TarefaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TarefaViewHolder holder, int position) {
        Tarefa tarefaAtual = tarefas.get(position);
        holder.textNome.setText(tarefaAtual.getNome());
        holder.textDataHora.setText(tarefaAtual.getData() + " " + tarefaAtual.getHora());
        holder.textStatus.setText(tarefaAtual.getStatus());
    }

    @Override
    public int getItemCount() {
        return tarefas.size();
    }

    public void setTarefas(List<Tarefa> tarefas) {
        this.tarefas = tarefas;
        notifyDataSetChanged();
    }

    class TarefaViewHolder extends RecyclerView.ViewHolder {
        private TextView textNome;
        private TextView textDataHora;
        private TextView textStatus;

        public TarefaViewHolder(@NonNull View itemView) {
            super(itemView);
            textNome = itemView.findViewById(R.id.text_titulo);
            textDataHora = itemView.findViewById(R.id.text_data);
            textStatus = itemView.findViewById(R.id.task_completed_checkbox);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(tarefas.get(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Tarefa tarefa);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
