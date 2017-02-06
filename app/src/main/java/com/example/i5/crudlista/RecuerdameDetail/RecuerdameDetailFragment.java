package com.example.i5.crudlista.RecuerdameDetail;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.i5.crudlista.R;
import com.example.i5.crudlista.addeditRecuerdame.AddEditRecuerdameActivity;
import com.example.i5.crudlista.data.Recuerdame;
import com.example.i5.crudlista.data.RecuerdameDbHelper;
import com.example.i5.crudlista.Recuerdame.MainActivity;
import com.example.i5.crudlista.Recuerdame.RecuerdameFragment;
import com.squareup.picasso.Picasso;

public class RecuerdameDetailFragment extends Fragment {
    private static final String ARG_RECUERDO_ID = "recuerdoId";
    private String mRecuerdoId;

    private CollapsingToolbarLayout mCollapsingView;
    private ImageView mAvatar;
    private TextView mTitle;
    private TextView mDescription;

    private RecuerdameDbHelper mRecuerdosDbHelper;

    public RecuerdameDetailFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static RecuerdameDetailFragment newInstance(String recuerdoId) {
        RecuerdameDetailFragment fragment = new RecuerdameDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_RECUERDO_ID, recuerdoId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRecuerdoId = getArguments().getString(ARG_RECUERDO_ID);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_recuerdame_detail, container, false);
        //Aqui pasamos en nombre del titulo del toolbar
        //en su caso es el titulo del recuerdo
        //ejemplo: terminar tesis, sacar basura
        mCollapsingView = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);

        mAvatar = (ImageView) getActivity().findViewById(R.id.iv_avatar_detail);
        mDescription = (TextView) root.findViewById(R.id.tv_description);

        mRecuerdosDbHelper = new RecuerdameDbHelper(getActivity());

        loadRecuerdame();

        return root;
    }

    private void loadRecuerdame() {
        new GetRecuerdoByIdTask().execute();
    }

    //Borrar y editar
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_edit:
                //Toast.makeText(getApplicationContext(),"Editar",Toast.LENGTH_SHORT).show();
                showEditScreen();
                break;
            case R.id.action_delete:
                //Toast.makeText(getApplicationContext(),"delete",Toast.LENGTH_SHORT).show();
                new DeleteRecuerdoTask().execute();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Acciones
        if (requestCode == RecuerdameFragment.REQUEST_UPDATE_DELETE_RECUERDAME) {
            if (resultCode == Activity.RESULT_OK) {
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();
            }
        }
    }

    private void showRecuerdame(Recuerdame recuerdame) {
        mCollapsingView.setTitle(recuerdame.getmTitle());
        Picasso.with(getContext())
                .load(R.drawable.background_recuedame_detail)
                .fit()
                .into(mAvatar);
        mDescription.setText(recuerdame.getmDescription());
    }

    private void showEditScreen() {
        Intent intent = new Intent(getActivity(), AddEditRecuerdameActivity.class);
        intent.putExtra(MainActivity.EXTRA_RECUERDAME_ID, mRecuerdoId);
        startActivityForResult(intent, RecuerdameFragment.REQUEST_UPDATE_DELETE_RECUERDAME);
    }

    private void showRecuerdameScreen(boolean requery) {
        if (!requery) {
            showDeleteError();
        }
        getActivity().setResult(requery ? Activity.RESULT_OK : Activity.RESULT_CANCELED);
        getActivity().finish();
    }

    private void showLoadError() {
        Toast.makeText(getActivity(),
                "Error al cargar información", Toast.LENGTH_SHORT).show();
    }

    private void showDeleteError() {
        Toast.makeText(getActivity(),
                "Error al eliminar tu recuerdo", Toast.LENGTH_SHORT).show();
    }

    private class GetRecuerdoByIdTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mRecuerdosDbHelper.getRecuerdameById(mRecuerdoId);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showRecuerdame(new Recuerdame(cursor));
            } else {
                showLoadError();
            }
        }

    }

    private class DeleteRecuerdoTask extends AsyncTask<Void, Void, Integer>{

        @Override
        protected Integer doInBackground(Void... params) {
            return mRecuerdosDbHelper.deleteRecuerdo(mRecuerdoId);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            showRecuerdameScreen(integer > 0);
        }
    }
}
