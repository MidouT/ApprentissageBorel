package univ.m2acdi.apprentissageborel.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import univ.m2acdi.apprentissageborel.R;
import univ.m2acdi.apprentissageborel.util.BMObject;

import static android.content.ContentValues.TAG;

public class GestureToSpeechActivity extends Activity {

    private JSONArray jsonArray ;
    private TextView textView ;
    private ImageView imageView ;
    private ImageButton imageButton ;
    private static int indice = 0;
    private SpeechRecognizer speechRecognizer;
    private Intent intent;
    private BMObject bm;
    private Boolean flag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_to_speech);
        jsonArray = readJsonFile(this);
        bm = readWord(indice);
        flag = false;
        textView = findViewById(R.id.word_text_view);
        imageView = findViewById(R.id.word_img_view);
        imageView.setImageDrawable(this.getResources().getDrawable(this.getResources().getIdentifier(bm.getGeste(), "drawable", getPackageName())));
        imageButton = findViewById(R.id.btn_next);
        initVoiceRecognizer();
    }

    @Override
    protected void onStart() {
        super.onStart();
        imageButton.setEnabled(flag);
        imageButton.setOnClickListener(onClickListener);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(indice == jsonArray.length()-1){
                indice = 0;
            }else{
                ++indice;
            }
            textView.setText("");
            bm = readWord(indice);
            imageView.setImageDrawable(getApplicationContext().getResources().getDrawable(getApplicationContext().getResources().getIdentifier(bm.getGeste(), "drawable", getPackageName())));
            flag = false;
            imageButton.setEnabled(flag);
        }
    };

    public BMObject readWord(int indice) {
        JSONObject jsonObject = null;
        BMObject bm = null;

        try {
            jsonObject = jsonArray.getJSONObject(indice);
            String son = jsonObject.getString("son");
            String graphie = jsonObject.getString("graphie");
            String texte_ref = jsonObject.getString("texte_ref");
            String geste = jsonObject.getString("geste");
            bm = new BMObject(son,graphie,texte_ref,geste);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bm;
    }

    /*
       Reconnaissance Vocale
     */

    private void initVoiceRecognizer() {
        speechRecognizer = getSpeechRecognizer();
        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "fr-FR");
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);

    }
    public void startListening(View v) {
        if (speechRecognizer!=null) {
            speechRecognizer.cancel();
        }
        speechRecognizer.startListening(intent);
    }

    private SpeechRecognizer getSpeechRecognizer(){
        if (speechRecognizer == null) {
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
            speechRecognizer.setRecognitionListener(new VoiceListener());
        }
        return speechRecognizer;
    }

    /*
       Fin reconnaissance vocale
     */

    public JSONArray readJsonFile(Context context){
        String json = null;
        try {
            InputStream is = context.getAssets().open("word_file.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            jsonArray = new JSONArray(json);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    /*
        Reconnaissance vocale
     */

    class VoiceListener implements RecognitionListener {
        public void onReadyForSpeech(Bundle params) {}
        public void onBeginningOfSpeech() {}
        public void onRmsChanged(float rmsdB) {}
        public void onBufferReceived(byte[] buffer) {}
        public void onEndOfSpeech() {
            Log.d(TAG, "onEndofSpeech");
        }
        public void onError(int error) {
            Log.v(TAG, "error "  + error);
        }
        public void onResults(Bundle results) {
            String str = new String();
            Log.v(TAG, "onResults " + results);
            System.out.println("============= Reconnaissance ============");
            ArrayList<String> data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            for(int i = 0; i < data.size(); i++){
                if((data.get(i).equalsIgnoreCase(bm.getSon()) || (data.get(i).equals(bm.getSon().toUpperCase())))){
                    flag = true;
                    imageButton.setEnabled(flag);
                    textView.setText(bm.getSon());
                    break;
                }
            }

            for(int i = 0; i < data.size(); i++){
                System.out.println(data.get(i));
            }

        }
        public void onPartialResults(Bundle partialResults) {}
        public void onEvent(int eventType, Bundle params) {}
    }

    /*
        Fin Reconnaissance vocale
     */
}
