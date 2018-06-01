package univ.m2acdi.apprentissageborel.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import univ.m2acdi.apprentissageborel.R;

public class ExerciseObjectAdapter extends ArrayAdapter<ExerciseObject> {

    private int xmlRessoruceID;
    private TextView tvExoWord;
    private TextView tvAllographe;
    private TextView tvSon;


    public ExerciseObjectAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ExerciseObject> exerciseObjects) {
        super(context, resource, exerciseObjects);
        xmlRessoruceID = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view;

        ExerciseObject exerciseObject = getItem(position);

        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(xmlRessoruceID, parent, false);
        }else{
            view = convertView;
        }


        tvExoWord = view.findViewById(R.id.exo_word_textView);
        tvExoWord.setText(exerciseObject.getMot());
        tvAllographe = view.findViewById(R.id.exo_allographe_textView);
        tvAllographe.setText(exerciseObject.getAllographe());
        tvSon = view.findViewById(R.id.exo_son_textView);
        tvSon.setText(exerciseObject.getSon());
        
        return view;
    }
}
