package com.example.tabatahiit.ui.notifications;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.tabatahiit.MainActivity;
import com.example.tabatahiit.R;
import com.example.tabatahiit.data.Compteur;
import com.example.tabatahiit.data.OnUpdateListener;
import com.example.tabatahiit.db.Cycle;

public class NotificationsFragment extends Fragment  implements OnUpdateListener {

    private NotificationsViewModel notificationsViewModel;

    private Compteur timer;
    private Cycle current_cycle;

    private ImageView btnStart;
    private ImageView btnPause;
    private ImageView btnReset;

    private TextView timerView;
    private TextView timer_state;
    private ProgressBar progressTimer;

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_chrono, container, false);

        timerView = root.findViewById(R.id.text_chrono);
        timer_state = root.findViewById(R.id.timer_state);
        progressTimer = root.findViewById(R.id.progress_timer);
        current_cycle = ((MainActivity)getActivity()).current_cycle;
        if ( current_cycle != null) {
            progressTimer.setMax(current_cycle.getTime_set()*1000);
            timer = new Compteur(current_cycle.getTime_set()*1000);
            current_cycle.setNb_serie(current_cycle.getNb_serie() - 1);
            timer.setType("SET");
            timer.addOnUpdateListener(this);
        }

        miseAJour();


        btnStart = root.findViewById(R.id.on_click_start);
        btnPause = root.findViewById(R.id.on_click_pause);
        btnReset = root.findViewById(R.id.on_click_reset);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timer != null) {
                    timer.start();
                }
                btnStart.setVisibility(View.GONE);
                btnPause.setVisibility(View.VISIBLE);
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timer != null) {
                    timer.pause();
                }
                btnPause.setVisibility(View.GONE);
                btnStart.setVisibility(View.VISIBLE);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timer != null) {
                    btnPause.setVisibility(View.GONE);
                    btnStart.setVisibility(View.VISIBLE);
                    timer.reset();
                }
            }
        });

        return root;
    }

    // Mise à jour graphique
    private void miseAJour() {
        if ( current_cycle != null) {
            // Affichage des informations du compteur
            timerView.setText("" + timer.getMinutes() + ":"
                    + String.format("%02d", timer.getSecondes()) + ":"
                    + String.format("%03d", timer.getMillisecondes()));
            timer_state.setText(timer.getType() + " !");
            progressTimer.setProgress(timer.getUpdateTime());
        }
    }

    private void majTimer(){
        if (current_cycle.getNb_serie() != 0) {
            if (timer.getType().equals("SET")) {
                progressTimer.setProgressDrawable(getResources().getDrawable(R.drawable.drawable_circle_rest));
                progressTimer.setBackground(getResources().getDrawable(R.drawable.drawable_circle_rest_background));
                timer = new Compteur(current_cycle.getTime_rest()*1000);
                progressTimer.setMax(current_cycle.getTime_rest()*1000);
                timer.setType("REST");
                timer.addOnUpdateListener(this);

                btnStart.setImageResource(R.drawable.icon_start_rest);
                btnPause.setImageResource(R.drawable.icon_stop_rest);
                btnReset.setImageResource(R.drawable.icon_reset_rest);

                timerView.setTextColor(getResources().getColor(R.color.colorPrimary));

                timer.start();
            } else if (timer.getType().equals("REST")) {
                progressTimer.setProgressDrawable(getResources().getDrawable(R.drawable.drawable_circle));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    progressTimer.setBackground(getResources().getDrawable(R.drawable.drawable_circle_dark));
                }
                timer = new Compteur(current_cycle.getTime_set()*1000);
                progressTimer.setMax(current_cycle.getTime_set()*1000);
                current_cycle.setNb_serie(current_cycle.getNb_serie() - 1);
                timer.setType("SET");
                timer.addOnUpdateListener(this);

                btnStart.setImageResource(R.drawable.icon_start);
                btnPause.setImageResource(R.drawable.icon_stop);
                btnReset.setImageResource(R.drawable.icon_reset);

                timerView.setTextColor(getResources().getColor(R.color.orange));

                timer.start();
            }
        }

    }

    /**
     * Méthode appelée à chaque update du compteur (l'activité est abonnée au compteur)
     *
     */
    @Override
    public void onUpdate() {
        miseAJour();
    }

    @Override
    public void onFinish(){
        majTimer();
        miseAJour();
    }
}
