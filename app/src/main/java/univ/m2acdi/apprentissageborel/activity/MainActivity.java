package univ.m2acdi.apprentissageborel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import univ.m2acdi.apprentissageborel.R;
import univ.m2acdi.apprentissageborel.fragment.MenuListFragment;
import univ.m2acdi.apprentissageborel.fragment.WelcomeFragment;
import univ.m2acdi.apprentissageborel.util.TextSpeaker;

public class MainActivity extends AppCompatActivity implements WelcomeFragment.FragmentTransactionListener, MenuListFragment.BtnClickListener {

    private final int CHECK_CODE = 0x1;

    private TextSpeaker textSpeaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkTTS();

        showFragment(new WelcomeFragment());
    }

    @Override
    public void onStartImageLinkClick() {
        showFragment(new MenuListFragment());
    }

    void showFragment(Fragment fragment){

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentContainer, fragment, null);
        ft.commit();
    }

    @Override
    public void onBtnClick(View view) {

        Intent intent = new Intent(this, SectionIntroductActivity.class);
        intent.putExtra("speaker", textSpeaker);
        switch (view.getId()) {

            case R.id.listen_btn:
                intent.putExtra("section", 1);

                break;

            case R.id.what_about_btn:
                intent.putExtra("section", 2);
                break;

            case R.id.gesture_order_btn:
                intent.putExtra("section", 3);
                break;

        }
        startActivity(intent);
    }

    private void checkTTS(){
        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkIntent, CHECK_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CHECK_CODE: {
                if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                    textSpeaker = new TextSpeaker(this);
                } else {
                    Intent install = new Intent();
                    install.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                    startActivity(install);
                }
            }
            default:
                break;
        }
    }

    @Override
    public void onRestart() {
        super.onRestart();
        //Toast.makeText(this, null, Toast.LENGTH_SHORT).show();
        checkTTS();
    }


}
