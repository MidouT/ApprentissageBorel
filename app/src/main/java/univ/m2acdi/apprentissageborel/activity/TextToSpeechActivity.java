package univ.m2acdi.apprentissageborel.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import univ.m2acdi.apprentissageborel.R;
import univ.m2acdi.apprentissageborel.fragment.ListenSpeakOutFragment;
import univ.m2acdi.apprentissageborel.util.SpeechRecognizeManager;
import univ.m2acdi.apprentissageborel.util.TextSpeaker;
import univ.m2acdi.apprentissageborel.util.Util;

import static java.util.concurrent.TimeUnit.SECONDS;

public class TextToSpeechActivity extends Activity {

    private final int SHORT_DURATION = 1000;
    private static int repeatcount;

    private ImageView speechBtnPrompt;
    private ImageView repeatButton;
    private ImageView stepSuccessButton;

    private TextSpeaker textSpeaker;

    private TTSpeechAsyncTask textSpeechTask;
    private SpeechRecognizeManager speechRecognizeManager;

    private ListenSpeakOutFragment lspFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_speech);

        lspFragment = new ListenSpeakOutFragment();
        setFragment(lspFragment);

        repeatcount = 0;

        textSpeaker = (TextSpeaker) getIntent().getSerializableExtra("speaker");
        //textSpeaker.setPitchRate(0.7f);

        speechBtnPrompt = findViewById(R.id.speech_prompt_btn);
        speechBtnPrompt.setOnClickListener(onSpeechPromptBtnClickListener);
        speechBtnPrompt.setVisibility(View.INVISIBLE);

        stepSuccessButton = findViewById(R.id.step_success_btn);
        stepSuccessButton.setVisibility(View.INVISIBLE);

        repeatButton = findViewById(R.id.speech_text_repeat_btn);
        repeatButton.setOnClickListener(onRepeatSpeechBtnClickListener);

        speechRecognizeManager = new SpeechRecognizeManager(this);
        speechRecognizeManager.initVoiceRecognizer(recognitionListener);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {

        textSpeaker.destroy();
        speechRecognizeManager.destroy();

        Intent intent = new Intent(this,MainActivity.class);

        intent.putExtra("back_to_main",true);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        TextToSpeechActivity.this.finish();
        startActivity(intent);
    }



    // ************************* LISTENERS DE BUTTONS *********************************

    View.OnClickListener onSpeechPromptBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            speechBtnPrompt.setImageDrawable(Util.getImageViewByName(getApplicationContext(), "icon_micro_on"));
            speechBtnPrompt.setVisibility(View.VISIBLE);
            speechRecognizeManager.startListeningSpeech();
        }
    };

    View.OnClickListener onRepeatSpeechBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            stepSuccessButton.setVisibility(View.INVISIBLE);
            textSpeechTask = new TTSpeechAsyncTask();
            textSpeechTask.execute();
        }
    };


    /**
     * Gère les transitions de fragment
     * @param fragment
     */
    void setFragment(Fragment fragment) {

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.lspFragmentContainer, fragment, null);
        ft.commit();
    }

    /**
     *
     */
    public void speakOutViewText() {
        TextView textView = findViewById(R.id.word_text_view);
        String text = textView.getText().toString();

        try {
            SECONDS.sleep(1);
            speakOut(text);
            SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    /**
     * Prononce le text passé en paramètre
     * @param text
     */
    protected void speakOut(String text) {
        if (!textSpeaker.isSpeaking()) {
            textSpeaker.speakText(text);
            textSpeaker.pause(SHORT_DURATION);
        }

    }

    /**
     * Met à jour la visibilité (dépuis le fragment)
     */
    public void setStepSuccessButtonVisibility(){
        stepSuccessButton.setVisibility(View.INVISIBLE);
    }

    public void createNewSpeechTask(){
        textSpeechTask = new TTSpeechAsyncTask();
        textSpeechTask.execute();
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
            speechBtnPrompt.setImageDrawable(Util.getImageViewByName(getApplicationContext(), "icon_micro_off"));
            if(repeatcount == 2){

                TextView textView = findViewById(R.id.text_ref_view);
                String text = textView.getText().toString();

                try {
                    SECONDS.sleep(1);
                    speakOut(text);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                repeatcount = 0;
            }

            repeatcount++;
        }

        @Override
        public void onError(int error) {

        }

        @Override
        public void onResults(Bundle results) {

            TextView textView = findViewById(R.id.word_text_view);
            String son = textView.getText().toString();

            ArrayList<String> data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

            if(data != null && data.size() > 0){
                for (int i = 0; i < data.size(); i++){
                    if(data.get(i).toLowerCase().contains(son.toLowerCase())){
                        speechBtnPrompt.setVisibility(View.INVISIBLE);
                        stepSuccessButton.setVisibility(View.VISIBLE);
                        repeatcount = 0 ;
                        break;
                    }
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

    /**
     * Classe de gestion asynchrone de la synthèse vocale
     */
    private class TTSpeechAsyncTask extends AsyncTask<Void, Boolean, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Boolean... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            speakOutViewText();

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            speechBtnPrompt.setImageDrawable(Util.getImageViewByName(getApplicationContext(), "icon_micro_on"));
            speechBtnPrompt.setVisibility(View.VISIBLE);
            speechRecognizeManager.startListeningSpeech();

        }

    }

}
