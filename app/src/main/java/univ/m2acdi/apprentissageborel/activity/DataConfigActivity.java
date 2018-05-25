package univ.m2acdi.apprentissageborel.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import univ.m2acdi.apprentissageborel.R;
import univ.m2acdi.apprentissageborel.util.BMObject;
import univ.m2acdi.apprentissageborel.util.BMObjectAdapter;
import univ.m2acdi.apprentissageborel.util.Util;

public class DataConfigActivity extends Activity {

    private TextView tvTitle;
    private ListView lvDataList;
    private Button btnAddNew;

    private ArrayList<BMObject> bmObjectList;
    private BMObjectAdapter bmObjectAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_config);

        tvTitle = findViewById(R.id.data_config_view_title);
        btnAddNew = findViewById(R.id.data_config_addNew_btn);
        lvDataList = findViewById(R.id.data_config_listView);

        bmObjectList = new ArrayList<>();
        bmObjectAdapter = new BMObjectAdapter(this, R.layout.data_raw, bmObjectList);

        lvDataList.setAdapter(bmObjectAdapter);

        bmObjectAdapter.addAll(getAllData());
    }

    /**
     * Lis le fichier de données et renvoie une liste d'objet (BMObject)
     * @return
     */
    private ArrayList<BMObject> getAllData(){
        ArrayList<BMObject> objectArrayList = new ArrayList<>();
        JSONArray jsonArray = Util.readJsonDataFile(this, "word_file.json");
        for(int i = 0; i < jsonArray.length(); i++){
            BMObject bmObject = new BMObject();
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                bmObject.setSon(jsonObject.getString("son"));
                bmObject.setGraphie(jsonObject.getString("graphie"));
                bmObject.setTexte_ref(jsonObject.getString("texte_ref"));
                bmObject.setGeste(jsonObject.getString("geste"));
                bmObject.setAnim("anim");
                System.out.println("Son "+i+": "+jsonObject.getString("son"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            objectArrayList.add(bmObject);
        }

        return objectArrayList;

    }
}
