package com.example.aarcon;

import com.example.aarcon.Actions.Action;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

public class Control implements Observer {
    private float updateFrequency = 1f;
    private ArrayList<Action> actions = new ArrayList<>();

    public Control(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                update(null,null);
            }
        },2*1000,(int)(updateFrequency*1000));
    }

    public Control(float updateFrequencySecs){
        updateFrequency = updateFrequencySecs;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                update(null,null);
            }
        },2*1000,(int)(updateFrequencySecs*1000));
    }

    @Override
    public void update(Observable observable, Object arg) {
        for(int prio = 3; prio>0; prio--) {
            for (int i = 0; i < actions.size(); i++) {
                if(actions.get(i).getPriority() == prio) {
                    actions.get(i).evaluate();
                }
            }
        }
        /*System.out.println("---Update");
        for (int i = 0;i<actions.size();i++){
            System.out.println(actions.get(i).getClass().toString());
        }
        System.out.println("---Update end");*/
    }


    public void registerAction(Action action){
        actions.add(action);
    }

    public void deleteAction(Action action){
        if(actions.contains(action)) {
            actions.remove(action);
        }
    }
    public ArrayList<Action> getActions(){
        return actions;
    }

    public float getUpdateFrequency() {
        return updateFrequency;
    }

    public void setUpdateFrequency(float updateFrequency) {
        this.updateFrequency = updateFrequency;
    }
}