package com.example.statemachine.data.model;

import java.util.List;

public class Command {
    String commandName;
    String initialState;

    List<State> states;

    public String getCommandName() {
        return commandName;
    }

    public String getInitialState() {
        return initialState;
    }

    public State findState(String name) {
        for (State s : states) {
            if (s.name.equals(name)) return s;
        }
        return null;
    }
}
