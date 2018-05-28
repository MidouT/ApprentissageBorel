package univ.m2acdi.apprentissageborel.listener;

import android.view.View;

import univ.m2acdi.apprentissageborel.util.BMObject;

public interface AdminConfigListener {

    void onAddNewBtnClicked(View view);

    void onNewBMObjectCreateBtnClicked(BMObject bmObject);

    void onFileUploadBtnClicked();
}
