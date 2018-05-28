package univ.m2acdi.apprentissageborel.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import univ.m2acdi.apprentissageborel.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuListFragment extends Fragment {

    private ImageView listen_btn;
    private ImageView what_about_btn;
    private ImageView gesture_order_btn;

    private BtnClickListener myListener;

    public MenuListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_list, container, false);

        listen_btn = view.findViewById(R.id.listen_btn);
        listen_btn.setOnClickListener(onClickListener);

        what_about_btn = view.findViewById(R.id.what_about_btn);
        what_about_btn.setOnClickListener(onClickListener);

        gesture_order_btn = view.findViewById(R.id.gesture_order_btn);
        gesture_order_btn.setOnClickListener(onClickListener);

        return view ;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof BtnClickListener) {
            myListener = (BtnClickListener) activity;
        } else {
            throw new ClassCastException(activity.toString() + " don't implement appropriate listener");
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            myListener.onBtnClick(view);
        }
    };

    public interface BtnClickListener {
        void onBtnClick(View view);
    }

}
