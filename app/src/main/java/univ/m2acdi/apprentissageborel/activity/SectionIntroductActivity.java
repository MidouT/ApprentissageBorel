package univ.m2acdi.apprentissageborel.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import univ.m2acdi.apprentissageborel.R;
import univ.m2acdi.apprentissageborel.fragment.SectionIntroductFragment;
import univ.m2acdi.apprentissageborel.util.BMObject;
import univ.m2acdi.apprentissageborel.util.TextSpeaker;

public class SectionIntroductActivity extends Activity implements SectionIntroductFragment.SectionStartListener {

    private final int SHORT_DURATION = 1000;
    private int section;

    private TextSpeaker textSpeaker;
    private JSONArray jsonArray ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_introduct);

        section = getIntent().getExtras().getInt("section");
        textSpeaker = (TextSpeaker) getIntent().getSerializableExtra("speaker");

        Toast.makeText(this, "Section" + section, Toast.LENGTH_SHORT).show();

        introductSection();
        jsonArray = readJsonDataFile(this);

    }



    View.OnClickListener readListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //speakOut();
        }
    };

    private void introductSection() {

        String text = "";

        switch (section){
            case 1:
                text = this.getResources().getString(R.string.introduct_section_1);
                break;
            case 2:
                text = this.getResources().getString(R.string.introduct_section_2);
                break;
            case 3:
                text = this.getResources().getString(R.string.introduct_section_3);
                break;

                default:
                    break;
        }

        speakOut(text);
    }

    /**
     *
     * @param text
     */
    protected void speakOut(String text) {
        if (!textSpeaker.isSpeaking()) {
            textSpeaker.speakText(text);
            textSpeaker.pause(SHORT_DURATION);
        }

    }


    @Override
    protected void onStop() {

        super.onStop();
    }

    /**
     * Méthode de lecture du fichier de données
     *
     * Initialise la liste de données (tableau JSON)
     * @param context
     * @return
     */
    public JSONArray readJsonDataFile(Context context){
        String jsonStr;
        try {
            InputStream is = context.getAssets().open("word_file.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonStr = new String(buffer, "UTF-8");
            jsonArray = new JSONArray(jsonStr);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    /**
     * Définition de la méthode du listener
     * @param view
     */
    @Override
    public void onSectionButtonClick(View view) {
        Intent intent = new Intent();
        int section = getIntent().getExtras().getInt("section");
        TextSpeaker textSpeaker = (TextSpeaker)getIntent().getSerializableExtra("speaker");
        intent.putExtra("speaker", textSpeaker);
        intent.putExtra("jsonArray", jsonArray.toString());
        switch (section){
            case 1:
                intent.setClass(this, TextToSpeechActivity.class);
                break;
            case 2:
                intent.setClass(this, GestureToSpeechActivity.class);
                break;
            case 3:
                break;
            default:
                intent.setClass(this, SectionIntroductActivity.class);
                break;
        }
        startActivity(intent);
    }

}
