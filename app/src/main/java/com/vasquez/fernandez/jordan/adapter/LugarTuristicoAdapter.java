package com.vasquez.fernandez.jordan.adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vasquez.fernandez.jordan.appturistica.R;
import com.vasquez.fernandez.jordan.logica.LugarTuristico;
import com.vasquez.fernandez.jordan.utils.Helper;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class LugarTuristicoAdapter extends  RecyclerView.Adapter<LugarTuristicoAdapter.ViewHolder>{

    private Context context; //Permite relacionar el Adaptador con el fragement donde se mostrara el listado
    private ArrayList<LugarTuristico> listadoLugaresAux;
    public  int posItemSeleccionadoRecyclerView;

    public LugarTuristicoAdapter(Context context) {
        this.context = context;
        listadoLugaresAux = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Permite enlazar el Adaptador(ClienteAdaptador) con el archivo XML que contiene el cardview
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lugares_cardview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LugarTuristico lugarTuristico = listadoLugaresAux.get(position);

        holder.txtNombre.setText(lugarTuristico.getNombre());
        holder.txtCategoria.setText(lugarTuristico.getCategoria());
        holder.txtHorarios.setText(lugarTuristico.getHorarioAtencion());
        holder.txtCosto.setText(String.valueOf(lugarTuristico.getCostoIngtreso()));
        holder.txtDiasAtencion.setText(String.valueOf(lugarTuristico.getDias()));
        if (lugarTuristico.getFoto()==null){
            holder.imgLugar.setImageResource(R.drawable.backgroound_brown);
        } else {
            //Mostrar la foto real grabada en la base de datos
            holder.imgLugar.setImageBitmap(Helper.base64ToImage(lugarTuristico.getFoto()));
        }


    }

    @Override
    public int getItemCount() {
        return listadoLugaresAux.size();
    }

    public void cargarLugares(ArrayList<LugarTuristico> listadoLugares){
        listadoLugaresAux = listadoLugares;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, View.OnLongClickListener {

        //Declarar los controles del cardview
        CircleImageView imgLugar;
        TextView txtCategoria,txtNombre,txtCosto,txtDiasAtencion,txtHorarios;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgLugar = itemView.findViewById(R.id.img_lugar_foto);
            txtNombre = itemView.findViewById(R.id.txt_lugar_nombre);
            txtCategoria = itemView.findViewById(R.id.txt_lugar_categoria);
            txtCosto = itemView.findViewById(R.id.txt_costo_ingreso);
            txtDiasAtencion = itemView.findViewById(R.id.txt_lugar_dias);
            txtHorarios = itemView.findViewById(R.id.txt_lugar_horario);

            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Opciones");
            contextMenu.add(0,1,0,"Eliminar");
            contextMenu.add(0,1,0,"Ver en el mapa");
        }

        @Override
        public boolean onLongClick(View view) {
            posItemSeleccionadoRecyclerView = getAdapterPosition();
            return false;
        }



    }

}
