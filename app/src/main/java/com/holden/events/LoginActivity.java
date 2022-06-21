package com.holden.events;

import android.app.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.*;
import android.util.*;
import android.view.View;
import android.widget.*;

import com.auth0.android.Auth0;
import com.auth0.android.authentication.*;
import com.auth0.android.callback.Callback;
import com.auth0.android.result.Credentials;
import com.holden.events.databinding.ActivityLoginBinding;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    private AuthenticationAPIClient authClient;
    private ProgressBar loadingProgressBar;
    private EditText usernameEditText;
    private TextView usernameError;
    private EditText passwordEditText;
    private TextView passwordError;
    private Button loginButton;
    private Button signupButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.holden.events.databinding.ActivityLoginBinding binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        usernameEditText = binding.username;
        usernameEditText.addTextChangedListener(usernameTextWatcher);
        usernameError = binding.usernameError;

        passwordEditText = binding.password;
        passwordEditText.addTextChangedListener(passwordTextWatcher);
        passwordError = binding.passwordError;

        loginButton = binding.login;
        signupButton = binding.register;
        loadingProgressBar = binding.loading;

        encryptedSharedPreferences = new EncryptedSharedPreferencesUtilities(this);

        Auth0 auth0 = new Auth0(getString(R.string.com_auth0_client_id), getString(R.string.com_auth0_domain));
        authClient = new AuthenticationAPIClient(auth0);

        loginButton.setOnClickListener(v -> {
            loadingProgressBar.setVisibility(View.VISIBLE);
            Login();
        });

        signupButton.setOnClickListener(v -> {
            loadingProgressBar.setVisibility(View.VISIBLE);
            SignUp();
        });
    }

    private void SetUpEncryptedSharedPreferences() {
        try {
            encryptedSharedPreferences = EncryptedSharedPreferences.create(
                    "secret_shared_prefs",
                    MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
                    this,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        sharedPreferencesEditor = encryptedSharedPreferences.edit();
    }

    private void Login() {
        authClient.login(/*usernameEditText.getText().toString()*/"holdenmark33+eventsapp@gmail.com",
            /*passwordEditText.getText().toString()*/"eventsappA1!",
            "Username-Password-Authentication")
            .start(new Callback<Credentials, AuthenticationException>() {
                @Override
                public void onSuccess(Credentials credentials) {
                    SetCredentialsAndCloseActivity(credentials);
                }

                @Override
                public void onFailure(AuthenticationException e) {
                    passwordError.setText(R.string.login_failed);
                    passwordError.setVisibility(View.VISIBLE);
                    loadingProgressBar.setVisibility(View.GONE);
                }
            });
    }

    private void SignUp() {
        authClient.signUp(usernameEditText.getText().toString(),
                passwordEditText.getText().toString(),
                "Username-Password-Authentication")
            .start(new Callback<Credentials, AuthenticationException>() {
                @Override
                public void onSuccess(Credentials credentials) {
                    SetCredentialsAndCloseActivity(credentials);
                }

                @Override
                public void onFailure(AuthenticationException e) {
                    passwordError.setText(R.string.account_creation_failed);
                    passwordError.setVisibility(View.VISIBLE);
                    loadingProgressBar.setVisibility(View.GONE);
                }
            });
    }

    private void SetCredentialsAndCloseActivity(Credentials credentials) {
        //TODO: remove hack.
        encryptedSharedPreferences.setCredentials(credentials, role);
        loadingProgressBar.setVisibility(View.GONE);
        setResult(Activity.RESULT_OK);
        finish();
    }

    /**
     * TextWatcher for showing/hiding username error.
     */
    private final TextWatcher usernameTextWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!isUsernameValid(s.toString())) {
                usernameError.setText(R.string.invalid_username);
                usernameError.setVisibility(View.VISIBLE);
            } else {
                usernameError.setVisibility(View.GONE);
                if (isPasswordValid(passwordEditText.getText().toString())) {
                    loginButton.setEnabled(true);
                    signupButton.setEnabled(true);
                }
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        @Override
        public void afterTextChanged(Editable s) { }
    };

    /**
     * Username validation check.
     * @param username Username to validate.
     * @return Boolean of if the username is valid or not.
     */
    private boolean isUsernameValid(String username) {
        return username.contains("@")
                && Patterns.EMAIL_ADDRESS.matcher(username).matches();
    }

    /**
     * TextWatcher for showing/hiding password error.
     */
    private final TextWatcher passwordTextWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!isPasswordValid(s.toString())) {
                passwordError.setText(R.string.invalid_password);
                passwordError.setVisibility(View.VISIBLE);
            } else {
                passwordError.setVisibility(View.GONE);
                if (isUsernameValid(usernameEditText.getText().toString())) {
                    loginButton.setEnabled(true);
                    signupButton.setEnabled(true);
                }
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        @Override
        public void afterTextChanged(Editable s) { }
    };

    /**
     * Password validation check.
     * @param password Password to validate.
     * @return Boolean of if the password ot valid or not.
     */
    private boolean isPasswordValid(String password) {
        return password.length() >= 8
                && Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,}$", password);
    }
}