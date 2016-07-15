package com.example.mvn;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller                // --> [1]
@EnableAutoConfiguration   // --> [2]
@ComponentScan
public class App
{
    @RequestMapping("/")   // --> [3]
    @ResponseBody          // --> [4]
    String home() {
        return "Hello World!"; // --> [5]
    }

    public static void main( String[] args ) {
        SpringApplication.run(App.class, args); // -->[6]
    }
}
