package com.example.tabatahiit.data;

import android.os.CountDownTimer;
import android.view.View;

/**
 * Created by fbm on 24/10/2017.
 */
public class Compteur extends UpdateSource {

    // CONSTANTE
    private long timerDuration;

    // DATA
    private long updatedTime = timerDuration;
    private CountDownTimer timer;   // https://developer.android.com/reference/android/os/CountDownTimer.html

    private String type;
     private boolean running;

    public Compteur(long time) {
        timerDuration = time;
        updatedTime = timerDuration;
    }

    // Lancer le compteur
    public void start() {
        running = true;
        if (timer == null) {

            // Créer le CountDownTimer
            timer = new CountDownTimer(updatedTime, 10) {

                // Callback fired on regular interval
                public void onTick(long millisUntilFinished) {
                    updatedTime = millisUntilFinished;

                    // Mise à jour
                    update();
                }

                // Callback fired when the time is up
                public void onFinish() {
                    updatedTime = 0;
                    finish();

                    // Mise à jour
                }

            }.start();   // Start the countdown
        }

    }

    // Mettre en pause le compteur
    public void pause() {

        if (timer != null) {

            // Arreter le timer
            stop();

            // Mise à jour
            update();
        }
    }


    // Remettre à le compteur à la valeur initiale
    public void reset() {

        if (timer != null) {

            // Arreter le timer
            stop();
        }

        // Réinitialiser
        updatedTime = timerDuration;

        // Mise à jour
        update();
    }

    // Arrete l'objet CountDownTimer et l'efface
    private void stop() {
        timer.cancel();
        timer = null;
    }

    public int getMinutes() {
        return getSecondes()/60;
    }

    public int getSecondes() {
        int secs = (int) (updatedTime / 1000);
        return secs % 60;
    }

    public int getMillisecondes() {
        return (int) (updatedTime % 1000);
    }

    public int getUpdateTime() {
        return (int) updatedTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
