package io.github.activehouse;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    EditText etUsername;
    EditText etPassword;
    CheckBox cbSave;
    Button btnLogin;


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

                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                //intent.putExtra("SomeStringData", "");

                startActivity(intent);
                finish();



            }
        });
    }

    public void loginButtonClick(View view) {


    }


}
