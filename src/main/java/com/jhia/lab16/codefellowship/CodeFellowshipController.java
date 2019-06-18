package com.jhia.lab16.codefellowship;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CodeFellowshipController {

    @GetMapping("/")
    public String getIndex() {
        return "index";
    }

    @GetMapping("/codefellowship")
    public String getFellowship() {
        return "codefellowship";
    }
}
