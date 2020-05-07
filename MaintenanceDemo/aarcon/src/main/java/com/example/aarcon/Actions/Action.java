package com.example.aarcon.Actions;

import com.example.aarcon.Conditions.Condition;
import com.example.aarcon.Control;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Action superclass with the main logic which should be inherited by all the Action subclasses.
 */
public abstract class Action extends Observable {

    Control control;
    private boolean active = false;
    private int priority = 2;
    private ArrayList<Condition> conditions = new ArrayList<>();

    public Action(){}

    public Action(Control control) {
        this.control = control;
        control.registerRule(this);
    }

    public Action(Control control, int priority) {
        this.control = control;
        this.priority = priority;
        control.registerRule(this);
    }

    /**
     * Evaluates all the conditions, returns false if they are not as required
     * @return
     */
    public final void evaluate(){
        for(int i = 0; i<conditions.size(); i++){
            if (conditions.get(i).evaluate()==false){
                if (active){
                    active = false;
                    setChanged();
                    notifyObservers();
                    unexecute();
                }
                return;
            }
        }
        if (!active){
            active = true;
            setChanged();
            notifyObservers();
            execute();
        }
    }

    /**
     * Needs to be implemented by user.
     */
    public abstract void execute();

    /**
     * Can be implemented by user. What has to be done to unexecute the changes?
     */
    public void unexecute(){};

    public void addCondition(Condition condition){
        conditions.add(condition);
        condition.addObserver(control);
    }

    public void deactivate(){
        control.deleteRule(this);
    }

    public void deleteConditions(){
        for (int i = 0; i<conditions.size();i++){
            conditions.get(i).delete();
        }
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Control getControl(){
        return control;
    }

    public void setControl(Control control){
        this.control = control;
        this.addObserver(control);
        control.registerRule(this);
    }

    public boolean getActive(){
        return active;
    }
}
