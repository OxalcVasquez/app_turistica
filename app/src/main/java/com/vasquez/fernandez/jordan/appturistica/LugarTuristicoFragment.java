package com.vasquez.fernandez.jordan.appturistica;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class LugarTuristicoFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomMenuLugar;


    public LugarTuristicoFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Título
        getActivity().setTitle("Lugares turisticos");
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_lugar_turistico, container, false);
        // Llamando controles del XML
        bottomMenuLugar = view.findViewById(R.id.bottom_menu_lugares);
        bottomMenuLugar.setOnNavigationItemSelectedListener(this);

        //Por defecto
        this.onNavigationItemSelected(bottomMenuLugar.getMenu().getItem(0));

        return view;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = new Fragment();
        if (id == R.id.opcion_agregar_lugar) {
            fragment = new LugarTuristicoAgregarFragment();
        } else if (id == R.id.opcion_listado_luga){
            fragment = new ListadoLugaresFragment();

        }
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.contenedor_lugares,fragment).commit();
        return true;
    }

}