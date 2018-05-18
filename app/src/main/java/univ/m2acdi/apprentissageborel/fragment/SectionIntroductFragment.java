package univ.m2acdi.apprentissageborel.fragment;


import android.app.Activity;
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

    private SectionStartListener sectionStartListener;


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

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof SectionStartListener) {
            sectionStartListener = (SectionStartListener) activity;
        } else {
            throw new ClassCastException(activity.toString() + " don't implement appropriate listener");
        }
    }

    /**
     * Instance listener
     */
    View.OnClickListener sectionStartBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            sectionStartListener.onSectionButtonClick(view);
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

    public interface SectionStartListener{
        void onSectionButtonClick(View view);
    }
}
