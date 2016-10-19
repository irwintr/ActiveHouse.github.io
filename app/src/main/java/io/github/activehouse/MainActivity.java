package io.github.activehouse;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_main);

        EditText etUsername = (EditText) findViewById(R.id.input_username);
        EditText etPassword = (EditText) findViewById(R.id.input_password);
        CheckBox cbSave = (CheckBox) findViewById(R.id.input_save);

        SharedPreferences sharedPref = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
        String savedUsername = sharedPref.getString("username", "");
        String savedPassword = sharedPref.getString("password", "");
        if (!savedUsername.equals("") && !savedPassword.equals("")) {
            etUsername.setText(savedUsername);
            etPassword.setText(savedPassword);
            cbSave.setChecked(true);
        }



    }

    public void loginButtonClick(View view) {
        SharedPreferences sharedPref = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("username", "Trent");
        editor.putString("password", "Password");
        editor.apply(); 
    }
}
