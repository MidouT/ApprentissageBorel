package univ.m2acdi.apprentissageborel.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import univ.m2acdi.apprentissageborel.R;
import univ.m2acdi.apprentissageborel.util.BMObject;
import univ.m2acdi.apprentissageborel.util.Util;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListenSpeakOutFragment extends Fragment {

    private static int index = 0;

    private TextView textView;
    private ImageView imageView;

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

        textView = view.findViewById(R.id.word_text_view);
        textView.setText(bmObject.getSon());

        imageView = view.findViewById(R.id.word_img_view);
        imageView.setImageDrawable(getImageViewByName(bmObject.getGeste()));

        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityIntent = getActivity().getIntent();
        jsonArray = Util.getJsonArrayDataFromIntent(activityIntent, "jsonArray");
        readNextWord();
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

    public void readNextWord() {

        bmObject = Util.readNextWord(jsonArray, index);
        index++;
        if (index == jsonArray.length()) {
            index = 0;
        }
    }

    /**
     * Récupère une image (Objet Drawable)
     *
     * @param geste
     * @return
     */
    private Drawable getImageViewByName(String geste) {

        Context context = getActivity().getApplicationContext();

        int image_id = context.getResources().getIdentifier(geste, "drawable", getActivity().getPackageName());

        return context.getResources().getDrawable(image_id);
    }
}
