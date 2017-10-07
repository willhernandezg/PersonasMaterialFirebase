package me.willhernandezg.personasmaterial;

import android.content.Context;
import android.content.res.Resources;
import android.provider.ContactsContract;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by android on 07/10/2017.
 */

public class AdaptadorPersona extends RecyclerView.Adapter<AdaptadorPersona.PersonaViewHolder> {
    private ArrayList<Persona> personas;
    private Resources res;

    public AdaptadorPersona(Context contexto,ArrayList<Persona> personas) {
        this.personas = personas;
        res = contexto.getResources();
    }

    @Override
    public PersonaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_personas,parent,false);
        return  new PersonaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdaptadorPersona.PersonaViewHolder holder, int position) {
        final Persona p = personas.get(position);
        holder.foto.setImageDrawable(ResourcesCompat.getDrawable(res,p.getFoto(),null));
        holder.nombre.setText(p.getNombre());
        holder.apellido.setText(p.getApellido());
    }

    @Override
    public int getItemCount() {
        return personas.size();
    }


    public static class PersonaViewHolder extends RecyclerView.ViewHolder{
        private ImageView foto;
        private TextView nombre;
        private TextView apellido;
        private View v;

        public PersonaViewHolder(View itemView) {
            super(itemView);
            foto = (ImageView) itemView.findViewById(R.id.imgFoto);
            nombre = (TextView) itemView.findViewById(R.id.lblNombre);
            apellido = (TextView) itemView.findViewById(R.id.lblApellido);
        }
    }
}


