package me.willhernandezg.personasmaterial;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EditarPersona extends AppCompatActivity implements RewardedVideoAdListener {
    private ImageView fotoE;
    private EditText txtCedulaE, txtNombreE, txtApellidoE;
    private TextInputLayout cajaCedulaE, cajaNombreE, cajaApellidoE;
    private ArrayList<Integer> fotos;
    private Resources res;
    private Spinner sexoE;
    private int sexo;
    private ArrayAdapter<String> adapter;
    private String[] opcE;
    private Bundle bundle, b3;
    private Intent i;
    private String foto, id ,cedula, nombre, apellido;
    private StorageReference storageReference;
    private Uri filePath;
    private RewardedVideoAd rewardedVideoAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_persona);

        storageReference = FirebaseStorage.getInstance().getReference();
        fotoE = (ImageView) findViewById(R.id.fotoEditar);
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
        id = bundle.getString("id");
        foto = bundle.getString("foto");
        cedula = bundle.getString("cedula");
        nombre = bundle.getString("nombre");
        apellido = bundle.getString("apellido");
        sexo = bundle.getInt("sexo");

        txtCedulaE.setText(cedula);
        txtNombreE.setText(nombre);
        txtApellidoE.setText(apellido);
        sexoE.setSelection(sexo);
        storageReference.child(foto).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(EditarPersona.this).load(uri).into(fotoE);
            }
        });

        rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        rewardedVideoAd.setRewardedVideoAdListener(this);

    }

    public void editar(View v){
        String ced = txtCedulaE.getText().toString();
        String nom = txtNombreE.getText().toString();
        String ape = txtApellidoE.getText().toString();
        Integer sex = sexoE.getSelectedItemPosition();
        Persona p = new Persona(id,foto,ced,nom,ape,sex);
        if (rewardedVideoAd.isLoaded()){
            rewardedVideoAd.show();
        }

        if (cedula.equals(ced)){
            p.editar();
            if (filePath !=null)subir_foto(foto);
            Snackbar.make(v, res.getString(R.string.mensaje_modificar), Snackbar.LENGTH_LONG).setAction("action", null).show();
            Snackbar.make(v, res.getString(R.string.mensaje_modificar), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        }else {
            if(Metodos.existencia_persona(Datos.obtenerPersonas(),ced)){
                txtCedulaE.setError(res.getString(R.string.persona_existente_error));
                txtCedulaE.requestFocus();
            }else{
                p.editar();

                // Cancelar();
            }
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
        Cancelar();
    }

    public void seleccionar_foto(View v){
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i,res.getString(R.string.mensaje_seleccion)),1);
    }

    public void subir_foto(String foto){
        StorageReference chilRef = storageReference.child(foto);
        UploadTask uploadTask = chilRef.putFile(filePath);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Cancelar();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            filePath = data.getData();
            if (filePath != null){
                fotoE.setImageURI(filePath);
            }
        }
    }

    public void Cancelar(){
        finish();
        Intent i = new Intent(EditarPersona.this,Principal.class);
        startActivity(i);

    }

    @Override
    public void onRewardedVideoAdLoaded() {

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {

    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }
}
