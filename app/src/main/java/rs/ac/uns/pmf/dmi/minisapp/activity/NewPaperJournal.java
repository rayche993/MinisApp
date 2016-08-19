package rs.ac.uns.pmf.dmi.minisapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.List;

import rs.ac.uns.pmf.dmi.minisapp.R;
import rs.ac.uns.pmf.dmi.minisapp.model.Journal;
import rs.ac.uns.pmf.dmi.minisapp.model.Language;
import rs.ac.uns.pmf.dmi.minisapp.model.LoginInfo;
import rs.ac.uns.pmf.dmi.minisapp.model.MinisModel;
import rs.ac.uns.pmf.dmi.minisapp.model.PaperJournal;
import rs.ac.uns.pmf.dmi.minisapp.model.PaperType;
import rs.ac.uns.pmf.dmi.minisapp.model.Person;
import rs.ac.uns.pmf.dmi.minisapp.model.PersonName;
import rs.ac.uns.pmf.dmi.minisapp.model.RestLanguage;
import rs.ac.uns.pmf.dmi.minisapp.model.RestPaperType;
import rs.ac.uns.pmf.dmi.minisapp.rest.LanguageRestAsync;
import rs.ac.uns.pmf.dmi.minisapp.rest.PaperJournalsRestAsync;
import rs.ac.uns.pmf.dmi.minisapp.rest.PaperTypeRestAsync;
import rs.ac.uns.pmf.dmi.minisapp.rest.PersonNamesRestAsync;

public class NewPaperJournal extends MyActivity {
    private PaperJournal paperJournal;
    private Spinner languageSpinner;
    private Spinner paperTypeSpinner;
    private EditText txtTitle;
    private EditText txtFirstPage;
    private EditText txtLastPage;
    private EditText txtVolume;
    private EditText txtNumber;
    private EditText txtYear;
    private EditText txtDoi;
    private ListView authorsListView;
    private PersonNameAdapter authorsListAdapter;
    private AutoCompleteTextView personNameAutoComplete;
    static private final int ADD_PERSON_NAME_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_paper_journal);

        txtTitle = (EditText)findViewById(R.id.txtTitle);
        txtFirstPage = (EditText)findViewById(R.id.txtFirstPage);
        txtLastPage = (EditText)findViewById(R.id.txtLastPage);
        txtVolume = (EditText)findViewById(R.id.txtVolume);
        txtNumber = (EditText)findViewById(R.id.txtNumber);
        txtYear = (EditText)findViewById(R.id.txtYear);
        txtDoi = (EditText)findViewById(R.id.txtPJDoi);

        paperJournal = new PaperJournal();

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            LoginInfo loginInfo = new LoginInfo();
            loginInfo.setToken(extras.getString("token"));
            super.setLoginInfo(loginInfo);
        }

        new LanguageRestAsync(NewPaperJournal.this, NewPaperJournal.class, HttpMethod.GET, "").execute();
        new PaperTypeRestAsync(NewPaperJournal.this, NewPaperJournal.class, HttpMethod.GET, "").execute();

        authorsListView = (ListView)findViewById(R.id.authorsListView);
        authorsListAdapter = new PersonNameAdapter(this, new ArrayList<List<PersonName>>());
        authorsListView.setAdapter(authorsListAdapter);

        AutoCompleteTextView journalAutoComplete = (AutoCompleteTextView)findViewById(R.id.autoCompleteJournal);
        ACJournalAdapter adapter = new ACJournalAdapter(this, R.layout.auto_complete_list_item, getLoginInfo().getToken());
        journalAutoComplete.setAdapter(adapter);

        journalAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                paperJournal.setJournal((Journal)parent.getAdapter().getItem(position));
            }
        });

        personNameAutoComplete = (AutoCompleteTextView)findViewById(R.id.autoCompleteAuthors);
        ACPersonNameAdapter personNameACAdapter = new ACPersonNameAdapter(this, R.layout.auto_complete_list_item, getLoginInfo().getToken());
        personNameAutoComplete.setAdapter(personNameACAdapter);

        personNameAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                authorsListAdapter.add((PersonName)parent.getAdapter().getItem(position));
                personNameAutoComplete.setText("");
            }
        });

        Button btnPJCancel = (Button)findViewById(R.id.btnPJCancel);
        btnPJCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });

        Button btnPJSave = (Button)findViewById(R.id.btnPJSave);
        btnPJSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paperJournal.setTitle(txtTitle.getText().toString());
                paperJournal.setStartPage(txtFirstPage.getText().toString());
                paperJournal.setEndPage(txtLastPage.getText().toString());
                paperJournal.setVolume(txtVolume.getText().toString());
                paperJournal.setNumber(txtNumber.getText().toString());
                paperJournal.setYear(txtYear.getText().toString());
                paperJournal.setDoi(txtDoi.getText().toString());

                new PaperJournalsRestAsync(NewPaperJournal.this, NewPaperJournal.class, paperJournal);
            }
        });

        languageSpinner = (Spinner)findViewById(R.id.languageSpiner);
        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                paperJournal.setLanguage((Language)parent.getAdapter().getItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        paperTypeSpinner = (Spinner)findViewById(R.id.paperTypeSpiner);
        paperTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                paperJournal.setPaperType((PaperType)parent.getAdapter().getItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void addPersonName(int id, Person person){
        Intent newPersonName = new Intent(this, NewPersonNameActivity.class);
        newPersonName.putExtra("id", id);
        newPersonName.putExtra("personID", person.getId());
        newPersonName.putExtra("token", getLoginInfo().getToken());
        startActivityForResult(newPersonName, ADD_PERSON_NAME_REQUEST_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_PERSON_NAME_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Bundle extras = data.getExtras();
            if (extras != null){
                int person = extras.getInt("person");
                Long personNameID = extras.getLong("personNameID");

                new PersonNamesRestAsync(this, NewPaperJournal.class, personNameID.toString(), person).execute();
            }
        }
    }

    @Override
    public void proceedResult(MinisModel result) {
        if (result.getClass() == RestLanguage.class) {
            RestLanguage rest = (RestLanguage)result;
            ArrayAdapter<Language> adapter = new ArrayAdapter<Language>(this, R.layout.dropdown_item, rest.getContent());
            languageSpinner.setAdapter(adapter);
        }else if (result.getClass() == RestPaperType.class){
            RestPaperType rest = (RestPaperType)result;
            ArrayAdapter<PaperType> adapter = new ArrayAdapter<PaperType>(this, R.layout.dropdown_item, rest.getContent());
            paperTypeSpinner.setAdapter(adapter);
        }
    }

    @Override
    public void proceedResult(MinisModel result, int id) {
        if (result.getClass() == PersonName.class){
            authorsListAdapter.add((PersonName)result, id);
        }
    }

    @Override
    public void proceedPost(MinisModel result) {
        if (result.getClass() == PaperJournal.class){
            setResult(Activity.RESULT_OK);
            finish();
        }
    }
}