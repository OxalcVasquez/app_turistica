package com.vasquez.fernandez.jordan.appturistica;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.vasquez.fernandez.jordan.adapter.LugarTuristicoAdapter;
import com.vasquez.fernandez.jordan.logica.LugarTuristico;
import com.vasquez.fernandez.jordan.utils.Helper;


public class ListadoLugaresFragment extends Fragment {

    RecyclerView rvLugares;
    LugarTuristicoAdapter adapter;

    public ListadoLugaresFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listado_lugares, container, false);

        getActivity().setTitle("Listado lugares");

        rvLugares = view.findViewById(R.id.rv_lugares);
        //Configurar el recycler view
        rvLugares.setHasFixedSize(true);
        rvLugares.setLayoutManager(new LinearLayoutManager(this.getContext()));

        //Enlazar este fragement con el adpatador
        adapter = new LugarTuristicoAdapter(this.getContext());
        //Asignarle al RecyclerView el adaptador que administra el listado
        rvLugares.setAdapter(adapter);
        listar();
        return view;
    }

    private void listar(){
        //Enviar lista de clientes al adaptador
        new LugarTuristico().cargarDatos();
        //Enlazar
        adapter.cargarLugares(LugarTuristico.listadoLugaresTuristicos);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case 1:
                Helper.mensajeConfirmacion(getActivity(),"Desea eliminar el lugar turistico?","SI","NO",new EliminarLugarTask());
                break;
            case 2:
                break;
        }
        return super.onContextItemSelected(item);
    }

    class EliminarLugarTask implements Runnable{

        @Override
        public void run() {
            eliminar();
        }
    }

    private void eliminar(){
        try {
            LugarTuristico lugarTuristico = new LugarTuristico();
            int pos = adapter.posItemSeleccionadoRecyclerView;
            int id = LugarTuristico.listadoLugaresTuristicos.get(pos).getId();
            lugarTuristico.setId(id);
            long r = lugarTuristico.eliminar();
            if (r > 0){
                listar();
                Helper.mensajeInformacion(getContext(),"Lugar turistico","Eliminado correctamente");
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}