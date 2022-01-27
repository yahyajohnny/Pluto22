package de.hawlandshut.pluto22;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class ManageAccountActivity extends AppCompatActivity implements View.OnClickListener {

    static final String TAG = "xx ManageAccount";

    // DEKLARATION DER UI VARIABELN
    TextView mTextViewEmail;
    TextView mTextViewAccountVerified;
    TextView mTextViewId;
    Button mButtonSignOut;
    Button mButtonSendActivationMail;
    Button mButtonDeleteAccount;
    EditText mEditTextPassword;


    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_account);
        Log.d(TAG,"called onCreate");

        // INITIALISIERUNG DER UI-VARIABELN
        mTextViewEmail = findViewById(R.id.manageAccountEmail);
        mTextViewAccountVerified = findViewById(R.id.manageAccountTextViewAccountVerified);
        mTextViewId = findViewById(R.id.manageAccountTextViewId);
        mButtonSignOut = findViewById(R.id.manageAccountButtonSignOut);
        mButtonSendActivationMail = findViewById(R.id.manageAccountButtonSendActivationMail);
        mButtonDeleteAccount = findViewById(R.id.manageAccountButtonDeleteAccount);
        mEditTextPassword = findViewById(R.id.manageAccountEditTextPassword);

        // BUTTON LISTENERS
        mButtonSignOut.setOnClickListener(this);
        mButtonSendActivationMail.setOnClickListener(this);
        mButtonDeleteAccount.setOnClickListener(this);

        //TODO: PRESET FOR TESTING. REMOVE LATER
        mEditTextPassword.setText("123456");
    }

    /**
     * WENN USER ANGEMELDET IST, WERDEN IN DER ACTIVITY OBEN IN DEN TEXT-VIEWS
     * WICHTIGE INFORMATIONEN WIE EMAIL, ID UND VERFIKATIONS-STATUS DES USERS
     * ANGEZEIGT
     */
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            mTextViewEmail.setText("E-Mail: " + user.getEmail());
            mTextViewAccountVerified.setText(user.isEmailVerified() ? "Konto ist verifiziert" : "Konto ist nicht verifiziert");
            mTextViewId.setText("ID: " + user.getUid());
        }
    }

    /**
     * ONCLICK LISTENER FÜR BUTTONS
     *
     * @param v
     */
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

    /**
     * DIESE METHODE MACHT ES DEM USER MÖGLICH SEINEN ACCOUNT ZU LÖSCHEN
     */
    public void doDeleteAccount() {
        FirebaseUser user;
        user = FirebaseAuth.getInstance().getCurrentUser();

        if(user == null) {
            /* KEIN USER ANGEMELDET */
            Toast.makeText(getApplicationContext(),"Deletion not possible (No user signed in)!",Toast.LENGTH_SHORT).show();
        } else {

            /* REAUTHENTICATE */
            String email = user.getEmail();
            String password = mEditTextPassword.getText().toString();

            AuthCredential credential = EmailAuthProvider.getCredential(email,password);
            user.reauthenticate(credential)
                    .addOnCompleteListener(
                            this,
                            new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        //ERFOLGSFALL
                                        Toast.makeText(getApplicationContext(),"Reauth is fine!",Toast.LENGTH_SHORT).show();
                                        finalDeletion();
                                    } else {
                                        //FEHLERFALL
                                        Toast.makeText(getApplicationContext(),"Reauth FAILED!",Toast.LENGTH_SHORT).show();
                                        Log.d(TAG, "REAUTH ERROR: " + task.getException());
                                    }
                                }
                            });
        }
    }

    /**
     * IN DIESER METHODE LÖSCHEN WIR DEN ACCOUNT DANN ENDGÜLTIG
     */
    private void finalDeletion() {
        FirebaseUser user;
        user = FirebaseAuth.getInstance().getCurrentUser();

        if(user == null) {
            //KEIN USER ANGEMELDET
            Log.e(TAG, "Serious error: Null user in final deletion");
            return;
        } else {
            user.delete()
                    .addOnCompleteListener(
                            this,
                            new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        //ERFOLGSFALL
                                        Toast.makeText(getApplicationContext(),"Account deleted!",Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        //FEHLERFALL
                                        Toast.makeText(getApplicationContext(),"Deletion FAILED!",Toast.LENGTH_SHORT).show();
                                        Log.d(TAG, "DELETE ACCOUNT ERROR: " + task.getException());
                                    }
                                }
                            });
        }
    }

    /**
     * IN DIESER METHODE WIRD DIE FUNKTIONALITÄT PROGRAMMIERT MIT DER DER USER
     * SEINE EMAIL ADRESSE VERIFIZIEREN KANN. WENN KEIN USER ANGEMELDET IST, BEKOMMT
     * MAN EINE FEHLERMELDUNG.
     *
     * WENN EIN USER ANGEMELDET IST, DANN WIRD EINE EMAIL MIT EINEM VERFICATION
     * LINK VON FIREBASE AN DIE EMAIL ADRESSE DES USERS GESCHICKT. SOBALD DIESER
     * DANN IN DER MAIL AUF DEN LINK KLICKT BEKOMMT DER USER SOZUSAGEN DEN STATUS "VERIFIZIERT"
     *
     *
     */
    public void doSendActivationMail() {
        FirebaseUser user;
        user = FirebaseAuth.getInstance().getCurrentUser();

        if(user == null) {
            //KEIN USER ANGEMELDET
            Toast.makeText(getApplicationContext(),"Sending not possible (No user signed in)!",Toast.LENGTH_SHORT).show();
        } else {

            user.sendEmailVerification()
                    .addOnCompleteListener(
                            this,
                            new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        //ERFOLGSFALL
                                        Toast.makeText(getApplicationContext(),"Verification Mail sent!",Toast.LENGTH_SHORT).show();
                                    } else {
                                        //FEHLERFALL
                                        Toast.makeText(getApplicationContext(),"Sending Verfication Mail FAILED!",Toast.LENGTH_SHORT).show();
                                        Log.d(TAG, "SENDING VER. MAIL ERROR: " + task.getException());
                                    }
                                }
                            });
        }
    }

    /**
     * DIESE METHODE IST DAFÜR DA DAMIT SICH DER USER ABMELDEN KANN. SOBALD SICH DER USER ABGEMELDET HAT
     * SOLLTE ER WIEDER AUF DIE MAINACTIVITY LANDEN, VON DA AUS WIRD ER (WEIL KEIN USER ANGEMELDET IST) DANN
     * AN DIE SIGN-IN ACTIVITY WEITER GELEITET
     */
    public void doSignOut() {
        FirebaseUser user;
        user = FirebaseAuth.getInstance().getCurrentUser();


        if(user == null) {
            //KEIN USER ANGEMELDET
            Toast.makeText(getApplicationContext(),"Senseless, because no user is logged in!",Toast.LENGTH_SHORT).show();
        } else {
            FirebaseAuth.getInstance().signOut();

            Toast.makeText(getApplicationContext(),user.getEmail() + " was signed out",Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}