//Active Applications
//Active House Project

package io.github.activehouse;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


public class RoomActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    int RoomID;

    TextView On, Off;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        On = (TextView) findViewById(R.id.textViewLights);
        Off = (TextView) findViewById(R.id.textViewLightsOff);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerLayout = navigationView.getHeaderView(0); // 0-index header

        TextView housename = (TextView) headerLayout.findViewById(R.id.tvHouseName);
        housename.setText(getString(R.string.the) + MainActivity.LastName + getString(R.string.house));
        TextView usern = (TextView) headerLayout.findViewById(R.id.textViewUser);
        usern.setText(getString(R.string.user) + MainActivity.FirstName);

        navigationView.setNavigationItemSelectedListener(this);

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
            else if (name.toLowerCase().contains("garage")) {
                item.setIcon(R.drawable.ic_garage_black_48dp);
            }
            else if (name.toLowerCase().contains("dining")) {
                item.setIcon(R.drawable.ic_silverware_black_48dp);
            }
            else if (name.toLowerCase().contains("bath") || name.toLowerCase().contains("wash")) {
                item.setIcon(R.drawable.ic_human_male_female_black_48dp);
            }
            else {
                item.setIcon(R.drawable.ic_home_outline_black_48dp);

            }
        }





        Intent i = getIntent();
        RoomID = i.getIntExtra("ROOMID", 0);


        updateLayout();



        LinearLayout lightStatus = (LinearLayout) findViewById(R.id.lightStatusToggle);
        lightStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (HomeActivity.myhouse.getRoom(RoomID).isLightStatus()) {
                    HomeActivity.myhouse.getRoom(RoomID).setLightStatus(false);
                    Toast.makeText(RoomActivity.this, R.string.lightOff,  Toast.LENGTH_SHORT).show();
                }
                else {
                    HomeActivity.myhouse.getRoom(RoomID).setLightStatus(true);
                    Toast.makeText(RoomActivity.this, R.string.lightOn,  Toast.LENGTH_SHORT).show();
                }
                HomeActivity.myhouse.getRoom(RoomID).updateRoom();
                updateLayout();

            }
        });

        LinearLayout lightSchedule = (LinearLayout) findViewById(R.id.lightScheduleToggle);
        lightSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (HomeActivity.myhouse.getRoom(RoomID).isLightSchedule()) {
                    HomeActivity.myhouse.getRoom(RoomID).setLightSchedule(false);
                    Toast.makeText(RoomActivity.this, R.string.lightScheduleOff,  Toast.LENGTH_SHORT).show();
                }
                else {
                    HomeActivity.myhouse.getRoom(RoomID).setLightSchedule(true);
                    Toast.makeText(RoomActivity.this, R.string.lightScheduleOn,  Toast.LENGTH_SHORT).show();
                }
                HomeActivity.myhouse.getRoom(RoomID).updateRoom();
                updateLayout();


            }
        });

        LinearLayout lightScheduleOn = (LinearLayout) findViewById(R.id.linearLayout7);
        lightScheduleOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hour = Integer.parseInt(HomeActivity.myhouse.getRoom(RoomID).getTimeOn().split("\\:")[0]);
                int minute =Integer.parseInt(HomeActivity.myhouse.getRoom(RoomID).getTimeOn().split("\\:")[1]);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(RoomActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String min, timeString;
                        if (selectedMinute < 10) {
                            min = "0" + selectedMinute;
                        }
                        else {
                            min = String.valueOf(selectedMinute);
                        }
                        if (selectedHour == 0) {
                            timeString =  "12:" + min + "am";
                        } else if (selectedHour < 12) {
                            timeString = selectedHour + ":" + min + "am";
                        } else if (selectedHour == 12) {
                            timeString = "12:" + min + "pm";
                        } else {
                            timeString = (selectedHour-12) + ":" + min +"pm";
                        }
                        On.setText(timeString);
                        HomeActivity.myhouse.getRoom(RoomID).setTimeOn(selectedHour + ":" + selectedMinute);
                        HomeActivity.myhouse.getRoom(RoomID).updateRoom();
                    }
                }, hour, minute, false);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();


            }
        });


        LinearLayout lightScheduleOff = (LinearLayout) findViewById(R.id.linearLayout8);
        lightScheduleOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hour = Integer.parseInt(HomeActivity.myhouse.getRoom(RoomID).getTimeOff().split("\\:")[0]);
                int minute =Integer.parseInt(HomeActivity.myhouse.getRoom(RoomID).getTimeOff().split("\\:")[1]);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(RoomActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        HomeActivity.myhouse.getRoom(RoomID).setTimeOff(selectedHour + ":" + selectedMinute);
                        HomeActivity.myhouse.getRoom(RoomID).updateRoom();
                        updateLayout();
                    }
                }, hour, minute, false);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();


            }
        });



    }

    public void updateLayout() {

        getSupportActionBar().setTitle(HomeActivity.myhouse.getRoom(RoomID).getName());




        TextView Temp = (TextView) findViewById(R.id.textViewTemp);
        TextView Humidity = (TextView) findViewById(R.id.textViewHumidity);
        TextView Brightness = (TextView) findViewById(R.id.textViewBrightness);
        TextView Gas = (TextView) findViewById(R.id.textViewGas);
        TextView LightStatus = (TextView) findViewById(R.id.textViewLightStatus);
        TextView LightSchedule = (TextView) findViewById(R.id.textViewLightSchedule);


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

        TableRow tbr = (TableRow) findViewById(R.id.tableRow4);
        if (HomeActivity.myhouse.getRoom(RoomID).isLightSchedule()) {
            LightSchedule.setText(R.string.on);
            tbr.setVisibility(View.VISIBLE);
        }
        else {
            LightSchedule.setText(R.string.off);

            tbr.setVisibility(View.GONE);
        }

        String timeString, min;
        int selectedHour = Integer.parseInt(HomeActivity.myhouse.getRoom(RoomID).getTimeOn().split("\\:")[0]);
        int selectedMinute =Integer.parseInt(HomeActivity.myhouse.getRoom(RoomID).getTimeOn().split("\\:")[1]);
        if (selectedMinute < 10) {
            min = "0" + selectedMinute;
        }
        else {
            min = String.valueOf(selectedMinute);
        }
        if (selectedHour == 0) {
            timeString =  "12:" + min + "am";
        } else if (selectedHour < 12) {
            timeString = selectedHour + ":" + min + "am";
        } else if (selectedHour == 12) {
            timeString = "12:" + min + "pm";
        } else {
            timeString = (selectedHour-12) + ":" + min +"pm";
        }
        On.setText(timeString);

        selectedHour = Integer.parseInt(HomeActivity.myhouse.getRoom(RoomID).getTimeOff().split("\\:")[0]);
        selectedMinute =Integer.parseInt(HomeActivity.myhouse.getRoom(RoomID).getTimeOff().split("\\:")[1]);
        if (selectedMinute < 10) {
            min = "0" + selectedMinute;
        }
        else {
            min = String.valueOf(selectedMinute);
        }
        if (selectedHour == 0) {
            timeString =  "12:" + min + "am";
        } else if (selectedHour < 12) {
            timeString = selectedHour + ":" + min + "am";
        } else if (selectedHour == 12) {
            timeString = "12:" + min + "pm";
        } else {
            timeString = (selectedHour-12) + ":" + min +"pm";
        }
        Off.setText(timeString);

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
        getMenuInflater().inflate(R.menu.room, menu);
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
            Intent intent = new Intent(RoomActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            super.finish();
            return true;
        }
        else if (id == R.id.action_rename) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            alert.setTitle(R.string.urn);
            alert.setMessage(R.string.renameMessage);

            // Set an EditText view to get user input
            final EditText input = new EditText(this);
            alert.setView(input);

            alert.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    // Do something with value!
                    if (!input.getText().toString().isEmpty()) {
                        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

                        for (int i = 0; i < navigationView.getMenu().size(); i++) {
                            if (navigationView.getMenu().getItem(i).getTitle().toString() == HomeActivity.myhouse.getRoom(RoomID).getName()) {
                                navigationView.getMenu().getItem(i).setTitle(input.getText());

                                if(input.getText().toString().toLowerCase().contains("kitchen")) {
                                    navigationView.getMenu().getItem(i).setIcon(R.drawable.ic_kitchen_black_48dp);
                                }
                                else if (input.getText().toString().toLowerCase().contains("bedroom")) {
                                    navigationView.getMenu().getItem(i).setIcon(R.drawable.ic_hotel_black_48dp);
                                }
                                else if (input.getText().toString().toLowerCase().contains("living")) {
                                    navigationView.getMenu().getItem(i).setIcon(R.drawable.ic_weekend_black_48dp);
                                }
                                else if (input.getText().toString().toLowerCase().contains("garage")) {
                                    navigationView.getMenu().getItem(i).setIcon(R.drawable.ic_garage_black_48dp);
                                }
                                else if (input.getText().toString().toLowerCase().contains("dining")) {
                                    navigationView.getMenu().getItem(i).setIcon(R.drawable.ic_silverware_black_48dp);
                                }
                                else if (input.getText().toString().toLowerCase().contains("bath") || input.getText().toString().toLowerCase().contains("wash")) {
                                    navigationView.getMenu().getItem(i).setIcon(R.drawable.ic_human_male_female_black_48dp);
                                }
                                else {
                                    navigationView.getMenu().getItem(i).setIcon(R.drawable.ic_home_outline_black_48dp);

                                }

                            }
                        }
                        HomeActivity.myhouse.getRoom(RoomID).setName(input.getText().toString());
                        HomeActivity.myhouse.getRoom(RoomID).updateRoom();
                        updateLayout();


                        Toast.makeText(RoomActivity.this, R.string.room_name_updated,  Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(RoomActivity.this, R.string.renameError,  Toast.LENGTH_SHORT).show();
                    }
                }
            });

            alert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // Canceled.
                }
            });

            alert.show();
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
