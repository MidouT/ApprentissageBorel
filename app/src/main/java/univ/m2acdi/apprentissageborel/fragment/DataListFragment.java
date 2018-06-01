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
import univ.m2acdi.apprentissageborel.activity.DataConfigActivity;
import univ.m2acdi.apprentissageborel.listener.AdminConfigListener;
import univ.m2acdi.apprentissageborel.util.BMObject;
import univ.m2acdi.apprentissageborel.util.BMObjectAdapter;
import univ.m2acdi.apprentissageborel.util.Constante;
import univ.m2acdi.apprentissageborel.util.ExerciseObject;
import univ.m2acdi.apprentissageborel.util.Util;

/**
 * A simple {@link Fragment} subclass.
 */
public class DataListFragment extends Fragment {

    private ListView lvDataList;
    private Button btnAddNew;

    private ArrayList<BMObject> bmObjectList;
    private BMObjectAdapter bmObjectAdapter;

    private AdminConfigListener adminConfigListener;


    public DataListFragment() {
        // Required empty public constructor
    }

    public static DataListFragment newInstance() {

        Bundle args = new Bundle();
        
        DataListFragment fragment = new DataListFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_data_list, container, false);

        btnAddNew = view.findViewById(R.id.data_config_addNew_btn);
        btnAddNew.setOnClickListener(onAddNewBtnClickListener);

        lvDataList = view.findViewById(R.id.data_config_listView);
        registerForContextMenu(lvDataList);

        bmObjectList = new ArrayList<>();
        bmObjectAdapter = new BMObjectAdapter(getActivity().getApplicationContext(), R.layout.data_raw, bmObjectList);

        lvDataList.setAdapter(bmObjectAdapter);

        bmObjectAdapter.addAll(getAllData());

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

    View.OnClickListener onAddNewBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            adminConfigListener.onAddNewBtnClicked(view);
        }
    };

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
                BMObject selectedObject = (BMObject) lvDataList.getItemAtPosition(info.position);
                ((DataConfigActivity)getActivity()).updateBMObject(selectedObject, info.position);
                return true;
            case R.id.delete:
                bmObjectList.remove(info.position);
                bmObjectAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    /**
     * Lis le fichier de données et renvoie une liste d'objet (BMObject)
     * @return
     */
    private ArrayList<BMObject> getAllData(){
        ArrayList<BMObject> objectArrayList = new ArrayList<>();
        JSONArray jsonArray = Util.readJsonDataFile(getActivity().getApplicationContext(), Constante.DATA_FILE_NAME);
        for(int i = 0; i < jsonArray.length(); i++){
            BMObject bmObject = new BMObject();
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                bmObject.setSon(jsonObject.getString("son"));
                bmObject.setGraphie(jsonObject.getString("graphie"));
                bmObject.setTexte_ref(jsonObject.getString("texte_ref"));
                bmObject.setGeste(jsonObject.getString("geste"));
                bmObject.setMotRef(jsonObject.getString("mot_ref"));
                bmObject.setImgMot(jsonObject.getString("img_mot"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            objectArrayList.add(bmObject);
        }

        return objectArrayList;
    }


    /**
     * Ajout un item à la listView
     * @param bmObject
     */
    public void addNewBMObject(BMObject bmObject, int position){
        // position vaut toujours -1 si c'est un novelle objet
        if(position >= 0){
            bmObjectList.remove(position);
            bmObjectList.add(position, bmObject);
            bmObjectAdapter.notifyDataSetChanged();
            writeItems();
        }else {
            if (!bmObject.getSon().isEmpty() && !bmObject.getGraphie().isEmpty()){
                bmObjectAdapter.add(bmObject);
                writeItems();
            }else {
                Toast.makeText(getActivity().getApplicationContext(), "Données vides", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void writeItems() {
        String data = Util.convertBMOBjectListToJSonArray(bmObjectList);
        Util.writeJsonDataFile(getActivity().getApplicationContext(), data, Constante.DATA_FILE_NAME);
    }

}
