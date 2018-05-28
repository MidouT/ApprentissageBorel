package univ.m2acdi.apprentissageborel.activity;

import android.app.Activity;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;
import univ.m2acdi.apprentissageborel.R;
import univ.m2acdi.apprentissageborel.util.BMObject;
import univ.m2acdi.apprentissageborel.util.SpeechRecognizeManager;
import univ.m2acdi.apprentissageborel.util.Util;


public class GestureToSpeechActivity extends Activity {

    //private TextView textView ;
    private ImageView imageView ;
    private ImageButton imageButton;
    private GifImageView gifImageView;
    private TextView textUser;

    private static int index = 0;
    private static Boolean flag = false;

    private BMObject bmObject;
    private static JSONArray jsonArray;

    private SpeechRecognizeManager speechRecognizeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_to_speech);

        jsonArray = Util.getJsonArrayDataFromIntent(getIntent(), "jsonArray");

        bmObject = Util.readNextWord(jsonArray, index);
        //textView = findViewById(R.id.word_text_view);
        gifImageView = findViewById(R.id.gifImageView);
        imageView = findViewById(R.id.word_img_view);
        textUser = findViewById(R.id.text_user);
        imageView.setImageDrawable(Util.getImageViewByName(getApplicationContext(), bmObject.getGeste()));
        imageButton = findViewById(R.id.btn_next);

        speechRecognizeManager = new SpeechRecognizeManager(this);

        speechRecognizeManager.initVoiceRecognizer(recognitionListener);
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
            //textView.setText("");
            gifImageView.setImageResource(R.drawable.point);
            textUser.setText("");
            //textView.setCompoundDrawablesWithIntrinsicBounds( R.drawable.point, 0, 0, 0);
            bmObject = Util.readNextWord(jsonArray, index);
            imageView.setImageDrawable(Util.getImageViewByName(getApplicationContext(), bmObject.getGeste()));
            flag = false;
            imageButton.setEnabled(flag);
        }
    };


    /**
     * Méthode pour l'écoute du clic sur le button activant le microphone
     * @param v
     */
    public void startListening(View v) {
        speechRecognizeManager.startListeningSpeech();
    }


    protected RecognitionListener recognitionListener = new RecognitionListener() {
        @Override
        public void onReadyForSpeech(Bundle params) {

        }

        @Override
        public void onBeginningOfSpeech() {

        }

        @Override
        public void onRmsChanged(float rmsdB) {

        }

        @Override
        public void onBufferReceived(byte[] buffer) {

        }

        @Override
        public void onEndOfSpeech() {

        }

        @Override
        public void onError(int error) {

        }

        @Override
        public void onResults(Bundle results) {

            ArrayList<String> data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            for (int i = 0; i < data.size(); i++){
                if(data.get(i).toLowerCase().contains(bmObject.getSon())){
                    textUser.setText(data.get(i));
                    flag = true;
                    imageButton.setEnabled(flag);
                    gifImageView.setImageResource(Util.getRessourceId(getApplicationContext(),bmObject.getAnim()));

                    break;
                }
            }

        }

        @Override
        public void onPartialResults(Bundle partialResults) {

        }

        @Override
        public void onEvent(int eventType, Bundle params) {

        }
    };

}
