package com.practice.realmexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.jar.Manifest;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    EditText mName, mSurname;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mName = (EditText) findViewById(R.id.et_name);
        mSurname = (EditText) findViewById(R.id.et_surname);
    }

    public void showData(View view) {

        realm = Realm.getDefaultInstance();

        RealmResults realmResults = realm.where(PersonModel.class).findAll();
        Log.d("RealmExample", "the data" + realmResults);
    }

    public void saveToDb(View view) {

        Log.d("RealmExample", "Saving to db");

         realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                PersonModel person = bgRealm.createObject(PersonModel.class);
                person.setName(mName.getText().toString());
                person.setSurname(mSurname.getText().toString());
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Transaction was a success.
                Toast.makeText(getApplicationContext(), "Successful save", Toast.LENGTH_SHORT).show();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.
                Toast.makeText(getApplicationContext(), "Error in saving", Toast.LENGTH_SHORT).show();
            }
        });
       // mName.setText("");
      //  mSurname.setText("");
    }
}
