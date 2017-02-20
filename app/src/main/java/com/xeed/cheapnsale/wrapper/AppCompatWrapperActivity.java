package com.xeed.cheapnsale.wrapper;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

public class AppCompatWrapperActivity extends AppCompatActivity{

    private ProgressDialog mProgressDialog;

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("loading");
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setCancelable(false);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    public void progressDialogDismiss(){
        mProgressDialog.dismiss();
    }

}
