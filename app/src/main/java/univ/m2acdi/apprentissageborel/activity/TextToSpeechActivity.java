package univ.m2acdi.apprentissageborel.activity;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import univ.m2acdi.apprentissageborel.R;
import univ.m2acdi.apprentissageborel.util.TextSpeaker;

public class TextToSpeechActivity extends AppCompatActivity {

    private final int CHECK_CODE = 0x1;
    private final int SHORT_DURATION = 1000;
    private Button readText;
    private EditText textToRead;

    private TextSpeaker textSpeaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_speech);

        readText = findViewById(R.id.read_text_btn);
        readText.setOnClickListener(readListener);

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

    View.OnClickListener readListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            textToRead = findViewById(R.id.text_to_read);
            String text = textToRead.getText().toString();
            speakOut(text);
        }
    };

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

    /**
     * Lecture du texte
     * @param text
     */
    private void speakOut(String text) {
        if (!textSpeaker.isSpeaking()) {
            textSpeaker.speakText(text);
            textSpeaker.pause(SHORT_DURATION);
        }
    }

}
