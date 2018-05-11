package univ.m2acdi.apprentissageborel.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import univ.m2acdi.apprentissageborel.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeFragment extends Fragment {

    private FragmentTransactionListener myListener;

    private ImageView imageView;


    public WelcomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_welcome, container, false);

        imageView = view.findViewById(R.id.start_img_link);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myListener.onStartImageLinkClick();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentTransactionListener) {
            myListener = (FragmentTransactionListener) context;
        } else {
            throw new ClassCastException(context.toString() + " don't implement appropriate listener");
        }
    }

    public interface FragmentTransactionListener{
        void onStartImageLinkClick();
    }

}
