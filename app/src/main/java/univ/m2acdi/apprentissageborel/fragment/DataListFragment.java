package univ.m2acdi.apprentissageborel.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import univ.m2acdi.apprentissageborel.R;
import univ.m2acdi.apprentissageborel.listener.AdminConfigListener;
import univ.m2acdi.apprentissageborel.util.BMObject;
import univ.m2acdi.apprentissageborel.util.BMObjectAdapter;
import univ.m2acdi.apprentissageborel.util.Util;

/**
 * A simple {@link Fragment} subclass.
 */
public class DataListFragment extends Fragment {

    private TextView tvTitle;
    private ListView lvDataList;
    private Button btnAddNew;

    private ArrayList<BMObject> bmObjectList;
    private BMObjectAdapter bmObjectAdapter;

    private AdminConfigListener adminConfigListener;


    public DataListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_data_list, container, false);

        tvTitle = view.findViewById(R.id.data_config_view_title);

        btnAddNew = view.findViewById(R.id.data_config_addNew_btn);
        btnAddNew.setOnClickListener(onAddNewBtnClickListener);

        lvDataList = view.findViewById(R.id.data_config_listView);

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

    /**
     * Lis le fichier de données et renvoie une liste d'objet (BMObject)
     * @return
     */
    private ArrayList<BMObject> getAllData(){
        ArrayList<BMObject> objectArrayList = new ArrayList<>();
        JSONArray jsonArray = Util.readJsonDataFile(getActivity().getApplicationContext(), "word_file.json");
        for(int i = 0; i < jsonArray.length(); i++){
            BMObject bmObject = new BMObject();
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                bmObject.setSon(jsonObject.getString("son"));
                bmObject.setGraphie(jsonObject.getString("graphie"));
                bmObject.setTexte_ref(jsonObject.getString("texte_ref"));
                bmObject.setGeste(jsonObject.getString("geste"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            objectArrayList.add(bmObject);
        }

        return objectArrayList;

    }

}
