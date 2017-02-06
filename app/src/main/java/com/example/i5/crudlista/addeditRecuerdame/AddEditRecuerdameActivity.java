package com.example.i5.crudlista.addeditRecuerdame;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.example.i5.crudlista.Recuerdame.MainActivity;
import com.example.i5.crudlista.R;
import com.example.i5.crudlista.data.Recuerdame;

public class AddEditRecuerdameActivity extends AppCompatActivity {
    public static final int REQUEST_ADD_RECUERDAME = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_recuerdame);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //jff
        String recuerdameId = getIntent().getStringExtra(MainActivity.EXTRA_RECUERDAME_ID);
        setTitle(recuerdameId == null ? "Añadir recuerdo" : "Editar recuerdo");

        AddEditRecuerdameFragment addEditLawyerFragment = (AddEditRecuerdameFragment)
                getSupportFragmentManager().findFragmentById(R.id.content_add_edit_recuerdame);
        if (addEditLawyerFragment == null) {
            addEditLawyerFragment = AddEditRecuerdameFragment.newInstance(recuerdameId);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.content_add_edit_recuerdame, addEditLawyerFragment)
                    .commit();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /*Menu para guardar nuestro recuerdo*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_add_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
