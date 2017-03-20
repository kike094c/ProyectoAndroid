package es.pgl.dam.incidencias;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Todo extends AppCompatActivity {
    //creamos las variables que usaremos.
    private TextView tv3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        //inicializamos las variables.
        tv3=(TextView) findViewById(R.id.tv3);
        //hacemos la consulta para luego mostrar las incidencias.
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"incidencias",null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila=bd.rawQuery("select * from incidencia",null);
        //mostramos el resultado.
        if(fila.moveToFirst()) {
            do {
                tv3.append("Número incidencia: "+fila.getString(0) + " \n Fecha y hora inicio: " + fila.getString(1)+" " + fila.getString(2)+
                        " \n Fecha y hora fin: " + fila.getString(3) + " " + fila.getString(4)+" \n Lugar: " + fila.getString(5)
                        + " \n Número mostrador: " + fila.getString(6)+" \n Descripción: " + fila.getString(7)+"\n Solución: "+fila.getString(8)+"\n"+

                        " Compañía: "+fila.getString(9)+" - Handling: "+fila.getString(10)+"\n Técnico: "+fila.getString(11));
                tv3.append("\n \n");
            } while (fila.moveToNext());
        }
        bd.close();
    }
    //cierra el activity.
    public void volver(View v){
        finish();
    }
}
