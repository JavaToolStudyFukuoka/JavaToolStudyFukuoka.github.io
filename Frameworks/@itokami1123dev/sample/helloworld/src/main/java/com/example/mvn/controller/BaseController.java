package com.example.mvn.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Controller
public class BaseController {
    @RequestMapping("/*Compute")
    @ResponseBody
    String compute() {
        return String.valueOf(1 + 1);
    }

    @RequestMapping("/view")
    String view() {
        return "index";
    }

    @RequestMapping("**/menu")
    @ResponseBody
    List<String> menu() {
        return this.getMenu();
    }

    protected List<String> getMenu() {
        return new ArrayList<String>();
    }

    @RequestMapping("**/api/__{proc}__/{action}")
    @ResponseBody
    List<String> proc(
            @PathVariable(value = "proc") String proc,
            @PathVariable(value = "action") String action

    ) {
        return this.getProc(proc, action);
    }

    protected List<String> getProc(String proc, String action) {
        return new ArrayList<String>();
    }

}
