package univ.m2acdi.apprentissageborel.activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;
import univ.m2acdi.apprentissageborel.R;
import univ.m2acdi.apprentissageborel.util.BMObject;
import univ.m2acdi.apprentissageborel.util.SpeechRecognizeManager;
import univ.m2acdi.apprentissageborel.util.Util;

import static java.util.concurrent.TimeUnit.SECONDS;


public class GestureToSpeechActivity extends Activity {

    private ImageView imageView ;
    private ImageButton imageButtonNext;
    private ImageButton imageButtonPrec;
    private TextView texteViewLettres;
    private ImageView wordImgIndice;
    private ImageView imageViewMicro;
    private TextView textUser;

    private static int index = 0;

    private BMObject bmObject;
    private static JSONArray jsonArray;

    private SpeechRecognizeManager speechRecognizeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_to_speech);

        jsonArray = Util.getJsonArrayDataFromIntent(getIntent(), "jsonArray");

        bmObject = Util.readNextWord(jsonArray, index);
        texteViewLettres = findViewById(R.id.texteViewLettres);
        imageView = findViewById(R.id.word_img_view);
        textUser = findViewById(R.id.text_user);
        wordImgIndice = findViewById(R.id.word_img_view);
        imageView.setImageDrawable(Util.getImageViewByName(getApplicationContext(), bmObject.getGeste()));
        imageButtonNext = findViewById(R.id.btn_next);
        imageButtonPrec = findViewById(R.id.btn_prec);
        imageViewMicro = findViewById(R.id.micro);
        wordImgIndice = findViewById(R.id.word_img_indice);

        speechRecognizeManager = new SpeechRecognizeManager(this);

        speechRecognizeManager.initVoiceRecognizer(recognitionListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        imageButtonNext.setOnClickListener(onClickListener);
        imageButtonPrec.setOnClickListener(onClickListener);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()){
                case R.id.btn_next:
                    if(index==jsonArray.length() - 1){
                        index = 0;
                    }else{
                        ++index;
                    }
                    clearComponent();
                    break;
                case R.id.btn_prec:
                    if(index == 0){
                        index = jsonArray.length() - 1;
                    }else{
                        --index;
                    }
                    clearComponent();
                    break;
            }
            bmObject = Util.readNextWord(jsonArray, index);
            imageView.setImageDrawable(Util.getImageViewByName(getApplicationContext(), bmObject.getGeste()));
        }
    };

    /**
     * Méthode pour renitialiser les différents composants
     */

    public void clearComponent(){

        texteViewLettres.setText("?");
        textUser.setText("");
        wordImgIndice.setImageDrawable(Util.getImageViewByName(getApplicationContext(), "imageview_border"));
    }

    /**
     * Méthode pour l'écoute du clic sur l'imageButton permettant d'afficher l'image à découvrir
     */
    public void showIndice(View v) {
        wordImgIndice.setImageDrawable(Util.getImageViewByName(getApplicationContext(), bmObject.getImgMot()));
    }

    /**
     * Méthode pour l'écoute du clic sur le button activant le microphone
     * @param v
     */
    public void startListening(View v) {
        imageViewMicro.setImageDrawable(Util.getImageViewByName(getApplicationContext(), "icon_micro_on"));
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
            imageViewMicro.setImageDrawable(Util.getImageViewByName(getApplicationContext(), "icon_micro_off"));
        }

        @Override
        public void onError(int error) {

        }

        @Override
        public void onResults(Bundle results) {

            imageViewMicro.setImageDrawable(Util.getImageViewByName(getApplicationContext(), "icon_micro_off"));
            ArrayList<String> data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            for (int i = 0; i < data.size(); i++){
                System.out.println(data.get(i));
            }
            for (int i = 0; i < data.size(); i++){
                if(data.get(i).toLowerCase().contains(bmObject.getMotRef().toLowerCase())){
                    wordImgIndice.setImageDrawable(Util.getImageViewByName(getApplicationContext(), bmObject.getImgMot()));
                    texteViewLettres.setText(bmObject.getSon());
                    String result = colorChar(bmObject.getMotRef(),bmObject.getSon());
                    textUser.setText(Html.fromHtml(result), TextView.BufferType.SPANNABLE);
                    //Util.showCongratDialog(GestureToSpeechActivity.this);
                    CongratDialogTask congratDialogTask = new CongratDialogTask();
                    congratDialogTask.execute();
                    break;
                }
            }

        }

        public String colorChar(String chaine, String c){
            int i = 0;
            String text = "";
            while(i < chaine.length()){
                if(chaine.charAt(i) == c.charAt(0)){
                    text += "<font color='red'>" + chaine.substring(i,i+c.length()) + "</font>";
                    i += c.length();
                }else{
                    text += String.valueOf(chaine.charAt(i));
                    i++;
                }
            }
            return text;
        }

        @Override
        public void onPartialResults(Bundle partialResults) {

        }

        @Override
        public void onEvent(int eventType, Bundle params) {

        }
    };

    private class CongratDialogTask extends AsyncTask<Void, Boolean, Void> {

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

            try {
                SECONDS.sleep(2);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Util.showCongratDialog(GestureToSpeechActivity.this);
        }

    }
}
