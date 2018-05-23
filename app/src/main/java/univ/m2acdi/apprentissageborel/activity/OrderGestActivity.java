package univ.m2acdi.apprentissageborel.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private LinearLayout mylayout;
    private BMObject bm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_gest);
        jsonData = Util.getJsonArrayDataFromIntent(getIntent(), "jsonArray");
        jsonExo = readJsonDataFile(this, "exercise.json");
        index = 0;
        flag = false;
        read_text = findViewById(R.id.dispay_word);
        mylayout = (LinearLayout) findViewById(R.id.image_layout);
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


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ExerciseWord exo;
            if (index == jsonExo.length() - 1) {
                //si tout les mots des exercices sont fini, on arrete o bien on boucle?
                index = 0;
                //traitement a definir
            } else {
                index++;
            }
            read_text.setText("");
            exo = readWord(index);
            read_text.setText(exo.getWord());
           /* imageView.setImageDrawable(getApplicationContext().getResources().getDrawable(getApplicationContext().getResources().getIdentifier(bm.getGeste(), "drawable", getPackageName())));
            flag = false;
            imageButton.setEnabled(flag);*/
        }
    };

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

    //fonction de lecture des fichiers  contenant les mots exercises
    public JSONArray readJsonDataFile(Context context, String filename) {
        String jsonStr;
        JSONArray jsonArray = null;
        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonStr = new String(buffer, "UTF-8");
            jsonArray = new JSONArray(jsonStr);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;


    }
}
