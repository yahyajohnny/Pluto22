package de.hawlandshut.pluto22;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hawlandshut.pluto22.model.Post;
import de.hawlandshut.pluto22.testdata.PostTestData;

public class MainActivity extends AppCompatActivity {

    static final String TAG = "xx MainActivity";

    /* DEKLARATION UI-VARIABELN */
    ListView mListviewMessages;
    ArrayList<Post> mPostList = new ArrayList<Post>(); //DIESE LIST WIRD SPÄTER DIE POSTS ENTHALTEN, DIE ANGEZEIGT WERDEN SOLl.
    ArrayAdapter<Post> mAdapter;

    /* DEKLARATION DER SACHEN DIE WIR FÜR DEN LISTENER BENÖTIGEN */
    ChildEventListener mCEL;
    Query mQuery;
    boolean mListenerIsRunning = false;


    /**
     *
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG,"called onCreate");


        FirebaseDatabase.getInstance().setPersistenceEnabled(true); //--> ERWEITERTE OFFLINE FUNKTIONALITÄT


        // ADAPTER INITIAILISIEREN UND IMPLEMENTIEREN
        //DAFÜR DA UM DIE POSTS AUCH IN UNSERER ACTIVITY DARZUSTELLEN
        mAdapter = new ArrayAdapter<Post>(this, android.R.layout.simple_list_item_2, android.R.id.text1, mPostList) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView text1, text2;
                text1 = view.findViewById(android.R.id.text1);
                text2 = view.findViewById(android.R.id.text2);

                Post post =  getItem(position);

                text1.setText(post.title + " (" + post.author + ")");
                text1.setTextColor(Color.BLUE);
                text2.setText(post.body);

                Log.d(TAG,"Called with position. " +  position);

                return view;
            }
        };

        mListviewMessages = findViewById(R.id.mainListViewMessages);
        mListviewMessages.setAdapter(mAdapter);

        //CEL erzeugen...
        mCEL = getChildEventListener();

        //LISTENER AUF EINEM KNOTEN AKTIVIEREN
        mQuery = FirebaseDatabase.getInstance().getReference("Posts/").limitToLast(10);
    }

    /**
     * IN DER ONSTART() KONTROLLIEREN WIR ALS ALLER ERSTES
     * OB DER USER ANGEMELDET IST ODER NICHT
     *
     * IST KEIN USER ANGEMELDET MACHEN WIR MIT EINEM EXPLIZITEM INTENT
     * EINE WEITERLEITUNG ZUR SIGN IN ACTIVITY. DAVOR MACHEN WIR EINEN
     * RESET DER APP ( ERKLÄRUNG DAVON BEI DER METHODE RESETAPP() )
     *
     * IST DER USER ANGEMELDET, DANN SORGEN WIR DAFÜR, DASS DIE GANZEN
     * POSTS ANGEZEIGT WERDEN.
     */
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"called onStart");
        //TODO: NUR ZUM TESTEN VON SIGNING - MUSS DANACH ENTFERNT WERDEN
        //GEHE DIREKT ZUM START

        FirebaseUser user;
        user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null) {
            resetApp();
            Intent intent = new Intent(getApplication(),SignInActivity.class);
            startActivity(intent);
        } else {
            if(!mListenerIsRunning) {
                mPostList.clear();
                mQuery.addChildEventListener(mCEL);
                mListenerIsRunning = true;
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * IST KEIN USER ANGEMELDET WIRD DIESE METHODE AUFGERUFEN.
     * DAMIT ENTFERNEN WIR DEN CHILDEVENTLISTENER, DA DIESER
     * NUR DANN BENÖTIGT WIR WENN AUCH EIN USER ANGEMELDET IST
     * AUSSERDEM LEEREN WIR DIE POSTS-LISTE AUF UNSEREM GERÄT.
     *
     */
    private void resetApp() {
       if (mListenerIsRunning) {
           mQuery.removeEventListener(mCEL);
           mListenerIsRunning = false;
       }
       mPostList.clear();
       mAdapter.notifyDataSetChanged();
    }

    /**
     * MIT DER METHODE SORGEN WIR DAFÜR, DASS DAS MENÜ OBEN
     * RECHTS IN DER MAIN ACTIVITY ANGEZEIGT WERDEN KANN.
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return true;
    }

    /**
     *
     * MIT DER METHODE WEISEN WIR DEN EINZELNEN PUNKTEN IM MENÜ OBEN
     * RECHTS DIE FUNKTIONLITÄTEN ZU.
     *
     * ES WIRD ALSO DEFINIERT Z.B WAS SOLL PASSIEREN WENN ICH AUF
     * "MANAGE ACCOUNT" KLICKE.
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menu_main_manage_account:
                intent = new Intent(getApplication(),ManageAccountActivity.class); //--> expliziter Intent
                startActivity(intent);
                return true;

            case R.id.menu_main_go_to_post:
                intent = new Intent(getApplication(),PostActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * ÜBER DEN CHILDEVENTLISTENER BEKOMMT UNSERE PLUTO APP DIE INFORMATION
     * OB SICH IN DER DATENBANK BEI DEN POSTS IRGENDWAS GEÄNDERT HAT.
     *
     * JE NACHDEM WELCHES EVENT EINTRIFFT, MÜSSEN/SOLLTEN WIR IN DER APP DARAUF REAGIEREN
     *
     * WENN ZB IN DER DATENBANK EIN NEUER POST HINZUKOMMT, DANN BEKOMMT UNSERE APP DIE INFORMATION
     * DASS IN DER DATENBANK EIN NEUER POST HINZUGEKOMMEN IST. UNSERE AUFGABE IST ES DANN
     * EINE LOGIK ZU PROGRAMMIEREN UM DIESEN NEUEN POST BEI UNS IN DER APP DARZUSTELLEN
     *
     * DA ES BEI DER GENUTZTEN DATENBANK UM EINE REALTIME DATABASE HANDELT, BEKOMMEN WIR
     * TEILWEISE INNERHALB EIN PAAR MILLISEKUNDEN, NACHDEM SICH IRGENDWAS IN DER DATENBANK
     * ÄNDERT, DIE INFORMATION ÜBER DEN CHILDEVENTLISTENER, UND KÖNNEN DANN DARAUF REAGIEREN
     *
     * IN DER PLUTO APP HABEN WIR NUR DIE ONCHILDADDED UND ON CHILDREMOVED AUSPROGRAMMIERT
     *
     * HÄTTEN WIR ZB IN DER APP AUCH DIE MÖGLICHKEIT EINZELNE POSTS IN DER PLUTO APP ZU BEARBEITEN,
     * DANN MÜSSTEN WIR NATÜRLICH AUCH DIE ONCHILDREMOVED() FUNKTIONALITÄT AUSPROGRAMMIEREN.
     *
     * @return
     */
    private ChildEventListener getChildEventListener() {
        return new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.d(TAG,"CEL: onChildAdded, " +
                        "Key=" + snapshot.getKey() +
                        " Title=" + snapshot.child("title").getValue());

                Post p = Post.fromSnapShot(snapshot);
                mPostList.add(p);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //Log.d(TAG,"CEL: onChildChanged, Key=" + snapshot.getKey());
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Log.d(TAG,"CEL: onChildRemoved, Key=" + snapshot.getKey());

                String key = snapshot.getKey();
                for(int i = 0; i < mPostList.size(); i++) {
                    if(key.equals( mPostList.get(i).firebaseKey )) {
                        mPostList.remove(i);
                        break;
                    }
                }
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.d(TAG,"CEL: onChildMoved, Key=" + snapshot.getKey());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG,"CEL: onCancelled");
                mListenerIsRunning = false;
            }
        };
    }
}