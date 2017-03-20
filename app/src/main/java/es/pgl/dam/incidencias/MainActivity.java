package es.pgl.dam.incidencias;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    //métodos que abren las activities.
    public void altaHandling(View v){
        Intent i=new Intent(this,Handling.class);
        startActivity(i);
    }
    public void altaCompania(View v){
        Intent i=new Intent(this,Companias.class);
        startActivity(i);
    }
    public void Incidencias(View v){
        Intent i=new Intent(this,Incidencia.class);
        startActivity(i);
    }
    public void pendientes2(View v){
        Intent i=new Intent(this,Pendientes.class);
        startActivity(i);
    }
    public void todo(View v){
        Intent i=new Intent(this,Todo.class);
        startActivity(i);
    }
    public void tecnicos(View v){
        Intent i=new Intent(this,Tecnicos.class);
        startActivity(i);
    }
}
