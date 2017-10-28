package me.willhernandezg.personasmaterial;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class CrearPersona extends AppCompatActivity {
    private ImageView foto;
    private EditText txtCedula, txtNombre, txtApellido;
    private TextInputLayout cajaCedula, cajaNombre, cajaApellido;
    private ArrayList<Integer> fotos;
    private Resources res;
    private Spinner sexo;
    private ArrayAdapter<String> adapter;
    private String[] opc;
    private Uri filePath;
    private StorageReference storageReference;
    private AdView adView;
    private InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_persona);

        storageReference = FirebaseStorage.getInstance().getReference();

        foto = (ImageView) findViewById(R.id.fotoCrear);
        txtCedula = (EditText) findViewById(R.id.txtCedula);
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtApellido = (EditText) findViewById(R.id.txtApellido);
        res = this.getResources();
        cajaCedula = (TextInputLayout) findViewById(R.id.cajaCedula);
        cajaNombre = (TextInputLayout) findViewById(R.id.cajaNombre);
        cajaApellido = (TextInputLayout) findViewById(R.id.cajaApellido);
        sexo = (Spinner) findViewById(R.id.cmbSexo);
        opc = res.getStringArray(R.array.sexo);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,opc);
        sexo.setAdapter(adapter);

        adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(res.getString(R.string.id_inter_admod));
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                //super.onAdClosed();
                otroInter();
            }
        });

        otroInter();


    }

    public void otroInter(){
        AdRequest adRequest = new AdRequest.Builder().build();
        interstitialAd.loadAd(adRequest);
    }

    public void guardar(View v){
        if (validar()){
            String id = Datos.getId();
            String foto = id+".jpg";

            Persona p = new Persona(id, foto, txtCedula.getText().toString(), txtNombre.getText().toString(), txtApellido.getText().toString(),sexo.getSelectedItemPosition());
            p.guardar();
            subir_foto(foto);
            Snackbar.make(v, res.getString(R.string.mensaje_guardado), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            limpiar();
        }
    }

    public void limpiar(View v){
        limpiar();
    }

    private void limpiar(){
        foto.setImageDrawable(ResourcesCompat.getDrawable(res,android.R.drawable.ic_menu_gallery,null));
        txtCedula.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        sexo.setSelection(0);
        txtCedula.requestFocus();

        if (interstitialAd.isLoaded()){
            interstitialAd.show();
        }
    }

    public void onBackPressed(){
        finish();
        Intent i = new Intent (CrearPersona.this,Principal.class);
        startActivity(i);
    }

    public boolean validar(){
        if (validar_aux(txtCedula, cajaCedula)) return false;
        else if (validar_aux(txtNombre, cajaNombre)) return false;
        else if (validar_aux(txtApellido, cajaApellido)) return false;
        else if (Metodos.existencia_persona(Datos.obtenerPersonas(),txtCedula.getText().toString())){
            txtCedula.setError(res.getString(R.string.persona_existente_error));
            txtCedula.requestFocus();
            return false;
        }

        return true;
    }

    public boolean validar_aux(TextView t, TextInputLayout ct){
        if (t.getText().toString().isEmpty()){
            t.requestFocus();
            t.setError("No puede ser vacio");
            return true;
        }
        return false;
    }

    public void seleccionar_foto(View v){
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i,res.getString(R.string.mensaje_seleccion)),1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            filePath = data.getData();
            if (filePath != null){
                foto.setImageURI(filePath);
            }
        }
    }

    public void subir_foto(String foto){
        StorageReference chilRef = storageReference.child(foto);
        UploadTask uploadTask = chilRef.putFile(filePath);

    }
}
