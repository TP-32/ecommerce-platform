package com.tp32.ecommerceplatform.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    /**
     * Routes all requests to "/" to the static index.html page
     * @return the view that is to be resolved and displayed
     */
    @RequestMapping("/")
    public String index() {
        return "index.html";
    }
    
}
