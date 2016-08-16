package rs.ac.uns.pmf.dmi.minisapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import rs.ac.uns.pmf.dmi.minisapp.R;
import rs.ac.uns.pmf.dmi.minisapp.activity.MyActivity;
import rs.ac.uns.pmf.dmi.minisapp.model.Journal;
import rs.ac.uns.pmf.dmi.minisapp.model.LoginInfo;
import rs.ac.uns.pmf.dmi.minisapp.model.MinisModel;
import rs.ac.uns.pmf.dmi.minisapp.model.RestJournal;
import rs.ac.uns.pmf.dmi.minisapp.model.User;
import rs.ac.uns.pmf.dmi.minisapp.rest.JournalsRestAsync;

public class JournalsActivity extends MyActivity {
    private JournalsAdapter journalsAdapter;
    static private final int ADD_JOURNAL_REQUEST_CODE = 1;
    static private final int EDIT_JOURNAL_REQUEST_CODE = 2;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journals);

        listView = (ListView)findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Journal journal = (Journal)parent.getItemAtPosition(position);
                Intent editJournal = new Intent(JournalsActivity.this, NewJournalActivity.class);
                editJournal.putExtra("token", getLoginInfo().getToken());
                editJournal.putExtra("journalID", journal.getId());
                editJournal.putExtra("requestCode", EDIT_JOURNAL_REQUEST_CODE);
                startActivityForResult(editJournal, EDIT_JOURNAL_REQUEST_CODE);
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String token = extras.getString("token");
            LoginInfo loginInfo = new LoginInfo();
            loginInfo.setToken(token);
            setLoginInfo(loginInfo);
            showLoadingProgressDialog();
            new JournalsRestAsync(JournalsActivity.this, JournalsActivity.class, "").execute();
        }

        Button btnAddJournal = (Button)findViewById(R.id.btnAddJournal);
        btnAddJournal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addJournal = new Intent(JournalsActivity.this, NewJournalActivity.class);
                addJournal.putExtra("token", getLoginInfo().getToken());
                addJournal.putExtra("requestCode", ADD_JOURNAL_REQUEST_CODE);
                startActivityForResult(addJournal, ADD_JOURNAL_REQUEST_CODE);
            }
        });
    }

    @Override
    public void proceedResult(MinisModel result){
        if (result.getClass() == RestJournal.class){
            journalsAdapter = new JournalsAdapter(((RestJournal)result).getJournals());
            listView.setAdapter(journalsAdapter);
        }else if (result.getClass() == Journal.class){
            Journal journal = (Journal)result;
            journalsAdapter.add(journal);
        }
        dismissProgressDialog();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_CANCELED)
            Toast.makeText(this, "Journal isn't saved!", Toast.LENGTH_LONG).show();
        else {
            if (requestCode == ADD_JOURNAL_REQUEST_CODE)
                Toast.makeText(this, "Successfully saved new journal!", Toast.LENGTH_LONG).show();

            else if (requestCode == EDIT_JOURNAL_REQUEST_CODE)
                Toast.makeText(this, "Successfully edited journal!", Toast.LENGTH_LONG).show();

            Bundle extras = data.getExtras();
            if (extras != null) {
                String journalID = extras.getString("journalID");
                showLoadingProgressDialog();
                new JournalsRestAsync(JournalsActivity.this, JournalsActivity.class, journalID).execute();
            }
        }

    }
}