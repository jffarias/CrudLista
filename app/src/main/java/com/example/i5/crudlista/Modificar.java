package com.example.i5.crudlista;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Modificar extends AppCompatActivity {
    EditText et_tarea;
    Button btnAgregar;
    int Id=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar);

        et_tarea = (EditText) findViewById(R.id.et_tarea);
        btnAgregar = (Button) findViewById(R.id.btnAgregar);
        Bundle b = this.getIntent().getExtras();
        if(b!=null){
            RecuperarInfo(b.getInt("idModificar"));
        }
    }

    public void ModificarDatos(View v){
        String NombreTarea = et_tarea.getText().toString();
        BaseHelper dbhelper = new BaseHelper(this, "DBAPP", null, 1);
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        if(db != null){
            ContentValues ModificarReg = new ContentValues();
            ModificarReg.put("Nombre", NombreTarea);
            long i = db.update("Tareas", ModificarReg, "Id="+Id,null);
            if(i>0){
                Toast.makeText(this, "Recuerdo Modificado", Toast.LENGTH_SHORT).show();
                super.onBackPressed();
            }
        }
    }

    public void RecuperarInfo(int id){
        BaseHelper dbhelper = new BaseHelper(this, "DBAPP", null, 1);
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        if(db != null) {
            Cursor c = db.rawQuery("select Id, Nombre from Tareas where Id="+id, null);
            try{
                if(c.moveToFirst()){
                    Id = c.getInt(0);
                    et_tarea.setText(c.getString(1));
                }
            }finally {
                c.close();
            }
        }
    }
}
