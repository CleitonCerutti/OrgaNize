package com.example.organize;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Lembrete {
    private int id;
    private String titulo;
    private Date dataHora;
    private boolean status;

    // Construtor principal
    public Lembrete(int id, String titulo, Date dataHora, boolean status) {
        this.id = id;
        this.titulo = titulo;
        this.dataHora = dataHora;
        this.status = status;
    }

    public Lembrete(String titulo, Date dataHora, boolean status) {
        this(0, titulo, dataHora, status);
    }

    // Construtor para criação de novos lembretes (sem ID)
    public Lembrete(String titulo, Date dataHora) {
        this(0, titulo, dataHora, true);
    }

    // Construtor para compatibilidade com o banco de dados
    public Lembrete(String titulo, String data, String hora, Boolean status) {
        this.titulo = titulo;
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
            this.dataHora = format.parse(data + " " + hora);
        } catch (Exception e) {
            this.dataHora = new Date(); // Data atual como fallback
        }
        this.status = status;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public boolean isStatusAtivo() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    // Métodos de formatação
    public String getDataFormatada() {
        return new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(dataHora);
    }

    public String getHoraFormatada() {
        return new SimpleDateFormat("HH:mm", Locale.getDefault()).format(dataHora);
    }

    public String getDataHoraCompleta() {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(dataHora);
    }

    // Métodos para integração com o banco
    public String getDataParaBanco() {
        return getDataFormatada();
    }

    public String getHoraParaBanco() {
        return getHoraFormatada();
    }

    // Métodos auxiliares para compatibilidade
    public String getData() {
        return getDataFormatada();
    }

    public String getHorario() {
        return getHoraFormatada();
    }

    @Override
    public String toString() {
        return titulo + " - " + getDataHoraCompleta() +
                (status ? " (Concluído)" : " (Pendente)");
    }
}
