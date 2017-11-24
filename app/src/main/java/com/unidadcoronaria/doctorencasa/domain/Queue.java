package com.unidadcoronaria.doctorencasa.domain;

/**
 * Created by agustin on 17/11/17.
 */

public class Queue {

    private int doctorsAttending;
    private int usersQueue;
    private long waitTime;

    public int getDoctorsAttending() {
        return doctorsAttending;
    }

    public void setDoctorsAttending(int doctorsAttending) {
        this.doctorsAttending = doctorsAttending;
    }

    public int getUsersQueue() {
        return usersQueue;
    }

    public void setUsersQueue(int usersQueue) {
        this.usersQueue = usersQueue;
    }

    public long getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(long waitTime) {
        this.waitTime = waitTime;
    }
}
