package com.example.organize;

public class Habito {
    private int id;
    private String name;
    private String time;

    // Construtor vazio
    public Habito() {
    }

    // Construtor com parâmetros
    public Habito(int id, String name, String time) {
        this.id = id;
        this.name = name;
        this.time = time;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
