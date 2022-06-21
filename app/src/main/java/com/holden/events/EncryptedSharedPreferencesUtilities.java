package com.holden.events;

import android.content.*;

import androidx.security.crypto.*;

import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationAPIClient;
import com.auth0.android.result.Credentials;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EncryptedSharedPreferencesUtilities {
    private AuthenticationAPIClient _authClient;
    private SharedPreferences _encryptedSharedPreferences;
    private SharedPreferences.Editor _sharedPreferencesEditor;
    private Context _context;

    public EncryptedSharedPreferencesUtilities(Context context) {
        _context = context;
        setUpEncryptedSharedPreferences();
        _sharedPreferencesEditor = _encryptedSharedPreferences.edit();

        Auth0 auth0 = new Auth0(_context.getString(R.string.com_auth0_client_id), _context.getString(R.string.com_auth0_domain));
        _authClient = new AuthenticationAPIClient(auth0);
    }

    public void setCredentials(Credentials credentials, int role) {
        _sharedPreferencesEditor.putString(_context.getString(R.string.access_token), credentials.getIdToken());
        _sharedPreferencesEditor.putString(_context.getString(R.string.expires_at), credentials.getExpiresAt().toString());
        _sharedPreferencesEditor.putString(_context.getString(R.string.role), String.valueOf(role));
        _sharedPreferencesEditor.apply();
    }

    public boolean isAuthenticated() {
        String expirationString = _encryptedSharedPreferences.getString(_context.getString(R.string.expires_at), null);
        String accessToken = _encryptedSharedPreferences.getString(_context.getString(R.string.access_token), null);

        return accessToken != null
                && expirationString != null
                && LocalDateTime.now().isBefore(LocalDateTime.parse(expirationString, DateTimeFormatter.ofPattern(_context.getString(R.string.date_format)))); // why do people even use Java...
    }

    public boolean canAddEvents() {
        // TODO: get rid of this insecure hack to make roles work
        String roleString = _encryptedSharedPreferences.getString(_context.getString(R.string.role), null);

        if (roleString != null) {
            int role = Integer.parseInt(roleString);
            return isAuthenticated() && role < 1;
        }

        return false;
    }

    public boolean canEditEvents() {
        // TODO: get rid of this insecure hack to make roles work
        String roleString = _encryptedSharedPreferences.getString(_context.getString(R.string.role), null);

        if (roleString != null) {
            int role = Integer.parseInt(roleString);
            return isAuthenticated() && role < 2;
        }

        return false;
    }

    private void setUpEncryptedSharedPreferences() {
        try {
            String keys = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            _encryptedSharedPreferences = EncryptedSharedPreferences.create(
                    _context.getString(R.string.secret_shared_prefs),
                    keys,
                    _context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
