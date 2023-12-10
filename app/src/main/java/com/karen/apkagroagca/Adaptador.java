package com.karen.apkagroagca;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Adaptador extends BaseAdapter {
    ArrayList<Contacto> lista;
    daoContacto dao;
    Contacto c;
    Activity a;
    int id=0;
    public Adaptador(Activity a, ArrayList<Contacto> lista, daoContacto dao){
        this.lista=lista;
        this.a=a;
        this.dao=dao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Contacto getItem(int i) {
        c=lista.get(i);
        return null;
    }

    @Override
    public long getItemId(int i) {
        c=lista.get(i);
        return 0;
    }

    @Override
    public View getView(int posicion, View view, ViewGroup viewGroup) {
            View v=view;
            if(v==null){
                LayoutInflater li=(LayoutInflater) a.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v=li.inflate(R.layout.item,null);
            }

            c=lista.get(posicion);
        TextView nombre=(TextView) v.findViewById(R.id.t_nombre);
        TextView apellido=(TextView) v.findViewById(R.id.t_apellido);
        TextView direccion=(TextView) v.findViewById(R.id.t_direccion);
        TextView telefono=(TextView) v.findViewById(R.id.t_telefono);
        Button editar=(Button) v.findViewById(R.id.editar);
        Button eliminar=(Button) v.findViewById(R.id.eliminar);
        nombre.setText(c.getNombre());
        apellido.setText(c.getApellido());
        direccion.setText(c.getDireccion());
        telefono.setText(c.getTelefono());
        editar.setTag(posicion);
        eliminar.setTag(posicion);
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Dialogo de editar dialogo.xml
                int pos=Integer.parseInt(v.getTag().toString());
                final Dialog dialogo=new Dialog(a);
                dialogo.setTitle("Editar Registro resgistro");
                dialogo.setCancelable(true);
                dialogo.setContentView(R.layout.dialogo);
                dialogo.show();
                final EditText nombre=(EditText) dialogo.findViewById(R.id.nombre);
                final EditText apellido=(EditText) dialogo.findViewById(R.id.apellido);
                final EditText direccion=(EditText) dialogo.findViewById(R.id.direccion);
                final EditText telefono=(EditText) dialogo.findViewById(R.id.telefono);
                Button guardar=(Button) dialogo.findViewById(R.id.d_agregar);
                guardar.setText("Guardar");
                Button cancelar=(Button) dialogo.findViewById(R.id.d_cancelar);
                c=lista.get(pos);
                setId(c.getId());
                nombre.setText(c.getNombre());
                apellido.setText(c.getApellido());
                direccion.setText(c.getDireccion());
                telefono.setText(c.getTelefono());
                guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try{
                            c=new Contacto(getId(), nombre.getText().toString(),
                                    apellido.getText().toString(),
                                    direccion.getText().toString(),
                                    telefono.getText().toString());

                            dao.editar(c);
                            lista=dao.verTodos();
                            notifyDataSetChanged();
                            dialogo.dismiss();

                        }catch (Exception e){
                            Toast.makeText(a,"ERROR",Toast.LENGTH_SHORT).show();;
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
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Dialogo para confirmar SI/NO
                int pos=Integer.parseInt(v.getTag().toString());
                c=lista.get(pos);
                setId(c.getId());
                AlertDialog.Builder del=new AlertDialog.Builder(a);
                del.setMessage("Seguro que deseas eliminar el usuario?");
                del.setCancelable(false);
                del.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dao.eliminar(getId());
                        lista=dao.verTodos();
                        notifyDataSetChanged();

                    }
                });
                del.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                del.show();

            }
        });

        return v;
    }
}
