package univ.m2acdi.apprentissageborel.activity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import static java.util.concurrent.TimeUnit.*;

public class TextToSpeechActivity extends AppCompatActivity {

    private final int REQ_CODE_SPEECH_INPUT = 100;
    private ImageButton speechBtnPrompt;
    private ImageButton imageButton;
    private ImageView speechTextCheickStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_speech);

        setFragment(new ListenSpeakOutFragment());

        speechBtnPrompt = findViewById(R.id.speech_prompt_btn);
        speechBtnPrompt.setOnClickListener(onClickListener);

        speechTextCheickStatus = findViewById(R.id.speech_text_cheick_status);

        imageButton = findViewById(R.id.btn_next);
        //imageButton.setOnClickListener(onClickListener);

    }


    @Override
    protected void onRestart() {
        super.onRestart();
    }

    /**
     * Speech input dialog
     * */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Lis la lettre ou le mot");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(), "Non supporté", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Réception du texte entendu
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ArrayList<String> result = null;
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    speechTextCheickStatus.setImageDrawable(getImageViewByName("good"));
                    //txtSpeechCheick.setText(result.get(0));
                }
                break;
            }

        }

        if(result!=null){
            for (int i = 0; i<result.size(); i++){
                System.out.println("\n Sequence: "+result.get(i));
            }
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            promptSpeechInput();
        }
    };

    void setFragment(Fragment fragment){

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.lspFragmentContainer, fragment, null);
        ft.commit();
    }


    /**
     * Récupère une image (Objet Drawable)
     * @param geste
     * @return
     */
    private Drawable getImageViewByName(String geste) {

        Context context = getApplicationContext();

        int image_id = context.getResources().getIdentifier(geste, "drawable", getPackageName());

        return context.getResources().getDrawable(image_id);
    }


}
