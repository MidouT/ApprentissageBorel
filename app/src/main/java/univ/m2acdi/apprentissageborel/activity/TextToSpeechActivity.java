package univ.m2acdi.apprentissageborel.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import univ.m2acdi.apprentissageborel.R;
import univ.m2acdi.apprentissageborel.util.BMObject;
import univ.m2acdi.apprentissageborel.util.TextSpeaker;

public class TextToSpeechActivity extends AppCompatActivity {

    private final int CHECK_CODE = 0x1;
    private final int SHORT_DURATION = 1000;

    private static int index = 0;

    private TextView textView;
    private ImageView imageView;
    private ImageButton imageButton;

    private TextSpeaker textSpeaker;
    private BMObject wordObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_speech);

        readNextWord();

        textView = findViewById(R.id.word_text_view);
        textView.setText(wordObject.getSon());

        imageView = findViewById(R.id.word_img_view);
        imageView.setImageDrawable(getImageViewByName(wordObject.getGeste()));

        imageButton = findViewById(R.id.btn_next);
        imageButton.setOnClickListener(onClickListener);

        checkTTS();
    }

    /**
     * Vérifier si le périphérique possède les outils nécessaires afin d’utilser la classe TextToSpeech
     */
    private void checkTTS() {
        Intent intentCheck = new Intent();
        intentCheck.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(intentCheck, CHECK_CODE);
    }

    /*
    View.OnClickListener readListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            textToRead = findViewById(R.id.text_to_read);
            String text = textToRead.getText().toString();
            speakOut(text);
        }
    };
    */

    @Override
    protected void onRestart() {
        super.onRestart();
        checkTTS();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CHECK_CODE: {
                if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                    textSpeaker = new TextSpeaker(this);
                } else {
                    Intent install = new Intent();
                    install.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                    startActivity(install);
                }
            }
            default:
                break;
        }
    }

    @Override
    protected void onStop() {
        textSpeaker.destroy();
        super.onStop();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            readNextWord();

            textView = findViewById(R.id.word_text_view);
            textView.setText(wordObject.getSon());

            imageView = findViewById(R.id.word_img_view);
            imageView.setImageDrawable(getImageViewByName(wordObject.getGeste()));
        }
    };

    /**
     * Lecture du texte
     *
     * @param text
     */
    private void speakOut(String text) {
        if (!textSpeaker.isSpeaking()) {
            textSpeaker.speakText(text);
            textSpeaker.pause(SHORT_DURATION);
        }
    }

    private Drawable getImageViewByName(String geste){

        JSONArray jsonArray = readJsonWordFile();

        int image_id = 0;

        Context context = getApplicationContext();

        image_id = context.getResources().getIdentifier(geste, "drawable", getPackageName());
        System.out.println("\n Image ressource id: "+image_id);

        return context.getResources().getDrawable(image_id);
    }

    private JSONArray readJsonWordFile() {

        JSONArray jsonarray = null;

        try {
            InputStream stream = this.getAssets().open("word_file.json");
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

    private String getRandomWordFromBaseFile(){
        String word = "0";
        JSONArray jsonArray = readJsonWordFile();

        try {
            JSONObject jsonobject = jsonArray.getJSONObject(1);
            word = jsonobject.getString("son");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return word;
    }

    public void readNextWord(){

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

        if(index == jsonArray.length()){
            index = 0;
        }

    }

}
