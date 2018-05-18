package univ.m2acdi.apprentissageborel.util;

import android.content.Intent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Util {

    /**
     * Renvoie l'objet se trouvant dans la position indexé dans le tableau
     * @param jsonArray
     * @param index
     * @return
     */
    public static BMObject readNextWord(JSONArray jsonArray, int index){

        BMObject wordObject = new BMObject();
        try {
            JSONObject jsonobject = jsonArray.getJSONObject(index);
            wordObject.setSon(jsonobject.getString("son"));
            wordObject.setGraphie(jsonobject.getString("graphie"));
            wordObject.setTexte_ref(jsonobject.getString("texte_ref"));
            wordObject.setGeste(jsonobject.getString("geste"));
        } catch (JSONException e) {

        }

        return wordObject;
    }

    /**
     * Méthode de récupération des données passées à une activité via l'intent
     * @param intent
     * @param attrName
     * @return
     */
    public static JSONArray getJsonArrayDataFromIntent(Intent intent, String attrName){

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
}
