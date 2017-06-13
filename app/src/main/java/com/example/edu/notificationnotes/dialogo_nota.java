package com.example.edu.notificationnotes;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class dialogo_nota extends DialogFragment implements View.OnClickListener {

    private Button dismiss,aceptar;
    private EditText d_titulo,d_info;

    public interface pasoDatosListener {
        public void enviarDatos(String titulo, String info);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.fragment_dialogo_nota, container, false);
        getDialog().setTitle(R.string.action_addNote);

        dismiss = (Button) rootView.findViewById(R.id.dialog_rechazar);
        aceptar = (Button) rootView.findViewById(R.id.dialog_aceptar);
        d_titulo = (EditText) rootView.findViewById(R.id.dialogo_titulo);
        d_info = (EditText) rootView.findViewById(R.id.dialogo_info);

        dismiss.setOnClickListener(this);
        aceptar.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.dialog_rechazar:
                dismiss();
                break;

            case R.id.dialog_aceptar:
                String tit = d_titulo.getText().toString();
                String inf = d_info.getText().toString();
                if(tit.length()>0 && inf.length()>0) {
                    pasoDatosListener listener = (pasoDatosListener) getActivity();
                    listener.enviarDatos(tit,inf);
                    dismiss();
                }
                else {
                    Toast.makeText(getActivity(), "Faltan campos", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
