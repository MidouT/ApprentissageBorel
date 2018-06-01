package univ.m2acdi.apprentissageborel.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import univ.m2acdi.apprentissageborel.R;
import univ.m2acdi.apprentissageborel.activity.TrainSectionActivity;
import univ.m2acdi.apprentissageborel.listener.AdminConfigListener;
import univ.m2acdi.apprentissageborel.util.Constante;
import univ.m2acdi.apprentissageborel.util.ExerciseObject;
import univ.m2acdi.apprentissageborel.util.ExerciseObjectAdapter;
import univ.m2acdi.apprentissageborel.util.Util;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrainSectionFragment extends Fragment {

    private ListView lvExoList;
    private Button btnAddNew;

    private ArrayList<ExerciseObject> exoObjectList;
    private ExerciseObjectAdapter adapter;

    private AdminConfigListener adminConfigListener;


    public TrainSectionFragment() {
        // Required empty public constructor
    }

    public static TrainSectionFragment newInstance() {

        Bundle args = new Bundle();

        TrainSectionFragment fragment = new TrainSectionFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_train_section, container, false);

        btnAddNew = view.findViewById(R.id.addNewExo_btn);
        btnAddNew.setOnClickListener(onAddNewBtnClickListener);

        lvExoList = view.findViewById(R.id.exo_listView);
        registerForContextMenu(lvExoList);

        exoObjectList = new ArrayList<>();
        adapter = new ExerciseObjectAdapter(getActivity().getApplicationContext(), R.layout.exo_item_row, exoObjectList);

        lvExoList.setAdapter(adapter);

        adapter.addAll(getAllExerciseData());

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof AdminConfigListener) {
            adminConfigListener = (AdminConfigListener) activity;
        } else {
            throw new ClassCastException(activity.toString() + " don't implement appropriate listener");
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.data_config_listView) {
            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.context_menu, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()) {
            case R.id.edit:
                ExerciseObject selectedObject = (ExerciseObject) lvExoList.getItemAtPosition(info.position);
                ((TrainSectionActivity)getActivity()).updateExerciseObject(selectedObject, info.position);
                return true;
            case R.id.delete:
                exoObjectList.remove(info.position);
                adapter.notifyDataSetChanged();
                writeItems();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }



    View.OnClickListener onAddNewBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            adminConfigListener.onAddNewBtnClicked(view);
        }
    };

    /**
     * Ajout un item à la listView
     * @param exerciseObject
     */
    public void addNewExoObject(ExerciseObject exerciseObject, int position){
        // position vaut toujours -1 si c'est un novelle objet
        if(position >= 0){
            exoObjectList.remove(position);
            exoObjectList.add(position, exerciseObject);
            adapter.notifyDataSetChanged();
            writeItems();
        }else {
            if (!exerciseObject.getMot().isEmpty() && !exerciseObject.getAllographe().isEmpty()){
                adapter.add(exerciseObject);
                writeItems();
            }else {
                Toast.makeText(getActivity().getApplicationContext(), "Données vides", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Lis le fichier de données et renvoie une liste d'objet (ExerciseObject)
     * @return
     */
    private ArrayList<ExerciseObject> getAllExerciseData(){
        ArrayList<ExerciseObject> objectArrayList = new ArrayList<>();
        JSONArray jsonArray = Util.readJsonDataFile(getActivity().getApplicationContext(), Constante.EXERCISE_FILE_NAME);
        for(int i = 0; i < jsonArray.length(); i++){
            ExerciseObject exerciseObject = new ExerciseObject();
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                exerciseObject.setSon(jsonObject.getString("son"));
                exerciseObject.setAllographe(jsonObject.getString("graphie"));
                exerciseObject.setMot(jsonObject.getString("mot"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            objectArrayList.add(exerciseObject);
        }

        return objectArrayList;
    }

    public void writeItems() {
        String data = Util.convertExoOBjectListToJSonArray(exoObjectList);
        Util.writeJsonDataFile(getActivity().getApplicationContext(), data, Constante.EXERCISE_FILE_NAME);
    }

}
