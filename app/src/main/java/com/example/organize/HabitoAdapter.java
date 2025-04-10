package com.example.organize;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HabitoAdapter extends RecyclerView.Adapter<HabitoAdapter.HabitoViewHolder> {

    private Cursor cursor;

    public HabitoAdapter(Cursor cursor) {
        this.cursor = cursor;
    }

    @NonNull
    @Override
    public HabitoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla o layout do item do hábito
        View habitItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_habito, parent, false);
        return new HabitoViewHolder(habitItemView);
    }

    @Override
    public void onBindViewHolder(HabitoViewHolder holder, int position) {
        if (!cursor.moveToPosition(position)) return;

        // Busca o indice das colunas
        int nomeIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_HABIT_NAME);
        int horarioIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_HABIT_TIME);

        // Verifica se os indices foram encontrados
        if (nomeIndex == -1 || horarioIndex == -1) {
            throw new IllegalStateException("Colunas não encontradas no Cursor.");
        }

        // Busca os valores
        String nome = cursor.getString(nomeIndex);
        String horario = cursor.getString(horarioIndex);

        holder.textNome.setText(nome);
        holder.textHorario.setText(horario);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public static class HabitoViewHolder extends RecyclerView.ViewHolder {
        TextView textNome, textHorario;

        public HabitoViewHolder(View itemView) {
            super(itemView);
            textNome = itemView.findViewById(R.id.txt_habito_nome);
            textHorario = itemView.findViewById(R.id.txt_habito_horario);
        }
    }
    public void swapCursor(Cursor newCursor) {
        if (cursor != null) {
            cursor.close();
        }
        cursor = newCursor;
        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }
}
