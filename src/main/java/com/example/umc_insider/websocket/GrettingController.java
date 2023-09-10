//package com.example.umc_insider.websocket;
//
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.util.HtmlUtils;
//
//@Controller
//public class GrettingController {
//    @MessageMapping("/hello")
//    @SendTo("/topic/greeting")
//    public Greeting greeting(HelloMessage message) throws Exception{
//        // Thread.sleep(1000); // delay
//        return new Greeting("Hello," + HtmlUtils.htmlEscape(message.getName()));
//    }
//}
