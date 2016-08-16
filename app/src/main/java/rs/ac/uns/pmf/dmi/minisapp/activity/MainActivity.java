package rs.ac.uns.pmf.dmi.minisapp.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Button btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingProgressDialog();
                username = ((EditText)findViewById(R.id.txtUsername)).getText().toString();
                password = ((EditText)findViewById(R.id.txtPassword)).getText().toString();
                new AuthRestAsync(MainActivity.this, MainActivity.class, HttpMethod.POST, username, password).execute();
            }
        });
    }

    @Override
    public void proceedResult(MinisModel result){
        super.setLoginInfo((LoginInfo)result);
        dismissProgressDialog();
        Intent menu = new Intent(this, MenuActivity.class);
        menu.putExtra("token", getLoginInfo().getToken());
        startActivity(menu);
    }
}
/*
        //Authentication
        final Button btnAuth = (Button)findViewById(R.id.btnAuth);
        btnAuth.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new AuthRestAsync(MainActivity.this, MainActivity.class, HttpMethod.POST, "admin@localhost.com", "admin").execute();
            }
        });

        //Authorization
        final Button btnAcc = (Button)findViewById(R.id.btnAcc);
        btnAcc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new AccountRestAsync(MainActivity.this, MainActivity.class, HttpMethod.GET).execute();
            }
        });

        //Journals
        final Button btnJournals = (Button)findViewById(R.id.btnJournals);
        btnJournals.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new JournalsRestAsync(MainActivity.this, MainActivity.class, HttpMethod.GET, "").execute();
            }
        });

        //Journal
        final Button btnJournal = (Button)findViewById(R.id.btnJournal);
        btnJournal.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new JournalsRestAsync(MainActivity.this, MainActivity.class, HttpMethod.GET, "36").execute();
            }
        });

        //Languages
        final Button btnLanguages = (Button)findViewById(R.id.btnLanguages);
        btnLanguages.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new LanguageRestAsync(MainActivity.this, MainActivity.class, HttpMethod.GET, "").execute();
            }
        });

        //Language
        final Button btnLanguage = (Button)findViewById(R.id.btnLanguage);
        btnLanguage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new LanguageRestAsync(MainActivity.this, MainActivity.class, HttpMethod.GET, "1").execute();
            }
        });*/




/*if (result.getClass() == LoginInfo.class) {
            super.setLoginInfo((LoginInfo) result);
            Log.e("TOKEN", ((LoginInfo) result).getToken());
        }else if (result.getClass() == User.class){
            Log.e("USER", ((User) result).getLogin());
        }else if((result.getClass() == RestJournal.class) || (result.getClass() == Journal.class)){
            if (result.getClass() == RestJournal.class)
                Log.e("Journals", Integer.toString(((RestJournal) result).getJournals().size()));
            else
                Log.e("Journal", ((Journal)result).getName());

        }else if((result.getClass() == RestLanguage.class) || (result.getClass() == Language.class)){
            if (result.getClass() == RestLanguage.class)
                Log.e("Languages", Integer.toString(((RestLanguage) result).getNumberOfElements()));
            else
                Log.e("Language", ((Language)result).getName());
        }*/