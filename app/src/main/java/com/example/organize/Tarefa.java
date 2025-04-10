package com.example.organize;

public class Tarefa {
    private int id;
    private String nome;
    private String descricao;
    private String data;
    private String hora;
    private String prioridade;
    private String status;
    private String categoria;
    private boolean isChecked;

    // Construtor completo
    public Tarefa(int id, String nome, String descricao, String data, String hora,
                  String prioridade, String status, String categoria) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.data = data;
        this.hora = hora;
        this.prioridade = prioridade;
        this.status = status;
        this.categoria = categoria;
    }

    // Construtor simplificado para inserção
    public Tarefa(String nome, String descricao, String data, String hora,
                  String prioridade, String status, String categoria) {
        this(0, nome, descricao, data, hora, prioridade, status, categoria);
    }



    public Tarefa(String nome, Object o, Object o1, String descricao, String data, String hora, String prioridade, String status, Object ignoredCategoria) {
        this.nome = nome;
        this.descricao = descricao;
        this.data = data;
        this.hora = hora;
        this.prioridade = prioridade;
        this.status = status;
    }

    public Tarefa(Object o, Object o1, String nome, Object o2, Object o3, String descricao, Object o4, Object o5, Object data, Object o6, Object o7, Object hora, Object o8, Object o9, Object prioridade, Object o10, Object o11, int status, Object o12, Object o13, Object categoria) {
    }



    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return nome + " - " + data + " " + hora + " (" + status + ")";
    }

    public boolean isConcluida() {
        return "Concluída".equals(status);

    }

    public void setisConcluida(boolean isChecked) {
        this.isChecked = isChecked;
    }
}
