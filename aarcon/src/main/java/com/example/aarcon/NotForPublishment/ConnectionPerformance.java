package com.example.aarcon.NotForPublishment;

import java.util.Observable;

public class ConnectionPerformance extends Observable {

    ConnectionPerformanceEnum state = null;

    public void setState(ConnectionPerformanceEnum state) {
        this.state = state;
        notifyObservers(state);
    }

    public ConnectionPerformanceEnum getState() {
        //get network info todo
        state = ConnectionPerformanceEnum.Mobile_2G;
        return state;
    }

}
