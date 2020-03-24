package com.example.tabatahiit.ui.dashboard;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.tabatahiit.MainActivity;
import com.example.tabatahiit.R;
import com.example.tabatahiit.data.OnUpdateListener;
import com.example.tabatahiit.db.AppDatabase;
import com.example.tabatahiit.db.Cycle;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    private int set_time;
    private int rest_time;
    private int nb_serie;

    private AppDatabase db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ((MainActivity) getActivity()).listCycle();

        db = ((MainActivity) getActivity()).getDb();

        final TextView text_set = root.findViewById(R.id.set_time);
        set_time = Integer.parseInt(text_set.getText().toString());

        final TextView text_rest = root.findViewById(R.id.rest_time);
        rest_time = Integer.parseInt(text_rest.getText().toString());

        final TextView text_serie = root.findViewById(R.id.nb_serie);
        nb_serie = Integer.parseInt(text_serie.getText().toString());

        Button btnPlusSet = root.findViewById(R.id.plus_set_time);
        Button btnMinusSet = root.findViewById(R.id.minus_set_time);

        btnPlusSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_time++;
                text_set.setText(Integer.toString(set_time));
            }
        });

        btnMinusSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (set_time > 0) {
                    set_time--;
                    text_set.setText(Integer.toString(set_time));
                }
            }
        });

        Button btnPlusRest = root.findViewById(R.id.plus_rest_time);
        Button btnMinusRest = root.findViewById(R.id.minus_rest_time);

        btnPlusRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rest_time++;
                text_rest.setText(Integer.toString(rest_time));
            }
        });

        btnMinusRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rest_time > 0) {
                    rest_time--;
                    text_rest.setText(Integer.toString(rest_time));
                }
            }
        });

        Button btnPlusSerie = root.findViewById(R.id.plus_nb_serie);
        Button btnMinusSerie = root.findViewById(R.id.minus_nb_serie);

        btnPlusSerie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nb_serie++;
                text_serie.setText(Integer.toString(nb_serie));
            }
        });

        btnMinusSerie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nb_serie > 0) {
                    nb_serie--;
                    text_serie.setText(Integer.toString(nb_serie));
                }
            }
        });

        Button btnSave = root.findViewById(R.id.save_time);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                class SaveCycle extends AsyncTask<Void, Void, Void> {

                    @Override
                    protected Void doInBackground(Void... voids) {
                        ((MainActivity) getActivity()).listCycle();
                        // creating a cycle
                        Cycle cycle = new Cycle(set_time, rest_time, nb_serie);

                        // adding to database
                        db.cycleDao().InsertAll(cycle);
                        return null;
                    }
                }

                SaveCycle sc = new SaveCycle();
                sc.execute();
            }
        });

        Button btnDrop = root.findViewById(R.id.drop_table);

        btnDrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                class DropCycle extends AsyncTask<Void, Void, Void> {

                    @Override
                    protected Void doInBackground(Void... voids) {
                        // adding to database
                        db.cycleDao().nukeTable();
                        ((MainActivity) getActivity()).listCycle();
                        return null;
                    }
                }

                DropCycle dc = new DropCycle();
                dc.execute();
            }
        });

        return root;
    }

}