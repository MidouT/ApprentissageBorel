package univ.m2acdi.apprentissageborel.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import univ.m2acdi.apprentissageborel.R;
import univ.m2acdi.apprentissageborel.fragment.NewExerciseObjectFragment;
import univ.m2acdi.apprentissageborel.fragment.TrainSectionFragment;
import univ.m2acdi.apprentissageborel.listener.AdminConfigListener;
import univ.m2acdi.apprentissageborel.util.BMObject;
import univ.m2acdi.apprentissageborel.util.ExerciseObject;

public class TrainSectionActivity extends Activity implements AdminConfigListener {

    private static final String TRAIN_SECTION_FRAG_TAG = "TRAIN_SECTION_FRAG_TAG";
    private static final String NEW_EXO_OBJECT_FRAG_TAG ="NEW_EXO_OBJECT_FRAG_TAG";

    private static int itemPosition;

    private TrainSectionFragment trainSectionFragment;
    private NewExerciseObjectFragment newExoObjectFragment;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_section);

        if(savedInstanceState == null){
            fragmentManager = getFragmentManager();
            trainSectionFragment = TrainSectionFragment.newInstance();
            newExoObjectFragment = NewExerciseObjectFragment.newInstance();
        }

        initAllFragment();

    }

    protected void initAllFragment(){
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.trainFragmentContainer, trainSectionFragment, TRAIN_SECTION_FRAG_TAG);
        ft.add(R.id.trainFragmentContainer, newExoObjectFragment, NEW_EXO_OBJECT_FRAG_TAG);
        ft.show(trainSectionFragment);
        ft.hide(newExoObjectFragment);
        ft.commit();
    }

    /**
     * Affiche le fragment de la liste de de données
     */
    protected void showTrainSectionFragment(){
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if(trainSectionFragment.isAdded()){
            ft.show(trainSectionFragment);
        }else {
            ft.add(R.id.adminFragmentContainer, trainSectionFragment, TRAIN_SECTION_FRAG_TAG);
        }

        if(newExoObjectFragment.isAdded()){
            ft.hide(newExoObjectFragment);
        }

        ft.commit();
    }

    /**
     * Affiche le fragment du formulaire de création d'un nouvel objet
     */
    protected void showNewExoObjectFragment(ExerciseObject exerciseObject, int position){
        FragmentTransaction ft = fragmentManager.beginTransaction();
        itemPosition = position ;
        if(newExoObjectFragment.isAdded()){
            newExoObjectFragment.initAllField(exerciseObject);
            ft.show(newExoObjectFragment);
        }else {
            ft.add(R.id.adminFragmentContainer, newExoObjectFragment, NEW_EXO_OBJECT_FRAG_TAG);
        }

        if(trainSectionFragment.isAdded()){
            ft.hide(trainSectionFragment);
        }

        ft.commit();
    }



    //Met à jour l'objet indiqué avec sa position
    public void updateExerciseObject(ExerciseObject exerciseObject, int position){
        showNewExoObjectFragment(exerciseObject, position);
    }


    @Override
    public void onAddNewBtnClicked(View view) {
        showNewExoObjectFragment(null, itemPosition);
    }

    @Override
    public void onNewBMObjectCreateBtnClicked(BMObject bmObject) {

    }

    @Override
    public void onNewExoObjectCreateBtnClicked(ExerciseObject exerciseObject) {

        if(trainSectionFragment != null){
            trainSectionFragment.addNewExoObject(exerciseObject, itemPosition);
            showTrainSectionFragment();
        }

    }

    @Override
    public void onFileUploadBtnClicked() {

    }
}
