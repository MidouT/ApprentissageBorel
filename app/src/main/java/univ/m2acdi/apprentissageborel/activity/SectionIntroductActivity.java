package univ.m2acdi.apprentissageborel.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import univ.m2acdi.apprentissageborel.R;
import univ.m2acdi.apprentissageborel.fragment.SectionIntroductFragment;
import univ.m2acdi.apprentissageborel.util.TextSpeaker;

public class SectionIntroductActivity extends Activity {

    private final int SHORT_DURATION = 1000;

    private int section;

    private TextSpeaker textSpeaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_introduct);

        section = getIntent().getExtras().getInt("section");
        textSpeaker = (TextSpeaker) getIntent().getSerializableExtra("speaker");

        Toast.makeText(this, "Section" + section, Toast.LENGTH_SHORT).show();

        introductSection();

    }



    View.OnClickListener readListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //speakOut();
        }
    };

    private void introductSection() {

        String text = "";

        switch (section){
            case 1:
                text = this.getResources().getString(R.string.introduct_section_1);
                break;
            case 2:
                text = this.getResources().getString(R.string.introduct_section_2);
                break;
            case 3:
                text = this.getResources().getString(R.string.introduct_section_3);
                break;

                default:
                    break;
        }

        speakOut(text);
    }

    protected void speakOut(String text) {
        if (!textSpeaker.isSpeaking()) {
            textSpeaker.speakText(text);
            textSpeaker.pause(SHORT_DURATION);
        }

    }


    @Override
    protected void onStop() {

        super.onStop();
    }
}
