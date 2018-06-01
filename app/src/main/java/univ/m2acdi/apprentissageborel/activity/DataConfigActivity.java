package univ.m2acdi.apprentissageborel.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import univ.m2acdi.apprentissageborel.R;
import univ.m2acdi.apprentissageborel.fragment.DataListFragment;
import univ.m2acdi.apprentissageborel.fragment.NewBMObjectFragment;
import univ.m2acdi.apprentissageborel.listener.AdminConfigListener;
import univ.m2acdi.apprentissageborel.util.BMObject;
import univ.m2acdi.apprentissageborel.util.ExerciseObject;

public class DataConfigActivity extends Activity implements AdminConfigListener{

    private static final int READ_REQUEST_CODE = 42;

    private static final String DATA_LIST_FRAG_TAG = "DATALIST_FRAG_TAG";
    private static final String NEW_OBJECT_FRAG_TAG ="NEWOBJECT_FRAG_TAG";

    private DataListFragment dataListFragment;
    private NewBMObjectFragment newBmoFragment;

    private FragmentManager fragmentManager;

    private Bitmap bitmap;
    private Uri filePath;

    private static int itemPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_config);

        if(savedInstanceState == null){
            fragmentManager = getFragmentManager();
            dataListFragment = DataListFragment.newInstance();
            newBmoFragment = NewBMObjectFragment.newInstance();
        }

       initAllFragment();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    protected void initAllFragment(){
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.adminFragmentContainer, dataListFragment, DATA_LIST_FRAG_TAG);
        ft.add(R.id.adminFragmentContainer, newBmoFragment, NEW_OBJECT_FRAG_TAG);
        ft.show(dataListFragment);
        ft.hide(newBmoFragment);
        ft.commit();
    }

    /**
     * Affiche le fragment de la liste de de données
     */
    protected void showDataListFragment(){
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if(dataListFragment.isAdded()){
            ft.show(dataListFragment);
        }else {
            ft.add(R.id.adminFragmentContainer, dataListFragment, DATA_LIST_FRAG_TAG);
        }

        if(newBmoFragment.isAdded()){
            ft.hide(newBmoFragment);
        }

        ft.commit();
    }

    /**
     * Affiche le fragment du formulaire de création d'un nouvel objet
     */
    protected void showNewBMObjectFragment(BMObject bmObject, int position){
        FragmentTransaction ft = fragmentManager.beginTransaction();
        itemPosition = position ;
        if(newBmoFragment.isAdded()){
            newBmoFragment.initAllField(bmObject);
            ft.show(newBmoFragment);
        }else {
            ft.add(R.id.adminFragmentContainer, newBmoFragment, NEW_OBJECT_FRAG_TAG);
        }

        if(dataListFragment.isAdded()){
            ft.hide(dataListFragment);
        }

        ft.commit();
    }

    /**
     * Crée un Intent pour déclencher le selecteur de ficher
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
                                 Intent data) {

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            String fileName = newBmoFragment.getObjectGesteStr()+".PNG";
            if (data != null && data.getData() != null) {
                filePath = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    createDirAndSaveImageFile(bitmap, fileName);
                    newBmoFragment.setImgViewGeste(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Méthode permet de sauvegarder une image
     * @param imageToSave
     * @param fileName
     */
    private void createDirAndSaveImageFile(Bitmap imageToSave, String fileName) {

        File direct = new File(getApplicationContext().getFilesDir() + "/images/");

        if (!direct.exists()) {
            direct.mkdirs();
        }

        File file = new File(direct, fileName);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            imageToSave.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //********************* Redéfinition des méthodes du Listener *********************//
    @Override
    public void onAddNewBtnClicked(View view) {
        //On active le fragment de création d'un nouvel objet
        showNewBMObjectFragment(null, -1);
    }

    @Override
    public void onNewBMObjectCreateBtnClicked(BMObject bmObject) {

        if(dataListFragment != null){
            dataListFragment.addNewBMObject(bmObject, itemPosition);
            showDataListFragment();
        }

    }

    @Override
    public void onNewExoObjectCreateBtnClicked(ExerciseObject exerciseObject) {

    }

    @Override
    public void onFileUploadBtnClicked() {
        performFileSearch();
    }

    //Met à jour l'objet indiqué avec sa position
    public void updateBMObject(BMObject bmObject, int position){
        showNewBMObjectFragment(bmObject, position);
    }
}
