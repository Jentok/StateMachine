package com.example.statemachine.data.model;

import java.util.List;

public class Config {
    String version;
    List<Command> commands;

    public String getVersion() {
        return version;
    }

    public List<Command> getCommands() {
        return commands;
    }

    public Command findCommand(String name) {
        for (Command c : commands) {
            if (c.commandName.equals(name)) return c;
        }
        return null;
    }
}
