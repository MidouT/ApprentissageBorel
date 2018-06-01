package univ.m2acdi.apprentissageborel.fragment;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;

import univ.m2acdi.apprentissageborel.R;
import univ.m2acdi.apprentissageborel.activity.TextToSpeechActivity;
import univ.m2acdi.apprentissageborel.util.BMObject;
import univ.m2acdi.apprentissageborel.util.Util;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListenSpeakOutFragment extends Fragment {

    private static int index = 0;

    private TextView tvSon;
    private TextView tvTextRef;
    private ImageView ivGeste;

    private ImageView nextButton;
    private ImageView previousButton;

    private BMObject bmObject;
    private static JSONArray jsonArray;

    private Intent activityIntent;


    public ListenSpeakOutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_listen_speak_out, container, false);

        tvSon = view.findViewById(R.id.word_text_view);
        tvTextRef = view.findViewById(R.id.text_ref_view);
        ivGeste = view.findViewById(R.id.word_img_view);


        nextButton = view.findViewById(R.id.btn_next_right);
        nextButton.setOnClickListener(onNextBtnClickListener);

        previousButton = view.findViewById(R.id.btn_next_left);
        previousButton.setOnClickListener(onNextBtnClickListener);

        ((TextToSpeechActivity)getActivity()).createNewSpeechTask();

        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityIntent = getActivity().getIntent();
        jsonArray = Util.getJsonArrayDataFromIntent(activityIntent, "jsonArray");
    }



    @Override
    public void onStart() {
        super.onStart();
        readNextWord();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    View.OnClickListener onNextBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int id = view.getId();

            switch (id){
                case R.id.btn_next_right:
                    if(index == jsonArray.length()-1){
                        index = 0;
                    }else {
                        index++;
                    }
                    break;

                case R.id.btn_next_left:
                    if(index == 0){
                        index = jsonArray.length() -1;
                    }else {
                        index--;
                    }
                    break;

                    default:
                        break;
            }

            readNextWord();
            ((TextToSpeechActivity)getActivity()).setStepSuccessButtonVisibility();
            ((TextToSpeechActivity)getActivity()).createNewSpeechTask();
        }
    };

    /**
     * Lis l'objet situé en position index
     */
    public void readNextWord() {

        bmObject = Util.readNextWord(jsonArray, index);

        tvSon.setText(bmObject.getSon());
        tvTextRef.setText(bmObject.getTexte_ref());
        ivGeste.setImageDrawable(Util.getImageViewByName(getActivity().getApplicationContext(), bmObject.getGeste()));

    }

}
