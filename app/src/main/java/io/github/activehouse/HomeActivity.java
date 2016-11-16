package io.github.activehouse;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String TAG = MainActivity.class.getSimpleName();
    NavigationView navigationView;
    private ListView lv;

    public House myhouse;

    //ArrayList<HashMap<String, String>> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, R.string.allLightsOff,  Toast.LENGTH_LONG).show();
            }
        });*/
        LinearLayout LightsOn = (LinearLayout) findViewById(R.id.linearLayoutOn);
        LightsOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, R.string.allLightsOn,  Toast.LENGTH_SHORT).show();
            }
        });

        LinearLayout LightsOff = (LinearLayout) findViewById(R.id.linearLayoutOff);
        LightsOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, R.string.allLightsOff,  Toast.LENGTH_SHORT).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //DB Code

        //int HouseID = 1;
        Intent intent = getIntent();
        int HouseID = Integer.parseInt(intent.getStringExtra("HouseID"));
        myhouse = new House();
        myhouse.setHouseID(HouseID);
        //lv = (ListView) findViewById(R.id.list);

        new GetHouse().execute();






    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

        } else if (id == R.id.nav_viewRooms) {
            Intent intent = new Intent(HomeActivity.this, RoomViewActivity.class);
            //intent.putExtra("SomeStringData", "");

            startActivity(intent);

        } else if (id == R.id.nav_stats) {

        } else if (id == R.id.nav_settings) {

        } else {
            startActivity(item.getIntent());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private class GetHouse extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Toast.makeText(HomeActivity.this,"Json Data is downloading",Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "http://munro.humber.ca/~n01046059/ActiveHouse/get_rooms.php?houseid=" + myhouse.getHouseID();
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray rooms = jsonObj.getJSONArray("rooms");

                    // looping through All Contacts
                    for (int i = 0; i < rooms.length(); i++) {
                        JSONObject c = rooms.getJSONObject(i);


                        String name = c.getString("ROOM_NAME");
                        String timeOn = c.getString("LIGHT_TIME_ON");
                        String timeOff = c.getString("LIGHT_TIME_OFF");
                        String timeUpdated = c.getString("UPDATE_TIME");
                        int roomID = c.getInt("ROOM_ID");
                        boolean gas, lightStatus, lightSchedule;
                        if (c.getInt("GAS") == 1) {
                            gas = true;
                        }
                        else {
                            gas = false;
                        }

                        if (c.getInt("LIGHT_STATUS") == 1) {
                            lightStatus = true;
                        }
                        else {
                            lightStatus = false;
                        }

                        if (c.getInt("LIGHT_SCHEDULE") == 1) {
                            lightSchedule = true;
                        }
                        else {
                            lightSchedule = false;
                        }
                        double temp = c.getDouble("TEMPERATURE");
                        double humidity = c.getDouble("HUMIDITY");
                        double luminosity = c.getDouble("LUMINOSITY");

                        Room newRoom = new Room(name, timeOn, timeOff, timeUpdated, roomID, gas, lightStatus, lightSchedule, temp, humidity, luminosity);
                        myhouse.addRoom(newRoom);


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

            TextView Temp = (TextView) findViewById(R.id.textViewTemp);
            TextView Humidity = (TextView) findViewById(R.id.textViewHumidity);
            TextView PowerToday = (TextView) findViewById(R.id.textViewPowerToday);
            TextView PowerAverage = (TextView) findViewById(R.id.textViewPowerAverage);
            TextView WaterToday = (TextView) findViewById(R.id.textViewWaterToday);
            TextView WaterAverage = (TextView) findViewById(R.id.textViewWaterAverage);
            TextView Lights = (TextView) findViewById(R.id.textViewLights);

            Temp.setText(String.valueOf(myhouse.getAverageTemp()) + "°c");
            Humidity.setText(String.valueOf(myhouse.getAverageHumidity()) + "%");
            PowerToday.setText(String.valueOf(myhouse.getPowerCurrent()) + "kWh");
            PowerAverage.setText(String.valueOf(myhouse.getPowerAverage()) + "kWh");
            WaterToday.setText(String.valueOf(myhouse.getWaterCurrent()) + "L");
            WaterAverage.setText(String.valueOf(myhouse.getWaterAverage()) + "L");
            Lights.setText(String.valueOf(myhouse.getLightsOn()));

            //navigationView.getMenu().addIntentOptions("Rooms");


            for (int i = 0; i < myhouse.getRooms().size(); i++) {
                String name = myhouse.getRooms().get(i).getName();
                MenuItem item;
                if(i == 0) {
                    item = navigationView.getMenu().findItem(R.id.nav_1);
                    item.setTitle(name);
                }
                else {
                    item = navigationView.getMenu().add(name);
                }
                Intent intent = new Intent(HomeActivity.this, RoomActivity.class);
                intent.putExtra("ROOMID", myhouse.getRooms().get(i).getRoomID());
                item.setIntent(intent);


                if(name.toLowerCase().contains("kitchen")) {
                    item.setIcon(R.drawable.ic_kitchen_black_48dp);
                }
                else if (name.toLowerCase().contains("bedroom")) {
                    item.setIcon(R.drawable.ic_hotel_black_48dp);
                }
                else if (name.toLowerCase().contains("living")) {
                    item.setIcon(R.drawable.ic_weekend_black_48dp);
                }
                //item.setVisible(true);
            }


        }
    }
}
