package univ.m2acdi.apprentissageborel.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONArray;

import univ.m2acdi.apprentissageborel.R;
import univ.m2acdi.apprentissageborel.util.Constante;
import univ.m2acdi.apprentissageborel.util.TextSpeaker;
import univ.m2acdi.apprentissageborel.util.Util;

public class SectionIntroductActivity extends Activity {

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
        jsonArray = Util.readJsonDataFile(this, Constante.DATA_FILE_NAME);

    }

    /**
     * Méthode permettant de déterminer le titre de la section a afficher
     */
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
     *
     */
    public void goToSection(){
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
                intent.setClass(this, OrderGestActivity.class);
                break;
            default:
                //intent.setClass(this, SectionIntroductActivity.class);
                break;
        }
        startActivity(intent);
    }

}
