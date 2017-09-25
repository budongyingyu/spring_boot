package com.li.test.spring_boot.readinglist;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ReaderLoginController {
    @RequestMapping("login")
    public String readerLogin(){
        return "login";
    }
}
