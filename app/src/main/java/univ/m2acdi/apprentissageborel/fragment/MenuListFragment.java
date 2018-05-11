package univ.m2acdi.apprentissageborel.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import univ.m2acdi.apprentissageborel.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuListFragment extends Fragment {

    private Button listen_btn;
    private Button what_about_btn;
    private Button gesture_order_btn;
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
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BtnClickListener) {
            myListener = (BtnClickListener) context;
        } else {
            throw new ClassCastException(context.toString() + " don't implement appropriate listener");
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
