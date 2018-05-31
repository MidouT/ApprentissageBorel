package univ.m2acdi.apprentissageborel.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import univ.m2acdi.apprentissageborel.R;
import univ.m2acdi.apprentissageborel.activity.GestureToSpeechActivity;

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
    public static JSONArray readJsonDataFile(Context context, String fileName) {
        JSONArray jsonArray = null;
        InputStream stream;
        try {
            //Vérification dans ApplicationContext
            File file = context.getFileStreamPath(fileName);
            if (file == null || !file.exists()) {
                // Si le fichier de données n'existe pas encore dans le contexte de l'application on lit dépuis 'assets'
                System.out.println("================= Asset file read ==================");
                stream = context.getAssets().open(fileName);
            } else {
                System.out.println("================= App context file exist ==================");
                stream = context.openFileInput(fileName);
            }
            int dataSize = stream.available();
            byte[] buffer = new byte[dataSize];
            stream.read(buffer);
            stream.close();
            String jsonStr = new String(buffer, "UTF-8");
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
     * Méthode d'écriture (sauvegarde de lise de données
     *
     * @param context
     * @param data
     */
    public static void writeJsonDataFile(Context context, String data) {

        FileOutputStream outputStream;

        try {
            outputStream = context.openFileOutput(Constante.DATA_FILE_NAME, Context.MODE_PRIVATE);
            outputStream.write(data.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Récupère une image (Objet Drawable)
     *
     * @param geste
     * @return
     */
    public static Drawable getImageViewByName(Context context, String geste) {

        int image_id = context.getResources().getIdentifier(geste, "drawable", context.getPackageName());

        if (image_id != 0){
            return context.getResources().getDrawable(image_id);
        }else {
            Bitmap bitmap = getBitmapFromAppDir(context, geste+".PNG");

            if (bitmap != null){
                return new BitmapDrawable(context.getResources(), bitmap);
            }
        }

        return null;
    }

    /**
     * Récupère un objet image (Bitmap) de puis le dossier propre au contexte de l'application
     *
     * @param filename
     * @return
     */
    public static Bitmap getBitmapFromAppDir(Context context, String filename) {

        Bitmap bitmap = null;
        try {
            String dirPath = context.getFilesDir() + "/images/";
            File filePath = new File(dirPath,filename);
            if (filePath.exists()){
                FileInputStream fileInputStream = new FileInputStream(filePath);
                bitmap = BitmapFactory.decodeStream(fileInputStream);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return bitmap;
    }


    /**
     * Coverti le tableau json de l'attribut "graphie" en chaine de caractères propre
     * @param graphie
     * @return
     */
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
     * Recupère l'objet d'un son passer en parametre
     *
     * @param jsonArray
     * @param son
     * @return
     */
    public static BMObject getWordObject(JSONArray jsonArray, String son) {
        BMObject bm;
        for (int i = 0; i < jsonArray.length(); i++) {
            bm = Util.readNextWord(jsonArray, i);
            if (bm.getSon().toString().equals(son.toString()))
                return bm;
        }
        return null;
    }

    /**
     * Crée un ensemble chaines de caractères à partir de la liste d'objet passer en paramètre
     * @param bmObjectList
     * @return
     */
    public static String convertBMOBjectListToJSonArray(List<BMObject> bmObjectList) {
        JSONArray jsonArray = new JSONArray();
        for (BMObject bmObject : bmObjectList) {
            JSONObject jsonObj = new JSONObject();
            try {
                jsonObj.put("son", bmObject.getSon());
                jsonObj.put("graphie", bmObject.getGraphie());
                jsonObj.put("texte_ref", bmObject.getTexte_ref());
                jsonObj.put("geste", bmObject.getGeste());
                jsonObj.put("mot_ref",bmObject.getMotRef());
                jsonObj.put("img_mot",bmObject.getImgMot());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArray.put(jsonObj);
        }
        return jsonArray.toString();
    }

    public static void showCongratDialog(Activity activity){
        // Creation de la boite de dialog
        final Dialog dialog = new Dialog(activity);
        // Ressource
        dialog.setContentView(R.layout.dialog);
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(dialog.isShowing()){
                    dialog.dismiss();
                }
            }
        }, 5000);
    }
}
