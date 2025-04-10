package com.example.organize;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LembreteAdapter extends RecyclerView.Adapter<LembreteAdapter.LembreteViewHolder> {

    private Cursor cursor;
    private OnItemClickListener listener;

    public LembreteAdapter(Cursor cursor) {
        this.cursor = cursor;
    }

    @NonNull
    @Override
    public LembreteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_item_lembrete, parent, false);
        return new LembreteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LembreteViewHolder holder, int position) {
        if (!cursor.moveToPosition(position)) return;

        int tituloIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_TITULO);
        int dataIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_DATA);
        int horarioIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_HORARIO);
        int statusIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_STATUS);

        if (tituloIndex == -1 || dataIndex == -1 || horarioIndex == -1 || statusIndex == -1) {
            throw new IllegalStateException("Campos não encontradas na busca do lembrete.");
        }

        holder.textTitulo.setText(cursor.getString(tituloIndex));
        holder.textDataHora.setText(cursor.getString(dataIndex) + " " + cursor.getString(horarioIndex));
        holder.checkConcluido.setChecked(Boolean.valueOf(cursor.getString(statusIndex)));

        Date dataHora;
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
            dataHora = format.parse(cursor.getString(dataIndex) + " " + cursor.getString(horarioIndex));
        } catch (Exception e) {
            dataHora = new Date();
        }

        Lembrete lembreteAtual = new Lembrete(
            cursor.getString(tituloIndex),
            dataHora,
            Boolean.valueOf(cursor.getString(statusIndex))
        );

        holder.checkConcluido.setOnCheckedChangeListener((buttonView, isChecked) -> {
            lembreteAtual.setStatus(isChecked);
            if (listener != null) {
                listener.onCheckChanged(lembreteAtual, isChecked);
            }
        });

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(lembreteAtual);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

//    public void updateLembretes(List<Lembrete> novosLembretes) {
//        this.lembretes = novosLembretes;
//        notifyDataSetChanged();
//    }

    static class LembreteViewHolder extends RecyclerView.ViewHolder {
        TextView textTitulo;
        TextView textDataHora;
        CheckBox checkConcluido;

        public LembreteViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitulo = itemView.findViewById(R.id.text_titulo_lembrete);
            textDataHora = itemView.findViewById(R.id.text_data_hora_lembrete);
            checkConcluido = itemView.findViewById(R.id.check_concluido);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Lembrete lembrete);
        void onCheckChanged(Lembrete lembrete, boolean isChecked);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {

        this.listener = listener;
    }
}
