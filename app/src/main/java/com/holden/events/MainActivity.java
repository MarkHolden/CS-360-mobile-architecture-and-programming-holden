package com.holden.events;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.security.crypto.*;

import android.content.*;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainActivity extends AppCompatActivity {

    private EncryptedSharedPreferencesUtilities encryptedSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        encryptedSharedPreferences = new EncryptedSharedPreferencesUtilities(this);

        if (!encryptedSharedPreferences.isAuthenticated()) {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        }

        // TODO: fix the back button letting you bypass auth

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            EventListFragment fragment = new EventListFragment();
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();
        }
    }
}
