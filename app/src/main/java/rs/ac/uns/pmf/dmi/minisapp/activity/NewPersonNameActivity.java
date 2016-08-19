package rs.ac.uns.pmf.dmi.minisapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import rs.ac.uns.pmf.dmi.minisapp.R;
import rs.ac.uns.pmf.dmi.minisapp.model.LoginInfo;
import rs.ac.uns.pmf.dmi.minisapp.model.MinisModel;
import rs.ac.uns.pmf.dmi.minisapp.model.Person;
import rs.ac.uns.pmf.dmi.minisapp.model.PersonName;
import rs.ac.uns.pmf.dmi.minisapp.rest.PersonNamesRestAsync;
import rs.ac.uns.pmf.dmi.minisapp.rest.PersonRestAsync;

public class NewPersonNameActivity extends MyActivity {
    private int id;
    private Person person;
    private EditText txtFirstName;
    private EditText txtLastName;
    private EditText txtMiddleName;
    private EditText txtPerson;
    private Button btnSave;
    private Button btnCancel;
    private PersonName personName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_person_name);

        personName = new PersonName();

        txtFirstName = (EditText)findViewById(R.id.txtFirstName);
        txtLastName = (EditText)findViewById(R.id.txtLastName);
        txtMiddleName = (EditText)findViewById(R.id.txtMiddleName);
        txtPerson = (EditText)findViewById(R.id.txtPerson);
        btnSave = (Button)findViewById(R.id.btnPNSave);
        btnCancel = (Button)findViewById(R.id.btnPNCancel);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            this.id = extras.getInt("id");
            Long personID = extras.getLong("personID");
            String token = extras.getString("token");
            LoginInfo loginInfo = new LoginInfo();
            loginInfo.setToken(token);
            super.setLoginInfo(loginInfo);

            showLoadingProgressDialog();
            new PersonRestAsync(this, NewPersonNameActivity.class, personID.toString()).execute();
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                personName.setPerson(person);
                personName.setFirstname(txtFirstName.getText().toString());
                personName.setLastname(txtLastName.getText().toString());
                personName.setMiddleName(txtMiddleName.getText().toString());

                showLoadingProgressDialog();
                new PersonNamesRestAsync(NewPersonNameActivity.this, NewPersonNameActivity.class, personName).execute();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });
    }

    @Override
    public void proceedResult(MinisModel result) {
        dismissProgressDialog();
        if (result.getClass() == Person.class){
            person = (Person)result;
            txtPerson.setText(person.getFirstName() + " " + person.getMiddleName() + " " + person.getLastName());
        }
    }

    @Override
    public void proceedPost(MinisModel result) {
        dismissProgressDialog();
        if (result.getClass() == PersonName.class) {
            Intent data = new Intent();
            data.putExtra("personNameID", ((PersonName) result).getId());
            data.putExtra("person", id);
            setResult(Activity.RESULT_OK, data);
            finish();
        }
    }
}