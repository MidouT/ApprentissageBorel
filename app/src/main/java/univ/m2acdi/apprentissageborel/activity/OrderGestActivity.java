package univ.m2acdi.apprentissageborel.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import univ.m2acdi.apprentissageborel.R;
import univ.m2acdi.apprentissageborel.util.BMObject;
import univ.m2acdi.apprentissageborel.util.ExerciseWord;
import univ.m2acdi.apprentissageborel.util.Util;

public class OrderGestActivity extends AppCompatActivity {
    private JSONArray jsonData;
    private JSONArray jsonExo;
    private TextView read_text;
    private ImageView image;
    private ImageButton imageButton;
    private Intent intent;
    private int index;
    private Boolean flag;
    private RelativeLayout mylayout;
    private RelativeLayout find_layout;
   // private RelativeLayout.LayoutParams layoutParams;
    private BMObject bm;
    private int it;
    private int wordFind;
    private ExerciseWord exo;
    private int width;
    private int height;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_gest);
        jsonData = Util.getJsonArrayDataFromIntent(getIntent(), "jsonArray");
        jsonExo = Util.readJsonDataFile(this, "exercise.json");
        image=null;
        index = 0;
        it=0;
        wordFind=0;
        exo=null;
        flag = false;
        read_text = findViewById(R.id.dispay_word);
        mylayout = (RelativeLayout) findViewById(R.id.image_layout);
        find_layout = (RelativeLayout) findViewById(R.id.find_layout);
        //layoutParams=null;
        imageButton = findViewById(R.id.btn_next);
    }

    @Override
    protected void onStart() {
        super.onStart();
       // imageButton.setEnabled(flag);
        imageButton.setOnClickListener(onClickListener);

    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    View.OnClickListener myWordChecker=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
                String[] a=exo.getGraphie();

                if(v.getId()==wordFind) {
                    String wordColored="";
                    String notColored="";
                    for(int i=0; i<wordFind;i++)
                        wordColored+=a[i].toString();
                    for(int i=wordFind+1; i<a.length;i++)
                        notColored+=a[i].toString();

                    mylayout.removeViewInLayout(v);
                    bm = Util.getWordObject(jsonData, a[wordFind-1]);
                        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                                width,
                                height
                        );
                        if(wordFind==1) {
                            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                        }
                        else{
                            layoutParams.addRule(RelativeLayout.RIGHT_OF, (wordFind-1));
                        }
                        image=new ImageView(find_layout.getContext());
                        image.setId(wordFind);
                        image.setImageDrawable(Util.getImageViewByName(getApplicationContext(),bm.getGeste().toString()));
                        find_layout.addView(image,layoutParams);




                    String text = "<font color='green'>"+wordColored+"</font>";
                    if(wordFind<a.length)
                    text+="<font color='red'>"+a[wordFind]+"</font>";
                    text+="<font color='black'>"+notColored+"</font>";
                    read_text.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE);
                    wordFind++;
                }
                if(wordFind>a.length)
                    showWordAndimage();
        }
    };
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            showWordAndimage();

        }
    };

    public void showWordAndimage(){
        //initialisation de l'ecran: on enleve toutes les anciennes images
        mylayout.removeAllViewsInLayout();
        find_layout.removeAllViewsInLayout();
        it=1;

        BMObject bm;
        if (index == jsonExo.length() - 1) {
            //si tout les mots des exercices sont fini, on arrete o bien on boucle?
            index = 0;
            //traitement a definir
        } else {
            index++;
        }
        read_text.setText("");
        exo = readWord(index);

        //colorier la premiere lettre
        String graphie[]=exo.getGraphie();
        String notColored="";
        for(int i=1; i<graphie.length;i++)
            notColored+=graphie[i].toString();
        String text = "<font color='red'>"+graphie[0]+"</font>";
        text+="<font color='black'>"+notColored+"</font>";
        read_text.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE);

        width=(int)mylayout.getWidth()/exo.getWord().length();
        height=(int)mylayout.getHeight()/2;

        for(String x: exo.getSon()) {
            bm = Util.getWordObject(jsonData, x);
            if(bm!=null)
            {
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                        width,
                        height
                );
                if(it==1) {
                    // layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                }
                else{
                   /* int a=it%2;

                       if(a==0) {
                                    layoutParams.addRule(RelativeLayout.LEFT_OF, (it - 2)); }
                        else
                            if(a==1) {*/
                                         layoutParams.addRule(RelativeLayout.LEFT_OF, (it-1));
                                   //  }
                }
                image=new ImageView(mylayout.getContext());
                image.setId(it);
                image.setImageDrawable(Util.getImageViewByName(getApplicationContext(),bm.getGeste().toString()));
                image.setOnClickListener(myWordChecker);
                mylayout.addView(image,layoutParams);

                it++;
            }
            wordFind=1;
            //imageButton.setEnabled(flag);
        }
    }

    public ExerciseWord readWord(int indice) {
        JSONObject jsonObject = null;
        String word = null;
        String son=null;
        ExerciseWord exo=null;

        try {
            jsonObject = jsonExo.getJSONObject(indice);
            word = jsonObject.getString("graphie");
            son=jsonObject.getString("son");
            exo=new ExerciseWord(word, son);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return exo;
    }


}
