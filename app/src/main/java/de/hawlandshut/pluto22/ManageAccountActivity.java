package de.hawlandshut.pluto22;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class ManageAccountActivity extends AppCompatActivity implements View.OnClickListener {

    static final String TAG = "xx ManageAccount";

    // 3.1 DECLARE UI-VARIABLES
    TextView mTextViewEmail;
    TextView mTextViewAccountVerified;
    TextView mTextViewId;
    Button mButtonSignOut;
    Button mButtonSendActivationMail;
    Button mButtonDeleteAccount;
    EditText mEditTextPassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_account);
        Log.d(TAG,"called onCreate");

        // 3.2 INITIALIZE UI-VARIABlES
        mTextViewEmail = findViewById(R.id.manageAccountEmail);
        mTextViewAccountVerified = findViewById(R.id.manageAccountTextViewAccountVerified);
        mTextViewId = findViewById(R.id.manageAccountTextViewId);
        mButtonSignOut = findViewById(R.id.manageAccountButtonSignOut);
        mButtonSendActivationMail = findViewById(R.id.manageAccountButtonSendActivationMail);
        mButtonDeleteAccount = findViewById(R.id.manageAccountButtonDeleteAccount);
        mEditTextPassword = findViewById(R.id.manageAccountEditTextPassword);

        // 3.3 IMPLEMENT LISTENERS (VERBINDUNG UI-ELEMENTE - LISTENER HERSTELLEN)
        mButtonSignOut.setOnClickListener(this);
        mButtonSendActivationMail.setOnClickListener(this);
        mButtonDeleteAccount.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();

        switch (i) {
            case R.id.manageAccountButtonSignOut:
                doSignOut();
                return;

            case R.id.manageAccountButtonSendActivationMail:
                doSendActivationMail();
                return;

            case R.id.manageAccountButtonDeleteAccount:
                doDeleteAccount();
                return;

            default:
                return;
        }
    }

    public void doSignOut() {
        Log.d(TAG,"called doSingOut()");
        Toast.makeText(getApplicationContext(), "Clicked doSignOut()", Toast.LENGTH_SHORT).show();
    }

    public void doSendActivationMail() {
        Log.d(TAG,"called doSendActivationMail()");
        Toast.makeText(getApplicationContext(), "Clicked doSendActivationMail()", Toast.LENGTH_SHORT).show();
    }

    public void doDeleteAccount() {
        Log.d(TAG,"called doDeleteAccount()");
        Toast.makeText(getApplicationContext(), "Clicked doDeleteAccount()", Toast.LENGTH_SHORT).show();
    }

}