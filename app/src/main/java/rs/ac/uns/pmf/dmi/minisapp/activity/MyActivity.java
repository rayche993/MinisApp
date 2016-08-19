package rs.ac.uns.pmf.dmi.minisapp.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

import rs.ac.uns.pmf.dmi.minisapp.model.LoginInfo;
import rs.ac.uns.pmf.dmi.minisapp.model.MinisModel;
import rs.ac.uns.pmf.dmi.minisapp.model.User;

/**
 * Created by rayche on 8/12/16.
 */
public class MyActivity extends AppCompatActivity {
    private LoginInfo loginInfo;
    private User myUser;
    private ProgressDialog progressDialog;
    private boolean destroyed = false;

    public MyActivity(){

    }

    public MyActivity(LoginInfo loginInfo, User user){
        this.setLoginInfo(loginInfo);
        this.setMyUser(user);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyed = true;
    }

    public void showLoadingProgressDialog() {
        this.showProgressDialog("Loading. Please wait...");
    }

    public void showProgressDialog(CharSequence message) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setIndeterminate(true);
        }

        progressDialog.setMessage(message);
        progressDialog.show();
    }

    public void dismissProgressDialog() {
        if (progressDialog != null && !destroyed) {
            progressDialog.dismiss();
        }
    }

    public LoginInfo getLoginInfo() {
        return loginInfo;
    }

    public void setLoginInfo(LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
    }


    public void proceedResult(MinisModel result){

    }

    public void proceedResult(MinisModel result, int id) {

    }

    public void proceedPost(MinisModel result){

    }

    public User getMyUser() {
        return myUser;
    }

    public void setMyUser(User myUser) {
        this.myUser = myUser;
    }
}