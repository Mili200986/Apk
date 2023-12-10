package com.karen.apkagroagca;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    daoContacto dao;
    Adaptador adapter;
    ArrayList<Contacto> lista;
    Contacto c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dao=new daoContacto(this);
        lista=dao.verTodos();
        adapter=new Adaptador(this,lista,dao);
        ListView list=(ListView) findViewById(R.id.lista);
        Button agregar=(Button)findViewById(R.id.agregar);
        if(lista!=null && lista.size()>0){
            list.setAdapter(adapter);
        }
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Dialogo para ver vista previa de registro vista.xml

            }
        });
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Dialogo de agregar dialogo.xml
                Dialog dialogo=new Dialog(MainActivity.this);
                dialogo.setTitle("Nuevo resgistro");
                dialogo.setCancelable(true);
                dialogo.setContentView(R.layout.dialogo);
                dialogo.show();
                final EditText nombre=(EditText) dialogo.findViewById(R.id.nombre);
                final EditText apellido=(EditText) dialogo.findViewById(R.id.apellido);
                final EditText direccion=(EditText) dialogo.findViewById(R.id.direccion);
                final EditText telefono=(EditText) dialogo.findViewById(R.id.telefono);
                Button guardar=(Button) dialogo.findViewById(R.id.d_agregar);
                guardar.setText("Agregar");
                Button cancelar=(Button) dialogo.findViewById(R.id.d_cancelar);
                guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try{
                            c=new Contacto(nombre.getText().toString(),
                                    apellido.getText().toString(),
                                    direccion.getText().toString(),
                                    telefono.getText().toString());

                            dao.insertar(c);
                            lista=dao.verTodos();
                            adapter.notifyDataSetChanged();
                            dialogo.dismiss();

                        }catch (Exception e){
                            Toast.makeText(getApplication(),"ERROR",Toast.LENGTH_SHORT).show();;
                        }

                    }
                });
                cancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogo.dismiss();

                    }
                });

            }
        });
    }
}