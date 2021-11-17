package de.hawlandshut.pluto22;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class PostActivity extends AppCompatActivity {

    static final String TAG = "xx Post";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        Log.d(TAG,"called onCreate()");
    }
}