package co.com.passrecovery;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Style;

import java.util.ArrayList;
import java.util.List;

import co.com.passrecovery.adapters.ClavesAdapter;
import co.com.passrecovery.adapters.RecyclerTouchListener;
import co.com.passrecovery.db.claves.ClavesOperations;
import co.com.passrecovery.model.Claves;
import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {

    private ClavesOperations clavesData;
    private RecyclerView reciclerView;
    private List<Claves> claves;
    private ClavesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        clavesData = new ClavesOperations(this);

        reciclerView = (RecyclerView) findViewById(R.id.recicler_view_pass);
        claves = new ArrayList<>();

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        reciclerView.setLayoutManager(mLayoutManager);
        reciclerView.setItemAnimator(new DefaultItemAnimator());
        reciclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), reciclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = inflater.inflate(R.layout.dialog_detalle_clave, null);

                try {
                    final Claves clave = claves.get(position);
                    TextView usuarioData = (TextView) customView.findViewById(R.id.dialog_data_nombre_usuario);
                    final TextView contraseniaData = (TextView) customView.findViewById(R.id.dialog_data_contrasenia);
                    Switch verPass = (Switch) customView.findViewById(R.id.sw_ver_pass);
                    ImageButton imgClipboard = (ImageButton) customView.findViewById(R.id.img_clipboard);

                    verPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                contraseniaData.setText(clave.getPass());
                            } else {
                                contraseniaData.setText("**********");
                            }
                        }
                    });

                    imgClipboard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ClipData clip = ClipData.newPlainText("text", clave.getPass().toString());
                            ClipboardManager clipboard = (ClipboardManager)getApplicationContext().getSystemService(CLIPBOARD_SERVICE);
                            clipboard.setPrimaryClip(clip);
                            Toasty.info(getApplicationContext(), "Contraseña copiada al portapapeles!", Toast.LENGTH_SHORT, true).show();
                        }
                    });

                    usuarioData.setText(clave.getNombreUsuario());
                    contraseniaData.setText("**********");

                    new MaterialStyledDialog.Builder(view.getContext())
                            .setTitle(clave.getNombreServicio())
                            .withIconAnimation(false)
                            .withDialogAnimation(true)
                            .setCustomView(customView)
                            .setStyle(Style.HEADER_WITH_ICON)
                            .setIcon(R.drawable.ic_action_dialog_pass)
                            .setPositiveText("Aceptar")
                            .autoDismiss(false)
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    dialog.dismiss();
                                }
                            })
                            .show();
                } catch(Exception e) {
                    e.getMessage();
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        llenarRecycler(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CrearClaveActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    private void llenarRecycler(Context context) {
        clavesData = new ClavesOperations(context);
        clavesData.open();
        claves = clavesData.getAllClaves();
        clavesData.close();
        mAdapter = new ClavesAdapter(this, claves);
        reciclerView.setAdapter(mAdapter);
    }
}
