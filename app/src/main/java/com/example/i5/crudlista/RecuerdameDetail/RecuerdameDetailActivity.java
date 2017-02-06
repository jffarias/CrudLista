package com.example.i5.crudlista.RecuerdameDetail;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.i5.crudlista.R;
import com.example.i5.crudlista.Recuerdame.MainActivity;
import com.example.i5.crudlista.data.Recuerdame;

public class RecuerdameDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuerdame_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String id = getIntent().getStringExtra(MainActivity.EXTRA_RECUERDAME_ID);

        RecuerdameDetailFragment recuerdameDetailFragment =
                (RecuerdameDetailFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.content_recuerdame_detail);
        if(recuerdameDetailFragment == null){
            recuerdameDetailFragment = RecuerdameDetailFragment.newInstance(id);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.content_recuerdame_detail, recuerdameDetailFragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recuerdame_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
