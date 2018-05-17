package univ.m2acdi.apprentissageborel.fragment;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import univ.m2acdi.apprentissageborel.R;
import univ.m2acdi.apprentissageborel.activity.GestureToSpeechActivity;
import univ.m2acdi.apprentissageborel.activity.SectionIntroductActivity;
import univ.m2acdi.apprentissageborel.activity.TextToSpeechActivity;
import univ.m2acdi.apprentissageborel.util.TextSpeaker;

/**
 * A simple {@link Fragment} subclass.
 */
public class SectionIntroductFragment extends Fragment {

    private TextView sectionIntroductTitle;
    private ImageButton sectionStartBtn;


    public SectionIntroductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_section_introduct, container, false);

        sectionIntroductTitle = view.findViewById(R.id.section_introduct_title);
        sectionStartBtn = view.findViewById(R.id.section_activity_start_btn);
        sectionStartBtn.setOnClickListener(sectionStartBtnClickListener);

        setSectionTitle();

        return view;
    }

    View.OnClickListener sectionStartBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            int section = getActivity().getIntent().getExtras().getInt("section");
            TextSpeaker textSpeaker = (TextSpeaker) getActivity().getIntent().getSerializableExtra("speaker");
            intent.putExtra("speaker", textSpeaker);
            switch (section){
                case 1:
                    intent.setClass(getActivity().getBaseContext(), TextToSpeechActivity.class);
                    break;
                case 2:
                    intent.setClass(getActivity().getBaseContext(), GestureToSpeechActivity.class);
                    break;
                case 3:
                    break;
                default:
                    intent.setClass(getActivity().getBaseContext(), SectionIntroductActivity.class);
                    break;
            }
            startActivity(intent);
        }
    };


    protected void setSectionTitle() {

        int section = getActivity().getIntent().getExtras().getInt("section");

            switch (section){
                case 1:
                    sectionIntroductTitle.setText(getResources().getString(R.string.section_title_1));
                    break;
                case 2:
                    sectionIntroductTitle.setText(getResources().getString(R.string.section_title_2));
                    break;
                case 3:
                    sectionIntroductTitle.setText(getResources().getString(R.string.section_title_3));
                    break;
                default:
                    sectionIntroductTitle.setText(getResources().getString(R.string.app_name));
            }
    }
}
