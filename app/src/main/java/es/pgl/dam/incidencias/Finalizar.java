package es.pgl.dam.incidencias;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Finalizar extends AppCompatActivity {
    //creamos las variables que usaremos.
    private Spinner spinner2, spinner3;
    private EditText hora, min, sol;
    private TextView tv5;
    String id="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalizar);
        //inicializamos las variables.
        tv5=(TextView) findViewById(R.id.textView5);
        hora=(EditText) findViewById(R.id.hora);
        min=(EditText) findViewById(R.id.min);
        sol=(EditText) findViewById(R.id.sol);

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "incidencias", null, 1);
        SQLiteDatabase bd = admin.getReadableDatabase();
        String valor=getIntent().getStringExtra("spinner");
        int valor2=Integer.parseInt(valor);
        Cursor fila1 = bd.rawQuery("select * from incidencia where _idinc="+valor2, null);
        if(fila1.moveToFirst()) {
            do {
                tv5.append("Número incidencia: "+fila1.getString(0) + " \n Fecha y hora inicio: " + fila1.getString(1)+
                        " " + fila1.getString(2)+ " \n Lugar: " + fila1.getString(5) + " \n Número mostrador: "
                        + fila1.getString(6)+" \n Descripción: " + fila1.getString(7)+"\n"+ "\n Técnico: "+fila1.getString(11));
                tv5.append("\n \n");
            } while (fila1.moveToNext());
        }
        //Listamos los handlings.
        ArrayList<String> handling = new ArrayList<>();

        Cursor fila = bd.rawQuery("select * from handling", null);
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list = new ArrayList<String>();
        for (int i=0;fila.moveToNext();i++) {
            list.add(fila.getString(1));

            ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, R.layout.spinner, list);

            spinner3.setAdapter(adaptador);
        }

        //Listamos las compañías
        ArrayList<String> compania = new ArrayList<>();

        AdminSQLiteOpenHelper admin2 = new AdminSQLiteOpenHelper(this, "incidencias", null, 1);
        SQLiteDatabase bd2 = admin2.getReadableDatabase();
        Cursor fila2 = bd2.rawQuery("select * from compania", null);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        List<String> list2 = new ArrayList<String>();
        for (int i=0;fila2.moveToNext();i++) {
            list2.add(fila2.getString(1));

            ArrayAdapter<String> adaptador2 = new ArrayAdapter<String>(this, R.layout.spinner, list2);

            spinner2.setAdapter(adaptador2);
        }

    }
    public void fin(View v){

        // obtenemos fecha y hora.
        Date horaActual=new Date();
        String hora2="";
        String fecha=(horaActual.getYear()+1900)+"-"+horaActual.getMonth()+1+"-"+horaActual.getDate();
        if(hora.getText().toString().isEmpty() && min.getText().toString().isEmpty()) {
            hora2= horaActual.getHours()+":"+horaActual.getMinutes()+":"+horaActual.getSeconds();
        }else{
            hora2 = hora.getText().toString() + ":" + min.getText().toString() + ":00";
        }
        //obtenemos la solución:
        String solucion=sol.getText().toString();

        //ahora procedemos a actualizar el registro.
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "incidencias", null, 1);
        SQLiteDatabase bd = admin.getReadableDatabase();
        String valor=getIntent().getStringExtra("spinner");
        int valor2=Integer.parseInt(valor);

        SQLiteDatabase bd2 = admin.getWritableDatabase();
        ContentValues registro=new ContentValues();
        registro.put("handling",spinner3.getSelectedItem().toString());
        registro.put("compa",spinner2.getSelectedItem().toString());
        registro.put("fechafin",fecha);
        registro.put("horafin",hora2);
        registro.put("solucion",solucion);
        bd2.update("incidencia",registro,"_idinc="+valor2,null);

        Toast.makeText(this, "Incidencia Finalizada correctamente", Toast.LENGTH_SHORT).show();
        finish();
        bd.close();
    }
    //cerramos el activity.
    public void volver(View v){
        finish();
    }
}
