package univ.m2acdi.apprentissageborel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import univ.m2acdi.apprentissageborel.R;
import univ.m2acdi.apprentissageborel.util.BMObject;
import univ.m2acdi.apprentissageborel.util.ExerciseWord;
import univ.m2acdi.apprentissageborel.util.TextSpeaker;
import univ.m2acdi.apprentissageborel.util.Util;

import static java.util.concurrent.TimeUnit.SECONDS;

public class OrderGestActivity extends AppCompatActivity {

    private final int SHORT_DURATION = 1000;

    private TextView read_text;
    private ImageView image;
    private ImageButton nextButton;
    private ImageButton previousButton;

    private static int index;
    private static int image_rank;
    private int wordFind;

    private RelativeLayout mylayout;
    private RelativeLayout find_layout;
    private List<ImageView> wordimages;

    private JSONArray jsonData;
    private JSONArray jsonExo;
    private ExerciseWord exo;

    private TextSpeaker textSpeaker;

    private int width;
    private int height;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_gest);

        jsonData = Util.getJsonArrayDataFromIntent(getIntent(), "jsonArray");
        jsonExo = Util.readJsonDataFile(this, "exercise.json");
        textSpeaker = (TextSpeaker) getIntent().getSerializableExtra("speaker");
        textSpeaker.setSpeedRate(0.7f);
        image = null;
        index = 0;
        wordFind = 0;
        exo = null;
        wordimages = new ArrayList<>();
        read_text = findViewById(R.id.dispay_word);
        mylayout = findViewById(R.id.image_layout);
        find_layout = findViewById(R.id.find_layout);
        nextButton = findViewById(R.id.btn_next);
        previousButton = findViewById(R.id.btn_prec);

    }

    @Override
    protected void onStart() {
        super.onStart();

        nextButton.setOnClickListener(onClickListener);
        previousButton.setOnClickListener(onClickListener);
        showWordAndimage();

        ViewGroup vg = findViewById (R.id.image_layout);
        vg.invalidate();

    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onBackPressed() {

        textSpeaker.destroy();

        Intent intent = new Intent(this, MainActivity.class);

        intent.putExtra("back_to_main", true);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        OrderGestActivity.this.finish();
        startActivity(intent);
    }

    View.OnClickListener myWordChecker = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String[] graphie = exo.getGraphie();

            if (v.getId() == wordFind) {
                String wordColored = "";
                String notColored = "";

                for (int i = 0; i < wordFind; i++){
                    wordColored += graphie[i].toString();
                }


                for (int i = wordFind + 1; i < graphie.length; i++){
                    notColored += graphie[i].toString();
                }

                mylayout.removeViewInLayout(v);
                BMObject bmObject = Util.getWordObject(jsonData, graphie[wordFind - 1]);

                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, height);

                if (wordFind == 1) {
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                } else {
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, (wordFind - 1));
                }

                image = new ImageView(find_layout.getContext());
                image.setId(wordFind);
                image.setImageDrawable(Util.getImageViewByName(getApplicationContext(), bmObject.getGeste()));
                find_layout.addView(image, layoutParams);

                String text = "<font color='green'>" + wordColored + "</font>";
                if (wordFind < graphie.length){
                    text += "<font color='red'>" + graphie[wordFind] + "</font>";
                }

                text += "<font color='black'>" + notColored + "</font>";

                read_text.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE);

                wordFind++;
                speakOut("Bravo, continue comme ça");
            }else {
                speakOut("Bien tenté, essaye une autre");
            }

            if (wordFind > graphie.length)
                Util.showCongratDialog(OrderGestActivity.this);
        }
    };

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id) {
                case R.id.btn_next:
                    index++;
                    if (index == jsonExo.length()) {
                        //si tout les mots des exercices sont fini, on arrete o bien on boucle?
                        index = 0;
                        //traitement a definir
                    }
                    break;
                case R.id.btn_prec:
                    index--;
                    if (index < 0) {
                        //si tout les mots des exercices sont fini, on arrete o bien on boucle?
                        index = jsonExo.length() - 1;
                        //traitement a definir
                    }
                    break;
                default:
                    break;
            }
            showWordAndimage();

        }
    };


    /**
     *
     */
    public void showWordAndimage() {
        //initialisation de l'ecran: on enleve toutes les anciennes images
        mylayout.removeAllViewsInLayout();
        find_layout.removeAllViewsInLayout();
        wordimages=new ArrayList<>();
        image_rank = 1;

        BMObject bmObject;

        read_text.setText("");
        exo = readWord(index);

        //colorier la premiere lettre
        String graphie[] = exo.getGraphie();
        String notColored = "";
        for (int i = 1; i < graphie.length; i++){
            notColored += graphie[i].toString();
        }
        String text = "<font color='red'>" + graphie[0] + "</font>";
        text += "<font color='black'>" + notColored + "</font>";
        read_text.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE);

        width = mylayout.getWidth() / exo.getWord().length();
        height = mylayout.getHeight() / 2;

        for (String son : exo.getSon()) {
            bmObject = Util.getWordObject(jsonData, son);
            if (bmObject != null) {

                image = new ImageView(mylayout.getContext());
                image.setId(image_rank);
                image.setImageDrawable(Util.getImageViewByName(getApplicationContext(), bmObject.getGeste().toString()));
                image.setOnClickListener(myWordChecker);
                wordimages.add(image);
                image_rank++;
            }
            wordFind = 1;
        }

        mixImage(image_rank - 1);
        int currentId = 0;
        for (ImageView imageView : wordimages) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, height);

            if (currentId == 0) {
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            } else {
                layoutParams.addRule(RelativeLayout.LEFT_OF, (currentId));
            }

            mylayout.addView(imageView, layoutParams);
            currentId = imageView.getId();
        }

    }

    //desordonne les image
    public void mixImage(int id) {
        ImageView pivot;
        int rand;
        Random rd = new Random();
        for (int i = 0; i < id; i++) {
            pivot = wordimages.get(i);
            rand = rd.nextInt(id);
            wordimages.set(i, wordimages.get(rand));
            wordimages.set(rand, pivot);
        }

    }

    public ExerciseWord readWord(int indice) {
        JSONObject jsonObject ;
        String word ;
        String son ;
        ExerciseWord exo = null;

        try {
            jsonObject = jsonExo.getJSONObject(indice);
            word = jsonObject.getString("graphie");
            son = jsonObject.getString("son");
            exo = new ExerciseWord(word, son);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return exo;
    }


    /**
     * Prononce le text passé en paramètre
     * @param text
     */
    protected void speakOut(String text) {
        if (!textSpeaker.isSpeaking()) {
            textSpeaker.speakText(text);
            //textSpeaker.pause(SHORT_DURATION);
        }

    }



}
