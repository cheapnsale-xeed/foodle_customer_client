package com.xeed.cheapnsale.wrapper;

import android.app.ProgressDialog;

import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;

public class SlidingWrapperActivity extends SlidingActivity {

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
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
}
