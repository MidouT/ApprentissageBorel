package univ.m2acdi.apprentissageborel.activity;

import android.app.Activity;
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

import java.util.ArrayList;

import univ.m2acdi.apprentissageborel.R;
import univ.m2acdi.apprentissageborel.util.BMObject;
import univ.m2acdi.apprentissageborel.util.Util;

import static android.content.ContentValues.TAG;

public class GestureToSpeechActivity extends Activity {

    private TextView textView ;
    private ImageView imageView ;
    private ImageButton imageButton;

    private static int index = 0;
    private static Boolean flag = false;

    private BMObject bmObject;
    private static JSONArray jsonArray;

    private Intent speechRecognizerIntent;
    private SpeechRecognizer speechRecognizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_to_speech);

        jsonArray = Util.getJsonArrayDataFromIntent(getIntent(), "jsonArray");

        bmObject = Util.readNextWord(jsonArray, index);
        textView = findViewById(R.id.word_text_view);
        imageView = findViewById(R.id.word_img_view);
        imageView.setImageDrawable(this.getResources().getDrawable(this.getResources().getIdentifier(bmObject.getGeste(), "drawable", getPackageName())));
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
            if(index == jsonArray.length()){
                index = 0;
            }else{
                ++index;
            }
            textView.setText("");
            bmObject = Util.readNextWord(jsonArray, index);
            imageView.setImageDrawable(getApplicationContext().getResources().getDrawable(getApplicationContext().getResources().getIdentifier(bmObject.getGeste(), "drawable", getPackageName())));
            flag = false;
            imageButton.setEnabled(flag);
        }
    };


    /**
     * Reconnaissance Vocale
     */
    private void initVoiceRecognizer() {
        speechRecognizer = getSpeechRecognizer();
        speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "fr-FR");
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.getPackageName());
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);

    }
    public void startListening(View v) {
        if (speechRecognizer!=null) {
            speechRecognizer.cancel();
        }
        speechRecognizer.startListening(speechRecognizerIntent);
    }

    private SpeechRecognizer getSpeechRecognizer(){
        if (speechRecognizer == null) {
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
            speechRecognizer.setRecognitionListener(new VoiceListener());
        }
        return speechRecognizer;
    }
    // Fin reconnaissance vocale



    /*
        Reconnaissance vocale
     */

    class VoiceListener implements RecognitionListener {

        public void onReadyForSpeech(Bundle params) {}
        public void onBeginningOfSpeech() {}
        public void onRmsChanged(float rmsdB) {}
        public void onBufferReceived(byte[] buffer) {}
        public void onEndOfSpeech() {}

        public void onError(int error) {
            Log.v(TAG, "error "  + error);
        }
        public void onResults(Bundle results) {
            System.out.println("============= Reconnaissance ============");
            ArrayList<String> data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            for(int i = 0; i < data.size(); i++){
                if((data.get(i).equalsIgnoreCase(bmObject.getSon()) || (data.get(i).equals(bmObject.getSon().toUpperCase())))){
                    flag = true;
                    imageButton.setEnabled(flag);
                    textView.setText(bmObject.getSon());
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

}
