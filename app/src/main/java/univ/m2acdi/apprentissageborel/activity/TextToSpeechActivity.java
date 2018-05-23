package univ.m2acdi.apprentissageborel.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import univ.m2acdi.apprentissageborel.R;
import univ.m2acdi.apprentissageborel.fragment.ListenSpeakOutFragment;
import univ.m2acdi.apprentissageborel.util.TextSpeaker;
import univ.m2acdi.apprentissageborel.util.Util;

import static java.util.concurrent.TimeUnit.SECONDS;

public class TextToSpeechActivity extends Activity {

    private final int REQ_CODE_SPEECH_INPUT = 100;
    private final int SHORT_DURATION = 1000;
    private ImageButton speechBtnPrompt;
    private ImageButton imageButton;
    private ImageView speechTextCheickStatus;

    private TextSpeaker textSpeaker;
    private static boolean isOk = false;
    private static int repeatCount = 0;

    private TTSpeechAsyncTask textSpeechTask;

    private ListenSpeakOutFragment lspFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_speech);

        lspFragment = new ListenSpeakOutFragment();

        setFragment(lspFragment);

        textSpeaker = (TextSpeaker) getIntent().getSerializableExtra("speaker");

        speechBtnPrompt = findViewById(R.id.speech_prompt_btn);
        speechBtnPrompt.setOnClickListener(onClickListener);

        speechTextCheickStatus = findViewById(R.id.speech_text_cheick_status);

        imageButton = findViewById(R.id.btn_next);
        //imageButton.setOnClickListener(onClickListener);

        //speakOutViewText();

    }

    @Override
    protected void onStart() {
        super.onStart();
        textSpeechTask = new TTSpeechAsyncTask();
        textSpeechTask.execute();
    }

    /**
     * Speech input dialog
     */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        //intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Lis la lettre ou le mot");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(), "Non supporté", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Réception du texte entendu
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        final int CONTROL_CODE = 99;

        ArrayList<String> result = null;
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    speechTextCheickStatus.setImageDrawable(Util.getImageViewByName(getApplicationContext(), "good"));
                    //txtSpeechCheick.setText(result.get(0));
                }
                repeatCount++;
                break;
            }
            case CONTROL_CODE:
                break;
        }
        if (repeatCount != 2) {
            textSpeechTask = new TTSpeechAsyncTask();
            textSpeechTask.execute();
        } else {

            try {
                SECONDS.sleep(3);
                lspFragment = new ListenSpeakOutFragment();
                setFragment(lspFragment);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent();
            try {
                startActivityForResult(intent, CONTROL_CODE);
            } catch (ActivityNotFoundException aex) {
                aex.printStackTrace();
            }

            isOk = true;
            repeatCount = 0;


        }


    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            promptSpeechInput();
        }
    };

    void setFragment(Fragment fragment) {

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.lspFragmentContainer, fragment, null);
        ft.commit();
    }

    public void speakOutViewText() {
        TextView textView = findViewById(R.id.word_text_view);
        String text = textView.getText().toString();

        try {
            SECONDS.sleep(3);
            speakOut(text);
            SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    protected void speakOut(String text) {
        if (!textSpeaker.isSpeaking()) {
            textSpeaker.speakText(text);
            textSpeaker.pause(SHORT_DURATION);
        }

    }

    /**
     * Classe de gestion asynchrone de la synthèse vocale
     */
    private class TTSpeechAsyncTask extends AsyncTask<Void, Boolean, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Toast.makeText(getApplicationContext(), "Début du traitement asynchrone", Toast.LENGTH_LONG).show();
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
            promptSpeechInput();
        }

    }

}
