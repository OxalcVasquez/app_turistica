package com.vasquez.fernandez.jordan.appturistica;

import android.app.ActionBar;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.vasquez.fernandez.jordan.datos.Conexion;
import com.vasquez.fernandez.jordan.logica.Categoria;
import com.vasquez.fernandez.jordan.logica.LugarTuristico;
import com.vasquez.fernandez.jordan.utils.Gallery;
import com.vasquez.fernandez.jordan.utils.Helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;


public class LugarTuristicoAgregarFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    MaterialButton btnInicio,btnFin,btnRegistrar,btnFoto;
    AutoCompleteTextView actvCategoria;
    TextInputEditText txtNombre,txtCosto;
    CheckBox chkLunes,chkMartes,chkMiercoles,chkJueves,chkViernes,chkSabado,chkDomingo;
    String dias="";
    ImageView imgLugar;
    int posCategoriaSeleccionada;

    int hora,minuto;



    public LugarTuristicoAgregarFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View view =  inflater.inflate(R.layout.fragment_lugar_turistico_agregar, container, false);

      //Referenciando contexto DB
        Conexion.contextApp = this.getContext();
      //Elementos del xml
      btnInicio = view.findViewById(R.id.btn_inicio);
      btnFin = view.findViewById(R.id.btn_fin);
      btnFoto = view.findViewById(R.id.btn_lugar_foto);
      btnRegistrar = view.findViewById(R.id.btn_registrar);
      actvCategoria = view.findViewById(R.id.actv_lugar_categoria);
      txtCosto = view.findViewById(R.id.txt_costo_ingreso);
      txtNombre = view.findViewById(R.id.txt_lugar_nombre);
      chkLunes = view.findViewById(R.id.chk_lunes);
      chkMartes = view.findViewById(R.id.chk_martes);
      chkMiercoles = view.findViewById(R.id.chk_miercoles);
      chkJueves = view.findViewById(R.id.chk_jueves);
      chkViernes = view.findViewById(R.id.chk_viernes);
      chkSabado = view.findViewById(R.id.chk_sabado);
      chkDomingo = view.findViewById(R.id.chk_domingo);
      imgLugar = view.findViewById(R.id.img_lugar);
        imgLugar.setTag("foto_no_real");

        //Configurar el evento click de los controles
      btnInicio.setOnClickListener(this);
      btnFin.setOnClickListener(this);
      btnRegistrar.setOnClickListener(this);
      btnFoto.setOnClickListener(this);
      imgLugar.setOnClickListener(this);
      actvCategoria.setOnItemClickListener(this);

      //Cargando datos al actvCategorias
        this.cargarCategorias();
      return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == btnInicio.getId()){
            mostrarTimePicker(btnInicio);
        } else if (id == btnFin.getId()) {
            mostrarTimePicker(btnFin);

        } else if (id == btnFoto.getId()) {
           abrirGaleria();
        } else if (id == btnRegistrar.getId()){
            if ( registrar() != -1) {
                Helper.mensajeInformacion(getContext(),"LUGAR TURISTICO","Agregado correctamente");
            }
        }
    }

    private void mostrarTimePicker(MaterialButton btnTiempo){
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int horaSeleccionda, int minutoSelecto) {
                hora = horaSeleccionda;
                minuto = minutoSelecto;
                btnTiempo.setText(String.format(Locale.getDefault(),"%02d:%02d",hora,minuto));
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),onTimeSetListener,hora,minuto,true);
        timePickerDialog.show();
    }

    private void cargarCategorias(){
        String categorias[] = new Categoria().obtenerNombresCategoria();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_list_item_1,categorias);
        actvCategoria.setAdapter(adapter);
    }

    private long registrar(){
        LugarTuristico lugarTuristico = new LugarTuristico();
        lugarTuristico.setNombre(txtNombre.getText().toString());
        Categoria categoria = new Categoria();
        lugarTuristico.setCategoriaId(posCategoriaSeleccionada);
        lugarTuristico.setHorarioAtencion(btnInicio.getText()+"-"+btnFin.getText());
        llenarDias();
        lugarTuristico.setDias(dias);
        lugarTuristico.setCostoIngtreso(Double.parseDouble(txtCosto.getText().toString()));
        if (imgLugar.getTag().equals("foto_real")){
            lugarTuristico.setFoto(
                    Helper.imageToBase64(
                            ((BitmapDrawable) imgLugar.getDrawable()).getBitmap()
                    )
            );
        } else {
            lugarTuristico.setFoto(null);

        }
       return lugarTuristico.agregar();
    }

    private void llenarDias(){
        if (chkLunes.isChecked()) {
            dias = "Lu-";
        }
        if (chkMartes.isChecked()) {
            dias += "Ma-";
        }
        if (chkMiercoles.isChecked()) {
            dias += "Mi-";
        }
        if (chkJueves.isChecked()) {
            dias += "Ju-";
        }
        if (chkViernes.isChecked()) {
            dias += "Vi-";
        }
        if (chkSabado.isChecked()) {
            dias += "Sa-";
        }
        if (chkDomingo.isChecked()) {
            dias += "Do";
        }

        if (dias.charAt(dias.length() - 1) == '-'){
            dias = dias.substring(0,dias.length()-1);
        }

    }
    public static final int REQUEST_PICK = 1;

    private void abrirGaleria() {
        startActivityForResult(new Intent(Intent.ACTION_PICK).setType("image/*"),REQUEST_PICK);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PICK){
            if(resultCode == Activity.RESULT_OK){
                try {
                    Uri rutaImagen = data.getData();
                    Bitmap bitmap = Gallery.rotateImage(getActivity(),rutaImagen,Gallery.getOrientation(getActivity(),rutaImagen));
                    Bitmap bitmapCompress = Gallery.compress(bitmap);
                    imgLugar.setImageBitmap(bitmapCompress);
                    imgLugar.setTag("foto_real");
                } catch (IOException e){
                    e.printStackTrace();
                }

            }
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        posCategoriaSeleccionada = i+1;
    }
}