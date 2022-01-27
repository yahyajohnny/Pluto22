package de.hawlandshut.pluto22;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    static final String TAG = "xx SignInActivity";

    // DEKLARATION DER UI-VARIABELN
    EditText mEditTextEmail;
    EditText mEditTextPassword;
    Button mButtonSignIn;
    Button mButtonResetPassword;
    Button mButtonCreateAccount;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Log.d(TAG,"called onCreate");

        // INITIALISIERUNG DER UI-VARIABELN
        mEditTextEmail = (EditText) findViewById(R.id.signInEmail);
        mEditTextPassword = (EditText) findViewById(R.id.signInPassword);
        mButtonSignIn = (Button) findViewById(R.id.signInButtonSignIn);
        mButtonResetPassword = (Button) findViewById(R.id.signInButtonResetPassword);
        mButtonCreateAccount = (Button) findViewById(R.id.signInButtonCreateAccount);


        // IMPLEMENTIERUNG DER BUTTON-LISTENER
        mButtonSignIn.setOnClickListener(this);
        mButtonResetPassword.setOnClickListener(this);
        mButtonCreateAccount.setOnClickListener(this);

        //TODO: ONLY FOR TESTING - REMOVE LATER
        mEditTextEmail.setText("hi@yahyapervaiz.com");
        mEditTextPassword.setText("123456");
    }

    /**
     *  SOLLTE MAN AUF DER SIGN IN ACTIVITY LANDEN OBWOHL MAN ANGEMELDET IST,
     *  DANN WIRD DIE SIGN IN ACTIVITY GESCHLOSSEN
     */
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            finish(); //IN DIESEM FALL KOMMEN WIR VON CREATE ACCOUNT
        }
    }

    /*
    *
     * ON CLICK LISTENER FÜR DIE BUTTONS
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        int i = v.getId();

        switch (i) {
            case R.id.signInButtonSignIn:
                doSignIn();
                return;

            case R.id.signInButtonResetPassword:
                doResetPassword();
                return;

            case R.id.signInButtonCreateAccount:
                doGoToCreateAccount();
                return;

            default:
                return;
        }
    }

    /**
     * IN DIESER METHODE HOLEN WIR UNS DIE EINGABEDATEN AUS DEN TEXTFELDERN
     * UND WENN DIE EINGABEDATEN RICHTIG SIND, UND ES EINEN USER IN DER FIREBASE
     * DATENBANK GIBT, DANN WIRD DIESER USER ANGEMELDET UND DIE ACTIVITY GESCHLOSSEN.
     * SOLLTEN WIR VON FIREBASE EINEN FEHLER ERHALTEN WIRD EINE FEHLERMEDLUNG
     * IN FORM EINES TOASTS AUSGEGEBEN
     *
     */
    public void doSignIn() {
        String email = mEditTextEmail.getText().toString(); //--> AUS DER EINGABE DES USERS
        String password = mEditTextPassword.getText().toString(); //--> AUS DER EINGABE DES USERS

        FirebaseUser user;
        user = FirebaseAuth.getInstance().getCurrentUser(); //--> AKTUELLER USER

        if(user != null) {
            /* USER IST BEREITS ANGEMELDET */
            Toast.makeText(getApplicationContext(),"Please sign out first!",Toast.LENGTH_SHORT).show();
            return;
        }


        /* AUFRUF DER FIREBASE METHODE ZUM ANMELDEN EINES USERS */
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(
                        this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful()) {
                                    /* ERFOLGSFALL */
                                    Toast.makeText(getApplicationContext(),"You are signed in!",Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    /* FEHLERFALL */
                                    Toast.makeText(getApplicationContext(),"Sign in failed!",Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "SING IN ERROR: " + task.getException());
                                }
                            }
                        });

    }

    /**
     * DIESE METHODE IST DAFÜR DA, DAMIT DER USER SEIN PASSWORD ZURÜCK-
     * SETZEN KANN. DAFÜR WIRD AUCH EINE FIREBASE METHODE AUFGERUFEN. DADURCH
     * SCHICKT DAS FIREBASE SYSTEM EINE EMAIL MIT EINEM LINK ZUM PASSWORT
     * ZURÜCKSETZEN AN DER USER. DIESER KANN DANN DIREKT IM BROWSER SEIN PASSWORT
     * ZURÜCKSETZEN UND SICH MIT SEINEM NEUEN PASSWORT BEI UNS IN DER APP ANMELDEN.
     */
    public void doResetPassword() {
        String email = mEditTextEmail.getText().toString(); //--> EINGABE DES USERS

        /* AUFRUF DER FIREBASE METHODE ZUM ZURÜCKSETZEN DES PASSWORTES FÜR EINEN USER */
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(
                        this,
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    /* ERFOLGSFALL */
                                    Toast.makeText(getApplicationContext(),"PWD-Reset Mail sent!",Toast.LENGTH_SHORT).show();
                                } else {
                                    /* FEHLERFALL */
                                    Toast.makeText(getApplicationContext(),"Sending PW-Reset Mail FAILED!",Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "SENDING PW-Reset MAIL ERROR: " + task.getException());
                                }
                            }
                        });

    }

    /**
     * GEHT ZUR ACTIVITY CREATE ACCOUNT
     */
    public void doGoToCreateAccount() {
        Intent intent = new Intent(getApplication(),CreateAccountActivity.class);
        startActivity(intent);
    }
}










