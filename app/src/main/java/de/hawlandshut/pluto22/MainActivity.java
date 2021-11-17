package de.hawlandshut.pluto22;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import de.hawlandshut.pluto22.testdata.PostTestData;

public class MainActivity extends AppCompatActivity {

    static final String TAG = "xx MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG,"called onCreate");

        //TODO: just for testing. remove Later.
        Toast.makeText(getApplicationContext(),"Ich mag kein Toast. Danke!", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"called onStart");
        //TODO: NUR ZUM TESTEN VON SIGNING - MUSS DANACH ENTFERNT WERDEN
        //GEHE DIREKT ZUM START
        Intent intent = new Intent(getApplication(), PostActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_main_post1:
                Toast.makeText(getApplicationContext(),"Post 1 was pressed",Toast.LENGTH_LONG).show();
                return true;

            case R.id.menu_main_post2:
                Toast.makeText(getApplicationContext(),"Post 2 was pressed",Toast.LENGTH_LONG).show();
                return true;

            case R.id.menu_main_test:
                Toast.makeText(getApplicationContext(),"Test was pressed",Toast.LENGTH_LONG).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //TODO: remove, if not needed
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"called onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"called onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"called onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"called onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"called onDestroy");
    }
}