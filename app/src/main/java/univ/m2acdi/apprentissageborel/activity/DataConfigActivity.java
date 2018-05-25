package univ.m2acdi.apprentissageborel.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import univ.m2acdi.apprentissageborel.R;
import univ.m2acdi.apprentissageborel.fragment.DataListFragment;
import univ.m2acdi.apprentissageborel.fragment.NewBMObjectFragment;
import univ.m2acdi.apprentissageborel.listener.AdminConfigListener;
import univ.m2acdi.apprentissageborel.util.BMObject;
import univ.m2acdi.apprentissageborel.util.Util;

public class DataConfigActivity extends Activity implements AdminConfigListener{

    private static final int READ_REQUEST_CODE = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_config);

        setFragment(new DataListFragment());
    }


    /**
     * Permet de gérer les transitions de fragment
     * @param fragment
     */
    public void setFragment(Fragment fragment){

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.adminFragmentContainer, fragment, null);
        ft.commit();
    }


    /**
     * Fires an intent to spin up the "file chooser" UI and select an image.
     */

    private ArrayList<BMObject> getAllData() {
        ArrayList<BMObject> objectArrayList = new ArrayList<>();
        JSONArray jsonArray = Util.readJsonDataFile(this, "word_file.json");
        for (int i = 0; i < jsonArray.length(); i++) {
            BMObject bmObject = new BMObject();
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                bmObject.setSon(jsonObject.getString("son"));
                bmObject.setGraphie(jsonObject.getString("graphie"));
                bmObject.setTexte_ref(jsonObject.getString("texte_ref"));
                bmObject.setGeste(jsonObject.getString("geste"));
                bmObject.setAnim("anim");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return objectArrayList;
    }
    public void performFileSearch() {

        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers,
        // it would be "*/*".
        intent.setType("image/*");

        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                //showImage(uri);
            }
        }
    }

    //********************* Redéfinition des méthodes du Listener *********************//
    @Override
    public void onAddNewBtnClicked(View view) {
        //Ajout du fragment pour la création d'un objet
        setFragment(new NewBMObjectFragment());
    }

    @Override
    public void onNewBMObjectCreateBtnClicked() {

    }

    @Override
    public void onFileUploadBtnClicked() {
        performFileSearch();
    }
}
