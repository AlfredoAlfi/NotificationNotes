package com.example.edu.notificationnotes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Edu on 29/04/2017.
 */

public class Nota {
    private long id;
    private String titulo;
    private String info;
    private long fecha_numero;
    private String fecha;
    private short encendido;

    public Nota(long id, String titulo, String info, long fecha, short encendido) {
        java.util.Date time=new java.util.Date((long)fecha*1000);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");// HH:mm:ss
        this.id = id;
        this.fecha = df.format(time);
        this.fecha_numero = fecha;
        this.titulo = titulo;
        this.info = info;
        this.encendido = encendido;
    }
    public long getID() { return this.id; }
    public String getTitulo() {
        return this.titulo;
    }
    public String getInfo() {
        return this.info;
    }
    public String returnNota() {
        return titulo+"|"+info+"|"+fecha;
    }
    public boolean getEstado() { return (encendido==1); }
    public short toggle() {
        if(this.encendido == 1) {
            this.encendido = 0;
        }
        else {
            this.encendido = 1;
        }
        return this.encendido;
    }
}
