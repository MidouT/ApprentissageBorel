package univ.m2acdi.apprentissageborel.fragment;


import android.content.Context;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class ListenSpeakOutFragment extends Fragment {

    private static int index = 0;

    private TextView textView;
    private ImageView imageView;

    private BMObject wordObject;


    public ListenSpeakOutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_listen_speak_out, container, false);

        textView = view.findViewById(R.id.word_text_view);
        textView.setText(wordObject.getSon());

        imageView = view.findViewById(R.id.word_img_view);
        imageView.setImageDrawable(getImageViewByName(wordObject.getGeste()));

        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readNextWord();
    }

    @Override
    public void onStart() {
        super.onStart();
        //String text = textView.getText().toString();
        //((TextToSpeechActivity) getActivity()).speakOutViewText(text);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private JSONArray readJsonWordFile() {

        JSONArray jsonarray = null;

        try {
            InputStream stream = getActivity().getAssets().open("word_file.json");
            int size = stream.available();
            byte[] byteArray = new byte[size];
            stream.read(byteArray);
            stream.close();
            String jsonStr = new String(byteArray);

            jsonarray = new JSONArray(jsonStr);

        } catch (IOException ex) {
            System.out.println("\n \n IOException !\n\n");
            ex.printStackTrace();
        } catch (JSONException ex) {
            System.out.println("\n JSONException !\n\n");
            ex.printStackTrace();
        }
        return jsonarray;
    }

    public void readNextWord() {

        JSONArray jsonArray = readJsonWordFile();
        JSONObject jsonobject;

        String son;
        String graphie;
        String texte_ref;
        String geste;

        try {
            jsonobject = jsonArray.getJSONObject(index);
            son = jsonobject.getString("son");
            graphie = jsonobject.getString("graphie");
            texte_ref = jsonobject.getString("texte_ref");
            geste = jsonobject.getString("geste");

            wordObject = new BMObject(son, graphie, texte_ref, geste);
        } catch (JSONException e) {

        }

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

    public BMObject getWordObject() {
        return wordObject;
    }
}
