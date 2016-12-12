//Active Applications
//Active House Project

package io.github.activehouse;


import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RegisterActivity extends AppCompatActivity {

    EditText etUsername, etPassword, etFName, etLName, etEmail, etHouseID;
    String Username, Password, FName, LName, Email, HouseID;
    Button regButton;
    private String TAG = RegisterActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = (EditText) findViewById(R.id.input_username);
        etPassword = (EditText) findViewById(R.id.input_password);
        etFName = (EditText) findViewById(R.id.input_fname);
        etLName = (EditText) findViewById(R.id.input_lname);
        etEmail = (EditText) findViewById(R.id.input_email);
        etHouseID = (EditText) findViewById(R.id.input_houseid);
        regButton = (Button) findViewById(R.id.btn_register);

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (etPassword.getText().length() > 0 && etUsername.getText().length() > 0 && etFName.getText().length() > 0
                        && etLName.getText().length() > 0 && etEmail.getText().length() > 0 && etHouseID.getText().length() > 0) {

                    //attempt registration
                    Username = etUsername.getText().toString();
                    Password = md5(etPassword.getText().toString());
                    FName = etFName.getText().toString();
                    LName = etLName.getText().toString();
                    Email = etEmail.getText().toString();
                    HouseID = etHouseID.getText().toString();
                    new Registration().execute();

                }
                else {
                    Toast.makeText(RegisterActivity.this, R.string.errorRegistering,Toast.LENGTH_SHORT).show();

                }

            }
        });

    }


    private class Registration extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Toast.makeText(HomeActivity.this,"Json Data is downloading",Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String url = "http://munro.humber.ca/~n01046059/ActiveHouse/register.php?username=" + Username + "&password=" + Password
                    + "&fname=" + FName + "&lname=" + LName + "&email=" + Email + "&houseid=" + HouseID;
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    int success = jsonObj.getInt("success");
                    if (success == 1) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), R.string.regSuccess,Toast.LENGTH_LONG).show();
                                finish();
                            }
                        });


                    }
                    else if (success == 2) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), R.string.invalid_houseid,Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), R.string.errorRegistering,Toast.LENGTH_LONG).show();
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
