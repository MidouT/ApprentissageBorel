package univ.m2acdi.apprentissageborel.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import univ.m2acdi.apprentissageborel.R;
import univ.m2acdi.apprentissageborel.activity.MainActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeFragment extends Fragment {

    public WelcomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_welcome, container, false);

        Handler loopHandler = new Handler(Looper.getMainLooper());

        loopHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (getActivity() != null) {

                    ((MainActivity) getActivity()).showFragment(new MenuListFragment());

                }
            }
        }, 7000);

        return view;
    }


}
