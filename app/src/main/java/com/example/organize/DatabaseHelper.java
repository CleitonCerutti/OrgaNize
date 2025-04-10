package com.example.organize;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String COLUMN_HABIT_TIME ="time" ;
    private static final String DATABASE_NAME = "organize.db";
    private static final int DATABASE_VERSION = 2;

    // Nomes das tabelas
    private static final String TABLE_USUARIO = "usuario";
    private static final String TABLE_LEMBRETES = "lembretes";
    private static final String TABLE_TAREFAS = "tarefas";
    private static final String TABLE_HABITS = "habits";

    // Colunas comuns
    private static final String COLUMN_ID = "id";

    // Colunas da tabela Usuário
    private static final String COLUMN_NOME = "nome";
    private static final String COLUMN_IDADE = "idade";
    private static final String COLUMN_PESO = "peso";
    private static final String COLUMN_ALTURA = "altura";
    private static final String COLUMN_HORA_DORMIR = "horaDormir";
    private static final String COLUMN_HORA_ACORDAR = "horaAcordar";

    // Colunas da tabela Lembretes
    public static final String COLUMN_TITULO = "titulo";
    public static final String COLUMN_DATA = "data";
    public static final String COLUMN_HORARIO = "horario";
    public static final String COLUMN_STATUS = "status";

    // Colunas da tabela Tarefas

    public static final String COLUMN_TAREFA_NOME = "nome";
    public static final String COLUMN_TAREFA_DESCRICAO = "descricao";
    public static final String COLUMN_TAREFA_DATA = "data";
    public static final String COLUMN_TAREFA_HORA = "hora";
    public static final String COLUMN_TAREFA_PRIORIDADE = "prioridade";
    public static final String COLUMN_TAREFA_STATUS = "status";
    public static final String COLUMN_TAREFA_CATEGORIA = "categoria";

    // Colunas da tabela Habits
    static final String COLUMN_HABIT_NAME = "name";
    private int isChecked;
    private int id;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Criar tabela Usuário
        String CREATE_TABLE_USUARIO = "CREATE TABLE " + TABLE_USUARIO + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NOME + " TEXT, " +
                COLUMN_IDADE + " INTEGER, " +
                COLUMN_PESO + " REAL, " +
                COLUMN_ALTURA + " REAL, " +
                COLUMN_HORA_DORMIR + " TEXT, " +
                COLUMN_HORA_ACORDAR + " TEXT)";
        db.execSQL(CREATE_TABLE_USUARIO);

        // Criar tabela Lembretes
        String CREATE_TABLE_LEMBRETES = "CREATE TABLE " + TABLE_LEMBRETES + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITULO + " TEXT, " +
                COLUMN_DATA + " TEXT, " +
                COLUMN_HORARIO + " TEXT, " +
                COLUMN_STATUS + " TEXT DEFAULT 'false')";
        db.execSQL(CREATE_TABLE_LEMBRETES);

        // Criar tabela Tarefas (corrigida com COLUMN_ID)
        String CREATE_TABLE_TAREFAS = "CREATE TABLE " + TABLE_TAREFAS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TAREFA_NOME + " TEXT NOT NULL, " +
                COLUMN_TAREFA_DESCRICAO + " TEXT, " +
                COLUMN_TAREFA_DATA + " TEXT, " +
                COLUMN_TAREFA_HORA + " TEXT, " +
                COLUMN_TAREFA_PRIORIDADE + " TEXT, " +
                COLUMN_TAREFA_STATUS + " TEXT, " +
                COLUMN_TAREFA_CATEGORIA + " TEXT)";
        db.execSQL(CREATE_TABLE_TAREFAS);

        // Criar tabela Habits
        String CREATE_TABLE_HABITS = "CREATE TABLE " + TABLE_HABITS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_HABIT_NAME + " TEXT NOT NULL, " +
                COLUMN_HABIT_TIME + " TEXT NOT NULL)";
        db.execSQL(CREATE_TABLE_HABITS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LEMBRETES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TAREFAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HABITS);
        onCreate(db);
    }

    // ==================== MÉTODOS PARA USUÁRIO ====================
    public boolean inserirUsuario(String nome, int idade, double peso, double altura,
                                  String horaDormir, String horaAcordar) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USUARIO, null, null); // Garante apenas um usuário

        ContentValues values = new ContentValues();
        values.put(COLUMN_NOME, nome);
        values.put(COLUMN_IDADE, idade);
        values.put(COLUMN_PESO, peso);
        values.put(COLUMN_ALTURA, altura);
        values.put(COLUMN_HORA_DORMIR, horaDormir);
        values.put(COLUMN_HORA_ACORDAR, horaAcordar);

        long result = db.insert(TABLE_USUARIO, null, values);
        db.close();
        return result != -1;
    }

    public Cursor getUsuario() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_USUARIO + " LIMIT 1", null);
    }

    // ==================== MÉTODOS PARA LEMBRETES ====================
    public long inserirLembrete(String titulo, String data, String horario, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITULO, titulo);
        values.put(COLUMN_DATA, data);
        values.put(COLUMN_HORARIO, horario);
        values.put(COLUMN_STATUS, status);
        long result = db.insert(TABLE_LEMBRETES, null, values);
        db.close();
        return result;
    }

    public boolean deletarLembrete(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_LEMBRETES, COLUMN_ID + "=?",
                new String[]{String.valueOf(id)});
        db.close();
        return rowsDeleted > 0;
    }

    public Cursor getLembretes() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_LEMBRETES,
                new String[]{COLUMN_ID, COLUMN_TITULO, COLUMN_DATA, COLUMN_HORARIO, COLUMN_STATUS},
                null, null, null, null,
                COLUMN_DATA + " ASC, " + COLUMN_HORARIO + " ASC");
    }

    // ==================== MÉTODOS PARA TAREFAS ====================
    public long inserirTarefa(Tarefa tarefa) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_TAREFA_NOME, tarefa.getNome());
        values.put(COLUMN_TAREFA_DESCRICAO, tarefa.getDescricao());
        values.put(COLUMN_TAREFA_DATA, tarefa.getData());
        values.put(COLUMN_TAREFA_HORA, tarefa.getHora());
        values.put(COLUMN_TAREFA_PRIORIDADE, tarefa.getPrioridade());
        values.put(COLUMN_TAREFA_STATUS, tarefa.getStatus());
        values.put(COLUMN_TAREFA_CATEGORIA, tarefa.getCategoria());

        long id = db.insert(TABLE_TAREFAS, null, values);
        db.close();
        return id;
    }

    public boolean atualizarTarefa() {
        return atualizarTarefa(null);
    }

    public boolean atualizarTarefa(Tarefa tarefa) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_TAREFA_NOME, tarefa.getNome());
        values.put(COLUMN_TAREFA_DESCRICAO, tarefa.getDescricao());
        values.put(COLUMN_TAREFA_DATA, tarefa.getData());
        values.put(COLUMN_TAREFA_HORA, tarefa.getHora());
        values.put(COLUMN_TAREFA_PRIORIDADE, tarefa.getPrioridade());
        values.put(COLUMN_TAREFA_STATUS, tarefa.getStatus());
        values.put(COLUMN_TAREFA_CATEGORIA, tarefa.getCategoria());

        int rowsAffected = db.update(TABLE_TAREFAS, values,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(tarefa.getId())});
        db.close();
        return rowsAffected > 0;
    }

    public List<Tarefa> getTodasTarefas() {
        List<Tarefa> tarefas = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_TAREFAS, null, null, null, null, null,
                    COLUMN_TAREFA_DATA + " ASC, " + COLUMN_TAREFA_HORA + " ASC");

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    // Validação segura das colunas
                    int idIndex = cursor.getColumnIndex(COLUMN_ID);
                    int nomeIndex = cursor.getColumnIndex(COLUMN_TAREFA_NOME);
                    int descricaoIndex = cursor.getColumnIndex(COLUMN_TAREFA_DESCRICAO);
                    int dataIndex = cursor.getColumnIndex(COLUMN_TAREFA_DATA);
                    int horaIndex = cursor.getColumnIndex(COLUMN_TAREFA_HORA);
                    int prioridadeIndex = cursor.getColumnIndex(COLUMN_TAREFA_PRIORIDADE);
                    int statusIndex = cursor.getColumnIndex(COLUMN_TAREFA_STATUS);
                    int categoriaIndex = cursor.getColumnIndex(COLUMN_TAREFA_CATEGORIA);

                    if (idIndex == -1 || nomeIndex == -1 || descricaoIndex == -1 || dataIndex == -1 || horaIndex == -1
                            || prioridadeIndex == -1 || statusIndex == -1 || categoriaIndex == -1) {
                        throw new IllegalStateException("Coluna não encontrada no Cursor.");
                    }

                    Tarefa tarefa = new Tarefa(
                            cursor.getInt(idIndex),
                            cursor.getString(nomeIndex),
                            cursor.getString(descricaoIndex),
                            cursor.getString(dataIndex),
                            cursor.getString(horaIndex),
                            cursor.getString(prioridadeIndex),
                            cursor.getString(statusIndex),
                            cursor.getString(categoriaIndex)
                    );
                    tarefas.add(tarefa);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
        return tarefas;
    }

    public Tarefa getTarefa(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TAREFAS,
                new String[]{COLUMN_ID, COLUMN_TAREFA_NOME, COLUMN_TAREFA_DESCRICAO,
                        COLUMN_TAREFA_DATA, COLUMN_TAREFA_HORA, COLUMN_TAREFA_PRIORIDADE,
                        COLUMN_TAREFA_STATUS, COLUMN_TAREFA_CATEGORIA},
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Tarefa tarefa = new Tarefa(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7));
            cursor.close();
            return tarefa;
        }
        return null;
    }

    public boolean deletarTarefa(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_TAREFAS,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
        return rowsDeleted > 0;
    }

    // ==================== MÉTODOS PARA HÁBITOS ====================
    public long inserirHabito(String name, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_HABIT_NAME, name);
        values.put(COLUMN_HABIT_TIME, time);
        long result = db.insert(TABLE_HABITS, null, values);
        db.close();
        return result;
    }

    public boolean deletarHabito(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_HABITS, COLUMN_ID + "=?",
                new String[]{String.valueOf(id)});
        db.close();
        return rowsDeleted > 0;
    }

    public Cursor getHabitos() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_HABITS,
                new String[]{COLUMN_ID, COLUMN_HABIT_NAME, COLUMN_HABIT_TIME},
                null, null, null, null,
                COLUMN_HABIT_TIME + " ASC");
    }

    // ==================== MÉTODOS ÚTEIS ====================
    public String getUserName() {
        SQLiteDatabase db = this.getReadableDatabase();
        String userName = "Usuário";
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_NOME + " FROM " + TABLE_USUARIO + " LIMIT 1", null);
        if (cursor.moveToFirst()) {
            userName = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return userName;
    }

    public double[] getUserWeightAndHeight() {
        SQLiteDatabase db = this.getReadableDatabase();
        double[] data = {0.0, 0.0};
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_PESO + ", " + COLUMN_ALTURA +
                " FROM " + TABLE_USUARIO + " LIMIT 1", null);
        if (cursor.moveToFirst()) {
            data[0] = cursor.getDouble(0); // Peso
            data[1] = cursor.getDouble(1) / 100; // Altura em metros
        }
        cursor.close();
        db.close();
        return data;
    }

    public long insertHabit(String name, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_HABIT_NAME, name);
        values.put(COLUMN_HABIT_TIME, time);

        long result = db.insert(TABLE_HABITS, null, values);
        db.close();

        return result;
    }
    public List<Habito> getAllHabits() {
        List<Habito> habitList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_HABITS, null);

        if (cursor.moveToFirst()) {
            do {
                Habito habit = new Habito();
                habit.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HABIT_NAME)));
                habit.setTime(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HABIT_TIME)));

                habitList.add(habit);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return habitList;
    }

    public Cursor getAllHabitsCursor() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_HABITS, null);
    }

    public boolean atualizarStatusLembrete(int id, int isChecked) {

        this.id = id;
        this.isChecked = isChecked;
        return false;
    }

    public Cursor getAllLembretesCursor() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_LEMBRETES, null);
    }
}
