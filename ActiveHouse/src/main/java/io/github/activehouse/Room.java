//Active Applications
//Active House Project

package io.github.activehouse;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

public class Room {
    private String name, timeOn, timeOff, timeUpdated;
    private int roomID;
    private boolean lightStatus, lightSchedule, gas;
    private double temp, humidity, luminosity;


    public Room(String name, String timeOn, String timeOff, String timeUpdated, int roomID, boolean gas, boolean lightStatus, boolean lightSchedule, double temp, double humidity, double luminosity) {
        this.name = name;
        this.timeOn = timeOn;
        this.timeOff = timeOff;
        this.timeUpdated = timeUpdated;
        this.roomID = roomID;
        this.gas = gas;
        this.lightStatus = lightStatus;
        this.lightSchedule = lightSchedule;
        this.temp = temp;
        this.humidity = humidity;
        this.luminosity = luminosity;
    }

    public Room() {

    }

    public void updateRoom() {
        new UpdateRoom().execute();
    }

    public class UpdateRoom extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Toast.makeText(HomeActivity.this,"Json Data is downloading",Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            String schedule, status;
            if (lightSchedule) {
                schedule = "1";
            }
            else {
                schedule = "0";
            }
            if (lightStatus) {
                status = "1";
            }
            else {
                status = "0";
            }

            // Making a request to url and getting response
            String url = "http://munro.humber.ca/~n01046059/ActiveHouse/update_room.php?ROOM_ID=" + roomID + "&LIGHT_STATUS=" + status
                    + "&LIGHT_SCHEDULE=" + schedule + "&LIGHT_TIME_ON=" + timeOn + "&LIGHT_TIME_OFF=" + timeOff + "&ROOM_NAME=" + name;
            String jsonStr = sh.makeServiceCall(url);

            Log.e(Room.class.getSimpleName(), "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    int success = jsonObj.getInt("success");
                    if (success == 1) {
                        // Getting JSON Array node

                        Log.e(Room.class.getSimpleName(), "Room successfully updated");


                    }
                    else {
                        /*runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Error Logging in, check Username and Password",Toast.LENGTH_LONG).show();
                            }
                        });*/
                    }

                } catch (final JSONException e) {
                    Log.e(Room.class.getSimpleName(), "Json parsing error: " + e.getMessage());
                    /*runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });*/

                }

            } else {
                Log.e(Room.class.getSimpleName(), "Couldn't get json from server.");
                /*runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });*/
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);





        }
    }


    //Getters and Setters


    public boolean isLightStatus() {
        return lightStatus;
    }

    public void setLightStatus(boolean lightStatus) {
        this.lightStatus = lightStatus;
    }

    public boolean isLightSchedule() {
        return lightSchedule;
    }

    public void setLightSchedule(boolean lightSchedule) {
        this.lightSchedule = lightSchedule;
    }

    public String getTimeOn() {
        return timeOn;
    }

    public void setTimeOn(String timeOn) {
        this.timeOn = timeOn;
    }

    public String getTimeOff() {
        return timeOff;
    }

    public void setTimeOff(String timeOff) {
        this.timeOff = timeOff;
    }

    public String getTimeUpdated() {
        return timeUpdated;
    }

    public void setTimeUpdated(String timeUpdated) {
        this.timeUpdated = timeUpdated;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public boolean getGas() {
        return gas;
    }

    public void setGas(boolean gas) {
        this.gas = gas;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getLuminosity() {
        return luminosity;
    }

    public void setLuminosity(double luminosity) {
        this.luminosity = luminosity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
