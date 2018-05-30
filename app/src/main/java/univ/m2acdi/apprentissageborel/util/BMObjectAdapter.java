package univ.m2acdi.apprentissageborel.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import univ.m2acdi.apprentissageborel.R;

public class BMObjectAdapter extends ArrayAdapter<BMObject> {

    private int xmlRessoruceID;
    private TextView tvSon;
    private TextView tvGraphie;
    private TextView tvRefText;
    private ImageView ivGeste;

    public BMObjectAdapter(Context context, int resource, ArrayList<BMObject> bmObjectList) {
        super(context, resource, bmObjectList);
        xmlRessoruceID = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View myView;

        BMObject bmObject = getItem(position);

        //Check if an existing view is being reused, otherwise inflate the view
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            myView = layoutInflater.inflate(xmlRessoruceID, parent, false);
        }else{
            myView = convertView;
        }


        tvSon = myView.findViewById(R.id.raw_word_textView);
        tvSon.setText(bmObject.getSon());
        tvGraphie = myView.findViewById(R.id.raw_graphie_textView);
        tvGraphie.setText(Util.getFormatedGraphieStr(bmObject.getGraphie()));
        tvRefText = myView.findViewById(R.id.raw_refText_textView);
        tvRefText.setText(bmObject.getTexte_ref());
        ivGeste = myView.findViewById(R.id.raw_geste_imageView);
        ivGeste.setImageDrawable(Util.getImageViewByName(getContext(), bmObject.getGeste()));

        return myView;
    }
}
