package io.ruin.process.event;

import kilim.Pausable;

import java.io.IOException;

@FunctionalInterface
public interface EventConsumer {

    void accept(Event event) throws Pausable, IOException;

}