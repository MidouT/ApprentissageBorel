package univ.m2acdi.apprentissageborel.util;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

public class TextSpeaker implements TextToSpeech.OnInitListener {


    private static Locale language = Locale.FRANCE;

    private static TextToSpeech textToSpeech;
    private boolean isReady = false;

    public TextSpeaker(Context context){
        textToSpeech = new TextToSpeech(context, this);
        textToSpeech.setPitch(0.8f);
        textToSpeech.setSpeechRate(0.9f);
    }

    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS){
            textToSpeech.setLanguage(language);
            isReady = true;
        } else{
            isReady = false;
        }
    }

    public void speakText(String text){
        if(isReady) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_ADD, null, null);
        }
    }

    public void setSpeedRate(float speechrate) {
        textToSpeech.setSpeechRate(speechrate);
    }

    public void setPitchRate(float pitchrate) {
        textToSpeech.setPitch(pitchrate);
    }

    public boolean isSpeaking() {
        return textToSpeech.isSpeaking();
    }

    public void pause(int duration){
        textToSpeech.playSilentUtterance(duration, TextToSpeech.QUEUE_ADD, null);
    }

    public void stop() {
        textToSpeech.stop();
    }

    public void destroy() {
        textToSpeech.shutdown();
    }

}
