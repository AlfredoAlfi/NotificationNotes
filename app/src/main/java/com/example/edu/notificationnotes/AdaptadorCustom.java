package com.example.edu.notificationnotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Edu on 29/04/2017.
 */

public class AdaptadorCustom extends ArrayAdapter {

    private Context context;
    private ArrayList datos;

    public interface listenerSwitch {
        public void actualizarSwitch(long id, short estado, String titulo, String info);
        public void eliminarNota(long id, int position);
    }

    public AdaptadorCustom(Context context, ArrayList datos) {
        super(context, R.layout.item_list, datos);
        // Guardamos los parámetros en variables de clase.
        this.context = context;
        this.datos = datos;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ContenedorListaItems contenedor;
        LayoutInflater inflater = LayoutInflater.from(context);
        View item = convertView;
        if(item==null) {
            item = LayoutInflater.from(context).inflate(R.layout.item_list, null);
            contenedor = new ContenedorListaItems();
            contenedor.titulo = (TextView) item.findViewById(R.id.list_item_titulo);
            contenedor.info = (TextView) item.findViewById(R.id.list_item_info);
            contenedor.trash = (ImageButton) item.findViewById(R.id.botonEliminar);
            contenedor.cambio = (Switch) item.findViewById(R.id.apagar_Encender);
            item.setTag(contenedor);
        }
        contenedor = (ContenedorListaItems) item.getTag();
        final Nota nota_tmp = (Nota) datos.get(position);
        contenedor.titulo.setText(nota_tmp.getTitulo());
        contenedor.info.setText(nota_tmp.getInfo());
        contenedor.cambio.setChecked(nota_tmp.getEstado());
        contenedor.cambio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenerSwitch listener = (listenerSwitch) context;
                listener.actualizarSwitch(nota_tmp.getID(),nota_tmp.toggle(), nota_tmp.getTitulo(), nota_tmp.getInfo());
            }
        });
        contenedor.trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenerSwitch listener = (listenerSwitch) context;
                listener.eliminarNota(nota_tmp.getID(), position);
            }
        });
        return item;
    }

}
