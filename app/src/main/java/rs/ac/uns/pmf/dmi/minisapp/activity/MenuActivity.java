package rs.ac.uns.pmf.dmi.minisapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.springframework.http.HttpMethod;

import rs.ac.uns.pmf.dmi.minisapp.R;
import rs.ac.uns.pmf.dmi.minisapp.model.Journal;
import rs.ac.uns.pmf.dmi.minisapp.model.LoginInfo;
import rs.ac.uns.pmf.dmi.minisapp.model.MinisModel;
import rs.ac.uns.pmf.dmi.minisapp.model.User;
import rs.ac.uns.pmf.dmi.minisapp.rest.AccountRestAsync;
import rs.ac.uns.pmf.dmi.minisapp.rest.JournalsRestAsync;

public class MenuActivity extends MyActivity {
    static private final int ADD_PAPER_JOURNAL_REQUEST_CODE = 1;
    static private final int SHOW_POPULAR_JOURNALS_REQUEST_CODE = 2;
    static private final int SHOW_JOURNALS_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            String token = extras.getString("token");
            LoginInfo info = new LoginInfo();
            info.setToken(token);
            super.setLoginInfo(info);
            //showLoadingProgressDialog();
            new AccountRestAsync(MenuActivity.this, MenuActivity.class, HttpMethod.GET).execute();
        }

        Button btnJournals = (Button)findViewById(R.id.btnJournals);
        btnJournals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent journals = new Intent(MenuActivity.this, JournalsActivity.class);
                journals.putExtra("token", getLoginInfo().getToken());
                journals.putExtra("request_code", SHOW_JOURNALS_REQUEST_CODE);
                startActivity(journals);
            }
        });

        Button btnAddPaperJournal = (Button)findViewById(R.id.btnAddPaperJournal);
        btnAddPaperJournal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addPaperJournal = new Intent(MenuActivity.this, NewPaperJournal.class);
                addPaperJournal.putExtra("token", getLoginInfo().getToken());
                startActivityForResult(addPaperJournal, ADD_PAPER_JOURNAL_REQUEST_CODE);
            }
        });

        Button btnPopularJournals = (Button)findViewById(R.id.btnPopularJournals);
        btnPopularJournals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent journals = new Intent(MenuActivity.this, JournalsActivity.class);
                journals.putExtra("token", getLoginInfo().getToken());
                journals.putExtra("request_code", SHOW_POPULAR_JOURNALS_REQUEST_CODE);
                startActivity(journals);
            }
        });
    }

    @Override
    public void proceedResult(MinisModel result){
        super.setMyUser((User)result);
        TextView txtWelcome = (TextView)findViewById(R.id.lblWelcome);
        txtWelcome.setText(txtWelcome.getText() + " " + getMyUser().getLogin() + "!");
        //dismissProgressDialog();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == ADD_PAPER_JOURNAL_REQUEST_CODE)
            Toast.makeText(this, "Paper Journal is successfully saved!", Toast.LENGTH_LONG).show();
        if (resultCode == Activity.RESULT_CANCELED && requestCode == ADD_PAPER_JOURNAL_REQUEST_CODE)
            Toast.makeText(this, "Paper Journal isn't saved!", Toast.LENGTH_LONG).show();
    }
}