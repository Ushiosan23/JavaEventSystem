package com.github.ushiosan23.eventsystem;

import com.github.ushiosan23.eventsystem.event.ValueEvent;
import com.github.ushiosan23.eventsystem.property.ObjectProperty;

import java.util.Timer;
import java.util.TimerTask;

public class ExampleEvent {

    ObjectProperty<Integer> example = new ObjectProperty<>(1);

    public static void main(String[] args) {
        ExampleEvent exampleEvent = new ExampleEvent();
        exampleEvent.example.setOnChange(exampleEvent::onChanged);

        System.out.println(exampleEvent.example);
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                exampleEvent.example.setPropertyValue(120);
                timer.cancel();
            }
        }, 3000);
    }

    private void onChanged(ValueEvent<Integer> event) {
        System.out.println(event.getSource());
    }

}
