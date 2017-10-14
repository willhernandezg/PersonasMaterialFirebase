package me.willhernandezg.personasmaterial;

import android.content.Intent;
import android.content.res.Resources;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class EditarPersona extends AppCompatActivity {
    private EditText txtCedulaE, txtNombreE, txtApellidoE;
    private TextInputLayout cajaCedulaE, cajaNombreE, cajaApellidoE;
    private ArrayList<Integer> fotos;
    private Resources res;
    private Spinner sexoE;
    private int foto, sexo;
    private ArrayAdapter<String> adapter;
    private String[] opcE;
    private Bundle bundle, b3;
    private Intent i;
    private String cedula, nombre, apellido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_persona);

        txtCedulaE = (EditText) findViewById(R.id.txtCedulaE);
        txtNombreE = (EditText) findViewById(R.id.txtNombreE);
        txtApellidoE = (EditText) findViewById(R.id.txtApellidoE);

        res = this.getResources();
        cajaCedulaE = (TextInputLayout) findViewById(R.id.cajaCedulaE);
        cajaNombreE = (TextInputLayout) findViewById(R.id.cajaNombreE);
        cajaApellidoE = (TextInputLayout) findViewById(R.id.cajaApellidoE);
        sexoE = (Spinner) findViewById(R.id.cmbSexoE);
        opcE = res.getStringArray(R.array.sexo);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,opcE);
        sexoE.setAdapter(adapter);

        i = getIntent();
        bundle = i.getBundleExtra("datos");
        foto = bundle.getInt("foto");
        cedula = bundle.getString("cedula");
        nombre = bundle.getString("nombre");
        apellido = bundle.getString("apellido");
        sexo = bundle.getInt("sexo");

        txtCedulaE.setText(cedula);
        txtNombreE.setText(nombre);
        txtApellidoE.setText(apellido);
        sexoE.setSelection(sexo);

    }

    public void editar(View v){
        if (validarE()){
            Persona p = new Persona(foto, txtCedulaE.getText().toString(), txtNombreE.getText().toString(), txtApellidoE.getText().toString(),sexoE.getSelectedItemPosition());
            p.editar();
            Snackbar.make(v, res.getString(R.string.mensaje_guardado), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            onBackPressedE();
        }
    }

    public boolean validarE(){
        if (validar_auxE(txtCedulaE, cajaCedulaE)) return false;
        else if (validar_auxE(txtNombreE, cajaNombreE)) return false;
        else if (validar_auxE(txtApellidoE, cajaApellidoE)) return false;
        else if (Metodos.existencia_persona_editar(Datos.obtenerPersonas(),txtCedulaE.getText().toString(),cedula)) {
            txtCedulaE.setError(res.getString(R.string.persona_existente_error));
            txtCedulaE.requestFocus();
            return false;
        }
        return true;
    }

    public boolean validar_auxE(TextView t, TextInputLayout ct){
        if (t.getText().toString().isEmpty()){
            t.requestFocus();
            t.setError("No puede ser vacio");
            return true;
        }
        return false;
    }

    public void onBackPressedE(){
        Intent i = new Intent(this, DetallePersona.class);
        Bundle b3 = new Bundle();
        b3.putInt("foto",foto);
        b3.putString("cedula",txtCedulaE.getText().toString());
        b3.putString("nombre",txtNombreE.getText().toString());
        b3.putString("apellido",txtApellidoE.getText().toString());
        b3.putInt("sexo",sexoE.getSelectedItemPosition());
        i.putExtra("datos",b3);
        startActivity(i);
    }
}
