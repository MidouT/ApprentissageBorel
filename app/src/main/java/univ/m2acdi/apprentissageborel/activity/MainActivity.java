package univ.m2acdi.apprentissageborel.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import univ.m2acdi.apprentissageborel.R;
import univ.m2acdi.apprentissageborel.fragment.MenuListFragment;
import univ.m2acdi.apprentissageborel.fragment.WelcomeFragment;
import univ.m2acdi.apprentissageborel.util.TextSpeaker;

public class MainActivity extends Activity implements AppCompatCallback, MenuListFragment.BtnClickListener {

    private final int CHECK_CODE = 0x1;

    private TextSpeaker textSpeaker;

    private AppCompatDelegate delegate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //let's create the delegate, passing the activity at both arguments
        delegate = AppCompatDelegate.create(this, this);

        //the installViewFactory method replaces the default widgets
        //with the AppCompat-tinted versions
        delegate.installViewFactory();

        super.onCreate(savedInstanceState);

        //we need to call the onCreate() of the AppCompatDelegate
        delegate.onCreate(savedInstanceState);

        //we use the delegate to inflate the layout
        delegate.setContentView(R.layout.activity_main);

        //Finally, let's add the Toolbar
        Toolbar toolbar= findViewById(R.id.my_awesome_toolbar);
        delegate.setSupportActionBar(toolbar);


        checkTTS();

        showFragment(new WelcomeFragment());
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    public void showFragment(Fragment fragment){

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRestart() {
        super.onRestart();
        //Toast.makeText(this, null, Toast.LENGTH_SHORT).show();
        checkTTS();
    }


    @Override
    public void onSupportActionModeStarted(ActionMode mode) {

    }

    @Override
    public void onSupportActionModeFinished(ActionMode mode) {

    }

    @Nullable
    @Override
    public ActionMode onWindowStartingSupportActionMode(ActionMode.Callback callback) {
        return null;
    }
}
