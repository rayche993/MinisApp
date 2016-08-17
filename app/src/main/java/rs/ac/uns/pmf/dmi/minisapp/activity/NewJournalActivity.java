package rs.ac.uns.pmf.dmi.minisapp.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.springframework.http.HttpMethod;

import rs.ac.uns.pmf.dmi.minisapp.R;
import rs.ac.uns.pmf.dmi.minisapp.model.Journal;
import rs.ac.uns.pmf.dmi.minisapp.model.LoginInfo;
import rs.ac.uns.pmf.dmi.minisapp.model.MinisModel;
import rs.ac.uns.pmf.dmi.minisapp.rest.JournalsRestAsync;

public class NewJournalActivity extends MyActivity {
    private Journal journal;
    private EditText txtName;
    private EditText txtEIssn;
    private EditText txtPIssn;
    private EditText txtAbbreviation;
    private EditText txtNote;
    private EditText txtAbstract;
    private EditText txtUri;
    private EditText txtDoi;

    public NewJournalActivity(){

    }

    @Override
    public void proceedResult(MinisModel result){
        journal = (Journal)result;
        if (journal != null){
            setFields();
        }

        dismissProgressDialog();
    }

    @Override
    public void proceedPost(MinisModel result){
        journal = (Journal)result;
        dismissProgressDialog();
        Intent data = new Intent();
        data.putExtra("journalID", journal.getId().toString());
        setResult(Activity.RESULT_OK, data);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_journal);
        txtName = (EditText)findViewById(R.id.txtName);
        txtEIssn = (EditText)findViewById(R.id.txtEIssn);
        txtPIssn = (EditText)findViewById(R.id.txtPIssn);
        txtAbbreviation = (EditText)findViewById(R.id.txtAbbreviation);
        txtNote = (EditText)findViewById(R.id.txtNote);
        txtAbstract = (EditText)findViewById(R.id.txtAbstract);
        txtUri = (EditText)findViewById(R.id.txtUri);
        txtDoi = (EditText)findViewById(R.id.txtDoi);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            String token = extras.getString("token");
            LoginInfo loginInfo = new LoginInfo();
            loginInfo.setToken(token);
            setLoginInfo(loginInfo);

            int requestCode = extras.getInt("requestCode");

            if (requestCode == 2){
                showLoadingProgressDialog();
                Long id = extras.getLong("journalID");
                new JournalsRestAsync(NewJournalActivity.this, NewJournalActivity.class, Long.toString(id)).execute();
            }
        }

        Button btnSave = (Button)findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingProgressDialog();
                setJournal();
                Bundle extras = getIntent().getExtras();
                int requestCode = extras.getInt("requestCode");

                if (requestCode == 1)
                    new JournalsRestAsync(NewJournalActivity.this, NewJournalActivity.class, HttpMethod.POST, journal).execute();
                else
                    new JournalsRestAsync(NewJournalActivity.this, NewJournalActivity.class, HttpMethod.PUT, journal).execute();
            }
        });

        Button btnCancel = (Button)findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });
    }

    private void setJournal(){
        if (journal == null)
            journal = new Journal();

        journal.setName(txtName.getText().toString());
        journal.setEissn(txtEIssn.getText().toString());
        journal.setPissn(txtEIssn.getText().toString());
        journal.setAbbreviation(txtAbbreviation.getText().toString());
        journal.setNote(txtNote.getText().toString());
        journal.setAbstracT(txtAbstract.getText().toString());
        journal.setUri(txtUri.getText().toString());
        journal.setDoi(txtDoi.getText().toString());
    }

    private void setFields(){
        txtName.setText(journal.getName());
        txtEIssn.setText(journal.getEissn());
        txtPIssn.setText(journal.getPissn());
        txtAbbreviation.setText(journal.getAbbreviation());
        txtNote.setText(journal.getNote());
        txtAbstract.setText(journal.getAbstracT());
        txtUri.setText(journal.getUri());
        txtDoi.setText(journal.getDoi());
    }
}
