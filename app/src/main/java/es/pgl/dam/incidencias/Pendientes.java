package es.pgl.dam.incidencias;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Pendientes extends AppCompatActivity {
    //creamos las variables que usaremos.
    private Spinner spinner3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendientes);
        //el siguente array tendrá el resultado de la consulta que luego mostraremos en el spinner.
        ArrayList<String> pendiente = new ArrayList<>();

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "incidencias", null, 1);
        SQLiteDatabase bd = admin.getReadableDatabase();
        Cursor fila2 = bd.rawQuery("select * from incidencia where fechafin=''", null);
        spinner3 = (Spinner) findViewById(R.id.spinner);
        List<String> list = new ArrayList<String>();
        //Añadimos al arraylist los resultados.
        for (int i=0;fila2.moveToNext();i++) {
                list.add(fila2.getString(0));

                ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, R.layout.spinner, list);
                //los insertamos en el spinner.
                spinner3.setAdapter(adaptador);
        }
        bd.close();
    }
    //cerrar el activity.
    public void volver(View v){
        finish();
    }
    //Método que nos pasará el número de mostrador que seleccionemos a el activity donde finalizaremos la incidencia.
    public void finalizar2(View v){
        Intent i=new Intent(this,Finalizar.class);
        i.putExtra("spinner", spinner3.getSelectedItem().toString());
        startActivity(i);
        finish();
    }

}
