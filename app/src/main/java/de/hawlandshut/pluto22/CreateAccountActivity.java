package de.hawlandshut.pluto22;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener {

    static final String TAG = "xx CreateAccount";

    // 3.1 DECLARE UI-VARIABLES
    EditText mEditTextEmail;
    EditText mEditTextPassword1;
    EditText mEditTextPassword2;
    Button mButtonCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        Log.d(TAG, "calledonCreate()");

        // 3.2 INITIALIZE UI-VARIABlES
        mEditTextEmail = findViewById(R.id.createAccountEditTextEmail);
        mEditTextEmail = findViewById(R.id.createAccountEditTextPassword1);
        mEditTextEmail = findViewById(R.id.createAccountEditTextPassword2);
        mButtonCreateAccount = findViewById(R.id.createAccountButtonCreateAccount);

        // 3.3 IMPLEMENT LISTENERS (VERBINDUNG UI-ELEMENTE - LISTENER HERSTELLEN)
        mButtonCreateAccount.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();

        switch (i) {

            case R.id.createAccountButtonCreateAccount:
                doCreateAccount();
                return;

            default:
                return;
        }
    }

    /**
     *
     */
    public void doCreateAccount() {
        Toast.makeText(getApplicationContext(),"called CreateAccount()",Toast.LENGTH_SHORT).show();
    }
}