package com.squasage.alarm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ConfigurationController {

    @RequestMapping(value = "/configuration")
    public String configuration() {
        return "configuration";
    }

}
