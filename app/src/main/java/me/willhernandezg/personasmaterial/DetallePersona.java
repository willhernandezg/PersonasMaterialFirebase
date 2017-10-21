package me.willhernandezg.personasmaterial;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.provider.ContactsContract;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class DetallePersona extends AppCompatActivity {
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Persona p;
    private String id, cedula, nombre, apellido;
    private int fot, sexo;
    private Bundle bundle;
    private Intent i;
    private ImageView foto;
    private Resources res;
    private TextView ced, nom, ape, sex;
    private String[] opc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_persona);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        ced = (TextView) findViewById(R.id.lblCedulaD);
        nom = (TextView) findViewById(R.id.lblNombreD);
        ape = (TextView) findViewById(R.id.lblApellidoD);
        sex = (TextView) findViewById(R.id.lblSexoD);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        foto = (ImageView) findViewById(R.id.fotoPersona);

        res = this.getResources();
        opc = res.getStringArray(R.array.sexo);

        i = getIntent();
        bundle = i.getBundleExtra("datos");
        id = bundle.getString("id");
        fot = bundle.getInt("foto");
        cedula = bundle.getString("cedula");
        nombre = bundle.getString("nombre");
        apellido = bundle.getString("apellido");
        sexo = bundle.getInt("sexo");

        foto.setImageDrawable(ResourcesCompat.getDrawable(res,fot,null));
        collapsingToolbarLayout.setTitle(nombre+" "+apellido);
        ced.setText(cedula);
        nom.setText(nombre);
        ape.setText(apellido);
        sex.setText(opc[sexo]);
    }

    public void eliminar(View v){
        String positivo, negativo;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(res.getString(R.string.titulo_eliminar_mensaje));
        builder.setMessage(res.getString(R.string.eliminar_mensaje));
        positivo = res.getString(R.string.si_eliminar_mensaje);
        negativo = res.getString(R.string.no_eliminar_mensaje);

        builder.setNegativeButton(negativo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.setPositiveButton(positivo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Persona p = new Persona(id);
                Datos.eliminarPersona(p);
                onBackPressed();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void editar(View v){
        Intent i = new Intent(DetallePersona.this, EditarPersona.class);
        Bundle b2 = new Bundle();
        b2.putString("id",id);
        b2.putInt("foto",fot);
        b2.putString("cedula",cedula);
        b2.putString("nombre",nombre);
        b2.putString("apellido",apellido);
        b2.putInt("sexo",sexo);
        i.putExtra("datos",b2);
        startActivity(i);
    }

    public void onBackPressed(){
        finish();
        Intent i = new Intent (DetallePersona.this,Principal.class);
        startActivity(i);
    }
}
