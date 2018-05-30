package univ.m2acdi.apprentissageborel.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;

import univ.m2acdi.apprentissageborel.R;
import univ.m2acdi.apprentissageborel.activity.DataConfigActivity;
import univ.m2acdi.apprentissageborel.listener.AdminConfigListener;
import univ.m2acdi.apprentissageborel.util.BMObject;
import univ.m2acdi.apprentissageborel.util.Util;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewBMObjectFragment extends Fragment {

    private EditText edTxtSon;
    private EditText edTxtGraphie;
    private EditText edTxtTextRef;
    private ImageView imgViewGeste;
    private ImageButton fileUploadBtn;
    private Button submitButton;

    private AdminConfigListener adminConfigListener;


    public NewBMObjectFragment() {
        // Required empty public constructor
    }

    public static NewBMObjectFragment newInstance() {

        Bundle args = new Bundle();

        NewBMObjectFragment fragment = new NewBMObjectFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_bmobject, container, false);

        edTxtSon = view.findViewById(R.id.add_son_editext);
        edTxtGraphie = view.findViewById(R.id.add_graphie_editText);
        edTxtTextRef = view.findViewById(R.id.add_textRef_editText);
        imgViewGeste = view.findViewById(R.id.add_geste_imgView);
        fileUploadBtn = view.findViewById(R.id.add_file_upload_btn);
        fileUploadBtn.setOnClickListener(onFileUplodBtnClckListener);

        submitButton = view.findViewById(R.id.submit_new_object);
        submitButton.setOnClickListener(onSubmitBtnClckListener);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof AdminConfigListener) {
            adminConfigListener = (AdminConfigListener) activity;
        } else {
            throw new ClassCastException(activity.toString() + " don't implement appropriate listener");
        }
    }

    View.OnClickListener onFileUplodBtnClckListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            adminConfigListener.onFileUploadBtnClicked();
        }
    };

    //Listener pour la validation du formulaire
    View.OnClickListener onSubmitBtnClckListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String son = edTxtSon.getText().toString();
            String graphie = formatGraphieStr(edTxtGraphie.getText().toString());
            String textRef = edTxtTextRef.getText().toString();

            BMObject bmObject = new BMObject(son,graphie, textRef, getObjectGesteStr());

            ((DataConfigActivity)getActivity()).onNewBMObjectCreateBtnClicked(bmObject);
        }
    };

    /**
     * Formate un chaine de caractère sous forme d'un tableau JSON
     * @param edText
     * @return
     */
    protected String formatGraphieStr(String edText){

        JSONArray jsonArray = new JSONArray();

        if((edText != null) && (!edText.isEmpty())){
            String[] splitTab = edText.split(" ");
            for(int i = 0; i < splitTab.length; i++){
                String str = splitTab[i];
                jsonArray.put(str);
            }
        }

        return jsonArray.toString();
    }

    /**
     * Réinitialise les champs à chaque fois que le fragment est réaffiché
     */
    public void initAllField(BMObject bmObject){
        if(bmObject != null){
            edTxtSon.setText(bmObject.getSon());
            edTxtGraphie.setText(Util.getFormatedGraphieStr(bmObject.getGraphie()));
            edTxtTextRef.setText(bmObject.getTexte_ref());
            imgViewGeste.setImageDrawable(Util.getImageViewByName(getActivity().getApplicationContext(), bmObject.getGeste()));
        }else {
            edTxtSon.setText("");
            edTxtGraphie.setText("");
            edTxtTextRef.setText("");
            imgViewGeste.setImageBitmap(null);
        }


    }

    public void setImgViewGeste(Bitmap bitmap){
        imgViewGeste.setImageBitmap(bitmap);
    }

    // Chaine utilisé comme nom de l'image du geste du mot correspondant
    public String getObjectGesteStr(){
        String str = edTxtSon.getText().toString();

        return "bm_"+str;
    }

}
