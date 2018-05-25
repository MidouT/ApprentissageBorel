package univ.m2acdi.apprentissageborel.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import univ.m2acdi.apprentissageborel.R;

public class AdminMainActivity extends Activity {

    private Button appDataConfigBtn;
    private Button trainSectionBtn;
    private ImageButton trainMoreBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        appDataConfigBtn = findViewById(R.id.app_data_config_btnId);
        appDataConfigBtn.setOnClickListener(btnClickListener);

        trainSectionBtn = findViewById(R.id.app_train_section_btnId);
        trainSectionBtn.setOnClickListener(btnClickListener);

        trainMoreBtn = findViewById(R.id.app_train_more_btnId);
        trainMoreBtn.setOnClickListener(btnClickListener);
    }


    protected View.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent();
            int id = v.getId();

            switch (id){
                case R.id.app_data_config_btnId:
                    intent.setClass(getApplicationContext(),DataConfigActivity.class);
                    break;
                case R.id.app_train_section_btnId:
                    intent.setClass(getApplicationContext(),TrainSectionActivity.class);
                    break;
                case R.id.app_train_more_btnId:
                    intent.setClass(getApplicationContext(),TrainSectionActivity.class);
                    break;
                    default:
                        break;
            }
            startActivity(intent);

        }
    };
}
