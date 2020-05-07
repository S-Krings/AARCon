package com.example.aarcon.Conditions;

import com.example.aarcon.Control;

import java.util.Observable;
import java.util.Observer;

/**
 * Condition superclass with the main logic which should be inherited by all the Condition subclasses.
 */
public abstract class Condition extends Observable {

    private boolean state = false;

    /**
     * Default constructor, have to add an observer by hand
     */
    public Condition(){}

    /**
     * Takes Observer and adds it automatically
     * @param control Observer to be added
     */
    public Condition(Control control) {
        addObserver(control);
    }


    /**
     * Method to be called by Action: checks if condition is fulfilled and notifies the observer
     * @return whether the condition is fulfilled
     */
    public final boolean evaluate() {
        boolean b = check();
        //TODO System.out.println("############# Evaluate Action "+this.getClass()+", state is: "+b);//TODO
        if (b!=state){
            state = b;
            setChanged();
            notifyObservers();
        }
        return b;
    }

    /**
     * Overide with own check
     * @return whether the condition is fulfilled
     */
    public abstract boolean check();

    public void delete(){
        deleteObservers();
    }

    public void setControl(Control control){
        this.addObserver(control);
    }

    public boolean getState() {
        return state;
    }
}