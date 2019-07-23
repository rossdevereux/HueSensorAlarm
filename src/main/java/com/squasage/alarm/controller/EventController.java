package com.squasage.alarm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EventController {

    @RequestMapping(value = "/events")
    public String events() {
        // TODO Load events
        return "events";
    }

}
