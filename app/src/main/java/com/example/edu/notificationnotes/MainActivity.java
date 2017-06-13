package com.example.edu.notificationnotes;

import android.app.NotificationManager;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements AdaptadorCustom.listenerSwitch,dialogo_nota.pasoDatosListener/*,AdapterView.OnItemClickListener*/ {

    private BaseDeDatos mDbHelper;
    private ArrayList<Nota> lista_de_notas;
    private AdaptadorCustom adaptador;
    private NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        mDbHelper = new BaseDeDatos(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] projection = {
                Contrato.Notas._ID,
                Contrato.Notas.TITULO,
                Contrato.Notas.INFO,
                Contrato.Notas.ENCENDIDO,
                Contrato.Notas.FECHA
        };

        Cursor c = db.query(
                Contrato.Notas.TABLA,                     // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
        lista_de_notas = new ArrayList<Nota>();
        for(boolean f = c.moveToFirst();f!=false;f=c.moveToNext()) {

            Nota nueva_nota = new Nota(
                    c.getLong(c.getColumnIndexOrThrow(Contrato.Notas._ID)),
                    c.getString(c.getColumnIndexOrThrow(Contrato.Notas.TITULO)),
                    c.getString(c.getColumnIndexOrThrow(Contrato.Notas.INFO)),
                    c.getLong(c.getColumnIndexOrThrow(Contrato.Notas.FECHA)),
                    c.getShort(c.getColumnIndexOrThrow(Contrato.Notas.ENCENDIDO))
            );
            lista_de_notas.add(nueva_nota);

        }
        adaptador = new AdaptadorCustom(this,lista_de_notas);
        ListView lista = (ListView)findViewById(R.id.listaPrincipal);
        lista.setAdapter(adaptador);

        notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        for(Nota nota_tmp:lista_de_notas) {
            if(nota_tmp.getEstado()) {
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(this)
                                .setSmallIcon(R.drawable.ic_note)
                                .setContentTitle(nota_tmp.getTitulo())
                                .setContentText(nota_tmp.getInfo())
                                .setOngoing(true);

                notificationManager.notify((int)nota_tmp.getID(),mBuilder.build());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nueva_nota, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.accion_addNota:
                FragmentManager fm = getSupportFragmentManager();
                dialogo_nota nueva_nota = new dialogo_nota();
                nueva_nota.show(fm,"Nueva nota");
                break;
        }
        return true;
    }

    @Override
    public void enviarDatos(String titulo, String info) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Contrato.Notas.TITULO, titulo);
        values.put(Contrato.Notas.INFO, info);

        long newRowId = db.insert(Contrato.Notas.TABLA, null, values);
        long timestamp = System.currentTimeMillis() / 1000L;
        Nota nota_tmp = new Nota(newRowId,titulo,info,timestamp,(short)0);
        lista_de_notas.add(nota_tmp);
        adaptador.notifyDataSetChanged();
    }

    @Override
    public void actualizarSwitch(long id, short estado, String titulo, String info) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(Contrato.Notas.ENCENDIDO, estado);
        String selection = Contrato.Notas._ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(id) };

        db.update(
                Contrato.Notas.TABLA,
                values,
                selection,
                selectionArgs);
        adaptador.notifyDataSetChanged();
        if(estado==1) {
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.ic_note)
                            .setContentTitle(titulo)
                            .setContentText(info)
                            .setOngoing(true);

            notificationManager.notify((int)id,mBuilder.build());
        }
        else {
            notificationManager.cancel((int)id);
        }
    }

    @Override
    public void eliminarNota(long id, int position) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        String selection = Contrato.Notas._ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(id) };
        db.delete(Contrato.Notas.TABLA, selection, selectionArgs);
        lista_de_notas.remove(position);
        adaptador.notifyDataSetChanged();
    }

}
