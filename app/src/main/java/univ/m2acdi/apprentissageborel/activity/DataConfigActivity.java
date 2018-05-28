package univ.m2acdi.apprentissageborel.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import univ.m2acdi.apprentissageborel.R;
import univ.m2acdi.apprentissageborel.fragment.DataListFragment;
import univ.m2acdi.apprentissageborel.fragment.NewBMObjectFragment;
import univ.m2acdi.apprentissageborel.listener.AdminConfigListener;
import univ.m2acdi.apprentissageborel.util.BMObject;

public class DataConfigActivity extends Activity implements AdminConfigListener{

    private static final int READ_REQUEST_CODE = 42;

    private static final String DATA_LIST_FRAG_TAG = "DATALIST_FRAG_TAG";
    private static final String NEW_OBJECT_FRAG_TAG ="NEWOBJECT_FRAG_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_config);

        setFragment(new DataListFragment(), DATA_LIST_FRAG_TAG);
    }


    /**
     * Permet de gérer les transitions de fragment
     * @param fragment
     */
    public void setFragment(Fragment fragment, String tag){

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.adminFragmentContainer, fragment, tag);
        ft.commit();
    }

    /**
     * Fires an intent to spin up the "file chooser" UI and select an image.
     */
    public void performFileSearch() {

        // ACTION_OPEN_DOCUMENT: intent ouvrant l'explorateur de fichier
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        intent.addCategory(Intent.CATEGORY_OPENABLE);
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
        setFragment(new NewBMObjectFragment(), NEW_OBJECT_FRAG_TAG);
    }

    @Override
    public void onNewBMObjectCreateBtnClicked(BMObject bmObject) {
        DataListFragment dataListFragment = (DataListFragment) getFragmentManager().findFragmentByTag(DATA_LIST_FRAG_TAG);
        dataListFragment.addNewBMObject(bmObject);
    }

    @Override
    public void onFileUploadBtnClicked() {
        performFileSearch();
    }
}
