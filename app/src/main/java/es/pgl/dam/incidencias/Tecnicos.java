package es.pgl.dam.incidencias;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Tecnicos extends AppCompatActivity {
    //creamos las variables que usaremos.
    private EditText et1;
    private TextView textView4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tecnicos);
        //inicializamos las variables.
        et1=(EditText) findViewById(R.id.et1);
        textView4=(TextView) findViewById(R.id.textView4);
        //realizamos la consulta para mostrar los handlings que tenemos en la base de datos
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"incidencias",null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila=bd.rawQuery("select * from tecnicos",null);
        //mostramos el resultado
        if(fila.moveToFirst()) {
            do {
                textView4.append("Código: "+fila.getString(0) + " - Nombre: " + fila.getString(1));
                textView4.append("\n");
            } while (fila.moveToNext());
        }
        bd.close();
    }
    //método para añadir técnicos.
    public void añadirTecnico(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "incidencias", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        //obtenemos el nombre que hemos escrito
        String nombre = et1.getText().toString();
        //insertamos el nombre en la tabla handling.
        ContentValues registro = new ContentValues();
        registro.put("nombre", nombre.toUpperCase());
        bd.insert("tecnicos", null, registro);
        bd.close();
        //dejamos el edittext vacío para insertar otra.
        et1.setText("");
        //mostramos por pantalla que se ha insertado.
        Toast.makeText(this, "Nuevo técnico insertado correctamente", Toast.LENGTH_SHORT).show();
        //cerramos y volvemos a abrir el activity, esto nos servirá para poder ver  el técnico nuevo en el listado.
        finish();
        startActivity(getIntent());
    }
    // método para eliminar el técnico.
    public void borrarTecnico(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "incidencias", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        //obtenemos el codigo que hemos escrito y lo pasamos a entero.
        String codigo = et1.getText().toString();
        int cod=Integer.parseInt(codigo);
        //guardamos en una variable la sentencia de borrado
        int cant=bd.delete("tecnicos","_idtec="+cod,null);
        //cerramos la conexion y vaciamos el edittext.
        bd.close();
        et1.setText("");
        //si la variable obtiene 1 resultado, se muestra un mensaje de que se elimina correctamente y refrescamos la activity.
        if(cant==1) {
            Toast.makeText(this, "Técnico eliminado correctamente.", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(getIntent());
            //sino se muestra por pantalla que el codigo no existe.
        }else{
            Toast.makeText(this, "No existe ningún técnico con ese código, intentelo de nuevo.", Toast.LENGTH_SHORT).show();
        }
    }
    //metodo para cerrar el activity.
    public void volver(View v){
        finish();
    }
}

