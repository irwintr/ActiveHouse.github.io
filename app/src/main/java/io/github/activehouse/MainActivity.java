package io.github.activehouse;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_main);

        SharedPreferences sharedPref = MainActivity.this.getPreferences(Context.MODE_PRIVATE);

        //String highScore = sharedPref.getString("username", 0);

    }

    public void loginButtonClick(View view) {
        SharedPreferences sharedPref = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("username", "Trent");
        editor.putString("password", "Password");
        editor.commit();
    }
}
