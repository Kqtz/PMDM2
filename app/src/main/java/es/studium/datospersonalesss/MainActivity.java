package es.studium.datospersonalesss;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class MainActivity extends AppCompatActivity {

    private EditText etNombre, etApellidos, etEdad;
    private RadioGroup rgGenero;
    private Spinner spEstadoCivil;
    private Switch switchHijos;
    private TextView tvMensaje;
    private Button btnNotificar, btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar vistas
        etNombre = findViewById(R.id.etNombre);
        etApellidos = findViewById(R.id.etApellidos);
        etEdad = findViewById(R.id.etEdad);
        rgGenero = findViewById(R.id.rgGenero);
        spEstadoCivil = findViewById(R.id.spEstadoCivil);
        switchHijos = findViewById(R.id.switchHijos);
        tvMensaje = findViewById(R.id.tvMensaje);
        btnNotificar = findViewById(R.id.btnNotificar);
        btnReset = findViewById(R.id.btnReset);

        // Rellenar Spinner con valores
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.estado_civil_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEstadoCivil.setAdapter(adapter);

        // Botón para generar la notificación
        btnNotificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validarCampos()) {
                    mostrarInformacionToast(); // Muestra el Toast con la información
                    generarNotificacion(); // Luego genera la notificación
                }
            }
        });

        // Botón para resetear el formulario
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFormulario();
            }
        });
    }

    // Método para validar que los campos obligatorios estén completos
    private boolean validarCampos() {
        String nombre = etNombre.getText().toString().trim();
        String apellidos = etApellidos.getText().toString().trim();
        String edad = etEdad.getText().toString().trim();

        if (nombre.isEmpty() || apellidos.isEmpty() || edad.isEmpty()) {
            tvMensaje.setText(R.string.campo_requerido);
            tvMensaje.setVisibility(View.VISIBLE);
            return false;
        }
        tvMensaje.setVisibility(View.GONE);
        return true;
    }

    // Método para mostrar un Toast con la información del formulario
    private void mostrarInformacionToast() {
        String nombre = etNombre.getText().toString().trim();
        String apellidos = etApellidos.getText().toString().trim();
        String edad = etEdad.getText().toString().trim();
        String genero = ((RadioButton) findViewById(rgGenero.getCheckedRadioButtonId())).getText().toString();
        String estadoCivil = spEstadoCivil.getSelectedItem().toString();
        boolean tieneHijos = switchHijos.isChecked();

        String mensajeToast = "Nombre: " + nombre + "\n" +
                "Apellidos: " + apellidos + "\n" +
                "Edad: " + edad + "\n" +
                "Género: " + genero + "\n" +
                "Estado Civil: " + estadoCivil + "\n" +
                (tieneHijos ? "Tiene hijos" : "No tiene hijos");

        Toast.makeText(this, mensajeToast, Toast.LENGTH_LONG).show();
    }

    // Método para generar la notificación
    private void generarNotificacion() {
        String nombre = etNombre.getText().toString().trim();
        String apellidos = etApellidos.getText().toString().trim();
        String genero = ((RadioButton) findViewById(rgGenero.getCheckedRadioButtonId())).getText().toString();
        String estadoCivil = spEstadoCivil.getSelectedItem().toString();
        boolean tieneHijos = switchHijos.isChecked();

        String mensaje = apellidos + ", " + nombre + ", " +
                (Integer.parseInt(etEdad.getText().toString()) >= 18 ? "mayor" : "menor") +
                " de edad, " + genero + " " + estadoCivil +
                (tieneHijos ? " con hijos." : " sin hijos.");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(mensaje)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }

    // Método para resetear los campos del formulario
    private void resetFormulario() {
        etNombre.setText("");
        etApellidos.setText("");
        etEdad.setText("");
        rgGenero.clearCheck();
        spEstadoCivil.setSelection(0);
        switchHijos.setChecked(false);
        tvMensaje.setVisibility(View.GONE);
 }
}