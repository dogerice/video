package com.hisense.video.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Auther:liaohongbing@hisense.com
 * @date:2019/4/23
 * @des
 */

@RequestMapping("/test")
@Controller
public class TestController {

    @RequestMapping("/index")
    public String test(){
        return "test";
    }

}
