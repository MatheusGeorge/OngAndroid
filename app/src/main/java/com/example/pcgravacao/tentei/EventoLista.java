package com.example.pcgravacao.tentei;


public class EventoLista {

    private String data;
    private String titulo;

    public EventoLista(String data, String titulo) {
        this.data = data;
        this.titulo = titulo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
