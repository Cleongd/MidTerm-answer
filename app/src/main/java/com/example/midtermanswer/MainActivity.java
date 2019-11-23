package com.example.midtermanswer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private EditText editUserename, editPassword;
    private Button btnLogin;
    private OkHttpClient Client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FindViews();
        setListener();
    }

    private void FindViews(){
        editPassword = findViewById(R.id.ETpassword);
        editUserename = findViewById(R.id.ETusername);
        btnLogin = findViewById(R.id.BTNlogin);
    }

    private void setListener(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do something to check whether username and password is valid
                //1. Grab the data from application endpoint

                Request request = new Request.Builder()
                        .url("https://api.myjson.com/bins/arlt2")
                        .build();

                Client.newCall(request)
                        .enqueue(new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e) {

                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        //2. Check each user account and password are match
                                        String data = null;
                                        try {
                                            data = response.body().string();
                                            if(validateAccounts(data)){
                                                Toast.makeText(MainActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(MainActivity.this, "Account invalid!", Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                        });

            }
        });
    }

    private boolean validateAccounts(String data){
        try {
            JSONObject dataObject = new JSONObject(data);
            JSONArray accArray = dataObject.getJSONArray("accounts");

            String inputUsername = editUserename.getText().toString();
            String inputPassword = editPassword.getText().toString();

            for(int i = 0; i < accArray.length(); i++){
                JSONObject accObject = accArray.getJSONObject(i);

                String validationUsername = accObject.getString("username");
                String validationPassword = accObject.getString("password");

                inputPassword = SHA256(inputPassword);

                if(inputUsername.equals(validationUsername) && (inputPassword.equals(validationPassword))){
                    return true;
                }
            }
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public static String SHA256 (String text) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(text.getBytes(StandardCharsets.UTF_8));

        BigInteger number = new BigInteger( 1, hash);

        StringBuilder hexString = new StringBuilder(number.toString(16));
        while(hexString.length() < 32){
            hexString.insert(0,'0');
        }
        return hexString.toString();
    }

}

