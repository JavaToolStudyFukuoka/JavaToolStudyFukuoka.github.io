package com.example.mvn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/sage")
public class SageController extends BaseController {
    @Override
    protected List<String> getMenu() {
        return Arrays.asList("gyuSagari", "sagaGyu");
    }

    @Override
    protected List<String> getProc(String proc, String action) {
        return Arrays.asList("sage", proc, action);
    }
}
