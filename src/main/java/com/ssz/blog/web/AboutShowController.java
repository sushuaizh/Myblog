package com.ssz.blog.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author sushuaizhen
 * @date 2020/8/17
 */
@Controller
public class AboutShowController {

    @GetMapping("/about")
    public String about(){

        return "about";
    }
}
