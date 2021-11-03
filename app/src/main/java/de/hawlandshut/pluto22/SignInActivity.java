package de.hawlandshut.pluto22;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignInActivity extends AppCompatActivity {

    static final String TAG = "xx SignInActivity";

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

        //INIT UI VARIABLES
        mEditTextEmail = (EditText) findViewById(R.id.signInEmail);
        mEditTextPassword = (EditText) findViewById(R.id.signInPassword);
        mButtonSignIn = (Button) findViewById(R.id.signInButtonSignIn);
        mButtonResetPassword = (Button) findViewById(R.id.signInButtonResetPassword);
        mButtonCreateAccount = (Button) findViewById(R.id.signInButtonCreateAccount);
    }

}