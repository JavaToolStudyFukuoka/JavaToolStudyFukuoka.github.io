package com.example.mvn.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/age")
public class AgeController extends BaseController {
    @Override
    protected List<String> getMenu() {
        return Arrays.asList("karaAge", "atuAge");
    }

    @Override
    protected List<String> getProc(String proc, String action) {
        return Arrays.asList("karaAge", "atuAge", proc, action);
    }
}
