package de.hawlandshut.pluto22;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    static final String TAG = "xx SignInActivity";

    // 3.1 DECLARE UI-VARIABLES
    EditText mEditTextEmail;
    EditText mEditTextPassword;
    Button mButtonSignIn;
    Button mButtonResetPassword;
    Button mButtonCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Log.d(TAG,"called onCreate");

        // 3.2 INITIALIZE UI-VARIABlES
        mEditTextEmail = (EditText) findViewById(R.id.signInEmail);
        mEditTextPassword = (EditText) findViewById(R.id.signInPassword);

        mButtonSignIn = (Button) findViewById(R.id.signInButtonSignIn);
        mButtonResetPassword = (Button) findViewById(R.id.signInButtonResetPassword);
        mButtonCreateAccount = (Button) findViewById(R.id.signInButtonCreateAccount);

        // 3.3 IMPLEMENT LISTENERS (VERBINDUNG UI-ELEMENTE - LISTENER HERSTELLEN)
        mButtonSignIn.setOnClickListener(this);
        mButtonResetPassword.setOnClickListener(this);
        mButtonCreateAccount.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        //Log.d(TAG, "ID= " + v.getId());
        //Toast.makeText(getApplicationContext(), "Clicked " + v.getId(), Toast.LENGTH_SHORT).show();

        int i = v.getId();
        switch (i) {
            case R.id.signInButtonSignIn:
                doSignIn();
                return;

            case R.id.signInButtonResetPassword:
                doResetPassword();
                return;

            case R.id.signInButtonCreateAccount:
                doCreateAccount();
                return;

            default:
                return;
        }
    }

    /**
     *
     */
    public void doSignIn() {
        Log.d(TAG,"called doSignIn()");

    }

    /**
     *
     */
    public void doResetPassword() {
        Log.d(TAG,"called doResetPassword()");

    }

    /**
     *
     */
    public void doCreateAccount() {
        Log.d(TAG,"called doCreateAccount()");
    }
}


























