package de.hawlandshut.pluto22;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener {

    static final String TAG = "xx CreateAccount";

    /* DEKLARATION UI-VARIABELN */
    EditText mEditTextEmail;
    EditText mEditTextPassword1;
    EditText mEditTextPassword2;
    Button mButtonCreateAccount;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        Log.d(TAG, "calledonCreate()");

        // INITIALISIERUNG DER UI-VARIABELN
        mEditTextEmail = findViewById(R.id.createAccountEditTextEmail);
        mEditTextPassword1 = findViewById(R.id.createAccountEditTextPassword1);
        mEditTextPassword2 = findViewById(R.id.createAccountEditTextPassword2);
        mButtonCreateAccount = findViewById(R.id.createAccountButtonCreateAccount);

        // BUTTON LISTENER
        mButtonCreateAccount.setOnClickListener(this);
    }

    /**
     *
     * ONCLICK LISTENER FÜR BUTTONS
     *
     * @param v
     */
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
     * MIT DER METHODE WIRD EIN NEUER ACCOUNT ERSTELLT
     *
     * WIR NUTZEN DAFÜR AUCH WIEDER DIE FIRABASE FUNKTIONALITÄT
     * UM EINEN USER ANZULEGEN. DER USER WIRD DANN IN FIREBASE GESPEICHERT
     *
     * WAS HIER NOCH PROGRAMMIERT WERDEN MÜSSTE IST, OB IN DEN ZWEI PASSWORD-
     * FELDERN AUCH DAS GLEICHE PASSWORT ZWEI MAL DRIN STEHT ODER NICHT
     *
     */
    public void doCreateAccount() {
        String email = mEditTextEmail.getText().toString();
        String password = mEditTextPassword1.getText().toString();

        //TODO: CHECK EQUALITY OF PASSWORDS


        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(
                        this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    //ERFOLGSFALL
                                    Toast.makeText(getApplicationContext(),"Created user!",Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    //FEHLERFALL
                                    Toast.makeText(getApplicationContext(),"User creation FAILED!",Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "CREATE ACCOUNT ERROR: " + task.getException());
                                }
                            }
                        });
    }
}