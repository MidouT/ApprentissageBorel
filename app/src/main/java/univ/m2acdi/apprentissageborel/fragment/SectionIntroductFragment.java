package univ.m2acdi.apprentissageborel.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import univ.m2acdi.apprentissageborel.R;
import univ.m2acdi.apprentissageborel.activity.SectionIntroductActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SectionIntroductFragment extends Fragment {

    private TextView sectionIntroductTitle;

    public SectionIntroductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_section_introduct, container, false);

        sectionIntroductTitle = view.findViewById(R.id.section_introduct_title);

        Handler loopHandler = new Handler(Looper.getMainLooper());

        loopHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (getActivity() != null) {

                    ((SectionIntroductActivity) getActivity()).goToSection();

                }
            }
        }, 7000);

        setSectionTitle();

        return view;
    }


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
