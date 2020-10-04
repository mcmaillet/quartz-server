package com.scheduler.scheduler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @RequestMapping(path = "/")
    public String index() {
        return "redirect:/swagger-ui.html";
    }
}
