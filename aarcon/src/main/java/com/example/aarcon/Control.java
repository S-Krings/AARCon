package com.example.aarcon;

import com.example.aarcon.Conditions.Condition;
import com.example.aarcon.Rules.Rule;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

public class Control implements Observer {
    private float updateFrequency = 1f;
    private ArrayList<Rule> rules = new ArrayList<>();

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
            for (int i = 0; i < rules.size(); i++) {
                if(rules.get(i).getPriority() == prio) {
                    rules.get(i).evaluate();
                }
            }
        }
        /*System.out.println("---Update");
        for (int i = 0;i<rules.size();i++){
            System.out.println(rules.get(i).getClass().toString());
        }
        System.out.println("---Update end");*/
    }


    public void registerRule(Rule rule){
        rules.add(rule);
    }

    public void deleteRule(Rule rule){
        if(rules.contains(rule)) {
            rules.remove(rule);
        }
    }
    public ArrayList<Rule> getRules(){
        return rules;
    }

    public float getUpdateFrequency() {
        return updateFrequency;
    }

    public void setUpdateFrequency(float updateFrequency) {
        this.updateFrequency = updateFrequency;
    }
}