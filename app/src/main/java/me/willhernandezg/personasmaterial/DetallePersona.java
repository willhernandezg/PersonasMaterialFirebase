package me.willhernandezg.personasmaterial;

import android.content.Intent;
import android.content.res.Resources;
import android.provider.ContactsContract;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

public class DetallePersona extends AppCompatActivity {
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Persona p;
    private String cedula, nombre, apellido;
    private int fot, sexo;
    private Bundle bundle;
    private Intent i;
    private ImageView foto;
    private Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_persona);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        foto = (ImageView) findViewById(R.id.fotoPersona);

        res = this.getResources();

        i = getIntent();
        bundle = i.getBundleExtra("datos");
        fot = bundle.getInt("foto");
        cedula = bundle.getString("cedula");
        nombre = bundle.getString("nombre");
        apellido = bundle.getString("apellido");
        sexo = bundle.getInt("sexo");

        foto.setImageDrawable(ResourcesCompat.getDrawable(res,fot,null));
        collapsingToolbarLayout.setTitle(nombre+" "+apellido);
    }
}
