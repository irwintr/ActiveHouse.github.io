package io.github.activehouse;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


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
        String savedPassword = sharedPref.getString("password", "");
        if (!savedUsername.equals("") && !savedPassword.equals("")) {
            etUsername.setText(savedUsername);
            etPassword.setText(savedPassword);
            cbSave.setChecked(true);
        }



        btnLogin = (Button)findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                //attempt login
                username = etUsername.getText().toString();
                password = etPassword.getText().toString();
                new GetLogin().execute();


                if (cbSave.isChecked()) {
                    SharedPreferences sharedPref = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("username", etUsername.getText().toString());
                    editor.putString("password", etPassword.getText().toString());
                    editor.apply();


                }
                else {
                    SharedPreferences sharedPref = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("username", "");
                    editor.putString("password", "");
                    editor.apply();
                }






            }
        });
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
            // Making a request to url and getting response
            String url = "http://munro.humber.ca/~n01046059/ActiveHouse/login.php?username=" + username + "&password=" + password;
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
                        finish();

                    }
                    else {
                        Toast.makeText(MainActivity.this,"Error Logging in, check Username and Password",Toast.LENGTH_SHORT).show();
                    }









                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
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
                                "Couldn't get json from server. Check LogCat for possible errors!",
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


}
