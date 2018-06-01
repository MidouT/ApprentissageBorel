package univ.m2acdi.apprentissageborel.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import univ.m2acdi.apprentissageborel.R;
import univ.m2acdi.apprentissageborel.activity.TrainSectionActivity;
import univ.m2acdi.apprentissageborel.util.ExerciseObject;
import univ.m2acdi.apprentissageborel.util.Util;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewExerciseObjectFragment extends Fragment {

    private EditText editTextMot;
    private EditText editTextAllographe;
    private EditText editTextSon;
    private Button buttonValider;


    public NewExerciseObjectFragment() {
        // Required empty public constructor
    }

    public static NewExerciseObjectFragment newInstance() {

        Bundle args = new Bundle();

        NewExerciseObjectFragment fragment = new NewExerciseObjectFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_exercise_object, container, false);

        editTextMot = view.findViewById(R.id.edt_mot_id);
        editTextAllographe = view.findViewById(R.id.edt_allographe_id);
        editTextSon = view.findViewById(R.id.edt_son_id);
        buttonValider = view.findViewById(R.id.submit_exo_btn);
        buttonValider.setOnClickListener(onSubmitBtnClckListener);

        return view;
    }


    //Listener pour la validation du formulaire d'exercice
    View.OnClickListener onSubmitBtnClckListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String mot = editTextMot.getText().toString();
            String son = editTextSon.getText().toString();
            String allographe = editTextAllographe.getText().toString();

            ExerciseObject exerciseObject = new ExerciseObject(mot,allographe,son);
            ((TrainSectionActivity)getActivity()).onNewExoObjectCreateBtnClicked(exerciseObject);
        }
    };

    /**
     * Réinitialise les champs à chaque fois que le fragment est réaffiché
     */
    public void initAllField(ExerciseObject exerciseObject){
        if(exerciseObject != null){
            editTextMot.setText(exerciseObject.getMot());
            editTextAllographe.setText(Util.getFormatedGraphieStr(exerciseObject.getAllographe()));
            editTextSon.setText(exerciseObject.getSon());

        }else {
            editTextMot.setText("");
            editTextAllographe.setText("");
            editTextSon.setText("");
        }


    }

}
