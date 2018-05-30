package univ.m2acdi.apprentissageborel.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import univ.m2acdi.apprentissageborel.R;
import univ.m2acdi.apprentissageborel.activity.DataConfigActivity;
import univ.m2acdi.apprentissageborel.listener.AdminConfigListener;
import univ.m2acdi.apprentissageborel.util.BMObject;

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

    private LinearLayout addGraphieLayout;

    private AdminConfigListener adminConfigListener;


    public NewBMObjectFragment() {
        // Required empty public constructor
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

        addGraphieLayout = view.findViewById(R.id.add_graphie_layout);

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

    View.OnClickListener onSubmitBtnClckListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String son = edTxtSon.getText().toString();
            String graphie = edTxtGraphie.getText().toString();
            String textRef = edTxtTextRef.getText().toString();



            BMObject bmObject = new BMObject(son,graphie, textRef, "bm_a");

            ((DataConfigActivity)getActivity()).onNewBMObjectCreateBtnClicked(bmObject);
        }
    };

}
