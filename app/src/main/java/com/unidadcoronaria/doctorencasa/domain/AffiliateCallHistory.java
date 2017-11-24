package com.unidadcoronaria.doctorencasa.domain;

import java.io.Serializable;

/**
 * Created by agustin on 7/11/17.
 */

public class AffiliateCallHistory implements Serializable {

    private VideoCall lastVideocall;
    private int queries;
    private long waitTime;

    public VideoCall getLastVideocall() {
        return lastVideocall;
    }

    public void setLastVideocall(VideoCall lastVideocall) {
        this.lastVideocall = lastVideocall;
    }

    public int getQueries() {
        return queries;
    }

    public void setQueries(int queries) {
        this.queries = queries;
    }

    public long getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(long waitTime) {
        this.waitTime = waitTime;
    }
}
