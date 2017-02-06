package com.example.i5.crudlista.addeditRecuerdame;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.i5.crudlista.R;
import com.example.i5.crudlista.data.Recuerdame;
import com.example.i5.crudlista.data.RecuerdameDbHelper;

import java.security.PrivateKey;

public class AddEditRecuerdameFragment extends Fragment {
    private static final String ARG_RECUERDAME_ID = "arg_recuerdo_id";

    private String mRecuerdameId;

    private RecuerdameDbHelper mRecuerdameDbHelper;

    private TextInputEditText mTitleField;
    private TextInputEditText mDescriptionField;
    private TextInputLayout mTitleLabel;
    private TextInputLayout mDescriptionLabel;

    public AddEditRecuerdameFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AddEditRecuerdameFragment newInstance(String recuerdameId) {
        AddEditRecuerdameFragment fragment = new AddEditRecuerdameFragment();
        Bundle args = new Bundle();
        args.putString(ARG_RECUERDAME_ID, recuerdameId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRecuerdameId = getArguments().getString(ARG_RECUERDAME_ID);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add_edit_recuerdame, container, false);

        // Referencias UI
        mTitleField = (TextInputEditText) root.findViewById(R.id.et_title);
        mDescriptionField = (TextInputEditText) root.findViewById(R.id.et_description);
        mTitleLabel = (TextInputLayout) root.findViewById(R.id.til_title);
        mDescriptionLabel = (TextInputLayout) root.findViewById(R.id.til_description);

        // Eventos
        mRecuerdameDbHelper = new RecuerdameDbHelper(getActivity());

        // Carga de datos
        if (mRecuerdameId != null) {
            loadRecuerdame();
        }

        return root;
    }


    //Metodo para guardar el editar o el agregar un nuevo recuerdo
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.action_save:
                addEditRecuerdame();
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void loadRecuerdame(){
        new GetRecuerdoByIdTask().execute();
    }

    private void addEditRecuerdame() {
        Log.d("Fragment","Guardando recuerdo");
        boolean error = false;

        String titulo = mTitleField.getText().toString();
        String descripcion = mDescriptionField.getText().toString();

        if (TextUtils.isEmpty(titulo)) {
            mTitleLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (TextUtils.isEmpty(descripcion)) {
            mDescriptionLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (error) {
            return;
        }
        Recuerdame recuerdame = new Recuerdame(titulo, descripcion, R.drawable.ic_access_alarms_black_24dp);
        new AddEditRecuerdameTask().execute(recuerdame);
    }

    private void showRecuerdoScreen(Boolean requery) {
        if (!requery) {
            showAddEditError();
            getActivity().setResult(Activity.RESULT_CANCELED);
        } else {
            getActivity().setResult(Activity.RESULT_OK);
        }

        getActivity().finish();
    }

    private void showAddEditError() {
        Toast.makeText(getActivity(),
                "Error al agregar nueva información", Toast.LENGTH_SHORT).show();
    }

    private void showRecuerdo(Recuerdame recuerdame){
        mTitleField.setText(recuerdame.getmTitle());
        mDescriptionField.setText(recuerdame.getmDescription());
    }

    private void showLoadError() {
        Toast.makeText(getActivity(),
                "Error al editar tu recuerdo", Toast.LENGTH_SHORT).show();
    }

    private class GetRecuerdoByIdTask extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected Cursor doInBackground(Void... voids) {
            return mRecuerdameDbHelper.getRecuerdameById(mRecuerdameId);
        }
        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showRecuerdo(new Recuerdame(cursor));
            } else {
                showLoadError();
                getActivity().setResult(Activity.RESULT_CANCELED);
                getActivity().finish();
            }
        }
    }

    private class AddEditRecuerdameTask extends AsyncTask<Recuerdame, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Recuerdame... recuerdames) {
            if (mRecuerdameId != null) {
                return mRecuerdameDbHelper.updateRecuerdame(recuerdames[0], mRecuerdameId) > 0;
            } else {
                return mRecuerdameDbHelper.saveRecuerdame(recuerdames[0]) > 0;
            }
        }
        @Override
        protected void onPostExecute(Boolean result) {
            showRecuerdoScreen(result);
        }

    }
}
