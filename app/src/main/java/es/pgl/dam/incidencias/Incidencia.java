package es.pgl.dam.incidencias;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Incidencia extends AppCompatActivity {
    //creamos las variables que usaremos.
    private Spinner spinner1;
    private RadioButton fact;
    private RadioButton emb;
    private EditText most, tarjeta, descripcion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incidencia);

        ArrayList<String> handling = new ArrayList<>();

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "incidencias", null, 1);
        SQLiteDatabase bd = admin.getReadableDatabase();
        Cursor fila = bd.rawQuery("select * from tecnicos", null);
        spinner1 = (Spinner) findViewById(R.id.tecnico);
        List<String> list = new ArrayList<String>();
        for (int i=0;fila.moveToNext();i++) {
            list.add(fila.getString(1));

            ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, R.layout.spinner, list);

            spinner1.setAdapter(adaptador);
        }
        //inicializamos las variables.
        fact = (RadioButton) findViewById(R.id.F);
        emb = (RadioButton) findViewById(R.id.P);
        most = (EditText) findViewById(R.id.Most_num);
        tarjeta = (EditText) findViewById(R.id.tarjeta);
        descripcion = (EditText) findViewById(R.id.descripcion);
    }
    //metodo para insertar incidencia.
    public void añadirIncidencia(View v) {
        //obtenemos la hora y fecha local del dispositivo.
        Date horaActual=new Date();

        String fecha=(horaActual.getYear()+1900)+"-"+horaActual.getMonth()+1+"-"+horaActual.getDate();
        String hora= horaActual.getHours()+":"+horaActual.getMinutes()+":"+horaActual.getSeconds();

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "incidencias", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        //almacenamos en variables los datos que vamos a insertar
        String fechinicio = fecha;
        String horainicio = hora;
        String fechafin = "";
        String horafin = null;
        String mostrador = most.getText().toString();
        int mostrador2 = Integer.parseInt(mostrador);
        String tarjeta2 = tarjeta.getText().toString();
        int tarj=Integer.parseInt(tarjeta2);
        String descrip = descripcion.getText().toString();
        String tecnico = spinner1.getSelectedItem().toString();
        String handling = null;
        String sol=null;
        String comp = null;
        String lugar=null;

        if (fact.isChecked() == true) {
            lugar = "F";
        }
        if (emb.isChecked() == true) {
            lugar = "P";
        }
        try {
            //insertamos los valores en la base de datos.
            ContentValues registro = new ContentValues();
            registro.put("fechainicio", fechinicio);
            registro.put("horainicio", horainicio);
            registro.put("fechafin", fechafin);
            registro.put("horafin", horafin);
            registro.put("lugar", lugar);
            registro.put("nummost", mostrador2);
            registro.put("descripcion", descrip);
            registro.put("solucion", sol);
            registro.put("compa", comp);
            registro.put("handling", handling);
            registro.put("tecnico", tecnico);
            registro.put("numtarjeta", tarj);
            bd.insert("incidencia",null,registro);
            //mostramos que se ha insertado y cerramos el activity.
            Toast.makeText(this, "Incidencia añadida correctamente", Toast.LENGTH_SHORT).show();
            finish();
            bd.close();
        }catch(SQLiteException e){e.printStackTrace();}
    }


}
