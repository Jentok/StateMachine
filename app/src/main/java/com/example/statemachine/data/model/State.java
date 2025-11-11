package com.example.statemachine.data.model;

public class State {
    String name;
    String function;
    String nextState;
    String errorState;

    public String getName() {
        return name;
    }

    public String getFunction() {
        return function;
    }

    public String getNextState() {
        return nextState;
    }

    public String getErrorState() {
        return errorState;
    }
}
