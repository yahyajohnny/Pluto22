package de.hawlandshut.pluto22;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;

public class PostActivity extends AppCompatActivity implements View.OnClickListener {

    static final String TAG = "xx PostActivity";

    // DEKLARATION UI-VARIABELN
    Button mButtonPost;
    EditText mEditTextTitle;
    EditText mEditTextText;

    /**
     *
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        Log.d(TAG, "called onCreate()");

        // INITIALISIERUNG UI-VARIABELN
        mButtonPost = findViewById(R.id.postButtonPost);
        mEditTextTitle = findViewById(R.id.postEditTextTitle);
        mEditTextText = findViewById(R.id.postEditTextText);

        // BUTTON LISTENER
        mButtonPost.setOnClickListener(this);
    }

    /**
     * BUTTON LISTENER
     * @param v
     */
    @Override
    public void onClick(View v) {

        int i = v.getId();
        switch (i) {
            case R.id.postButtonPost:
                doPost();
                return;

            default:
                return;
        }
    }

    /**
     * HIER HOLEN WIR UNS ALS ALLER ERSTES DIE RELEVANTEN INFOS,
     * DIE WIR BENÖTIGEN UM EINEN POSTS MIT ALL SEINEN DATEN ZU
     * GENERIEREN.
     *
     * DIESE GANZEN DATEN SPEICHERN WIR IN EINE NEUE HASHMAP
     *
     * SOBALD DIE HASHMAP VOLLSTÄNDIG BEFÜLLT IST, SCHICKEN WIR DIESE
     * HASHMAP AN UNSERE FIREBASE-DATENBANK UNTER DEN KNOTEN POSTS/
     *
     * NACH DIESER METHODE HABEN WIR EINEN NEUEN POST HINZUGEFÜGT - DAS HEISST
     * ABER NICHT DASS DER POST HIER ABER SCHON AUCH IN UNSERER APP ANGEZEIGT WIRD,
     * DAS PASSIERT ERST IN DER MAINACTIVITY
     *
     */
    private void doPost() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // TODO: Add checkings before posting
        Map<String, Object> postMap = new HashMap<>();
        postMap.put("uid", user.getUid());
        postMap.put("author", user.getEmail());
        postMap.put("title", mEditTextTitle.getText().toString());
        postMap.put("body", mEditTextText.getText().toString());
        postMap.put("timestamp", ServerValue.TIMESTAMP);

        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Posts/");
        db.push().setValue(postMap);

        finish();
    }

}