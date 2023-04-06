/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ugarden.engine;

public class Timer {
    private final double duration;
    private long startTime;
    private boolean running = false;
    private boolean requested = false;
    public double remaining;

    // Set a timer for a duration in seconds
    public Timer(double duration) {
        this.duration = duration;
        remaining = duration;
    }

    // Function to call as often as possible to update the timer (see update in GameEngine)
    public void update(long now) {
        // time is in ns
        if (running) {
            remaining = duration * 1000000000 - (now - startTime);
            if (remaining < 0) {
                running = false;
            }
        } else if (requested) {
            running = true;
            requested = false;
            startTime = now;
            remaining = duration;
        }
    }

    // Start the timer
    public void start() {
        if (!running)
            requested = true;
        else
            remaining = duration;
    }

    public void reset() {
        running = false;
        requested = true;
        start();
    }

    // Check if the timer is still running
    public boolean isRunning() {
        return running || requested;
    }

}
