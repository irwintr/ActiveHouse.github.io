//Active Applications
//Active House Project

package io.github.activehouse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class RoomViewActivity extends AppCompatActivity {

    ArrayList<String> Rooms = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        for (int i = 0; i < HomeActivity.myhouse.getRooms().size(); i++) {
            Rooms.add(HomeActivity.myhouse.getRooms().get(i).getName());
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.list_item, Rooms);

        ListView listView = (ListView) findViewById(R.id.ListView);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


                // Starting new intent
                Intent in = new Intent(getApplicationContext(), RoomActivity.class);
                int rid = HomeActivity.myhouse.getRooms().get(position).getRoomID();
                // sending roomid to next activity
                in.putExtra("ROOMID", rid);

                // starting new activity and expecting some response back
                startActivityForResult(in, 100);
            }
        });

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            finish(); // close this activity
        }

        return true;
    }
}
