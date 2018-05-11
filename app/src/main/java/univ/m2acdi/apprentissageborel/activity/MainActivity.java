package univ.m2acdi.apprentissageborel.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import univ.m2acdi.apprentissageborel.fragment.MenuListFragment;
import univ.m2acdi.apprentissageborel.R;
import univ.m2acdi.apprentissageborel.fragment.WelcomeFragment;

public class MainActivity extends AppCompatActivity implements WelcomeFragment.FragmentTransactionListener, MenuListFragment.BtnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showFragment(new WelcomeFragment());
    }

    @Override
    public void onStartImageLinkClick() {
        showFragment(new MenuListFragment());
    }

    void showFragment(Fragment fragment){

        String TAG = fragment.getClass().getSimpleName();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentContainer, fragment, TAG);
        ft.commit();
    }

    @Override
    public void onBtnClick(View view) {
        switch (view.getId()) {

            case R.id.listen_btn:
                Intent intent = new Intent(this, TextToSpeechActivity.class);
                startActivity(intent);
                break;

            case R.id.what_about_btn:

                break;

            case R.id.gesture_order_btn:

                break;

        }
    }
}
