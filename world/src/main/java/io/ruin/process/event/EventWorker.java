package io.ruin.process.event;

import io.ruin.process.CoreWorker;
import io.ruin.process.task.TaskWorker;

import java.util.concurrent.ConcurrentLinkedQueue;

public class EventWorker extends TaskWorker {

    private static final ConcurrentLinkedQueue<Event> EVENTS = new ConcurrentLinkedQueue<>();

    public static void process() {
        try {
            EVENTS.removeIf(event -> !event.tick());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Event createEvent(EventConsumer eventConsumer) {
        /* manual processing is required when using this! */
        return new Event(eventConsumer);
    }

    public static Event startEvent(EventConsumer eventConsumer) {
        Event event = new Event(eventConsumer);
        if(!CoreWorker.isPast(CoreWorker.Stage.LOGIC)) {
            if (!event.tick())
                return event;
        }
        EVENTS.add(event);
        return event;
    }

}