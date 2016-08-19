package rs.ac.uns.pmf.dmi.minisapp.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import rs.ac.uns.pmf.dmi.minisapp.R;
import rs.ac.uns.pmf.dmi.minisapp.model.Journal;
import rs.ac.uns.pmf.dmi.minisapp.model.Language;
import rs.ac.uns.pmf.dmi.minisapp.model.LoginInfo;
import rs.ac.uns.pmf.dmi.minisapp.model.MinisModel;
import rs.ac.uns.pmf.dmi.minisapp.model.Problem;
import rs.ac.uns.pmf.dmi.minisapp.model.RestJournal;
import rs.ac.uns.pmf.dmi.minisapp.model.RestLanguage;
import rs.ac.uns.pmf.dmi.minisapp.model.User;
import rs.ac.uns.pmf.dmi.minisapp.rest.AccountRestAsync;
import rs.ac.uns.pmf.dmi.minisapp.rest.AuthRestAsync;
import rs.ac.uns.pmf.dmi.minisapp.rest.JournalsRestAsync;
import rs.ac.uns.pmf.dmi.minisapp.rest.LanguageRestAsync;

public class MainActivity extends MyActivity {
    private String username;
    private String password;
    private EditText txtUsername;
    private EditText txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Button btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingProgressDialog();
                txtUsername = (EditText)findViewById(R.id.txtUsername);
                txtPassword = (EditText)findViewById(R.id.txtPassword);
                username = txtUsername.getText().toString();
                password = txtPassword.getText().toString();

                new AuthRestAsync(MainActivity.this, MainActivity.class, HttpMethod.POST, username, password).execute();
            }
        });
    }

    @Override
    public void proceedResult(MinisModel result){
        if (result.getClass() == LoginInfo.class) {
            super.setLoginInfo((LoginInfo) result);
            dismissProgressDialog();
            Intent menu = new Intent(this, MenuActivity.class);
            menu.putExtra("token", getLoginInfo().getToken());
            startActivity(menu);
        }else if (result.getClass() == Problem.class){
            dismissProgressDialog();
            txtUsername.setText("");
            txtPassword.setText("");
            username = "";
            password = "";
            Toast.makeText(this, ((Problem)result).getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}