package univ.m2acdi.apprentissageborel.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import univ.m2acdi.apprentissageborel.R;
import univ.m2acdi.apprentissageborel.util.BMObject;
import univ.m2acdi.apprentissageborel.util.ExerciseObject;

public class TrainSectionActivity extends Activity {

    private EditText editTextMot;
    private EditText editTextAllographe;
    private EditText editTextSon;
    private TextView textViewErrorMots;
    private TextView textViewErrorAllographe;
    private TextView textViewErrorSon;
    private Button buttonValider;
    private ArrayList<ExerciseObject> exerciseObjectList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_section);
        editTextMot = findViewById(R.id.idValeurMots);
        editTextAllographe = findViewById(R.id.idValeurAllographe);
        editTextSon = findViewById(R.id.idValeurSon);
        textViewErrorMots = findViewById(R.id.idErrorMots);
        textViewErrorAllographe = findViewById(R.id.idErrorAllographe);
        textViewErrorSon = findViewById(R.id.idErrorSon);
        buttonValider = findViewById(R.id.buttonValider);
        buttonValider.setOnClickListener(onSubmitBtnClckListener);

    }

    //Listener pour la validation du formulaire d'exercice
    View.OnClickListener onSubmitBtnClckListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String mots = editTextSon.getText().toString();
            String son = editTextSon.getText().toString();
            String allographe = editTextAllographe.getText().toString();

            ExerciseObject exerciseObject = new ExerciseObject(mots,allographe,son);
            saveNewExerciceObject(exerciseObject);

        }
    };


    public void saveNewExerciceObject(ExerciseObject exerciseObject){

    }


}
