package CustomSetup;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class GeneralEventHandler implements EventHandler<ActionEvent>{
    private List<EventHandler<ActionEvent>> handlers;

    public GeneralEventHandler() {
        handlers = new ArrayList<>();
    }

    @Override
    public void handle(ActionEvent event) {
        for (EventHandler<ActionEvent> handler : handlers) {
            handler.handle(event);
        }
    }

    public void addHandler(EventHandler<ActionEvent> handler) {
        handlers.add(handler);
    }

    public void removeHandler(EventHandler<ActionEvent> handler) {
        handlers.remove(handler);
    }
}
