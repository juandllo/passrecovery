package co.com.passrecovery;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import co.com.passrecovery.db.claves.ClavesOperations;
import co.com.passrecovery.model.Claves;
import es.dmoral.toasty.Toasty;

public class CrearClaveActivity extends AppCompatActivity {
    private Button btnGuardar;
    private EditText txtNombreSistema;
    private EditText txtNombreUsuario;
    private EditText txtPass;

    private ClavesOperations clavesData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_clave);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setSubtitle("Crear");

        btnGuardar = (Button) findViewById(R.id.btn_guardar);
        txtNombreSistema = (EditText) findViewById(R.id.app_input_name);
        txtNombreUsuario = (EditText) findViewById(R.id.app_input_user);
        txtPass = (EditText) findViewById(R.id.input_password);

        clavesData = new ClavesOperations(this);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validarForm()) {
                    Toasty.error(view.getContext(), "El formulario no está completo!", Toast.LENGTH_SHORT, true).show();
                } else {
                    Claves clave = new Claves();
                    clave.setNombreServicio(txtNombreSistema.getText().toString());
                    clave.setNombreUsuario(txtNombreUsuario.getText().toString());
                    clave.setPass(txtPass.getText().toString());

                    clavesData.open();
                    clavesData.addClave(clave);

                    Toasty.success(view.getContext(), "La contraseña ha sido almacenada!", Toast.LENGTH_SHORT, true).show();
                    txtNombreSistema.setText("");
                    txtNombreUsuario.setText("");
                    txtPass.setText("");
                }
            }
        });
    }

    private boolean validarForm(){
        boolean retorno = true;

        try {
            if (txtNombreSistema.getText().toString().matches("")) {
                retorno = false;
            } else if (txtNombreUsuario.getText().toString().matches("")) {
                retorno = false;
            } else if (txtPass.getText().toString().matches("")) {
                retorno = false;
            }
        } catch (Exception e) {
            Log.d("CrearClaveActivity", "Error validando formulario: " + e.getMessage());
        }
        return retorno;
    }

}
