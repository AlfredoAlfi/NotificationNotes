package com.example.edu.notificationnotes;

import android.provider.BaseColumns;

/**
 * Created by Edu on 28/04/2017.
 */

public final class Contrato {

    private Contrato() {}

    public static class Notas implements BaseColumns {
        public static final String TABLA = "notas";
        public static final String TITULO = "titulo";
        public static final String INFO = "info";
        public static final String FECHA = "fecha";
        public static final String ENCENDIDO = "encendido";
    }
}
