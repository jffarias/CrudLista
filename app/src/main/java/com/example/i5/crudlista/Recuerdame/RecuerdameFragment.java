package com.example.i5.crudlista.Recuerdame;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.i5.crudlista.R;
import com.example.i5.crudlista.RecuerdameDetail.RecuerdameDetailActivity;
import com.example.i5.crudlista.addeditRecuerdame.AddEditRecuerdameActivity;
import com.example.i5.crudlista.data.RecuerdameContract.RecuerdameEntry;
import com.example.i5.crudlista.data.RecuerdameDbHelper;

public class RecuerdameFragment extends Fragment {
    public static final int REQUEST_UPDATE_DELETE_RECUERDAME = 2;

    //db
    private RecuerdameDbHelper mRecuerdameDbHelper;

    //lista
    ListView mRecuerdameList;
    RecuerdameCursorAdapter mRecuerdameAdapter;
    private FloatingActionButton mAddButton;


    public RecuerdameFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static RecuerdameFragment newInstance() {
        RecuerdameFragment fragment = new RecuerdameFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recuerdame, container, false);
        // Referencias UI
        mRecuerdameList = (ListView) root.findViewById(R.id.lista);
        mRecuerdameAdapter = new RecuerdameCursorAdapter(getActivity(), null);
        mAddButton = (FloatingActionButton) getActivity().findViewById(R.id.fab_add_recuerdo);
        //setup
        mRecuerdameList.setAdapter(mRecuerdameAdapter);
        //Eventos
        mRecuerdameList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor currentItem = (Cursor) mRecuerdameAdapter.getItem(position);
                String currentRecuerdameId = currentItem.getString(
                        currentItem.getColumnIndex(RecuerdameEntry.ID));

                showDetailScreen(currentRecuerdameId);
            }
        });

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddScreen();
            }
        });

        //instancia db
        mRecuerdameDbHelper = new RecuerdameDbHelper(getActivity());
        // Carga de datos
        loadRecuerdame();
        // Inflate the layout for this fragment
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Activity.RESULT_OK == resultCode) {
            switch (requestCode) {
                case AddEditRecuerdameActivity.REQUEST_ADD_RECUERDAME:
                    showSuccessfullSavedMessage();
                    loadRecuerdame();
                    break;
                case REQUEST_UPDATE_DELETE_RECUERDAME:
                    loadRecuerdame();
                    break;
            }
        }
    }

    private void loadRecuerdame() {
        new RecuerdameLoadTask().execute();
    }

    private void showSuccessfullSavedMessage() {
        Toast.makeText(getActivity(),
                "Recuerdo guardado correctamente", Toast.LENGTH_SHORT).show();
    }

    private void showAddScreen() {
        Intent intent = new Intent(getActivity(), AddEditRecuerdameActivity.class);
        startActivityForResult(intent, AddEditRecuerdameActivity.REQUEST_ADD_RECUERDAME);
    }

    private void showDetailScreen(String recuerdoId) {
        Intent intent = new Intent(getActivity(), RecuerdameDetailActivity.class);
        intent.putExtra(MainActivity.EXTRA_RECUERDAME_ID, recuerdoId);
        startActivityForResult(intent, REQUEST_UPDATE_DELETE_RECUERDAME);
    }

    private class RecuerdameLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mRecuerdameDbHelper.getAllRecuerdame();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                mRecuerdameAdapter.swapCursor(cursor);
            } else {
                // Mostrar empty state
            }
        }
    }
}
