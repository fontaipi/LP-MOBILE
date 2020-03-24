package com.example.tabatahiit;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tabatahiit.db.AppDatabase;
import com.example.tabatahiit.db.Cycle;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    AppDatabase db;
    public Cycle current_cycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_chrono)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database-tabata").build();
    }

    public void listCycle () {

        class ListCycle extends AsyncTask<Void, Void, List<Cycle>> {

            @Override
            protected List<Cycle> doInBackground(Void... voids) {
                List<Cycle> listCylcle = db.cycleDao().getAll();
                return listCylcle;
            }

            @Override
            protected void onPostExecute(List<Cycle> listCycle) {
               super.onPostExecute(listCycle);
                LinearLayout layout = findViewById(R.id.layout_db);
                layout.removeAllViews();
                for (final Cycle cycle : listCycle) {
                   Button btnCycle = new Button(getApplicationContext());
                   btnCycle.setText("Set time : " + Integer.toString(cycle.getTime_set()) +"\nRest time :"+ Integer.toString(cycle.getTime_rest()) + "\nNb set :" +Integer.toString(cycle.getNb_serie()));
                   layout.addView(btnCycle);
                   btnCycle.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_SHORT).show();
                           current_cycle = cycle;
                       }
                   });
               }
            }
        }

        ListCycle lc = new ListCycle();
        lc.execute();


    }

    public AppDatabase getDb () {
        return db;
    }

}
