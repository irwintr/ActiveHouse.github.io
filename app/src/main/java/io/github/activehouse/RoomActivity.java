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
import android.widget.TableRow;
import android.widget.TextView;

public class RoomActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    int RoomID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //TextView housename = (TextView) findViewById(R.id.tvHouseName);
        //String hn = (HomeActivity.myhouse.getHouseName());
        //housename.setText(HomeActivity.myhouse.getHouseName());
        //TextView usern = (TextView) findViewById(R.id.textViewUser);
        //usern.setText("User: " + MainActivity.FirstName);


        for (int i = 0; i < HomeActivity.myhouse.getRooms().size(); i++) {
            String name = HomeActivity.myhouse.getRooms().get(i).getName();
            MenuItem item;
            if(i == 0) {
                item = navigationView.getMenu().findItem(R.id.nav_1);
                item.setTitle(name);
            }
            else {
                item = navigationView.getMenu().add(name);
            }
            Intent intent = new Intent(RoomActivity.this, RoomActivity.class);
            intent.putExtra("ROOMID", HomeActivity.myhouse.getRooms().get(i).getRoomID());
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


        Intent i = getIntent();
        RoomID = i.getIntExtra("ROOMID", 0);
        getSupportActionBar().setTitle(HomeActivity.myhouse.getRoom(RoomID).getName());


        TextView Temp = (TextView) findViewById(R.id.textViewTemp);
        TextView Humidity = (TextView) findViewById(R.id.textViewHumidity);
        TextView Brightness = (TextView) findViewById(R.id.textViewBrightness);
        TextView Gas = (TextView) findViewById(R.id.textViewGas);
        TextView LightStatus = (TextView) findViewById(R.id.textViewLightStatus);
        TextView LightSchedule = (TextView) findViewById(R.id.textViewLightSchedule);
        TextView On = (TextView) findViewById(R.id.textViewLights);
        TextView Off = (TextView) findViewById(R.id.textViewLights);

        Temp.setText(String.valueOf(HomeActivity.myhouse.getRoom(RoomID).getTemp()) + "°c");
        Humidity.setText(String.valueOf(HomeActivity.myhouse.getRoom(RoomID).getHumidity()) + "%");
        Brightness.setText(String.valueOf((int) HomeActivity.myhouse.getRoom(RoomID).getLuminosity()) + "lx");
        if (HomeActivity.myhouse.getRoom(RoomID).getGas()) {
            Gas.setText(R.string.positive);
        }
        else {
            Gas.setText(R.string.negative);
        }
        if (HomeActivity.myhouse.getRoom(RoomID).isLightStatus()) {
            LightStatus.setText(R.string.on);
        }
        else {
            LightStatus.setText(R.string.off);
        }
        if (HomeActivity.myhouse.getRoom(RoomID).isLightSchedule()) {
            LightSchedule.setText(R.string.on);
        }
        else {
            LightSchedule.setText(R.string.off);
            TableRow tbr = (TableRow) findViewById(R.id.tableRow4);
            tbr.setVisibility(View.GONE);
        }

        //WaterToday.setText(String.valueOf(HomeActivity.myhouse.getWaterCurrent()) + "L");
        //WaterAverage.setText(String.valueOf(HomeActivity.myhouse.getWaterAverage()) + "L");
        //Lights.setText(String.valueOf(HomeActivity.myhouse.getLightsOn()));



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
            super.finish();
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
            Intent intent = new Intent(RoomActivity.this, HomeActivity.class);
            //intent.putExtra("SomeStringData", "");

            startActivity(intent);
        } else if (id == R.id.nav_viewRooms) {
            Intent intent = new Intent(RoomActivity.this, RoomViewActivity.class);
            //intent.putExtra("SomeStringData", "");

            startActivity(intent);
       // } else if (id == R.id.nav_stats) {

        //} else if (id == R.id.nav_settings) {

        }  else {
            startActivity(item.getIntent());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
