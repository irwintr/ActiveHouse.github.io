//Active Applications
//Active House Project

package io.github.activehouse;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Process;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MainActivity extends AppCompatActivity {

    EditText etUsername;
    EditText etPassword;
    CheckBox cbSave;
    Button btnLogin;
    String username, password;

    private String TAG = MainActivity.class.getSimpleName();

    public static String FirstName, LastName, Username, Email;
    public static int HouseID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        etUsername = (EditText) findViewById(R.id.input_username);
        etPassword = (EditText) findViewById(R.id.input_password);
        cbSave = (CheckBox) findViewById(R.id.input_save);

        SharedPreferences sharedPref = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
        String savedUsername = sharedPref.getString("username", "");
        if (!savedUsername.equals("")) {
            etUsername.setText(savedUsername);
            cbSave.setChecked(true);
        }



        btnLogin = (Button)findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (etPassword.getText().length() > 0 && etUsername.getText().length() > 0) {

                    //attempt login
                    username = etUsername.getText().toString();
                    password = etPassword.getText().toString();
                    new GetLogin().execute();


                    if (cbSave.isChecked()) {
                        SharedPreferences sharedPref = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("username", etUsername.getText().toString());
                        editor.apply();


                    }
                    else {
                        SharedPreferences sharedPref = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("username", "");
                        editor.apply();
                    }

                }
                else {
                    Toast.makeText(MainActivity.this, R.string.ERRORLOGGINGIN,Toast.LENGTH_SHORT).show();

                }

            }
        });

        TextView tvRegister = (TextView) findViewById(R.id.textViewRegister);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onBackPressed() {

            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(R.string.closing_activity)
                    .setMessage(R.string.close_body)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MainActivity.this.finishAffinity();

                        }

                    })
                    .setNegativeButton(R.string.no, null)
                    .show();

    }

    private class GetLogin extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Toast.makeText(HomeActivity.this,"Json Data is downloading",Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            String hashedPW = md5(password);
            // Making a request to url and getting response
            String url = "http://munro.humber.ca/~n01046059/ActiveHouse/login.php?username=" + username + "&password=" + hashedPW;
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    int success = jsonObj.getInt("success");
                    if (success == 1) {
                        // Getting JSON Array node
                        JSONArray user = jsonObj.getJSONArray("user");


                        JSONObject u = user.getJSONObject(0);
                        HouseID = u.getInt("HOUSE_ID");
                        Username = u.getString("USERNAME");
                        FirstName = u.getString("FIRST_NAME");
                        LastName = u.getString("LAST_NAME");
                        Email = u.getString("EMAIL");

                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);


                    }
                    else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), R.string.errorLoggingIn,Toast.LENGTH_LONG).show();
                            }
                        });
                    }









                } catch (final JSONException e) {
                    Log.e(TAG, R.string.jsonError + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    R.string.jsonError + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                R.string.jsonError3,
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);






        }
    }


    public static String md5(String s)
    {
        MessageDigest digest;
        try
        {
            digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes(Charset.forName("US-ASCII")),0,s.length());
            byte[] magnitude = digest.digest();
            BigInteger bi = new BigInteger(1, magnitude);
            String hash = String.format("%0" + (magnitude.length << 1) + "x", bi);
            return hash;
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return "";
    }


}
