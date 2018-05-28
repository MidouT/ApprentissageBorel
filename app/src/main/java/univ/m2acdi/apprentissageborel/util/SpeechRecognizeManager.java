package univ.m2acdi.apprentissageborel.util;

import android.app.Activity;
import android.content.Intent;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;

public class SpeechRecognizeManager {

    private Activity activity;
    private SpeechRecognizer speechRecognizer;
    private Intent speechRecognizerIntent;


    public SpeechRecognizeManager(Activity activity) {
        this.activity = activity;
    }

    /**

     * Reconnaissance Vocale
     */
    public void initVoiceRecognizer(RecognitionListener recognitionListener) {
        speechRecognizer = getSpeechRecognizer(recognitionListener);
        speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "fr-FR");
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, activity.getPackageName());
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
    }

    public void startListeningSpeech() {
        if (speechRecognizer!=null) {
            speechRecognizer.cancel();
        }
        speechRecognizer.startListening(speechRecognizerIntent);
    }

    private SpeechRecognizer getSpeechRecognizer(RecognitionListener recognitionListener){
        if (speechRecognizer == null) {
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(activity);
            speechRecognizer.setRecognitionListener(recognitionListener);
        }
        return speechRecognizer;
    }
    // Fin reconnaissance vocale
}
