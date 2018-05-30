package univ.m2acdi.apprentissageborel.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class Util {

    /**
     * Renvoie l'objet se trouvant dans la position indexé dans le tableau
     *
     * @param jsonArray
     * @param index
     * @return
     */
    public static BMObject readNextWord(JSONArray jsonArray, int index) {

        BMObject bmObject = new BMObject();
        try {
            JSONObject jsonObject = jsonArray.getJSONObject(index);
            bmObject.setSon(jsonObject.getString("son"));
            bmObject.setGraphie(jsonObject.getString("graphie"));
            bmObject.setTexte_ref(jsonObject.getString("texte_ref"));
            bmObject.setGeste(jsonObject.getString("geste"));
            bmObject.setMotRef(jsonObject.getString("mot_ref"));
            bmObject.setImgMot(jsonObject.getString("img_mot"));
        } catch (JSONException e) {

        }

        return bmObject;
    }

    /**
     * Méthode de récupération des données passées à une activité via l'intent
     *
     * @param intent
     * @param attrName
     * @return
     */
    public static JSONArray getJsonArrayDataFromIntent(Intent intent, String attrName) {

        String stringArray = intent.getStringExtra(attrName);
        JSONArray jsArray = null;
        try {
            jsArray = new JSONArray(stringArray);
            System.out.println(jsArray.toString(2));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsArray;
    }

    /**
     * Méthode de lecture du fichier de données
     * <p>
     * Initialise la liste de données (tableau JSON)
     *
     * @param context
     * @return
     */
    public static JSONArray readJsonDataFile(Context context, String filename) {
        JSONArray jsonArray = null;
        String jsonStr;
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

    /**
     * Récupère une image (Objet Drawable)
     *
     * @param geste
     * @return
     */
    public static Drawable getImageViewByName(Context context, String geste) {

        int image_id = context.getResources().getIdentifier(geste, "drawable", context.getPackageName());

        return context.getResources().getDrawable(image_id);
    }

    /**
     * Récupère un id de ressource (int)
     *
     * @param
     * @return
     */
    /*public static int getRessourceId(Context context, String anim) {

        return context.getResources().getIdentifier(anim, "drawable", context.getPackageName());
    }*/

    public static String getFormatedGraphieStr(String graphie) {
        String str = "";
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(graphie);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                str += jsonArray.getString(i) + "\t";
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return str;
    }

    /**
     * Recupere le bm object d'un son passer en parametre
     *
     * @param jsonArray
     * @param son
     * @return
     */
    public static BMObject getWordObject(JSONArray jsonArray, String son) {
        BMObject bm = null;
        for (int i = 0; i < jsonArray.length(); i++) {
            bm = Util.readNextWord(jsonArray, i);
            if (bm.getSon().toString().equals(son.toString()))
                return bm;
        }
        bm = null;
        return bm;
    }
}
